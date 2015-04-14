package edu.brown.cs.kwhan.stars;

/**
 * Point in three dimensional space.
 *
 * @author Katie Han
 * @version 1.0 Build Feb 11, 2015.
 */
public abstract class Point {
  protected double[] coordinates;

  /**
   * Accessor for coordinates.
   *
   * @param i index of coordinate to get, 0 maps to x, 1 to y, and 2 to z
   * @return appropriate coordinate in double
   * @throws IndexOutOfBoundsException if coord array size greater than # dimensions
   */
  public double getCoordinate(int i) {
    if (i > coordinates.length - 1) {
      throw new IndexOutOfBoundsException();
    }
    return coordinates[i];
  }

  /**
   * Returns the distance between inputted Point and this Point.
   *
   * @param p Point to measure distance to
   * @return distance in double
   */
  public double distanceTo(Point p) {
    double diffSum = 0;
    for (int i = 0; i < coordinates.length; i++) {
      diffSum += Math.pow(coordinates[i] - p.getCoordinate(i), 2);
    }
    return Math.sqrt(diffSum);
  }

  /**
   * Translates Point into String.
   *
   * @return String representing the point
   */
  @Override
  public String toString() {
    if (coordinates.length == 0) {
      return "Point DNE.";
    }
    String toReturn = "(";
    int i = 0;
    while (i < coordinates.length - 1) {
      toReturn = String.format("%s%f, ", toReturn, coordinates[i]);
      i++;
    }
    toReturn = String.format("%s%f)", toReturn, coordinates[i]);
    return toReturn;
  }

  /**
   * Checks if inputted object equals this Point.
   *
   * @param other Object to compare to
   * @return true if equal, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Point) {
      Point o = (Point) other;
      boolean toReturn = true;
      for (int i = 0; i < coordinates.length; i++) {
        toReturn = toReturn && (o.getCoordinate(i) == coordinates[i]);
      }
      return toReturn;
    } else {
      return false;
    }
  }

  /**
   * Calculates hash code for Point.
   *
   * @return hash code in int
   */
  @Override
  public int hashCode() {
    int hash = 5;
    int prime = 7;
    hash = prime * hash + coordinates.hashCode();
    return hash;
  }
}
