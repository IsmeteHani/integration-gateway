package se.moln.integrationgateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import se.moln.integrationgateway.dto.WeatherSummary;
import se.moln.integrationgateway.service.WeatherClient;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "weather-controller")
public class WeatherController {

    private final WeatherClient weatherClient;

    public WeatherController(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    @GetMapping
    @Operation(summary = "Weather summary by city")
    public WeatherSummary get(@RequestParam(defaultValue = "Stockholm") String city) {
        return weatherClient.getWeather(city);
    }
}
