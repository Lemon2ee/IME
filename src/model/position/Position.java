package model.position;

import java.util.ArrayList;

/**
 * The interface represents a position in a xy-coordinate system. Including finding the
 * clustering seed for the current position.
 */
public interface Position {
  /**
   * Get the x-axis value of the current position.
   *
   * @return the x-axis value as an int
   */
  int getX();

  /**
   * Get the y-axis value of the current position.
   *
   * @return the y-axis value as an int
   */
  int getY();

  /**
   * Find the index of the closest seed for the current point to cluster to.
   *
   * @param seeds the array of randomly generated seeds as array of Position2d
   * @return the index of the seed for the current pixel to cluster to as int
   */
  int getClusterSeed(ArrayList<Position> seeds);
}
