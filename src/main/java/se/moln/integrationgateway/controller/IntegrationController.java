package se.moln.integrationgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import se.moln.integrationgateway.dto.*;
import se.moln.integrationgateway.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationController {
    private final ProductClient productClient;
    private final OrderClient orderClient;
    private final WeatherClient weatherClient;

    public IntegrationController(ProductClient p, OrderClient o, WeatherClient w){
        this.productClient = p;
        this.orderClient = o;
        this.weatherClient = w;
    }

    @GetMapping("/summary")
    public SummaryResponse summary(@RequestParam(defaultValue = "Gothenburg") String city){
        WeatherSummary w = weatherClient.getWeather(city);
        OrderStats o = orderClient.getStats();
        List<ProductResponse> p = productClient.getTopProducts(5);
        return SummaryResponse.of(w, o, p);
    }
}
