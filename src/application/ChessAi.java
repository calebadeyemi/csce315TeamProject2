package application;

import static application.PieceValue.Pawn;

class ChessAi implements ChessMoveMakeable {

    @Override
    public int[][] getMove(int[][] state) {
        return makeMove(state);
    }

    private static int[][] makeMove(int[][] state) {
        int[][] moves = new int[8][8];
        int row, col = 0;
        for (row = 0; row < 8; row++) {
            for (col = 0; col < 8; col++) {
                if (state[row][col] == Pawn) {
                    moves = ChessMovement.getPotentialMovements(state, col, row);
                    // if there are moves to do
                    if(sumMatrix(moves) > 0) {
                        int mvRow, mvCol;
                        for (mvRow = 0; mvRow < 8; mvRow++) {
                            for (mvCol = 0; mvCol < 8; mvCol++) {
                                if (moves[mvRow][mvCol] > 0) {
                                    return ChessMovement.applyMove(state, row, col, mvRow, mvCol);
                                }
                            }
                        }
                    }
                }
            }
        }
        return state;
    }

    private static int sumMatrix(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int col : row) {
                sum += col;
            }
        }
        return sum;
    }


}
