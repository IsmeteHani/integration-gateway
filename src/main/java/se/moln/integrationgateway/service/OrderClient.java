package se.moln.integrationgateway.service;

import org.springframework.stereotype.Service;
import se.moln.integrationgateway.dto.OrderStats;

import java.time.LocalDate;
import java.util.Random;

@Service
public class OrderClient {
  private final Random rnd = new Random();

  public OrderStats getStats() {
    int base = 20 + rnd.nextInt(20);
    int today = (LocalDate.now().getDayOfWeek().getValue() >= 6) ? base / 2 : base;
    int last7 = today * 7 - rnd.nextInt(10);
    double revenue = last7 * (500 + rnd.nextInt(2000));
    return new OrderStats(today, last7, revenue);
  }
}
