package edu.brown.cs.cookups;

public interface IngredientProxy {
	public String name();
	public String id();
	public List<Recipe> recipes();
	private void fill();
	public Ingedient value();
	public Double ounces(); //amount in ounces
	public Double teaspoons(); //amount in teaspoons
	public Double expiration(); //seconds until expiration
}