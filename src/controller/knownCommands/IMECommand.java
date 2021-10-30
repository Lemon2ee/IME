package controller.knownCommands;

import model.ImageLibrary.ImageLibModel;

import java.io.IOException;

public interface IMECommand {
  void execute(ImageLibModel model) throws IOException;
}
