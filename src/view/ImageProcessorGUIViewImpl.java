package view;

import controller.IMEController;
import controller.IMEControllerGUI;
import controller.IMEControllerProGUI;
import model.library.ImageLib;
import utils.ControllerUtils;
import utils.ImageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.StringReader;
import java.nio.Buffer;
import java.util.*;
import java.util.function.Function;

public class ImageProcessorGUIViewImpl extends JFrame
    implements ImageProcessorView, ActionListener {
  private final ImageProcessorView delegate;
  private final IMEControllerGUI controller;
  private final ImageLib library;
  private final JLabel imageLabel;
  private final JComboBox<String> combobox;
  private final Map<String, String> imageNameExtension;
  private final Map<String, Image> bufferedImageMap;
  private final ArrayList<String> listOfGreyScale;
  private final ArrayList<String> flipDirections;

  public ImageProcessorGUIViewImpl(Appendable appendable, ImageLib library) {
    // initialize
    super();
    this.delegate = new ImageProcessorViewImpl(Objects.requireNonNull(appendable));
    this.library = Objects.requireNonNull(library);
    this.controller = new IMEControllerProGUI(this.library, this);
    this.imageNameExtension = new HashMap<>();
    this.bufferedImageMap = new HashMap<>();

    // set gui dimension and relative info
    setTitle("Image Processor");
    setSize(new Dimension(1280, 720));

    // create new panel
    JPanel mainPanel = new JPanel();
    // for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BorderLayout());
    // scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // exit program when JFrame is closed
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // set up the west control panel
    // TODO: probably set the control panel to scroll panel
    JPanel controlPanel = new JPanel();
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
    controlPanel.setLayout(new BorderLayout());
    mainPanel.add(controlPanel, BorderLayout.LINE_START);

    // set up the east image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Display"));
    imagePanel.setLayout(new BorderLayout());
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    /*
    Create a combo box in the control panel
     */
    combobox = new JComboBox<String>();
    // the event listener when an option is selected
    combobox.setActionCommand("select-image");
    combobox.addActionListener(this);
    this.updateCombobox();
    controlPanel.add(combobox, BorderLayout.PAGE_START);

    // set up the west operation panel in control panel
    JPanel operationPanel = new JPanel();
    operationPanel.setBorder(BorderFactory.createTitledBorder("Operation Panel"));
    operationPanel.setLayout(new BoxLayout(operationPanel, BoxLayout.PAGE_AXIS));
    controlPanel.add(operationPanel, BorderLayout.CENTER);

    // display the image as icon and show in the label
    imageLabel = new JLabel();
    JScrollPane imageScroll = new JScrollPane(imageLabel);
    imageScroll.setLayout(new ScrollPaneLayout());
    imageScroll.setPreferredSize(new Dimension(960, 600));
    imagePanel.add(imageScroll, BorderLayout.CENTER);

    this.listOfGreyScale = new ArrayList<>();
    this.flipDirections = new ArrayList<>();

    String[] supported = {
      "load",
      "save",
      "brighten",
      "red-component",
      "green-component",
      "blue" + "-component",
      "intensity-component",
      "value-component",
      "luma-component",
      "horizontal" + "-flip",
      "vertical-flip",
      "alpha-component",
      "sepia-component",
      "greyscale",
      "sepia",
      "blur",
      "sharpen"
    };

    for (String str : supported) {
      if (str.contains("component")) {
        listOfGreyScale.add(str);
      } else if (str.contains("flip")) {
        flipDirections.add(str);
      } else {
        JButton operationButton = new JButton(str);
        operationButton.setActionCommand(str);
        operationButton.addActionListener(this);
        operationPanel.add(operationButton);
      }
    }

    JButton componentGreyScale = new JButton("Component GreyScale");
    componentGreyScale.setActionCommand("component-greyscale");
    componentGreyScale.addActionListener(this);
    operationPanel.add(componentGreyScale);

    /*
    Place to display histogram.
     */
    JPanel histogram = new JPanel();
    controlPanel.add(histogram, BorderLayout.PAGE_END);
    histogram.setPreferredSize(new Dimension(290, 200));

    // frame display and config
    setVisible(true);
    setDefaultLookAndFeelDecorated(false);
  }

  // displaying message with a popup
  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(
        this, "Error message: " + message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  // perform action with the controller
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        {
          // copy and paste file selector panel from the swing demo
          final JFileChooser fileChooser = new JFileChooser(".");
          FileNameExtensionFilter filter =
              new FileNameExtensionFilter(
                  "Supported by the pro version controller", "jpg", "ppm", "png", "bmp");
          fileChooser.setFileFilter(filter);
          int retValue = fileChooser.showOpenDialog(this);
          if (retValue == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            String filePath = f.getPath();
            String name = JOptionPane.showInputDialog(this, "Please input the image name");
            this.imageNameExtension.put(name, new ControllerUtils().getExtension(filePath));
            this.controller.acceptCommand("load " + filePath + " " + name);
            this.updateCombobox();
            this.updateImageIcon(false);
          }
        }
        break;
      case "save":
        String name = (String) this.combobox.getSelectedItem();
        String filePath =
            JOptionPane.showInputDialog(
                this, "Please provide name and path of image you want to save to");
        this.controller.acceptCommand("save " + filePath + " " + name);
        break;
      case "select-image":
        this.updateImageIcon(false);
        break;
      case "component-greyscale":
        String imageNameGreyScale = (String) this.combobox.getSelectedItem();
        this.componentGreyScaleAction(imageNameGreyScale);
        break;
      default:
        break;
    }
    System.out.println(e.getActionCommand());
  }

  private void updateCombobox() {
    // fetch image name from image library and add to the select box
    for (String imageName : this.library.getKeys()) {
      if (((DefaultComboBoxModel<String>) combobox.getModel()).getIndexOf(imageName) == -1) {
        combobox.addItem(imageName);
      }
    }
  }

  private void updateImageIcon(boolean operationOnExistImage) {
    String imageNameSelect = this.returnSelectedName();
    Image resultImage;

    resultImage =
        new ImageUtil()
            .color2dToImage(
                this.library.read(imageNameSelect),
                this.imageNameExtension.getOrDefault(imageNameSelect, "jpg"));
    this.bufferedImageMap.put(imageNameSelect, resultImage);

    this.imageLabel.setIcon(new ImageIcon(resultImage));
  }

  private String returnSelectedName() {
    return (String) this.combobox.getSelectedItem();
  }

  private void selectImageAction(String selectedImage) {
    this.updateImageIcon(false);
  }

  private void componentGreyScaleAction(String selectedImage) {
    String filePathGreyScale =
        (String)
            JOptionPane.showInputDialog(
                this,
                "Please select one of the following supported greyscale component",
                "Title",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(),
                this.listOfGreyScale.toArray(),
                "green-component");
    String fileSaveGreyscale =
        JOptionPane.showInputDialog(
            this, "Please provide name and path of image you want to save to");
    this.controller.acceptCommand(
        filePathGreyScale + " " + selectedImage + " " + fileSaveGreyscale);
    this.updateCombobox();
    this.updateImageIcon(
        (((DefaultComboBoxModel<String>) combobox.getModel()).getIndexOf(selectedImage) != -1));
  }
}
