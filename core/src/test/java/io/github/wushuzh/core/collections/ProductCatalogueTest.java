package io.github.wushuzh.core.collections;

import org.junit.jupiter.api.Test;
import static io.github.wushuzh.core.collections.ProductFixtures.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProductCatalogueTest {
  @Test
  public void shouldOnlyHoldUniqueProducts() {
    ProductCatalogue catalogue = new ProductCatalogue();

    catalogue.isSuppliedBy(bobs);
    catalogue.isSuppliedBy(kates);

    assertThat(catalogue, containsInAnyOrder(door, floorPanel));
  }
}
