package io.github.wushuzh.core.lambda.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainBuildingStreams {
  public static void main(String[] args) {
    List<Integer> ints = Arrays.asList(0, 1, 2, 3, 4);
    Stream<Integer> stream1 = ints.stream();
    stream1.forEach(System.out::println);
    // 0
    // 1
    // 2
    // 3
    // 4

    Stream<Integer> stream2 = Stream.of(5, 6, 7, 8);
    stream2.forEach(System.out::println);
    // 5
    // 6
    // 7
    // 8

    Stream<String> streamOfStrings1 = Stream.generate(() -> "one");
    streamOfStrings1.limit(5).forEach(System.out::println);
    // one
    // one
    // one
    // one
    // one

    Stream<String> streamOfStrings2 = Stream.iterate("+", s -> s + "+");
    streamOfStrings2.limit(5).forEach(System.out::println);
    // +
    // ++
    // +++
    // ++++
    // +++++

    IntStream streamOfInt = ThreadLocalRandom.current().ints();
    streamOfInt.limit(5).forEach(System.out::println);
    // 1780834179
    // -1773171188
    // 220212821
    // -1560773322
    // -1187310538
  }
}
