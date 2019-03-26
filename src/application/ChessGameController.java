package application;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


class ChessGameController {
    private int[][] state = new int[8][8];
    private int[][] movementState = new int[8][8];
    private Pane root;
    private GridPane board;
    private Node selectedPiece;

    private EventHandler<Event> handleSelectPiece = event -> getPieceMoves((Node) event.getSource());
    private EventHandler<Event> handleSelectMove = event -> movePiece((Node) event.getSource());
    private EventHandler<Event> handleUiUpdate = event -> updateScene();
    private EventHandler<Event> handleReset = event -> resetScene();

    Scene generateRootScene(Pane root) {
        this.root = root;
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(70);

        Button resetBtn = new Button();
        resetBtn.setText("Reset");
        resetBtn.setSkin(new ButtonSkin(resetBtn) {{ this.consumeMouseEvents(false); }});
        resetBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, handleReset);

        header.getChildren().add(resetBtn);

        Label scoreBoard = new Label();
        scoreBoard.setText("0 : 0");
        header.getChildren().add(scoreBoard);
        root.getChildren().add(header);

        state = ChessState.getInitialPieceState();
        board = drawBoard(state);
        root.getChildren().add(board);

        return new Scene(root);
    }

    private GridPane drawBoard(int[][] state) {
        GridPane board = ChessBoard.drawGameBoard(new GridPane());
        board.addEventHandler(MouseEvent.MOUSE_CLICKED, handleUiUpdate);
        return ChessBoard.addPiecesToBoard(board, state, handleSelectPiece);
    }

    private GridPane drawMovementAnnotations(GridPane board, int[][] moves) {
        return ChessBoard.annotateBoard(board, moves, "green", handleSelectMove);
    }

    private void getPieceMoves(Node piece) {
        selectedPiece = piece;
        int col = GridPane.getColumnIndex(piece);
        int row = GridPane.getRowIndex(piece);
        if (state[row][col] < 0) {
            movementState = new int[8][8];
            movementState = ChessMovement.getPotentialMovements(state, col, row);
        } else {
            selectedPiece = null;
        }
    }

    private void movePiece(Node space) {
        movementState = new int[8][8];
        int toCol = GridPane.getColumnIndex(space);
        int toRow = GridPane.getRowIndex(space);
        int fromCol = GridPane.getColumnIndex(selectedPiece);
        int fromRow = GridPane.getRowIndex(selectedPiece);

        state = ChessMovement.applyMove(state, fromRow, fromCol, toRow, toCol);
        state = ChessAi.MovePiece(state);
    }

    private void updateScene() {
        root.getChildren().remove(board);
        board = drawBoard(state);
        board = drawMovementAnnotations(board, movementState);
        root.getChildren().add(board);
    }

    private void resetScene() {
        state = ChessState.getInitialPieceState();
        movementState = new int[8][8];
        updateScene();
    }
}
