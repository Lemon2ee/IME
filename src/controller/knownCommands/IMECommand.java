package controller.knownCommands;

import model.ImageModel;

/** An interface that represents executable command */
public interface IMECommand {
  /**
   * Execute certain commands to the given ImageModel.
   *
   * @param model The given model where the instructions will be executed.
   * @throws IllegalArgumentException when model failed to process with the given arguments.
   */
  void execute(ImageModel model) throws IllegalArgumentException;
}
