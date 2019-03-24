package application;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

class GameController {
    GridPane root = new GridPane();
    final int size = 8 ;

    Pane getGameScene() {
        root.setMinHeight(500);
        root.setMinWidth(500);
        root.setMaxHeight(600);
        root.setMaxWidth(600);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "black";
                }
                square.setStyle("-fx-background-color: "+color+";");
                root.add(square, col, row);
            }
        }
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
        return root;
    }

    void addPieces() {
        try {
            FileInputStream stream = new FileInputStream("images/king.png");
            Image image = new Image(stream);
            ImageView view = new ImageView(image);
            root.add(view, 1, 0);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found");
        }
    }
}
