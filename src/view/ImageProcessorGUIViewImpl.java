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
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ImageProcessorGUIViewImpl extends JFrame
    implements ImageProcessorView, ActionListener {
  private final ImageProcessorView delegate;
  private final IMEControllerGUI controller;
  private final ImageLib library;
  private final JLabel imageLabel;
  private final JLabel comboboxDisplay;
  private final JComboBox<String> combobox;
  private final Map<String, String> imageNameExtension;
  private final Map<String, BufferedImage> bufferedImageMap;

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
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
    // scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // exit program when JFrame is closed
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    // set up the west control panel
    // TODO: probably set the control panel to scroll panel
    JPanel controlPanel = new JPanel();
    controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(controlPanel);

    // set up the east image panel
    JPanel imagePanel = new JPanel();
    imagePanel.setBorder(BorderFactory.createTitledBorder("Image Display"));
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(imagePanel);

    // display the image as icon and show in the label
    imageLabel = new JLabel();
    JScrollPane imageScroll = new JScrollPane(imageLabel);
    imageScroll.setLayout(new ScrollPaneLayout());
    imageScroll.setPreferredSize(new Dimension(960, 600));
    imagePanel.add(imageScroll);

    /*
    Create a combo box in the control panel
     */
    comboboxDisplay = new JLabel("Select image");
    combobox = new JComboBox<String>();
    controlPanel.add(comboboxDisplay);
    // the event listener when an option is selected
    combobox.setActionCommand("select-image");
    combobox.addActionListener(this);
    this.updateCombobox();
    controlPanel.add(combobox);

    /*
    Create the load image button
    TODO: add in action performed
     */
    JPanel fileOpen = new JPanel();
    fileOpen.setLayout(new FlowLayout());
    controlPanel.add(fileOpen);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileOpen.add(fileOpenButton);

    /*
    Create save image button
    TODO: add in action performed
     */
    JPanel fileSave = new JPanel();
    fileSave.setLayout(new FlowLayout());
    controlPanel.add(fileSave);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("save file");
    fileSaveButton.addActionListener(this);
    fileSave.add(fileSaveButton);

    // frame display and config
    setVisible(true);
    setDefaultLookAndFeelDecorated(false);
  }

  @Override
  public void renderMessage(String message) {
    this.delegate.renderMessage(message);
  }

  // perform action with the controller
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Open file":
        {
          // copy and paste file selector panel from the swing demo
          final JFileChooser fileChooser = new JFileChooser(".");
          FileNameExtensionFilter filter =
              new FileNameExtensionFilter(
                  "Support for pro version controller", "jpg", "ppm", "png", "bmp");
          fileChooser.setFileFilter(filter);
          int retValue = fileChooser.showOpenDialog(this);
          if (retValue == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            String filePath = f.getPath();
            String name = JOptionPane.showInputDialog("Please input the image name");
            this.imageNameExtension.put(name, new ControllerUtils().getExtension(filePath));
            this.controller.acceptCommand("load " + filePath + " " + name);
            this.updateCombobox();
          }
        }
        break;
      case "save file":
        String str = "Something";
        break;
      case "select-image":
        String imageName = (String) this.combobox.getSelectedItem();
        Image iconImage =
            new ImageUtil()
                .color2dToImage(
                    this.library.read(imageName),
                    this.imageNameExtension.getOrDefault(imageName, "jpg"));
        this.imageLabel.setIcon(new ImageIcon(iconImage));
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
}
