# IME: Image Manipulation and Enhancement

### About

A simple image processing tool that is capable of manipulating PPM3，PNG, JPG and BMP file

### Code overview

Structure: In model package

- Package: library
    - ImageLibModel (interface, extend ReadOnlyImageLibModel)
        - Can add stuff to the library
    - ReadOnlyImageLibModel (interface)
        - Can read the content of the library with key
    - ImageLib (implements ImageLibModel)
        - A class which acts like a hashmap for <String, ImageModel>, but with better management
          (ReadOnly model).
- Package: image
    - ReadOnlyImageModel (interface)
        - Provides users with limited access to the ImageModel, that is, not being able to modify
          anything in the imageModel but can get information about it.
    - ImageModel (extend ReadOnlyImageModel)
        - Provides writable method to the users, acts like an interface which provides readable &
          writable permission.
- Package: enums
    - FlipDirection
        - The flip direction that has been provided.
    - GreyScaleValue
        - The available component for grey scale operations.
    - FilterType
        - The available types of filter to be applied on an image.
- Package: filter
    - IFilter (interface)
        - Provide necessary public method to apply the filter to the given 2d array of image data
          and generate a new 2d array after operation.
    - IFilterImpl (implements IFilter)
        - The class takes in a filter kernel in the constructor, and only public method is to apply
          the filter operation to the given image data.
- Package: feature
    - FeatureCommand (interface)
        - Provide necessary public method for all features as a function object that can be applied
          to the provided image data.
    - Package: basics
        - ChangeBrightness (implements FeatureCommand)
            - Change the brightness of the image by given value.
        - Flip (implements FeatureCommand)
            - Flip the image by the given flip direction.
        - GreyScale (implements FeatureCommand)
            - Basic operations of grey scale can use R, G, B, Intensity, Luma or average Value.
    - Package: pro
        - Filter (implements FeatureCommand)
            - Filter the image by specific filter kernel, can be either blur or sharpen.
        - ProGreyScale (implements FeatureCommand)
            - Advanced operations of grey scale can use the Alpha value or convert to Sepia tone.

Structure: In controller package

- Package: commands
    - IMECommand (interface)
        - Provides the execute command which accepts an ImageLibModel.
    - ABSCommand (abstract class which implements IMECommand)
        - Provides common filed required for implementation of IMECommand.
    - Brighten, Flip, Load, Save, ComponentGreyScale
        - Concrete classes which extends ABSCommand, which are the currently supported command
          (called by the controller).
- IMEController (interface)
    - Provides one function which initialize the program and process the input.
- IMEControllerBasic
    - The basic version of IMEController which supports rgb, luma, value, intensity greyscale,
      brighten, flip, load and save. = IMEControllerPro
    - Pro version of IMEController, with additional feature like blur, alpha-component, sharpen and
      sepia greyscale.

Structure: In view package

- ImageProcessorView (interface)
- ImageProcessorViewImpl
    - The implementation of ImageProcessorView, which only pass message to the given appendable
      object.
- ImageProcessorGUIViewImpl
    - Implementation of ImageProcessorView, which respond the gui display and action listener.

Structure: In utils package

- ControllerUtils (concrete class)
    - Contains method that are useful to controller.
- ImageUtil (concrete class)
    - Contains method that are useful to model and method related to IO.

ImageProcessor

- Contains the main method which takes in arguments to initialize the program accordingly, provide
  --help as arguments to see the help message (would be outputted on system.out)

#### Getting started

Please see USEME.md in the root directory.

#### Image Reference:

https://twitter.com/kyubumlee/status/1406091569854357509?s=20

Has been converted to PPM3 and cropped everything other than the center part of the picture to
reduce the size
