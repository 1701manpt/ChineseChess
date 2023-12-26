package personal.chinesechess.Models;

public class Advisor extends ChessPiece {

    public void log() {
        System.out.print("  2" + super.sideAlias + "  ");
    }

    @Override
    public void move(Point[][] map, Point start, Point end) throws Exception {
        super.move(map, start, end);
        moveOrCapture(map, start, end);
    }

    @Override
    public void capture(Point[][] map, Point start, Point end) throws Exception {
        super.capture(map, start, end);
        moveOrCapture(map, start, end);
    }

    private void moveOrCapture(Point[][] map, Point start, Point end) throws Exception {
        boolean if1 = super.side == 1 && end.a <= 2 && end.b >= 3 && end.b <= 5;
        boolean if2 = super.side == 0 && end.a >= 7 && end.b >= 3 && end.b <= 5;

        boolean upLeft = start.a - end.a == 1 && start.b - end.b == 1;
        boolean upRight = start.a - end.a == 1 && end.b - start.b == 1;
        boolean downLeft = end.a - start.a == 1 && start.b - end.b == 1;
        boolean downRight = end.a - start.a == 1 && end.b - start.b == 1;

        if (!(upLeft || upRight || downLeft || downRight)) {
            throw new Exception("Chỉ đi chéo một ô mỗi nước!");
        }

        if (!if1 && !if2) {
            throw new Exception("Luôn luôn phải ở trong cung!");
        }
    }
}
