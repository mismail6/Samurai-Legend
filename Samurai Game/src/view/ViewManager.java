package view;

import java.util.ArrayList;
import java.util.List;
import control.SubScenery;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.MenuButton;

public class ViewManager {
  
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private AnchorPane mainPane;
  private Stage mainStage;
  private Scene mainScene;
  private final static int START_X = 85;
  private final static int START_Y = 120;
  private SubScenery startSubScene;
  List<MenuButton> menuButtons;
  
  public ViewManager() {
    menuButtons = new ArrayList<MenuButton>();
    mainPane = new AnchorPane();
    mainScene = new Scene(mainPane, WIDTH, HEIGHT);
    mainStage = new Stage();
    mainStage.setScene(mainScene);
    createButtons();
    createBackground();
  }
  
  public Stage returnStage() {
    return mainStage;
  }
  
  private void addMenuButtons(MenuButton button) {
    button.setLayoutX(START_X);
    button.setLayoutY(START_Y + menuButtons.size()*120);
    menuButtons.add(button);
    mainPane.getChildren().add(button);
  }
  private void createButtons() {
    createStartButton();
    createUpgradesButton();
    createCreditsButton();
    createExitButton();
  }
  
  private void createStartButton() {
    MenuButton button = new MenuButton("Start");
    addMenuButtons(button);
    button.setOnAction(event -> {
      StartSceneManager newScene = new StartSceneManager(mainStage, mainScene);
      mainStage.setScene(newScene.getStartScene());
    });
  }
  
  private void createUpgradesButton() {
    MenuButton button = new MenuButton("Samurai");
    addMenuButtons(button);
  }
  
  private void createCreditsButton() {
    MenuButton button = new MenuButton("Credits");
    addMenuButtons(button);
    
  }
  
  private void createExitButton() {
    MenuButton button = new MenuButton("Exit");
    addMenuButtons(button);
    button.setOnAction(event -> {
      mainStage.close();
    });
  }
  
  private void createBackground() {
    Image image = new Image("view/resources/bi2.jpg", WIDTH, HEIGHT, false, true);
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
    mainPane.setBackground(new Background(backgroundImage));
  }
  
  
}