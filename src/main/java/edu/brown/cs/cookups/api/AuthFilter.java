package edu.brown.cs.cookups.api;

import spark.Filter;
import spark.Request;
import spark.Response;

public class AuthFilter implements Filter {
  private Authentication authenticator;

  public AuthFilter(Authentication auth) {
    authenticator = auth;
  }

  @Override
  public void handle(Request request, Response response) throws Exception {
    String id = request.cookie("id");
    String token = request.cookie("auth");
    boolean authenticated = authenticator.authenticate(id, token);

    if (!authenticated) {
      response.redirect("/");
    }
  }

}
