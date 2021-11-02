import model.enums.FlipDirection;
import model.image.ImageFile;
import model.image.ImageModel;
import model.image.ReadOnlyImageModel;
import model.imageLibrary.ImageLib;
import model.imageLibrary.ReadOnlyImageLibModel;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class ReadOnlyImageLibModelTest {

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
    TestUtils utils = new TestUtils();

    ReadOnlyImageLibModel readOnlyImageLibModel = lib;

    utils.compareTwoColorArrays(
        readOnlyImageLibModel.read("12").imageArrayCopy(), model.imageArrayCopy());
    colorArray[1][1] = new Color(0, 0, 0);
    assertNotEquals(
        readOnlyImageLibModel.read("12").flip(FlipDirection.Horizontal),
        readOnlyImageLibModel.read("12"));
  }
}
