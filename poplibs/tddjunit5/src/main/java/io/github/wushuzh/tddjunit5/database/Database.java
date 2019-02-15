package io.github.wushuzh.tddjunit5.database;

import java.util.HashMap;
import java.util.Map;

public class Database {
  private Map<String, String> registersUsers = new HashMap<>();


  public boolean login(Credentials credentials) {
    String username = credentials.getUsername();
    String password = credentials.getPassword();

    return registersUsers.keySet().contains(username)
        && registersUsers.get(username).equals(password);
  }

}
