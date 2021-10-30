package controller.knownCommands;

import model.ImageLibrary.ImageLibModel;

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
  public abstract void execute(ImageLibModel model) throws IOException;
}
