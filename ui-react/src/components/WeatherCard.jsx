export default function WeatherCard({weather}){
  if(!weather) return null;
  const s = (weather.summary||"").toLowerCase();
  const icon = s.includes("sun") ? "☀️" : s.includes("cloud") ? "⛅" : "🌧️";
  return (
    <div className="card">
      <h3 className="font-semibold mb-2">Weather — {weather.city}</h3>
      <div className="flex items-center gap-4">
        <div className="text-4xl select-none">{icon}</div>
        <div>
          <p className="text-4xl font-bold">{Math.round(weather.tempC)}°C</p>
          <p className="text-slate-600 dark:text-slate-400">{weather.summary}</p>
        </div>
      </div>
    </div>
  );
}
