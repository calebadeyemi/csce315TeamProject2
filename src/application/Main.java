package application;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        try {
            LocalVsAiGame game = new LocalVsAiGame();
            primaryStage.setTitle("Team Project 2");
            VBox presenter = new VBox();
            presenter.setMinWidth(500);
            presenter.setMinHeight(500);
            primaryStage.setScene(game.generateRootScene(presenter));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
