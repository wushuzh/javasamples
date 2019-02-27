package io.github.wushuzh.core.lambda;

public class MainComparator {
  public static void main(String[] args) {
    Comparator<Person> cmpFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
    Comparator<Person> cmpLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());
    Comparator<Person> cmpAge = (p1, p2) -> p2.getAge() - p1.getAge();
  }
}
