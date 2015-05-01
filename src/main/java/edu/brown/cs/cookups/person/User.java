package edu.brown.cs.cookups.person;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;

public class User implements Person {
  private String name;
  private String id;
  private List<Ingredient> ingredients;
  private List<String> favoriteCuisines;

  public User() {

  }

  public User(String name, String id,
      List<Ingredient> ingredients2) {
    this.name = name;
    this.id = id;
    this.ingredients = ingredients2;
    this.favoriteCuisines = new ArrayList<>();
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
  public List<String> favoriteCuisines() {
    return this.favoriteCuisines;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof User)) {
      return false;
    }
    User u = (User) o;
    return this.id.equals(u.id());
  }

  @Override
  public void addCuisine(String cuisine) {
    this.favoriteCuisines.add(cuisine);
  }

  @Override
  public String url() {
    String link = "/profile/" + id.split("@")[0];
    return link;
  }

}
