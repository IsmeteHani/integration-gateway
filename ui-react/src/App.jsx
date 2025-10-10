import React, { useEffect, useState } from "react";
import { getSummary } from "./lib/api";
import WeatherCard from "./components/WeatherCard";
import StatsCard from "./components/StatsCard";
import ProductsTable from "./components/ProductsTable";
import "./styles.css";

export default function App(){
  const [city, setCity] = useState(() => localStorage.getItem("city") || "Gothenburg");
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");
  const [dark, setDark] = useState(() => {
    const saved = localStorage.getItem("theme");
    return saved ? saved === "dark" : false;
  });

  useEffect(() => {
    const cl = document.documentElement.classList;
    dark ? cl.add("dark") : cl.remove("dark");
    localStorage.setItem("theme", dark ? "dark" : "light");
  }, [dark]);

  useEffect(() => {
    localStorage.setItem("city", city);
  }, [city]);

  async function load(){
    try { setLoading(true); setErr(""); setData(await getSummary(city)); }
    catch(e){ setErr("Nuk u lexua të dhënat"); }
    finally { setLoading(false); }
  }

  return (
    <div className="mx-auto max-w-6xl p-6">
      <header className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-3xl font-extrabold tracking-tight">Moln Integration Dashboard</h1>
        <div className="flex items-center gap-2">
          <input
            value={city}
            onChange={e=>setCity(e.target.value)}
            className="input"
            placeholder="City"
          />
          <button onClick={load} className="btn">Refresh</button>
          <button onClick={()=>setDark(d=>!d)} className="btn" title="Toggle theme">
            {dark ? "🌙" : "☀️"}
          </button>
        </div>
      </header>

      <p className="mt-5 text-slate-600 dark:text-slate-400">
        {data ? data.pretty : " "}
      </p>

      {err && <p className="mt-3 text-red-600">{err}</p>}

      {/* Skeleton during loading */}
      {loading && (
        <div className="grid gap-5 md:grid-cols-3 mt-4">
          <div className="skeleton"></div>
          <div className="skeleton"></div>
          <div className="skeleton"></div>
        </div>
      )}

      {!loading && data && (
        <div className="grid gap-5 md:grid-cols-3 mt-4">
          <WeatherCard weather={data.weather} />
          <StatsCard stats={data.orderStats} />
          <ProductsTable items={data.products||[]} />
        </div>
      )}
    </div>
  );
}
