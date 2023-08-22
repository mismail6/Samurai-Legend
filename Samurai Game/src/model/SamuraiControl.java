package model;

import java.awt.Event;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.sun.scenario.animation.shared.AnimationAccessor;
import control.CharacterControl;
import control.WaveControl;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.GameOver;

public class SamuraiControl {
  
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  
  private Label labelStart;
  private Label labelWave;
  private Rectangle healthBar;
  
  private SamuraiAbilities abilities;
  
  private Stage mainStage;
  private Scene mainScene;
  private Scene gameScene;
  private AnchorPane gamePane;
  
  private ArrayList<Skeleton> skeletons;
  private ArrayList<Mushroom> mushrooms;
  private ArrayList<Goblin> goblins;
  private ArrayList<FirstBoss> boss1;
  
  public CharacterControl idle;
  private CharacterControl walk;
  private CharacterControl flipWalk;
  private CharacterControl idleAttack;
  private CharacterControl runAttack;
  private CharacterControl takeHit;
  private CharacterControl death;
  
  private Image image = new Image("model/resources/Charcoal.jpg", WIDTH, HEIGHT, false, true);
  
  private boolean pressedW;
  private boolean pressedA;
  private boolean pressedS;
  private boolean pressedD;
  private boolean leftClicked;
  private boolean takingHit;
  private boolean setScene;
  private boolean gameIsOver;
  
  public Bounds bounds;
  public Bounds screenBounds;
  
  private int keysPressed;
  private int waveNum;
  private double moveSpeed = 1.5;
  
  AnimationTimer animateW = new AnimationTimer() {//W
    @Override
    public void handle(long arg0) {
      if (idle.getTranslateY() > -165.1 && !takingHit) {
        idle.moveY(-moveSpeed);
        walk.moveY(-moveSpeed);
        flipWalk.moveY(-moveSpeed);
        idleAttack.moveY(-moveSpeed);
        runAttack.moveY(-moveSpeed);
        
      }
    }  
  };
  AnimationTimer animateA = new AnimationTimer() {//A
    @Override
    public void handle(long arg0) {
      if (idle.getTranslateX() > -300 && !takingHit) {
        idle.moveX(-moveSpeed);
        walk.moveX(-moveSpeed);
        flipWalk.moveX(-moveSpeed);
        idleAttack.moveX(-moveSpeed);
        runAttack.moveX(-moveSpeed);
        if (idleAttack.animation.getStatus() != Animation.Status.STOPPED) {
          idleAttack.animation.stop();
        }
        if (runAttack.animation.getStatus() != Animation.Status.STOPPED) {
          runAttack.animation.stop();
        }
      }
    }  
  };
  AnimationTimer animateS = new AnimationTimer() {//S
    @Override
    public void handle(long arg0) {
      if (idle.getTranslateY() < 258.7 && !takingHit) {
        idle.moveY(moveSpeed);
        walk.moveY(moveSpeed);
        flipWalk.moveY(moveSpeed);
        idleAttack.moveY(moveSpeed);
        runAttack.moveY(moveSpeed);
      }
    }  
  };
  AnimationTimer animateD = new AnimationTimer() {//D
    @Override
    public void handle(long arg0) {
      if (idle.getTranslateX() < 900 && !takingHit) {
        idle.moveX(moveSpeed);
        walk.moveX(moveSpeed);
        flipWalk.moveX(moveSpeed);
        idleAttack.moveX(moveSpeed);
        runAttack.moveX(moveSpeed); 
 
      }
    }  
  };
  
