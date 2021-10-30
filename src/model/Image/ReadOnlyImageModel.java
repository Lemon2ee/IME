package model.Image;

/** An interface represents a read only Image model which provides limited access. */
public interface ReadOnlyImageModel {
  /**
   * Return a deep copy of the current class.
   *
   * @return An ImageModel object with same content but different memory address
   */
  ImageModel copy();

  /**
   * Convert an image to string.
   *
   * @return A single string that represents the content of a specific image
   */
  String imageToString();
}
