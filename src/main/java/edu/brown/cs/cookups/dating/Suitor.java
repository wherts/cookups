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

  public boolean isGay() {
    return gay;
  }

  public boolean isBi() {
    return bi;
  }

  public boolean isStraight() {
    return straight;
  }

  public boolean isQueer() {
    return queer;
  }

  public int calculateGay(Suitor suitor) {
    int toReturn = 0;
    if (!(this.isGay() || suitor.isGay()) || !sameSex(this, suitor)) {
      return 0;
    } else if ((this.isGay() && (suitor.isGay() || suitor.isBi()))
        && sameSex(this, suitor)) {
      if (this.getGender() - suitor.getGender() == 0) {
        return 200;
      } else {
        return (200 - Math.abs(this.getGender() - suitor.getGender()));  
      }
    }
    return toReturn;
  }
  
  public int calculateBi(Suitor suitor) {
    if (!(this.isBi() || suitor.isBi())) {
      return 0;
    } else if (this.isBi()) {
      if (suitor.isBi()) {
        return 200;
      }
    }
    return 0;
  }
   
  public int calculateStraight(Suitor suitor) {
    if (!this.isStraight() || !suitor.isStraight()) {
      return 0;
    } else if ((this.isStraight() && (suitor.isStraight()
        || suitor.isBi()))
        && !sameSex(this, suitor)) {
      return Math.abs(suitor.getGender() - this.getGender());
    }
    return 0;
  }
  
  public int calculateQueer(Suitor suitor) {
    int toReturn = 0;
    if (this.isQueer()) {
      if (suitor.isQueer()) {
        toReturn += 200;
      }
      if (suitor.isBi()) {
        toReturn += 100;
      }
      if (suitor.isGay() && sameSex(this, suitor)) {
        toReturn += .3 * (200 - Math.abs((this.getGender() - suitor.getGender())));
      }
      if (suitor.isStraight() && !sameSex(this, suitor)) {
        toReturn += .3 * Math.abs((this.getGender() - suitor.getGender()));
      }
    }
    return toReturn;
  }
  
  public int getGender() {
    return gender;
  }
  
  public boolean compatability(Suitor suitor) {
    return oneWayCompatability(suitor, this) || oneWayCompatability(this, suitor);
  }
  
  public boolean oneWayCompatability(Suitor suitor1, Suitor suitor2) {
    if (sameSex(suitor1, suitor2)) { //SAME GENDER
      if (suitor1.isGay() && (suitor2.isBi() || suitor2.isGay() || suitor2.isQueer())) {
        return true;
      }
      if (suitor1.isBi() && (suitor2.isBi() || suitor2.isGay() || suitor2.isQueer())) {//BOTH getBi()
        return true;
      }
      if (suitor1.isQueer() && (suitor2.isBi() || suitor2.isGay() || suitor2.isQueer())) {//suitor1 getQueer()
        return true;
      }
    }
    if (!sameSex(suitor1, suitor2)) {//DIFFERENT GENDERS
      if ((suitor1.isStraight() || suitor1.isBi()) && (suitor2.isBi() || suitor2.isStraight())) {//BOTH getStraight() OR ONE getBi() OR BOTH getBi()
        return true;
      }
      if (suitor1.isQueer() && (suitor2.isBi() || suitor2.isStraight() || suitor2.isQueer())) {//suitor1 getQueer()
        return true;
      }
    }
    return false;
  }
  
  public boolean sameSex(Suitor suitor1, Suitor suitor2) {
    return (suitor1.isFemale() == suitor2.isFemale() 
        || suitor1.isMale() == suitor2.isMale());
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
