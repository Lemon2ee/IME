package utils;

import model.image.ReadOnlyImageModel;

import java.awt.Color;
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
    double preAvg = (origin.getRed() + origin.getGreen() + origin.getBlue()) / 3.0;
    int intAvg = (int) Math.round(preAvg);
    return new Color(intAvg, intAvg, intAvg);
  }

  /**
   * Perform luma value grey scale on a rgb pixel.
   *
   * @param origin the original pixel for the grey scale as Color
   * @return the new pixel after grey scale as Color
   */
  public Color toLuma(Color origin) {
    double luma = 0.2126 * origin.getRed() + 0.7152 * origin.getGreen() + 0.0722 * origin.getBlue();
    int intLuma = (int) Math.round(luma);
    return new Color(intLuma, intLuma, intLuma);
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

    if (value > 0) {
      newR = Math.min(newR, 255);
      newG = Math.min(newG, 255);
      newB = Math.min(newB, 255);
    } else {
      newR = Math.max(newR, 0);
      newG = Math.max(newG, 0);
      newB = Math.max(newB, 0);
    }

    return new Color(newR, newG, newB);
  }

  public void writeImage(String filepath, ReadOnlyImageModel model) {
    Map<String, Function<Color[][], String>> map = new HashMap<>();
    map.put(".ppm", this::ppmToString);

    String extension = new ControllerUtils().getExtension(filepath);

    Color[][] image = model.imageArrayCopy();

    Function<Color[][], String> toStringFunction = map.getOrDefault(extension.toLowerCase(), null);

    // throw exception if it is an unsupported file format
    if (toStringFunction == null) {
      throw new IllegalArgumentException("Unsupported output format " + extension + "\n");
    }

    // writing to local file
    File file = new File(filepath);

    if (filepath.contains("/")) {
      boolean createParent = file.getParentFile().mkdirs();
    }

    try {
      FileWriter writer = new FileWriter(file, false);
      writer.write(this.ppmToString(image));
      writer.flush();
      writer.close();
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private String ppmToString(Color[][] array) {
    StringBuilder image = new StringBuilder();
    StringBuilder header = new StringBuilder();

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
    return header.append(image).toString();
  }
}
