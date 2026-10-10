// Every call the frontend makes to the Spring Boot backend.
// Set VITE_API_BASE_URL when the frontend is hosted away from the backend.
const BASE = import.meta.env.VITE_API_BASE_URL || "";

async function request(method, path, { body, signal } = {}) {
  let res;
  try {
    res = await fetch(BASE + path, {
      method,
      signal,
      headers: body !== undefined ? { "Content-Type": "application/json" } : undefined,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });
  } catch (e) {
    if (e.name === "AbortError") throw e;
    throw new Error("Couldn’t reach the server. Check that the backend is running on port 8080.");
  }
  const text = await res.text();
  let data = null;
  try { data = text ? JSON.parse(text) : null; } catch { /* non-JSON body */ }
  if (!res.ok) {
    throw new Error(data?.error ? data.error + " (HTTP " + res.status + ")" : "The server returned HTTP " + res.status + ".");
  }
  return data;
}

// --- NewsController: GET /api/darts-news
export async function getDartsNews(signal) {
  const articles = await request("GET", "/api/darts-news", { signal });
  if (!Array.isArray(articles)) throw new Error("The news server sent an unexpected response.");
  return articles;
}

// --- GameController (all rules run in the Java game classes)
export const getBots = (signal) => request("GET", "/api/bots", { signal });
export const createGame = (startScore, opponent) => request("POST", "/api/games", { body: { startScore, opponent } });
export const throwDart = (id, dart) => request("POST", `/api/games/${id}/dart`, { body: { dart } });
export const botDart = (id) => request("POST", `/api/games/${id}/bot-dart`);
export const undoDart = (id) => request("POST", `/api/games/${id}/undo`);
export const deleteGame = (id) => request("DELETE", `/api/games/${id}`);
