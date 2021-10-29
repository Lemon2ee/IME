package controller.knownCommands;

import model.ImageModel;

public class brighten extends ABSCommand {
  String value;

  public brighten(String origin, String destination, String value) {
    super(origin, destination);
    this.value = value;
  }

  @Override
  public void execute(ImageModel model) {
    int brightness;
    try {
      brightness = Integer.parseInt(this.value);
    } catch (Exception e) {
      // TODO: bad practice
      throw new IllegalArgumentException("Given value is not an integer");
    }
    model.changeBrightness(origin, destination, brightness);
  }
}
