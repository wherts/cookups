package edu.brown.cs.cookups.person;

import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;

public interface Person {
  public String name();

  public String id();

  public List<Ingredient> ingredients();

  public Double latitude();

  public Double longitude();

  public List<Recipe> favoriteRecipes();
}
