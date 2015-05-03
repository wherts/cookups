package edu.brown.cs.cookups.person;

import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;

public interface Person {
  public String name();

  public String id();

  public String url();

  public List<Ingredient> ingredients();

  public Double latitude();

  public Double longitude();

  public List<String> favoriteCuisines();

  public void setCuisines(List<String> cuisines);

  public void setIngredients(List<Ingredient> ingredients);

}
