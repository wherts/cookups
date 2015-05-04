package edu.brown.cs.cookups.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class ProfileView implements TemplateViewRoute {
  private PersonManager people;

  public ProfileView(PersonManager p) {
    people = p;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String name = request.params(":name");
    String profileID = name + "@brown.edu";
    Person person = people.getPersonById(profileID);

    if (person == null) {
      response.status(404);
    }

    boolean editable = false;
    String userID = request.cookie("id");
    if (profileID.equals(userID)) {
      editable = true;
    }
    String path = userID.split("@")[0];
    List<Ingredient> ingredients = people.getPersonIngredientsByID(profileID);
    Map<String, Object> variables =
        ImmutableMap.of("name", person.name(), "favCuisines",
            person.favoriteCuisines(), "personIngredients",
            ingredients,
            "editable", editable, "path", name);
    return new ModelAndView(variables, "profile.ftl");
  }

}
