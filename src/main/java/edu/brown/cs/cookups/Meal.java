package edu.brown.cs.cookups;

public interface Meal {
	public double time();
	public double duration();
	public Location place();
	public List<User> attendees();
	public User host();
	public Recipe recipe();
}