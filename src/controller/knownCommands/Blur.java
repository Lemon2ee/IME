package controller.knownCommands;

import model.enums.FilterType;
import model.image.ABSImageFile;
import model.image.ImageModel;
import model.image.ReadOnlyImageModel;
import model.imageLibrary.ImageLibModel;

import java.awt.*;

public class Blur extends ABSCommand {
  public Blur(String[] array) {
    super(array[1], array[2]);
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.filter(FilterType.Blur));
  }
}
