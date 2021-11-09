import model.image.ImageModel;
import model.library.ImageLibModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** A mock class of ImageLib used for test only. */
public class MockImageLib implements ImageLibModel {
  private final Map<String, ImageModel> dictionary;
  StringBuilder log;

  /**
   * Default constructor.
   *
   * @param log The logger as string builder.
   */
  public MockImageLib(StringBuilder log) {
    this.log = log;
    this.dictionary = new HashMap<>();
  }

  /**
   * Add the given imageModel to the hashmap.
   *
   * @param key access key for the ImageModel
   * @param value the given ImageModel which will be stored
   */
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

  /**
   * Read the imageModel corresponding to the given key.
   *
   * @param key The key of ImageModel
   * @return the copy of ImageModel
   * @throws IllegalArgumentException not going to happen.
   */
  @Override
  public ImageModel read(String key) throws IllegalArgumentException {
    this.log.append("Received read command looking for ").append(key).append("\n");
    return dictionary.get(key);
  }
}
