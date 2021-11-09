package controller.knownCommands;

import model.enums.GreyScaleValue;
import model.feature.pro.ProGreyScale;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.util.Objects;

public class ProComponentGreyScale extends ABSCommand {
  private final GreyScaleValue value;

  public ProComponentGreyScale(String[] commands, GreyScaleValue value) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(value);
    this.value = value;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new ProGreyScale(value));
    model.addToLib(destination, image);
  }
}
