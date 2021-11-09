package utils;

import java.io.File;

/** A utility class which contains method that are useful for the controller. */
public class ControllerUtils {
  /**
   * Find the extension of the given filepath.
   *
   * @param filepath The given filepath as a string
   * @return the real file extension of the given file path.
   */
  public String getExtension(String filepath) {
    File theFile = new File(filepath);
    String fileName = theFile.getName();
    // get the extension, should be the last .xxx in the file path
    return fileName.substring(fileName.lastIndexOf("."));
  }
}
