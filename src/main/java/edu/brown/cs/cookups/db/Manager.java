package edu.brown.cs.cookups.db;

import java.util.List;

import edu.brown.cs.cookups.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public interface Manager {

  public User getProfileById(String id);

  public void addRecipe(String name, String id, String text);

  public void addRecipeIngredient(String recipe, String id,
      float qty);

  public boolean hasRecipe(String id);

  public Recipe getRecipeById(String id);

  public void removeRecipe(String id);

  public void addUserIngredient(String id, Ingredient i);

  public void addPerson(Person p);

  public Person getPersonById(String id);

  public List<Ingredient> getPersonIngredients(String id);

  public void removePersonById(String id);

  public boolean hasPersonByID(String id);

  public List<Person> getPersonsByName(String name);

  public String getIngredientNameByID(String id);

  public String getIngredientIDbyName(String name);

  public List<String> getAllIngredientNames();

  public List<Recipe> getRecipesWithIngredient(String id);

  public List<Ingredient> getIngredientsByRecipe(String id);

  public String getRecipeNameByID(String id);

}