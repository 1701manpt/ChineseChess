package personal.chinesechess.Models;

public class Horse extends ChessPiece {
    public void log() {
        System.out.print("  4" + super.sideAlias + "  ");
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

        boolean upLeft = start.a - end.a == 2 && start.b - end.b == 1;
        boolean upRight = start.a - end.a == 2 && end.b - start.b == 1;
        boolean downLeft = end.a - start.a == 2 && start.b - end.b == 1;
        boolean downRight = end.a - start.a == 2 && end.b - start.b == 1;
        boolean leftUp = end.a - start.a == 1 && start.b - end.b == 2;
        boolean leftDown = start.a - end.a == 1 && start.b - end.b == 2;
        boolean rightUp = end.a - start.a == 1 && end.b - start.b == 2;
        boolean rightDown = start.a - end.a == 1 && end.b - start.b == 2;

        if (!(upLeft || upRight || downLeft || downRight || leftUp || leftDown || rightUp || rightDown)) {
            throw new Exception("Chỉ đi ngang (hay dọc) một ô và chéo (theo cùng hướng đi trước đó) một ô!");
        }

        if (upLeft || upRight) {
            a = start.a - 1;
            b = start.b;
        }

        if (downLeft || downRight) {
            a = start.a + 1;
            b = start.b;
        }

        if (leftUp || leftDown) {
            a = start.a;
            b = start.b - 1;
        }

        if (rightUp || rightDown) {
            a = start.a;
            b = start.b + 1;
        }

        if (!map[a][b].isEmpty()) {
            throw new Exception("Có quân cờ nào đó nằm ngay trên đường đi, không được đi đường đó!");
        }
    }
}
