package edu.brown.cs.cookups.food;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
/**
 * This class represents an ingredient.
 * @author wh7
 *
 */
public class Ingredient {
  private double ounces;
  private String id, name;
  private LocalDate dateCreated;
  private DBManager querier;
  private Set<Recipe> recipes;

  /**
   * Public constructor for an ingredient.
   * @param i id
   * @param oz weight
   * @param dbM way to query database for
   * more information about the ingredient
   */
  public Ingredient(String i, double oz, DBManager dbM) {
    id = i;
    ounces = oz;
    querier = dbM;
    dateCreated = LocalDateTime.now().toLocalDate();
  }

  /**
   * Accessor for ingredient id.
   * @return string id
   */
  public String id() {
    return id;
  }

  /**
   * Accessor for ingredient name.
   * @return string name
   * @throws SQLException if ingredient
   * id is not in database
   */
  public String name() throws SQLException {
    if (name == null) {
      name = querier.ingredients().ingredientNameCache(id);
    }
    return name;
  }

  /**
   * Accessor for amount in ounces.
   * @return double ounces
   */
  public double ounces() { // amount in ounces
    return ounces;
  }

  /**
   * Accessor for amount in teaspoons.
   * @return double teaspoons
   */
  public double teaspoons() { // amount in teaspoons
    return Conversion.teaspoons(ounces);
  }

  /**
   * Accessor for amount in tablespoons.
   * @return double tablespoons
   */
  public double tablespoons() { // amount in tablespoons
    return Conversion.tablespoons(ounces);
  }

  /**
   * Accessor for amount in cups.
   * @return double cups
   */
  public double cups() { // amount in cups
    return Conversion.cups(ounces);
  }

  /**
   * Accessor for time elapsed since the ingredient
   * was added.
   * @return double elapsed
   */
  public double elapsed() { // seconds until expiration
    LocalDate curr = LocalDateTime.now().toLocalDate();
    Period time = Period.between(curr, dateCreated);
    return (time.getYears() * 365) 
            + (time.getMonths() * 30) 
            + (time.getDays());
  }

  /**
   * Accessor for all the recipes this ingredient.
   * is used in.
   * @return set of recipes
   * @throws SQLException if ingredient does not
   * appear in recipe_ingredient table
   */
  public Set<Recipe> recipes() throws SQLException {
    if (recipes == null) {
      recipes = querier.recipes()
                       .getRecipesWithIngredient(id);
    }
    return recipes;
  }

  /**
   * Equality accessor for an object.
   * @return true if the ingredients are the same
   */
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

  /**
   * Accessor for ingredient's hashcode.
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return id.hashCode() + name.hashCode();
  }

  /**
   * Accessor for ingredient's string representation.
   * @return string
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder(name);
    sb.append(", ");
    sb.append(id);
    return sb.toString();
  }

  /**
   * Method to create a copy of the recipe.
   * @return new recipe with same id, querier
   */
  public Ingredient copy() {
    return new Ingredient(id, ounces, querier);
  }
}