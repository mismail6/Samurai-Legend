package control;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.geometry.Rectangle2D;

public class CharacterControl extends AnchorPane{
  int offsetY = 0;
  int score = 0;
  
  public CharacterAnimation animation;
  
  public CharacterControl(ImageView imageView, int timeD, int count, int columns, boolean isRepeated, int offsetX, int width, int height) {
    imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    animation = new CharacterAnimation(imageView, count, columns, offsetX, offsetY, width, height, Duration.millis(timeD), isRepeated);
    animation.play();
    
    getChildren().addAll(imageView);
  }
  public void moveX(double x) {
    this.setTranslateX(this.getTranslateX()+x);
  }
  
  public void moveY(double y) {
    this.setTranslateY(this.getTranslateY()+y);
  }
  
  public void play() {
    animation.play();
  }
  
  public void stop() {
    animation.stop();
  }
  
  public CharacterAnimation getAnimation() {
    return animation;
  }
    
 }
  
  
