package controller.knownCommands;

import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

public class Brighten extends ABSCommand {
  private final String value;

  public Brighten(String[] commands) {
    super(commands[2], commands[3]);
    this.value = commands[1];
    if (commands.length > 4) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    int brightness;
    try {
      brightness = Integer.parseInt(this.value);
    } catch (Exception e) {
      throw new IllegalArgumentException("Given value is not an integer\n");
    }

    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.changeBrightness(brightness));
  }
}
