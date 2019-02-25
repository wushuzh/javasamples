package io.github.wushuzh.core.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ViewsOverMaps {
  public static void main(String[] args) {
    final Map<Integer, Product> idToProduct = new HashMap<>();

    final Product door = new Product(1, "door", 35);
    final Product floorPanel = new Product(2, "floorPanel", 30);
    final Product window = new Product(3, "window", 20);
    idToProduct.put(1, door);
    idToProduct.put(2, floorPanel);
    idToProduct.put(3, window);

    System.out.println(idToProduct);
    System.out.println();

    final Set<Integer> ids = idToProduct.keySet();
    System.out.println(ids);

    ids.remove(1);
    System.out.println(ids);
    System.out.println(idToProduct);
    System.out.println("=============================");

    try {
      ids.add(4);
    } catch (UnsupportedOperationException e) {
      System.out.println("ids is a view of keys of underline Map, be careful!");
    }
    // {1=Product(id=1, name=door, weight=35), 2=Product(id=2, name=floorPanel, weight=30),
    // 3=Product(id=3, name=window, weight=20)}
    //
    // [1, 2, 3]
    // [2, 3]
    // {2=Product(id=2, name=floorPanel, weight=30), 3=Product(id=3, name=window, weight=20)}
    // =============================
    // ids is a view of keys of underline Map, be careful!

    // Note Collection is used since values are not unique
    final Collection<Product> products = idToProduct.values();

    System.out.println(products);
    System.out.println();

    products.remove(floorPanel);
    System.out.println(products);
    System.out.println(idToProduct);
    System.out.println("=============================");

    try {
      products.add(floorPanel);
    } catch (UnsupportedOperationException e) {
      System.out.println("products is a view of values of underline Map, be careful!");
    }
    // [Product(id=2, name=floorPanel, weight=30), Product(id=3, name=window, weight=20)]
    //
    // [Product(id=3, name=window, weight=20)]
    // {3=Product(id=3, name=window, weight=20)}
    // =============================
    // products is a view of values of underline Map, be careful!

    final Set<Map.Entry<Integer, Product>> entries = idToProduct.entrySet();
    for (Entry<Integer, Product> entry : entries) {
      System.out.println(entry.getKey() + " -> " + entry.getValue());
      entry.setValue(floorPanel);
    }
    System.out.println(idToProduct);
    // 3 -> Product(id=3, name=window, weight=20)
    // {3=Product(id=2, name=floorPanel, weight=30)}

  }
}
