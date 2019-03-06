package io.github.wushuzh.core.lambda.predicate;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Predicate<String> p = s -> s.length() < 20;

        boolean b = p.test("Hello");
        System.out.println("Hello is shorter than 20 charts: " + b);
    }
}