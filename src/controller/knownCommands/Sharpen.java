package controller.knownCommands;

import model.enums.FilterType;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

public class Sharpen extends ABSCommand {

  public Sharpen(String[] array) {
    super(array[1], array[2]);
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.filter(FilterType.Sharpen));
  }
}
