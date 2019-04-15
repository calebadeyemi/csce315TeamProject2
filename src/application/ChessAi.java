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

        // run MiniMax on each tree and store the associated depth
        for (Tree.Node<Move> move : moves) {
            treeValues.add(MiniMax(depth, color > 0, move, 0, 0));
        }

        // Get the index of the best minimax return and take the associated root move.
        int index = 0;
        int max = 0;
        for (int i = 0; i < treeValues.size(); i++) {
            if (treeValues.get(i) > max) {
                max = treeValues.get(i);
                index = i;
            }
        }

        Tree.Node<Move> node = moves.get(index);
        ChessState.printState(ChessMovement.applyMove(node.state, node.data));
        for (Tree.Node<Move> child : node.children){
            int[][] state1 = ChessMovement.applyMove(child.state, child.data);
            ChessState.printState(state1);
            System.out.println(ChessState.sumState(state1));
            for (Tree.Node<Move> grandchild : child.children) {
                ChessState.printState(ChessMovement.applyMove(grandchild.state, grandchild.data));
                System.out.println(ChessState.sumState(ChessMovement.applyMove(grandchild.state, grandchild.data)));
                for (Tree.Node<Move> greatgrandchild : grandchild.children) {
                    ChessState.printState(ChessMovement.applyMove(greatgrandchild.state, greatgrandchild.data));
                    System.out.println(ChessState.sumState(ChessMovement.applyMove(greatgrandchild.state,
                            greatgrandchild.data)));
                }
            }
        }

        return moves.get(index).data;
    }

    private ArrayList<Tree.Node<Move>> buildMoveTree(int[][]state, int depth, int color) {
        // generate a list of all initial moves and the initial state they moved from
        ArrayList<Move> moves = hypothesizeMoves(ChessState.getCopy(state), color);

        // for each initial move, generate a tree of moves.
        ArrayList<Tree.Node<Move>> forest = new ArrayList<>();
        for (Move move : moves)
            forest.add(new Tree.Node<>(move, ChessState.getCopy(state)));

        // if we have reached the bottom of the tree, stop
        if (depth == 0) {
            return forest;
        }

        // else, created child moves based on the current state with the associated move applied to other player
        for (Tree.Node<Move> node : forest) {
                node.children = buildMoveTree(ChessMovement.applyMove(ChessState.getCopy(state), node.data), depth - 1,color * -1);
        }

        return forest;
    }

    private ArrayList<Move> hypothesizeMoves(int[][] state, int color) {
        ArrayList<Move> moves = new ArrayList<>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (color * state[row][col] > 0) {
                    moves.addAll(ChessMovement.getPotentialMovements(ChessState.getCopy(state), col, row));
                }
            }
        }
        return moves;
    }


    private int MiniMax(int depth, Boolean maximizingPlayer, Tree.Node<Move> node, int alpha, int beta) {
        int value = 0;
        if (depth == 0) {
            int[][] newState = ChessMovement.applyMove(ChessState.getCopy(node.state), node.data);
            int augment = ChessState.getOptimalPieceStateAugment(node.state[node.data.rowFrom][node.data.colFrom], node.data);
            int[][] optimalMatrix = ChessState.applyOptimizer(newState, augment, node.data);
            value = ChessState.sumState(optimalMatrix);
        } else if (maximizingPlayer) {
            value = -10000;

            for (Tree.Node<Move> move : node.children) {
                value = Math.max(value, MiniMax(depth - 1, false, move, alpha, beta));
                alpha = Math.max(alpha, value);

                // Alpha Beta Pruning
                if (alpha >= beta) break;
            }
        } else {
            value = 10000;

            for (Tree.Node<Move> move : node.children) {
                value = Math.min(value, MiniMax(depth - 1, true, move, alpha, beta));
                beta = Math.min(beta, value);

                // Alpha Beta Pruning
                if (alpha >= beta) break;
            }
        }

        return value;
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
