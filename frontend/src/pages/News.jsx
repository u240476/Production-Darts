import { useCallback, useEffect, useState } from "react";
import { getDartsNews } from "../api.js";

const fmt = new Intl.DateTimeFormat("en-IE", { dateStyle: "medium", timeStyle: "short" });
const safeUrl = (u) => (/^https?:\/\//i.test(u || "") ? u : null);

export default function News() {
  const [state, setState] = useState({ status: "loading", articles: [], error: "" });

  const load = useCallback((signal) => {
    setState({ status: "loading", articles: [], error: "" });
    getDartsNews(signal)
      .then((articles) => setState({ status: "ready", articles, error: "" }))
      .catch((e) => e.name !== "AbortError" && setState({ status: "error", articles: [], error: e.message }));
  }, []);

  useEffect(() => {
    const ctrl = new AbortController();
    load(ctrl.signal);
    return () => ctrl.abort();
  }, [load]);

  return (
    <section className="page narrow">
      <h1>Darts news</h1>
      <p className="lede">The five latest darts stories, filtered by the backend.</p>

      {state.status === "loading" && <p className="notice" role="status">Loading the latest stories…</p>}

      {state.status === "error" && (
        <div className="notice error" role="alert">
          <p>{state.error}</p>
          <button className="btn" onClick={() => load()}>Try again</button>
        </div>
      )}

      {state.status === "ready" && state.articles.length === 0 && (
        <p className="notice">No darts stories match right now. Check back later.</p>
      )}

      <ul className="articles">
        {state.articles.map((a) => (
          <li key={a.url || a.title}>
            <article>
              {a.publishedAt && <time dateTime={a.publishedAt}>{fmt.format(new Date(a.publishedAt))}</time>}
              <h2>{safeUrl(a.url) ? <a href={safeUrl(a.url)} target="_blank" rel="noopener noreferrer">{a.title}</a> : a.title}</h2>
              {a.description && <p>{a.description}</p>}
            </article>
          </li>
        ))}
      </ul>
    </section>
  );
}
