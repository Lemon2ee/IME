package controller.knownCommands;

import utils.ControllerUtils;
import model.image.ImageFile;
import utils.ImageUtil;
import model.imageLibrary.ImageLibModel;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Load extends ABSCommand {

  public Load(String origin, String destination) {
    super(origin, destination);
  }

  @Override
  public void execute(ImageLibModel model) throws IOException {
    Map<String, Function<String, Color[][]>> knownExtension = new HashMap<>();
    knownExtension.put(".ppm", (String s) -> new ImageUtil().readPPM(s));

    // get the extension
    String extension = new ControllerUtils().getExtension(this.origin);
    // find the correct ImageModel constructor in the knownFileExtension map
    Function<String, Color[][]> fileFormatSupportFunction =
        knownExtension.getOrDefault(extension.toLowerCase(), null);

    // throw exception if it is an unsupported file format
    if (fileFormatSupportFunction == null) {
      throw new IllegalArgumentException("Unsupported extension " + extension + "\n");
    }

    // if everything worked out fine, add the ImageModel to the library
    model.addToLib(this.destination, new ImageFile(fileFormatSupportFunction.apply(this.origin)));
  }
}