  public SamuraiControl(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane) { //  CONSTRUCTOR
    this.gameScene = gameScene;
    this.gamePane = gamePane;
    this.mainStage = mainStage;
    this.mainScene = mainScene;
    skeletons = new ArrayList<Skeleton>();
    mushrooms = new ArrayList<Mushroom>();
    goblins = new ArrayList<Goblin>();
    boss1 = new ArrayList<FirstBoss>();
    labelStart = new Label("Do not let them pass!");
    labelStart.setStyle("-fx-font-family: \"Verdana\"; -fx-font-size: 40; -fx-text-fill: maroon;");
    labelStart.setLayoutY(200);
    labelStart.setLayoutX(430);
    labelWave = new Label("  Wave 1  ");
    labelWave.setStyle("-fx-font-family: \"Arial\"; -fx-font-size: 28; -fx-text-fill: red;");
    labelWave.setLayoutY(644);
    labelWave.setLayoutX(590);
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
    labelWave.setBackground(new Background(backgroundImage));
    initialize();
    gamePane.getChildren().add(labelStart);
    gamePane.getChildren().add(labelWave);
    takeHit.stop();
    death.stop();
    idleAttack.stop();
    runAttack.stop();
    pressedW = false; pressedA = false; pressedS = false; pressedD = false;
    leftClicked = false;
    takingHit = false;
    setScene = false;
    gameIsOver = false;
    keysPressed = 0;
    waveNum = 1;
    createListeners();
    abilities = new SamuraiAbilities(gameScene, gamePane, this);
  }
  
  private void initialize() {
    ImageView imageViewIdle = new ImageView(new Image("model/resources/CharacterIdle.png"));
    ImageView imageViewWalk = new ImageView(new Image("model/resources/Run.png"));
    ImageView imageViewFlipWalk = new ImageView(new Image("model/resources/flipRun.png"));
    ImageView imageViewIdleAttack = new ImageView(new Image("model/resources/IdleAttack.png"));
    ImageView imageViewRunAttack = new ImageView(new Image("model/resources/RunAttack.png"));
    ImageView imageViewTakeHit = new ImageView(new Image("model/resources/TakeHit.png"));
    ImageView imageViewDeath = new ImageView(new Image("model/resources/Death.png"));
    
    idle = new CharacterControl(imageViewIdle, 850, 8, 8, true, 0, 200, 200);
    walk = new CharacterControl(imageViewWalk, 550, 8, 8, true, 0, 200, 200);
    flipWalk = new CharacterControl(imageViewFlipWalk, 550, 8, 8, true, 0, 200, 200);
    idleAttack = new CharacterControl(imageViewIdleAttack, 450, 6, 6, false, 0, 200, 200);
    runAttack = new CharacterControl(imageViewRunAttack, 140, 4, 4, false, 400, 200, 200);
    takeHit = new CharacterControl(imageViewTakeHit, 150, 4, 4, false, 0, 200, 200);
    death = new CharacterControl(imageViewDeath, 900, 6, 6, false, 0, 200, 200);
    
    
    idle.setLayoutX(WIDTH/4 - 100);
    idle.setLayoutY(HEIGHT/2 - 125);
    walk.setLayoutX(WIDTH/4 - 100);
    walk.setLayoutY(HEIGHT/2 - 125);
    flipWalk.setLayoutX(WIDTH/4 - 100);
    flipWalk.setLayoutY(HEIGHT/2 - 125);
    idleAttack.setLayoutX(WIDTH/4 - 100);
    idleAttack.setLayoutY(HEIGHT/2 - 125);
    runAttack.setLayoutX(WIDTH/4 - 100);
    runAttack.setLayoutY(HEIGHT/2 - 125);
    takeHit.setLayoutX(WIDTH/4 - 100);
    takeHit.setLayoutY(HEIGHT/2 - 125);
    death.setLayoutX(WIDTH/4 - 100);
    death.setLayoutY(HEIGHT/2 - 125);
    gamePane.getChildren().add(walk);
    gamePane.getChildren().add(idle);
    gamePane.getChildren().add(takeHit);
    gamePane.getChildren().add(flipWalk);
    gamePane.getChildren().add(idleAttack);
    gamePane.getChildren().add(runAttack);
    gamePane.getChildren().add(death);
    takeHit.setVisible(false);
    walk.setVisible(false);
    flipWalk.setVisible(false);
    idleAttack.setVisible(false);
    runAttack.setVisible(false);
    death.setVisible(false);
    
    healthBar = new Rectangle(326.0, 16, Color.GREEN);
    gamePane.getChildren().add(healthBar);
    healthBar.setLayoutX(76);
    healthBar.setLayoutY(27);
  }
  
  public void takeHit() {
    takingHit = true;
    takeHit.setTranslateX(idle.getTranslateX());
    takeHit.setTranslateY(idle.getTranslateY());
    animateSamurai();
    takeHit.animation.play();
  }
  
