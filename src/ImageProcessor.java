import controller.IMEController;
import controller.IMEControllerImpl;
import model.ImageModel;
import model.PPMModel;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/** The class which */
public class ImageProcessor {
  public static void main(String[] args) throws IOException {
    ImageModel model = null;
    Readable readable = null;
    if (args.length == 0) {
      throw new IllegalArgumentException(
          "Requires at least one argument, use --help to learn the commands");
    }

    String instruction = args[0];

    switch (instruction) {
      case "--help":
        StringBuilder help;
        help = new StringBuilder();
        help.append("A simple image processing program\n");
        help.append(
            "--file [filepath] to start the program with a script file where all command "
                + "are stored\n");
        help.append("--interactive to start the program with interaction");
        System.out.print(help.toString());
        return;
      case "--file":
        try {
          readable = new FileReader(args[1]);
        } catch (IndexOutOfBoundsException e) {
          throw new IllegalArgumentException("Invalid argument (insufficient arguments)");
        } catch (FileNotFoundException e) {
          throw new IllegalArgumentException("Invalid argument (file not found)");
        }
        break;
      default:
        break;
    }
    model = new PPMModel();
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, model);
    IMEController controller = new IMEControllerImpl(model, readable, view);
    controller.initProcessor();
  }
}
