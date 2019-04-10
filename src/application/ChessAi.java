package application;

import static application.PieceValue.Pawn;
import java.io.*;

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


    static int minimax(int depth, Boolean maximizingPlayer, int values[][], int alpha,int beta){

        int MAX = 1000;
        int MIN = -1000;
        // Terminating condition. i.e
        // leaf node is reached
        if (depth == 3) {
           // return values[][];
        }

        if (maximizingPlayer)
        {
            int best = MIN;

            // Recur for left and
            // right children
            for (int i = 0; i < 2; i++)
            {
                int val = minimax(depth + 1, false, values, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        }
        else
        {
            int best = MAX;

            // Recur for left and
            // right children
            for (int i = 0; i < 2; i++)
            {

                int val = minimax(depth + 1, true, values, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta <= alpha)
                    break;
            }
            return best;
        }
    }



}
