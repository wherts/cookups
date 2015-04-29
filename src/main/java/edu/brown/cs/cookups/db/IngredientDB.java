package edu.brown.cs.cookups.db;

import java.util.List;

import edu.brown.cs.cookups.food.Ingredient;

public interface IngredientDB {

  public List<Ingredient> getIngredientsByRecipe(String id);

  public List<String> getAllIngredientNames();

  public Boolean hasIngredient(String id);

  public String getIngredientNameByID(String id);

  public String getIngredientIDByName(String name);

  public double priceByID(String id);

  public String storageByID(String id);

  public void defineIngredient(String id, String name,
      double price, String storage);

  public String ingredientNameCache(String id);

  public void clearCache();

}
