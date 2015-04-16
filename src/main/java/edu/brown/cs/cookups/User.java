package edu.brown.cs.cookups;

import java.util.List;

public class User implements Person{
	
	private String name;
	private String id;
	private List<Ingredient> ingredients;
	
	public User (String name, String id, List<Ingredient> ingredients) {
		this.name = name;
		this.id = id;
		this.ingredients = ingredients;
	}
	
	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String id() {
		return this.id;
	}

	@Override
	public List<Ingredient> ingredients() {
		return ingredients;
	}

	@Override
	public Double latitude() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double longitude() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> favoriteRecipes() {
		// TODO Auto-generated method stub
		return null;
	}

}
