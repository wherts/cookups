package edu.brown.cs.cookups.db;

import java.io.File;

public interface DBManager {

  public UserDB users();

  public IngredientDB ingredients();

  public RecipeDB recipes();

  public void clearCache();

  public void importIngredients(File file);

  public void importRecipeIngredients(File file);

  public void importRecipes(File file);

}