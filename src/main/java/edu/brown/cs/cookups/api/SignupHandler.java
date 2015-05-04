package edu.brown.cs.cookups.api;

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
    System.out.printf("ID: %s Name: %s Pass: %s%n", id, name, pass);
    List<Ingredient> i = new ArrayList<Ingredient>();
    people.addPerson(name, id, i);
    auth.setPassword(id, pass);
    String token = auth.getToken(id);
    auth.authenticate(id, token);

    String path = id.split("@")[0];
    String profLink = "/profile/" + path;
    
    response.cookie("auth", token);
    response.cookie("id", id);
    
    System.out.printf("Path: %s ProfLink: %s%n", path, profLink);
    Map<String, Object> variables =
        ImmutableMap.of("name", name, "favCuisines", "", "editable", true,
            "path", path);
    response.redirect(profLink);
    return new ModelAndView(variables, "profile.ftl");
  }
}
