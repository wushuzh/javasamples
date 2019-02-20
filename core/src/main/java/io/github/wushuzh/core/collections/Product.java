package io.github.wushuzh.core.collections;

import java.util.Comparator;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import static java.util.Comparator.comparing;

public @ToString class Product {
  public Product(String name, int weight) {
    super();
    this.name = name;
    this.weight = weight;
  }
  // java8
  public static final Comparator<Product> BY_WEIGHT = comparing(Product::getWeight);
  // java7
  public static final Comparator<Product> OLD_BY_WEIGHT
    = new Comparator<Product>() {
    public int compare(final Product p1, final Product p2) {
      return Integer.compare(p1.getWeight(), p2.getWeight());
    }
  };
  @Getter private final String name;
  @Getter private final int weight;
  @Override
  public int hashCode() {
    return Objects.hash(name, weight);
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Product other = (Product) obj;
    return Objects.equals(name, other.name) && weight == other.weight;
  }
}
