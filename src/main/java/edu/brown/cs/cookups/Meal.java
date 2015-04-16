package edu.brown.cs.cookups;

import java.util.List;

public interface Meal {
	public double time();
	public double duration();
	public String place();
	public List<UserOld> attendees();
	public UserOld host();
	public Recipe recipe();
}