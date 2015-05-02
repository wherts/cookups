package edu.brown.cs.cookups.api;

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
  private List<String> fridgeIngredients, pantryIngredients;
  private PersonManager people;
  private static final Gson GSON = new Gson();

  public ProfileDataHandler(List<String> favoriteCuisines, List<String> fridge,
      List<String> pantry,
      PersonManager people) {
    this.fridgeIngredients = fridge;
    this.pantryIngredients = pantry;
    this.people = people;
  }

  @Override
  public Object handle(Request request, Response response) {
    String name = request.params(":id");
    String profileID = name + "@brown.edu";

    Person person = people.getPersonById(profileID);

    if (person == null) {
      response.status(404);
    }

    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("fridge", fridgeIngredients)
            .put("pantry", pantryIngredients)
            .put("favCuisines", people.getPersonCuisines(person.id()))
            .put("personIngredients", people.getPersonIngredientsByID(person.id())).build();

    return GSON.toJson(variables);
  }
}
