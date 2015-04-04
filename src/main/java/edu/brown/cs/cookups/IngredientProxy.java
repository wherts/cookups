package edu.brown.cs.cookups;

import java.util.List;

public interface IngredientProxy {
	String name();
	String id();
	List<Recipe> recipes();
	void fill();
	Ingredient value();
<<<<<<< HEAD
	double ounces(); //amount in ounces
	double teaspoons(); //amount in teaspoons
	double expiration(); //seconds until expiration
}
=======
	Double ounces(); //amount in ounces
	Double teaspoons(); //amount in teaspoons
	Double expiration(); //seconds until expiration
}
>>>>>>> 961926eef6ad79a5a365fc56582490fec046db38
