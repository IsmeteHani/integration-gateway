package se.moln.integrationgateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.moln.integrationgateway.dto.WeatherSummary;
import se.moln.integrationgateway.service.WeatherClient;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherClient weatherClient;
    public WeatherController(WeatherClient weatherClient){ this.weatherClient = weatherClient; }

    @GetMapping
    public ResponseEntity<WeatherSummary> get(@RequestParam(defaultValue="Stockholm") String city){
        return ResponseEntity.ok(weatherClient.getWeather(city));
    }
}
