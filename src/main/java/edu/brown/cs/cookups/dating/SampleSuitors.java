package edu.brown.cs.cookups.dating;

import java.sql.SQLException;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.PersonManager;

public interface SampleSuitors {   
  static void createSuitors(PersonManager people) throws SQLException {
    Person anna = people.getPersonById("ak@brown.edu");
    Person sean = people.getPersonById("bond007@brown.edu");
    Person daniel = people.getPersonById("realBond@brown.edu");
    Person halle = people.getPersonById("berrydelicious@brown.edu");
    Person albie = people.getPersonById("albert_brown@brown.edu");
    Person taylor = people.getPersonById("taylor_derosa@brown.edu");
    Person grant = people.getPersonById("grant_gustafson@brown.edu");
    Person wes = people.getPersonById("wesley_herts@brown.edu");
    
    Suitor annaS = new Suitor.Builder(anna).setStraight().setGender(42).build();
    Suitor seanS = new Suitor.Builder(sean).setGay().setGender(-80).build();
    Suitor danielS = new Suitor.Builder(daniel).setStraight().setGender(-100).build();
    Suitor halleS = new Suitor.Builder(halle).setStraight().setBi().setGay().setGender(69).build();
    Suitor albieS = new Suitor.Builder(albie).setStraight().setGender(-65).build();
    Suitor taylorS = new Suitor.Builder(taylor).setStraight().setBi().setGay().setGender(55).build();
    Suitor grantS = new Suitor.Builder(grant).setStraight().setGender(-80).build();
    Suitor wesS = new Suitor.Builder(wes).setStraight().setBi().setGay().setGender(-70).build();
    
    people.cacheSuitor(annaS, seanS, danielS, halleS, albieS, taylorS, grantS, wesS);
  }
}
