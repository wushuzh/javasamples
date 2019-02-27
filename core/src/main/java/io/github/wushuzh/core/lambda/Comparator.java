package io.github.wushuzh.core.lambda;

import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {
  public int compare(T t1, T t2);

  public static Comparator<Person> comparing(Function<Person, Comparable> f) {
    return (p1, p2) -> f.apply(p2).compareTo(f.apply(p1));
  }
}
