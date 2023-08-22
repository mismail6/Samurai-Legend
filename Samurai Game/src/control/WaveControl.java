package control;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.FirstBoss;
import model.Goblin;
import model.Mushroom;
import model.SamuraiControl;
import model.Skeleton;
import view.GameOver;

public class WaveControl {
  
//  private Stage mainStage;
//  private Scene mainScene;
//  private Scene gameScene;
//  private AnchorPane mainPane;
//  private SamuraiControl samurai;
//  private Skeleton skeleton1;
//  private Skeleton skeleton2;
//  private Mushroom mushroom1;
//  
//  
//  public Wave1Control(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane mainPane, SamuraiControl samurai) {
//    this.mainStage = mainStage;
//    this.mainScene = mainScene; 
//    this.gameScene = gameScene;
//    this.mainPane = mainPane;
//    this.samurai = samurai;
//  }
  public static void initializeWave10(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    FirstBoss boss1 = new FirstBoss( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 230, 1);
    samurai.addBoss1(boss1);
  }
  
  
  public static void initializeWave9(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton1 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 500, 1);
    Skeleton skeleton2 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 150, 2);
    Skeleton skeleton3 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 3);
    Skeleton skeleton4 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 250, 4);
    Skeleton skeleton5 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 300, -1);
    Skeleton skeleton6 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 350, -2);
    Skeleton skeleton7 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, -3);
    Skeleton skeleton8 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 450, -4);
    skeleton1.isWave9 = true; skeleton2.isWave9 = true; skeleton3.isWave9 = true; skeleton4.isWave9 = true; skeleton5.isWave9 = true; skeleton6.isWave9 = true; skeleton7.isWave9 = true; skeleton8.isWave9 = true;
    samurai.addSkeleton(skeleton1);
    samurai.addSkeleton(skeleton2);
    samurai.addSkeleton(skeleton3);
    samurai.addSkeleton(skeleton4);
    samurai.addSkeleton(skeleton5);
    samurai.addSkeleton(skeleton6);
    samurai.addSkeleton(skeleton7);
    samurai.addSkeleton(skeleton8);
  }
  public static void initializeWave8(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Goblin goblin1 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),        100, 1);
    Goblin goblin2 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),         400, 2);
    Mushroom mushroom1 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 200, 3, true);
    Mushroom mushroom2 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 300, 4, true);
    samurai.addGoblin(goblin1);
    samurai.addGoblin(goblin2);
    samurai.addMushroom(mushroom1);
    samurai.addMushroom(mushroom2);
    
  }
  public static void initializeWave7(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton1 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, 1);
    Skeleton skeleton2 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 2);
    Skeleton skeleton3 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 250, 3);
    Skeleton skeleton4 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 300, 4);
    Skeleton skeleton5 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 350, 3);
    Mushroom mushroom1 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 250, 1, true);
    Mushroom mushroom2 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 350, 2, true);
    Mushroom mushroom3 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 450, 3, true);
    samurai.addSkeleton(skeleton1);
    samurai.addSkeleton(skeleton2);
    samurai.addSkeleton(skeleton3);
    samurai.addSkeleton(skeleton4);
    samurai.addSkeleton(skeleton5);
    samurai.addMushroom(mushroom1);
    samurai.addMushroom(mushroom2);
    samurai.addMushroom(mushroom3);
  }
  public static void initializeWave6(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Goblin goblin1 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),        200, 1);
    Goblin goblin2 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),         300, 2);
    Goblin goblin3 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),         400, 3);
    samurai.addGoblin(goblin1);
    samurai.addGoblin(goblin2);
    samurai.addGoblin(goblin3);
  }
  public static void initializeWave5(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton1 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 300, 1);
    Goblin goblin1 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),        200, 1);
    Goblin goblin2 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230),         400, 1);
    Mushroom mushroom1 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 300, 3, false);
    samurai.addSkeleton(skeleton1);
    samurai.addGoblin(goblin1);
    samurai.addGoblin(goblin2);
    samurai.addMushroom(mushroom1);
  }
  public static void initializeWave4(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton3 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 1);
    Skeleton skeleton4 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, 4);
    Goblin goblin1 = new Goblin( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 285, 4);
    samurai.addSkeleton(skeleton3);
    samurai.addSkeleton(skeleton4);
    samurai.addGoblin(goblin1);
  }
  public static void initializeWave3(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton1 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 1);
    Skeleton skeleton2 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 300, 2);
    Skeleton skeleton3 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, 4);
    Mushroom mushroom1 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 250, 3, true);
    Mushroom mushroom2 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 350, 3, true);
    samurai.addSkeleton(skeleton1);
    samurai.addSkeleton(skeleton2);
    samurai.addSkeleton(skeleton3);
    samurai.addMushroom(mushroom1);
    samurai.addMushroom(mushroom2);
  }
  
  public static void initializeWave2(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton1 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 1);
    Skeleton skeleton2 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, 4);
    Mushroom mushroom1 = new Mushroom( mainStage, mainScene, gameScene, gamePane, samurai, (1280-100), 300, 3, true);
    samurai.addSkeleton(skeleton1);
    samurai.addSkeleton(skeleton2);
    samurai.addMushroom(mushroom1);
  }
  public static void initializeWave1(Stage mainStage, Scene mainScene, Scene gameScene, AnchorPane gamePane, SamuraiControl samurai) {
    Skeleton skeleton3 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 200, 1);
    Skeleton skeleton4 = new Skeleton( mainStage, mainScene, gameScene, gamePane, samurai, (1280-230), 400, 4);
    samurai.addSkeleton(skeleton3);
    samurai.addSkeleton(skeleton4);
  }
  
}
