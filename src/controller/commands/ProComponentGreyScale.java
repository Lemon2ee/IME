package controller.commands;

import model.enums.GreyScaleValue;
import model.feature.pro.ProGreyScale;
import model.image.ImageModel;
import model.library.ImageLibModel;

import java.util.Objects;

/**
 * A pro version of component grey scale that supports sepia and alpha greyscale along with all
 * basic features (R,G,B etc.).
 */
public class ProComponentGreyScale extends ABSCommand {
  private final GreyScaleValue value;

  /**
   * Default constructor.
   *
   * @param commands The given array of commands
   * @param value The given grey scale value which would be passed to the ImageFile
   */
  public ProComponentGreyScale(String[] commands, GreyScaleValue value) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(value);
    this.value = value;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Perform the grey scale operation to the Image correspond to the origin field.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new ProGreyScale(value));
    model.addToLib(destination, image);
  }
}
