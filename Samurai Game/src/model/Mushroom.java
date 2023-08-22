package model;

import java.util.ArrayList;
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

public class Mushroom {
  private Stage mainStage;
  private Scene mainScene;
  private Scene gameScene;
  private AnchorPane gamePane;
  private SamuraiControl samurai;
  private CharacterControl mushroomWalk;
  private CharacterControl mushroomAtk;
  private CharacterControl mushroomTakeHit;
  private CharacterControl mushroomDeath;
  private CharacterControl mushroomSpitAtk;
  private boolean inZone;
  private boolean nearSamurai;
  private boolean takingHit;
  private boolean setScene;
  private boolean started;
  public boolean isDead;
  private boolean dying;
  private boolean diagonal;
  private boolean diagonalUp;
  private boolean bounced;
  
  private String walk;
  private String atk;
  private String takeHit;
  private String die;
  private double layoutX;
  private double layoutY;
  private int flinchY;
  private int health;
  private double moveSpeed = 0.5;
  private double dSpeed = 0.5;
  
  private ArrayList<ImageView> spits;
  
  AnimationTimer animate = new AnimationTimer() {
    @Override
    public void handle(long arg0) {
      if (getY() >= samurai.getY()) {
        setOnTop();
      }
      else {
        setOnBottom();
      }
      if (!spits.isEmpty()) {
        for (int i = 0; i < spits.size(); i++ ) {
          spits.get(i).setRotate(25);
          spits.get(i).setTranslateX(spits.get(i).getTranslateX() - 2);
          double spitX = spits.get(i).getBoundsInParent().getCenterX();
          double spitY = spits.get(i).getBoundsInParent().getCenterY();
          if ((spitX - samurai.getX() < 15) && (spitX > samurai.getX()) && spitY - samurai.getY() < 19 && spitY - samurai.getY() > - 34 ) {
            if (mushroomSpitAtk.animation.getStatus() == Animation.Status.STOPPED) {
              mushroomSpitAtk.setTranslateX(spits.get(i).getTranslateX());
              mushroomSpitAtk.setTranslateY(spits.get(i).getTranslateY());
              spits.get(i).setVisible(false);
              mushroomSpitAtk.setVisible(true);
              mushroomSpitAtk.animation.playFromStart();
              samurai.takeHit();
            }
          }
        }
        int size = spits.size();
        for (int j = 0; j < spits.size(); j++ ) {
          if (spits.get(j).getBoundsInParent().getCenterX() < 0) {
            
            gamePane.getChildren().remove(spits.get(j));
            spits.remove(j);
          }
        }
      }
      if (!inZone && !takingHit && !isDead) {
        mushroomWalk.setVisible(true);
        mushroomAtk.setVisible(false);
        mushroomAtk.animation.stop();
        if (getX() < 0.0) {
          samurai.setGameOver();
        }
        if ( diagonal) {
          if (!bounced) {
            if (getY() > 375) {
              mushroomWalk.moveY(dSpeed);
              if (getY() > 594) {
                bounced = true;
                dSpeed = -dSpeed;
              }
            }
            else {
              mushroomWalk.moveY(-dSpeed);
              if (getY() < 168) {
                bounced = true;
                dSpeed = -dSpeed;
              }
            }
          }
          else{
            if ( getY() >= 168 && getY() <= 594) {
              mushroomWalk.moveY(dSpeed);
            }
            else {
              if (getY() < 168) {
                mushroomWalk.moveY(0.6);
              }
              else if (getY() > 594) {
                mushroomWalk.moveY(-0.6);
              }
              dSpeed = -dSpeed;
            }
          }
        }
        mushroomWalk.moveX(-moveSpeed);
      }
      
      if (  getX() > samurai.getX() && Math.abs(getY() - samurai.getY()) < 34 && !takingHit && !isDead) { // in Zone
        inZone = true;
        mushroomAtk.setVisible(true);
        mushroomWalk.setVisible(false);
        if (mushroomAtk.animation.getStatus() == Animation.Status.STOPPED) {
          mushroomAtk.setTranslateX(mushroomWalk.getTranslateX());
          mushroomAtk.setTranslateY(mushroomWalk.getTranslateY());
          mushroomAtk.animation.playFromStart();
        }
      }
      else {
        inZone = false;
      }
    }  
  };
  
  
  public Mushroom (Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai, double layoutX, double layoutY, int flinchY, boolean diagonal) {
    this.mainStage = mainStage;
    this.mainScene = mainScene; 
    this.gameScene = gameScene;
    this.gamePane = gamePane;
    this.samurai = samurai;
    this.layoutX = layoutX;
    this.layoutY = layoutY;
    this.flinchY = flinchY;
    spits = new ArrayList<ImageView>();
    inZone = false;
    nearSamurai = false;
    takingHit = false;
    setScene = false;
    started = false;
    isDead = false;
    dying = false;
    bounced = false;
    this.diagonal = diagonal;
    initialize();
    mushroomWalk.stop();
    mushroomAtk.stop();
    mushroomTakeHit.stop();
    mushroomDeath.stop();
    mushroomSpitAtk.stop();
    createListeners();
    health = 50;
  }
  
