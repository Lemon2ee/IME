package controller;

/**
 * An interface represent the controller for an image processor, which contains only one method
 * to start initializing the controller of the program.
 */
public interface IMEController {
  /**
   * Initialize the controller to accept command inputs and perform the image processing.
   */
  void initProcessor();
}
