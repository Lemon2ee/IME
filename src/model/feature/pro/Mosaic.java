package model.feature.pro;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import model.feature.FeatureCommand;
import model.position.Position;
import model.position.Position2d;

/**
 * The class represents a mosaic operation on the provided image. Including apply the created
 * function object to the source image.
 */
public class Mosaic implements FeatureCommand {
  private final int seeds;

  /**
   * To construct a mosaic object with the given number of seeds.
   *
   * @param seeds the number of seeds for mosaic as int
   */
  public Mosaic(int seeds) throws IllegalArgumentException {
    if (seeds <= 0) {
      throw new IllegalArgumentException("Seed number needs to be positive.");
    }
    this.seeds = seeds;
  }

  @Override
  public Color[][] apply(Color[][] image) {
    int height = image.length;
    int width = image[0].length;

    int pixelNum = height * width;
    if (this.seeds >= pixelNum) {
      return image;
    }

    int[][] pixelCluster = new int[height][width];

    Random rand = new Random();

    ArrayList<Position> seedPos = new ArrayList<>();
    for (int i = 0; i < this.seeds; ) {
      Position newPos = new Position2d(rand.nextInt(height), rand.nextInt(width));
      if (!seedPos.contains(newPos)) {
        seedPos.add(newPos);
        i++;
      }
    }

    int[][] seedSum = new int[seeds][4];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Position origin = new Position2d(r, c);
        int seedIndex = origin.getClusterSeed(seedPos);
        pixelCluster[r][c] = seedIndex;
        seedSum[seedIndex][0] += image[r][c].getRed();
        seedSum[seedIndex][1] += image[r][c].getGreen();
        seedSum[seedIndex][2] += image[r][c].getBlue();
        seedSum[seedIndex][3] += 1;
      }
    }

    Color[][] output = new Color[height][width];
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int[] currSeed = seedSum[pixelCluster[r][c]];
        int newR = currSeed[0] / currSeed[3];
        int newG = currSeed[1] / currSeed[3];
        int newB = currSeed[2] / currSeed[3];

        output[r][c] = new Color(newR, newG, newB);
      }
    }

    return output;
  }
}
