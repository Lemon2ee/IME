package controller.commands;

import model.enums.FilterType;
import model.feature.pro.Filter;
import model.image.ImageModel;
import model.library.ImageLibModel;

/** A concrete class representing the command to blur an image. */
public class Blur extends ABSCommand {
  /**
   * Default constructor.
   *
   * @param array the given array of commands which contains the origin and destination of
   *     operation.
   */
  public Blur(String[] array) {
    super(array[1], array[2]);
    if (array.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Perform the blurring steps.
   *
   * @param model The given ImageLibModel where the image resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new Filter(FilterType.Blur));
    model.addToLib(destination, image);
  }
}
