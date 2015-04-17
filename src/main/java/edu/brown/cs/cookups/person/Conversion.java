package edu.brown.cs.cookups.person;

public class Conversion {
  public static double teaspoons(double ounces) {
    return ounces * 6;
  }
  
  public static double tablespoons(double ounces) {
    return ounces * 2;
  }
  
  public static double cups(double ounces) {
    return ounces * .125;
  }
}
