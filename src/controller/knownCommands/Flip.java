package controller.knownCommands;

import model.Image.ImageModel;
import model.ImageLibrary.ImageLibModel;
import model.enums.FlipDirection;

import java.util.Objects;

public class Flip extends ABSCommand {
  FlipDirection direction;

  public Flip(String origin, String destination, FlipDirection direction) {
    super(origin, destination);
    Objects.requireNonNull(direction);
    this.direction = direction;
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.flip(direction));
  }
}
