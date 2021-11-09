package controller.commands;

import model.library.ImageLibModel;
import utils.ImageUtil;

/**
 * A concrete class which implements the IMECommand and provides the functionality of saving the
 * image to the local filepath.
 */
public class Save extends ABSCommand {

  /**
   * Default constructor.
   *
   * @param commands the given array of commands which contains the origin and destination of
   *     operation.
   */
  public Save(String[] commands) {
    super(commands[2], commands[1]);
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Save the image correspond to the origin to the given local with given file path and the *
   * extension in the path.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    ImageUtil util = new ImageUtil();
    util.writeImage(this.destination, model.read(this.origin));
  }
}
