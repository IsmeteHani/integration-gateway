package se.moln.integrationgateway.service;

import org.springframework.stereotype.Service;
import se.moln.integrationgateway.dto.WeatherSummary;

import java.util.Map;

@Service
public class WeatherClient {

  private static final Map<String, Double> BASE = Map.of(
          "Gothenburg", 14.0,
          "Stockholm", 12.0,
          "MalmÃ¶",     15.0
  );

  public WeatherSummary getWeather(String city) {
    double base = BASE.getOrDefault(city, 13.0);
    double temp = Math.round((base + Math.sin(System.currentTimeMillis() / 3.6e6) * 3) * 10.0) / 10.0;
    String summary = temp >= base ? "Partly sunny" : "Cloudy";
    return new WeatherSummary(city, temp, Math.round(temp) + "Â°C, " + summary);
  }
}
