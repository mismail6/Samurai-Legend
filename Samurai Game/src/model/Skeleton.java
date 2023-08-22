package model;

import control.CharacterControl;
import control.Links;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.GameOver;

public class Skeleton {
  
  private Stage mainStage;
  private Scene mainScene;
  private Scene gameScene;
  private AnchorPane gamePane;
  
  private SamuraiControl samurai;
  private CharacterControl skeletonWalk;
  private CharacterControl skeletonAtk;
  private CharacterControl skeletonTakeHit;
  private CharacterControl skeletonDeath;
  private CharacterControl skeletonRevive;
  private boolean inZone;
  private boolean nearSamurai;
  private boolean isPlayed;
  private boolean takingHit;
  private boolean setScene;
  private boolean started;
  public boolean isDead;
  private boolean dying;
  public boolean isWave9;
  
  private double layoutX;
  private double layoutY;
  private double moveSpeed = 0.25;
  private int flinchY;
  private int health;
  
  AnimationTimer animate = new AnimationTimer() {
    @Override
    public void handle(long arg0) {
      if (getY() >= samurai.getY()) {
        setOnTop();
      }
      else {
        setOnBottom();
      }
      if (!inZone && !takingHit && !isDead) {
        skeletonWalk.setVisible(true);
        skeletonAtk.setVisible(false);
        skeletonAtk.animation.stop();
        isPlayed = false;
        if (getX() < 0.0) {
          samurai.setGameOver();
        }
        skeletonWalk.moveX(-moveSpeed);
      }
      
      if ( Math.abs(getY() - samurai.getY()) < 140 && Math.abs(getX() - samurai.getX()) < 140 && (getX() - samurai.getX()) > 0 && !takingHit && !isDead) { // in Zone
         inZone = true;
         skeletonWalk.setVisible(true);
         skeletonAtk.setVisible(false);
         if (!nearSamurai) {
           skeletonAtk.animation.pause();
           isPlayed = false;
         }
         if ( (getY() - samurai.getY()) < -10  + flinchY) {
             nearSamurai = false;
            skeletonWalk.moveY(moveSpeed);
         }
         else if ( (getY() - samurai.getY()) > 10 + flinchY) {
           nearSamurai = false;
           skeletonWalk.moveY(-moveSpeed);
         }
         
         if ( (getX() - samurai.getX()) > 48 ) {
           nearSamurai = false;
           skeletonWalk.moveX(-moveSpeed);
         }
         else if ( (getX() - samurai.getX()) <= 51 && Math.abs(getY() - samurai.getY()) < 13 + flinchY) {// Skeleton in attack range
           nearSamurai = true;
           skeletonWalk.setVisible(false);
           skeletonAtk.setTranslateX(skeletonWalk.getTranslateX());
           skeletonAtk.setTranslateY(skeletonWalk.getTranslateY());
           skeletonAtk.setVisible(true);
           if (!isPlayed) {
             skeletonAtk.animation.playFromStart();
             isPlayed = true;
           }
         }
      }
      else {
        inZone = false;
      }
    }  
  };
  
  
  public Skeleton (Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane mainPane, SamuraiControl samurai, double layoutX, double layoutY, int flinchY) {
    this.mainStage = mainStage;
    this.mainScene = mainScene; 
    this.gameScene = gameScene;
    this.gamePane = mainPane;
    this.samurai = samurai;
    this.layoutX = layoutX;
    this.layoutY = layoutY;
    this.flinchY = flinchY;
    inZone = false;
    nearSamurai = false;
    isPlayed = false;
    takingHit = false;
    setScene = false;
    started = false;
    isDead = false;
    dying = false;
    isWave9 = false;
    initialize();
    skeletonWalk.stop();
    skeletonAtk.stop();
    skeletonTakeHit.stop();
    skeletonDeath.stop();
    skeletonRevive.stop();
    createListeners();
    health = 50;
    
  }
  
