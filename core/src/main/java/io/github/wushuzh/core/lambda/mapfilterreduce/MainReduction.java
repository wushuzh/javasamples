package io.github.wushuzh.core.lambda.mapfilterreduce;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class MainReduction {
  public static void main(String[] args) {
    List<Integer> ints = Arrays.asList(-4, -3, -2, -1);

    List<BinaryOperator<Integer>> ops =
        Arrays.asList((i1, i2) -> i1 + i2, (i1, i2) -> i1 * i1 + i2 * i2,
            (i1, i2) -> Integer.max(i1, i2));

    for (BinaryOperator<Integer> op : ops) {
      int reduction = reduce(ints, 0, op);
      System.out.println("Reduction: " + reduction);

      // simulate parallel computation as Java8 does
      List<Integer> ints1 = Arrays.asList(-1, -2);
      List<Integer> ints2 = Arrays.asList(-3, -4);
      int reduction2 = reduce(Arrays.asList(reduce(ints1, 0, op), reduce(ints2, 0, op)), 0, op);
      System.out.println("Reduction parallel evenly: " + reduction2);

      // simulate not even distribute parallel computation
      List<Integer> ints3 = Arrays.asList(-1, -2, -3);
      List<Integer> ints4 = Arrays.asList(-4);
      int reduction3 = reduce(Arrays.asList(reduce(ints3, 0, op), reduce(ints4, 0, op)), 0, op);
      System.out.println("Reduction parallel unevenly: " + reduction3);
    }
    // Reduction: -10
    // Reduction parallel evenly: -10
    // Reduction parallel unevenly: -10

    // Reduction: 637145146
    // Reduction parallel evenly: 10034
    // Reduction parallel unevenly: 1336592

    // Reduction: 0
    // Reduction parallel evenly: 0
    // Reduction parallel unevenly: 0
  }

  public static int reduce(List<Integer> values, int valueIfEmpty,
      BinaryOperator<Integer> reduction) {
    int result = valueIfEmpty;
    for (int value : values) {
      result = reduction.apply(result, value);
    }
    return result;
  }
}
