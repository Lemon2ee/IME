package model.enums;

/** All grey scale value that is now supported. */
public enum GreyScaleValue {
  R, // Grey Scale operation by its R channel value
  G, // Grey Scale operation by its G channel value
  B, // Grey Scale operation by its B channel value
  Value, // Grey Scale operation by its maximum channel value
  Intensity, // Grey Scale operation by its calculated intensity
  Luma, // Grey Scale operation by its calculated Luma
  Alpha, // Grey Scale operation by its Alpha channel value
  Sepia // Sepia tone operation NOT actually grey scale
}
