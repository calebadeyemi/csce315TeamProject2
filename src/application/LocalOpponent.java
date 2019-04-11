package application;

public class LocalOpponent implements ChessMoveMakeable {
    @Override
    public Move getMove(int[][] state) {
        return new Move(1,1,3,1);
    }
}
