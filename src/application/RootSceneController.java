package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.HashMap;

/**
 * The root scene is the main frame of the GUI. It handles the overall layout of the program. This is where more
 * "global" changes should take place and should be the highest point of abstraction to transfer logic.
 */
class RootSceneController {
    int headerHeight = 70;
    Scene scene;

    /**
     * Generates the root scene for display.
     * @return A fully generated root layout.
     */
    Scene generateRootScene() {
        VBox vBox = new VBox();
        vBox.getChildren().add(generateHeader());
        vBox.getChildren().add(presentGame(new GameController()));

        scene = new Scene(vBox, 500, 500);
        return scene;
    }

    /**
     * Generates the display header
     * @return The header display
     */
    private HBox generateHeader() {
        HBox hBox = new HBox();
        hBox.setMinHeight(headerHeight);
        hBox.setSpacing(70);
        hBox.setAlignment(Pos.CENTER);

        Label scoreBoard = new Label();
        scoreBoard.setText("0 : 0");

        Button restartButton = new Button();
        restartButton.setText("Restart");
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                presentGame(new GameController());
                scoreBoard.setText("0 : 0");
            }
        });
        hBox.getChildren().add(restartButton);
        hBox.getChildren().add(scoreBoard);

        return hBox;
    }

    /**
     * Presents a new game
     * @param gameController
     * @return A board to play
     */
    private Pane presentGame(GameController gameController) {
        GridPane board = (GridPane)gameController.getGameBoard();
        gameController.addPieces(board);

        return board;
    }
}
