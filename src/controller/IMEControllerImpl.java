package controller;

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
    while (scanner.hasNextLine()){
      String lineCommand = scanner.nextLine();
      String[] commandInArray = lineCommand.split(" ");
      String command = commandInArray[0];

      //TODO: only passing arguments to model
      switch (command){
        case "save":

          break;
        case "load":
          break;
        case "vertical_flip":
          break;
        case "horizontal_flip":
          break;
        case "value_component":
          break;
      }

    }
  }
}
