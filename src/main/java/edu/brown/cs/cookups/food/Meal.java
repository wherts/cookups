package edu.brown.cs.cookups.food;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;

/** This class represents Cookups Meal.
 * Each meal has a host, a list of
 * guests, a date, and a recipe to make
 * @author wh7 */
public class Meal {
  private Person host;
  private List<Person> attending;
  private LatLong location;
  private Schedule schedule;
  private Recipe recipe;
  private String name;

  /** Constructor for a meal.
   * @param p The host of the meal */
  public Meal(Person p, Schedule s) {
    assert (p != null && s != null);
    this.host = p;
    this.schedule = s;
    this.attending = new ArrayList<Person>();
  }

  /** Accessor for meal name.
   * @return meal name */
  public String name() {
    return name;
  }

  /** Setter for meal name.
   * @param n name of meal
   * @return previous name */
  public String setName(String n) {
    String oldName = name;
    name = n;
    return oldName;
  }

  /** Accessor for date of meal.
   * @return localdate */
  public LocalDate date() {
    LocalDate d = schedule.date();
    return LocalDate.of(d.getYear(), d.getMonthValue(), d.getDayOfMonth());
  }

  /** Accessor for time of meal.
   * @return localtime */
  public LocalTime time() {
    LocalTime t = schedule.time();
    return LocalTime.of(t.getHour(), t.getMinute());
  }

  /** Setter for date of meal.
   * @param lD new date.
   * @return old date */
  public LocalDate setDate(LocalDate lD) {
    return schedule.changeDate(lD);
  }

  /** Setter for time of meal.
   * @param lT new time.
   * @return old time */
  public LocalTime setTime(LocalTime lT) {
    return schedule.changeTime(lT);
  }

  /** Accessor for end date.
   * @return null if no end set */
  public LocalDate endDate() {
    return schedule.endDate();
  }

  /** Accessor for end time.
   * @return null if no end set */
  public LocalTime endTime() {
    return schedule.endTime();
  }

  /** Setter for end date and time.
   * @param lDT ending datetime object */
  public void setEnd(LocalDateTime lDT) {
    schedule.setEnd(lDT);
  }

  /** Accessor for meal's location.
   * @return a LatLong object */
  public LatLong location() {
    return location;
  }

  /** Setter for a meal's location.
   * @param l new LatLong location
   * @return old location */
  public LatLong setLocation(LatLong l) {
    LatLong ret = location;
    this.location = l;
    return ret;
  }

  /** Accessor for guest list of a Meal.
   * @return list of Person attending */
  public List<Person> attending() {
    List<Person> copy = new ArrayList<>();
    for (Person p : attending) {
      copy.add(p);
    }
    return copy;
  }

  /** Method to add a guest to the Meal.
   * @param p new person coming to Meal */
  public void addAttending(Person p) {
    assert (p != null);
    attending.add(p);
  }

  /** Accessor for a Meal's host.
   * @return person object */
  public Person host() {
    return host;
  }

  /** Setter for a Meal's host.
   * @param h new host of meal
   * @return old host */
  public Person setHost(Person h) {
    Person ret = this.host;
    this.host = h;
    return ret;
  }

  /** Setter for a Meal's recipe.
   * @param r new recipe */
  public void changeRecipe(Recipe r) {
    this.recipe = r;
  }

  /** Accessor for a Meal's recipe.
   * @return Recipe object */
  public Recipe recipe() {
    return recipe;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((attending == null) ? 0 : attending.hashCode());
    result = prime * result + ((host == null) ? 0 : host.hashCode());
    result = prime * result + ((location == null) ? 0 : location.hashCode());
    result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
    result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
    return result;
  }

  /** Accessor for object's string.
   * @return string */
  @Override
  public String toString() {
    return host.toString() + ", "
        + recipe.toString() + ", "
        + location.toString();
  }

  /** Equality method for an object.
   * @return true of Meal has same
   *         host, recipe, guest list, and
   *         date, and time */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Meal)) {
      return false;
    }
    Meal m = (Meal) o;
    return this.host.equals(m.host())
        && this.recipe.equals(m.recipe())
        && this.attending.equals(m.attending())
        && this.schedule.date().equals(m.date())
        && this.schedule.time().equals(m.time());
  }
}
