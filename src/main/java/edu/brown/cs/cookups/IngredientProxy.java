package edu.brown.cs.cookups;

import java.util.List;

public interface IngredientProxy {
	String name();
	String id();
	List<Recipe> recipes();
	void fill();
	Ingredient value();
	Double ounces(); //amount in ounces
	Double teaspoons(); //amount in teaspoons
	Double expiration(); //seconds until expiration
}