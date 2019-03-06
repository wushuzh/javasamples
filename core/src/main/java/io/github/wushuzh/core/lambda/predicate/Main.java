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
  }
}
