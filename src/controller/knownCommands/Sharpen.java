package controller.knownCommands;

import model.enums.FilterType;
import model.feature.pro.Filter;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

public class Sharpen extends ABSCommand {

  public Sharpen(String[] array) {
    super(array[1], array[2]);
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new Filter(FilterType.Sharpen));
    model.addToLib(destination, image);
  }
}
