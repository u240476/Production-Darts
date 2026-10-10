# Oche: darts scorer with pro bots

```
src/main/java/com/example/          your game classes (Finish, ProBot, Checkout, Stats ...) + GameService
src/main/java/com/example/news/     Spring Boot app: NewsController (existing) + GameController (new)
frontend/                           React + Vite: Play page and News page
```

All game rules run in the Java classes. The frontend only displays state and sends darts.

## Run

```
NEWS_API_KEY=your-key mvn spring-boot:run      # http://localhost:8080
cd frontend && npm install && npm run dev      # http://localhost:5173 (/api is proxied to 8080)
```

## API

| Method | Route | Uses |
|---|---|---|
| GET | `/api/darts-news` | NewsController (unchanged) |
| GET | `/api/bots` | `BuildingProPlayer.ChoosingPlayer` |
| POST | `/api/games` `{"startScore":501,"opponent":"Luke Littler"}` | `Game.isValidGame` (opponent optional) |
| GET | `/api/games/{id}` | current state |
| POST | `/api/games/{id}/dart` `{"dart":60}` | `Darts.checkingValidDarts`, `Finish.evaluateDart`, `Checkout`, `Stats.calculate` |
| POST | `/api/games/{id}/bot-dart` | `ProBot.AssigningStats` / `ProBot.ProPlayer` |
| POST | `/api/games/{id}/undo` | takes back the last dart of the current human turn |
| DELETE | `/api/games/{id}` | |

Errors are `{"error":"..."}` with 400 (bad input), 404 (unknown game) or 409 (not your turn / game over).
Games are held in memory (latest 200).

## Changes to your existing Java (all additive; console behaviour is identical)

- `Finish`: the win/bust/double-out decision is now `evaluateDart()`; `updatingScore` calls it.
- `Stats`: new `calculate()` returns the figures; `StatisticsHelper` prints exactly what it did before.
- `Game`: new `isValidGame()`; `gettingGame` calls it.

## Deploying

`npm run build`, then copy `frontend/dist/*` into `src/main/resources/static/` so one Spring Boot app
serves the site and the API on the same origin (no CORS needed).
