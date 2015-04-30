package edu.brown.cs.cookups.dating;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.brown.cs.cookups.person.Person;
import edu.brown.cs.cookups.person.User;

public class DateRankingTest {
  private Suitor genderNeutral1 = new Suitor.Builder(new User("neutralAll",
      "n1", null)).setGender(0).setBi().setGay().setStraight().build();
  private Suitor genderNeutral2 = new Suitor.Builder(new User("neutralQueer",
      "n2", null)).setGender(0).build();

  @Test
 public void gayTest() {
 //MAKE WOMEN
 Suitor gayWoman = new Suitor.Builder(new User("gayWoman", "gw", null))
 .setGender(1).setGay().build();

 Suitor gayWoman1 = new Suitor.Builder(new User("gayWoman1", "gw1", null))
 .setGender(100).setGay().build();
 Suitor gayWoman2 = new Suitor.Builder(new User("gayWoman2", "gw2", null))
 .setGender(70).setGay().build();
 Suitor gayWoman3 = new Suitor.Builder(new User("gayWoman3", "gw3", null))
 .setGender(10).setGay().build();

 Suitor biWoman1 = new Suitor.Builder(new User("biWoman1", "bw1", null))
 .setGender(100).setBi().build();
 Suitor biWoman2 = new Suitor.Builder(new User("biWoman2", "bw2",
 null)).setGender(70)
 .setBi().build();
 Suitor biWoman3 = new Suitor.Builder(new User("biWoman3", "bw3",
 null)).setGender(10)
 .setBi().build();

 Suitor queerWoman1 = new Suitor.Builder(new User("queerWoman1", "qw1", null))
 .setGender(100).build();
 Suitor queerWoman2 = new Suitor.Builder(new User("queerWoman2", "qw2", null))
 .setGender(70).build();
 Suitor queerWoman3 = new Suitor.Builder(new User("queerWoman3", "qw3", null))
 .setGender(10).build();

 //MAKE MEN
 Suitor gayMan = new Suitor.Builder(new User("gayMan", "gm", null))
 .setGender(-1).setGay().build();

 Suitor gayMan1 = new Suitor.Builder(new User("gayMan1", "gm1", null))
 .setGender(-100).setGay().build();
 Suitor gayMan2 = new Suitor.Builder(new User("gayMan2", "gm2", null))
 .setGender(-70).setGay().build();
 Suitor gayMan3 = new Suitor.Builder(new User("gayMan3", "gm3", null))
 .setGender(-10).setGay().build();

 Suitor biMan1 = new Suitor.Builder(new User("biMan1", "bm1", null))
 .setGender(-100).setBi().build();
 Suitor biMan2 = new Suitor.Builder(new User("biMan2", "bm2",
 null)).setGender(-70)
 .setBi().build();
 Suitor biMan3 = new Suitor.Builder(new User("biMan3", "bm3",
 null)).setGender(-10)
 .setBi().build();

 Suitor queerMan1 = new Suitor.Builder(new User("queerMan1", "qm1", null))
 .setGender(-100).build();
 Suitor queerMan2 = new Suitor.Builder(new User("queerMan2", "qm2", null))
 .setGender(-70).build();
 Suitor queerMan3 = new Suitor.Builder(new User("queerMan3", "qm3", null))
 .setGender(-10).build();

 List<Suitor> suitors = new ArrayList<>();
 suitors.add(gayWoman);
 suitors.add(gayWoman1);
 suitors.add(gayWoman2);
 suitors.add(gayWoman3);
 suitors.add(queerWoman1);
 suitors.add(queerWoman2);
 suitors.add(queerWoman3);
 suitors.add(biWoman1);
 suitors.add(biWoman2);
 suitors.add(biWoman3);

 suitors.add(gayMan);
 suitors.add(gayMan1);
 suitors.add(gayMan2);
 suitors.add(gayMan3);
 suitors.add(queerMan1);
 suitors.add(queerMan2);
 suitors.add(queerMan3);
 suitors.add(biMan1);
 suitors.add(biMan2);
 suitors.add(biMan3);
 suitors.add(genderNeutral1);
 suitors.add(genderNeutral2);

 // for (Suitor match : suitors) {
 // System.out.println(match.person().name() + ": " +
 (gayMan.sexAppeal(match)+ match.sexAppeal(gayMan)));
 // }

 List<Person> matches = DateMatcher.match(suitors, gayMan);

