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

enum ChessPiece {
    King, Queen, Rook, Bishop, Knight, Pawn
}

public class GameController {
    HashMap<String, Image> pieceMap = new HashMap<>();
    /**
     * The standard chess board size is 8x8
     */
    private final int size = 8 ;

    GameController() {
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
         * Set alternating colors for the board
         */
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

    /**
     *
     * @param color
     * @param piece
     * @return
     */
    PieceView getPiece(String color, String piece) {
        SetColor setColor = color.equals("white") ? SetColor.White : SetColor.Black;
        ChessPiece chessPiece = null;

        switch (piece) {
            case "king": chessPiece = ChessPiece.King; break;
            case "queen": chessPiece = ChessPiece.Queen; break;
            case "rook": chessPiece = ChessPiece.Rook; break;
            case "bishop": chessPiece = ChessPiece.Bishop; break;
            case "knight": chessPiece = ChessPiece.Knight; break;
            case "pawn": chessPiece = ChessPiece.Pawn; break;
        }

        PieceView view =  new PieceView(pieceMap.get(color + "_" + piece));
        view.color = setColor;
        view.piece = chessPiece;
        view.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(view.color);
                System.out.println(view.piece);
            }
        });
        return view;
    }

    void addSet(GridPane root, String color, int kingRow, int pawnRow) {
        root.add(getPiece(color, "rook"),   0, kingRow);
        root.add(getPiece(color, "knight"), 1, kingRow );
        root.add(getPiece(color, "bishop"), 2, kingRow );
        root.add(getPiece(color, "king"),   3, kingRow );
        root.add(getPiece(color, "queen"),  4, kingRow );
        root.add(getPiece(color, "bishop"), 5, kingRow );
        root.add(getPiece(color, "knight"), 6, kingRow );
        root.add(getPiece(color, "rook"),   7, kingRow );
        root.add(getPiece(color, "pawn"),   0, pawnRow);
        root.add(getPiece(color, "pawn"),   1, pawnRow );
        root.add(getPiece(color, "pawn"),   2, pawnRow );
        root.add(getPiece(color, "pawn"),   3, pawnRow );
        root.add(getPiece(color, "pawn"),   4, pawnRow );
        root.add(getPiece(color, "pawn"),   5, pawnRow );
        root.add(getPiece(color, "pawn"),   6, pawnRow );
        root.add(getPiece(color, "pawn"),   7, pawnRow );
    }

    void addPieces(GridPane root) {
        addSet(root, "white", 0, 1);
        addSet(root, "black", 7, 6);
    }
}
