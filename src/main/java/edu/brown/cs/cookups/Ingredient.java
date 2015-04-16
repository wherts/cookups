package edu.brown.cs.cookups;

public interface Ingredient {
	public double ounces(); //amount in ounces
	public double teaspoons(); //amount in teaspoons
	public double expiration(); //seconds until expiration
	public String id();
}