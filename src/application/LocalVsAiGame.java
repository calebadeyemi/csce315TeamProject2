package application;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;

class LocalVsAiGame {
    private int[][] state = new int[8][8];
    private int[][] movementState = new int[8][8];
    private Pane root;
    private GridPane board;
    private Node selectedPiece;
    private ChessMoveMakeable opponent = new ChessAi(PieceValue.White);

    private EventHandler<Event> handleSelectPiece = event -> getPieceMoves((Node) event.getSource());
    private EventHandler<Event> handleSelectMove = event -> movePiece((Node) event.getSource());
    private EventHandler<Event> handleUiUpdate = event -> updateScene();
    private EventHandler<Event> handleReset = event -> resetScene();

    Scene generateRootScene(Pane root) {
        this.root = root;
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(70);
        header.setMinHeight(50);

        Button resetBtn = new Button();
        resetBtn.setText("Reset");
        resetBtn.setSkin(new ButtonSkin(resetBtn) {{ this.consumeMouseEvents(false); }});
        resetBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, handleReset);

        header.getChildren().add(resetBtn);

        HBox choice = new HBox();
        choice.setAlignment(Pos.CENTER);
        Label choiceLbl = new Label();
        choiceLbl.setText("Opponent: ");
        ChoiceBox opponentSelection = new ChoiceBox();
        opponentSelection.setItems(FXCollections.observableArrayList("computer", "local", "remote"));
        opponentSelection.setValue("computer");
        opponentSelection.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch (newValue.intValue()) {
                    case 0 : opponent = new ChessAi(PieceValue.Black); break;
                    case 1 : opponent = new LocalOpponent(); break;
                    case 2 : opponent = new RemoteOpponent(); break;
                }
            }
        });
        choice.getChildren().addAll(choiceLbl, opponentSelection);
        header.getChildren().add(choice);

        Label scoreBoard = new Label();
        scoreBoard.setText("0 : 0");
        header.getChildren().add(scoreBoard);
        root.getChildren().add(header);

        state = ChessState.getInitialPieceState();
        board = drawBoard(state);
        root.getChildren().add(board);

        return new Scene(root);
    }

    private void updateOpponent(ChessMoveMakeable opponent) {

    }

    private GridPane drawBoard(int[][] state) {
        GridPane board = ChessBoard.drawGameBoard(new GridPane(), handleSelectPiece);
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
            ArrayList<Move> moves = ChessMovement.getPotentialMovements(state, col, row);
            for (Move move : moves)
                movementState[move.rowTo][move.colTo] = 1;
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

        state = ChessMovement.applyMove(ChessState.getCopy(state), new Move(toRow, toCol, fromRow, fromCol));
        System.out.println("Score: " + ChessState.sumState(state));
        state = ChessMovement.applyMove(ChessState.getCopy(state), opponent.getMove(ChessState.getCopy(state)));
        System.out.println("Score: " + ChessState.sumState(state));
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
