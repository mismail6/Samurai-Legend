package model;

import java.util.Timer;
import java.util.TimerTask;
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
import javafx.util.Duration;

public class FirstBoss {
  private Stage mainStage;
  private Scene mainScene;
  private Scene gameScene;
  private AnchorPane gamePane;
  
  private SamuraiControl samurai;
  private CharacterControl bossIdle;
  private CharacterControl bossAtk1;
  private CharacterControl bossAtk2;
  private CharacterControl bossTakeHit;
  private CharacterControl bossDeath;
  
  private boolean inZone;
  private boolean nearSamurai;
  private boolean isPlayed;
  private boolean takingHit;
  private boolean setScene;
  private boolean started;
  public boolean isDead;
  private boolean dying;
  
  private double layoutX;
  private double layoutY;
  private double moveSpeed = 0.05;
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
      if (!inZone && !isDead && !takingHit) {
        bossAtk2.setVisible(false);
        if (bossAtk1.animation.getStatus() == Animation.Status.RUNNING) {
          bossIdle.setVisible(false);
          bossAtk1.setVisible(true);
        }
        else {
          bossIdle.setVisible(true);
          bossAtk1.setVisible(false);
        }
      }
      if ( (getX() - samurai.getX()) <= 105 && getY() - samurai.getY() < 25 && getY() - samurai.getY() > -66 && !takingHit && !isDead) { // in Zone
        inZone = true;
        bossIdle.setVisible(false);
        bossAtk1.setVisible(false);
        bossAtk2.setVisible(true);
        if (bossAtk2.animation.getStatus() == Animation.Status.STOPPED) {
          bossAtk2.animation.playFromStart();
        }
      }
      else {
        inZone = false;
      }
    }  
  };
  
  Timer timer1;
  TimerTask task = new TimerTask()
  {
          public void run()
          {
            if (!inZone && !isDead) {
              bossAtk1.animation.playFromStart();
            }
          }

  };
  
  public FirstBoss(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane mainPane, SamuraiControl samurai, double layoutX, double layoutY, int flinchY) {
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
    initialize();
    bossTakeHit.stop();
    bossDeath.stop();
    bossAtk1.stop();
    bossAtk2.stop();
    
    createListeners();
    health = 150;
    
    timer1 = new Timer();
    timer1.scheduleAtFixedRate(task, (long) 500, 5000);
    
  }
  
  private void initialize() {
    ImageView bossIdleView =    new ImageView(new Image(Links.b1Idle));
    ImageView bossAtk1View =     new ImageView(new Image(Links.b1Atk1));
    ImageView bossAtk2View =     new ImageView(new Image(Links.b1Atk2));
    ImageView bossTakeHitView = new ImageView(new Image(Links.b1TakeHit));
    ImageView bossDeathView =    new ImageView(new Image(Links.b1Die));
    
    bossIdle = new CharacterControl(bossIdleView, 600, 8, 8, true, 0, 250, 250);
    bossAtk1 = new CharacterControl(bossAtk1View, 600, 8, 8, false, 0, 250, 250);
    bossAtk2 = new CharacterControl(bossAtk2View, 600, 8, 8, false, 0, 250, 250);
    bossTakeHit = new CharacterControl(bossTakeHitView, 300, 3, 3, false, 0, 250, 250);
    bossDeath = new CharacterControl(bossDeathView, 750, 7, 7, false, 0, 250, 250);
    gamePane.getChildren().add(bossIdle);
    gamePane.getChildren().add(bossAtk1);
    gamePane.getChildren().add(bossAtk2);
    gamePane.getChildren().add(bossTakeHit);
    gamePane.getChildren().add(bossDeath);
    bossIdle.setLayoutX(layoutX);
    bossIdle.setLayoutY(layoutY);
    bossDeath.setLayoutX(layoutX);
    bossDeath.setLayoutY(layoutY);
    bossAtk1.setLayoutX(layoutX);
    bossAtk1.setLayoutY(layoutY);
    bossAtk2.setLayoutX(layoutX);
    bossAtk2.setLayoutY(layoutY);
    bossTakeHit.setLayoutX(layoutX);
    bossTakeHit.setLayoutY(layoutY);
    bossAtk1.setVisible(true);
    bossAtk2.setVisible(false);
    bossIdle.setVisible(false);
    bossTakeHit.setVisible(false);
    bossDeath.setVisible(false);
    
  }
  public void removeSelf() {
    if (bossDeath.animation.getStatus() == Animation.Status.RUNNING) {
      dying = true;
    }
    else {
      gamePane.getChildren().remove(bossAtk1);
      gamePane.getChildren().remove(bossIdle);
      gamePane.getChildren().remove(bossTakeHit);
      gamePane.getChildren().remove(bossDeath);
    }
    
  }
  public double getX() {
    return bossIdle.getBoundsInParent().getCenterX();
  }
  public double getY() {
    return bossIdle.getBoundsInParent().getCenterY();
  }
  
  public void setOnTop() {
    bossIdle.toFront();
    bossDeath.toFront();
    bossTakeHit.toFront();
    bossAtk1.toFront();
    bossAtk2.toFront();
  }
  public void setOnBottom() {
    bossIdle.toBack();
    bossDeath.toBack();
    bossTakeHit.toBack();
    bossAtk1.toBack();
    bossAtk2.toBack();
  }
  
  public void takeHit() {
    if (!isDead && started) {
      takingHit = true;
      bossTakeHit.setTranslateX(bossIdle.getTranslateX());
      bossTakeHit.setTranslateY(bossIdle.getTranslateY());
      bossIdle.setVisible(false);
      bossAtk1.setVisible(false);
      bossAtk2.setVisible(false);
      bossTakeHit.setVisible(true);
      bossTakeHit.animation.playFromStart(); 
      health = health - 25;
      if (health <= 0) {
        bossDeath.setTranslateX(bossIdle.getTranslateX());
        bossDeath.setTranslateY(bossIdle.getTranslateY());
        bossDeath.play();
        bossDeath.setVisible(true);
        bossTakeHit.setVisible(false);
        bossIdle.setVisible(false);
        bossAtk1.setVisible(false);
        bossAtk2.setVisible(false);
        isDead = true;
      }
    }
    
  }
  
  private void createListeners() {
    gameScene.addEventFilter(KeyEvent.ANY, keyEvent -> {
        if (!started) {
          started = true;
          bossIdle.play();
          animate.start();
        }
    });
    bossAtk1.animation.setOnFinished(event1 -> {
      if (!inZone) {
        samurai.setSkeletonsRevive();
        bossAtk1.setVisible(false);
        bossIdle.setVisible(true);
      }
    });
    bossAtk2.animation.setOnFinished(event -> {
      if (!isDead && inZone) {
        samurai.takeHit();
      }
    });
    bossTakeHit.animation.setOnFinished(event2 -> {
      takingHit = false;
      bossTakeHit.setVisible(false);
    });
    bossDeath.animation.setOnFinished(event3 -> {
      if (dying) {
        gamePane.getChildren().remove(bossAtk1);
        gamePane.getChildren().remove(bossIdle);
        gamePane.getChildren().remove(bossTakeHit);
        gamePane.getChildren().remove(bossDeath);
      }
    });
  }
}
