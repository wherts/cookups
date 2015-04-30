package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.Comparator;

import edu.brown.cs.cookups.food.Recipe;

public class PriceRanker implements Comparator<Recipe> {
  @Override
  public int compare(Recipe o1, Recipe o2) {
    double price1 = o1.shoppingPrice();
    double price2 = o2.shoppingPrice();
    if (price1 > price2) {
      return 1;
    } else if (price1 < price2) {
      return -1;
    }
    return 0;
  }

}
