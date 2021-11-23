import controller.IMEController;
import controller.IMEControllerPro;
import model.library.ImageLib;
import view.ImageProcessorGUIViewImpl;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;

/**
 * The entry point of the image processor, which contains a help message with --help and --file,
 * --interactive as two mode to start the program.
 */
public class ImageProcessor {
  /**
   * The main class which would initialize the image processing program.
   *
   * @param args the user provided arguments
   * @throws IllegalStateException when the program failed to transmit to the view object.
   */
  public static void main(String[] args) throws IllegalStateException {
    Readable readable = null;
    ImageLib model = new ImageLib();

    if (args.length == 0) {
      ImageProcessorView view = new ImageProcessorGUIViewImpl(model);
    } else {

      // the first argument
      String instruction = args[0];

      switch (instruction) {
        case "--help":
          StringBuilder help;
          help = new StringBuilder();
          help.append("A simple image processing program\n");
          help.append(
              "-file [filepath] to start the program with a script file where all command "
                  + "are stored\n");
          help.append("-text to start the program with interaction");
          System.out.print(help);
          return;
          // the file option allow the program go through a user provided text-based script
        case "-file":
          try {
            readable = new FileReader(args[1]);
          } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid argument (insufficient arguments)");
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Invalid argument (file not found)");
          }
          break;
          // interactive mode allow user to use system.in to interact with the program
        case "-text":
          readable = new InputStreamReader(System.in);
          break;
        default:
          break;
      }

      ImageProcessorView view = new ImageProcessorViewImpl(System.out);
      IMEController controller = new IMEControllerPro(model, readable, view);
      // run the program
      controller.initProcessor();
    }
  }
}
