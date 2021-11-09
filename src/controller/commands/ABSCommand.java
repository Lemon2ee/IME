package controller.commands;

import model.library.ImageLibModel;

/** An abstract class for IMECommand which contains commonly used field. */
public abstract class ABSCommand implements IMECommand {
  protected final String origin;
  protected final String destination;

  /**
   * The default constructor.
   *
   * @param origin The given string where the operation would perform with
   * @param destination Where the output of the operation would export to
   */
  public ABSCommand(String origin, String destination) {
    if (origin == null || destination == null) {
      throw new IllegalArgumentException("Does not accept null value\n");
    }
    this.destination = destination;
    this.origin = origin;
  }

  /**
   * Execute certain commands.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public abstract void execute(ImageLibModel model);
}
