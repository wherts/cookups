package edu.brown.cs.cookups.dating;

import edu.brown.cs.cookups.person.Person;


public class Suitor {
  private boolean queer, gay, straight, bi, isPlatonic;
  private Person person;
  private int gender;
  
  public static class Builder {
    private Person person;
    private boolean gay, straight, bi, queer, isPlatonic;
    private int gender;
    
    public Builder(Person p) {
      this.person = p;
      this.gay = false;
      this.straight = false;
      this.bi = false;
      this.isPlatonic = false;
      this.queer = false;
      this.gender = 0;
    }
    
    public Builder setGay() {
      this.gay = true;
      return this;
    }
    
    public Builder setStraight() {
      this.straight = true;
      return this;
    }
    
    public Builder setBi() {
      this.bi = true;
      return this;
    }
    
    public Builder setGender(int gender) {
      assert((gender <= 100) && (gender >= -100));
      this.gender = gender;
      return this;
    }
    
    public Builder setPlatonic() {
      this.isPlatonic = true;
      return this;
    }
    
    public Suitor build() {
      this.queer = !(this.gay || this.straight || this.bi);
      return new Suitor(this);
    }
  }

  private Suitor(Builder b) {
    this.gay = b.gay;
    this.straight = b.straight;
    this.bi = b.bi;
    this.isPlatonic = b.isPlatonic;
    this.gender = b.gender;
    this.queer = b.queer;
    person = b.person;
  }

  public Person person() {
    return person;
  }

  public boolean isPlatonic() {
    return isPlatonic;
  }
  
  public boolean isRomantic() {
    return !isPlatonic;
  }
  
  public boolean isMale() {
    return (gender <= 0);
  }
  
  public boolean isFemale() {
    return (gender >= 0);
  }
  
  public boolean getGay() {
    return gay;
  }
  
  public boolean getBi() {
    return bi;
  }
   
  public boolean getStraight() {
    return straight;
  }
  
  public boolean getQueer() {
    return queer;
  }
  
  public int calculateGay(Suitor suitor) {
    if (!this.getGay() || !suitor.getGay()) {
      return 0;
    } else if (this.getGay() && suitor.getGay()
        || (this.getGay() && suitor.getBi())
        || (suitor.getGay() && this.getBi())) {
      if (this.isFemale() != suitor.isFemale()) {
        return 0;
      } else if (this.getGender() - suitor.getGender() == 0) {
        return 200;
      } else {
        return 200 - (this.getGender() - suitor.getGender());  
      }
    }
    return 0;
  }
  
  public int calculateBi(Suitor suitor) {
    if (!(this.getBi() && suitor.getBi())) {
      return 0;
    } else {
      return 200;
    }
  }
   
  public int calculateStraight(Suitor suitor) {
    if (!this.getStraight() || !suitor.getStraight()) {
      return 0;
    } else if (this.getStraight() && suitor.getStraight()
        || (this.getStraight() && suitor.getBi())
        || (suitor.getStraight() && this.getBi())) {
      if (this.isFemale() == suitor.isFemale()) {
        return 0;
      } else {
        return Math.abs(suitor.getGender() - this.getGender());
      }
    }
    return 0;
  }
  
  public int calculateQueer(Suitor suitor) {
    if (this.getQueer() && suitor.getQueer()) {
      return 200;
    }
    return 0;
  }
  
  public int getGender() {
    return gender;
  }
  
  public boolean compatability(Suitor suitor) {
    return oneWayCompatability(suitor, this) || oneWayCompatability(this, suitor);
  }
  
  public boolean oneWayCompatability(Suitor suitor1, Suitor suitor2) {
    if ((suitor1.isFemale() == suitor2.isFemale())) { //SAME GENDER
      if (suitor1.getGay() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {
        return true;
      }
      if (suitor1.getBi() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {//BOTH getBi()
        return true;
      }
      if (suitor1.getQueer() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {//suitor1 getQueer()
        return true;
      }
    }
    if (suitor1.isFemale() != suitor2.isFemale()) {//DIFFERENT GENDERS
      if ((suitor1.getStraight() || suitor1.getBi()) && (suitor2.getBi() || suitor2.getStraight())) {//BOTH getStraight() OR ONE getBi() OR BOTH getBi()
        return true;
      }
      if (suitor1.getQueer() && (suitor2.getBi() || suitor2.getStraight() || suitor2.getQueer())) {//suitor1 getQueer()
        return true;
      }
    }
    return false;
  }
  
  public int sexAppeal(Suitor suitor) {
    return calculateGay(suitor) + calculateStraight(suitor) + calculateBi(suitor)
        + calculateQueer(suitor);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    } else if (o == this) {
      return true;
    } else if (!(o instanceof Suitor)) {
      return false;
    }
    return person.id().equals(((Suitor) o).person().id());
  }
}
