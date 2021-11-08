package controller.knownCommands;

import model.enums.FlipDirection;
import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.awt.*;
import java.util.Objects;

public class Flip extends ABSCommand {
  private final FlipDirection direction;

  public Flip(String[] commands, FlipDirection direction) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(direction);
    this.direction = direction;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    Color[][] color2dArray = image.imageArrayCopy();
    model.addToLib(
        destination,
        new ImageFile(new model.feature.basics.Flip(this.direction).apply(color2dArray)));
  }
}
