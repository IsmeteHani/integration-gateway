package se.moln.integrationgateway.dto;

import se.moln.integrationgateway.util.FormatUtil;

import java.util.List;
import java.util.stream.Collectors;

public record SummaryResponse(
        WeatherSummary weather,
        OrderStats orderStats,
        List<ProductResponse> products,
        String pretty
){
  public static SummaryResponse of(WeatherSummary w, OrderStats o, List<ProductResponse> p){
    String top = p.stream()
            .limit(3)
            .map(pr -> pr.name() + " " + FormatUtil.priceSEK(pr.price()))
            .collect(Collectors.joining(", "));

    String pretty = String.format(
            "%s: %s. Orders today: %d (%d/7d). Top products: %s",
            w.city(), w.summary(), o.today(), o.last7Days(), top
    );

    return new SummaryResponse(w, o, p, pretty);
  }
}
