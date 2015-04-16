package edu.brown.cs.cookups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecipeMatcher {
  private DBLink dbL;
  private int numPeople;

  public RecipeMatcher(DBLink d) {
    this.dbL = d;
  }

  public Set<Recipe> findRecipes(List<Person> chefs) {
    assert(chefs != null);
    numPeople = chefs.size();
    //maps ingredient id to the amount in the group
    Map<String, Double> currIngredients = new HashMap<>();
    //1. compile ingredients
    compileIngredients(chefs, currIngredients);
    //2. search database for recipes
    Set<Recipe> = 
    return new ArrayList<Recipe>();
  }

  public void compileIngredients(List<Person> chefs, Map<String, Double> map) {
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

  public Set<Recipe> matchRecipes(Map<String, Double> currIngredients,
                                   List<Recipe> recipes, int partySize) {
    Set<Recipe> myRecipes = new HashSet<>();
    Set<String> keys = currIngredients.keySet();
    for (String k : keys) {
      List<Recipe> recipesUsing = dbL.getRecipesThatUse(k);
      for (Recipe r : recipesUsing) {
        for (RecipeIngredientProxy rp : r.ingredients()) {
          if (currIngredients.containsKey(rp.id())) {
            //have some of recipe amount
            double 
          } else {
            //need all of recipe amount
            
          }
        }
      }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    for (Recipe recipe : recipes) {
      for (RecipeIngredientProxy ing : recipe.ingredients()) {
        if (currIngredients.containsKey(ing.id())) {
          if (ing.ounces() > currIngredients.get(ing.id())) {
            recipe.addToShoppingList(ing, ing.ounces() - currIngredients.get(ing.id()));
          }
        } else {
          recipe.addToShoppingList(ing, ing.ounces());
        }
        myRecipes.add(recipe.scale(partySize));
      }
    }
    return myRecipes;
  }
}