  private void initialize() {
    ImageView skeletonWalkView = new ImageView(new Image(Links.sWalk));
    ImageView skeletonAtkView = new ImageView(new Image(Links.sAtk));
    ImageView skeletonTakeHitView = new ImageView(new Image(Links.sTakeHit));
    ImageView skeletonDeathView = new ImageView(new Image(Links.sDie));
    ImageView skeletonReviveView = new ImageView(new Image(Links.sRevive));
    
    skeletonWalk = new CharacterControl(skeletonWalkView, 600, 4, 4, true, 0, 150, 150);
    skeletonAtk = new CharacterControl(skeletonAtkView, 600, 8, 8, false, 0, 150, 150);
    skeletonTakeHit = new CharacterControl(skeletonTakeHitView, 200, 4, 4, false, 0, 150, 150);
    skeletonDeath = new CharacterControl(skeletonDeathView, 950, 4, 4, false, 0, 150, 150);
    skeletonRevive = new CharacterControl(skeletonReviveView, 950, 4, 4, false, 0, 150, 150);
    
    gamePane.getChildren().add(skeletonWalk);
    gamePane.getChildren().add(skeletonAtk);
    gamePane.getChildren().add(skeletonTakeHit);
    gamePane.getChildren().add(skeletonDeath);
    gamePane.getChildren().add(skeletonRevive);
    skeletonWalk.setLayoutX(layoutX);
    skeletonWalk.setLayoutY(layoutY);
    skeletonDeath.setLayoutX(layoutX);
    skeletonDeath.setLayoutY(layoutY);
    skeletonRevive.setLayoutX(layoutX);
    skeletonRevive.setLayoutY(layoutY);
    skeletonAtk.setLayoutX(layoutX);
    skeletonAtk.setLayoutY(layoutY);
    skeletonTakeHit.setLayoutX(layoutX);
    skeletonTakeHit.setLayoutY(layoutY);
    skeletonAtk.setVisible(false);
    skeletonTakeHit.setVisible(false);
    skeletonDeath.setVisible(false);
    skeletonRevive.setVisible(false);
  }
  public void removeSelf() {
    if (!isWave9) {
      if (skeletonDeath.animation.getStatus() == Animation.Status.RUNNING) {
        dying = true;
      }
      else {
        gamePane.getChildren().remove(skeletonAtk);
        gamePane.getChildren().remove(skeletonWalk);
        gamePane.getChildren().remove(skeletonTakeHit);
        gamePane.getChildren().remove(skeletonDeath);
      }
    }
  }
  
  public void setOnTop() {
    skeletonWalk.toFront();
    skeletonDeath.toFront();
    skeletonRevive.toFront();
    skeletonAtk.toFront();
    skeletonTakeHit.toFront();
  }
  public void setOnBottom() {
    skeletonWalk.toBack();
    skeletonDeath.toBack();
    skeletonRevive.toBack();
    skeletonAtk.toBack();
    skeletonTakeHit.toBack();
  }
  
  public double getX() {
    return skeletonWalk.getBoundsInParent().getCenterX();
  }
  public double getY() {
    return skeletonWalk.getBoundsInParent().getCenterY();
  }
  
  public void takeHit() {
    if (!isDead && started) {
      takingHit = true;
      skeletonTakeHit.setTranslateX(skeletonWalk.getTranslateX());
      skeletonTakeHit.setTranslateY(skeletonWalk.getTranslateY());
      skeletonWalk.setVisible(false);
      skeletonAtk.setVisible(false);
      skeletonTakeHit.setVisible(true);
      skeletonTakeHit.animation.playFromStart(); 
      health = health - 25;
      if (health <= 0) {
        skeletonDeath.setTranslateX(skeletonWalk.getTranslateX());
        skeletonDeath.setTranslateY(skeletonWalk.getTranslateY());
        skeletonDeath.play();
        skeletonDeath.setVisible(true);
        skeletonTakeHit.setVisible(false);
        skeletonWalk.setVisible(false);
        skeletonAtk.setVisible(false);
        isDead = true;
      }
    }
  }
  
  public void revive() {
    if (isDead) {
      skeletonDeath.setVisible(false);
      skeletonRevive.setTranslateX(skeletonDeath.getTranslateX());
      skeletonRevive.setTranslateY(skeletonDeath.getTranslateY());
      skeletonRevive.setVisible(true);
      skeletonRevive.animation.playFromStart();
    }
  }
  
  private void createListeners() {
    gameScene.addEventFilter(KeyEvent.ANY, keyEvent -> {
        if (!started) {
          started = true;
          skeletonWalk.play();
          animate.start();
        }
    });
    skeletonAtk.animation.setOnFinished(event -> {
      if (!isDead) {
        samurai.takeHit();
        isPlayed = false;
      }
    });
    skeletonTakeHit.animation.setOnFinished(event2 -> {
      takingHit = false;
      skeletonTakeHit.setVisible(false);
    });
    skeletonDeath.animation.setOnFinished(event3 -> {
      if (dying && !isWave9) {
        gamePane.getChildren().remove(skeletonAtk);
        gamePane.getChildren().remove(skeletonWalk);
        gamePane.getChildren().remove(skeletonTakeHit);
        gamePane.getChildren().remove(skeletonDeath);
      }
    });
    skeletonRevive.animation.setOnFinished(event4 -> {
      skeletonRevive.setVisible(false);
      isDead = false;
    });
  }
  
}
