package edu.brown.cs.cookups.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import com.google.common.collect.ImmutableMap;

public class LoginView implements TemplateViewRoute {
  private Authentication authenticator;

  public LoginView(Authentication auth) {
    // TODO Auto-generated constructor stub
    authenticator = auth;
  }

  @Override
  public ModelAndView handle(Request request, Response response) {
    String url = null;
    try {
      url = "/recipe/" + URLEncoder.encode("/r/1.6", "UTF-8");
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    Map<String, Object> variables =
        ImmutableMap.of("title", "Brown Cookups", "linkToTest", url);
    return new ModelAndView(variables, "login.ftl");
  }
}
