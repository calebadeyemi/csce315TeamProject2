package application;

import java.awt.*;
import java.util.ArrayList;

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
        return getCopy(initialPieceState);
    }

    static int[][] getCopy(int[][] array) {
        int[][] copy = new int[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(array[i], 0, copy[i], 0, 8);
        }
        return copy;
    }

    static void printState(int[][] state) {
        String[][] chars = new String[8][8];

        String separator = "_________________________________________________";
        System.out.println("\n" + separator);
        for (int row = 0; row < 8; row++) {
            String line = "|";
            for (int col = 0; col < 8; col++) {
                String prefix = state[row][col] > 0 ? "| W" : "| B";
                switch(Math.abs(state[row][col])) {
                    case Rook: line +=    prefix + "R |"; break;
                    case Bishop: line +=  prefix + "B |"; break;
                    case Pawn: line +=    prefix + "P |"; break;
                    case King: line +=    prefix + "K |"; break;
                    case Queen: line +=   prefix + "Q |"; break;
                    case Knight: line +=  prefix + "H |"; break;
                    default: line  +=     "|    |"; break;
                }
            }
            System.out.println(line + "|");
        }
        System.out.println(separator);
    }
}
