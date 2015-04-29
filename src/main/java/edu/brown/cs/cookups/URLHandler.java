package edu.brown.cs.cookups;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

import edu.brown.cs.autocomplete.Engine;
import edu.brown.cs.autocomplete.Trie;
import edu.brown.cs.cookups.api.AutocompleteHandler;
import edu.brown.cs.cookups.api.BasicView;
import edu.brown.cs.cookups.api.CookFriendsHandler;
import edu.brown.cs.cookups.api.CookupHandler;
import edu.brown.cs.cookups.api.LoginHandler;
import edu.brown.cs.cookups.api.ProfileView;
import edu.brown.cs.cookups.api.RecipeView;
import edu.brown.cs.cookups.api.SearchHandler;
import edu.brown.cs.cookups.api.SignupHandler;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.person.PersonManager;
import freemarker.template.Configuration;

public class URLHandler {
  private DBLink db;
  private PersonManager people;
  private Engine names, ingredients;

  private final static Gson GSON = new Gson();

  public URLHandler(DBLink db)
      throws ClassNotFoundException,
      SQLException {
    this.db = db;
    people = new PersonManager(this.db);
    names = new Engine(new Trie(db.users().getAllNames()));
  }

  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    // Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(3456);
    // Setup Spark Routes
    Spark.get("/", new BasicView("login.ftl"), freeMarker);
    Spark.get("/cook", new BasicView("cook.ftl"), freeMarker);
    Spark.get("/cookwfriends", new BasicView("cookwfriends.ftl"), freeMarker);
    Spark.get("/cookup", new BasicView("cookup.ftl"), freeMarker);
    Spark.get("/search/:term", new SearchHandler(), freeMarker);

    Spark.get("/profile/:id", new ProfileView(people), freeMarker);
    Spark.get("/recipe/:id", new RecipeView(), freeMarker);

    Spark.post("/cookwfriends", new CookFriendsHandler(people));
    Spark.post("/cookup", new CookupHandler(people));
    Spark.post("/login", new LoginHandler());
    Spark.post("/signup", new SignupHandler());
    Spark.post("/autoPeople", new AutocompleteHandler(names));
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }
}
