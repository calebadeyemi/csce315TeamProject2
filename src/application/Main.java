package application;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Team Project 2");

            RootSceneController rootSceneController = new RootSceneController();
            primaryStage.setScene(rootSceneController.generateRootScene(new VBox()));

            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
