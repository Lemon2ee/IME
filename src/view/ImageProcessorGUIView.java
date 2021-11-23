package view;

/** An interface for the GUI view with new imageIcon method. */
public interface ImageProcessorGUIView extends ImageProcessorView {
  /**
   * Update the image icon to the one being provided.
   *
   * @param modifiedImage The given image name to be updated.
   */
  void updateImageIcon(String modifiedImage);
}
