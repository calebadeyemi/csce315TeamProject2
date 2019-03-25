package application;

import com.sun.javafx.scene.control.skin.ButtonSkin;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static application.PieceValue.*;

class Position {
    int col;
    int row;

    Position(int col, int row) {
        this.col = col;
        this.row = row;
    }
}

class GameController {
    private int boardSize = 8;
    // Set to the initial game state
    private final int[][] initialPieceState = {
            { Rook, Knight, Bishop, King,  Queen,  Bishop,  Knight,  Rook},
            { Pawn, Pawn, Pawn, Pawn, Pawn, Pawn, Pawn, Pawn},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            { -Pawn, -Pawn,-Pawn, -Pawn, -Pawn, -Pawn, -Pawn, -Pawn},
            { -Rook, -Knight, -Bishop, -King, -Queen, -Bishop, -Knight, -Rook},
    };
    private int[][] gameState = new int[boardSize][boardSize];

    private void resetGameState() {
        reset(gameState, initialPieceState);
    }

    private int getWhiteScore(int[][] state) {
        int score = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int value = state[row][col];
                if(value > 0) {
                   score += value;
                }
            }
        }
        return score;
    }

    private int getBlackScore(int[][] state) {
        int score = 0;
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int value = state[row][col];
                if(value < 0) {
                   score += -value;
                }
            }
        }
        return score;
    }

    private final int[][] initialMoveMarkerState = {
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0}
    };
    private int[][] moveMarkerState = new int[boardSize][boardSize];

    private void resetMarkerState() {
        reset(moveMarkerState, initialMoveMarkerState);
    }

    private void reset(int[][] currentState, int[][] initialState) {
        for (int row = 0; row < boardSize; row++) {
            System.arraycopy(initialState[row], 0, currentState[row], 0, boardSize);
        }
    }

    private Position moveFrom = null;

    private HashMap<String, Image> pieceImageMap = new HashMap<>();

    private EventHandler<Event> resetState = event -> {
        resetGameState();
        resetMarkerState();
    };

    private EventHandler<Event> showMoves = event -> {
        Node source = (Node)event.getSource();
        resetMarkerState();

        moveFrom = new Position(GridPane.getColumnIndex(source), GridPane.getRowIndex(source));
        List<Position> moves = getLegalMoves(gameState, moveFrom.col, moveFrom.row);

        if (moves != null) {
            for (Position move : moves) {
                moveMarkerState[move.row][move.col] = 1;
            }
        }
    };

    private EventHandler<Event> makeMove = event -> {
        Node source = (Node)event.getSource();
        int value = gameState[moveFrom.row][moveFrom.col];

        gameState[moveFrom.row][moveFrom.col] = 0;
        gameState[GridPane.getRowIndex(source)][GridPane.getColumnIndex(source)] = value;
        resetMarkerState();
    };

    GameController() {
        generateImageMap();
        resetGameState();
    }

    // Associates images with a piece
    private void generateImageMap() {
        try {
            for (String color : Colors) {
                for (String piece : Pieces) {
                    String path = "images/" + color + "_" + piece + ".png";
                    Image image = new Image(new FileInputStream(path));
                    String handle = color + "_" + piece;
                    pieceImageMap.put(handle, image);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Failed to build image map. Image file not found.");
        }
    }

    Pane drawCurrentState() {
        VBox vBox = new VBox();
        vBox.getChildren().add(drawHeader());

        GridPane board = new GridPane();
        board = drawGameBoard(board);
        board = addPiecesToBoard(board, gameState);
        board = annotateBoard(board, moveMarkerState);
        vBox.getChildren().add(board);

        return vBox;
    }

    private Pane drawHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(70);

        Button resetBtn = new Button();
        resetBtn.setText("Reset");
        resetBtn.setSkin(new ButtonSkin(resetBtn) {{ this.consumeMouseEvents(false); }});
        resetBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, resetState);

        header.getChildren().add(resetBtn);

        Label scoreBoard = new Label();
        int whiteScore = getWhiteScore(gameState);
        int blackScore = getBlackScore(gameState);

        if (whiteScore < King || blackScore < King) {
            scoreBoard.setText((whiteScore < King ? "White" : "Black") + " loses");
        } else {
            scoreBoard.setText("W: " + getWhiteScore(gameState) + " | B: " + getBlackScore(gameState));
        }
        header.getChildren().add(scoreBoard);

        Label checkMarker = new Label();
        header.getChildren().add(checkMarker);
        return header;
    }

    private GridPane annotateBoard(GridPane board, int[][] moveMarkerState) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (moveMarkerState[row][col] > 0) {
                    StackPane marker = new StackPane();
                    marker.setMinHeight(50);
                    marker.setMinWidth(50);
                    marker.setStyle("-fx-background-color: green;");
                    marker.addEventHandler(MouseEvent.MOUSE_CLICKED, makeMove);
                    marker.setOpacity(0.2);
                    board.add(marker, col, row);
                }
            }
        }

        return board;
    }

    // Associates the board state with pieces
    private GridPane addPiecesToBoard(GridPane board, int[][] gameState) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                int positionValue = gameState[row][col];
                board = addPieceToPosition(positionValue, board, row, col);
            }
        }
        return board;
    }

    // Assigns a piece to a position based on the positions value in the game state
    private GridPane addPieceToPosition(int value, GridPane board, int row, int col) {
        String color = value > 0 ? "white" : "black";
        switch(Math.abs(value)) {
            case Pawn:
                board.add(generatePiece(color, "pawn"), col, row);
                break;
            case Knight:
                board.add(generatePiece(color, "knight"), col, row);
                break;
            case Bishop:
                board.add(generatePiece(color, "bishop"), col, row);
                break;
            case Rook:
                board.add(generatePiece(color, "rook"), col, row);
                break;
            case Queen:
                board.add(generatePiece(color, "queen"), col, row);
                break;
            case King:
                board.add(generatePiece(color, "king"), col, row);
                break;
        }
        return board;
    }

    // Generates a checkered board that handles events
    private GridPane drawGameBoard(GridPane board) {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                StackPane square = new StackPane();
                square.setMinHeight(50);
                square.setMinWidth(50);

                String color = (row + col) % 2 == 0 ? "white" : "gray";
                square.setStyle("-fx-background-color: " + color + ";");
                square.setAlignment(Pos.CENTER);
                square.addEventHandler(MouseEvent.MOUSE_CLICKED, showMoves);
                board.add(square, col, row);
            }
        }

        return board;
    }

    private List<Position> getLegalMoves(int[][] gameState, int col, int row) {
        switch (Math.abs(gameState[row][col])) {
            case Pawn: return pawnMoves(gameState, col, row);
        }
        return null;
    }

    private ArrayList<Position> pawnMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        int pieceVal = gameState[row][col];

        int dir = 1;
        int startRow = 1;
        // if is white move down, else move up
        if (pieceVal < 0) {
            dir *= -1;
            startRow = 6;
        }

        int maxDist = 1;
        if (row == startRow) {
            maxDist = 2;
        }

        for (int dist = 1; dist <= maxDist; dist++) {
            int mvmtRow = row + dist * dir;
            if (inBounds(col, mvmtRow)) {
                // if another character in the way, stop trying
                if (gameState[mvmtRow][col] != 0) {
                    break;
                }
                Position p = new Position(col, mvmtRow);
                moves.add(p);
            }
        }

        // check attack vectors
        int[] sides = {-1, 1};
        for (int side : sides) {
            int tgtRow = row + dir;
            int tgtCol = col + side;
            if (inBounds(tgtRow, tgtCol)) {
                int tgtVal = gameState[tgtRow][tgtCol];

                // if they are not the same color or occupied
                if (tgtVal * pieceVal < 0) {
                    Position p = new Position(tgtCol, tgtRow);
                    moves.add(p);
                }
            }
        }
        return moves;
    }

    private boolean inBounds(int row, int col) {
        if (row > -1 && row < boardSize && col > -1 && col < boardSize) {
            return true;
        }
        return false;
    }

    private ImageView generatePiece(String color, String type) {
        return new ImageView(pieceImageMap.get(color + "_" + type));
    }
}
