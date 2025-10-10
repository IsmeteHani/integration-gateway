package se.moln.integrationgateway.service;

import org.springframework.stereotype.Service;
import se.moln.integrationgateway.dto.ProductResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductClient {

  public List<ProductResponse> getTopProducts(int size) {
    List<ProductResponse> all = new ArrayList<>(List.of(
            new ProductResponse(1L, "iPhone 15 Pro", 14990.0),
            new ProductResponse(2L, "Samsung Galaxy S24", 12990.0),
            new ProductResponse(3L, "MacBook Air 13", 16990.0),
            new ProductResponse(4L, "AirPods Pro", 2990.0),
            new ProductResponse(5L, "Sony WH-1000XM5", 3990.0),
            new ProductResponse(6L, "Logitech MX Master 3S", 1290.0)
    ));
    return all.stream()
            .sorted(Comparator.comparing(ProductResponse::price).reversed())
            .limit(size)
            .toList();
  }
}
