package edu.brown.cs.cookups;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {
  private Manager dbM;
  private int numPeople;

  public RecipeMatcher(Manager dbM) {
    this.dbM = dbM;
  }

  public List<Recipe> findRecipes(List<Person> chefs) {
    assert(chefs != null);
    numPeople = chefs.size();
    Map<String, Double> currIngredients = new HashMap<>();
    //1. compile ingredients
    compileIngredients(chefs, currIngredients);
    //2. search database for recipes
    return new ArrayList<Recipe>();
  }

  public void compileIngredients(List<Person> chefs, Map<String, Double> map) {
    for (Person p : chefs) {
      for (IngredientProxy ing : p.ingredients()) {
        if (map.containsKey(ing.id())) {
          map.replace(ing.id(), map.get(ing.id()) + ing.ounces());
        } else {
          map.put(ing.id(), ing.ounces());
        }
      }
    }
  }

  public List<Recipe> matchRecipes(Map<String, Double> map) {
    
    return null;
  }
}
