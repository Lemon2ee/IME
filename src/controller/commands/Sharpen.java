package controller.commands;

import model.enums.FilterType;
import model.feature.pro.Filter;
import model.image.ImageModel;
import model.library.ImageLibModel;

/** A concrete class which represents the sharpening operation. */
public class Sharpen extends ABSCommand {

  /**
   * Default constructor.
   *
   * @param array the given array of commands which contains the origin and destination of
   *     operation.
   */
  public Sharpen(String[] array) {
    super(array[1], array[2]);
  }

  /**
   * Apply filter to sharpen the corresponding image.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new Filter(FilterType.Sharpen));
    model.addToLib(destination, image);
  }
}
