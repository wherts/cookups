package edu.brown.cs.cookups.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader implements AutoCloseable {

  private BufferedReader file;

  public CSVReader(File file) throws FileNotFoundException {
    this.file = new BufferedReader(new FileReader(file));
  }

  public String[] readLine() throws IOException {
    String line = file.readLine();
    if (line != null) {
      return line.split(",");
    }
    return null;
  }

  @Override
  public void close() throws IOException {
    this.file.close();
  }

}
