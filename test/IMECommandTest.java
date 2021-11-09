import controller.commands.Brighten;
import controller.commands.ComponentGreyScale;
import controller.commands.Flip;
import controller.commands.Save;
import controller.commands.IMECommand;
import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.image.ImageFile;
import model.library.ImageLib;
import model.library.ImageLibModel;
import org.junit.Test;
import utils.ImageUtil;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/** A test class of IMECommand which includes every concrete class which implement it. */
public class IMECommandTest {
  // brighten command test
  @Test(expected = IllegalArgumentException.class)
  public void BrightenTestInvalidExtra() {
    ArrayList<String> los = new ArrayList<>();
    los.add("brighten 20 something.ppm sth extra");
    IMECommand command = new Brighten(los.toArray(new String[5]));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void BrightenTestInvalidMissingArgs() {
    ArrayList<String> los = new ArrayList<>();
    los.add("brighten 20 something.ppm");
    IMECommand command = new Brighten(los.toArray(new String[3]));
  }

  @Test(expected = IllegalArgumentException.class)
  public void BrightenTestInvalidNotAnInt() {
    ArrayList<String> los = new ArrayList<>();
    los.add("brighten notAnInt something.ppm something-brighten");
    IMECommand command = new Brighten(los.toArray(new String[4]));
  }

  @Test(expected = IllegalArgumentException.class)
  public void BrightenTestInvalidImageMissing() {
    ArrayList<String> los = new ArrayList<>();
    los.add("brighten 100 something.ppm something-brighten");
    IMECommand command = new Brighten(los.toArray(new String[4]));
  }

  @Test
  public void BrightenTestSuccess() {
    ImageLibModel library = new ImageLib();
    library.addToLib("test", new ImageFile(new ImageUtil().readPPM("testRes/test.ppm")));
    String[] commandString = "brighten 100 test test-brighten".split(" ");
    IMECommand command = new Brighten(commandString);
    command.execute(library);

    assertTrue(
        new UtilsTestUtils()
            .compareTwoColorArrays(
                new ImageUtil().readPPM("testRes/test-brighter.ppm"),
                library.read("test-brighten").imageArrayCopy()));
  }

  // flip command test
  @Test(expected = IllegalArgumentException.class)
  public void FlipTestInvalidExtra() {
    String[] commandString = "vertical-flip 20 something.ppm sth extra".split(" ");
    IMECommand command = new Flip(commandString, FlipDirection.Horizontal);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void FlipTestInvalidMissingArgs() {
    String[] commandString = "vertical-flip test.ppm".split(" ");
    IMECommand command = new Flip(commandString, FlipDirection.Horizontal);
  }

  @Test
  public void FlipTestSuccess() {
    ImageLibModel library = new ImageLib();
    library.addToLib("test", new ImageFile(new ImageUtil().readPPM("testRes/test.ppm")));
    String[] commandString = "vertical-flip test test-verti".split(" ");
    IMECommand command = new Flip(commandString, FlipDirection.Vertical);
    command.execute(library);

    assertTrue(
        new UtilsTestUtils()
            .compareTwoColorArrays(
                new ImageUtil().readPPM("testRes/test-verti.ppm"),
                library.read("test-verti").imageArrayCopy()));
  }

  // grey scale command test
  @Test(expected = IllegalArgumentException.class)
  public void GreyTestInvalidExtra() {
    String[] commandString = "red-component test.ppm test extra".split(" ");
    IMECommand command = new ComponentGreyScale(commandString, GreyScaleValue.R);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void GreyTestInvalidMissingArgs() {
    String[] commandString = "blue-component test.ppm".split(" ");
    IMECommand command = new ComponentGreyScale(commandString, GreyScaleValue.B);
  }

  @Test
  public void GreyTestSuccess() {
    ImageLibModel library = new ImageLib();
    library.addToLib("test", new ImageFile(new ImageUtil().readPPM("testRes/test.ppm")));
    String[] commandString = "luma-component test test-luma".split(" ");
    IMECommand command = new ComponentGreyScale(commandString, GreyScaleValue.Luma);
    command.execute(library);

    assertTrue(
        new UtilsTestUtils()
            .compareTwoColorArrays(
                new ImageUtil().readPPM("testRes/test-gs-luma.ppm"),
                library.read("test-luma").imageArrayCopy()));
  }

  // grey scale command test
  @Test(expected = IllegalArgumentException.class)
  public void SaveTestInvalidExtra() {
    String[] commandString = "save test.ppm test extra".split(" ");
    IMECommand command = new Save(commandString);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void SaveTestInvalidMissingArgs() {
    String[] commandString = "save test.ppm".split(" ");
    IMECommand command = new Save(commandString);
  }

  @Test
  public void SaveTestSuccess() {
    ImageLibModel library = new ImageLib();
    library.addToLib("test", new ImageFile(new ImageUtil().readPPM("testRes/test.ppm")));
    String[] commandString = "save testRes/test-test.ppm test".split(" ");
    IMECommand command = new Save(commandString);
    command.execute(library);

    assertTrue(
        new UtilsTestUtils()
            .compareTwoColorArrays(
                new ImageUtil().readPPM("testRes/test-test.ppm"),
                library.read("test").imageArrayCopy()));
  }
}
