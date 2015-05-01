package edu.brown.cs.cookups;

import java.io.File;
import java.sql.SQLException;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import edu.brown.cs.cookups.db.DBLink;
import edu.brown.cs.cookups.db.DBManager;

public class Main {
  private String[] args;

  /**
   * The main constructor.
   * @param args - the command line arguments
   * @throws SQLException throws SQLException on database errors
   * @throws ClassNotFoundException throws class not found exception on
   *           compilation error
   */
  public static void main(String[] args)
    throws ClassNotFoundException, SQLException {
    new Main(args).run();
  }

  /** @param args */
  private Main(String[] args) {
    this.args = args;
  }

  /**
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  private void run() throws ClassNotFoundException,
    SQLException {
    OptionParser parser = new OptionParser();
    OptionSpec<File> fileSpec = parser.nonOptions()
                                      .ofType(File.class);
    OptionSpec<String> dbPathSpec = parser.accepts("db")
                                          .withRequiredArg()
                                          .ofType(String.class);
    OptionSpec<File> ingredientsSpec = parser.accepts("ingredients")
                                             .withRequiredArg()
                                             .ofType(File.class);
    OptionSpec<File> usersSpec = parser.accepts("users")
                                       .withRequiredArg()
                                       .ofType(File.class);
    OptionSpec<File> recipesSpec = parser.accepts("recipes")
                                         .withRequiredArg()
                                         .ofType(File.class);
    OptionSet options = null;
    try {
      options = parser.parse(args);
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      System.exit(1);
    }

    if (!options.has("db")) {
      System.err.println("ERROR: usage ./run <db>");
      System.exit(1);
    }
    String dbPath = options.valueOf(dbPathSpec);
    System.out.println("DB is: " + dbPath);
    DBManager db = new DBLink(dbPath);
    if (options.has(ingredientsSpec)) {
      File f = options.valueOf(ingredientsSpec);
      if (f.isFile() && !f.isDirectory()) {
        db.importIngredients(f);
        System.out.println("Imported Ingredients from: "
            + f.getPath());
      } else {
        System.out.println("ERROR: " + f.getPath()
            + " is not a file");
      }

    }

    if (options.has(usersSpec)) {
      File dir = options.valueOf(usersSpec);
      if (!dir.isFile() && dir.isDirectory()) {
        db.importAllUsers(dir.getPath());
        System.out.println("Imported Users from: "
            + dir.getPath());
      } else {
        System.out.println("ERROR: " + dir.getPath()
            + " is not a directory");
      }

    }

    if (options.has(recipesSpec)) {
      File dir = options.valueOf(recipesSpec);
      if (!dir.isFile() && dir.isDirectory()) {
        db.importAllRecipes(dir.getPath());
        System.out.println("Imported Recipes from: "
            + dir.getPath());
      } else {
        System.out.println("ERROR: " + dir.getPath()
            + " is not a directory");
      }

    }

    URLHandler gui = new URLHandler((DBLink) db);
    gui.runSparkServer();
  }
}
