package edu.brown.cs.cookups.views;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

import edu.brown.cs.cookups.api.Authentication;

public class LoginView implements TemplateViewRoute {
  private Authentication authenticator;

  public LoginView(Authentication auth) {
    authenticator = auth;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    Map<String, Object> variables =
        ImmutableMap.of("title", "Brown Cookups");
    return new ModelAndView(variables, "login.ftl");
  }
}
