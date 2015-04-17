package edu.brown.cs.cookups.dating;

import edu.brown.cs.cookups.person.Person;


public class Suitor {
  private boolean queer, gay, straight, bi, isFemale, isPlatonic;
  private Person person;
  
  public static class Builder {
    private Person person;
    private boolean gay, straight, bi, queer, isFemale, isPlatonic;
    
    public Builder(Person p) {
      this.person = p;
      this.gay = false;
      this.straight = false;
      this.bi = false;
      this.isPlatonic = false;
      this.queer = false;
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
    
    public Builder setFemale() {
      this.isFemale = true;
      return this;
    }
    
    public Builder setMale() {
      this.isFemale = false;
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
    this.isFemale = b.isFemale;
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
    return !isFemale;
  }
  
  public boolean isFemale() {
    return isFemale;
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
  
  public int compatability(Suitor suitor) {
    return oneWayCompatability(suitor, this) + oneWayCompatability(this, suitor);
  }
  
  public int oneWayCompatability(Suitor suitor1, Suitor suitor2) {
    int compatability = 0;
    if ((suitor1.isFemale() == suitor2.isFemale())) { //SAME GENDER
      if (suitor1.getGay() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {
        compatability++;
      }
      if (suitor1.getBi() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {//BOTH BI
        compatability++;
      }
      if (suitor1.getQueer() && (suitor2.getBi() || suitor2.getGay() || suitor2.getQueer())) {//suitor1 QUEER
        compatability++;
      }
    }
    if (suitor1.isFemale() != suitor2.isFemale()) {//DIFFERENT GENDERS
      if ((suitor1.getStraight() || suitor1.getBi()) && (suitor2.getBi() || suitor2.getStraight())) {//BOTH STRAIGHT OR ONE BI OR BOTH BI
        compatability++;
      }
      if (suitor1.getQueer() && (suitor2.getBi() || suitor2.getStraight() || suitor2.getQueer())) {//suitor1 QUEER
        compatability++;
      }
    }
    return compatability;
  }

  @Override
  public boolean equals(Object o) {
    return person.id().equals(((Suitor) o).person().id());
  }
}
