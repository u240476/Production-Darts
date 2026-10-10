import { NavLink, Route, Routes } from "react-router-dom";
import Play from "./pages/Play.jsx";
import News from "./pages/News.jsx";

export default function App() {
  return (
    <>
      <header className="topbar">
        <NavLink to="/" className="brand">Dart-Board</NavLink>
        <nav aria-label="Main">
          <NavLink to="/" end>Play</NavLink>
          <NavLink to="/news">News</NavLink>
        </nav>
      </header>
      <main>
        <Routes>
          <Route path="/" element={<Play />} />
          <Route path="/news" element={<News />} />
          <Route path="*" element={<p className="notice">That page doesn’t exist. Head back to <NavLink to="/">Play</NavLink>.</p>} />
        </Routes>
      </main>
    </>
  );
}
