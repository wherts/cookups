package edu.brown.cs.cookups;

import java.util.Comparator;

import edu.brown.cs.cookups.food.Recipe;

public class TotalPriceRanker implements Comparator<Recipe> {
  @Override
  public int compare(Recipe o1, Recipe o2) {
    double price1 = o1.totalPrice();
    double price2 = o2.totalPrice();
    return Double.compare(price1, price2);
  }
}
