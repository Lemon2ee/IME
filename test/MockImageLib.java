import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.util.Arrays;

public class MockImageLib implements ImageLibModel {
  StringBuilder log;

  public MockImageLib(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void addToLib(String key, ImageModel value) {
    this.log
        .append("Received add command with ")
        .append(key)
        .append(" ")
        .append(Arrays.deepToString(value.imageArrayCopy()))
        .append("\n");
  }

  @Override
  public ImageModel read(String key) throws IllegalArgumentException {
    this.log.append("Received read command looking for ").append(key).append("\n");
    return null;
  }
}
