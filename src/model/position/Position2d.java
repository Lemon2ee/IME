package model.position;

import java.util.ArrayList;

/**
 *
 */
public class Position2d implements Position{
  private final int x;
  private final int y;

  /**
   * Create a point with position in the 2d coordinate system.
   * @param x the x-axis value of the point as int
   * @param y the y-axis value of the point as int
   */
  public Position2d(int x, int y) {
    this.x = x;
    this.y = y;
  }
  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Position) {
      Position compare = (Position) obj;
      return compare.getX() == this.x && compare.getY() == this.y;
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  /**
   * Find the index of the closest seed for the current point to cluster to.
   *
   * @param seeds the array of randomly generated seeds as array of Position2d
   * @return the index of the seed for the current pixel to cluster to as int
   */
  @Override
  public int getClusterSeed(ArrayList<Position> seeds) {
    int seed = 0;
    double tempDist = this.getDistance(seeds.get(0));

    for (int i = 1; i < seeds.size(); i++) {
      double currDist = this.getDistance(seeds.get(i));
      if (currDist < tempDist) {
        seed = i;
        tempDist = currDist;
      }
    }

    return seed;
  }

  /**
   * Get the distance between two position points.
   *
   * @param that the position of another point
   * @return the distance between two points as double
   */
  private double getDistance(Position that) {
    double temp = Math.pow(that.getX() - this.getX(), 2) + Math.pow(that.getY() - this.getY(), 2);
    return Math.sqrt(temp);
  }
}
