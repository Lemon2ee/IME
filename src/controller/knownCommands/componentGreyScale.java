package controller.knownCommands;

import model.ImageModel;
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
  public void execute(ImageModel model) {
    model.greyScale(origin, destination, value);
  }
}
