package edu.brown.cs.cookups.food;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.cookups.db.DBLink;

public class Recipe {
  private String id;
  private String name;
  private String instructions;
  private DBLink querier;
  private List<Ingredient> ingredients, toBuy;
  private double percentHave = 1;
  private double shoppingPrice = 0;

  public Recipe(String i, DBLink q) {
    id = i;
    querier = q;
    toBuy = new ArrayList<>();
  }

  public String id() {
    return id;
  }

  public String name() {
    if (name == null) {
      name = querier.ingredients()
                    .getIngredientNameByID(id);
    }
    return name;
  }

  public List<Ingredient> ingredients() {
    if (ingredients == null) {
      ingredients = querier.ingredients()
                           .getIngredientsByRecipe(id);
    }
    return ingredients;
  }

  public String instructions() {
    if (instructions == null) {
      instructions = querier.recipes()
                            .getInstructionsByRecipe(id);
    }
    return instructions;
  }

  public List<Ingredient> shoppingList() {
    return new ArrayList<>(toBuy);
  }

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

  public void addToShoppingList(Ingredient ing, double oz) {
    toBuy.add(new Ingredient(ing.id(), oz, querier));
  }

  public void setName(String n) {
    assert (n != null);
    this.name = n;
  }

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

  public void setIngredients(
      List<Ingredient> scaledIngredients) {
    ingredients = scaledIngredients;
  }

  public int hashCode() {
    return id.hashCode();
  }

  public String toString() {
    return id;
  }

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
}
