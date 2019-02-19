package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shipment implements Iterable<Product> {

  private final List<Product> products = new ArrayList<>();

  @Override
  public Iterator<Product> iterator() {
    return products.iterator();
  }

  public void add(Product product) {
    products.add(product);
  }

}
