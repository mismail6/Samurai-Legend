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

public class Goblin {
  private Stage mainStage;
  private Scene mainScene;
  private Scene gameScene;
  private AnchorPane gamePane;
  
  private SamuraiControl samurai;
  private CharacterControl goblinWalk;
  private CharacterControl goblinAtk1;
  private CharacterControl goblinAtk2;
  private CharacterControl goblinTakeHit;
  private CharacterControl goblinDeath;
  
  private boolean inZone;
  private boolean nearSamurai;
  private boolean atk1IsPlayed;
  private boolean atk2IsPlayed;
  private boolean takingHit;
  private boolean setScene;
  private boolean started;
  public boolean isDead;
  private boolean dying;
  
  private double layoutX;
  private double layoutY;
  private double moveSpeed = 0.5;
  private int scale = 165;
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
        goblinWalk.setVisible(true);
        goblinAtk1.setVisible(false);
        goblinAtk2.setVisible(false);
        goblinAtk1.animation.stop();
        goblinAtk2.animation.stop();
        atk1IsPlayed = false;
        if (getX() < 0.0) {
          samurai.setGameOver();
        }
        goblinWalk.moveX(-moveSpeed);
      }
      
      if ( Math.abs(getY() - samurai.getY()) < 140 && Math.abs(getX() - samurai.getX()) < 140 && (getX() - samurai.getX()) > 0 && !takingHit && !isDead) { // in Zone
         inZone = true;
         goblinWalk.setVisible(true);
         goblinAtk1.setVisible(false);
         if (goblinAtk2.animation.getStatus() == Animation.Status.STOPPED) {
           goblinAtk2.setVisible(false);
         }
         if (!nearSamurai) {
           goblinAtk1.animation.stop();
           atk1IsPlayed = false;
           goblinAtk2.animation.stop();
           atk2IsPlayed = false;
         }
         if ( (getY() - samurai.getY()) < -13  - flinchY) {
             nearSamurai = false;
            goblinWalk.moveY(moveSpeed);
         }
         else if ( (getY() - samurai.getY()) >= 16 + flinchY) {
           nearSamurai = false;
           goblinWalk.moveY(-moveSpeed);
         }
         
         if ( (getX() - samurai.getX()) > 58 ) {
           nearSamurai = false;
           goblinWalk.moveX(-moveSpeed);
         }
         else if ( (getX() - samurai.getX()) <= 58 && Math.abs(getY() - samurai.getY()) < 16 + flinchY) {// Skeleton in attack range
           nearSamurai = true;
           goblinWalk.setVisible(false);
           goblinAtk1.setTranslateX(goblinWalk.getTranslateX());
           goblinAtk1.setTranslateY(goblinWalk.getTranslateY());
           if (!goblinAtk2.isVisible()) {
             goblinAtk1.setVisible(true);
           }
           else {
             goblinAtk1.setVisible(false);
           }
           if (!atk1IsPlayed && !atk2IsPlayed) {
             goblinAtk1.animation.playFromStart();
             atk1IsPlayed = true;
           }
         }
      }
      else {
        inZone = false;
      }
    }  
  };
  
  
  public Goblin (Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane mainPane, SamuraiControl samurai, double layoutX, double layoutY, int flinchY) {
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
    atk1IsPlayed = false;
    atk2IsPlayed = false;
    takingHit = false;
    setScene = false;
    started = false;
    isDead = false;
    dying = true;
    initialize();
    goblinWalk.stop();
    goblinAtk1.stop();
    goblinAtk2.stop();
    goblinTakeHit.stop();
    goblinDeath.stop();
    createListeners();
    health = 60;
  }
  
  public void removeSelf() {
    if (goblinDeath.animation.getStatus() == Animation.Status.RUNNING) {
      dying = true;
    }
    else {
      gamePane.getChildren().remove(goblinAtk1);
      gamePane.getChildren().remove(goblinAtk2);
      gamePane.getChildren().remove(goblinWalk);
      gamePane.getChildren().remove(goblinTakeHit);
      gamePane.getChildren().remove(goblinDeath);
    }
  }
  
  private void initialize() {
    ImageView goblinWalkView = new ImageView(new Image(Links.gWalk));
    ImageView goblinAtk1View = new ImageView(new Image(Links.gAtk1));
    ImageView goblinAtk2View = new ImageView(new Image(Links.gAtk2));
    ImageView goblinTakeHitView = new ImageView(new Image(Links.gTakeHit));
    ImageView goblinDeathView = new ImageView(new Image(Links.gDie));
    goblinDeathView.toBack();
    goblinWalkView.setFitHeight(scale);
    goblinWalkView.setFitWidth(scale);
    goblinAtk1View.setFitHeight(scale);
    goblinAtk1View.setFitWidth(scale);
    goblinAtk2View.setFitHeight(scale);
    goblinAtk2View.setFitWidth(scale);
    goblinTakeHitView.setFitHeight(scale);
    goblinTakeHitView.setFitWidth(scale);
    goblinDeathView.setFitHeight(scale);
    goblinDeathView.setFitWidth(scale);
    
    
    goblinWalk = new CharacterControl(goblinWalkView, 750, 8, 8, true, 0, 150, 150);
    goblinAtk1 = new CharacterControl(goblinAtk1View, 700, 8, 8, false, 0, 150, 150);
    goblinAtk2 = new CharacterControl(goblinAtk2View, 520, 8, 8, false, 0, 150, 150);
    goblinTakeHit = new CharacterControl(goblinTakeHitView, 200, 4, 4, false, 0, 150, 150);
    goblinDeath = new CharacterControl(goblinDeathView, 700, 4, 4, false, 0, 150, 150);
    gamePane.getChildren().add(goblinWalk);
    gamePane.getChildren().add(goblinAtk1);
    gamePane.getChildren().add(goblinAtk2);
    gamePane.getChildren().add(goblinTakeHit);
    gamePane.getChildren().add(goblinDeath);
    goblinWalk.setLayoutX(layoutX);
    goblinWalk.setLayoutY(layoutY);
    goblinDeath.setLayoutX(layoutX);
    goblinDeath.setLayoutY(layoutY);
    goblinAtk1.setLayoutX(layoutX);
    goblinAtk1.setLayoutY(layoutY);
    goblinAtk2.setLayoutX(layoutX);
    goblinAtk2.setLayoutY(layoutY);
    goblinTakeHit.setLayoutX(layoutX);
    goblinTakeHit.setLayoutY(layoutY);
    goblinAtk1.setVisible(false);
    goblinAtk2.setVisible(false);
    goblinTakeHit.setVisible(false);
    goblinDeath.setVisible(false);
  }
  
  public double getX() {
    return goblinWalk.getBoundsInParent().getCenterX();
  }
  public double getY() {
    return goblinWalk.getBoundsInParent().getCenterY();
  }
  
  public void setOnTop() {
    goblinWalk.toFront();
    goblinDeath.toFront();
    goblinTakeHit.toFront();
    goblinAtk1.toFront();
    goblinAtk2.toFront();
  }
  public void setOnBottom() {
    goblinWalk.toBack();
    goblinDeath.toBack();
    goblinTakeHit.toBack();
    goblinAtk1.toBack();
    goblinAtk2.toBack();
  }
  
  public void takeHit() {
    if (!isDead && !atk2IsPlayed && started) {
      takingHit = true;
      goblinTakeHit.setTranslateX(goblinWalk.getTranslateX());
      goblinTakeHit.setTranslateY(goblinWalk.getTranslateY());
      goblinWalk.setVisible(false);
      goblinAtk1.setVisible(false);
      goblinTakeHit.setVisible(true);
      goblinTakeHit.animation.playFromStart(); 
      health = health - 25;
      if (health <= 0) {
        goblinDeath.setTranslateX(goblinWalk.getTranslateX());
        goblinDeath.setTranslateY(goblinWalk.getTranslateY());
        goblinDeath.play();
        goblinDeath.setVisible(true);
        goblinTakeHit.setVisible(false);
        goblinWalk.setVisible(false);
        goblinAtk1.setVisible(false);
        isDead = true;
      }
    }
    
  }
  
  private void createListeners() {
    gameScene.addEventFilter(KeyEvent.ANY, keyEvent -> {
        if (!started) {
          started = true;
          goblinWalk.play();
          animate.start();
        }
    });
    goblinAtk1.animation.setOnFinished(event -> {
      if (nearSamurai && !isDead && goblinAtk1.isVisible()) {
        samurai.takeHit();
        atk1IsPlayed = false;
        goblinAtk1.setVisible(false);
        goblinAtk2.setVisible(true);
        goblinAtk2.setTranslateX(goblinAtk1.getTranslateX());
        goblinAtk2.setTranslateY(goblinAtk1.getTranslateY());
        goblinAtk2.animation.playFromStart();
        atk2IsPlayed = true;
      }
    });
    goblinTakeHit.animation.setOnFinished(event2 -> {
      takingHit = false;
      goblinTakeHit.setVisible(false);
    });
    goblinAtk2.animation.setOnFinished(event3 -> {
      if (nearSamurai) {
        samurai.takeHit();
      }
      goblinAtk2.setVisible(false);
      atk2IsPlayed = false;
    });
    goblinDeath.animation.setOnFinished(event3 -> {
      if (dying) {
        gamePane.getChildren().remove(goblinAtk1);
        gamePane.getChildren().remove(goblinAtk2);
        gamePane.getChildren().remove(goblinWalk);
        gamePane.getChildren().remove(goblinTakeHit);
        gamePane.getChildren().remove(goblinDeath);
      }
    });
    
  }
}
