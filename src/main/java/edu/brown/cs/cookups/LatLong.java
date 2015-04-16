package edu.brown.cs.cookups;

/**
 * The LatLong class, which holds a latitude and longitude
 * of a point.
 * @author ajb7
 */
public class LatLong extends Point {

  /**
   * Constructor for a LatLong.
   * @param lat the latitude of the point
   * @param lng the longitude of the point
   */
  public LatLong(double lat, double lng) {
    super.coordinates = new double[2];
    super.coordinates[0] = lat;
    super.coordinates[1] = lng;
  }

  /**
   * Getter for latitude.
   * @return the latitude
   */
  public double getLat() {
    return super.getCoordinate(0);
  }

  /**
   * Getter for longitude.
   * @return the longitude
   */
  public double getLong() {
    return super.getCoordinate(1);
  }

}
