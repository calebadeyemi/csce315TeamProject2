package application;

import java.util.ArrayList;
import java.util.List;

import static application.PieceValue.*;


class ChessMovement {
    private static class Position {
        int col;
        int row;

        Position(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

    static int[][] getPotentialMovements(int[][] state, int col, int row) {
        List<Position> potentialMoves;

        int value = state[row][col];
        switch (Math.abs(value)) {
            case Pawn:
                potentialMoves = pawnMoves(state, col, row);
                break;
            case Knight:
                potentialMoves = knightMoves(state, col, row);
                break;
            case Bishop:
                potentialMoves = bishopMoves(state, col, row);
                break;
            case Rook:
                potentialMoves = rookMoves(state, col, row);
                break;
            case Queen:
                potentialMoves = queenMoves(state, col, row);
                break;
            case King:
                potentialMoves = kingMoves(state, col, row);
                break;
            default:
                potentialMoves = null;
        }

        int[][] potentialMovementState = new int[8][8];
        if (potentialMoves != null) {
            for (Position move : potentialMoves) {
                potentialMovementState[move.row][move.col] = 1;
            }
        }

        return potentialMovementState;
    }

    private static ArrayList<Position> knightMoves(int[][] gameState, int col, int row) {
        return new ArrayList<>();
    }

    private static ArrayList<Position> bishopMoves(int[][] gameState, int col, int row) {
        return new ArrayList<>();
    }

    private static ArrayList<Position> rookMoves(int[][] gameState, int col, int row) {
        return new ArrayList<>();
    }

    private static ArrayList<Position> queenMoves(int[][] gameState, int col, int row) {
        return new ArrayList<>();
    }

    private static ArrayList<Position> kingMoves(int[][] gameState, int col, int row) {
        return new ArrayList<>();
    }

    private static ArrayList<Position> pawnMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        // get the value of the present piece to determine set
        int pieceVal = gameState[row][col];

        // if is white move down from top, else move up from bottom
        int dir = 1;
        int startRow = 1;
        if (pieceVal < 0) {
            // is black
            dir *= -1;
            startRow = 6;
        }

        // a pawn can jump 2 spaces from startrow, but only moves 1 space
        // afterwards.
        int maxDist = 1;
        if (row == startRow) {
            maxDist = 2;
        }

        // check in the forward direction for spaces. Pawns cannot move over
        // pieces.
        for (int dist = 1; dist <= maxDist; dist++) {
            int mvmtRow = row + dist * dir;
            if (inBounds(col, mvmtRow, 8)) {
                // if another character in the way, stop trying
                if (gameState[mvmtRow][col] != 0) {
                    break;
                }
                Position p = new Position(col, mvmtRow);
                moves.add(p);
            }
        }

        // check attack vectors, which for pawns are diagonal one space forward
        int[] sides = {-1, 1};
        for (int side : sides) {
            int tgtRow = row + dir;
            int tgtCol = col + side;
            if (inBounds(tgtRow, tgtCol, 8)) {
                // check if the target value is the same color, else it is attackable
                int tgtVal = gameState[tgtRow][tgtCol];
                if (tgtVal * pieceVal < 0) {
                    Position p = new Position(tgtCol, tgtRow);
                    moves.add(p);
                }
            }
        }
        return moves;
    }

    private static boolean inBounds(int row, int col, int bound) {
        return row > -1 && row < bound && col > -1 && col < bound;
    }
}
