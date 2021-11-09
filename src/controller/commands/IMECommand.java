package controller.commands;

import model.library.ImageLibModel;

/** An interface which represents command which the controller know. */
public interface IMECommand {
  /**
   * Execute certain operation which has relations to the given ImageLibModel.
   *
   * @param model The given ImageLibModel which the resources would come from and would be stored.
   */
  void execute(ImageLibModel model);
}
