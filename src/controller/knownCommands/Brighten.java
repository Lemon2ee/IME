package controller.knownCommands;

import model.feature.basics.ChangeBrightness;
import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.awt.*;

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
    Color[][] color2dArray = image.imageArrayCopy();
    model.addToLib(
        destination, new ImageFile(new ChangeBrightness(brightness).apply(color2dArray)));
  }
}
