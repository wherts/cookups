package edu.brown.cs.cookups.db;

import java.util.List;
import java.util.Set;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public interface DBManager {

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

  public boolean hasPersonByName(String name);

  public List<Person> getPersonsByName(String name,
      PersonManager people);

  public String getIngredientNameByID(String id);

  public String getIngredientIDByName(String name);

  public List<String> getAllIngredientNames();

  public Set<Recipe> getRecipesWithIngredient(String id);

  public String getInstructionsByRecipe(String id);

  public List<Ingredient> getIngredientsByRecipe(String id);

  public String getRecipeNameByID(String id);

}