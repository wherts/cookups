package edu.brown.cs.cookups;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.dating.DateMatcher;
import edu.brown.cs.cookups.dating.Suitor;
import edu.brown.cs.cookups.dating.Suitor.Builder;
import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class DateMatcherTest {
  private Suitor gayMan1, gayMan2, straightMan1, straightMan2, biMan1, biMan2,
      queerMan1, queerMan2, gayWoman1, gayWoman2, straightWoman1,
      straightWoman2, biWoman1, biWoman2, queerWoman1, queerWoman2;

  @Before
  public void initialize() {
    //MAKE MEN
    gayMan1 = new Suitor.Builder(new User("gayMan1", "gm1", null)).setMale()
        .setGay().build();
    gayMan2 = new Suitor.Builder(new User("gayMan2", "gm2", null)).setMale()
        .setGay().build();
    straightMan1 = new Suitor.Builder(new User("straightMan1", "sm1", null)).setMale()
        .setStraight().build();
    straightMan2 = new Suitor.Builder(new User("straightMan2", "sm2", null)).setMale()
        .setStraight().build();
    biMan1 = new Suitor.Builder(new User("biMan1", "bm1", null)).setMale()
        .setBi().build();
    biMan2 = new Suitor.Builder(new User("biMan2", "bm2", null)).setMale()
        .setBi().build();
    queerMan1 = new Suitor.Builder(new User("queerMan1", "qm1", null)).setMale().build();
    queerMan2 = new Suitor.Builder(new User("queerMan2", "qm2", null)).setMale().build();

    //MAKE WOMEN
    gayWoman1 = new Suitor.Builder(new User("gayWoman1", "gw1", null)).setFemale()
        .setGay().build();
    gayWoman2 = new Suitor.Builder(new User("gayWoman2", "gw2", null)).setFemale()
        .setGay().build();
    straightWoman1 = new Suitor.Builder(new User("straightWoman1", "sw1", null)).setFemale()
        .setStraight().build();
    straightWoman2 = new Suitor.Builder(new User("straightWoman2", "sw2", null)).setFemale()
        .setStraight().build();
    biWoman1 = new Suitor.Builder(new User("biWoman1", "bw1", null)).setFemale()
        .setBi().build();
    biWoman2 = new Suitor.Builder(new User("biWoman2", "bw2", null)).setFemale()
        .setBi().build();
    queerWoman1 = new Suitor.Builder(new User("queerWoman1", "qw1", null)).setFemale().build();
    queerWoman2 = new Suitor.Builder(new User("queerWoman2", "qw2", null)).setFemale().build();
  }
  
  @Test
  public void builderTest() {
    Suitor s1 = new Suitor.Builder(new User("All", "all", null)).
        setBi().setGay().setStraight().setFemale().setPlatonic().build();
    assertTrue(s1.getBi());
    assertTrue(s1.getGay());
    assertTrue(s1.getStraight());
    assertTrue(s1.isFemale());
    assertTrue(s1.isPlatonic());
    assertFalse(s1.getQueer());
    assertFalse(s1.isRomantic());
    assertFalse(s1.isMale());
    
    Suitor s2 = new Suitor.Builder(new User("Nothing", "nothing", null)).build();
    assertFalse(s2.getBi());
    assertFalse(s2.getGay());
    assertFalse(s2.getStraight());
    assertFalse(s2.isFemale());
    assertFalse(s2.isPlatonic());
    assertTrue(s2.getQueer());
    assertTrue(s2.isRomantic());
    assertTrue(s2.isMale());
  }

  @Test
  public void equalsTest() {
    assertTrue(gayMan1.equals(gayMan1));
  }
  
  @Test
  public void noSelfMatch() {
    List<Suitor> suitors = new ArrayList<>();
    suitors.add(gayMan1);
    suitors.add(gayMan2);
    List<Person> matches = DateMatcher.match(suitors, gayMan1);
    
    assertTrue(matches.size() == 1);
    
    assertTrue(matches.get(0).id().equals("gm2"));
  }
  
  @Test
  public void matchStraight() {
    List<Suitor> suitors = new ArrayList<>();
    //THE PERSON TO MATCH (should not be added)
    suitors.add(straightMan1);
    
    //TO MATCH WITH
    suitors.add(straightWoman1);
    suitors.add(straightWoman2);
    suitors.add(biWoman1);
    suitors.add(biWoman2);
    
    //TO NOT MATCH WITH
    suitors.add(straightMan2);
    suitors.add(gayMan1);
    suitors.add(gayMan2);
    suitors.add(queerMan1);
    suitors.add(queerMan2);
    suitors.add(biMan1);
    suitors.add(biMan2);
    suitors.add(gayWoman1);
    suitors.add(gayWoman2);

    List<Person> matches = DateMatcher.match(suitors, straightMan1);
    assertTrue(straightMan1.compatability(straightMan2) == 0);
    
    assertTrue(matches.size() == 4);
    
    assertTrue(matches.contains(straightWoman1.person()));
    assertTrue(matches.contains(straightWoman2.person()));
    assertTrue(matches.contains(biWoman1.person()));
    assertTrue(matches.contains(biWoman2.person()));
  }
  
  @Test
  public void matchGay() {
    List<Suitor> suitors = new ArrayList<>();
    suitors.add(gayMan1);
    
    suitors.add(gayMan2);
    suitors.add(queerMan1);
    suitors.add(queerMan2);
    suitors.add(biMan1);
    suitors.add(biMan2);

    suitors.add(straightMan1);
    suitors.add(straightMan2);
    suitors.add(straightWoman1);
    suitors.add(straightWoman2);
    suitors.add(gayWoman1);
    suitors.add(gayWoman2);
    suitors.add(queerWoman1);
    suitors.add(queerWoman2);

    List<Person> matches = DateMatcher.match(suitors, gayMan1);
    
    assertTrue(matches.size() == 5);
    
    assertTrue(matches.contains(gayMan2.person()));
    assertTrue(matches.contains(queerMan1.person()));
    assertTrue(matches.contains(queerMan2.person()));
    assertTrue(matches.contains(biMan1.person()));
    assertTrue(matches.contains(biMan2.person()));
  }

  @Test
  public void matchBi() {
    List<Suitor> suitors = new ArrayList<>();
    //THE PERSON TO MATCH (should not be added)
    suitors.add(biMan1);
    
    //TO MATCH WITH
    suitors.add(biMan2);
    suitors.add(straightWoman1);
    suitors.add(straightWoman2);
    suitors.add(biWoman1);
    suitors.add(biWoman2);
    suitors.add(gayMan1);
    suitors.add(gayMan2);
    suitors.add(queerMan1);
    suitors.add(queerMan2);
    suitors.add(queerWoman1);
    suitors.add(queerWoman2);
    
    //TO NOT MATCH WITH
    suitors.add(straightMan1);
    suitors.add(straightMan2);
    suitors.add(gayWoman1);
    suitors.add(gayWoman2);

    List<Person> matches = DateMatcher.match(suitors, biMan1);
    
    assertTrue(matches.size() == 11);

    assertTrue(matches.contains(biMan2.person()));
    assertTrue(matches.contains(gayMan1.person()));
    assertTrue(matches.contains(gayMan2.person()));
    assertTrue(matches.contains(queerMan1.person()));
    assertTrue(matches.contains(queerMan2.person()));
    assertTrue(matches.contains(straightWoman1.person()));
    assertTrue(matches.contains(straightWoman2.person()));
    assertTrue(matches.contains(biWoman1.person()));
    assertTrue(matches.contains(biWoman2.person()));
    assertTrue(matches.contains(queerWoman1.person()));
    assertTrue(matches.contains(queerWoman2.person()));
  }
  
  @Test
  public void matchQueer() {
    List<Suitor> suitors = new ArrayList<>();
    //THE PERSON TO MATCH (should not be added)
    suitors.add(queerMan1);

    //TO MATCH WITH
    suitors.add(queerMan2);
    suitors.add(biMan1);
    suitors.add(biMan2);
    suitors.add(gayMan1);
    suitors.add(gayMan2);
    suitors.add(queerWoman1);
    suitors.add(queerWoman2);
    suitors.add(biWoman1);
    suitors.add(biWoman2);
    suitors.add(straightWoman1);
    suitors.add(straightWoman2);
    
    //TO NOT MATCH WITH
    suitors.add(straightMan1);
    suitors.add(straightMan2);
    suitors.add(gayWoman1);
    suitors.add(gayWoman2);

    List<Person> matches = DateMatcher.match(suitors, queerMan1);
    
    assertTrue(matches.size() == 11);

    assertTrue(matches.contains(queerMan2.person()));
    assertTrue(matches.contains(biMan2.person()));
    assertTrue(matches.contains(gayMan1.person()));
    assertTrue(matches.contains(gayMan2.person()));
    assertTrue(matches.contains(straightWoman1.person()));
    assertTrue(matches.contains(straightWoman2.person()));
    assertTrue(matches.contains(biWoman1.person()));
    assertTrue(matches.contains(biWoman2.person()));
    assertTrue(matches.contains(queerWoman1.person()));
    assertTrue(matches.contains(queerWoman2.person()));
  }
}
