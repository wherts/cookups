package edu.brown.cs.cookups.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;

import edu.brown.cs.cookups.food.Ingredient;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class DBLink implements DBManager {
  private Connection conn;
  public static final String USER = "user(id TEXT, name TEXT, cuisines TEXT, PRIMARY KEY (id))";
  public static final String INGREDIENT = "ingredient(id TEXT, name TEXT, price INTEGER, "
      + "storage TEXT, exp INTEGER, PRIMARY KEY(id))";
  public static final String USER_INGREDIENT = "user_ingredient(user TEXT, ingredient TEXT, qty FLOAT"
      + ", exp TEXT NOT NULL"
      + ", PRIMARY KEY(user, ingredient)"
      + ", FOREIGN KEY(user) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE"
      + ", FOREIGN KEY(ingredient) REFERENCES ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)";
  public static final String RECIPE = "recipe(id TEXT, name TEXT, instructions TEXT, PRIMARY KEY(id))";
  public static final String RECIPE_INGREDIENT = "recipe_ingredient(recipe TEXT, ingredient TEXT, qty FLOAT"
      + ", PRIMARY KEY(recipe, ingredient)"
      + ", FOREIGN KEY(recipe) REFERENCES recipe(id) ON DELETE CASCADE ON UPDATE CASCADE"
      + ", FOREIGN KEY(ingredient) REFERENCES ingredient(id) ON DELETE CASCADE ON UPDATE CASCADE)";
  public static final String AUTHENTICATION = "authentication(id TEXT, password TEXT"
      + ", PRIMARY KEY(id), FOREIGN KEY(id) REFERENCES user(id)"
      + "ON DELETE CASCADE ON UPDATE CASCADE)";
  public static final String MEAL = "meal(id TEXT, json TEXT, PRIMARY KEY(id))";
  public static final String USER_MEAL = "user_meal(user TEXT, meal TEXT, PRIMARY KEY(user, meal), "
      + "FOREIGN KEY(user) REFERENCES user(id) ON DELETE CASCADE ON UPDATE CASCADE,"
      + "FOREIGN KEY(meal) REFERENCES meal(id) ON DELETE CASCADE ON UPDATE CASCADE)";
  public static final String[] TABLE_SCHEMA = { USER,
      INGREDIENT, USER_INGREDIENT, RECIPE,
      RECIPE_INGREDIENT, AUTHENTICATION, MEAL, USER_MEAL };
  public static final String[] TABLES = { "user",
      "ingredient", "user_ingredient", "recipe",
      "recipe_ingredient", "authentication", "meal",
      "user_meal" };
  public static final int ID_IDX = 1;
  public static final int NAME_IDX = 2;
  public static final int INGREDIENT_IDX = 2;
  public static final int INGREDIENT_QTY_IDX = 3;
  public static final int PRICE_IDX = 3;
  public static final int STORAGE_IDX = 4;
  public static final int EXP_IDX = 5;

  public static final int RECIPE_TEXT_IDX = 3;
  public static final int QTY_IDX = 3;
  private final UserDB users;
  private final IngredientDB ingredients;
  private final RecipeDB recipes;
  private final MealDB meals;

  public DBLink(String db) throws ClassNotFoundException,
      SQLException {
    Class.forName("org.sqlite.JDBC");
    String urlToDb = "jdbc:sqlite:" + db;
    conn = DriverManager.getConnection(urlToDb);
    init();
    this.users = new UserDBLink(conn, this);
    this.ingredients = new IngredientsDBLink(conn, this);
    this.recipes = new RecipeDBLink(conn, this);
    this.meals = new MealDBLink(conn, this);
  }

  public void init() {

    try (Statement stat = conn.createStatement()) {
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String create = "CREATE TABLE IF NOT EXISTS ";
    for (String s : TABLE_SCHEMA) {
      // System.out.println(s);
      String schema = create + s;
      execute(schema);
    }

  }

  public void clearDataBase() {
    for (int i = TABLES.length - 1; i >= 0; i--) {
      removeTable(TABLES[i]);
    }
    init();
  }

  private void removeTable(String name) {
    String sql = "DROP TABLE IF EXISTS ";
    try (Statement stat = conn.createStatement()) {
      String drop = sql + name;
      stat.executeUpdate(drop);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void importAllRecipes(String dir) {
    try {
      Files.walk(Paths.get(dir)).forEach(filePath -> {
        if (Files.isRegularFile(filePath)) {
          importRecipe(new File(filePath.toString()));
        }
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private void execute(String sql) {
    try (Statement stat = conn.createStatement()) {
      stat.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void importRecipe(File file) {
    try (CSVReader reader = new CSVReader(file)) {
      String[] line;
      String recipeName = "INSERT OR IGNORE INTO recipe VALUES (?,?,?)";
      line = reader.readLine();
      if (line.length < 3) {
        System.out.println("ERROR: Bad CSV format");
        return;
      }
      StringBuilder instructions = new StringBuilder();
      instructions.append(line[RECIPE_TEXT_IDX - 1]);
      for (int i = RECIPE_TEXT_IDX; i < line.length; i++) {
        instructions.append("," + line[i]);
      }
      String recipe = line[NAME_IDX - 1];
      try (PreparedStatement prep = conn.prepareStatement(recipeName)) {
        prep.setString(ID_IDX, line[ID_IDX - 1]);
        prep.setString(NAME_IDX, recipe);
        prep.setString(RECIPE_TEXT_IDX,
                       instructions.toString());
        prep.addBatch();
        prep.executeBatch();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String sql = "INSERT OR IGNORE INTO recipe_ingredient VALUES (?,?, ?)";
      try (PreparedStatement prep = conn.prepareStatement(sql)) {
        while ((line = reader.readLine()) != null) {
          if (line.length != 3) {
            System.out.println("ERROR: Bad CSV format");
            return;
          }
          if (!this.ingredients.hasIngredient(line[INGREDIENT_IDX - 1])) {
            System.out.println("Warning: ingredient "
                + line[INGREDIENT_IDX - 1]
                + " does not exist. Aborting " + recipe);
            return;
          }
          prep.setString(ID_IDX, line[ID_IDX - 1]);
          prep.setString(NAME_IDX, line[NAME_IDX - 1]);
          prep.setString(QTY_IDX, line[QTY_IDX - 1]);
          prep.addBatch();
          prep.executeBatch();
        }
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.out.println(e.getMessage());
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void importIngredients(File file) {
    try (CSVReader reader = new CSVReader(file)) {
      String[] line;
      String query = "INSERT OR IGNORE INTO ingredient VALUES (?,?,?,?,?)";
      try (PreparedStatement prep = conn.prepareStatement(query)) {
        while ((line = reader.readLine()) != null) {
          if (line.length != 5) {
            for (String s : line) {
              System.out.println(s);
            }
            System.out.println("ERROR: Bad CSV format");
            return;
          }
          prep.setString(ID_IDX, line[ID_IDX - 1].trim());
          prep.setString(NAME_IDX,
                         line[NAME_IDX - 1].trim());
          prep.setString(PRICE_IDX,
                         line[PRICE_IDX - 1].trim());
          prep.setString(STORAGE_IDX,
                         line[STORAGE_IDX - 1].trim());
          prep.setString(EXP_IDX, line[EXP_IDX - 1].trim());
          prep.addBatch();
        }
        prep.executeBatch();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void importRecipeIngredients(File file) {
    try (CSVReader reader = new CSVReader(file)) {
      String[] line;
      String query = "INSERT INTO recipe_ingredient VALUES (?,?)";
      try (PreparedStatement prep = conn.prepareStatement(query)) {
        while ((line = reader.readLine()) != null) {
          if (line.length < 2) {
            System.out.println("ERROR: Bad CSV format");
          }
          prep.setString(ID_IDX, line[ID_IDX - 1]);
          prep.setString(NAME_IDX, line[NAME_IDX - 1]);
          prep.addBatch();
        }
        prep.executeBatch();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void importAllUsers(String dir) {
    try {
      Files.walk(Paths.get(dir)).forEach(filePath -> {
        if (Files.isRegularFile(filePath)) {
          importUser(new File(filePath.toString()));
        }
      });
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void importUser(File file) {
    try (CSVReader reader = new CSVReader(file)) {
      String[] line = reader.readLine();
      if (line.length != 2) {
        System.out.println("ERROR: Bad CSV Format");
        return;
      }
      String name = line[0];
      String uid = line[1];
      String[] cuisines = reader.readLine();

      List<Ingredient> ingredients = new ArrayList<Ingredient>();
      while ((line = reader.readLine()) != null) {
        if (line.length != 2) {
          System.out.println("ERROR: Bad CSV Format");
          return;
        }
        String i = line[0];
        double amt = Double.parseDouble(line[1]);
        Ingredient ingred = new Ingredient(i, amt, this);
        ingred.setDateCreated(LocalDateTime.now());
        ingredients.add(ingred);
      }
      Person p = new User(name, uid, ingredients);
      this.users.addPerson(p);
      p.setCuisines(Arrays.asList(cuisines));
      this.users.updatePersonCuisines(p);
      this.users.setPersonPassword(uid, "password");
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      System.out.println("ERROR: file " + file.getPath()
          + " does not exist");
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void clearCache() {
    this.ingredients.clearCache();
    this.recipes.clearCache();
  }

  @BeforeClass
  @Override
  public UserDB users() {
    return this.users;
  }

  @Override
  public IngredientDB ingredients() {
    return this.ingredients;
  }

  @Override
  public RecipeDB recipes() {
    return this.recipes;
  }

  @Override
  public MealDB meals() {
    return this.meals;
  }

}
