package controller;

import java.util.Set;

/**
 * An interface designed to fit the design of gui view, capable of handling command as string rather
 * than system input and able to return the currently supported command.
 */
public interface IMEControllerGUI extends IMEController {
  /**
   * Accept the command to be executed as a String argument.
   *
   * @param command the command to be executed.
   */
  void acceptCommand(String command);

  /**
   * Fetch all supported command.
   *
   * @return all supported command in a Set of string.
   */
  Set<String> getSupportCommands();
}
