package edu.brown.cs.cookups.api;

import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class AuthValidator implements Route {
  private Authentication auth;
  private Gson gson = new Gson();

  public AuthValidator(Authentication auth) {
    this.auth = auth;
  }

  @Override
  public Object handle(Request req, Response arg1) {
    QueryParamsMap qm = req.queryMap();
    String uid = qm.value("uid");
    String password = qm.value("password");
    boolean authenticated = auth.authenticatePassword(uid,
                                                      password);
    return gson.toJson(ImmutableMap.of("valid",
                                       authenticated));
  }
}
