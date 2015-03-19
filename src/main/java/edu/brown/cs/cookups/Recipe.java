package edu.brown.cs.cookups;

public interface Recipe {
	public String id();
	public String name();
	public List<String> ingredients();
	public void setIngredients(List<String> ingredients);
	public void setName(String n);
}
