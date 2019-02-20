package io.github.wushuzh.core.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.github.wushuzh.core.collections.ProductFixtures.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductCatalogueTest {
  private ProductCatalogue catelogue;

  @BeforeEach
  public void setUp() {
    catelogue = new ProductCatalogue();
    catelogue.isSuppliedBy(bobs);
    catelogue.isSuppliedBy(kates);
  }

  @Test
  public void shouldOnlyHoldUniqueProducts() {
    assertThat(catelogue, containsInAnyOrder(door, floorPanel, window));
  }

  @Test
  public void shouldFindLightVanProducts() {
    assertThat(catelogue.lightVanProducts(), containsInAnyOrder(window));
  }

  @Test
  public void shouldFindHeavyVanProducts() {
    assertThat(catelogue.heavyVanProducts(), containsInAnyOrder(door, floorPanel));
  }
}
