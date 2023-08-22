package control;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class CharacterAnimation extends Transition{
  
  private final ImageView imageView;
  private final int count;
  private final int columns;
  private int offsetX;
  private int offsetY;
  private final int width;
  private final int height;
  private int lastIndex;
  
  public CharacterAnimation(ImageView imageView, int count, int columns, int offsetX, int offsetY, int width, int height, Duration duration, boolean isRepeated) {
    this.imageView = imageView;
    this.count = count;
    this.columns = columns;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
    
    //method
    setCycleDuration(duration);
    if (isRepeated) {
      setCycleCount(Animation.INDEFINITE);
    }
    setInterpolator(Interpolator.LINEAR);
  }
  
  public void setoffsetX(int x) {
    this.offsetX = x;
  }
  public void setoffsetY(int y) {
    this.offsetY = y;
  }

  @Override
  protected void interpolate(double frac) {
    final int index = Math.min((int) Math.floor(frac * count), count - 1);
    if (index != lastIndex) {
        final int x = (index % columns) * width  + offsetX;
        final int y = (index / columns) * height + offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
        lastIndex = index;
    }
  }
}
