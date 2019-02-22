package io.github.wushuzh.core.collections;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class CalculatorTest {
  private Calculator calc = new Calculator();

  @Test
  void shouldEvaluateconstants() {
    int result = calc.evalute("1");
    assertEquals(1, result);
  }

  @Test
  void shouldSupportAddtion() {
    int result = calc.evalute("1 + 2");
    assertEquals(3, result);
  }

  @Test
  void shouldSupportSubtraction() {
    int result = calc.evalute("1 - 2");
    assertEquals(-1, result);
  }

  @Test
  void shouldSupportComplexStatements() {
    int result = calc.evalute("1 - 3 + 2 + 4");
    assertEquals(4, result);
  }
}
