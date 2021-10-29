package controller.knownCommands;

import model.ImageModel;

import java.io.IOException;

public interface IMECommand {
  void execute(ImageModel model) throws IOException;
}
