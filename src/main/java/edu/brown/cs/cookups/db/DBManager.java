package edu.brown.cs.cookups.db;

import java.io.File;

public interface DBManager {

  public UserDB users();

  public IngredientDB ingredients();

  public RecipeDB recipes();

  public MealDB meals();

  public void clearCache();

  public void importIngredients(File file);

  public void importAllRecipes(String path);

  public void importRecipeIngredients(File file);

  public void importAllUsers(String dir);

}