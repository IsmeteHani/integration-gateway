// src/lib/api.ts
export const API_BASE = import.meta.env.VITE_API_BASE
export const API_PATH = import.meta.env.VITE_API_PATH ?? "/api/weather"

if (!API_BASE) {
  console.error("VITE_API_BASE mungon. Vendose në .env.production")
}

export type Weather = {
  city: string
  tempC?: number
  condition?: string
  [k: string]: unknown
}

export async function fetchWeather(city: string): Promise<Weather> {
  const url = ${API_BASE}?city=
  const res = await fetch(url /* , { credentials: "include" } */)
  if (!res.ok) {
    const text = await res.text().catch(() => "")
    throw new Error(\HTTP \ \ – \\)
  }
  return res.json()
}
