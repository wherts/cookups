package edu.brown.cs.cookups.food;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.schedule.LatLong;
import edu.brown.cs.cookups.schedule.Schedule;
/**
 * This class represents Cookups Meal.
 * Each meal has a host, a list of
 * guests, a date, and a recipe to make
 * @author wh7
 *
 */
public class Meal {
  private Person host;
  private List<Person> attending;
  private LatLong location;
  private Schedule schedule;
  private LocalDate date;
  private LocalTime time;
  private double duration;
  private Recipe recipe;

  /**
   * Constructor for a meal.
   * @param p The host of the meal
   */
  public Meal(Person p) {
    assert (p != null);
    this.host = p;
    this.attending = new ArrayList<Person>();
  }

  /**
   * Setter for the time of day
   * that the meal is.
   * @param t new LocalTime object
   * @return Old LocalTime object
   */
  public LocalTime setTime(LocalTime lT) {
    LocalTime ret = this.time;
    this.time = lT;
    return ret;
  }

  /**
   * Accessor for the time of a meal.
   * @return LocalTime object
   */
	public LocalTime time() {
	  return LocalTime.of(time.getHour(), time.getMinute());
	}

	/**
	 * Setter for the duration of a meal,
	 * in minutes.
	 * @param d minutes
	 * @return old meal duration
	 */
	public double setDuration(double d) {
	  double ret = this.duration;
	  this.duration = d;
	  return ret;
	}

	/**
	 * Accessor for a meal's duration.
	 * @return double minutes
	 */
	public double duration() {
	  return duration;
	}

	/**
	 * Accessor for meal's location.
	 * @return a LatLong object
	 */
	public LatLong location() {
	  return location;
	}

	/**
	 * Accessor for a meal's date.
	 * @return a LocalDate object
	 */
	public LocalDate date() {
	  return LocalDate.of(date.getYear(),
	                      date.getMonthValue(),
	                      date.getDayOfMonth());
	}

	/**
	 * Setter for a meal's location.
	 * @param l new LatLong location
	 * @return old location
	 */
	public LatLong setLocation(LatLong l) {
	  LatLong ret = location;
	  this.location = l;
	  return ret;
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
	 * @param p new person coming to Meal
	 */
	public void addAttending(Person p) {
	  assert (p != null);
	  attending.add(p);
	}

	/**
	 * Accessor for a Meal's host.
	 * @return person object
	 */
	public Person host() {
	  return host;
	}

	/**
	 * Setter for a Meal's host.
	 * @param h new host of meal
	 * @return old host
	 */
	public Person setHost(Person h) {
	  Person ret = this.host;
	  this.host = h;
	  return ret;
	}

	/**
	 * Setter for a Meal's date.
	 * @param d new LocalDate
	 * @return old date object
	 */
	public LocalDate setDate(LocalDate d) {
	  LocalDate ret = this.date;
	  this.date = d;
	  return ret;
	}

	/**
	 * Setter for a Meal's recipe.
	 * @param r new recipe
	 */
	public void changeRecipe(Recipe r) {
	  this.recipe = r;
	}

	/**
	 * Accessor for a Meal's recipe.
	 * @return Recipe object
	 */
	public Recipe recipe() {
	  return recipe;
	}

	/**
	 * Accessor for object's hashcode.
	 * @return int hashcode
	 */
	@Override
	public int hashCode() {
	  return host.hashCode() + recipe.hashCode();
	}

	/**
	 * Accessor for object's string.
	 * @return string
	 */
	@Override
	public String toString() {
	  return host.toString() + ", " 
	         + recipe.toString() + ", " 
	         + location.toString();
	}

	/**
	 * Equality method for an object.
	 * @return true of Meal has same
	 * host, recipe, guest list, and
	 * date, and time
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
	  return this.host.equals(m.host())
	         && this.recipe.equals(m.recipe())
	         && this.attending.equals(m.attending())
	         && this.date.equals(m.date())
	         && this.time.equals(m.time());
	}
}