package io.github.wushuzh.core.collections;

import java.util.HashMap;
import java.util.Map;

public class Java8EnhancementsMap {
  public static void main(String[] args) {
    final Product defaultProduct = new Product(-1, "Whatever the customer wants", 100);

    final Map<Integer, Product> idToProduct = new HashMap<>();
    idToProduct.put(1, new Product(1, "Wooden Door", 35));
    idToProduct.put(2, new Product(2, "floorPanel", 25));
    idToProduct.put(3, new Product(3, "window", 20));

    // ==================getOrDefault=========================
    System.out.println("Try to get a not exist Product is " + idToProduct.get(10));
    // Try to get a not exist Product is null

    System.out.println("Return a Default value when get a no exit one: "
        + idToProduct.getOrDefault(10, defaultProduct));
    // Return a Default value when get a no exit one:
    // Product(id=-1, name=Whatever the customer wants, weight=100)

    // ==================replace=========================
    Product oldOne = idToProduct.replace(1, new Product(1, "Big Door", 50));
    System.out.println(oldOne);
    System.out.println(idToProduct.get(1));
    // Product(id=1, name=Wooden Door, weight=35)
    // Product(id=1, name=Big Door, weight=50)

    Product noOldOne = idToProduct.replace(4, new Product(1, "Big Door", 50));
    System.out.println(noOldOne);
    System.out.println(idToProduct.get(4));
    // null
    // null

    // ==================replaceAll=========================
    System.out.println(idToProduct);
    idToProduct.replaceAll(
        (id, oldProduct) -> new Product(id, oldProduct.getName(), oldProduct.getWeight() + 10));
    System.out.println(idToProduct);
    // {1=Product(id=1, name=Big Door, weight=50), 2=Product(id=2, name=floorPanel, weight=25),
    // 3=Product(id=3, name=window, weight=20)}
    // {1=Product(id=1, name=Big Door, weight=60), 2=Product(id=2, name=floorPanel, weight=35),
    // 3=Product(id=3, name=window, weight=30)}

    // ==================computeIfAbsent=========================
    for (int pid : new int[] {10, 2}) {
      Product result =
          idToProduct.computeIfAbsent(pid, (id) -> new Product(id, "Custom Product", 10));
      System.out.println(result);
      System.out.println(idToProduct.get(pid));
    }
    // Product(id=10, name=Custom Product, weight=10)
    // Product(id=10, name=Custom Product, weight=10)
    // Product(id=2, name=floorPanel, weight=35)
    // Product(id=2, name=floorPanel, weight=35)

    // ==================forEach=========================
    idToProduct.forEach((key, value) -> System.out.println(key + " -> " + value));
    // 1 -> Product(id=1, name=Big Door, weight=60)
    // 2 -> Product(id=2, name=floorPanel, weight=35)
    // 3 -> Product(id=3, name=window, weight=30)
    // 10 -> Product(id=10, name=Custom Product, weight=10)
  }
}
