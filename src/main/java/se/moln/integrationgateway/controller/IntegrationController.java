package se.moln.integrationgateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.moln.integrationgateway.dto.*;
import se.moln.integrationgateway.service.OrderClient;
import se.moln.integrationgateway.service.ProductClient;
import se.moln.integrationgateway.service.WeatherClient;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationController {

    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final WeatherClient weatherClient;

    public IntegrationController(ProductClient productClient,
                                 OrderClient orderClient,
                                 WeatherClient weatherClient) {
        this.productClient = productClient;
        this.orderClient = orderClient;
        this.weatherClient = weatherClient;
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(defaultValue = "Stockholm") String city,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        WeatherSummary weather = weatherClient.getWeather(city);

        List<ProductResponse> products;
        try {
            products = productClient.getProducts(page, size);
        } catch (Exception e) {
            products = Collections.emptyList();
        }

        OrderStats stats;
        try {
            stats = orderClient.getStats();
        } catch (Exception e) {
            stats = new OrderStats(0L, 0L, 0.0);
        }

        SummaryResponse dto = new SummaryResponse(weather, stats, products, "integration-gateway");
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/weather")
    public ResponseEntity<WeatherSummary> getWeather(
            @RequestParam(defaultValue = "Stockholm") String city) {
        return ResponseEntity.ok(weatherClient.getWeather(city));
    }
}
