package application;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import static application.PieceValue.*;

class ChessBoard {

    ChessBoard() {
        generateImageMap();
    }

    // Associates images with a piece
    private static HashMap<String, Image> generateImageMap() {
        HashMap<String, Image> pieceImageMap = new HashMap<>();
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
        return pieceImageMap;
    }


   static GridPane annotateBoard(GridPane board, int[][] annotationMatrix, String color,
                                 EventHandler<Event> onAnnotationSelected) {
        for (int row = 0; row < annotationMatrix.length; row++) {
            for (int col = 0; col < annotationMatrix.length; col++) {
                if (annotationMatrix[row][col] > 0) {
                    StackPane marker = new StackPane();
                    marker.setMinHeight(50);
                    marker.setMinWidth(50);
                    marker.setStyle("-fx-background-color: " + color + ";");
                    marker.addEventHandler(MouseEvent.MOUSE_CLICKED, onAnnotationSelected);
                    marker.setOpacity(0.2);
                    board.add(marker, col, row);
                }
            }
        }

        return board;
    }

    // Associates the board state with pieces
    static GridPane addPiecesToBoard(GridPane board, int[][] gameState, EventHandler<Event> onPieceSelectd) {
        for (int row = 0; row <gameState.length; row++) {
            for (int col = 0; col < gameState.length; col++) {
                int positionValue = gameState[row][col];
                board = addPieceToPosition(positionValue, board, row, col, onPieceSelectd);
            }
        }
        return board;
    }

    // Assigns a piece to a position based on the positions value in the game state
    private static GridPane addPieceToPosition(int value, GridPane board, int row, int col, EventHandler onSelectPiece) {
        String color = value > 0 ? "white" : "black";
        ImageView image = new ImageView();
        switch(Math.abs(value)) {
            case Pawn:
                image = generatePiece(color, "pawn");
                break;
            case Knight:
                image = generatePiece(color, "knight");
                break;
            case Bishop:
                image = generatePiece(color, "bishop");
                break;
            case Rook:
                image = generatePiece(color, "rook");
                break;
            case Queen:
                image = generatePiece(color, "queen");
                break;
            case King:
                image = generatePiece(color, "king");
                break;
        }
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectPiece);
        board.add(image, col, row);
        return board;
    }

    // Generates a checkered board
    static GridPane drawGameBoard(GridPane board, EventHandler<Event> onSelectSquare) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane square = new StackPane();
                square.setMinHeight(50);
                square.setMinWidth(50);

                String color = (row + col) % 2 == 0 ? "white" : "gray";
                square.setStyle("-fx-background-color: " + color + ";");
                square.setAlignment(Pos.CENTER);
                square.addEventHandler(MouseEvent.MOUSE_CLICKED, onSelectSquare);
                board.add(square, col, row);
            }
        }

        return board;
    }

    private static ImageView generatePiece(String color, String type) {
        return new ImageView(generateImageMap().get(color + "_" + type));
    }

}
