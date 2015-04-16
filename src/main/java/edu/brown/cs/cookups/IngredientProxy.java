package edu.brown.cs.cookups;

import java.util.List;

public interface IngredientProxy {

  public String name();


  public String id();

  public List<Recipe> recipes();

  public void fill();

  public Ingredient value();

  public Double ounces(); // amount in ounces

  public Double teaspoons(); // amount in teaspoons

  public Double expiration(); // seconds until expiration
}

