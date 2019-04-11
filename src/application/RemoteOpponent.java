package application;

public class RemoteOpponent implements ChessMoveMakeable {
    @Override
    public Move getMove(int[][] state) {
        return new Move(0,0,0,0);
    }
}
