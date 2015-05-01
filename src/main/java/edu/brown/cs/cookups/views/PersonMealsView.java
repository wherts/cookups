package edu.brown.cs.cookups.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.food.Meal;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class PersonMealsView implements TemplateViewRoute {
  private PersonManager people;

  public PersonMealsView(PersonManager people) {
    this.people = people;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String id = request.cookie("id");
    String profileLink = "/profile/" + id.split("@")[0];

    Person person = null;
    try {
      person = people.getPersonById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (person == null) {
      response.status(404);
    }

    List<Meal> meals = people.getPersonMeals(person);

    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("meals", meals)
            .put("profLink", profileLink).build();

    return new ModelAndView(variables, "meals.ftl");
  }

}
