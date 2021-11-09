import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MockImageLib implements ImageLibModel {
  private final Map<String, ImageModel> dictionary;
  StringBuilder log;

  public MockImageLib(StringBuilder log) {
    this.log = log;
    this.dictionary = new HashMap<>();
  }

  @Override
  public void addToLib(String key, ImageModel value) {
    this.log
        .append("Received add command with ")
        .append(key)
        .append(" ")
        .append(Arrays.deepToString(value.imageArrayCopy()))
        .append("\n");

    dictionary.put(key, value);
  }

  @Override
  public ImageModel read(String key) throws IllegalArgumentException {
    this.log.append("Received read command looking for ").append(key).append("\n");
    return dictionary.get(key);
  }
}
