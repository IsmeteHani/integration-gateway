import React, { useEffect, useState } from "react";
import { getSummary } from "./lib/api";
import WeatherCard from "./components/WeatherCard";
import StatsCard from "./components/StatsCard";
import ProductsTable from "./components/ProductsTable";
import "./styles.css";

export default function App() {
  const [city, setCity] = useState(() => localStorage.getItem("city") || "Gothenburg");
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");
  const [dark, setDark] = useState(() => (localStorage.getItem("theme") === "dark"));

  // tema
  useEffect(() => {
    const cl = document.documentElement.classList;
    dark ? cl.add("dark") : cl.remove("dark");
    localStorage.setItem("theme", dark ? "dark" : "light");
  }, [dark]);

  // persisto qytetin
  useEffect(() => {
    localStorage.setItem("city", city);
  }, [city]);

  async function load(c = city) {
    try {
      setLoading(true);
      setErr("");
      const resp = await getSummary(c);
      setData(resp);
    } catch (e) {
      console.error("getSummary failed:", e);
      setErr("Nuk u lexua të dhënat");
      setData(null);
    } finally {
      setLoading(false);
    }
  }

  // Thirre në mount dhe kur ndryshon city
  useEffect(() => {
    load(city);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [city]);

  return (
      <div className="mx-auto max-w-6xl p-6">
        <header className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <h1 className="text-3xl font-extrabold tracking-tight">Moln Integration Dashboard</h1>
          <div className="flex items-center gap-2">
            <input
                value={city}
                onChange={e => setCity(e.target.value)}
                className="input"
                placeholder="City"
                onKeyDown={(e) => e.key === "Enter" && load()}
            />
            <button onClick={() => load()} className="btn" disabled={loading}>
              {loading ? "Loading…" : "Refresh"}
            </button>
            <button onClick={() => setDark(d => !d)} className="btn" title="Toggle theme">
              {dark ? "🌙" : "☀️"}
            </button>
          </div>
        </header>

        {/* Info e lehtë sipër */}
        {data && (
            <p className="mt-5 text-slate-600 dark:text-slate-400">
              Weather: {data.weather?.city} — {data.weather?.tempC}°C, {data.weather?.summary}
            </p>
        )}

        {err && <p className="mt-3 text-red-600">{err}</p>}

        {/* Skeleton gjatë loading */}
        {loading && (
            <div className="grid gap-5 md:grid-cols-3 mt-4">
              <div className="skeleton" />
              <div className="skeleton" />
              <div className="skeleton" />
            </div>
        )}

        {!loading && data && (
            <div className="grid gap-5 md:grid-cols-3 mt-4">
              <WeatherCard weather={data.weather} />
              <StatsCard stats={data.orderStats} />
              <ProductsTable items={data.products || []} />
            </div>
        )}
      </div>
  );
}
