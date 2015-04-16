package edu.brown.cs.cookups;

import java.util.List;

public interface Person {
  public String name();

  public String id();

  public List<Ingredient> ingredients();

  public Double latitude();

  public Double longitude();

  public List<Recipe> favoriteRecipes();
}
