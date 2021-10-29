package controller.knownCommands;

import model.ImageModel;

import java.util.Objects;

/** An abstract class that implement IMECommand which contains the origin and destination field. */
public abstract class ABSCommand implements IMECommand {
  String origin;
  String destination;

  /**
   * Default constructor for the ABSCommand that initialize the filed with given arguments
   *
   * @param origin The given name of image which user wish to be processed.
   * @param destination The place where the image will be outputted.
   */
  public ABSCommand(String origin, String destination) {
    this.destination = Objects.requireNonNull(origin);
    this.origin = Objects.requireNonNull(destination);
  }

  /**
   * Instruct the model to execute certain command.
   *
   * @param model The given model where the instructions will be executed.
   * @throws IllegalArgumentException When the model failed to process with given arguments.
   */
  @Override
  public abstract void execute(ImageModel model) throws IllegalArgumentException;
}
