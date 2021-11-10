import model.image.ImageFile;
import model.library.ImageLib;
import model.library.ImageLibModel;
import model.library.ReadOnlyImageLibModel;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

/**
 * The test class which verify ImageLib works as desired. Including reading and adding to the
 * library
 */
public class ModelImageLibTest {
  // no constructor test since it does not need one.

  // writeable and readable ImageLib tests

  @Test
  public void AddAndRead() {
    ImageLibModel library = new ImageLib();
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    library.addToLib("1", new ImageFile(array));
    boolean boo =
        new UtilsTestUtils().compareTwoColorArrays(array, library.read("1").imageArrayCopy());
    assertTrue(boo);
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
    ReadOnlyImageLibModel readLibrary = library;
    Color[][] array = new Color[1][1];
    array[0][0] = new Color(244);
    library.addToLib("1", new ImageFile(array));
    boolean boo =
        new UtilsTestUtils().compareTwoColorArrays(array, readLibrary.read("1").imageArrayCopy());
    assertTrue(boo);
  }
}
