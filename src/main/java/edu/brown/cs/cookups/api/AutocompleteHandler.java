package edu.brown.cs.cookups.api;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

public class AutocompleteHandler implements Route {
  private List<String> options;
  private static final Gson GSON = new Gson();

  public AutocompleteHandler(List<String> options) {
    this.options = options;
  }

  @Override
  /**
   * Handles the view for ranking suggestions from the text input in the gui.
   */
  public Object handle(final Request req, final Response res) {
    return GSON.toJson(options.toArray());
  }
}
