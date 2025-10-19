// src/lib/api.js
export const API_BASE = import.meta.env.VITE_API_BASE || "https://igw-backend-app.azurewebsites.net";

/** Kthen summary për një qytet */
export async function fetchSummary(city) {
  const url = `${API_BASE}/api/integrations/summary?city=${encodeURIComponent(city)}`;
  const res = await fetch(url);
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(`HTTP ${res.status} ${res.statusText} – ${text}`);
  }
  return res.json();
}

export { fetchSummary as getSummary };

