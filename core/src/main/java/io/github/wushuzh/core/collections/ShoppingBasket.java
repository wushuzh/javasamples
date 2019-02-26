package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingBasket {

  final List<Product> items = new ArrayList<>();
  int totalWeight;

  public static void main(String[] args) {
    ShoppingBasket basket = new ShoppingBasket();
    basket.add(new Product(1, "first", 10));
    System.out.println(basket);

    try {
      basket.getItems().add(new Product(999, "err", 999));
    } catch (UnsupportedOperationException e) {
      System.out.println("Returning the Readonly view of underline ArrayList");
    }


    basket.add(new Product(2, "second", 20));
    System.out.println(basket);

    // Shopping Basket of
    // [Product(id=1, name=first, weight=10)]
    // with weight: 10
    // Returning the Readonly view of underline ArrayList
    // Shopping Basket of
    // [Product(id=1, name=first, weight=10), Product(id=2, name=second, weight=20)]
    // with weight: 30
  }

  private void add(Product product) {
    items.add(product);
    totalWeight += product.getWeight();
  }

  public String toString() {
    return "Shopping Basket of \n" + items + "\nwith weight: " + totalWeight;
  }

  public List<Product> getItems() {
    return Collections.unmodifiableList(items);
  }
}
