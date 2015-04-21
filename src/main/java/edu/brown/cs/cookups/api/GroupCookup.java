package edu.brown.cs.cookups.api;

import java.util.Date;

public interface GroupCookup {

  public int makeCookup(String id);

  public boolean setUserAsHost(String id);

  public boolean addOtherUserToCookup(String hostID,
      String userID);

  public boolean setSchedule(Date dateFront, Date dateLast);

}
