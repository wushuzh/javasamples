package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort {
  public static void main(String[] args) {
    List<Product> products = new ArrayList<>();

    products.add(new Product(1, "aaa", 30));
    products.add(new Product(2, "aa", 20));
    products.add(new Product(3, "a", 10));

    System.out.println(products);

    Collections.sort(products, Product.BY_NAME);
    System.out.println(products);

    products.sort(Product.BY_WEIGHT);
    System.out.println(products);

    // [Product(id=1, name=aaa, weight=30), Product(id=2, name=aa, weight=20),
    // Product(id=3, name=a, weight=10)]

    // [Product(id=3, name=a, weight=10), Product(id=2, name=aa, weight=20),
    // Product(id=1, name=aaa, weight=30)]

    // [Product(id=3, name=a, weight=10), Product(id=2, name=aa, weight=20),
    // Product(id=1, name=aaa, weight=30)]

  }
}
