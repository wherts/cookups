package edu.brown.cs.cookups.api;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class LogoutHandler implements TemplateViewRoute {

  @Override
  public ModelAndView handle(Request request, Response response) {
    response.removeCookie("id");
    response.removeCookie("auth");
    Map<String, Object> variables =
        ImmutableMap.of("title", "Brown Cookups");
    return new ModelAndView(variables, "login.ftl");
  }

}
