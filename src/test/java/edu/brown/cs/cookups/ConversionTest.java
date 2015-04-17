package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.brown.cs.cookups.food.Conversion;

public class ConversionTest {

  @Test
  public void convertTest() {
    assertTrue(Conversion.teaspoons(1) == 6);
    assertTrue(Conversion.tablespoons(1) == 2);
    assertTrue(Conversion.cups(1000) == 125);
  }
}
