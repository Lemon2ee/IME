package controller.knownCommands;

import model.imageLibrary.ImageLibModel;

public interface IMECommand {
  void execute(ImageLibModel model);
}
