package edu.brown.cs.cookups;

public interface Manager {
	public User getProfileById(String id);
	public Recipe getRecipeById(String id);
	public String getUserNameById(String id);
	public String getUserIdByName(String name);
}