package controller;

import java.awt.event.ActionListener;
import java.util.Set;

public interface IMEControllerGUI extends IMEController {
  // TODO: add something
  void acceptCommand(String command);

  Set<String> getSupportCommands();
}
