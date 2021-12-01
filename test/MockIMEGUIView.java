import controller.IMEControllerGUI;
import view.ImageProcessorView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** A mock class of the IME GUI view, only the actionPerformed is being utilized in the test. */
public class MockIMEGUIView implements ImageProcessorView, ActionListener {
  private final IMEControllerGUI controllerGUI;

  /**
   * Mock class constructor that takes in the controller being used.
   *
   * @param controllerGUI1 the given controller for testing purposes (a controller contains other
   *     mocks)
   */
  public MockIMEGUIView(IMEControllerGUI controllerGUI1) {
    this.controllerGUI = controllerGUI1;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        controllerGUI.acceptCommand("load testRes/test.ppm test\n");
        break;
      case "flip":
        controllerGUI.acceptCommand("horizontal-flip test test-hori\n");
        break;
      default:
        break;
    }
  }

  @Override
  public void renderMessage(String message) {
    // does nothing
  }
}
