package application;

public class LocalOpponent implements ChessMoveMakeable {
    @Override
    public int[][] getMove(int[][] state) {
        return state;
    }
}