  public void setOnTop() {
    idle.toFront();
    walk.toFront();
    flipWalk.toFront();
    idleAttack.toFront();
    takeHit.toFront();
    runAttack.toFront();
    death.toFront();
  }
  public void setOnBottom() {
    idle.toBack();
    walk.toBack();
    flipWalk.toBack();
    idleAttack.toBack();
    takeHit.toBack();
    runAttack.toBack();
    death.toBack();
  }
  
  public void addSkeleton(Skeleton skeleton) {
    skeletons.add(skeleton);
  }
  public void addMushroom(Mushroom mushroom) {
    mushrooms.add(mushroom);
  }
  public void addGoblin(Goblin goblin) {
    goblins.add(goblin);
  }
  public void addBoss1(FirstBoss boss1) {
    this.boss1.add(boss1);
  }
  
  public void setSkeletonsRevive() {
    for (int i = 0; i < skeletons.size(); i++ ) {
      skeletons.get(i).revive();
    }
  }

  private boolean waveEnded() {
    for (int i = 0; i < skeletons.size(); i++) {
      if (!skeletons.get(i).isDead) {
        return false;
      }
    }
    for (int j = 0; j < mushrooms.size(); j++) {
      if (!mushrooms.get(j).isDead) {
        return false;
      }
    }
    for (int k = 0; k < goblins.size(); k++) {
      if (!goblins.get(k).isDead) {
        return false;
      }
    }
    for (int l = 0; l < boss1.size(); l++) {
      if (!boss1.get(l).isDead) {
        return false;
      }
    }
    return true;
  }
  
