package application;

public class RemoteOpponent implements ChessMoveMakeable {
    @Override
    public int[][] getMove(int[][] state) {
        return state;
    }
}
