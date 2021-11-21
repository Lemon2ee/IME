package view;

import java.awt.*;
import java.util.Objects;

import javax.swing.JPanel;

/**
 * The subclass of JPanel. Used to visualize the histogram data of the given image.
 */
public class HistogramPanel extends JPanel {
  private static final int BOARDER = 5;
  private static final int HEIGHT = 200;
  private final int[][] histogram;
  private int maxVal;

  /**
   * To create an instance of the HistogramPanel with the provided data.
   *
   * @param histogram the given histogram data as a 4 * 256 array of int
   */
  public HistogramPanel(int[][] histogram) {
    this.histogram = Objects.requireNonNull(histogram);
    this.maxVal = 0;

    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 256; c++) {
        maxVal = Math.max(histogram[r][c], maxVal);
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    double ratio = (double) HEIGHT / this.maxVal;
    int y1;
    int y2;
    for (int x = 0; x < 255; x++) {
      g.setColor(Color.RED);
      y1 = (int) Math.round(this.histogram[0][x] * ratio);
      y2 = (int) Math.round(this.histogram[0][x + 1] * ratio);
      g.drawLine(BOARDER + x, BOARDER + HEIGHT - y1, BOARDER + x + 1, BOARDER + HEIGHT - y2);

      g.setColor(Color.GREEN);
      y1 = (int) Math.round(this.histogram[1][x] * ratio);
      y2 = (int) Math.round(this.histogram[1][x + 1] * ratio);
      g.drawLine(BOARDER + x, BOARDER + HEIGHT - y1, BOARDER + x + 1, BOARDER + HEIGHT - y2);

      g.setColor(Color.BLUE);
      y1 = (int) Math.round(this.histogram[2][x] * ratio);
      y2 = (int) Math.round(this.histogram[2][x + 1] * ratio);
      g.drawLine(BOARDER + x, BOARDER + HEIGHT - y1, BOARDER + x + 1, BOARDER + HEIGHT - y2);

      g.setColor(Color.BLACK);
      y1 = (int) Math.round(this.histogram[3][x] * ratio);
      y2 = (int) Math.round(this.histogram[3][x + 1] * ratio);
      g.drawLine(BOARDER + x, BOARDER + HEIGHT - y1, BOARDER + x + 1, BOARDER + HEIGHT - y2);
    }
  }
}
