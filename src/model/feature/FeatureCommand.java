package model.feature;

import java.awt.*;

public interface FeatureCommand {
  Color[][] apply(Color[][] image);
}
