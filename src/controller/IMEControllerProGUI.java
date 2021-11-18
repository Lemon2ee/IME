package controller;

import model.library.ImageLibModel;
import view.ImageProcessorView;

import java.awt.event.ActionEvent;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Set;

public class IMEControllerProGUI extends IMEControllerPro implements IMEControllerGUI {
  /**
   * The default constructor, but with additional command support.
   *
   * @param libModel The provided model which will handle all manipulation of images
   * @param view The given view object where essential message will be rendered
   */
  public IMEControllerProGUI(ImageLibModel libModel, ImageProcessorView view) {
    super(libModel, new InputStreamReader(System.in), view);
  }


  @Override
  public void acceptCommand(String command) {
    this.readable = new StringReader(command);
    System.out.println("Accepted command: " + command);
    this.initProcessor();
  }

  @Override
  public Set<String> getSupportCommands() {
    return this.knownCommands.keySet();
  }
}
