package io.github.wushuzh.core.lambda.collectionapi;

import lombok.Data;

@Data
public class City {
  private String name;

  public City(final String name) {
    this.name = name;
  }
}
