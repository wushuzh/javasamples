package io.github.wushuzh.core.collections;

public interface ProductLookupTable {
  Product lookupById(int id);

  void addProduct(Product productToAdd);

  void clear();
}
