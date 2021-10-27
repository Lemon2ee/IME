package controller.knownCommands;

import model.ImageModel;
import model.enums.FlipDirection;

import java.util.Objects;

public class flip extends ABSCommand {
  FlipDirection direction;

  public flip(String origin, String destination, FlipDirection direction) {
    super(origin, destination);
    Objects.requireNonNull(direction);
    this.direction = direction;
  }

  @Override
  public void execute(ImageModel model) {
    model.flip(origin, destination, direction);
  }
}
