package edu.brown.cs.cookups;

import java.util.List;

public interface Meal {

  public double time();

  public double duration();

  public double place();

  public List<User> attendees();

  public User host();

  public Recipe recipe();

	
}