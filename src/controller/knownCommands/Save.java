package controller.knownCommands;

import model.Image.ImageUtil;
import model.ImageLibrary.ImageLibModel;

import java.io.IOException;

public class Save extends ABSCommand {

  public Save(String origin, String destination) {
    super(origin, destination);
  }

  @Override
  public void execute(ImageLibModel model) throws IOException {
    ImageUtil util = new ImageUtil();

    util.writeImage(this.destination, model.read(this.origin));
  }
}
