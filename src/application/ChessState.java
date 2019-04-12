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

    static int[][] getFlippedReverseCopy(int[][] array) {
        int[][] frc = new int[8][8];
        for (int i = 7; i >= 0; i--) {
            System.arraycopy(array[i], 0, frc[7 - i], 0, 8);
        }

        for (int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                frc[row][col] *= 1;
            }
        }
        return frc;
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

    static int[][] getOptimalPieceState(int pieceValue) {
        int[][] copy = new int[8][8];

        switch (Math.abs(pieceValue)) {
            case King: copy     = optimalKingState; break;
            case Queen: copy    = optimalQueenState; break;
            case Rook: copy     = optimalRookState; break;
            case Bishop: copy   = optimalBishopState; break;
            case Knight: copy   = optimalKnightState; break;
            case Pawn: copy     = optimalPawnState; break;
        }

        if (!(pieceValue < 0)) {
            return getFlippedReverseCopy(copy);
        } else {
            return copy;
        }
    }

    private static int[][] optimalKingState = {
            { -3, -4, -4, -5, -5, -4, -4, -3},
            { -3, -4, -4, -5, -5, -4, -4, -3},
            { -3, -4, -4, -5, -5, -4, -4, -3},
            { -3, -4, -4, -5, -5, -4, -4, -3},
            { -2, -3, -4, -4, -4, -4, -3, -2},
            { -1, -2, -2, -2, -2, -2, -2, -1},
            { 02, 02, 00, 00, 00, 00, 02, 02},
            { 02, 03, 01, 00, 00, 01, 03, 02},
    };

    private static int[][] optimalQueenState = {
            { -2, -1, -1, 00, 00, -1, -1, -2},
            { -1, 00, 00, 05, 05, 00, 00, -1},
            { -1, 00, 01, 01, 01, 01, 00, -1},
            { 00, 00, 01, 01, 01, 01, 00, 00},
            { 00, 00, 01, 01, 01, 01, 00, 00},
            { -1, 00, 01, 01, 01, 01, 00, -1},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -2, -1, -1, 00, 00, -1, -1, -2},
    };

    private static int[][] optimalRookState = {
            { 00, 00, 00, 00, 00, 00, 00, 00},
            { 01, 01, 01, 01, 01, 01, 01, 01},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { 00, 00, 00, 01, 01, 00, 00, 00},
    };

    private static int[][] optimalBishopState = {
            { -2, -1, -1, -1, -1, -1, -1, -2},
            { -1, 00, 00, 00, 00, 00, 00, -1},
            { -1, 00, 01, 02, 02, 01, 00, -1},
            { -1, 01, 01, 02, 02, 01, 01, -1},
            { -1, 01, 01, 02, 02, 01, 01, -1},
            { -1, 02, 01, 02, 02, 01, 02, -1},
            { -1, 01, 00, 00, 00, 00, 01, -1},
            { -2, -1, -1, -1, -1, -1, -1, -2},
    };

    private  static int[][] optimalKnightState = {
            { -5, -4, -3, -3, -3, -3, -4, -5},
            { -4, -2, 00, 00, 00, 00, -2, -4},
            { -3, 00, 01, 02, 02, 01, 00, -3},
            { -3, 01, 02, 03, 03, 02, 01, -3},
            { -3, 01, 02, 03, 03, 02, 01, -3},
            { -3, 00, 01, 02, 02, 01, 00, -3},
            { -4, -2, 00, 01, 01, 00, -2, -4},
            { -5, -4, -3, -3, -3, -3, -4, -5},
    };

    private static int[][] optimalPawnState = {
            { 00, 00, 00, 00, 00, 00, 00, 00},
            { 05, 05, 05, 05, 05, 05, 05, 05},
            { 01, 01, 01, 01, 01, 01, 02, 01},
            { 01, 01, 02, 03, 03, 02, 01, 00},
            { 00, 01, 02, 03, 03, 02, 01, 03},
            { 01, 01, -1, 00, 00, -2, -1, 01},
            { 01, 02, 02, -2, -2, 01, 01, 01},
            { 00, 00, 00, 00, 00, 00, 00, 05},
    };
}
