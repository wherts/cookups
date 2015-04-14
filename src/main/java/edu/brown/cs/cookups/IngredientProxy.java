package edu.brown.cs.cookups;

import java.util.List;

public interface IngredientProxy {
  public String name();
  String id();
  List<Recipe> recipes();
  void fill();
  double ounces(); //amount in ounces
  double teaspoons(); //amount in teaspoons
  double expiration(); //seconds until expiration
}
