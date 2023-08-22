package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

public class MenuButton extends Button{
  private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
  private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/red_button_pressed.png');";
  private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/red_button.png');";
  
  public MenuButton(String text) {
    setText(text);
    setButtonFont();
    setPrefWidth(190);
    setPrefHeight(49);
    setStyle(BUTTON_STYLE);
    initializeListeners();
  }
  
  private void setButtonFont() {
    try {
      setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void setPressedStyle() {
    setStyle(BUTTON_PRESSED_STYLE);
    setPrefHeight(45);
    setLayoutY(getLayoutY() + 4);
  }
  
  private void setReleasedStyle() {
    setStyle(BUTTON_STYLE);
    setPrefHeight(49);
    setLayoutY(getLayoutY() - 4);
  }
  
  private void initializeListeners() {
    setOnMousePressed( (event) -> {
      if (event.getButton().equals(MouseButton.PRIMARY)) {
        setPressedStyle();
      }
    });
    
    setOnMouseReleased( (event) -> {
      if (event.getButton().equals(MouseButton.PRIMARY)) {
        setReleasedStyle();
      }
    });
    
    setOnMouseEntered( (event) -> {
      setEffect(new DropShadow());
    });
    
    setOnMouseExited( (event) -> {
      setEffect(null);
    });
  }
}