 System.out.println("GAY MAN:");
 for (Person match : matches) {
 System.out.println(match.name());
 }

 matches = DateMatcher.match(suitors, gayWoman);
 System.out.println("GAY WOMAN:");

 for (Person match : matches) {
 System.out.println(match.name());
 }
 }

  @Test
 public void biTest() {
 //MAKE WOMEN
 Suitor biWoman = new Suitor.Builder(new User("biWoman", "bw", null))
 .setGender(1).setBi().build();

 Suitor gayWoman1 = new Suitor.Builder(new User("gayWoman1", "gw1", null))
 .setGender(100).setGay().build();
 Suitor gayWoman2 = new Suitor.Builder(new User("gayWoman2", "gw2", null))
 .setGender(70).setGay().build();
 Suitor gayWoman3 = new Suitor.Builder(new User("gayWoman3", "gw3", null))
 .setGender(10).setGay().build();

 Suitor biWoman1 = new Suitor.Builder(new User("biWoman1", "bw1", null))
 .setGender(100).setBi().build();
 Suitor biWoman2 = new Suitor.Builder(new User("biWoman2", "bw2",
 null)).setGender(70)
 .setBi().build();
 Suitor biWoman3 = new Suitor.Builder(new User("biWoman3", "bw3",
 null)).setGender(10)
 .setBi().build();

 Suitor queerWoman1 = new Suitor.Builder(new User("queerWoman1", "qw1", null))
 .setGender(100).build();
 Suitor queerWoman2 = new Suitor.Builder(new User("queerWoman2", "qw2", null))
 .setGender(70).build();
 Suitor queerWoman3 = new Suitor.Builder(new User("queerWoman3", "qw3", null))
 .setGender(10).build();

 //MAKE MEN
 Suitor biMan = new Suitor.Builder(new User("biMan", "bm", null))
 .setGender(-1).setBi().build();

 Suitor gayMan1 = new Suitor.Builder(new User("gayMan1", "gm1", null))
 .setGender(-100).setGay().build();
 Suitor gayMan2 = new Suitor.Builder(new User("gayMan2", "gm2", null))
 .setGender(-70).setGay().build();
 Suitor gayMan3 = new Suitor.Builder(new User("gayMan3", "gm3", null))
 .setGender(-10).setGay().build();

 Suitor biMan1 = new Suitor.Builder(new User("biMan1", "bm1", null))
 .setGender(-100).setBi().build();
 Suitor biMan2 = new Suitor.Builder(new User("biMan2", "bm2",
 null)).setGender(-70)
 .setBi().build();
 Suitor biMan3 = new Suitor.Builder(new User("biMan3", "bm3",
 null)).setGender(-10)
 .setBi().build();

 Suitor queerMan1 = new Suitor.Builder(new User("queerMan1", "qm1", null))
 .setGender(-100).build();
 Suitor queerMan2 = new Suitor.Builder(new User("queerMan2", "qm2", null))
 .setGender(-70).build();
 Suitor queerMan3 = new Suitor.Builder(new User("queerMan3", "qm3", null))
 .setGender(-10).build();

 List<Suitor> suitors = new ArrayList<>();
 suitors.add(gayWoman1);
 suitors.add(gayWoman2);
 suitors.add(gayWoman3);
 suitors.add(queerWoman1);
 suitors.add(queerWoman2);
 suitors.add(queerWoman3);
 suitors.add(biWoman1);
 suitors.add(biWoman2);
 suitors.add(biWoman3);

 suitors.add(gayMan1);
 suitors.add(gayMan2);
 suitors.add(gayMan3);
 suitors.add(queerMan1);
 suitors.add(queerMan2);
 suitors.add(queerMan3);
 suitors.add(biMan1);
 suitors.add(biMan2);
 suitors.add(biMan3);
 suitors.add(genderNeutral1);
 suitors.add(genderNeutral2);

 // for (Suitor match : suitors) {
 // System.out.println(match.person().name() + ": " +
 (gayMan.sexAppeal(match)+ match.sexAppeal(gayMan)));
 // }

 List<Person> matches = DateMatcher.match(suitors, biMan);

 // System.out.println("BI MAN:");
 // for (Person match : matches) {
 // System.out.println(match.name());
 // }
 //
 // matches = DateMatcher.match(suitors, biWoman);
 // System.out.println("BI WOMAN:");
 //
 // for (Person match : matches) {
 // System.out.println(match.name());
 // }
 }
}
