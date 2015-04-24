package edu.brown.cs.cookups.food;
/**
 * Interface used to convert food weights
 * in ounces to more digestible amounts.
 * Uses default static methods.
 * @author wh7
 *
 */
public interface Conversion {
  /**
   * Convert ounces to teaspoons.
   * @param ounces weight.
   * @return double of teaspoons.
   */
  public static double teaspoons(double ounces) {
    return ounces * 6;
  }

  /**
   * Convert ounces to tablespoons.
   * @param ounces weight
   * @return double of tablespoons
   */
  public static double tablespoons(double ounces) {
    return ounces * 2;
  }

  /**
   * Convert ounces to cups.
   * @param ounces weight
   * @return double of cups
   */
  public static double cups(double ounces) {
    return ounces * .125;
  }
}
