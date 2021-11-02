package controller.knownCommands;

import model.imageLibrary.ImageLibModel;
import utils.ImageUtil;

public class Save extends ABSCommand {

  public Save(String[] commands) {
    super(commands[2], commands[1]);
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  @Override
  public void execute(ImageLibModel model) {
    ImageUtil util = new ImageUtil();

    util.writeImage(this.destination, model.read(this.origin));
  }
}
