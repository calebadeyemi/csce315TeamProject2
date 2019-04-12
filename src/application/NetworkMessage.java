package application;

public class NetworkMessage {

    static String WelcomeMessage = "Welcome";
    static String GameReady = "READY";
    static String Acknowledgment = "OK";

    static String MoveToMoveMessage(Move move) {
        return numToColLetter(move.colFrom) + move.rowFrom + " " + numToColLetter(move.colTo) + move.rowTo;

    }

    static Move MoveMessageToMove(String moveMessage) {
        int rowFrom = Character.getNumericValue(moveMessage.charAt(1));
        int colFrom = colLetterToNum(moveMessage.charAt(0));
        int rowTo = Character.getNumericValue(moveMessage.charAt(3));
        int colTo = colLetterToNum(moveMessage.charAt(2));
        return new Move(rowTo, colTo, rowFrom, colFrom);
    }

    private static int colLetterToNum(char letter) {
        switch (letter) {
            case 'a':
            case 'A': return 0;
            case 'b':
            case 'B': return 1;
            case 'c':
            case 'C': return 2;
            case 'd':
            case 'D': return 3;
            case 'e':
            case 'E': return 4;
            case 'f':
            case 'F': return 5;
            case 'g':
            case 'G': return 6;
            case 'h':
            case 'H': return 7;
            default: return 8;
        }
    }

    private static String numToColLetter(int num) {
        switch (num) {
            case 0: return "A";
            case 1: return "B";
            case 2: return "C";
            case 3: return "D";
            case 4: return "E";
            case 5: return "F";
            case 6: return "G";
            case 7: return "H";
            default: return "R";
        }
    }

}
