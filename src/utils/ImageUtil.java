package utils;

import model.image.ReadOnlyImageModel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/** This class contains utility methods to read an image from file and manipulate a color */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @return A two-dimensional array of Color that represents the pixels
   * @throws IllegalArgumentException either when the given file cannot find or the given file is
   *     not a PPM3 file.
   */
  public Color[][] readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    // set up the scanner, exit the program if an invalid file path is given
    // might throw
    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found\n");
    }

    StringBuilder builder = new StringBuilder();
    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3\n");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    Color[][] colorArray = new Color[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Color colorToAdd = new Color(r, g, b);
        colorArray[i][j] = colorToAdd;
      }
    }

    return colorArray;
  }

  public Color[][] imageIORead(String filename) {
    File image = new File(filename);

    if (!image.exists()) {
      throw new IllegalArgumentException("File not found");
    }

    try {
      BufferedImage bufferedImage = ImageIO.read(image);

      int height = bufferedImage.getHeight();
      int width = bufferedImage.getWidth();

      Color[][] imageArray = new Color[width][height];

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          imageArray[x][y] = new Color(bufferedImage.getRGB(x, y));
        }
      }
      return imageArray;
    } catch (IOException e) {
      throw new IllegalArgumentException("An IOException occurred when reading image");
    }
  }

  /**
   * Perform red component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toRed(Color origin) {
    return new Color(origin.getRed(), origin.getRed(), origin.getRed());
  }

  /**
   * Perform green component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toGreen(Color origin) {
    return new Color(origin.getGreen(), origin.getGreen(), origin.getGreen());
  }

  /**
   * Perform blue component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toBlue(Color origin) {
    return new Color(origin.getBlue(), origin.getBlue(), origin.getBlue());
  }

  /**
   * Perform maximum component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toValue(Color origin) {
    int maxValue = Math.max(origin.getRed(), Math.max(origin.getGreen(), origin.getBlue()));
    return new Color(maxValue, maxValue, maxValue);
  }

  /**
   * Perform maximum component grey scale on a rgb pixel.
   *
   * @param origin the original pixel for grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toAlpha(Color origin) {
    return new Color(origin.getAlpha(), origin.getAlpha(), origin.getAlpha());
  }

  /**
   * Perform intensity value grey scale on a rgb pixel.
   *
   * @param origin the original pixel for the grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toIntensity(Color origin) {
    return colorTransform(
        origin,
        new double[][] {
          {1.0 / 3, 1.0 / 3, 1.0 / 3}, {1.0 / 3, 1.0 / 3, 1.0 / 3}, {1.0 / 3, 1.0 / 3, 1.0 / 3}
        });
  }

  /**
   * Perform luma value grey scale on a rgb pixel.
   *
   * @param origin the original pixel for the grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toLuma(Color origin) {
    return colorTransform(
        origin,
        new double[][] {
          {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}
        });
  }

  public Color toSepia(Color origin) {
    return colorTransform(
        origin,
        new double[][] {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});
  }

  /**
   * Change the rgb brightness of a pixel with given value.
   *
   * @param origin the original pixel to change the brightness as Color
   * @param value the value to be changed for the color brightness
   * @return the new pixel after changing brightness as Color
   */
  public Color colorBrightness(Color origin, int value) {
    int newR = origin.getRed() + value;
    int newG = origin.getGreen() + value;
    int newB = origin.getBlue() + value;

    return new Color(clampRange(newR), clampRange(newG), clampRange(newB));
  }

  /**
   * Perform a linear color transformation by using the transform value matrix.
   *
   * @param origin the original color to be transformed
   * @param transformMatrix the 3 * 3 matrix of the transformation as 2d array of double
   * @return the new color after transformation as Color
   * @throws IllegalArgumentException if the transformMatrix null or dimension is invalid
   */
  private Color colorTransform(Color origin, double[][] transformMatrix)
      throws IllegalArgumentException {
    if (transformMatrix == null) {
      throw new IllegalArgumentException("The transformation matrix cannot be null.");
    }
    if (transformMatrix.length != 3 || transformMatrix[0].length != 3) {
      throw new IllegalArgumentException("The transformation matrix needs to be 3 * 3.");
    }

    int srcR = origin.getRed();
    int srcG = origin.getGreen();
    int srcB = origin.getBlue();
    double newR =
        srcR * transformMatrix[0][0] + srcG * transformMatrix[0][1] + srcB * transformMatrix[0][2];
    double newG =
        srcR * transformMatrix[1][0] + srcG * transformMatrix[1][1] + srcB * transformMatrix[1][2];
    double newB =
        srcR * transformMatrix[2][0] + srcG * transformMatrix[2][1] + srcB * transformMatrix[2][2];

    return new Color(
        clampRange((int) Math.round(newR)),
        clampRange((int) Math.round(newG)),
        clampRange((int) Math.round(newB)));
  }

  /**
   * Clamp the range of the color value into the valid range of 0-255.
   *
   * @param value the calculated new channel value
   * @return the clamped value within the valid range of 0-255
   */
  public int clampRange(int value) {
    if (value < 0) {
      return 0;
    } else {
      return Math.min(value, 255);
    }
  }

  public void writeImage(String filepath, ReadOnlyImageModel model) {

    String extension = new ControllerUtils().getExtension(filepath);

    switch (extension) {
      case ".ppm":
        this.writePPM(model, filepath);
        break;
      case ".png":
        this.imageIOWrite(model, filepath, "png");
        break;
      case ".bpm":
        this.imageIOWrite(model, filepath, "bpm");
        break;
      case ".jpg":
        this.imageIOWrite(model, filepath, "jpg");
        break;
      default:
        break;
    }
  }

  private void writePPM(ReadOnlyImageModel model, String filepath) {
    StringBuilder image = new StringBuilder();
    StringBuilder header = new StringBuilder();

    Color[][] array = model.imageArrayCopy();

    int height = array.length;
    int width = array[0].length;

    for (Color[] colorRow : array) {
      for (Color color : colorRow) {
        int redValue = color.getRed();
        int greenValue = color.getGreen();
        int blueValue = color.getBlue();
        image.append(redValue).append("\n");
        image.append(greenValue).append("\n");
        image.append(blueValue).append("\n");
      }
    }

    header.append("P3\n");
    header.append(
        "# Created by Image Manipulation and Enhancement (IME) written by JerryGCDing "
            + "and Lemon2ee\n");
    header.append(width).append(" ").append(height).append("\n");
    header.append("255").append("\n");
    header.append("# end of the header\n");

    // writing to local file
    File file = new File(filepath);

    if (filepath.contains("/")) {
      boolean createParent = file.getParentFile().mkdirs();
    }

    try {
      FileWriter writer = new FileWriter(file, false);
      writer.write(header.append(image).toString());
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private void imageIOWrite(ReadOnlyImageModel model, String filepath, String extension) {
    // Initialize BufferedImage, assuming Color[][] is already properly populated.
    BufferedImage bufferedImage =
        new BufferedImage(model.getWidth(), model.getHeight(), BufferedImage.TYPE_INT_RGB);

    Color[][] array = model.imageArrayCopy();

    // Set each pixel of the BufferedImage to the color from the Color[][].
    for (int y = 0; y < model.getHeight(); y++) {
      for (int x = 0; x < model.getWidth(); x++) {
        bufferedImage.setRGB(x, y, array[y][x].getRGB());
      }
    }

    try {
      ImageIO.write(bufferedImage, extension, new File(filepath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Encountered IO exception when trying to write image");
    }
  }
}
