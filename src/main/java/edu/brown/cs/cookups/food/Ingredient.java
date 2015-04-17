package edu.brown.cs.cookups.food;

import java.sql.SQLException;
import java.util.Date;
import java.util.Set;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.person.Conversion;

public class Ingredient {
  private double ounces;
  private String id, name;
  private Date dateCreated;
  private DBLink querier;
  private Set<Recipe> recipes;

  public Ingredient(String i, double oz, DBLink dbLink) {
    id = i;
    ounces = oz;
    querier = dbLink;
    dateCreated = new Date();
  }

  public String id() {
    return id;
  }

  public String name() throws SQLException {
    if (name == null) {
      name = querier.ingredientNameCache(id);
    }
    return name;
  }

  public double ounces() { // amount in ounces
    return ounces;
  }

  public double teaspoons() { // amount in teaspoons
    return Conversion.teaspoons(ounces);
  }

  public double tablespoons() { // amount in tablespoons
    return Conversion.tablespoons(ounces);
  }

  public double cups() { // amount in cups
    return Conversion.cups(ounces);
  }

  public double elapsed() { // seconds until expiration
    Date curr = new Date();
    return curr.getTime() - dateCreated.getTime();
  }

  public Set<Recipe> recipes() throws SQLException {
    if (recipes == null) {
      recipes = querier.getRecipesWithIngredient(id);
    }
    return recipes;
  }

  // MIGHT FAIL WITH Foating Point error from DB
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Ingredient)) {
      return false;
    }

    Ingredient i = (Ingredient) o;
    return (this.id.equals(i.id()) && this.ounces() == i.ounces);
  }
}