package io.github.wushuzh.tddjunit5.airport;

import java.util.Objects;

public class Passenger {
  private String name;
  private boolean vip;

  public Passenger(String name, boolean vip) {
    this.name = name;
    this.vip = vip;
  }

  public String getName() {
    return name;
  }

  public boolean isVip() {
    return vip;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Passenger other = (Passenger) obj;
    return Objects.equals(name, other.name);
  }

}
