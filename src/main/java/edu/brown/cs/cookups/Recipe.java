package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
  private String id;
  private String name;
  private DBLink querier;
  private List<Ingredient> ingredients, toBuy;

  public Recipe(String i, DBLink q) {
    id = i;
    querier = q;
    toBuy = new ArrayList<>();
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

  public List<Ingredient> ingredients() throws SQLException {
    if (ingredients == null) {
      ingredients = querier.getIngredientsByRecipe(id);
    }
    return ingredients;
  }

  public void addToShoppingList(Ingredient ing,
      double oz) {
    toBuy.add(new Ingredient(ing.id(), oz, querier));
  }

  public void setName(String n) {
  }

  public Recipe scale(double partySize) {
    Recipe toReturn = this;
    List<Ingredient> ingreds = ingredients;
    List<Ingredient> scaledIngredients = new ArrayList<>();
    for (Ingredient ing : ingreds) {
     scaledIngredients.add(new Ingredient(ing.id(),
         ing.ounces() * partySize, querier));
    }
    toReturn.setIngredients(scaledIngredients);
    return toReturn;
  }

  private void setIngredients(List<Ingredient> scaledIngredients) {
    ingredients = scaledIngredients;
  }
}
