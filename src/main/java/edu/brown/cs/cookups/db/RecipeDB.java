package edu.brown.cs.cookups.db;

import java.util.List;
import java.util.Set;

import edu.brown.cs.cookups.food.Recipe;

public interface RecipeDB {
  public void addRecipe(String name, String id, String text);

  public void addRecipeIngredient(String recipe, String id,
      float qty);

  public boolean hasRecipe(String id);

  public Recipe getRecipeById(String id);

  public void removeRecipe(String id);

  public Set<Recipe> getRecipesWithIngredient(String id);

  public String getInstructionsByRecipe(String id);

  public String getRecipeNameByID(String id);

  public List<String> getAllRecipeNames();

  public Recipe cacheRecipe(String id);

  public void clearCache();
}
