package controller.knownCommands;

import model.ImageModel;

public class save extends ABSCommand {

  public save(String origin, String destination) {
    super(origin, destination);
  }

  @Override
  public void execute(ImageModel model) {
    model.save(origin, destination);
  }
}
