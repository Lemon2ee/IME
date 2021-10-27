import controller.IMEController;
import controller.IMEControllerImpl;
import model.ImageModel;
import model.PPMModel;
import view.ImageProcessorView;
import view.ImageProcessorViewImpl;

import java.io.IOException;
import java.io.InputStreamReader;

/** The class which */
public class ImageProcessor {
  public static void main(String[] args) throws IOException {
    ImageModel model = new PPMModel();
    ImageProcessorView view = new ImageProcessorViewImpl(System.out, model);
    IMEController controller = new IMEControllerImpl(model, new InputStreamReader(System.in), view);
    controller.initProcessor();
  }
}
