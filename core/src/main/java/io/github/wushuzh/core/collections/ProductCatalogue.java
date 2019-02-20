package io.github.wushuzh.core.collections;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class ProductCatalogue implements Iterable<Product> {

  private final SortedSet<Product> products = new TreeSet<>(Product.BY_WEIGHT);

  @Override
  public Iterator<Product> iterator() {
    return products.iterator();
  }

  public void isSuppliedBy(Supplier supplier) {
    products.addAll(supplier.getProducts());
  }

  public Set<Product> lightVanProducts() {
    Product heaviestLightVanProduct = findHeaviestLightVanProduct();
    return products.headSet(heaviestLightVanProduct);
  }

  private Product findHeaviestLightVanProduct() {
    for (Product product : products) {
      if (product.getWeight() > 20) {
        return product;
      }
    }
    return products.last();
  }

  public Set<Product> heavyVanProducts() {
    Product heaviestLightVanProduct = findHeaviestLightVanProduct();
    return products.tailSet(heaviestLightVanProduct);
  }

}
