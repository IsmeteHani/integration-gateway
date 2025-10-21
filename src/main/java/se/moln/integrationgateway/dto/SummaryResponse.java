package se.moln.integrationgateway.dto;

import java.util.List;

public class SummaryResponse {
  private WeatherSummary weather;
  private OrderStats orderStats;
  private List<ProductResponse> products;
  private String source;

  public SummaryResponse() {}

  public SummaryResponse(WeatherSummary weather, OrderStats orderStats,
                         List<ProductResponse> products, String source) {
    this.weather = weather;
    this.orderStats = orderStats;
    this.products = products;
    this.source = source;
  }

  public WeatherSummary getWeather() { return weather; }
  public void setWeather(WeatherSummary weather) { this.weather = weather; }

  public OrderStats getOrderStats() { return orderStats; }
  public void setOrderStats(OrderStats orderStats) { this.orderStats = orderStats; }

  public List<ProductResponse> getProducts() { return products; }
  public void setProducts(List<ProductResponse> products) { this.products = products; }

  public String getSource() { return source; }
  public void setSource(String source) { this.source = source; }
}
