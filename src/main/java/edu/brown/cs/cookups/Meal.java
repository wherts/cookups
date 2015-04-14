package edu.brown.cs.cookups;

import java.util.List;

public interface Meal {
	double time();
	double duration();
  LatLong place();
  List<User> attendees();
	User host();
	Recipe recipe();
}