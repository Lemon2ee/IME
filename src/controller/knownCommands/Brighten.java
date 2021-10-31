package controller.knownCommands;

import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

public class Brighten extends ABSCommand {
  String value;

  public Brighten(String origin, String destination, String value) {
    super(origin, destination);
    this.value = value;
  }

  @Override
  public void execute(ImageLibModel model) {
    int brightness;
    try {
      brightness = Integer.parseInt(this.value);
    } catch (Exception e) {
      // TODO: bad practice?
      throw new IllegalArgumentException("Given value is not an integer");
    }

    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.changeBrightness(brightness));
  }
}
