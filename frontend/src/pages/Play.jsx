import { useCallback, useEffect, useState } from "react";
import { botDart, createGame, deleteGame, getBots, throwDart, undoDart } from "../api.js";

const FRIEND = "friend";
const START_SCORES = [301, 401, 501];
const MULT = { S: 1, D: 2, T: 3 };
const label = (v) => (v === 0 ? "Miss" : v === 50 ? "Bull" : String(v));

export default function Play() {
  const [setup, setSetup] = useState({ start: 501, opponent: FRIEND });
  const [bots, setBots] = useState({ status: "loading", list: [] });
  const [game, setGame] = useState(null);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");

  const loadBots = useCallback(() => {
    setBots({ status: "loading", list: [] });
    getBots()
      .then((list) => setBots({ status: "ready", list }))
      .catch((e) => setBots({ status: "error", list: [], error: e.message }));
  }, []);
  useEffect(loadBots, [loadBots]);

  // Runs one backend call and stores the game state it returns.
  const run = async (call) => {
    setBusy(true);
    setError("");
    try {
      setGame(await call());
    } catch (e) {
      setError(e.message);
    } finally {
      setBusy(false);
    }
  };

  const begin = () => run(() => createGame(setup.start, setup.opponent === FRIEND ? null : setup.opponent));
  const quit = () => {
    deleteGame(game.id).catch(() => {});
    setGame(null);
    setError("");
  };

  const cur = game?.players[game.currentPlayer];

  // When it's the bot's turn, ask the backend for one dart at a time so the turn is readable.
  useEffect(() => {
  if (!game || game.status !== "IN_PROGRESS" || !cur.bot || busy || error) return;

  
  // Pause before the next dart
  // Longer pause after the third dart so it can be seen
  const delay =  1500;

  const id = setTimeout(() => {
    run(() => botDart(game.id));
  }, delay);

  return () => clearTimeout(id);
}, [game, busy, error]); // eslint-disable-line react-hooks/exhaustive-deps

  if (!game) {
    return <Setup setup={setup} setSetup={setSetup} bots={bots} onRetryBots={loadBots} onStart={begin} busy={busy} error={error} />;
  }

  const humanTurn = game.status === "IN_PROGRESS" && !cur.bot;

  return (
    <section className="page game">
      <div className="boards">
        {game.players.map((p, i) => (
          <Scoreboard key={i} player={p} index={i} game={game} active={game.currentPlayer === i && game.status === "IN_PROGRESS"} />
        ))}
      </div>

      <div className="statusbar">
        <Status game={game} />
        {game.status === "IN_PROGRESS" && <EndGame onEnd={quit} />}
      </div>

      {error && (
        <div className="notice error" role="alert">
          <p>{error}</p>
          <button className="btn" onClick={() => setError("")}>OK</button>
        </div>
      )}

      {game.status === "FINISHED" ? (
        <Result game={game} onRematch={begin} onNew={quit} busy={busy} onUndo={() => run(() => undoDart(game.id))} />
      ) : (
        <DartPad
          disabled={!humanTurn || busy}
          canUndo={game.canUndo && !busy}
          onPick={(value) => run(() => throwDart(game.id, value))}
          onUndo={() => run(() => undoDart(game.id))}
        />
      )}
    </section>
  );
}

function Setup({ setup, setSetup, bots, onRetryBots, onStart, busy, error }) {
  return (
    <section className="page setup">
      <div className="hero">
        <div className="chalk" aria-hidden="true">{setup.start}</div>
        <div>
          <h1>Pick a game, pick your opponents.</h1>
          <p className="lede">Score a match dart by dart. The board tells you when you can checkout and keeps your three-dart average.</p>
        </div>
      </div>

      <fieldset>
        <legend>Starting score</legend>
        <div className="segmented">
          {START_SCORES.map((n) => (
            <label key={n} className={setup.start === n ? "on" : ""}>
              <input type="radio" name="start" checked={setup.start === n} onChange={() => setSetup({ ...setup, start: n })} />
              {n}
            </label>
          ))}
        </div>
      </fieldset>

      <fieldset>
        <legend>Opponent</legend>
        <div className="opponents">
          <label className={"opp" + (setup.opponent === FRIEND ? " on" : "")}>
            <input type="radio" name="opp" checked={setup.opponent === FRIEND} onChange={() => setSetup({ ...setup, opponent: FRIEND })} />
            <strong>A friend</strong>
            <span>Take turns on this device and enter both players&rsquo; darts.</span>
          </label>
          {bots.list.map((b) => (
            <label key={b.name} className={"opp" + (setup.opponent === b.name ? " on" : "")}>
              <input type="radio" name="opp" checked={setup.opponent === b.name} onChange={() => setSetup({ ...setup, opponent: b.name })} />
              <strong>{b.name}</strong>
              <span>Hits the treble {b.treblePercentage}% of the time and finishes {b.checkoutPercentage}% of checkouts.</span>
            </label>
          ))}
        </div>
        {bots.status === "loading" && <p className="lede" role="status">Loading the pro bots&hellip;</p>}
        {bots.status === "error" && (
          <div className="notice error" role="alert">
            <p>{bots.error}</p>
            <button className="btn" onClick={onRetryBots}>Try again</button>
          </div>
        )}
      </fieldset>

      {error && <div className="notice error" role="alert"><p>{error}</p></div>}
      <button className="btn primary big" onClick={onStart} disabled={busy}>{busy ? "Starting\u2026" : "Start " + setup.start}</button>
    </section>
  );
}

