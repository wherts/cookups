package edu.brown.cs.cookups.db;

import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.User;

public interface Manager {

	public User getProfileById(String id);
	public Recipe getRecipeById(String id);
	public String getUserNameById(String id);
	public String getUserIdByName(String name);

}