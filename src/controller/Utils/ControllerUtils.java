package controller.Utils;

import java.io.File;

public class ControllerUtils {
  public String getExtension(String filepath) {
    File theFile = new File(filepath);
    String fileName = theFile.getName();
    // get the extension, should be the last .xxx in the file path
    return fileName.substring(fileName.lastIndexOf("."));
  }
}
