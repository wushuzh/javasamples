package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shipment implements Iterable<Product> {

  private static final int PRODUCT_NOT_PRESENT = -1;
  private final List<Product> products = new ArrayList<>();

  @Override
  public Iterator<Product> iterator() {
    return products.iterator();
  }

  public void add(Product product) {
    products.add(product);
  }

  public void replace(Product oldProduct, Product newProduct) {
    int replaceIdx = products.indexOf(oldProduct);
    if (replaceIdx != PRODUCT_NOT_PRESENT) {
      products.set(replaceIdx, newProduct);
    }
  }
}
