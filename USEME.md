## Getting started

Currently, we do not have support for GUI, so all operations have to be done in either interactive
mode or file mode.

### Interactive mode:

You can enter this mod by running the jar file (in the res directory) by either given no argument,
or append --interactive as the argument.

### File mode:

You can enter this mod by providing --file /path/to/command.txt as the argument of the jar file.

Please note:
Each line in the command file should only contain one command (see "Supported commands" above), You
can have empty line and comment start with #.'

#### File mode example:

Assume the jar, command file and image are in the same directory.

Content of cmd.txt: 


# load image.png in the same directory as image (name)
load image.png image
# brighten the image named image by 10 and store as
brighten 10 image image-brighten-10
save image-10.png image-brighten-10 // save the brightened image as image-10.png


Command to run the code (in the terminal and cd into the directory which contains the jar file 
and resources):

java -jar ood_IME.jar --file cmd.txt



#### To run the text based script:

You need to provide the following line as a command line argument to run the given script.

--file cmd.txt

You can replace cmd.txt with the path of any script you want to run. The script must be formatted,
that is, each line in the script should contain only one command.
