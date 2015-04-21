package edu.brown.cs.cookups.db;

public interface DBManager {

  public UserDB users();

  public IngredientDB ingredients();

  public RecipeDB recipes();

  public void clearCache();

}