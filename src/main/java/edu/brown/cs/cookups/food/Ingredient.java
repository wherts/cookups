package edu.brown.cs.cookups.food;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;

import edu.brown.cs.cookups.db.DBManager;

/** This class represents an ingredient.
 * @author wh7 */
public class Ingredient {
  private double ounces;
  private String id, name, storage;
  private LocalDate dateCreated;
  private DBManager querier;
  private Set<Recipe> recipes;
  private double price = 0;

  /** Public constructor for an ingredient.
   * @param i id
   * @param oz weight
   * @param dbM way to query database for
   *        more information about the ingredient */
  public Ingredient(String i, double oz, DBManager dbM) {
    id = i;
    ounces = oz;
    querier = dbM;
    dateCreated = LocalDateTime.now().toLocalDate();
  }

  /** Accessor for ingredient id.
   * @return string id */
  public String id() {
    return id;
  }

  /** Accessor for ingredient name.
   * @return string name
   * @throws SQLException if ingredient
   *         id is not in database */
  public String name() throws SQLException {
    if (name == null) {
      name = querier.ingredients().ingredientNameCache(id);
    }
    return name;
  }

  /** Accessor for amount in ounces.
   * @return double ounces */
  public double ounces() { // amount in ounces
    return ounces;
  }

  /** Accessor for amount in teaspoons.
   * @return double teaspoons */
  public double teaspoons() { // amount in teaspoons
    return Conversion.teaspoons(ounces);
  }

  /** Accessor for amount in tablespoons.
   * @return double tablespoons */
  public double tablespoons() { // amount in tablespoons
    return Conversion.tablespoons(ounces);
  }

  /** Accessor for amount in cups.
   * @return double cups */
  public double cups() { // amount in cups
    return Conversion.cups(ounces);
  }

  /** Accessor for time elapsed since the ingredient
   * was added.
   * @return double elapsed */
  public double elapsed() { // seconds until expiration
    LocalDate curr = LocalDateTime.now().toLocalDate();
    Period time = Period.between(curr, dateCreated);
    return (time.getYears() * 365)
        + (time.getMonths() * 30)
        + (time.getDays());
  }

  /**
   * Method to tell if an ingredient is past
   * its expiration date.
   * @return true if this ingredient is expired.
   * @throws SQLException if ingredient is not
   * in the database
   */
  public boolean isExpired() throws SQLException {
    //get expiration time in minutes, if 0 return false
    //get date created
    //get period, if minutes of period > expiration time
    return false;
  }

  /** Accessor for all the recipes this ingredient.
   * is used in.
   * @return set of recipes
   * @throws SQLException if ingredient does not
   *         appear in recipe_ingredient table */
  public Set<Recipe> recipes() throws SQLException {
    if (recipes == null) {
      recipes = querier.recipes()
          .getRecipesWithIngredient(id);
    }
    return recipes;
  }

  /** Accessor for the price of this amount of
   * this ingredient.
   * @return double of ingredient's cost
   * @throws SQLException if ingredient does not
   *         appear in ingredients table */
  public int price() throws SQLException {
    if (price == 0) { // hasn't been set yet
      price = querier.ingredients()
          .priceByID(id);
    }
    return (int) (price * ounces); // price values are stored in cents
  }

  /** Accessor for the storage type of this ingredient.
   * @return string of storage location.
   * @throws SQLException of ingredient does
   *         not appear in database. */
  public String storage() throws SQLException {
    if (storage == null) {
      storage = querier.ingredients()
          .storageByID(id);
    }
    return storage;
  }

  /** Equality accessor for an object.
   * @return true if the ingredients are the same */
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

  /** Accessor for ingredient's hashcode.
   * @return int hashcode */
  @Override
  public int hashCode() {
    return id.hashCode() + name.hashCode();
  }

  /** Accessor for ingredient's string representation.
   * @return string */
  @Override
  public String toString() {
    StringBuilder sb = null;
    try {
      sb = new StringBuilder(this.name());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    sb.append(", ");
    sb.append(id);
    return sb.toString();
  }

  /** Method to create a copy of the recipe.
   * @return new recipe with same id, querier */
  public Ingredient copy() {
    return new Ingredient(id, ounces, querier);
  }
}
