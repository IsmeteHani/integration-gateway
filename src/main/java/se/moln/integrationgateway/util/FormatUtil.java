package se.moln.integrationgateway.util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtil {
  private static final NumberFormat SEK = NumberFormat.getCurrencyInstance(new Locale("sv","SE"));

  public static String priceSEK(Double v) {
    if (v == null) return "0 kr";
    return SEK.format(v);
  }
}
