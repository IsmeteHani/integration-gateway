package se.moln.integrationgateway.service;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import se.moln.integrationgateway.dto.ProductResponse;

@Service
public class ProductClient {

  private final RestTemplate http;
  private final String baseUrl;

  public ProductClient(RestTemplate http, @Value("${services.product.url}") String baseUrl) {
    this.http = http;
    this.baseUrl = baseUrl;
  }

  public List<ProductResponse> getProducts(int page, int size) {
    try {
      String url = baseUrl + "/api/products/all";
      ResponseEntity<List<ProductResponse>> resp = http.exchange(
              url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponse>>() {});
      return resp.getBody() != null ? resp.getBody() : Collections.emptyList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }
}
