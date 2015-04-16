package edu.brown.cs.cookups;

import java.util.List;

import edu.brown.cs.cookups.Ingredient;
import edu.brown.cs.cookups.Person;
import edu.brown.cs.cookups.Recipe;

public class User implements Person {

  private String name;
  private String id;
  private List<Ingredient> ingredients;

  public User(String name, String id,
      List<Ingredient> ingredients2) {
    this.name = name;
    this.id = id;
    this.ingredients = ingredients2;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String id() {
    return this.id;
  }

  @Override
  public List<Ingredient> ingredients() {
    return ingredients;
  }

  @Override
  public Double latitude() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double longitude() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Recipe> favoriteRecipes() {
    // TODO Auto-generated method stub
    return null;
  }

}
