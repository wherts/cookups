package edu.brown.cs.cookups;

public interface Manager {
	User getProfileById(String id);
	Recipe getRecipeById(String id);
	String getUserNameById(String id);
	String getUserIdByName(String name);
	
}