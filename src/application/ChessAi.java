package application;

import java.util.*;

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
        ArrayList<Tree.Node<Move>> moves = buildMoveTree(state, 3, color);
        return moves.get(0).data;
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

    private ArrayList<Tree.Node<Move>> buildMoveTree(int[][]state, int depth, int color) {
        ArrayList<Move> moves = hypothesizeMoves(state, color);
        // a list of initial moves
        ArrayList<Tree.Node<Move>> forest = new ArrayList<>();

        for (Move move : moves) {
            Tree.Node<Move> node = new Tree.Node<>(move);
            forest.add(node);
        }

        if (depth == 0) {
            return forest;
        }

        for (Tree.Node<Move> node : forest) {
            // if depth is even, calc AI moves
            if (depth % 2 == 0) {
                node.children = buildMoveTree(ChessMovement.applyMove(state, node.data), depth - 1, color);
            } else { // do opponent moves
                node.children = buildMoveTree(ChessMovement.applyMove(state, node.data), depth - 1, color*-1);
            }
        }

        return forest;
    }

    private ArrayList<Move> hypothesizeMoves(int[][] state, int color) {
        ArrayList<Move> moves = new ArrayList<>(16);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (color * state[row][col] > 0) {
                    moves.addAll(ChessMovement.getPotentialMovements(state, col, row));
                }
            }
        }
        return moves;
    }


    private  int MiniMax(int depth, Boolean maximizingPlayer, int values[][], int alpha, int beta) {
        int MAX = 10000;
        int MIN = -10000;

        if (depth == 0 || depth == 3) {
            // tree evaluated, return best.
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

class Tree<T> {
    public Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>(rootData);
    }

    public static class Node<T> {
        public T data;
        public Node<T> parent;
        public ArrayList<Node<T>> children;

        public Node(T data) {
            this.data = data;
            this.parent = null;
            this.children = new ArrayList<>();
        }
    }
}
