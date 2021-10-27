package controller;

import model.FlipDirection;
import model.ImageModel;
import view.ImageProcessorView;

import java.util.Scanner;

public class IMEControllerImpl implements IMEController {
  private final ImageModel model;
  private final Readable readable;
  private final ImageProcessorView view;

  public IMEControllerImpl(ImageModel model, Readable readable, ImageProcessorView view) {
    if (model == null || readable == null) {
      throw new IllegalArgumentException("Require non-null arguments");
    }
    this.model = model;
    this.readable = readable;
    this.view = view;
  }

  @Override
  public void initProcessor() {
    Scanner scanner = new Scanner(this.readable);
    while (scanner.hasNextLine()) {
      String lineCommand = scanner.nextLine();
      String[] commandInArray = lineCommand.split(" ");
      String command = commandInArray[0];

      // TODO: only passing arguments to model
      switch (command) {
        case "save":
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
          this.model.flip(verticalFlipImageName, verticalFlipImageNameDest, FlipDirection.Vertical);
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
        case "value_component":
          break;
      }
    }
  }
}
