package view;

import control.WaveControl;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.SamuraiControl;

public class StartSceneManager{
  
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  
  private AnchorPane mainPane;
  private Scene gameScene;
  private Scene mainScene;
  private Stage mainStage;
  
  
  public StartSceneManager(Stage mainStage, Scene mainScene) {
    mainPane = new AnchorPane();
    this.mainScene = mainScene;
    this.mainStage = mainStage;
    gameScene = new Scene(mainPane, WIDTH, HEIGHT);
    initializeImage();
    initializeGame();
  }
  
  private void initializeGame() {
    SamuraiControl samurai = new SamuraiControl(mainStage, mainScene, gameScene, mainPane);
    WaveControl.initializeWave1(mainStage, mainScene, gameScene, mainPane, samurai);
    samurai.setOnTop();
  }
  
  private void initializeImage() {
    Image image = new Image("model/resources/hall.jpg", WIDTH, HEIGHT, false, true);
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
    
    ImageView imageView = new ImageView(new Image("model/resources/HealthBar.png", 500, 70, false, true));
    mainPane.setBackground(new Background(backgroundImage));
    mainPane.getChildren().add(imageView);
    imageView.setLayoutX(5);
    imageView.setLayoutY(5);
  }
  
  public Scene getStartScene() {
    return gameScene;
  }
  
  
  
}
