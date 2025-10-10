export async function getSummary(city="Gothenburg"){
  const r = await fetch(`/api/integrations/summary?city=${encodeURIComponent(city)}`);
  if(!r.ok) throw new Error("Failed");
  return r.json();
}
