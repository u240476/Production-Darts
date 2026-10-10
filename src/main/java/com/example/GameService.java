package com.example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

/**
 * Lets the web frontend play the dart game. It keeps one {@link Session} per game and
 * hands every decision to the existing game classes:
 * <ul>
 *   <li>{@link Game#isValidGame(int)} for 301/401/501</li>
 *   <li>{@link Darts#checkingValidDarts(int)} to validate a thrown dart</li>
 *   <li>{@link Finish#evaluateDart(int, int)} for win / bust / double-out</li>
 *   <li>{@link BuildingProPlayer} and {@link ProBot} for the bots</li>
 *   <li>{@link Checkout#getCheckoutOptions(int)} for finishing hints</li>
 *   <li>{@link Stats#calculate(List)} for the statistics</li>
 * </ul>
 * Games are held in memory only, and the oldest are dropped after {@value #MAX_GAMES}.
 */
public class GameService {

    private static final int MAX_GAMES = 200;
    /** ProBot keeps its percentages in static fields, so bot throws are done one at a time. */
    private static final Object BOT_LOCK = new Object();

    private final Map<String, Session> games = Collections.synchronizedMap(
            new LinkedHashMap<String, Session>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, Session> eldest) {
                    return size() > MAX_GAMES;
                }
            });

    // ---------- what the API returns ----------

    public record BotView(String name, double treblePercentage, double numberPercentage, double checkoutPercentage) {}

    public record StatsView(int darts, double threeDartAverage, int bullseyes, int trebleTwenties) {}

    public record PlayerView(String name, boolean bot, int score, List<String> checkout, StatsView stats) {}

    /** The last dart thrown and what it did: WIN, NOT_A_DOUBLE, BUST or CONTINUE. */
    public record EventView(int player, int dart, String outcome) {}

    /** A finished turn. {@code scored} is how far the player's score dropped during it. */
    public record TurnView(int player, List<Integer> darts, int scored, boolean bust) {}

    public record GameView(String id, int startScore, String status, int currentPlayer, Integer winner,
                           List<PlayerView> players, List<Integer> turnDarts, boolean canUndo,
                           EventView lastEvent, TurnView lastTurn) {}

    // ---------- operations ----------

    public List<BotView> bots() {
        List<BotView> out = new ArrayList<>();
        for (String name : Finish.avaliableBots) {
            double[] s = BuildingProPlayer.ChoosingPlayer(name);
            out.add(new BotView(name, s[0], s[1], s[4]));
        }
        return out;
    }

    /**
     * @param startScore 301, 401 or 501
     * @param opponent the name of a pro bot, or null/blank for a second human player
     */
    public GameView create(Integer startScore, String opponent) {
        if (startScore == null || !Game.isValidGame(startScore)) {
            throw new IllegalArgumentException("Game must be 301,401 or 501");
        }
        String bot = null;
        if (opponent != null && !opponent.isBlank()) {
            if (!Finish.avaliableBots.contains(opponent)) {
                throw new IllegalArgumentException("Invalid player name");
            }
            bot = opponent;
        }
        Session session = new Session(UUID.randomUUID().toString(), startScore, bot);
        games.put(session.id, session);
        return session.view();
    }

    public GameView get(String id) {
        return find(id).view();
    }

    public GameView throwDart(String id, Integer dart) {
        return find(id).throwHumanDart(dart);
    }

    public GameView botDart(String id) {
        return find(id).throwBotDart();
    }

    public GameView undo(String id) {
        return find(id).undo();
    }

    public void delete(String id) {
        games.remove(id);
    }

    private Session find(String id) {
        Session s = games.get(id);
        if (s == null) {
            throw new NoSuchElementException("Game not found");
        }
        return s;
    }

    // ---------- one game ----------

    private static final class Session {
        final String id;
        final int startScore;
        final String botName;            // null when player 2 is human
        final String[] names;
        final int[] score = new int[2];
        /** Per player, the darts stored the way Finish.storingStatistics does (0 for a bust). */
        final List<List<Integer>> statDarts = List.of(new ArrayList<>(), new ArrayList<>());
        int current = 0;
        final List<Integer> turnDarts = new ArrayList<>();
        int turnStartScore;
        Integer winner = null;
        EventView lastEvent = null;
        TurnView lastTurn = null;
        final Deque<Snapshot> history = new ArrayDeque<>();

        Session(String id, int startScore, String botName) {
            this.id = id;
            this.startScore = startScore;
            this.botName = botName;
            this.names = new String[] {"Player 1", botName != null ? botName : "Player 2"};
            score[0] = startScore;
            score[1] = startScore;
            turnStartScore = startScore;
        }

        boolean isBot(int player) {
            return player == 1 && botName != null;
        }

        synchronized GameView throwHumanDart(Integer dart) {
            if (winner != null) throw new IllegalStateException("The game is already finished.");
            if (isBot(current)) throw new IllegalStateException("It is the bot's turn.");
            if (dart == null || !Darts.checkingValidDarts(dart)) {
                throw new IllegalArgumentException("Invalid dart: " + dart);
            }
            history.push(snapshot());
            apply(dart);
            return view();
        }

        synchronized GameView throwBotDart() {
            if (winner != null) throw new IllegalStateException("The game is already finished.");
            if (!isBot(current)) throw new IllegalStateException("It is not the bot's turn.");
            int dart;
            synchronized (BOT_LOCK) {
                ProBot.AssigningStats(BuildingProPlayer.ChoosingPlayer(botName));
                dart = ProBot.ProPlayer(score[current]);
            }
            apply(dart);
            return view();
        }

        /** Applies one dart using the rules in Finish.evaluateDart. */
        private void apply(int dart) {
            int p = current;
            Finish.DartResult result = Finish.evaluateDart(score[p], dart);
            turnDarts.add(dart);
            lastEvent = new EventView(p, dart, result.name());
            switch (result) {
                case WIN -> {
                    score[p] = 0;
                    statDarts.get(p).add(dart);
                    winner = p;
                    lastTurn = new TurnView(p, List.copyOf(turnDarts), turnStartScore, false);
                }
                case NOT_A_DOUBLE, BUST -> {
                    statDarts.get(p).add(0);
                    endTurn(true);
                }
                case CONTINUE -> {
                    score[p] -= dart;
                    statDarts.get(p).add(dart);
                    if (turnDarts.size() == 3) endTurn(false);
                }
            }
        }

        private void endTurn(boolean bust) {
            int p = current;
            lastTurn = new TurnView(p, List.copyOf(turnDarts), turnStartScore - score[p], bust);
            turnDarts.clear();
            history.clear();
            current = 1 - p;
            turnStartScore = score[current];
        }

        boolean canUndo() {
            return !history.isEmpty() && !turnDarts.isEmpty() && !isBot(current);
        }

        synchronized GameView undo() {
            if (!canUndo()) throw new IllegalStateException("Nothing to undo.");
            restore(history.pop());
            return view();
        }

        GameView view() {
            List<PlayerView> players = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                String[] options = Checkout.getCheckoutOptions(score[i]);
                Stats.Summary s = Stats.calculate(statDarts.get(i));
                players.add(new PlayerView(names[i], isBot(i), score[i],
                        options == null ? null : List.of(options),
                        new StatsView(statDarts.get(i).size(), s.threeDartAverage(), s.bullseyes(), s.tripleTwenties())));
            }
            return new GameView(id, startScore, winner == null ? "IN_PROGRESS" : "FINISHED", current, winner,
                    players, List.copyOf(turnDarts), canUndo(), lastEvent, lastTurn);
        }

        

        private record Snapshot(int[] score, List<Integer> darts0, List<Integer> darts1, int current,
                                List<Integer> turnDarts, int turnStartScore, Integer winner,
                                EventView lastEvent, TurnView lastTurn) {}

        private Snapshot snapshot() {
            return new Snapshot(score.clone(), List.copyOf(statDarts.get(0)), List.copyOf(statDarts.get(1)),
                    current, List.copyOf(turnDarts), turnStartScore, winner, lastEvent, lastTurn);
        }

        private void restore(Snapshot s) {
            System.arraycopy(s.score(), 0, score, 0, 2);
            statDarts.get(0).clear();
            statDarts.get(0).addAll(s.darts0());
            statDarts.get(1).clear();
            statDarts.get(1).addAll(s.darts1());
            current = s.current();
            turnDarts.clear();
            turnDarts.addAll(s.turnDarts());
            turnStartScore = s.turnStartScore();
            winner = s.winner();
            lastEvent = s.lastEvent();
            lastTurn = s.lastTurn();
        }
    }
}