function Scoreboard({ player, active, game, index }) {
const route = player.checkout;
const last = game.lastTurn?.player === index ? game.lastTurn : null;
const slots = active ? game.turnDarts : (last?.darts ?? []);
  return (
    <div className={"board" + (active ? " active" : "")} aria-label={player.name + " scoreboard"}>
      <div className="who">
        <h2>{player.name}</h2>
        {active && <span className="turnflag">{player.bot ? "Throwing" : "Your throw"}</span>}
      </div>
      <div className="score" aria-live={active ? "polite" : "off"}>{player.score}</div>
      <div className="route">
        {route ? <>To finish: <b>{route.join("  ")}</b></> : player.score > 170 ? "No finish above 170" : "No finish available"}
      </div>
      <div className="slots" aria-label="Darts this turn">
        {[0, 1, 2].map((i) => (
          <span key={i} className={"slot" + (slots[i] !== undefined ? " filled" : "")}>{slots[i] !== undefined ? label(slots[i]) : "\u2013"}</span>
        ))}
      </div>
      <dl className="mini">
        <div><dt>3-dart avg</dt><dd>{player.stats.threeDartAverage}</dd></div>
        <div><dt>Darts</dt><dd>{player.stats.darts}</dd></div>
      </dl>
      {last && (
        <p className="last">
          Last turn: {last.darts.map(label).join(", ")} {last.bust ? "(bust)" : "= " + last.scored}
        </p>
      )}
    </div>
  );
}

function Status({ game }) {
  const e = game.lastEvent;
  let text;
  let tone = "";
  if (game.status === "FINISHED") {
    text = game.players[game.winner].name + " wins the leg.";
    tone = "win";
  } else if (e?.outcome === "BUST" || e?.outcome === "NOT_A_DOUBLE") {
    const who = game.players[e.player];
    text = who.name + (e.outcome === "BUST" ? " bust." : " must finish on a double.") + " Score stays at " + who.score + ". " + game.players[game.currentPlayer].name + " to throw.";
    tone = "bust";
  } else {
    const p = game.players[game.currentPlayer];
    text = p.name + (p.bot ? " is throwing dart " : ", enter dart ") + (game.turnDarts.length + 1) + " of 3.";
  }
  return <p className={"status " + tone} role="status">{text}</p>;
}

function DartPad({ disabled, onPick, onUndo, canUndo }) {
  const [ring, setRing] = useState("S");
  // Back to Single after every dart so a stray tap can't score a treble.
  const throwIt = (value) => {
    setRing("S");
    onPick(value);
  };
  return (
    <div className={"pad" + (disabled ? " off" : "")}>
      <div className="segmented small" role="group" aria-label="Dart type">
        {[["S", "Single"], ["D", "Double"], ["T", "Treble"]].map(([r, l]) => (
          <button key={r} type="button" className={ring === r ? "on" : ""} aria-pressed={ring === r} disabled={disabled} onClick={() => setRing(r)}>{l}</button>
        ))}
      </div>
      <div className="numbers">
        {Array.from({ length: 20 }, (_, i) => i + 1).map((n) => (
          <button key={n} type="button" disabled={disabled} onClick={() => throwIt(n * MULT[ring])}>{n}</button>
        ))}
      </div>
      <div className="extras three">
        <button type="button" disabled={disabled} onClick={() => throwIt(25)}>Outer 25</button>
        <button type="button" disabled={disabled} onClick={() => throwIt(50)}>Bull 50</button>
        <button type="button" disabled={disabled} onClick={() => throwIt(0)}>Miss</button>
      </div>
      <button type="button" className="btn undo" disabled={!canUndo} onClick={onUndo}>Undo last dart</button>
    </div>
  );
}

function EndGame({ onEnd }) {
  const [asking, setAsking] = useState(false);
  if (!asking) return <button type="button" className="btn small" onClick={() => setAsking(true)}>End game</button>;
  return (
    <span className="confirm" role="group" aria-label="Confirm ending the game">
      <button type="button" className="btn small danger" onClick={onEnd}>Yes, end it</button>
      <button type="button" className="btn small" onClick={() => setAsking(false)}>Keep playing</button>
    </span>
  );
}

function Result({ game, onRematch, onNew, onUndo, busy }) {
  const winner = game.players[game.winner];
  return (
    <div className="result">
      <h2>{winner.name} wins</h2>
      <table>
        <thead>
          <tr><th scope="col">Player</th><th scope="col">3-dart average</th><th scope="col">Darts</th><th scope="col">Bullseyes</th><th scope="col">Treble 20s</th></tr>
        </thead>
        <tbody>
          {game.players.map((p, i) => (
            <tr key={i}>
              <th scope="row">{p.name}</th><td>{p.stats.threeDartAverage}</td><td>{p.stats.darts}</td><td>{p.stats.bullseyes}</td><td>{p.stats.trebleTwenties}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="extras">
        <button className="btn primary" onClick={onRematch} disabled={busy}>Rematch</button>
        <button className="btn" onClick={onNew}>Change game</button>
        {game.canUndo && <button className="btn" onClick={onUndo} disabled={busy}>Undo last dart</button>}
      </div>
    </div>
  );
}
