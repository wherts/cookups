package edu.brown.cs.cookups.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.PersonManager;

public class SignupHandler implements TemplateViewRoute {
  private Authentication auth;
  private PersonManager people;

  public SignupHandler(Authentication auth, PersonManager people) {
    this.auth = auth;
    this.people = people;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    // Get form data
    QueryParamsMap qm = request.queryMap();
    String id = qm.value("email");
    String name = qm.value("name");
    String pass = qm.value("password");
    List<Ingredient> i = new ArrayList<Ingredient>();

    try {
      people.addPerson(name, id, i);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    auth.setPassword(id, pass);
    String token = auth.getToken(id);
    auth.authenticate(id, token);

    System.out.println("signed up!!");
    String profLink = "/profile/" + id.split("@")[1];
    Map<String, Object> variables =
        ImmutableMap.of("name", name, "favRecipes", "",
            "profLink", profLink, "editable", true);
    return new ModelAndView(variables, "profile.ftl");
  }

}
