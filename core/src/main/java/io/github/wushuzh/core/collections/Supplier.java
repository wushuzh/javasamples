package io.github.wushuzh.core.collections;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

public @ToString class Supplier {

  @Getter
  private final String name;
  @Getter
  private final List<Product> products = new ArrayList<>();

  public Supplier(String name) {
    this.name = name;
  }
}
