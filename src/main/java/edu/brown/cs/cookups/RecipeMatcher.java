package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeMatcher {

  public RecipeMatcher() {
  }

  public static Set<Recipe> findRecipes(List<Person> chefs, DBLink dbL) throws SQLException {
    assert(chefs != null);
    //maps ingredient id to the amount in the group
    Map<String, Double> currIngredients = new HashMap<>();
    //1. compile ingredients
    compileIngredients(chefs, currIngredients);
    //2. search database for recipes
    return matchRecipes(currIngredients, chefs.size(), dbL);
  }

  public static void compileIngredients(List<Person> chefs, Map<String, Double> map) {
    for (Person p : chefs) {
      for (Ingredient ing : p.ingredients()) {
        if (map.containsKey(ing.id())) {
          map.replace(ing.id(), map.get(ing.id()) + ing.ounces());
        } else {
          map.put(ing.id(), ing.ounces());
        }
      }
    }
  }

  //helper method
  public static Set<Recipe> matchRecipes(
                                  Map<String, Double> currIngredients,
                                  double partySize,
                                  DBLink dbL) throws SQLException {
    Set<Recipe> myRecipes = new HashSet<>();
    Set<String> keys = currIngredients.keySet();
    for (String k : keys) {
      List<Recipe> recipesUsing = dbL.getRecipesWithIngredient(k);
      //update the shopping list
      for (Recipe recipe : recipesUsing) {
        recipe.scale(partySize);
        buildShoppingList(currIngredients, recipe);
      }
    }
    return myRecipes;
  }

  public static void buildShoppingList(Map<String, Double> currIngredients,
      Recipe recipe) {
    for (Ingredient ing : recipe.ingredients()) {
      if (currIngredients.containsKey(ing.id())) {
        if (ing.ounces() > currIngredients.get(ing.id())) {
          recipe.addToShoppingList(ing, ing.ounces() - currIngredients.get(ing.id()));
        }
      } else {
        recipe.addToShoppingList(ing, ing.ounces());
      }
    }
  }  
}
