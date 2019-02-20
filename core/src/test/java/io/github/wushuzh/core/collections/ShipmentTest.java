package io.github.wushuzh.core.collections;

import static io.github.wushuzh.core.collections.ProductFixtures.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import org.junit.jupiter.api.Test;

public class ShipmentTest {
  private Shipment shipment = new Shipment();

  @Test
  public void shouldAddItems() {
    shipment.add(door);
    shipment.add(window);

    assertThat(shipment, contains(door, window));
  }

  @Test
  public void shouldReplaceItem() {
    shipment.add(door);
    shipment.add(floorPanel);

    shipment.replace(floorPanel, window);

    assertThat(shipment, contains(door, window));
  }

  @Test
  public void shouldNotReplaceMissingItem() {
    shipment.add(door);

    shipment.replace(window, floorPanel);

    assertThat(shipment, contains(door));
  }
}
