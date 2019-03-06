package io.github.wushuzh.core.lambda.predicate;

/**
 * Predicate
 */
@FunctionalInterface
public interface Predicate<T> {
    public boolean test(T t);
}