package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

class PieceView extends ImageView {
    ChessPiece piece;
    SetColor color;

    PieceView(Image image) {
        super(image);
    }
}
