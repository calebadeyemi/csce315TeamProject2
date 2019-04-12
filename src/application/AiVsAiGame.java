package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class AiVsAiGame implements Runnable {
    private int[][] state = ChessState.getInitialPieceState();
    private int[][] movementState = new int[8][8];
    private Pane root;
    private GridPane board = new GridPane();
    private ChessMoveMakeable white = new ChessAi(PieceValue.White);
    private ChessMoveMakeable black = new ChessAi(PieceValue.Black);

    private EventHandler<Event> handleUiUpdate = event -> updateScene();

    Scene generateRootScene(Pane root) {
        this.root = root;
        updateScene();
        return new Scene(root);
    }

    private GridPane drawBoard(int[][] state) {
        GridPane board = ChessBoard.drawGameBoard(new GridPane(), event -> {});
        return ChessBoard.addPiecesToBoard(board, state, event -> {});
    }

    private void updateScene() {
        root.getChildren().remove(board);
        board = drawBoard(state);
        root.getChildren().add(board);
    }

    private void resetScene() {
        state = ChessState.getInitialPieceState();
        movementState = new int[8][8];
        updateScene();
    }

    @Override
    public void run() {
        updateScene();
        state = ChessMovement.applyMove(state, white.getMove(state));
        updateScene();
        state = ChessMovement.applyMove(state, black.getMove(state));
    }
}
