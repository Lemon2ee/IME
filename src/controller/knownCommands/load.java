package controller.knownCommands;

import model.ImageModel;

public class load extends ABSCommand {
  public load(String origin, String destination) {
    super(origin, destination);
  }

  @Override
  public void execute(ImageModel model) {
    model.load(destination, origin);
  }
}
