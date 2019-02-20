package io.github.wushuzh.core.collections;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ProductCatalogue implements Iterable<Product> {

  private final Set<Product> products = new HashSet<>();

  @Override
  public Iterator<Product> iterator() {
    return products.iterator();
  }

  public void isSuppliedBy(Supplier supplier) {
    products.addAll(supplier.getProducts());
  }

}
