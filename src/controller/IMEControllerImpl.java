package controller;

import controller.knownCommands.*;
import controller.supportedExtensions.FileFormatSupport;
import controller.supportedExtensions.PPMFileExtension;
import model.ImageLibrary.ImageLibModel;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import view.ImageProcessorView;

import java.io.File;
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
    Map<String, FileFormatSupport> knownExtension = new HashMap<>();

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

      // put supported file extension
      knownExtension.put(".ppm", new PPMFileExtension());

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
          // TODO: Update with command line design pattern
          try {
            // TODO: better implementation
            switch (command) {
                // load file with different extension as different ImageModel to the library
              case "load":
                // utilize file object to parse the given path
                File theFile = new File(commandInArray[1]);
                String fileName = theFile.getName();
                // get the extension, should be the last .xxx in the file path
                String extension = fileName.substring(fileName.lastIndexOf("."));
                // find the correct ImageModel constructor in the knownFileExtension map
                FileFormatSupport fileFormatSupportFunction =
                    knownExtension.getOrDefault(extension.toLowerCase(), null);

                // throw exception if it is an unsupported file format
                if (fileFormatSupportFunction == null) {
                  throw new IllegalArgumentException("Unsupported extension " + extension + "\n");
                }

                // if everything worked out fine, add the ImageModel to the library
                this.libModel.addToLib(
                    commandInArray[2], fileFormatSupportFunction.constructModel(commandInArray[1]));
                break;
                // save the designated image to the designated filepath
              case "save":
                this.view.save(commandInArray[2], commandInArray[1]);
                break;
                // if it isn't load or save, then lookup the supported command map
              default:
                Function<String[], IMECommand> cmd = knownCommands.getOrDefault(command, null);
                if (cmd == null) {
                  throw new IllegalArgumentException("Unsupported command " + command + "\n");
                } else {
                  c = cmd.apply(commandInArray);
                  c.execute(this.libModel);
                }
                break;
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
