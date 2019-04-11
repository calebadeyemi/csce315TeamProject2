package application;

import java.util.*;

class ChessAi implements ChessMoveMakeable {
    int color;

    ChessAi(int color) {
        this.color = color;
    }

    @Override
    public Move getMove(int[][] state) {
        int depth = 3;
        ArrayList<Tree.Node<Move>> moves = buildMoveTree(ChessState.getCopy(state), depth, color);
        ArrayList<Integer> treeValues = new ArrayList<>();

        // run minimax on each tree and store the associated depth
        for (Tree.Node<Move> move : moves) {
            treeValues.add(MiniMax(depth, color > 0, move, 0, 0));
        }

        // Get the index of thte max minimax return and take the root move.
        int index = 0;
        int max = 0;
        for (int i = 0; i < treeValues.size(); i++) {
            if (treeValues.get(i) > max) {
                max = treeValues.get(i);
                index = i;
            }
        }
        return moves.get(index).data;
    }

    private int sumMatrix(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int col : row) {
                sum += col;
            }
        }
        return sum;
    }

    private ArrayList<Tree.Node<Move>> buildMoveTree(int[][]state, int depth, int color) {
        int[][] stateCopy = ChessState.getCopy(state);
        ArrayList<Move> moves = hypothesizeMoves(stateCopy, color);

        ArrayList<Tree.Node<Move>> forest = new ArrayList<>();
        for (Move move : moves)
            forest.add(new Tree.Node<>(move, stateCopy));

        if (depth == 0) {
            return forest;
        }

        for (Tree.Node<Move> node : forest) {
            // if depth is even, calc AI moves
            if (depth % 2 == 0) {
                node.children = buildMoveTree(ChessMovement.applyMove(stateCopy, node.data), depth - 1, color);
            } else { // do opponent moves
                node.children = buildMoveTree(ChessMovement.applyMove(stateCopy, node.data), depth - 1, color * -1);
            }
        }

        return forest;
    }

    private ArrayList<Move> hypothesizeMoves(int[][] state, int color) {
        ArrayList<Move> moves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (color * state[row][col] > 0) {
                    moves.addAll(ChessMovement.getPotentialMovements(ChessState.getCopy(state), col, row));
                    String c = color > 1 ? "white" : "black";
                    System.out.println(c + ": " + state[row][col] + " > " + moves.size() + " moves found");
                }
            }
        }
        return moves;
    }


    private int MiniMax(int depth, Boolean maximizingPlayer, Tree.Node<Move> node, int alpha, int beta) {
        if (depth == 0) {
            int value = sumMatrix(ChessMovement.applyMove(ChessState.getCopy(node.state), node.data));
            return value;
        }

        if (maximizingPlayer) {
            int value = -10000;

            for (Tree.Node<Move> move : node.children) {
                value = Math.max(value, MiniMax(depth - 1, false, move, alpha, beta));
                alpha = Math.max(alpha, value);

                // Alpha Beta Pruning
                if (alpha >= beta) break;
            }

            return value;
        } else {
            int value = 10000;

            for (Tree.Node<Move> move : node.children) {
                value = Math.min(value, MiniMax(depth - 1, true, node, alpha, beta));
                beta = Math.min(beta, value);

                // Alpha Beta Pruning
                if (alpha >= beta) break;
            }
            return value;
        }
    }
}

class Tree<T> {
    public Node<T> root;

    public Tree(T rootData, int[][] state) {
        root = new Node<T>(rootData, state);
    }

    public static class Node<T> {
        public T data;
        public int[][] state;
        public ArrayList<Node<T>> children;

        public Node(T data, int[][] state) {
            this.data = data;
            this.state = state;
            this.children = new ArrayList<>();
        }
    }
}
