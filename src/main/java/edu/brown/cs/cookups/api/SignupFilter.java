package edu.brown.cs.cookups.api;

import spark.Filter;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
import edu.brown.cs.cookups.person.PersonManager;

public class SignupFilter implements Filter {
  private PersonManager people;

  public SignupFilter(PersonManager people) {
    this.people = people;
  }

  @Override
  public void handle(Request request, Response response)
    throws Exception {
    QueryParamsMap qm = request.queryMap();
    String id = qm.value("email");

    if (people.hasPersonByID(id)) {
      System.out.println("ID exists");
      response.redirect("/");
      Spark.halt();
    }
  }
}
