package io.github.wushuzh.core.lambda.collectionapi;

import lombok.Data;

@Data
public class Person {
  private String name;
  private int age;

  public Person(final String name, final int age) {
    this.name = name;
    this.age = age;
  }
}
