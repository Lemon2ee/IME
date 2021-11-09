package controller.knownCommands;

import model.imageLibrary.ImageLibModel;

public abstract class ABSCommand implements IMECommand {
  protected final String origin;
  protected final String destination;

  public ABSCommand(String origin, String destination) {
    if (origin == null || destination == null) {
      throw new IllegalArgumentException("Does not accept null value\n");
    }
    this.destination = destination;
    this.origin = origin;
  }

  @Override
  public abstract void execute(ImageLibModel model);
}
