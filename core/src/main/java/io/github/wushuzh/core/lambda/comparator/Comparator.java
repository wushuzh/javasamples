package io.github.wushuzh.core.lambda.comparator;

import java.util.function.Function;

@FunctionalInterface
public interface Comparator<T> {
  public int compare(T t1, T t2);

  // TODO: read more on
  // https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
  public default Comparator<T> thenComparing(Comparator<T> fallbackCmp) {
    return (p1, p2) -> compare(p1, p2) == 0 ? fallbackCmp.compare(p1, p2) : compare(p1, p2);
  }

  public default Comparator<T> thenComparing(Function<T, Comparable> f) {
    return thenComparing(comparing(f));
  }

  public static <U> Comparator<U> comparing(Function<U, Comparable> f) {
    return (p1, p2) -> f.apply(p2).compareTo(f.apply(p1));
  }
}
