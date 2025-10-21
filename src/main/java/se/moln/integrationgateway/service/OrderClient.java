package se.moln.integrationgateway.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.moln.integrationgateway.dto.OrderStats;

@Service
public class OrderClient {

  private final RestTemplate http;
  private final String baseUrl;

  public OrderClient(RestTemplate http, @Value("${services.order.url}") String baseUrl) {
    this.http = http;
    this.baseUrl = baseUrl;
  }

  public OrderStats getStats() {

    try {
      String url = baseUrl + "/analytics/monthly-kpis";
      return http.getForObject(url, OrderStats.class);
    } catch (Exception e) {
      return new OrderStats(0L, 0L, 0.0);
    }
  }
}
