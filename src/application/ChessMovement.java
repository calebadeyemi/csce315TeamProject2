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

    static int[][] applyMove(int[][]state, int fromRow, int fromCol, int toRow,int toCol) {
        int value = state[fromRow][fromCol];
        state[fromRow][fromCol] = 0;
        state[toRow][toCol] = value;
        return state;
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
        ArrayList<Position> moves = new ArrayList<>();


        //get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        //movement of knight
        int[] rowOffset = {-2, 2};
        int[] colOffset = {-1, 1};
        for(int side : rowOffset) {
            int tgtCol = 0;
            int tgtRow = 0;

            for (int side2 : colOffset) {
                tgtRow = side + row;
                tgtCol = side2 + col;

                if (isValid(pieceColor, gameState, tgtRow, tgtCol)) {
                    Position p = new Position(tgtCol, tgtRow);
                    moves.add(p);
                }

                tgtRow = row + side2;
                tgtCol = col + side;

                if (isValid(pieceColor, gameState, tgtRow, tgtCol)) {
                    Position p = new Position(tgtCol, tgtRow);
                    moves.add(p);
                }
            }
        }


        return moves;
    }

    private static ArrayList<Position> bishopMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        //get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        int i = 0;
        int num = 0;

        //bishop can move diagonally to the left
        int[] rowOffset = {-1};
        int[] colOffset = {-1};
        while(num < 8) {
            for (int side : rowOffset) {
                for (int side2 : colOffset) {
                    int tgtRow = side + row + i;
                    int tgtCol = side2 + col + i;

                    if (inBounds(tgtRow, tgtCol, 8)) {
                        //check to see when can attack ( same color)
                    int tgtVal = gameState[tgtRow][tgtCol];
                        if (tgtVal * pieceColor <= 0) {
                            Position p = new Position(tgtCol, tgtRow);
                            moves.add(p);
                        }
                    }
                }
            }
            i = i -1;
            num++;
        }

        //bishop can move diagonally to the right
        num = 0;
        int j =0;
        int[] rowOffset2 = {-1};
        int[] colOffset2 = {1};

        while(num < 8) {
            for (int side3 : rowOffset2) {
                for (int side4 : colOffset2) {
                    int tgtRow2 = side3 + row + j;
                    int tgtCol2 = side4 + col - j;

                    if (inBounds(tgtRow2, tgtCol2, 8)) {
                        //check to see when can attack ( same color)
                        int tgtVal = gameState[tgtRow2][tgtCol2];
                        if (tgtVal * pieceColor <= 0) {
                            Position p = new Position(tgtCol2, tgtRow2);
                            moves.add(p);
                        }
                    }
                }
            }
            j = j -1;
            num++;
        }

        //bishop can move down diagonally to the left
        num = 0;
        int k =0;
        int[] rowOffset3 = {1};
        int[] colOffset3 = {1};
        while(num < 8) {
            for (int side5 : rowOffset3) {
                for (int side6 : colOffset3) {
                    int tgtRow3 = side5 + row + k;
                    int tgtCol3 = side6 + col + k;

                    if (inBounds(tgtRow3, tgtCol3, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol3, tgtRow3);
                        moves.add(p);
                    }
                }
            }
            k = k + 1;
            num++;
        }

        //bishop can move down diagonally to the right
        num = 0;
        int l =0;
        int[] rowOffset4 = {-1};
        int[] colOffset4 = {1};

        while(num < 8) {
            for (int side7 : rowOffset4) {
                for (int side8 : colOffset4) {
                    int tgtRow4 = side7 + row - l;
                    int tgtCol4 = side8 + col + l;

                    if (inBounds(tgtRow4, tgtCol4, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol4, tgtRow4);
                        moves.add(p);
                    }
                }
            }
            l = l -1;
            num++;
        }


        return moves;
    }

    private static ArrayList<Position> rookMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        //get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        //movement of rook
        int[] rowOffset = {1};
        int[] colOffset = {1};

        //rook can move up
        int i = 0;
        int num = 0;
        while(num < 8){
            for(int side : rowOffset) {
                int tgtRow = row + i;

                if (inBounds(tgtRow, col, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(col, tgtRow);
                    moves.add(p);
                }

            }
            i = i -1;
            num++;
        }

        //rook can move left
        num = 0;
        int j = 0;
        while(num < 8){
            for(int side2 : colOffset) {
                int tgtCol = col - j;

                if (inBounds(row, tgtCol, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(tgtCol, row);
                    moves.add(p);
                }

            }
            j = j + 1;
            num++;
        }

        //rook can move down
        num = 0;
        int m = 0;
        while(num < 8){
            for(int side3 : rowOffset) {
                int tgtRow2 = row + m;

                if (inBounds(tgtRow2, col, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(col, tgtRow2);
                    moves.add(p);
                }

            }
            m = m + 1;
            num++;
        }

        //rook can move right
        num = 0;
        int n = 0;
        while(num < 8){
            for(int side4 : colOffset) {
                int tgtCol2 = col + n;

                if (inBounds(row, tgtCol2, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(tgtCol2, row);
                    moves.add(p);
                }

            }
            n = n + 1;
            num++;
        }


        return moves;
    }

    private static ArrayList<Position> queenMoves(int[][] gameState, int col, int row) {

        ArrayList<Position> moves = new ArrayList<>();

        //get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        int i = 0;
        int num = 0;

        //queen can move diagonally to the left
        int[] rowOffset = {-1};
        int[] colOffset = {-1};
        while(num < 8) {
            for (int side : rowOffset) {
                for (int side2 : colOffset) {
                    int tgtRow = side + row + i;
                    int tgtCol = side2 + col + i;

                    if (inBounds(tgtRow, tgtCol, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol, tgtRow);
                        moves.add(p);
                    }
                }
            }
            i = i -1;
            num++;
        }

        //queen can move diagonally to the right
        num = 0;
        int j =0;
        int[] rowOffset2 = {-1};
        int[] colOffset2 = {1};

        while(num < 8) {
            for (int side3 : rowOffset2) {
                for (int side4 : colOffset2) {
                    int tgtRow2 = side3 + row + j;
                    int tgtCol2 = side4 + col - j;

                    if (inBounds(tgtRow2, tgtCol2, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol2, tgtRow2);
                        moves.add(p);
                    }
                }
            }
            j = j -1;
            num++;
        }

        //queen can move down diagonally to the left
        num = 0;
        int k =0;
        int[] rowOffset3 = {1};
        int[] colOffset3 = {1};
        while(num < 8) {
            for (int side5 : rowOffset3) {
                for (int side6 : colOffset3) {
                    int tgtRow3 = side5 + row + k;
                    int tgtCol3 = side6 + col + k;

                    if (inBounds(tgtRow3, tgtCol3, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol3, tgtRow3);
                        moves.add(p);
                    }
                }
            }
            k = k + 1;
            num++;
        }

        //queen can move down diagonally to the right
        num = 0;
        int l =0;
        int[] rowOffset4 = {-1};
        int[] colOffset4 = {1};

        while(num < 8) {
            for (int side7 : rowOffset4) {
                for (int side8 : colOffset4) {
                    int tgtRow4 = side7 + row - l;
                    int tgtCol4 = side8 + col + l;

                    if (inBounds(tgtRow4, tgtCol4, 8)) {
                        //check to see when can attack ( same color)

                        Position p = new Position(tgtCol4, tgtRow4);
                        moves.add(p);
                    }
                }
            }
            l = l -1;
            num++;
        }

////////////////////////////////////////////////////////////

        int[] rowOffset5 = {1};
        int[] colOffset5 = {1};

        //queen can move up
        int r = 0;
        num = 0;
        while(num < 8){
            for(int side : rowOffset5) {
                int tgtRow5 = row + r;

                if (inBounds(tgtRow5, col, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(col, tgtRow5);
                    moves.add(p);
                }

            }
            r = r -1;
            num++;
        }

        //queen can move left
        num = 0;
        int s = 0;
        while(num < 8){
            for(int side2 : colOffset5) {
                int tgtCol5 = col - s;

                if (inBounds(row, tgtCol5, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(tgtCol5, row);
                    moves.add(p);
                }

            }
            s = s + 1;
            num++;
        }

        //queen can move down
        num = 0;
        int m = 0;
        while(num < 8){
            for(int side3 : rowOffset5) {
                int tgtRow6 = row + m;

                if (inBounds(tgtRow6, col, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(col, tgtRow6);
                    moves.add(p);
                }

            }
            m = m + 1;
            num++;
        }

        //queen can move right
        num = 0;
        int n = 0;
        while(num < 8){
            for(int side4 : colOffset5) {
                int tgtCol7 = col + n;

                if (inBounds(row, tgtCol7, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(tgtCol7, row);
                    moves.add(p);
                }

            }
            n = n + 1;
            num++;
        }
        ////////////////////////////////////////////////////////////////////
        return moves;
    }

    private static ArrayList<Position> kingMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        // get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        int[] rowOffset = {-1,1};
        int[] colOffset = {-1,1};

        //king can move diagonally
        for (int side : rowOffset) {
            for (int side2 : colOffset) {
                int tgtRow = side + row;
                int tgtCol = side2 + col;

                if (inBounds(tgtRow, tgtCol, 8)) {
                    //check to see when can attack ( same color)

                    Position p = new Position(tgtCol, tgtRow);
                    moves.add(p);
                }
            }
        }

        //king can move up
        int maxDistance = 1;
        for(int i = 1;  i <= maxDistance; i++){
            int tgtRow2 = row - i;
            if (inBounds(tgtRow2, col, 8)) {
                //check to see when can attack ( same color)

                Position p = new Position(col, tgtRow2);
                moves.add(p);
            }
        }

        //king can move down
        for(int i = 1;  i <= maxDistance; i++){
            int tgtRow3 = row + i;
            if (inBounds(tgtRow3, col, 8)) {
                //check to see when can attack ( same color)

                Position p = new Position(col, tgtRow3);
                moves.add(p);
            }
        }

        //king can move right
        for(int i = 1;  i <= maxDistance; i++){
            int tgtCol2 = col + i;
            if (inBounds(row, tgtCol2, 8)) {
                //check to see when can attack ( same color)

                Position p = new Position(tgtCol2, row);
                moves.add(p);
            }
        }

        //king can move left
        for(int i = 1;  i <= maxDistance; i++){
            int tgtCol3 = col - i;
            if (inBounds(row, tgtCol3, 8)) {
                //check to see when can attack ( same color)

                Position p = new Position(tgtCol3, row);
                moves.add(p);
            }
        }

        return moves;
    }

    private static ArrayList<Position> pawnMoves(int[][] gameState, int col, int row) {
        ArrayList<Position> moves = new ArrayList<>();

        // get the value of the present piece to determine set
        int pieceColor = gameState[row][col];

        // if is white move down from top, else move up from bottom
        int dir = 1;
        int startRow = 1;
        if (pieceColor < 0) {
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
                if (tgtVal * pieceColor < 0) {
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

    private static boolean isSameColor(int color, int[][] state, int row, int col) {
        return color * state[row][col] > 0;
    }

    private static boolean isValid(int color, int[][]state, int row, int col) {
        return inBounds(row, col, 8) && isSameColor(color, state, row, col);
    }
}
