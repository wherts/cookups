package edu.brown.cs.cookups.api;

import java.sql.SQLException;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public class ProfileView implements TemplateViewRoute {
  private PersonManager people;

  public ProfileView(PersonManager p) {
    people = p;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String id = request.params(":id") + "@brown.edu";

    Person person = null;
    try {
      person = people.getPersonById(id);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (person == null) {
      response.status(404);
    }


    Map<String, Object> variables =
        ImmutableMap.of("name", person.name(), "favRecipes",
            person.favoriteRecipes());
    return new ModelAndView(variables, "profile.ftl");
  }

}
