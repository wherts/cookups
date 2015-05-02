package edu.brown.cs.cookups.api;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
  private Authentication auth;

  public LoginHandler(Authentication auth) {
    this.auth = auth;
  }

  @Override
  public Object handle(Request request, Response response) {
    // Get form data
    QueryParamsMap qm = request.queryMap();
    String id = qm.value("id");
    String pass = qm.value("password");

    // Authenticate password
    boolean authenticated = auth.authenticatePassword(id, pass);

    if (authenticated) {
      String token = auth.getToken(id);
      response.cookie("auth", token);
      response.cookie("id", id);
      String profilePath = id.split("@")[0];
      response.redirect("/profile/" + profilePath);
    } else {
    response.redirect("/");
    }
    return null;
  }

}
