package io.github.wushuzh.core.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MutableHashMapKeys {
  public static void main(String[] args) {
    final Map<MutableString, String> brokenMap = new HashMap<>();

    final String value = "abc";

    final MutableString key = new MutableString(value);
    brokenMap.put(key, value);

    System.out.println(brokenMap);
    System.out.println(brokenMap.get(key));

    key.set("def");

    System.out.println("==== re-assign the mutable map key ===");
    System.out.println(brokenMap);
    System.out.println(brokenMap.get(key));

    // {abc=abc}
    // abc
    // ==== re-assign the mutable map key ===
    // {def=abc}
    // null

    // TODO: https://www.ibm.com/developerworks/library/j-jtp05273/#N10172
    // <<Hashing it out>> by Brian Goetz 2003-05-27

  }

  private static class MutableString {

    public int hashCode() {
      return value.hashCode();
    }

    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      MutableString other = (MutableString) obj;
      return value.equals(other.value);
    }

    public String toString() {
      return value;
    }

    private String value;

    public MutableString(final String value) {
      set(value);
    }

    public String get() {
      return value;
    }

    public void set(final String value) {
      Objects.requireNonNull(value);
      this.value = value;
    }
  }
}
