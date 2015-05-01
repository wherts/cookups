package edu.brown.cs.cookups.api;

import java.util.List;
import java.util.Map;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class SendUsersHandler implements Route {
  private List<String> names, ids;
  private static final Gson GSON = new Gson();

  public SendUsersHandler(List<String> names, List<String> ids) {
    this.names = names;
    this.ids = ids;
  }

  @Override
  /**
   * Handles the view for ranking suggestions from the text input in the gui.
   */
  public Object handle(final Request req, final Response res) {
    Map<String, Object> variables =
        new ImmutableMap.Builder<String, Object>()
            .put("names", names)
            .put("ids", ids).build();

    return GSON.toJson(variables);
  }
}
