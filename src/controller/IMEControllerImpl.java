package controller;

import controller.knownCommands.*;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.ImageModel;
import view.ImageProcessorView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class IMEControllerImpl implements IMEController {
  private final ImageModel model;
  private final Readable readable;
  private final ImageProcessorView view;

  public IMEControllerImpl(ImageModel model, Readable readable, ImageProcessorView view) {
    if (model == null || readable == null || view == null) {
      throw new IllegalArgumentException("Require non-null arguments");
    }
    this.model = model;
    this.readable = readable;
    this.view = view;
  }

  @Override
  public void initProcessor() throws IllegalArgumentException {
    Scanner scanner = new Scanner(this.readable);

    Map<String, Function<String[], IMECommand>> knownCommands = new HashMap<>();
    knownCommands.put(
        "save",
        (String[] s) -> {
          return new save(s[1], s[2]);
        });
    knownCommands.put(
        "load",
        (String[] s) -> {
          return new load(s[1], s[2]);
        });
    knownCommands.put(
        "horizontal-flip",
        (String[] s) -> {
          return new flip(s[1], s[2], FlipDirection.Horizontal);
        });
    knownCommands.put(
        "vertical-flip",
        (String[] s) -> {
          return new flip(s[1], s[2], FlipDirection.Vertical);
        });
    knownCommands.put(
        "red-component",
        (String[] s) -> {
          return new componentGreyScale(s[1], s[2], GreyScaleValue.R);
        });
    knownCommands.put(
        "green-component",
        (String[] s) -> {
          return new componentGreyScale(s[1], s[2], GreyScaleValue.G);
        });
    knownCommands.put(
        "blue-component",
        (String[] s) -> {
          return new componentGreyScale(s[1], s[2], GreyScaleValue.B);
        });
    knownCommands.put(
        "intensity_component",
        (String[] s) -> {
          return new componentGreyScale(s[1], s[2], GreyScaleValue.Intensity);
        });
    knownCommands.put(
        "luma_component",
        (String[] s) -> {
          return new componentGreyScale(s[1], s[2], GreyScaleValue.Luma);
        });

    try {
      while (scanner.hasNextLine()) {
        String lineCommand = scanner.nextLine();
        if (lineCommand.equals("q")) {
          return;
        }
        String[] commandInArray = lineCommand.split(" ");
        String command = commandInArray[0];
        IMECommand c;
        // TODO: only passing arguments to model
        try {
          Function<String[], IMECommand> cmd = knownCommands.getOrDefault(command, null);
          if (cmd == null) {
            throw new IllegalArgumentException("Unsupported command");
          } else {
            c = cmd.apply(commandInArray);
            c.execute(this.model);
          }
        } catch (IllegalArgumentException e) {
          this.view.renderMessage(e.getMessage());
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("View failed to transmit the message to appendable");
    }
  }

  private void componentCommands(String[] listOfArgs, GreyScaleValue greyScaleValue)
      throws IllegalArgumentException {
    if (listOfArgs.length != 3) {
      throw new IllegalArgumentException("Insufficient arguments");
    }
    this.model.greyScale(listOfArgs[1], listOfArgs[2], greyScaleValue);
  }
}
