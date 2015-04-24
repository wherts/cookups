package edu.brown.cs.cookups.food;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.schedule.LatLong;

public class Meal {
  public Person host;
  public List<Person> attending;
  public LatLong location;
  public LocalDate date;
  public double time, duration;
  public Recipe recipe;
  public List<Ingredient> shoppingList;

  public Meal(Person p) {
    assert (p != null);
    this.host = p;
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

	public LocalDate date() {
	  return date;
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

	public LocalDate setDate(LocalDate d) {
	  LocalDate ret = this.date;
	  this.date = d;
	  return ret;
	}

	public void changeRecipe(Recipe r) {
	  this.recipe = r;
	}

	public Recipe recipe() {
	  return recipe;
	}

	@Override
	public int hashCode() {
	  return host.hashCode() + recipe.hashCode();
	}

	@Override
	public String toString() {
	  return host.toString() + ", " 
	         + recipe.toString() + ", " 
	         + location.toString();
	}

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
	         && this.date.equals(m.date());
	}
}