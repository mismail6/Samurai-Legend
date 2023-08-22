package control;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class SubScenery extends SubScene{
  
  private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
  private final static String BACKGROUND_IMAGE = "model/resources/bi3.jpg";
  
  public SubScenery() {
    super(new AnchorPane(), 1280, 720);
    prefWidth(1280);
    prefHeight(720);
    
    BackgroundImage bImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 1280, 720, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
    AnchorPane root = (AnchorPane) this.getRoot();
    root.setBackground(new Background(bImage));
    setLayoutY(-720);
  }
  public void moveSubScene() {
    TranslateTransition transition = new TranslateTransition();
    transition.setDuration(Duration.seconds(0.4));
    transition.setNode(this);
    transition.setToY(720);
    transition.play();
  }

}
