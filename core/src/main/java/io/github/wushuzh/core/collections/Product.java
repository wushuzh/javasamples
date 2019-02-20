package io.github.wushuzh.core.collections;

import java.util.Comparator;
import lombok.Data;

import static java.util.Comparator.comparing;

public @Data class Product {
  // java8
  public static final Comparator<Product> BY_WEIGHT = comparing(Product::getWeight);
  // java7
  public static final Comparator<Product> OLD_BY_WEIGHT
    = new Comparator<Product>() {
    public int compare(final Product p1, final Product p2) {
      return Integer.compare(p1.getWeight(), p2.getWeight());
    }
  };
  private final String name;
  private final int weight;
}
