package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.MenuButton;

public class GameOver {
  
  private Scene overScene;
  private AnchorPane overPane;
  
  public GameOver(Stage mainStage, Scene mainScene) {
    overPane = new AnchorPane();
    Image image = new Image("view/resources/GameOver.jpg", 1280, 720, false, true);
    BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
    overPane.setBackground(new Background(backgroundImage));
    MenuButton button = new MenuButton("Return to Main Menu");
    button.setLayoutX(525);
    button.setLayoutY(520);
    overPane.getChildren().add(button);
    button.setOnAction(event -> {
      mainStage.setScene(mainScene);
    });
  }
  
  public Scene getGameOverScene() {
    return new Scene(overPane, 1280, 720);
  }
}
