package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public void start(Stage primaryStage) {
        try {
            RootSceneController rootSceneController = new RootSceneController();
            primaryStage.setTitle("Team Project 2");
            primaryStage.setScene(rootSceneController.generateRootScene());
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
