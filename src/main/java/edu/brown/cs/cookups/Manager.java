package edu.brown.cs.cookups;

import edu.brown.cs.cookups.food.Recipe;
import edu.brown.cs.cookups.person.User;

public interface Manager {
	User getProfileById(String id);
	Recipe getRecipeById(String id);
	String getUserNameById(String id);
	String getUserIdByName(String name);	
}