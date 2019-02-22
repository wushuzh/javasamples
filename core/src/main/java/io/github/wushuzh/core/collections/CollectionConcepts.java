package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class CollectionConcepts {
  public static void main(String[] args) {
    Product door = new Product(1, "Wooden Door", 35);
    Product floorPanel = new Product(2, "Floor Panel", 25);
    Product window = new Product(3, "Glass Window", 10);

    Collection<Product> products = new ArrayList<>();
    products.add(door);
    products.add(floorPanel);
    products.add(window);

    // java5 for each syntax sugar
    for (Product product : products) {
      System.out.println(product);
    }
    // Product(name=Wooden Door, weight=35)
    // Product(name=Floor Panel, weight=25)
    // Product(name=Glass Window, weight=10)

    // while loop and iterator allows the remove operation
    final Iterator<Product> productIterator = products.iterator();
    while (productIterator.hasNext()) {
      Product product = productIterator.next();
      if (product.getWeight() > 20) {
        System.out.println(product);
      } else {
        // products.remove(product) will cause java.util.ConcurrentModificationException
        productIterator.remove();
      }
    }
    // Product(name=Wooden Door, weight=35)
    // Product(name=Floor Panel, weight=25)

    System.out.println(products.isEmpty()); // false
    System.out.println(products.size()); // 2
    System.out.println(products.contains(window)); // false

    Collection<Product> otherProducts = new ArrayList<>();
    otherProducts.add(window);
    otherProducts.add(door);

    products.removeAll(otherProducts);
    System.out.println(products);
    // [Product(name=Floor Panel, weight=25)]
    boolean successful = products.remove(window);
    System.out.println(successful); // false

  }
}
