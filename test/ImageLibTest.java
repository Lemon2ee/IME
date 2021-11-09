import model.image.ImageFile;
import model.imageLibrary.ImageLib;
import model.imageLibrary.ImageLibModel;
import model.imageLibrary.ReadOnlyImageLibModel;
import org.junit.Test;

import java.awt.Color;

public class ImageLibTest {
  // no constructor test since it does not need one.

  // writeable and readable ImageLib tests

  @Test
  public void AddAndRead() {
    ImageLibModel library = new ImageLib();
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    library.addToLib("1", new ImageFile(array));
    new UtilsTestUtils().compareTwoColorArrays(array, library.read("1").imageArrayCopy());
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
    new UtilsTestUtils().compareTwoColorArrays(array, ReadLibrary.read("1").imageArrayCopy());
  }
}
