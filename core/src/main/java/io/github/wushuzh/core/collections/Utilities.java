package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utilities {
  public static void main(String[] args) {
    Product first = new Product(1, "1", 10);
    Product second = new Product(2, "2", 20);
    Product third = new Product(3, "3", 30);

    List<Product> products = new ArrayList<>();
    Collections.addAll(products, first, second, third);
    System.out.println(products);

    final Product heaviestProduct = Collections.max(products, Product.BY_WEIGHT);
    System.out.println(heaviestProduct);

    // [Product(id=1, name=1, weight=10), Product(id=2, name=2, weight=20), Product(id=3, name=3,
    // weight=30)]
    // Product(id=3, name=3, weight=30)
  }
}
