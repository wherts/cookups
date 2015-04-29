package edu.brown.cs.cookups.food;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;
/**
 * This class represents a Recipe
 * object that we use in Cookups.
 * @author wh7
 *
 */
public class Recipe {
  private String id;
  private String name;
  private String instructions;
  private DBManager querier;
  private List<Ingredient> ingredients, toBuy;
  private double percentHave = 1;
  private double shoppingPrice = 0;

  /**
   * Constructor for the Recipe object.
   * @param i all recipes must be instatiated.
   * with an id.
   * @param q all recipes must be given a way
   * to query the database for information about
   * itself
   */
  public Recipe(String i, DBManager q) {
    id = i;
    querier = q;
    toBuy = new ArrayList<>();
  }

  /**
   * Accessor for recipe id.
   * @return id string
   */
  public String id() {
    return id;
  }

  /**
   * Accessor for recipe's name.
   * @return name string
   */
  public String name() {
    if (name == null) {
      name = querier.recipes()
                    .getRecipeNameByID(id);
    }
    return name;
  }

  /**
   * Accessor for recipe's ingredients.
   * @return list of ingredients
   */
  public List<Ingredient> ingredients() {
    if (ingredients == null) {
      ingredients = querier.ingredients()
                           .getIngredientsByRecipe(id);
    }
    return new ArrayList<>(ingredients);
  }

  /**
   * Accessor for recipe's instructions.
   * @return string of instructions.
   */
  public String instructions() {
    if (instructions == null) {
      instructions = querier.recipes()
                            .getInstructionsByRecipe(id);
    }
    return instructions;
  }

  /**
   * Accessor for ingredients needed
   * to complete the recipe.
   * @return list of ingredients.
   */
  public List<Ingredient> shoppingList() {
    return new ArrayList<>(toBuy);
  }

  /**
   * Accessor for what total percentage (in weight)
   * of the ingredients the recipe has.
   * @return double of percentage
   */
  public double percentHave() {
    if (toBuy.size() == 0) {
      return 1;
    } else if (percentHave == 1) {
      //only calculate percenthave once
      //if it's 1, then it hasn't been set yet
      double need = 0;
      double total = 0;
      for (Ingredient ing : toBuy) {
        need += ing.ounces();
      }
      for (Ingredient i : ingredients) {
        total += i.ounces();
      }
      percentHave = (need / total);
    }
    return percentHave;
  }

  /**
   * Accessor for how much money it costs
   * to complete the recipe.
   * @return double of price
   */
  public double shoppingPrice() {
//    if (toBuy.size() == 0) {
//      return 0;
//    }else if (shoppingPrice == 0) {
//      //only want to calculate the price once from the db
//      //if it's zero then we haven't calculated it yet
//      for (Ingredient ing : toBuy) {
//        shoppingPrice += querier.getPriceById(ing.id());
//      }
//    }
    return shoppingPrice;
  }

  /**
   * Method to add an item to the recipe's shoppinglist.
   * @param ing new ingredient
   * @param oz amount needed
   */
  public void addToShoppingList(Ingredient ing, double oz) {
    toBuy.add(new Ingredient(ing.id(), oz, querier));
  }

  /**
   * Method for scaling the ingredients of a recipe
   * based on the number of people it will cook for.
   * @param partySize number of people eating the recipe
   * @return new recipe with new ingredient amounts
   */
  public Recipe scale(double partySize) {
    Recipe toReturn = new Recipe(this.id, this.querier);
    List<Ingredient> ingreds = this.ingredients();
    List<Ingredient> scaledIngredients = new ArrayList<>();
    for (Ingredient ing : ingreds) {
      scaledIngredients.add(new Ingredient(ing.id(),
          ing.ounces() * partySize,
          querier));
    }
    toReturn.setIngredients(scaledIngredients);

    return toReturn;
  }

  /**
   * Setter for a recipe's ingredients.
   * @param scaledIngredients new ingredients
   */
  public void setIngredients(
      List<Ingredient> scaledIngredients) {
    ingredients = scaledIngredients;
  }

  /**
   * Accessor for Recipe's hashcode.
   * @return int hashcode
   */
  @Override
  public int hashCode() {
    return id.hashCode();
  }

  /**
   * Accessor for Recipe to string.
   * @return string of recipe
   */
  public String toString() {
    return id;
  }

  /**
   * Equality comparison for a recipe.
   * @return true of if the recipes
   * have the same id
   */
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Recipe)) {
      return false;
    }
    Recipe r = (Recipe) o;
    return id.equals(r.id());
  }

  /**
   * Method to create a copy of the recipe.
   * @return new recipe with same id, querier
   */
  public Recipe copy() {
    return new Recipe(id, querier);
  }
}
