package application;

import java.awt.*;

import static application.PieceValue.*;

class ChessState {
    private static final int[][] initialPieceState = {
            { Rook, Knight, Bishop, King,  Queen,  Bishop,  Knight,  Rook},
            { Pawn, Pawn, Pawn, Pawn, Pawn, Pawn, Pawn, Pawn},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            {  0,   0,   0,   0,    0,   0,   0,   0},
            { -Pawn, -Pawn,-Pawn, -Pawn, -Pawn, -Pawn, -Pawn, -Pawn},
            { -Rook, -Knight, -Bishop, -King, -Queen, -Bishop, -Knight, -Rook},
    };

    static int[][] getInitialPieceState() {
        int[][] copy = new int[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(initialPieceState[i], 0, copy[i], 0, 8);
        }
        return copy;
    }
}
