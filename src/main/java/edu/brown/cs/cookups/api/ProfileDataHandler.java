package edu.brown.cs.cookups.api;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class ProfileDataHandler implements Route {
  private List<String> recipes, ingredients;
  private PersonManager people;
  private static final Gson GSON = new Gson();

  public ProfileDataHandler(List<String> recipes, List<String> ingredients,
      PersonManager people) {
    this.recipes = recipes;
    this.ingredients = ingredients;
    this.people = people;
  }

  @Override
  public Object handle(Request request, Response response) {
    String name = request.params(":id");
    String profileID = name + "@brown.edu";

    Person person = null;
    try {
      person = people.getPersonById(profileID);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (person == null) {
      response.status(404);
    }

    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("recipes", recipes)
            .put("ingredients", ingredients)
            .put("favRecipes", person.favoriteRecipes())
            .put("personIngredients", person.ingredients()).build();

    return GSON.toJson(variables);
  }
}
