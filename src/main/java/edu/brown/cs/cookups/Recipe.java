package edu.brown.cs.cookups;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
  private String id;
  private String name;
  private List<RecipeIngredientProxy> ingredients;
  private List<RecipeIngredientProxy> toBuy;
  
  public Recipe(String id, String name) {
    this.id = id;
    this.name = name;
  }
  
	public String id() {
    return id;
  }
	public String name() {
    return name;
  }
  public List<RecipeIngredientProxy> ingredients() {
    return ingredients;
  }
  public List<RecipeIngredientProxy> toBuy() {
    return toBuy;
  }
  public void addToShoppingList(RecipeIngredientProxy ing, double oz) {
//    toBuy.add(e)
  }
	public void setIngredients(List<RecipeIngredientProxy> ingredients) {
	  this.ingredients = ingredients;
  }
	public void setName(String n) {
  }
  public Recipe scale(int partySize) {
    Recipe toReturn = this;
    List<RecipeIngredientProxy> ingreds = ingredients;
    List<RecipeIngredientProxy> scaledIngredients = new ArrayList<>();
    for (RecipeIngredientProxy ing : ingreds) {
//      scaledIngredients.add(new RecipeIngredientProxy(ing.ounces() * partySize));
    }
    toReturn.setIngredients(scaledIngredients);
    return toReturn;
  }
}
