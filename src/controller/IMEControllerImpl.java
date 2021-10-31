package controller;

import controller.knownCommands.*;
import model.ImageLibrary.ImageLibModel;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
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
public class IMEControllerImpl implements IMEController {
  private final ImageLibModel libModel;
  private final Readable readable;
  private final ImageProcessorView view;

  /**
   * The default constructor.
   *
   * @param libModel The provided model which will handle all manipulation of images
   * @param readable The given readable object where all input would come from
   * @param view The given view object where essential message will be rendered
   */
  public IMEControllerImpl(ImageLibModel libModel, Readable readable, ImageProcessorView view) {
    if (libModel == null || readable == null || view == null) {
      throw new IllegalArgumentException("Require non-null arguments");
    }
    this.libModel = libModel;
    this.readable = readable;
    this.view = view;
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
    Map<String, Function<String[], IMECommand>> knownCommands = new HashMap<>();

    try {
      // following command block might throw index out of bound exception
      // put supported commands
      knownCommands.put(
          "horizontal-flip", (String[] s) -> new Flip(s[1], s[2], FlipDirection.Horizontal));
      knownCommands.put(
          "vertical-flip", (String[] s) -> new Flip(s[1], s[2], FlipDirection.Vertical));
      knownCommands.put(
          "red-component", (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.R));
      knownCommands.put(
          "green-component", (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.G));
      knownCommands.put(
          "blue-component", (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.B));
      knownCommands.put(
          "intensity-component",
          (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.Intensity));
      knownCommands.put(
          "value-component",
          (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.Value));
      knownCommands.put(
          "luma-component",
          (String[] s) -> new ComponentGreyScale(s[1], s[2], GreyScaleValue.Luma));
      knownCommands.put("brighten", (String[] s) -> new Brighten(s[2], s[3], s[1]));
      knownCommands.put("load", (String[] s) -> new Load(s[1], s[2]));
      knownCommands.put("save", (String[] s) -> new Save(s[2], s[1]));

      while (scanner.hasNextLine()) {
        String lineCommand = scanner.nextLine();
        // quite the program when detected "q"
        if (lineCommand.equalsIgnoreCase("q")) {
          return;
        }

        // ignore space, # and etc.
        if (!lineCommand.equals("") && lineCommand.charAt(0) != '#') {
          // parse the command input
          String[] commandInArray = lineCommand.split(" ");
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
            // print error message if the given command does not have enough command
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
