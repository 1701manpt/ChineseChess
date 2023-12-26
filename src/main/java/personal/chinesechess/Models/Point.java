package personal.chinesechess.Models;

public class Point {
    public int a;
    public int b;
    public ChessPiece chessPiece;

    public Point() {
    }

    public void log() {
        if (!isEmpty()) {
            chessPiece.log();
        } else {
            System.out.print("      ");
        }
    }

    public boolean isEmpty() {
        return chessPiece == null;
    }
}
