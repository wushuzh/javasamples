package io.github.wushuzh.core.collections;

import java.util.HashMap;
import java.util.Map;

public class MapProductLookupTable implements ProductLookupTable {

  private final Map<Integer, Product> idToProduct = new HashMap<>();

  @Override
  public Product lookupById(int id) {
    return idToProduct.get(id);
  }

  @Override
  public void addProduct(Product productToAdd) {
    final int id = productToAdd.getId();
    if (idToProduct.containsKey(id)) {
      throw new IllegalArgumentException("Unable to add product, duplicate id for " + productToAdd);
    }
    idToProduct.put(id, productToAdd);
  }

  @Override
  public void clear() {
    idToProduct.clear();
  }

}
