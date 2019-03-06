package io.github.wushuzh.core.lambda.predicate;

/**
 * Main
 */
public class Main {

  public static void main(String[] args) {
    Predicate<String> p1 = s -> s.length() < 20;
    Predicate<String> p2 = s -> s.length() > 5;

    Predicate<String> p3 = p1.and(p2);

    System.out.println("p3 for Yes: " + p3.test("Yes"));
    System.out.println("p3 for Good morning: " + p3.test("Good morning"));
    System.out.println("p3 for Good morning gentlemen: " + p3.test("Good morning gentlemen"));
    // p3 for Yes: false
    // p3 for Good morning: true
    // p3 for Good morning gentlemen: false

    Predicate<String> p4 = p1.or(p2);

    System.out.println("p4 for Yes: " + p4.test("Yes"));
    System.out.println("p4 for Good morning: " + p4.test("Good morning"));
    System.out.println("p4 for Good morning gentlemen: " + p4.test("Good morning gentlemen"));
    // p4 for Yes: true
    // p4 for Good morning: true
    // p4 for Good morning gentlemen: true

    Predicate<String> p5 = Predicate.isEqualsTo("Yes");

    System.out.println("p5 for Yes: " + p5.test("Yes"));
    System.out.println("p5 for No: " + p5.test("No"));
    // p5 for Yes: true
    // p5 for No: false

    Predicate<Integer> p6 = Predicate.isEqualsTo(42);

    System.out.println("p6 for 42: " + p6.test(42));
    System.out.println("p6 for 1: " + p6.test(1));
    // p6 for 42: true
    // p6 for 1: false
  }
}
