package edu.brown.cs.cookups.api;

import java.util.List;

import spark.Request;
import spark.Response;
import spark.Route;

import com.google.gson.Gson;

import edu.brown.cs.autocorrect.Engine;

public class AutocorrectHandler implements Route {
  private Engine autocomplete;
  private static final Gson GSON = new Gson();

  public AutocorrectHandler(Engine e) {
    autocomplete = e;
  }

  @Override
  /**
   * Handles the view for ranking suggestions from the text input in the gui.
   */
  public Object handle(final Request req, final Response res) {
    String jsInput = req.params(":input");
    System.out.println(jsInput);
    List<String> suggestions = autocomplete.generate(jsInput);

    return GSON.toJson(suggestions);
  }
}
