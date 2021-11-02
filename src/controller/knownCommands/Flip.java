package controller.knownCommands;

import model.enums.FlipDirection;
import model.image.ImageModel;
import model.imageLibrary.ImageLibModel;

import java.util.Objects;

public class Flip extends ABSCommand {
  private final FlipDirection direction;

  public Flip(String[] commands, FlipDirection direction) {
    super(commands[1], commands[2]);
    Objects.requireNonNull(direction);
    this.direction = direction;
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageModel image = model.read(this.origin);
    model.addToLib(destination, image.flip(direction));
  }
}
