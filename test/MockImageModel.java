import model.enums.FlipDirection;
import model.enums.GreyScaleValue;
import model.ImageModel;

public class MockImageModel implements ImageModel {
  private final StringBuilder log;

  public MockImageModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void load(String name, String filePath) throws IllegalArgumentException {
    this.log.append("name ").append(name).append(" filePath ").append(filePath);
  }

  @Override
  public void greyScale(String origin, String destination, GreyScaleValue op)
      throws IllegalArgumentException {
    this.log
        .append("Origin ")
        .append(origin)
        .append(" Destination ")
        .append(destination)
        .append(" op ")
        .append(op);
  }

  @Override
  public void changeBrightness(String origin, String destination, int value)
      throws IllegalArgumentException {
    this.log
        .append("Origin ")
        .append(origin)
        .append(" Destination ")
        .append(destination)
        .append(" value ")
        .append(value);
  }

  @Override
  public void flip(String origin, String destination, FlipDirection fd)
      throws IllegalArgumentException {
    this.log
        .append("Origin ")
        .append(origin)
        .append(" Destination ")
        .append(destination)
        .append(" fd ")
        .append(fd);
  }

  @Override
  public void save(String filePath, String origin) throws IllegalArgumentException {
    this.log.append("filePath ").append(filePath).append(" origin ").append(origin);
  }
}
