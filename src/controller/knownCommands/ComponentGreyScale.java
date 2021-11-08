package controller.knownCommands;

import model.enums.GreyScaleValue;
import model.feature.basics.GreyScale;
import model.feature.pro.ProGreyScale;
import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.awt.*;
import java.util.Objects;

public class ComponentGreyScale extends ABSCommand {
  private final GreyScaleValue value;

  public ComponentGreyScale(String[] commands, GreyScaleValue value) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(value);
    this.value = value;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    Color[][] color2dArray = image.imageArrayCopy();
    model.addToLib(destination, new ImageFile(new ProGreyScale(value).apply(color2dArray)));
  }
}
