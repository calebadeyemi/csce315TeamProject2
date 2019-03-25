package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.HashMap;

class RootSceneController {
    int headerHeight = 50;
    Scene scene;
    VBox vBox = new VBox();
    Label scoreBoard;
    Button startButton;
    HashMap<String, Pane> views = new HashMap<>();

    RootSceneController() {
        views.put("Header", generateHeader());
        views.put("Welcome", generateWelcomePane());
    }

    Scene generateRootScene() {
        vBox.getChildren().add(views.get("Header"));
        vBox.getChildren().add(views.get("Welcome"));

        scene = new Scene(vBox, 500, 500);
        return scene;
    }

    HBox generateHeader() {
        HBox hBox = new HBox();
        hBox.setId("Header");
        hBox.setSpacing(70);
        hBox.setAlignment(Pos.CENTER);

        scoreBoard = new Label();
        scoreBoard.setId("ScoreBoard");

        startButton = new Button();
        startButton.setId("StartBtn");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presentGame();
            }
        });

        startButton.setText("Start");
        hBox.getChildren().add(startButton);
        scoreBoard.setText("Score Board");
        hBox.getChildren().add(scoreBoard);

        return hBox;
    }

    Pane generateWelcomePane() {
        AnchorPane gamePane = new AnchorPane();
        gamePane.setMinHeight(300);
        gamePane.setMinWidth(300);

        Label lbl = new Label();
        lbl.setText("Press \"Start\" to begin");
        gamePane.getChildren().add(lbl);

        return  gamePane;
    }

    void presentGame() {
        GameController gameController = new GameController();
        startButton.setText("Stop");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presentWelcome();
            }
        });
        scoreBoard.setText("0:0");
        GridPane board = (GridPane)gameController.getGameBoard();
        views.put("Game", board);
        gameController.addPieces(board);
        vBox.getChildren().remove(views.get("Welcome"));
        vBox.getChildren().add(views.get("Game"));
    }

    void presentWelcome() {
        startButton.setText("Start");
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presentGame();
            }
        });
        scoreBoard.setText("---");
        vBox.getChildren().remove(views.get("Game"));
        views.remove("Game");
        vBox.getChildren().add(views.get("Welcome"));
    }
}
