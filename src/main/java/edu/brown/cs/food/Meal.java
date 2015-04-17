package edu.brown.cs.food;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.schedule.LatLong;

public class Meal {
  public Person host;
  public List<Person> attending;
  public LatLong location;
  public double time, duration;
  public Recipe recipe;
  public List<Ingredient> shoppingList;

  public Meal() {
    this.attending = new ArrayList<Person>();
    this.shoppingList = new ArrayList<Ingredient>();
  }

  public double setTime(double t) {
    double ret = this.time;
    this.time = t;
    return ret;
  }

	public double time() {
	  return time;
	}

	public double setDuration(double d) {
	  double ret = this.duration;
	  this.duration = d;
	  return ret;
	}

	public double duration() {
	  return duration;
	}

	public LatLong location() {
	  return location;
	}

	public LatLong setLocation(LatLong l) {
	  LatLong ret = location;
	  this.location = l;
	  return ret;
	}

	public List<Person> attending() {
	  List<Person> copy = new ArrayList<>();
	  for (Person p : attending) {
	    copy.add(p);
	  }
	  return copy;
	}

	public void addAttending(Person p) {
	  assert (p != null);
	  attending.add(p);
	}

	public Person host() {
	  return host;
	}

	public Person setHost(Person h) {
	  Person ret = this.host;
	  this.host = h;
	  return ret;
	}

	public void changeRecipe(Recipe r) {
	  this.recipe = r;
	}

	public Recipe recipe() {
	  return recipe;
	}
}