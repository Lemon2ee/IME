package controller.knownCommands;

import model.enums.FilterType;
import model.feature.pro.Filter;
import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.awt.*;

public class Sharpen extends ABSCommand {

  public Sharpen(String[] array) {
    super(array[1], array[2]);
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    Color[][] color2dArray = image.imageArrayCopy();
    model.addToLib(destination, new ImageFile(new Filter(FilterType.Sharpen).apply(color2dArray)));
  }
}