  public void removeSelf() {
    if (mushroomDeath.animation.getStatus() == Animation.Status.RUNNING) {
      dying = true;
    }
    else {
      gamePane.getChildren().remove(mushroomAtk);
      gamePane.getChildren().remove(mushroomWalk);
      gamePane.getChildren().remove(mushroomTakeHit);
      gamePane.getChildren().remove(mushroomDeath);
    }
  }
  
  public void setOnTop() {
    mushroomWalk.toFront();
    mushroomDeath.toFront();
    mushroomTakeHit.toFront();
    mushroomAtk.toFront();
  }
  public void setOnBottom() {
    mushroomWalk.toBack();
    mushroomDeath.toBack();
    mushroomTakeHit.toBack();
    mushroomAtk.toBack();
  }
  
  private void initialize() {
    ImageView skeletonWalkView = new ImageView(new Image(Links.mWalk));
    ImageView skeletonAtkView = new ImageView(new Image(Links.mAtk));
    ImageView skeletonTakeHitView = new ImageView(new Image(Links.mTakeHit));
    ImageView skeletonDeathView = new ImageView(new Image(Links.mDie));
    ImageView skeletonSpitView = new ImageView(new Image(Links.mSpitAtk));
    skeletonSpitView.setFitWidth(75);
    skeletonSpitView.setFitHeight(75);
    mushroomWalk = new CharacterControl(skeletonWalkView, 600, 4, 4, true, 0, 150, 150);
    mushroomAtk = new CharacterControl(skeletonAtkView, 800, 8, 8, false, 0, 150, 150);
    mushroomTakeHit = new CharacterControl(skeletonTakeHitView, 200, 4, 4, false, 0, 150, 150);
    mushroomDeath = new CharacterControl(skeletonDeathView, 950, 4, 4, false, 0, 150, 150);
    mushroomSpitAtk = new CharacterControl(skeletonSpitView, 400, 8, 8, false, 0, 50, 50);
    
    gamePane.getChildren().add(mushroomWalk);
    gamePane.getChildren().add(mushroomAtk);
    gamePane.getChildren().add(mushroomTakeHit);
    gamePane.getChildren().add(mushroomDeath);
    gamePane.getChildren().add(mushroomSpitAtk);
    mushroomWalk.setLayoutX(layoutX);
    mushroomWalk.setLayoutY(layoutY);
    mushroomDeath.setLayoutX(layoutX);
    mushroomDeath.setLayoutY(layoutY);
    mushroomAtk.setLayoutX(layoutX);
    mushroomAtk.setLayoutY(layoutY);
    mushroomTakeHit.setLayoutX(layoutX);
    mushroomTakeHit.setLayoutY(layoutY);
    mushroomSpitAtk.setLayoutX(layoutX);
    mushroomSpitAtk.setLayoutY(layoutY + 40);
    mushroomSpitAtk.setVisible(false);
    mushroomAtk.setVisible(false);
    mushroomTakeHit.setVisible(false);
    mushroomDeath.setVisible(false);
  }
  
  public double getX() {
    return mushroomWalk.getBoundsInParent().getCenterX();
  }
  public double getY() {
    return mushroomWalk.getBoundsInParent().getCenterY();
  }
  
  public void takeHit() {

    if (!isDead && started) {
      takingHit = true;
      mushroomTakeHit.setTranslateX(mushroomWalk.getTranslateX());
      mushroomTakeHit.setTranslateY(mushroomWalk.getTranslateY());
      mushroomWalk.setVisible(false);
      mushroomAtk.setVisible(false);
      mushroomTakeHit.setVisible(true);
      mushroomTakeHit.animation.playFromStart(); 
      health = health - 25;
      if (health <= 0) {
        mushroomDeath.setTranslateX(mushroomWalk.getTranslateX());
        mushroomDeath.setTranslateY(mushroomWalk.getTranslateY());
        mushroomDeath.play();
        mushroomDeath.setVisible(true);
        mushroomTakeHit.setVisible(false);
        mushroomWalk.setVisible(false);
        mushroomAtk.setVisible(false);
        isDead = true;
      }
    }
    
  }
  
  private void createListeners() {
    gameScene.addEventFilter(KeyEvent.ANY, keyEvent -> {
        if (!started) {
          started = true;
          mushroomWalk.play();
          animate.start();
        }
    });
    mushroomAtk.animation.setOnFinished(event -> {
      if (!isDead) {
        spits.add(new ImageView(new Image(Links.mSpitBall)));
        ImageView tempView = spits.get(spits.size()-1);
        tempView.setFitHeight(75);
        tempView.setFitWidth(75);
        tempView.setLayoutX(layoutX);
        tempView.setLayoutY(layoutY + 40);
        tempView.setTranslateX(mushroomWalk.getTranslateX());
        tempView.setTranslateY(mushroomWalk.getTranslateY());
        gamePane.getChildren().add(tempView);
      }
    });
    mushroomTakeHit.animation.setOnFinished(event -> {
      takingHit = false;
      mushroomTakeHit.setVisible(false);
    });
    mushroomDeath.animation.setOnFinished(event3 -> {
      if (dying) {
        gamePane.getChildren().remove(mushroomAtk);
        gamePane.getChildren().remove(mushroomWalk);
        gamePane.getChildren().remove(mushroomTakeHit);
        gamePane.getChildren().remove(mushroomDeath);
      }
    });
  }
}
