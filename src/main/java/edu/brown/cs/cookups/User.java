package edu.brown.cs.cookups;

import java.util.List;

public interface User {

	/**
	  * Gets a User's ID.
	  * @return id
	  */
	String id();

	/**
	  * Sets a User's ID.
	  * @param id of user
	  */
	void setId(String id);

	/**
	  * Gets a User's name.
	  * @return name
	  */
	String name();

	/**
	  * Sets a User's name.
	  * @param name username
	  */
	void setName(String name);

	/**
	  * Gets a User's ingredients.
	  * @return list of UserIngredients
	  */
	List<IngredientProxy> ingredients();

	/**
	  * Gets a user's schedule.
	  * If a person is not active, return null.  
	  * @return schedule
	  */
	Schedule schedule();

	/**
	  * Setss a user's schedule.
	  * If a person is not active, set's null.  
	  * @param schedule user's schedule
	  */
	void setSchedule(Schedule schedule);

	/** 
	  * Gets a User's location.
	  * @return lat long
	  */
	LatLong location();

	/** 
	  * Get's a User's location.
	  * @param location of user
	  */
	void setLocation(LatLong location);


}
