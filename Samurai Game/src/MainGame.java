import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;

public class MainGame extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    
    ViewManager manager = new ViewManager();
    stage = manager.returnStage();
    stage.setResizable(false);
    stage.show();
    
  }
  
  
  
  public static void main(String[] args) {
    try {
    launch(args);
    }
    catch (IndexOutOfBoundsException e) {
      System.out.println("error");
    }
  }

}
