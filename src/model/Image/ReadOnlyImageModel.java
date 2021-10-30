package model.Image;

/** An interface represents a read only Image model which provides limited access. */
public interface ReadOnlyImageModel {
  /**
   * Return a deep copy of the current class with limited access.
   *
   * @return An ReadOnlyImageModel object with same content but different memory address
   */
  ReadOnlyImageModel copyReadOnly();

  /**
   * Convert an image to string.
   *
   * @return A single string that represents the content of a specific image
   */
  String imageToString();
}
