package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rotate {
  public static void main(String[] args) {
    List<Product> products = new ArrayList<>();

    products.add(new Product(1, "first", 10));
    products.add(new Product(2, "second", 20));
    products.add(new Product(3, "third", 30));

    System.out.println(products);
    System.out.println();

    Collections.rotate(products, 1);
    System.out.println(products);
    System.out.println();

    Collections.rotate(products, 1);
    System.out.println(products);
    System.out.println();

    Collections.rotate(products, 1);
    System.out.println(products);
    System.out.println();

    // [Product(id=1, name=first, weight=10),
    // Product(id=2, name=second, weight=20), Product(id=3, name=third, weight=30)]
    //
    // [Product(id=3, name=third, weight=30),
    // Product(id=1, name=first, weight=10), Product(id=2, name=second, weight=20)]
    //
    // [Product(id=2, name=second, weight=20),
    // Product(id=3, name=third, weight=30), Product(id=1, name=first, weight=10)]
    //
    // [Product(id=1, name=first, weight=10),
    // Product(id=2, name=second, weight=20), Product(id=3, name=third, weight=30)]

  }
}
