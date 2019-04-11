package application;

import java.util.ArrayList;

class ChessAi implements ChessMoveMakeable {
    int color;

    ChessAi(int color) {
        this.color = color;
    }

    @Override
    public Move getMove(int[][] state) {
        return makeMove(state);
    }

    private Move makeMove(int[][] state) {
        ArrayList<Move> moves = hypothesizeMoves(state, 3);
        return moves.get(0);
    }

    private  int sumMatrix(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int col : row) {
                sum += col;
            }
        }
        return sum;
    }

    private  ArrayList<Move> hypothesizeMoves(int[][] state, int depth) {
        ArrayList<Move> moves = new ArrayList<>(60000);

        for (; depth >= 0; depth--) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (color * state[row][col] > 0) {
                        moves.addAll(ChessMovement.getPotentialMovements(state, col, row));
                    }
                }
            }
        }
        return moves;
    }


    private  int MiniMax(int depth, Boolean maximizingPlayer, int values[][], int alpha, int beta) {
        int MAX = 10000;
        int MIN = -10000;
        // Terminating condition. i.e
        // leaf node is reached
        if (depth == 3) {
            // return values[][];
        }

        if (maximizingPlayer) {
            int best = MIN;

            // Recur for left and
            // right children
            for (int i = 0; i < 2; i++) {
                int val = MiniMax(depth + 1, false, values, alpha, beta);
                best = Math.max(best, val);
                alpha = Math.max(alpha, best);

                // Alpha Beta Pruning
                if (beta <= alpha) break;
            }
            return best;
        } else {
            int best = MAX;

            // Recur for left and
            // right children
            for (int i = 0; i < 2; i++) {

                int val = MiniMax(depth + 1, true, values, alpha, beta);
                best = Math.min(best, val);
                beta = Math.min(beta, best);

                // Alpha Beta Pruning
                if (beta <= alpha) break;
            }
            return best;
        }
    }
}
