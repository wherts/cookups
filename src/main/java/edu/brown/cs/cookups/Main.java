package edu.brown.cs.cookups;

import java.sql.SQLException;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import edu.brown.cs.cookups.db.DBLink;

public class Main {
  private String[] args;

  /** The main constructor.
   * @param args - the command line arguments
   * @throws SQLException
   * @throws ClassNotFoundException */
  public static void main(String[] args) throws ClassNotFoundException,
      SQLException {
    new Main(args).run();
  }

  /** @param args */
  private Main(String[] args) {
    this.args = args;
  }

  /** @throws SQLException
   * @throws ClassNotFoundException */
  private void run() throws ClassNotFoundException, SQLException {
    OptionParser parser = new OptionParser();
    OptionSpec<String> argv = parser.nonOptions();

    OptionSet options = null;
    try {
      options = parser.parse(args);
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
      System.exit(1);
    }

    List<String> parsedArgs = options.valuesOf(argv);
    if (parsedArgs.size() != 1) {
      System.err.println("ERROR: usage ./run <db>");
      System.exit(1);
    }
    String db = parsedArgs.get(0);

    URLHandler gui = new URLHandler(new DBLink(db));
    gui.runSparkServer();
  }
}
