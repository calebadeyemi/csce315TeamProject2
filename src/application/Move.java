package application;

public class Move {
    int rowTo;
    int colTo;
    int rowFrom;
    int colFrom;

    Move(int rowTo, int colTo, int rowFrom, int colFrom) {
        this.rowTo = rowTo;
        this.colTo = colTo;
        this.rowFrom = rowFrom;
        this.colFrom = colFrom;
    }
}
