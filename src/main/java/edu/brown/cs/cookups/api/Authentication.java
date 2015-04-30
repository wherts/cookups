package edu.brown.cs.cookups.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import edu.brown.cs.cookups.db.DBManager;

public class Authentication {
  private static final String CHARS =
      "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final int TOKEN_LENGTH = 9;
  private DBManager db;
  private Map<String, String> tokens;

  public Authentication(DBManager db) {
    this.db = db;
    this.tokens = new HashMap<String, String>();
  }

  public boolean authenticatePassword(String id,
      String password) {
    String dbPassword = db.users().getPersonPassword(id);
    if (dbPassword.equals("")) {
      return false;
    }
    if (dbPassword.equals(password)) {
      return true;
    }
    return false;
  }

  public String getToken(String id) {
    String token = getRandomString();
    this.tokens.put(id, token);
    return token;
  }

  public boolean setPassword(String id, String password) {
    if (!db.users().hasPersonByID(id)) {
      return false;
    }
    db.users().setPersonPassword(id, password);
    return true;
  }

  public boolean authenticate(String id, String token) {
    if (id == null || token == null) {
      return false;
    }
    String realToken = this.tokens.get(id);
    if (realToken == null) {
      return false;
    }
    if (realToken.equals(token)) {
      return true;
    }
    return false;
  }

  private String getRandomString() {
    int length = CHARS.length();
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < TOKEN_LENGTH; i++) {
      sb.append(CHARS.charAt(random.nextInt(length)));
    }
    return sb.toString();
  }
}
