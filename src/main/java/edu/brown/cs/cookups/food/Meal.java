package edu.brown.cs.cookups.food;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.brown.cs.cookups.PercentageRanker;
import edu.brown.cs.cookups.ShoppingPriceRanker;
import edu.brown.cs.cookups.TotalPriceRanker;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

/**
 * This class represents Cookups Meal. Each meal has a host, a list of guests, a
 * date, and a recipe to make
 * @author wh7
 */
public class Meal {
  private String id;
  private Person host;
  private List<Person> attending;
  private Schedule schedule;
  private List<Recipe> recipes;
  private String name;

  /**
   * Constructor for a meal.
   * @param Person The host of the meal
   */
  public Meal(Person Person, Schedule s) {

    assert (Person != null && s != null);
    this.host = Person;
    this.schedule = s;
    this.attending = new ArrayList<Person>();
    this.recipes = new ArrayList<Recipe>();
  }

  /**
   * Accessor for meal name.
   * @return meal name
   */
  public String name() {
    return name;
  }

  /**
   * Setter for meal name.
   * @param n name of meal
   * @return previous name
   */
  public String setName(String n) {
    String oldName = name;
    name = n;
    return oldName;
  }
  
  /**
   * Accessor for date of meal.
   * @return localdate
   */
  public LocalDate date() {
    LocalDate d = schedule.date();
    return LocalDate.of(d.getYear(),
                        d.getMonthValue(),
                        d.getDayOfMonth());
  }

  /**
   * Accessor for time of meal.
   * @return localtime
   */
  public LocalTime time() {
    LocalTime t = schedule.time();
    return LocalTime.of(t.getHour(), t.getMinute());
  }

  /**
   * Setter for date of meal.
   * @param lD new date.
   * @return old date
   */
  public LocalDate setDate(LocalDate lD) {
    return schedule.changeDate(lD);
  }
  
  public Schedule schedule() {
	  return this.schedule;
  }

  /**
   * Setter for time of meal.
   * @param lT new time.
   * @return old time
   */
  public LocalTime setTime(LocalTime lT) {
    return schedule.changeTime(lT);
  }

  /**
   * Accessor for end date.
   * @return null if no end set
   */
  public LocalDate endDate() {
    return schedule.endDate();
  }

  /**
   * Accessor for end time.
   * @return null if no end set
   */
  public LocalTime endTime() {
    return schedule.endTime();
  }

  /**
   * Setter for end date and time.
   * @param lDT ending datetime object
   */
  public void setEnd(LocalDateTime lDT) {
    schedule.setEnd(lDT);
  }

  /**
   * Accessor for guest list of a Meal.
   * @return list of Person attending
   */
  public List<Person> attending() {
    List<Person> copy = new ArrayList<>();
    for (Person p : attending) {

      copy.add(p);
    }
    return copy;
  }

  /**
   * Method to add a guest to the Meal.
   * @param p new Person coming to Meal
   */
  public void addAttending(Person a) {
    assert (a != null);
    attending.add(a);
  }

  /**
   * Accessor for a Meal's host.
   * @return Person object
   */
  public Person host() {

    return host;
  }

  /**
   * Setter for a Meal's host.
   * @param h new host of meal
   * 
   * @return old host
   */
  public Person setHost(Person h) {
    Person ret = this.host;
    this.host = h;
    return ret;
  }

  /**
   * Add a recipe to a Meal.
   * @param r new recipe
   */
  public void addRecipe(Recipe r) {
    this.recipes.add(r);
  }

  /**
   * Accessor for a Meal's recipes.
   * @return list of Recipes
   */

  public List<Recipe> recipes() {
    ArrayList<Recipe> ret = new ArrayList<>();
    for (Recipe r : recipes) {
      ret.add(r.copy());
    }
    return ret;
  }

  public List<Recipe> sortRecipes(String sortType) {
    Comparator<Recipe> comp = null;
    switch (sortType) { //have to play with reversed()
      case "fancy-des":
        comp = new TotalPriceRanker();
        break;
      case "fancy-asc":
        comp = new TotalPriceRanker().reversed();
        break;
      case "price-des":
        comp = new ShoppingPriceRanker();
        break;
      case "price-asc":
        comp = new ShoppingPriceRanker().reversed();
        break;
      case "fewest-missing":
      default:
        comp = new PercentageRanker();   
    }
    ArrayList<Recipe> ret = new ArrayList<>();
    for (Recipe r : recipes) {
      ret.add(r.copy());
    }
    return ret;
  }

  
  public void setID(String id) {
    this.id = id;
  }

  public String id() {
    return this.id;
  }

  /**
   * Method for hashing meal object
   * @return int for hasing
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((attending == null) ? 0 : attending.hashCode());
    result = prime * result
        + ((host == null) ? 0 : host.hashCode());
    result = prime * result
        + ((recipes == null) ? 0 : recipes.hashCode());
    result = prime * result
        + ((schedule == null) ? 0 : schedule.hashCode());
    return result;
  }

  /**
   * Accessor for object's string.
   * @return string
   */
  @Override
  public String toString() {

    StringBuilder sB = new StringBuilder();
    sB.append(host.toString());
    sB.append(", ");
    sB.append(recipes.toString());
    sB.append(", ");
    return sB.toString();

  }

  /**
   * Equality method for an object.
   * @return true of Meal has same host, recipe, guest list, and date, and time
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Meal)) {
      return false;
    }
    Meal m = (Meal) o;
    return this.host().equals(m.host())
        && this.recipes().equals(m.recipes())
        && this.attending().equals(m.attending())
        && this.schedule.date().equals(m.date())
        && this.schedule.time().equals(m.time());
  }
}
