package io.github.wushuzh.core.lambda.collectionapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainMapAPI {
  public static void main(String[] args) {
    Person p1 = new Person("Alice", 23);
    Person p2 = new Person("Brian", 56);
    Person p3 = new Person("Chelsea", 46);
    Person p4 = new Person("David", 28);
    Person p5 = new Person("Erica", 37);
    Person p6 = new Person("Francisco", 18);

    City newYork = new City("New York");
    City shangHai = new City("Shanghai");
    City paris = new City("Paris");

    Map<City, List<Person>> map = new HashMap<>();

    System.out.println("People from Paris: " + map.get(paris));
    System.out.println("People from Paris: " + map.getOrDefault(paris, Collections.EMPTY_LIST));
    // People from Paris: null
    // People from Paris: []

    map.putIfAbsent(paris, new ArrayList<>());
    map.get(paris).add(p1);
    System.out.println("People from Paris: " + map.getOrDefault(paris, Collections.EMPTY_LIST));

    map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p2);
    map.computeIfAbsent(newYork, city -> new ArrayList<>()).add(p3);
    System.out
        .println("People from New York: " + map.getOrDefault(newYork, Collections.EMPTY_LIST));
    // People from Paris: [Person(name=Alice, age=23)]
    // People from New York: [Person(name=Brian, age=56), Person(name=Chelsea, age=46)]

  }
}
