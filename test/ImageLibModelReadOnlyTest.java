import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLib;
import model.imageLibrary.ReadOnlyImageLibModel;
import org.junit.Test;

import java.awt.Color;

public class ImageLibModelReadOnlyTest {

  @Test
  public void read() {
    Color[][] colorArray =
        new Color[][] {
          {new Color(255, 0, 0), new Color(0, 255, 0), new Color(0, 0, 255)},
          {new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0)}
        };
    ImageModel model = new ImageFile(colorArray);
    ImageLib lib = new ImageLib();
    lib.addToLib("12", model);
    UtilsTestUtils utils = new UtilsTestUtils();

    ReadOnlyImageLibModel readOnlyImageLibModel = lib;

    utils.compareTwoColorArrays(
        readOnlyImageLibModel.read("12").imageArrayCopy(), model.imageArrayCopy());
    colorArray[1][1] = new Color(0, 0, 0);
  }
}
