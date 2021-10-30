package controller.knownCommands;

import model.Image.ImageModel;
import model.ImageLibrary.ImageLibModel;
import model.enums.GreyScaleValue;

import java.util.Objects;

public class componentGreyScale extends ABSCommand {
  GreyScaleValue value;

  public componentGreyScale(String origin, String destination, GreyScaleValue value) {
    super(origin, destination);
    Objects.requireNonNull(value);
    this.value = value;
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.greyScale(value));
  }
}