  private void checkWave() {
    if (waveEnded() ) {
      for (int i = 0; i < skeletons.size(); i++ ) {
        skeletons.get(i).removeSelf();
      }
      for (int j = 0; j < mushrooms.size(); j++ ) {
        mushrooms.get(j).removeSelf();
      }
      for (int k = 0; k < goblins.size(); k++ ) {
        goblins.get(k).removeSelf();
      }
      for (int l = 0; l < boss1.size(); l++ ) {
        boss1.get(l).removeSelf();
      }
      if (!skeletons.isEmpty() && !skeletons.get(0).isWave9) {
        skeletons.clear();
      }
        mushrooms.clear();
        goblins.clear();
        boss1.clear();
        waveNum++;
      labelWave.setText("  Wave " + waveNum + "  ");
      
      switch (waveNum) {
        case 2:
          WaveControl.initializeWave2(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 3:
          WaveControl.initializeWave3(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 4:
          WaveControl.initializeWave4(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 5:
          WaveControl.initializeWave5(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 6:
          WaveControl.initializeWave6(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 7:
          WaveControl.initializeWave7(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 8:
          WaveControl.initializeWave8(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 9:
          WaveControl.initializeWave9(mainStage, mainScene, gameScene, gamePane, this);
          break;
        case 10:
          WaveControl.initializeWave10(mainStage, mainScene, gameScene, gamePane, this);
          break;
      }
    }
  }
  
  public double getX() {
    return idle.getBoundsInParent().getCenterX();
  }
  public double getY() {
    return idle.getBoundsInParent().getCenterY();
  }
  
  private void animateSamurai() {
    labelStart.setVisible(false);
    
    if (keysPressed == 0 && !leftClicked && !takingHit) { // idle
      idle.setVisible(true);
      walk.setVisible(false);
      flipWalk.setVisible(false);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
    }
    else if (keysPressed > 0 && !leftClicked && !pressedA && !takingHit){ //walk
      idle.setVisible(false);
      walk.setVisible(true);
      flipWalk.setVisible(false);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
    }
    else if(keysPressed > 0 && !leftClicked && pressedA && !takingHit) { // walk left
      idle.setVisible(false);
      walk.setVisible(false);
      flipWalk.setVisible(true);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
      
    }
    else if (keysPressed == 0 && leftClicked && !takingHit) {//idle attack
      idle.setVisible(false);
      walk.setVisible(false);
      flipWalk.setVisible(false);
      idleAttack.setVisible(true);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
      if (idleAttack.animation.getStatus() == Animation.Status.STOPPED) {
        idleAttack.animation.playFromStart();
      }
    }
    else if (keysPressed > 0 && leftClicked && (pressedS || pressedW) && !pressedA && !pressedD) {
      idle.setVisible(false);
      walk.setVisible(true);
      flipWalk.setVisible(false);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
    }
    else if (keysPressed > 0 && leftClicked && !pressedA && pressedD && !takingHit) {//running attack
      idle.setVisible(false);
      walk.setVisible(false);
      flipWalk.setVisible(false);
      idleAttack.setVisible(false);
      runAttack.setVisible(true);
      takeHit.setVisible(false);
      if (runAttack.animation.getStatus() == Animation.Status.STOPPED) {
        runAttack.animation.playFromStart();
      }
    }
    else if (keysPressed > 0 && leftClicked && pressedA && !takingHit) {
      idle.setVisible(false);
      walk.setVisible(false);
      flipWalk.setVisible(true);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(false);
    }
    else if ( takingHit && !gameIsOver) {
      idle.setVisible(false);
      walk.setVisible(false);
      flipWalk.setVisible(false);
      idleAttack.setVisible(false);
      runAttack.setVisible(false);
      takeHit.setVisible(true);
    }
  }
  
  private void teleportSamurai(double shurikenTX) {
    idle.setTranslateX(shurikenTX);
    idle.setTranslateY(abilities.shuriken.getTranslateY());
    walk.setTranslateX(shurikenTX);
    walk.setTranslateY(abilities.shuriken.getTranslateY());
    flipWalk.setTranslateX(shurikenTX);
    flipWalk.setTranslateY(abilities.shuriken.getTranslateY());
    idleAttack.setTranslateX(shurikenTX);
    idleAttack.setTranslateY(abilities.shuriken.getTranslateY());
    runAttack.setTranslateX(shurikenTX);
    runAttack.setTranslateY(abilities.shuriken.getTranslateY());
    death.setTranslateX(shurikenTX);
    death.setTranslateY(abilities.shuriken.getTranslateY());
    takeHit.setTranslateX(shurikenTX);
    takeHit.setTranslateY(abilities.shuriken.getTranslateY());
  }
  
  public boolean getEnemyPos(double shurikenX, double shurikenY, double shurikenTX) {
    for (int i = 0; i < skeletons.size(); i++ ) {
      if (!skeletons.get(i).isDead && (skeletons.get(i).getX() - shurikenX < 20 && skeletons.get(i).getX() - shurikenX > 0) && Math.abs(skeletons.get(i).getY() - shurikenY) < 30 ) {
        teleportSamurai(shurikenTX);
        skeletons.get(i).takeHit();
        checkWave();
        return true;
      }
    }
    for (int j = 0; j < mushrooms.size(); j++ ) {
      if ( !mushrooms.get(j).isDead && (mushrooms.get(j).getX() - shurikenX < 20 && mushrooms.get(j).getX() - shurikenX > 0) && Math.abs(mushrooms.get(j).getY() - shurikenY) < 30 ) {
        teleportSamurai(shurikenTX);
        mushrooms.get(j).takeHit();
        checkWave();
        return true;
      }
    }
    for (int k = 0; k < goblins.size(); k++ ) {
      if (!goblins.get(k).isDead && (goblins.get(k).getX() - shurikenX < 20 && goblins.get(k).getX() - shurikenX > 0) && Math.abs(goblins.get(k).getY() - shurikenY) < 30 ) {
        teleportSamurai(shurikenTX);
        goblins.get(k).takeHit();
        checkWave();
        return true;
      }
    }
    for (int l = 0; l < boss1.size(); l++ ) {
      if (!boss1.get(l).isDead && (boss1.get(l).getX() - shurikenX < 20 && boss1.get(l).getX() - shurikenX > 0) && Math.abs(boss1.get(l).getY() - shurikenY) < 30 ) {
        teleportSamurai(shurikenTX);
        boss1.get(l).takeHit();
        return true;
      }
    }
    return false;
  }
  
  public Scene getSamuraiScene() {
    return gameScene;
  }
  
  private void createListeners() {
    gameScene.setOnKeyPressed(event -> {
      
      Object keyC = event.getCode();
      if (keyC == KeyCode.W) {// W
        if(!pressedW) {
          animateW.start();
          pressedW = true;
          keysPressed++;
          animateSamurai();
        }
        
      }
      
      if (keyC == KeyCode.A) {// A
        if(!pressedA) {
          animateA.start();
          pressedA = true;
          keysPressed++;
          animateSamurai();
        }
        
      }

      if (keyC == KeyCode.S) {// S
        if(!pressedS) {
          animateS.start();
          pressedS = true;
          keysPressed++;
          animateSamurai();
        }
        

      }

      if (keyC == KeyCode.D) {// D
        if(!pressedD) {
          animateD.start();
          pressedD = true;
          keysPressed++;
          animateSamurai();
        }
      }

        
    });
    
    gameScene.setOnKeyReleased(event2 -> {
      if (event2.getCode() == KeyCode.W) {
        animateW.stop();
        pressedW = false;
        keysPressed--;
        animateSamurai();
      }
      if (event2.getCode() == KeyCode.A) {
        animateA.stop();
        pressedA = false;
        keysPressed--;
        animateSamurai();
      }
      if (event2.getCode() == KeyCode.S) {
        animateS.stop();
        pressedS = false;
        keysPressed--;
        animateSamurai();
      }
      if (event2.getCode() == KeyCode.D) {
        animateD.stop();
        pressedD = false;
        keysPressed--;
        animateSamurai();
      }
      
    });
    
    gameScene.setOnMousePressed(event -> {
      leftClicked = true;
      if ( !takingHit && keysPressed == 0) {
        idleAttack.getAnimation().playFromStart();
        animateSamurai();
      }
      else if (keysPressed > 0 && pressedD && !pressedA && !takingHit) {
        runAttack.getAnimation().playFromStart();
        animateSamurai();
      }
    });
    
    gameScene.setOnMouseReleased(event -> {
      leftClicked = false;
      idleAttack.stop();
      animateSamurai();
    });
    
    idleAttack.getAnimation().setOnFinished(event3 -> { // idle Attack finished
      if (!pressedA) {
        for (int i = 0; i < skeletons.size(); i++ ) { // skeletons
          double xDiff = skeletons.get(i).getX() - getX();
          double yDiff = skeletons.get(i).getY() - getY();
          if (xDiff > 0 && xDiff < 100) {
            if (yDiff < 0 && Math.abs(yDiff) < 55) { // skeleton above
              if (idleAttack.isVisible()) {
                skeletons.get(i).takeHit();
                checkWave();
              }
            }
            else if (yDiff >= 0 && Math.abs(yDiff) < 30) { //skeleton below
              if (idleAttack.isVisible()) {
                skeletons.get(i).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int j = 0; j < mushrooms.size(); j++ ) { // mushrroms
          double xmDiff = mushrooms.get(j).getX() - getX();
          double ymDiff = mushrooms.get(j).getY() - getY();
          if (xmDiff > 0 && xmDiff < 100) {
            if (ymDiff < 0 && Math.abs(ymDiff) < 55) { // mushroom above
              if (idleAttack.isVisible()) {
                mushrooms.get(j).takeHit();
                checkWave();
              }
            }
            else if (ymDiff >= 0 && Math.abs(ymDiff) < 30) { //mushroom below
              if (idleAttack.isVisible()) {
                mushrooms.get(j).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int k = 0; k < goblins.size(); k++ ) { // goblins
          double xgDiff = goblins.get(k).getX() - getX();
          double ygDiff = goblins.get(k).getY() - getY();
          if (xgDiff > 0 && xgDiff < 100) {
            if (ygDiff < 0 && Math.abs(ygDiff) < 55) { // goblin above
              if (idleAttack.isVisible()) {
                goblins.get(k).takeHit();
                checkWave();
              }
            }
            else if (ygDiff >= 0 && Math.abs(ygDiff) < 30) { //goblin below
              if (idleAttack.isVisible()) {
                goblins.get(k).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int l = 0; l < boss1.size(); l++ ) { // boss
          double xgDiff = boss1.get(l).getX() - getX();
          double ygDiff = boss1.get(l).getY() - getY();
          if (xgDiff > 0 && xgDiff < 100) {
            if (ygDiff < 0 && Math.abs(ygDiff) < 55) { // boss above
              if (idleAttack.isVisible()) {
                boss1.get(l).takeHit();
                checkWave();
              }
            }
            else if (ygDiff >= 0 && Math.abs(ygDiff) < 30) { //boss below
              if (idleAttack.isVisible()) {
                boss1.get(l).takeHit();
                checkWave();
              }
            }
          }
        }
      }
      if ( keysPressed == 0 ) {
        idleAttack.getAnimation().playFromStart();
      }
      animateSamurai();
    });
    
    runAttack.getAnimation().setOnFinished(event4 -> { // run attack finished
      if (!pressedA) {
        for (int i = 0; i < skeletons.size(); i++ ) {
          double xDiff = skeletons.get(i).getX() - getX();
          double yDiff = skeletons.get(i).getY() - getY();
          if (xDiff > 0 && xDiff < 100) {
            if (yDiff < 0 && Math.abs(yDiff) < 55) { // skeleton above
              if ( runAttack.isVisible()) {
                skeletons.get(i).takeHit();
                checkWave();
              }
            }
            else if (yDiff >= 0 && Math.abs(yDiff) < 30) { //skeleton below
              if ( runAttack.isVisible()) {
                skeletons.get(i).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int j = 0; j < mushrooms.size(); j++ ) {
          double xmDiff = mushrooms.get(j).getX() - getX();
          double ymDiff = mushrooms.get(j).getY() - getY();
          if (xmDiff > 0 && xmDiff < 100) {
            if (ymDiff < 0 && Math.abs(ymDiff) < 55) { // skeleton above
              if (runAttack.isVisible()) {
                mushrooms.get(j).takeHit();
                checkWave();
              }
            }
            else if (ymDiff >= 0 && Math.abs(ymDiff) < 30) { //skeleton below
              if (runAttack.isVisible()) {
                mushrooms.get(j).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int k = 0; k < goblins.size(); k++ ) {
          double xgDiff = goblins.get(k).getX() - getX();
          double ygDiff = goblins.get(k).getY() - getY();
          if (xgDiff > 0 && xgDiff < 100) {
            if (ygDiff < 0 && Math.abs(ygDiff) < 55) { // skeleton above
              if (runAttack.isVisible()) {
                goblins.get(k).takeHit();
                checkWave();
              }
            }
            else if (ygDiff >= 0 && Math.abs(ygDiff) < 30) { //skeleton below
              if (runAttack.isVisible()) {
                goblins.get(k).takeHit();
                checkWave();
              }
            }
          }
        }
        for (int l = 0; l < boss1.size(); l++ ) { // goblins
          double xgDiff = boss1.get(l).getX() - getX();
          double ygDiff = boss1.get(l).getY() - getY();
          if (xgDiff > 0 && xgDiff < 100) {
            if (ygDiff < 0 && Math.abs(ygDiff) < 55) { // goblin above
              if (runAttack.isVisible()) {
                boss1.get(l).takeHit();
                checkWave();
              }
            }
            else if (ygDiff >= 0 && Math.abs(ygDiff) < 30) { //goblin below
              if (runAttack.isVisible()) {
                boss1.get(l).takeHit();
                checkWave();
              }
            }
          }
        }
      }
        runAttack.getAnimation().playFromStart();

      animateSamurai();
    });
    
    takeHit.animation.setOnFinished(event -> { //take hit on finished
      takingHit = false;
      if (healthBar.getWidth() > 50.0) {
        healthBar.setWidth(healthBar.getWidth()-50.0);
      }
      else {
        gameIsOver = true;
        death.setTranslateX(idle.getTranslateX());
        death.setTranslateY(idle.getTranslateY());
        idle.setVisible(false);
        walk.setVisible(false);
        flipWalk.setVisible(false);
        idleAttack.setVisible(false);
        runAttack.setVisible(false);
        takeHit.setVisible(false); 
        gamePane.getChildren().remove(idle);
        gamePane.getChildren().remove(walk);
        gamePane.getChildren().remove(flipWalk);
        gamePane.getChildren().remove(idleAttack);
        gamePane.getChildren().remove(runAttack);
        gamePane.getChildren().remove(takeHit);
        death.setVisible(true);
        healthBar.setWidth(0);
        death.play();
      }
      animateSamurai();
    });
    death.animation.setOnFinished(event -> { //death on finished
      setGameOver();
    });
  }
  
  public void setGameOver() {
    if (!setScene) {
      GameOver gameOver = new GameOver(mainStage, mainScene);
      mainStage.setScene(gameOver.getGameOverScene());
      setScene = true;
    }
  }
}
