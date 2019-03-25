package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

class ChessAction implements EventHandler<MouseEvent> {
    ChessPiece piece;
    ChessAction(ChessPiece piece) {
        this.piece = piece;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}
