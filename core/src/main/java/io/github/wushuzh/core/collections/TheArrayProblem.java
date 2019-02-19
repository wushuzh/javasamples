package io.github.wushuzh.core.collections;

import java.util.Arrays;
import lombok.val;

public class TheArrayProblem {
  public static void main(String[] args) {
    Product door = new Product("Wooden Door", 35);
    Product floorPanel = new Product("Floor Panel", 25);

    // Create
    Product[] products = {door, floorPanel};

    // Print
    System.out.println(products);
    // Useless print:
    // [Lio.github.wushuzh.core.collections.Product;@15db9742
    System.out.println(Arrays.toString(products));
    // Friendly print:
    // [Product(name=Wooden Door, weight=35), Product(name=Floor Panel, weight=25)]

    // Add
    val windows = new Product("Window", 15);
    try {
      products[2] = windows;
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Impossible to resize the array");
      products = add(windows, products);
    }
    System.out.println(Arrays.toString(products));
    // [Product(name=Wooden Door, weight=35), Product(name=Floor Panel, weight=25), Product(name=Window, weight=15)]

    // Duplicated items: no guarantee of unique
    products = add(windows, products);
    System.out.println(Arrays.toString(products));
    // [Product(name=Wooden Door, weight=35), Product(name=Floor Panel, weight=25), Product(name=Window, weight=15), Product(name=Window, weight=15)]

  }

  private static Product[] add(Product product, Product[] array) {
    int length = array.length;
    Product[] newArray = Arrays.copyOf(array, length + 1);
    newArray[length] = product;
    return newArray;
  }
}
