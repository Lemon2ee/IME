## Getting started

Currently, we do not have support for GUI, so all operations have to be done in either interactive
mode or file mode.

## Supported commands:

- load /path/to/image image_name // read local image file and store it as the name provided.
- save /path/to/store image_name // store image corresponding to the give image_name to the
  designated file path
- brighten value image_name_source image_name_dest // brighten the image with name
  "image_name_source" by the given value (negative means darken) and store the result as
  image_name_dest.
- xxx-component image_name_source image_name_dest // convert the image with name
  "image_name_source" according to the given grey scale value (xxx, which can be one of the
  following) and store it as image_name_dest
    - red
    - green
    - blue
    - alpha
    - luma
    - intensity
    - value
    - sepia
- blur image_name_source image_name_dest // apply blurring effect to the image with name
  image_name_source and store it as image_name_dest
- sharpen image_name_source image_name_dest // apply sharpening effect to the image with name
  image_name_source and store it as image_name_dest
- foo-flip image_name_source image_name_dest // flip the image with name 
  "image_name_source" according to the given foo (foo is one of the following) and store it as 
  "image_name_dest" 
  - vertical
  - horizontal
  

### Interactive mode:

You can enter this mod by running the jar file (in the res directory) by either given no argument,
or append `--interactive` as the argument.

### File mode:

You can enter this mod by providing `--file /path/to/command.txt` as the argument of the jar file.

Please note:
Each line in the command file should only contain one command (see "Supported commands" above), You
can have empty line and comment start with #.'

#### File mode example:

Assume the jar, command file and image are in the same directory.

Content of cmd.txt:

```
# load image.png in the same directory as image (name)
load image.png image
# brighten the image named image by 10 and store as
brighten 10 image image-brighten-10
# save the brightened image as image-10.png
save image-10.png image-brighten-10
```

Command to run the code (in the terminal and cd into the directory which contains the jar file and
resources):

`java -jar ood_IME.jar --file cmd.txt`

