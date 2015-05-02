package edu.brown.cs.cookups;

import java.util.Comparator;

import edu.brown.cs.cookups.food.Recipe;
/**
 * Class implements Comparator for
 * ranking recipes based on completion
 * percentage.
 * @author wh7
 *
 */
public class PercentageRanker implements Comparator<Recipe> {
  /**
   * Method for comparing two recipes
   * based on their percentage
   */
  @Override
  public int compare(Recipe o1, Recipe o2) {
    double percent1 = o1.percentNeed();
    double percent2 = o2.percentNeed();
    return Double.compare(percent2, percent1);
  }
}
