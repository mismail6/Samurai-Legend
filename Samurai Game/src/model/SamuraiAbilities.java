package model;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SamuraiAbilities {
  
  private Scene gameScene;
  private AnchorPane gamePane;
  private SamuraiControl samurai;
  public ImageView shuriken;
  private RotateTransition rotateShuriken;
  private boolean isThrown;
  
  AnimationTimer throwShuriken = new AnimationTimer() {//W
    @Override
    public void handle(long arg0) {
      if (isThrown) {
        shuriken.setTranslateX(shuriken.getTranslateX() + 3);
        if (samurai.getEnemyPos(shurikenGetX(), shurikenGetY(), shuriken.getTranslateX())) {
          //shuriken.setVisible(false);
          isThrown = false;
          gamePane.getChildren().remove(shuriken);
        }
        else if (shurikenGetX() > 1280) {
          isThrown = false;
          gamePane.getChildren().remove(shuriken);
        }
      }
    }
  };
  
  
  
  public SamuraiAbilities (Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    this.gameScene = gameScene;
    this.gamePane = gamePane;
    this.samurai = samurai;
    isThrown = false;
    createListeners();
    
    
  }
  private void createListeners() {
    gameScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
      if (event.getCode() == KeyCode.Q) {
        if (!isThrown) {
          initializeShuriken();
          isThrown = true;
        }
      }
    });
  }
  
  public double shurikenGetX() {
    return shuriken.getBoundsInParent().getCenterX();
  }
  public double shurikenGetY() {
    return shuriken.getBoundsInParent().getCenterY();
  }
  
  private void initializeShuriken() {
    shuriken = new ImageView(new Image("model/resources/shuriken1.png"));
    shuriken.setLayoutX(samurai.idle.getLayoutX() + 100);
    shuriken.setLayoutY(samurai.idle.getLayoutY() + 100);
    shuriken.setTranslateX(samurai.idle.getTranslateX());
    shuriken.setTranslateY(samurai.idle.getTranslateY());
    shuriken.setFitHeight(25);
    shuriken.setFitWidth(25);
    gamePane.getChildren().add(shuriken);
    
    rotateShuriken = new RotateTransition(Duration.millis(400), shuriken);
    rotateShuriken.setByAngle(360);
    rotateShuriken.setCycleCount(Animation.INDEFINITE);
    rotateShuriken.setInterpolator(Interpolator.LINEAR);
    
    throwShuriken.start();
    rotateShuriken.play();
  }
}
  
