package io.github.wushuzh.core.collections;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class ProductCatalogue implements Iterable<Product> {

  private final Set<Product> products = new TreeSet<>(Product.BY_NAME);

  @Override
  public Iterator<Product> iterator() {
    return products.iterator();
  }

  public void isSuppliedBy(Supplier supplier) {
    products.addAll(supplier.getProducts());
  }

}
