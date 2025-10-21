package se.moln.integrationgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/openapi")
public class OpenApiProxyController {

    private final RestClient rest;

    public OpenApiProxyController(RestClient rest) {
        this.rest = rest;
    }

    @Value("${services.order.url}")    private String orderUrl;
    @Value("${services.order.openapi:/v3/api-docs}") private String orderOpenApi;

    @Value("${services.product.url}")  private String productUrl;
    @Value("${services.product.openapi:/v3/api-docs}") private String productOpenApi;

    @Value("${services.user.url}")     private String userUrl;
    @Value("${services.user.openapi:/v3/api-docs}") private String userOpenApi;

    private String resolveBase(String service) {
        return switch (service.toLowerCase()) {
            case "order", "orders"     -> orderUrl   + orderOpenApi;
            case "product","products"  -> productUrl + productOpenApi;
            case "user","users"        -> userUrl    + userOpenApi;
            default -> throw new IllegalArgumentException("Unknown service: " + service);
        };
    }

    @GetMapping(value="/{service}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> proxyJson(@PathVariable String service) {
        String url = resolveBase(service);
        try {
            String body = rest.get().uri(url).retrieve().body(String.class);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(body);
        } catch (Exception ex) {
            return ResponseEntity.status(502)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Failed to fetch OpenAPI from " + url + ": " + ex.getMessage());
        }
    }
}
