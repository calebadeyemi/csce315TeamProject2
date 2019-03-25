package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

enum SetColor {
    White, Black
}

enum PieceType {
    King, Queen, Rook, Bishop, Knight, Pawn
}

class GameController {
    HashMap<String, Image> pieceMap = new HashMap<>();

    GameController() {
        generateImageMap();
    }

    private void generateImageMap() {
         try {
            String[] colors = {"black", "white"};
            String[] pieces = {"king", "queen", "bishop", "rook", "knight", "pawn"};

            for (String color : colors) {
                for (String piece : pieces) {
                    String path = "images/" + color + "_" + piece + ".png";
                    Image image = new Image(new FileInputStream(path));
                    String handle = color + "_" + piece;
                    pieceMap.put(handle, image);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Piece image file not found.");
        }
    }

    Pane getGameBoard() {
        GridPane root = new GridPane();
        root.setMinHeight(500);
        root.setMinWidth(500);
        root.setMaxHeight(600);
        root.setMaxWidth(600);

        /*
         * Set alternating colors for the board.
         * The standard chess board size is 8x8.
         */
        int size = 8;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                square.setMinHeight(50);
                square.setMinWidth(50);

                String color = (row + col) % 2 == 0 ? "white" : "gray";
                square.setStyle("-fx-background-color: " + color + ";");
                square.setAlignment(Pos.CENTER);

                root.add(square, col, row);
            }
        }
        return root;
    }

    private ChessPiece generatePiece(String color, String type) {
        ChessPiece chessPiece =  new ChessPiece(pieceMap.get(color + "_" + type));
        chessPiece.color = color.equals("white") ? SetColor.White : SetColor.Black;
        switch (type) {
            case "king"  :
                chessPiece.type = PieceType.King;
                chessPiece.value = 900;
                break;
            case "queen" :
                chessPiece.type = PieceType.Queen;
                chessPiece.value = 90;
                break;
            case "rook"  :
                chessPiece.type = PieceType.Rook;
                chessPiece.value = 50;
                break;
            case "bishop":
                chessPiece.type = PieceType.Bishop;
                chessPiece.value = 30;
                break;
            case "knight":
                chessPiece.type = PieceType.Knight;
                chessPiece.value = 30;
                break;
            case "pawn"  :
                chessPiece.type = PieceType.Pawn;
                chessPiece.value = 10;
                break;
        }

        chessPiece.addEventHandler(MouseEvent.MOUSE_CLICKED, new ChessAction(chessPiece));

        return chessPiece;
    }

    void addPieces(GridPane root) {
        addSet(root, "white", 0, 1);
        addSet(root, "black", 7, 6);
    }

    private void addSet(GridPane root, String color, int kingRow, int pawnRow) {
        root.add(generatePiece(color, "rook"),   0, kingRow);
        root.add(generatePiece(color, "knight"), 1, kingRow );
        root.add(generatePiece(color, "bishop"), 2, kingRow );
        root.add(generatePiece(color, "king"),   3, kingRow );
        root.add(generatePiece(color, "queen"),  4, kingRow );
        root.add(generatePiece(color, "bishop"), 5, kingRow );
        root.add(generatePiece(color, "knight"), 6, kingRow );
        root.add(generatePiece(color, "rook"),   7, kingRow );
        root.add(generatePiece(color, "pawn"),   0, pawnRow);
        root.add(generatePiece(color, "pawn"),   1, pawnRow );
        root.add(generatePiece(color, "pawn"),   2, pawnRow );
        root.add(generatePiece(color, "pawn"),   3, pawnRow );
        root.add(generatePiece(color, "pawn"),   4, pawnRow );
        root.add(generatePiece(color, "pawn"),   5, pawnRow );
        root.add(generatePiece(color, "pawn"),   6, pawnRow );
        root.add(generatePiece(color, "pawn"),   7, pawnRow );
    }
}
