package edu.brown.cs.cookups;

public class Suitor {
  private boolean gay, straight, bi, isFemale, isRomantic;
  private Person person;
  
  public Suitor(Person p) {
    person = p;
  }

  public Person person() {
    return person;
  }

  public boolean isRomantic() {
    return isRomantic;
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
    return !(gay || straight || bi);
  }
  
  public int compatability(Suitor suitor) {
    int compatability = 0;
    if ((suitor.isFemale() == this.isFemale())) { //SAME GENDER
      if (suitor.getGay() && this.getGay()) { //BOTH GAY
        compatability++;
      }
      if (suitor.getBi() && this.getBi()) {//BOTH BI
        compatability++;
      }
      if (suitor.getQueer() && (this.getBi() || this.getGay())) {//SUITOR QUEER
        compatability++;
      }
      if (this.getQueer() && (suitor.getBi() || suitor.getGay())) {//TOFIND QUEER
        compatability++;
      }
    }
    if (suitor.isFemale() == !this.isFemale()) {//DIFFERENT GENDERS
      if (suitor.getBi() && (this.getBi() || this.getStraight())) {//SUITOR BI
        compatability++;
      }
      if (this.getBi() && (suitor.getBi() || suitor.getStraight())) {//TOFIND BI
        compatability++;
      }
      if (suitor.getQueer() && (this.getBi() || this.getStraight() || this.getQueer())) {//SUITOR QUEER
        compatability++;
      }
      if (this.getQueer() && (suitor.getBi() || suitor.getStraight() || suitor.getQueer())) {//TOFIND QUEER
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
