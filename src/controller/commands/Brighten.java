package controller.commands;

import model.feature.basics.ChangeBrightness;
import model.image.ImageModel;
import model.library.ImageLibModel;

/**
 * An implementation of IMECommand which would brighten an image and output it to the given
 * destination.
 */
public class Brighten extends ABSCommand {
  private final String value;

  /**
   * The default constructor which accept an array of command which contains its destination and
   * origin.
   *
   * @param commands The given string array of commands.
   */
  public Brighten(String[] commands) {
    super(commands[2], commands[3]);
    this.value = commands[1];
    if (commands.length > 4) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Brighten the image in the given imageLibModel correspond to the origin filed.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    int brightness;
    try {
      brightness = Integer.parseInt(this.value);
    } catch (Exception e) {
      throw new IllegalArgumentException("Given value is not an integer\n");
    }

    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new ChangeBrightness(brightness));
    model.addToLib(destination, image);
  }
}
