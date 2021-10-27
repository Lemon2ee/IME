package controller;

import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.ImageModel;
import view.ImageProcessorView;

import java.io.IOException;
import java.util.Scanner;

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
    try {
      while (scanner.hasNextLine()) {
        String lineCommand = scanner.nextLine();
        if (lineCommand.equals("q")) {
          return;
        }
        String[] commandInArray = lineCommand.split(" ");
        String command = commandInArray[0];

        // TODO: only passing arguments to model
        try {
          switch (command) {
            case "save":
              if (commandInArray.length != 3) {
                throw new IllegalArgumentException("Insufficient arguments");
              }
              this.model.save(commandInArray[1], commandInArray[2]);
              break;
            case "load":
              if (commandInArray.length != 3) {
                throw new IllegalArgumentException("Insufficient arguments");
              }
              String filePath = commandInArray[1];
              String imageName = commandInArray[2];
              this.model.load(imageName, filePath);
              break;
            case "vertical_flip":
              if (commandInArray.length != 3) {
                throw new IllegalArgumentException("Insufficient arguments");
              }
              String verticalFlipImageName = commandInArray[1];
              String verticalFlipImageNameDest = commandInArray[2];
              this.model.flip(
                  verticalFlipImageName, verticalFlipImageNameDest, FlipDirection.Vertical);
              break;
            case "horizontal_flip":
              if (commandInArray.length != 3) {
                throw new IllegalArgumentException("Insufficient arguments");
              }
              String horizontalFlipImageName = commandInArray[1];
              String horizontalFlipImageNameDest = commandInArray[2];
              this.model.flip(
                  horizontalFlipImageName, horizontalFlipImageNameDest, FlipDirection.Horizontal);
              break;
            case "red_component":
              this.componentCommands(commandInArray, GreyScaleValue.R);
              break;
            case "green_component":
              this.componentCommands(commandInArray, GreyScaleValue.G);
              break;
            case "blue_component":
              this.componentCommands(commandInArray, GreyScaleValue.B);
              break;
            case "intensity_component":
              this.componentCommands(commandInArray, GreyScaleValue.Intensity);
              break;
            case "luma_component":
              this.componentCommands(commandInArray, GreyScaleValue.Luma);
              break;
            default:
              break;
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
