package Calculator;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculator extends Application
{

  public static void main(String[] args)
  {
    launch(args);
  }

  public void start(Stage window) throws IOException
  {
    window.setTitle("Calculator");
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("Calculator.fxml"));
    Scene scene = new Scene(loader.load());
    window.setScene(scene);
    window.show();
  }
}
