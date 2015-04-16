package edu.brown.cs.cookups;

import java.util.Date;
import java.util.List;

public class Ingredient {
  private double ounces;
  private String id, name;
  private Date dateCreated;
  private DBLink querier;
  private List<Recipe> recipes;
  
  public Ingredient(String i, double oz, DBLink db) {
    id = i;
    ounces = oz;
    querier = db;
    dateCreated = new Date();
  }
  
  public String id() {
    return id;
  }
  
  public String name() {
    if (name == null) {
      name = querier.ingredientNameCache(id);
    }
    return name;
  }
  
	public double ounces() { //amount in ounces
	  return ounces;
	}
	
	public double teaspoons() { //amount in teaspoons
	  return Converter.teaspoons(ounces);
	}
	
  public double tablespoons() { //amount in tablespoons
    return Converter.tablespoons(ounces);
  }
  
  public double cups() { //amount in cups
    return Converter.cups(ounces);
  }
  
	public double elapsed() { //seconds until expiration
	  Date curr = new Date();
	  return curr.getTime() - dateCreated.getTime();
	}

  public List<Recipe> recipes() {
    if (recipes == null) {
      recipes = querier.getRecipesByIngredient(id);
    }
    return recipes;
  }
}