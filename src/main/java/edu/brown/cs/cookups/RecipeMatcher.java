package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;

public class RecipeMatcher {

  private RecipeMatcher() {
  }

  public static List<Recipe> matchRecipes(
      List<Person> chefs, DBManager dbM) throws SQLException {
    assert (chefs != null);
    int size = chefs.size();
    // maps ingredient id to the amount in the group
    Map<String, Double> currIngredients = new HashMap<>();
    // 1. compile ingredients
    compileIngredients(chefs, currIngredients);
    // 2. search database for recipes
    return matchHelper(currIngredients, size, dbM);
  }

  public static void compileIngredients(List<Person> chefs,
      Map<String, Double> map) {
    for (Person p : chefs) {
      for (Ingredient ing : p.ingredients()) {
        if (map.containsKey(ing.id())) {
          map.replace(ing.id(), map.get(ing.id())
              + ing.ounces());
        } else {
          map.put(ing.id(), ing.ounces());
        }
      }
    }
  }

  private static List<Recipe> matchHelper(
      Map<String, Double> currIngredients,
      double partySize, DBManager dbL) throws SQLException {
    List<Recipe> myRecipes = new ArrayList<>();
    Set<String> keys = currIngredients.keySet();
    Set<Recipe> recipesUsing = new HashSet<>();
    for (String k : keys) {
      recipesUsing.addAll(dbL.recipes()
                             .getRecipesWithIngredient(k));
      // update the shopping list
    }
    for (Recipe recipe : recipesUsing) {
      recipe.scale(partySize);
      buildShoppingList(currIngredients, recipe);
      myRecipes.add(recipe);
    }
    return myRecipes;
  }

  public static void buildShoppingList(
      Map<String, Double> currIngredients, Recipe recipe) {
    try {
      for (Ingredient ing : recipe.ingredients()) {
        if (currIngredients.containsKey(ing.id())) {
          if (ing.ounces() > currIngredients.get(ing.id())) {
            recipe.addToShoppingList(ing, ing.ounces()
                - currIngredients.get(ing.id()));
          }
        } else {
          recipe.addToShoppingList(ing, ing.ounces());
        }
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
