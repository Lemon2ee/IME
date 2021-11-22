package view;

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
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.function.Function;

/**
 * An ImageProcessorGUIView implementation which acts as the GUI of this program. The default
 * windows size is set to be 1280*720 which can be resized when needed, but only the image display
 * panel would be resized, the control panel (AKA. the place where all buttons and histogram exist.)
 * would not change. All panel and component would not be scaled to fit the window size.
 */
public class ImageProcessorGUIViewImpl extends JFrame
    implements ImageProcessorGUIView, ActionListener {
  private final IMEControllerGUI controller;
  private final ImageLib library;
  private final JLabel imageLabel;
  private final JComboBox<String> combobox;
  private final Map<String, String> imageNameExtension;
  private final Map<String, Image> bufferedImageMap;
  private final ArrayList<String> listOfGreyScale;
  private final ArrayList<String> flipDirections;
  private final JPanel histogram;

  /**
   * The default constructor where all panel and frame would be initialized.
   *
   * @param library The given image library where all image resources would be stored.
   */
  public ImageProcessorGUIViewImpl(ImageLib library) {
    // initialize
    super();
    this.library = Objects.requireNonNull(library);
    this.controller = new IMEControllerProGUI(this.library, this);
    this.imageNameExtension = new HashMap<>();
    this.bufferedImageMap = new HashMap<>();
    this.listOfGreyScale = new ArrayList<>();
    this.flipDirections = new ArrayList<>();

    // set gui dimension and relative info
    setTitle("Image Processor");
    setSize(new Dimension(1280, 720));

    // create the master panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // set up the west control panel
    JPanel controlPanel = new JPanel();
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
    controlPanel.setLayout(new BorderLayout());
    mainPanel.add(controlPanel, BorderLayout.LINE_START);

    // set up the east image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Display"));
    imagePanel.setLayout(new BorderLayout());
    mainPanel.add(imagePanel, BorderLayout.CENTER);

    // Add image selection box to the top of control panel
    combobox = new JComboBox<>();
    combobox.setActionCommand("select-image");
    combobox.addActionListener(this);
    this.updateCombobox();
    controlPanel.add(combobox, BorderLayout.PAGE_START);

    // set up the center operation panel in control panel
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

    List<String> commands = new ArrayList<>(Arrays.asList("load", "save"));

    Set<String> listOfCommand = this.controller.getSupportCommands();
    commands.forEach(listOfCommand::remove);
    commands.addAll(listOfCommand);
    commands.sort(Comparator.comparing(String::length));

    // create supported buttons
    for (String str : commands) {
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

    // create separate greyscale button
    JButton componentGreyScale = new JButton("Component GreyScale");
    componentGreyScale.setActionCommand("component-greyscale");
    componentGreyScale.addActionListener(this);
    operationPanel.add(componentGreyScale);

    // create separate flip button
    JButton flip = new JButton("Flip");
    flip.setActionCommand("flip");
    flip.addActionListener(this);
    operationPanel.add(flip);

    /*
    Place to display histogram.
    TODO: add histogram
     */
    histogram = new JPanel(new BorderLayout());
    controlPanel.add(histogram, BorderLayout.PAGE_END);
    histogram.setPreferredSize(new Dimension(290, 200));

    // frame display and config
    setVisible(true);
    setDefaultLookAndFeelDecorated(false);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  /**
   * Pop up message box when error message being received.
   *
   * @param message the message to be transmitted
   */
  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(
        this, "Error message: " + message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Perform actions correspondingly.
   *
   * @param e the given action event as string
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        this.loadAction();
        break;
      case "save":
        this.saveAction();
        break;
      case "select-image":
        this.selectAction();
        break;
      case "component-greyscale":
        this.componentGSAction();
        break;
      case "flip":
        this.flipAction();
        break;
      case "blur":
        this.simpleAction("blur");
        break;
      case "sharpen":
        this.simpleAction("sharpen");
        break;
      case "sepia":
        this.simpleAction("sepia");
        break;
      case "greyscale":
        this.simpleAction("greyscale");
        break;
      case "brighten":
        this.brightenAction();
        break;
      default:
        break;
    }
  }

  private void brightenAction() {
    String image = (String) this.combobox.getSelectedItem();

    String value =
        JOptionPane.showInputDialog(
            this, "Please provide the value you wish to brighten (negative to darken)");

    Integer brightenValue = null;
    String imageSaveLocation = null;

    try {
      if (value != null) {
        brightenValue = Integer.parseInt(value);
      }
    } catch (NumberFormatException e) {
      this.renderMessage("Wrong input format for brighten value");
    }

    if (brightenValue != null) {
      imageSaveLocation =
          JOptionPane.showInputDialog(
              this, "Please provide name and path of image you want to save to");
    }

    if (imageSaveLocation != null) {
      this.controller.acceptCommand(
          "brighten" + " " + brightenValue + " " + image + " " + imageSaveLocation);
      this.updateCombobox();
      this.updateImageIcon(imageSaveLocation, true);
      this.updateHistogram();
    }
  }

  private void simpleAction(String filterType) {
    String image = (String) this.combobox.getSelectedItem();

    String imageSaveLocation =
        JOptionPane.showInputDialog(
            this, "Please provide name and path of image you want to save to");

    if (imageSaveLocation != null) {
      this.controller.acceptCommand(filterType + " " + image + " " + imageSaveLocation);
      this.updateCombobox();
      this.updateImageIcon(imageSaveLocation, true);
      this.updateHistogram();
    }
  }

  private void flipAction() {
    String image = (String) this.combobox.getSelectedItem();
    String imageSaveLocation = null;
    String flipDirection;

    flipDirection =
        (String)
            JOptionPane.showInputDialog(
                this,
                "Please select a flip direction",
                "Direction selection",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(),
                this.flipDirections.toArray(),
                "");

    if (flipDirection != null) {
      imageSaveLocation =
          JOptionPane.showInputDialog(
              this, "Please provide name and path of image you want to save to");
    }

    if (imageSaveLocation != null) {
      this.controller.acceptCommand(flipDirection + " " + image + " " + imageSaveLocation);
      this.updateCombobox();
      this.updateImageIcon(imageSaveLocation, true);
      this.updateHistogram();
    }
  }

  private void componentGSAction() {
    String imageNameGreyScale = (String) this.combobox.getSelectedItem();
    this.componentGreyScaleAction(imageNameGreyScale);
  }

  private void selectAction() {
    this.updateImageIcon(this.returnSelectedName(), false);
    this.updateHistogram();
  }

  private void saveAction() {
    String name = (String) this.combobox.getSelectedItem();
    String filePath =
        JOptionPane.showInputDialog(
            this, "Please provide name and path of image you want to save to");
    if (filePath != null) {
      this.controller.acceptCommand("save " + filePath + " " + name);
    }
  }

  private void loadAction() {
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
      this.controller.acceptCommand("load " + filePath.replace(" ", "\\\\") + " " + name);
      this.updateCombobox();
      this.updateImageIcon(name, false);
      this.updateHistogram();
    }
  }

  private void updateCombobox() {
    // fetch image name from image library and add to the select box
    for (String imageName : this.library.getKeys()) {
      if (((DefaultComboBoxModel<String>) combobox.getModel()).getIndexOf(imageName) == -1) {
        combobox.addItem(imageName);
      }
    }
  }

  public void updateImageIcon(String modifiedImage, boolean modified) {
    Image resultImage;

    resultImage = this.bufferedImageMap.getOrDefault(modifiedImage, null);

    if (resultImage == null || modified) {
      resultImage =
          new ImageUtil()
              .color2dToImage(
                  this.library.read(modifiedImage),
                  this.imageNameExtension.getOrDefault(modifiedImage, "jpg"));
    }

    this.bufferedImageMap.put(modifiedImage, resultImage);
    if (Objects.equals(modifiedImage, this.returnSelectedName())) {
      this.imageLabel.setIcon(new ImageIcon(resultImage));
    }
  }

  private String returnSelectedName() {
    return (String) this.combobox.getSelectedItem();
  }

  private void componentGreyScaleAction(String selectedImage) {
    String fileSaveGreyscale = null;
    String filePathGreyScale;

    filePathGreyScale =
        (String)
            JOptionPane.showInputDialog(
                this,
                "Please select one of the following supported greyscale component",
                "Title",
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon(),
                this.listOfGreyScale.toArray(),
                "");

    if (filePathGreyScale != null) {
      fileSaveGreyscale =
          JOptionPane.showInputDialog(
              this, "Please provide name and path of image you want to save to");
    }

    if (filePathGreyScale != null && fileSaveGreyscale != null) {
      this.controller.acceptCommand(
          filePathGreyScale + " " + selectedImage + " " + fileSaveGreyscale);
      this.updateCombobox();
      this.updateImageIcon(fileSaveGreyscale, true);
      this.updateHistogram();
    }
  }

  private void updateHistogram() {
    String imageNameSelect = this.returnSelectedName();
    int[][] histogram =
        new ImageUtil().histogram(this.library.read(imageNameSelect).imageArrayCopy());
    JPanel histogramPanel = new HistogramPanel(histogram);
    this.histogram.removeAll();
    this.histogram.add(histogramPanel, BorderLayout.CENTER);
    this.histogram.revalidate();
    this.histogram.repaint();
  }
}
