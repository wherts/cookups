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
/**
 * Interface for recipe matching
 * functionality.
 * @author wh7
 *
 */
public interface RecipeMatcher {

  /**
   * Method for matching recipes to a
   * group of people
   * @param chefs people to match for
   * @param dbM database query class
   * @return list of recipes that
   * these people can cook based
   * on what they have
   * @throws SQLException if ingredient
   * is not the database
   */
  static List<Recipe> matchRecipes(
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

  /**
   * Method for compiling the ingredients
   * a list of people have into a map.
   * @param chefs people
   * @param map to aggregate ingredients to
   */
  static void compileIngredients(List<Person> chefs,
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

  /**
   * Private method helps match recipes
   * based on ingredients had.
   * @param currIngredients ingredients had
   * @param partySize number of people cooking
   * @param dbL connection to database for queries
   * @return list of recipes that can be made
   * from existing ingredients
   * @throws SQLException if ingredient is not
   * in the database
   */
  static List<Recipe> matchHelper(
      Map<String, Double> currIngredients,
      double partySize, DBManager dbL) throws SQLException {
    List<Recipe> myRecipes = new ArrayList<>();
    Set<String> keys = currIngredients.keySet();
    Set<Recipe> recipesUsing = new HashSet<>();
    for (String k : keys) {
      recipesUsing.addAll(dbL.recipes()
                             .getRecipesWithIngredient(k));
    }
    for (Recipe recipe : recipesUsing) {
      Recipe scaled = recipe.scale(partySize);
//      System.out.printf("Scaled recipe %s to %f%n", recipe.name(), partySize);
      // update the shopping list
      buildShoppingList(currIngredients, scaled);
      myRecipes.add(scaled);
    }
    return myRecipes;
  }

  /**
   * Method for building a shopping list
   * based on ingredients had.
   * @param currIngredients that you have
   * @param recipe you are shopping for
   */
  static void buildShoppingList(
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
      e.printStackTrace();
    }
  }
}
