package io.github.wushuzh.tddjunit5.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.github.wushuzh.tddjunit5.airport.Flight;

public class Database {
  private Map<String, String> registersUsers = new HashMap<>();
  List<List<String>> queriedData = new ArrayList<>();

  public boolean login(Credentials credentials) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    return registersUsers.keySet().contains(username)
        && registersUsers.get(username).equals(password);
  }

  public List<List<String>> queryFlightsDatabase() {
    return queriedData;
  }

  public double averageDistance(List<Flight> flightsList) {
    return flightsList.stream().mapToDouble(Flight::getDistance).average().getAsDouble();
  }

  public int minimumDistance(List<Flight> flightsList) {
    return flightsList.stream().mapToInt(Flight::getDistance).min().getAsInt();
  }

  public int maximumDistanct(List<Flight> flightsList) {
    return flightsList.stream().mapToInt(Flight::getDistance).max().getAsInt();
  }
}
