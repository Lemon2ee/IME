package controller.knownCommands;

import model.ImageModel;

import java.io.IOException;

public class save extends ABSCommand {

  public save(String origin, String destination) {
    super(origin, destination);
  }

  @Override
  public void execute(ImageModel model) throws IOException {
    model.save(origin, destination);
  }
}
