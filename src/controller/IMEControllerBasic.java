package controller;

import controller.knownCommands.*;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.imageLibrary.ImageLibModel;
import view.ImageProcessorView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A class represents the controller of the image processor, which is responsible for passing
 * received command to model and tell view what to render.
 */
public class IMEControllerBasic implements IMEController {
  private final ImageLibModel libModel;
  private final Readable readable;
  private final ImageProcessorView view;
  protected final Map<String, Function<String[], IMECommand>> knownCommands;

  /**
   * The default constructor.
   *
   * @param libModel The provided model which will handle all manipulation of images
   * @param readable The given readable object where all input would come from
   * @param view The given view object where essential message will be rendered
   */
  public IMEControllerBasic(ImageLibModel libModel, Readable readable, ImageProcessorView view) {
    if (libModel == null || readable == null || view == null) {
      throw new IllegalArgumentException("Require non-null arguments\n");
    }
    this.libModel = libModel;
    this.readable = readable;
    this.view = view;
    this.knownCommands = new HashMap<>();
    knownCommands.put("horizontal-flip", (String[] s) -> new Flip(s, FlipDirection.Horizontal));
    knownCommands.put("vertical-flip", (String[] s) -> new Flip(s, FlipDirection.Vertical));
    knownCommands.put("red-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.R));
    knownCommands.put(
        "green-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.G));
    knownCommands.put(
        "blue-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.B));
    knownCommands.put(
        "intensity-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.Intensity));
    knownCommands.put(
        "value-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.Value));
    knownCommands.put(
        "luma-component", (String[] s) -> new ComponentGreyScale(s, GreyScaleValue.Luma));
    knownCommands.put("brighten", Brighten::new);
    knownCommands.put("load", Load::new);
    knownCommands.put("save", Save::new);
  }

  /**
   * The method that would initialize the controller with all its fields, capable of parsing inputs
   * and report error to user (without breaking the whole thing).
   *
   * @throws IllegalArgumentException when the view object failed to reader
   */
  @Override
  public void initProcessor() throws IllegalStateException {
    Scanner scanner = new Scanner(this.readable);

    try {
      while (scanner.hasNextLine()) {
        String lineCommand = scanner.nextLine();
        // quite the program when detected "q"
        if (lineCommand.equalsIgnoreCase("q")) {
          return;
        }

        // ignore space, # and etc.
        if (!lineCommand.equals("") && lineCommand.charAt(0) != '#') {
          // parse the command input
          String[] commandInArray = lineCommand.split("\\s+");
          String command = commandInArray[0];
          IMECommand c;

          // execute given commands
          try {
            // TODO: better implementation
            Function<String[], IMECommand> cmd = knownCommands.getOrDefault(command, null);
            if (cmd == null) {
              throw new IllegalArgumentException("Unsupported command " + command + "\n");
            } else {
              c = cmd.apply(commandInArray);
              c.execute(this.libModel);
            }
          } catch (IllegalArgumentException e) {
            // print error message from the exception received from the model or because of
            // unsupported command
            this.view.renderMessage(e.getMessage());
          } catch (IndexOutOfBoundsException e) {
            this.view.renderMessage("Insufficient argument given\n");
          }
        }
      }
    } catch (IOException e) {
      // when transmission to the view fails
      throw new IllegalStateException("Transmission to the view fails");
    }
  }
}
