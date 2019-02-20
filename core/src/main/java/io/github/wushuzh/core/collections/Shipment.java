package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Shipment implements Iterable<Product> {

  private static final int PRODUCT_NOT_PRESENT = -1;
  private static final int LIGHT_VAN_MAX_WEIGHT = 20;
  private final List<Product> products = new ArrayList<>();
  private List<Product> lightVanProducts;
  private List<Product> heavyVanProducts;

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

  public void prepare() {
    // An algo is only reasonable for demo methods of list, never use it in prodution
    // sort out list of products by weight
    products.sort(Product.BY_WEIGHT);

    // find the product index that needs the heavy van
    int splitPoint = findSplitPoint();

    // assign view of the product list for heavy and light vans
    lightVanProducts = products.subList(0, splitPoint);
    heavyVanProducts = products.subList(splitPoint, products.size());
  }

  private int findSplitPoint() {
    for (int i = 0; i < products.size(); i++) {
      final Product product = products.get(i);
      if (product.getWeight() > LIGHT_VAN_MAX_WEIGHT) {
        return i;
      }
    }
    return 0;
  }

  public List<Product> getLightVanProducts() {
    return lightVanProducts;
  }

  public List<Product> getHeavyVanProducts() {
    return heavyVanProducts;
  }
}
