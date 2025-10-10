export default function WeatherCard({weather}){
  if(!weather) return null;
  return (
    <div className="p-4 rounded-2xl shadow bg-white">
      <h3 className="font-semibold mb-2">Weather — {weather.city}</h3>
      <p className="text-3xl font-bold">{Math.round(weather.tempC)}°C</p>
      <p className="text-gray-600">{weather.summary}</p>
    </div>
  );
}
