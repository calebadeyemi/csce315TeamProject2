package application;

import javafx.application.Application;
import javafx.stage.Stage;

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
