package edu.brown.cs.cookups;

import java.util.Comparator;

import edu.brown.cs.cookups.food.Recipe;

public class PercentageRanker implements Comparator<Recipe> {
  @Override
  public int compare(Recipe o1, Recipe o2) {
    double percent1 = o1.percentHave();
    double percent2 = o2.percentHave();
    if (percent1 > percent2) {
      return -1;
    } else if (percent1 < percent2) {
      return 1;
    }
    return 0;
  }
}
