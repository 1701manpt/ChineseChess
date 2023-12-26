package personal.chinesechess.Models;

public class Elephant extends ChessPiece {
    public void log() {
        System.out.print("  3" + super.sideAlias + "  ");
    }

    @Override
    protected void move(Point[][] map, Point start, Point end) throws Exception {
        super.move(map, start, end);
        moveOrCapture(map, start, end);
    }

    @Override
    public void capture(Point[][] map, Point start, Point end) throws Exception {
        super.capture(map, start, end);
        moveOrCapture(map, start, end);
    }

    private void moveOrCapture(Point[][] map, Point start, Point end) throws Exception {
        int a = 0, b = 0;

        boolean upLeft = start.a - end.a == 2 && start.b - end.b == 2;
        boolean upRight = start.a - end.a == 2 && end.b - start.b == 2;
        boolean downLeft = end.a - start.a == 2 && start.b - end.b == 2;
        boolean downRight = end.a - start.a == 2 && end.b - start.b == 2;

        if (!(upLeft || upRight || downLeft || downRight)) {
            throw new Exception("Chỉ đi chéo đúng hai ô mỗi nước!");
        }

        if (upLeft) {
            a = start.a - 1;
            b = start.b - 1;
        }

        if (upRight) {
            a = start.a - 1;
            b = start.b + 1;
        }

        if (downLeft) {
            a = start.a + 1;
            b = start.b - 1;
        }

        if (downRight) {
            a = start.a + 1;
            b = start.b + 1;
        }

        if (!map[a][b].isEmpty()) {
            throw new Exception("Không thể đi được vì có một quân cờ nằm giữa nước đi!");
        }

        boolean if1 = super.side == 1 && end.a > 4;
        boolean if2 = super.side == 0 && end.a <= 4;

        if (if1 || if2) {
            throw new Exception("Không được phép đi qua sông!");
        }
    }
}
