package controller.knownCommands;

import model.enums.FlipDirection;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.util.Objects;

/** A concrete class which implements the IMECommand which flips an Image. */
public class Flip extends ABSCommand {
  private final FlipDirection direction;

  /**
   * Default constructor which takes in an array of commands and a flip direction.
   *
   * @param commands The given array of command which contains the origin and destination of the
   *     image operation.
   * @param direction The given flip direction.
   */
  public Flip(String[] commands, FlipDirection direction) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(direction);
    this.direction = direction;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Execute the flip operation on the image which correspond to the origin.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin).copy();
    image.applyFunctional(new model.feature.basics.Flip(this.direction));
    model.addToLib(destination, image);
  }
}
