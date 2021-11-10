package controller;

import controller.commands.Blur;
import controller.commands.ProComponentGreyScale;
import controller.commands.Sharpen;
import model.enums.GreyScaleValue;
import model.library.ImageLibModel;
import view.ImageProcessorView;

/**
 * A pro version of IMEControllerBasic with support of Alpha, Sepia greyscale and blurring,
 * sharpening.
 */
public class IMEControllerPro extends IMEControllerBasic {
  /**
   * The default constructor, but with additional command support.
   *
   * @param libModel The provided model which will handle all manipulation of images
   * @param readable The given readable object where all input would come from
   * @param view The given view object where essential message will be rendered
   */
  public IMEControllerPro(ImageLibModel libModel, Readable readable, ImageProcessorView view) {
    super(libModel, readable, view);
    super.knownCommands.put(
        "alpha-component", (String[] s) -> new ProComponentGreyScale(s, GreyScaleValue.Alpha));
    super.knownCommands.put(
        "sepia-component", (String[] s) -> new ProComponentGreyScale(s, GreyScaleValue.Sepia));
    super.knownCommands.put(
        "greyscale", (String[] s) -> new ProComponentGreyScale(s, GreyScaleValue.Luma));
    super.knownCommands.put(
        "sepia", (String[] s) -> new ProComponentGreyScale(s, GreyScaleValue.Sepia));
    super.knownCommands.put("blur", Blur::new);
    super.knownCommands.put("sharpen", Sharpen::new);
  }
}
