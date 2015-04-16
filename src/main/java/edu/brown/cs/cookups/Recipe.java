package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
  private String id;
  private String name;
  private DBLink querier;
  private List<Ingredient> ingredients;

  public Recipe(String i, DBLink q) {
    id = i;
    querier = q;
  }

  public String id() {
    return id;
  }

  public String name() throws SQLException {
    if (name == null) {
      name = querier.getIngredientNameByID(id);
    }
    return name;
  }

  public List<Ingredient> ingredients() {
    return ingredients;
  }

  public void addToShoppingList(Ingredient ing,
      double oz) {
    // toBuy.add(e)
  }

  public void setIngredients(
      List<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  public void setName(String n) {
  }

  public Recipe scale(double partySize) {
    Recipe toReturn = this;
    List<Ingredient> ingreds = ingredients;
    List<Ingredient> scaledIngredients = new ArrayList<>();
    for (Ingredient ing : ingreds) {
      // scaledIngredients.add(new RecipeIngredientProxy(ing.ounces() *
      // partySize));
    }
    toReturn.setIngredients(scaledIngredients);
    return toReturn;
  }
}
