package controller.commands;

import model.image.ImageFile;
import model.library.ImageLibModel;
import utils.ControllerUtils;
import utils.ImageUtil;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A concrete class which implements the IMECommand and provides the functionality of importing the
 * image to the program.
 */
public class Load extends ABSCommand {

  /**
   * Default constructor.
   *
   * @param commands The given array of commands which contains the origin and destination of the
   *     operation.
   */
  public Load(String[] commands) {
    super(commands[1], commands[2]);
    if (commands.length > 3) {
      throw new IllegalArgumentException("Invalid arguments length\n");
    }
  }

  /**
   * Load the image correspond from the origin in the given local with according to the extension in
   * the filepath.
   *
   * @param model The given ImageLibModel where the resources would come from.
   */
  @Override
  public void execute(ImageLibModel model) {
    Map<String, Function<String, Color[][]>> knownExtension = new HashMap<>();
    knownExtension.put(".ppm", (String s) -> new ImageUtil().readPPM(s));
    knownExtension.put(".png", (String s) -> new ImageUtil().imageIORead(s));
    knownExtension.put(".bmp", (String s) -> new ImageUtil().imageIORead(s));
    knownExtension.put(".jpg", (String s) -> new ImageUtil().imageIORead(s));

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
    model.addToLib(
        this.destination,
        new ImageFile(fileFormatSupportFunction.apply(this.origin.replace("\\\\", " "))));
  }
}
