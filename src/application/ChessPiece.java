package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class ChessPiece extends ImageView {
    PieceType type;
    SetColor color;
    int value;

    ChessPiece(Image image) {
        super(image);
    }
}
