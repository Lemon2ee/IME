import model.enums.FlipDirection;
import model.image.ImageFile;
import model.image.ImageModel;
import model.imageLibrary.ImageLib;
import model.imageLibrary.ImageLibModel;
import model.imageLibrary.ReadOnlyImageLibModel;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertNotEquals;

public class ImageLibTest {
  // no constructor test since it does not need one.

  // writeable and readable ImageLib tests

  @Test
  public void AddAndRead() {
    ImageLibModel library = new ImageLib();
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    library.addToLib("1", new ImageFile(array));
    new TestUtils().compareTwoColorArrays(array, library.read("1").imageArrayCopy());
  }

  @Test(expected = IllegalArgumentException.class)
  public void AddInvalid() {
    ImageLibModel library = new ImageLib();
    library.addToLib("1", new ImageFile(null));
  }

  @Test(expected = IllegalArgumentException.class)
  public void AddKeyInvalid() {
    ImageLibModel library = new ImageLib();
    library.addToLib(null, new ImageFile(new Color[4][4]));
  }

  @Test(expected = IllegalArgumentException.class)
  public void FindKeyNull() {
    ImageLibModel library = new ImageLib();
    library.read(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void FindKeyInvalid() {
    ImageLibModel library = new ImageLib();
    library.read("something does not exist");
  }

  // Read only model test
  @Test
  public void ReadOnlyAddAndRead() {
    ImageLibModel library = new ImageLib();
    ReadOnlyImageLibModel ReadLibrary = library;
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    library.addToLib("1", new ImageFile(array));
    new TestUtils().compareTwoColorArrays(array, ReadLibrary.read("1").imageArrayCopy());
  }

  // Read returns a deep copy
  @Test
  public void ReadOnlyDeepCopyAddAndRead() {
    ImageLibModel library = new ImageLib();
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    ImageModel model = new ImageFile(array);
    library.addToLib("1", model);

    ImageModel returnModel = library.read("1");
    returnModel.flip(FlipDirection.Horizontal);

    assertNotEquals(returnModel.imageArrayCopy(), model.imageArrayCopy());
  }
}
