package edu.brown.cs.cookups;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.gson.Gson;

import edu.brown.cs.autocomplete.Engine;
import edu.brown.cs.autocomplete.Trie;
import edu.brown.cs.cookups.api.AuthFilter;
import edu.brown.cs.cookups.api.Authentication;
import edu.brown.cs.cookups.api.AutocompleteHandler;
import edu.brown.cs.cookups.api.BasicView;
import edu.brown.cs.cookups.api.CookFriendsHandler;
import edu.brown.cs.cookups.api.CookupHandler;
import edu.brown.cs.cookups.api.LoginHandler;
import edu.brown.cs.cookups.api.LoginView;
import edu.brown.cs.cookups.api.ProfileDataHandler;
import edu.brown.cs.cookups.api.ProfileView;
import edu.brown.cs.cookups.api.RecipeView;
import edu.brown.cs.cookups.api.SearchEngine;
import edu.brown.cs.cookups.api.SendUsersHandler;
import edu.brown.cs.cookups.api.SignupHandler;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.person.PersonManager;
import freemarker.template.Configuration;

public class URLHandler {
  private DBLink db;
  private PersonManager people;
  private List<String> ids, names, ingredients, recipes;
  private Authentication auth;
  private Engine recipeSearch, peopleSearch;

  private final static Gson GSON = new Gson();

  public URLHandler(DBLink db)
      throws ClassNotFoundException,
      SQLException {
    this.db = db;
    people = new PersonManager(this.db);
    List<List<String>> userData = db.users().getNamesAndIDs();
    ids = userData.get(0);
    names = userData.get(1);
    ingredients = db.ingredients().getAllIngredientNames();
    recipes = db.recipes().getAllRecipeNames();
    auth = new Authentication(this.db);

    recipeSearch = new Engine(new Trie(recipes));
    peopleSearch = new Engine(new Trie(names));
  }

  public void runSparkServer() {
    Spark.externalStaticFileLocation("src/main/resources/static");
    // Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();
    Spark.setPort(3456);

    // Set up authentication filters
    Spark.before("/cookwfriends", new AuthFilter(auth));
    Spark.before("/cook", new AuthFilter(auth));
    Spark.before("/cookup", new AuthFilter(auth));
    Spark.before("/recipe/:id", new AuthFilter(auth));
    Spark.before("/profile/:id", new AuthFilter(auth));
    Spark.before("/allUsers", new AuthFilter(auth));
    Spark.before("/profileData", new AuthFilter(auth));

    // Basic template rendering routes
    Spark.get("/cookwfriends",
        new BasicView("cookwfriends.ftl"), freeMarker);
    Spark.get("/cook", new BasicView("cook.ftl"), freeMarker);
    Spark.get("/cookup", new BasicView("cookup.ftl"), freeMarker);
    Spark.get("/", new LoginView(auth), freeMarker);
    Spark.get("/recipe/:id", new RecipeView(), freeMarker);
    Spark.get("/profile/:name", new ProfileView(people), freeMarker);

    // Form handling routes
    Spark.post("/cookwfriends", new CookFriendsHandler(people, db));
    Spark.post("/cookup", new CookupHandler(people));
    Spark.post("/login", new LoginHandler(auth));
    Spark.post("/signup", new SignupHandler(auth, people));
    Spark.post("/search", new SearchEngine(recipeSearch, peopleSearch),
        freeMarker);
    // JSON data routes
    Spark.get("/allRecipes", new AutocompleteHandler(recipes));
    Spark.get("/allUsers", new SendUsersHandler(names, ids));

    Spark.get("/allIngredients", new AutocompleteHandler(ingredients));
    Spark.get("/profileData", new ProfileDataHandler(recipes, ingredients,
        people));
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
