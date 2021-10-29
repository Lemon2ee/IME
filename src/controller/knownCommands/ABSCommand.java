package controller.knownCommands;

import model.ImageModel;

import java.io.IOException;
import java.util.Objects;

public abstract class ABSCommand implements IMECommand {
  String origin;
  String destination;

  public ABSCommand(String origin, String destination) {
    Objects.requireNonNull(origin);
    Objects.requireNonNull(destination);
    this.destination = destination;
    this.origin = origin;
  }

  @Override
  public abstract void execute(ImageModel model) throws IOException;
}
