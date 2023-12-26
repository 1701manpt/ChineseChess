package personal.chinesechess.Models;

public class Chariot extends ChessPiece {
    public void log() {
        System.out.print("  5" + super.sideAlias + "  ");
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
        int from = 0, to = 0, fixed = 0;

        boolean up = start.a > end.a && start.b == end.b;
        boolean down = start.a < end.a && start.b == end.b;
        boolean left = start.a == end.a && start.b > end.b;
        boolean right = start.a == end.a && start.b < end.b;

        if (!(up || down || left || right)) {
            throw new Exception("Chỉ đi ngang hoặc dọc trên bàn cờ!");
        }

        // đi lên
        if (up) {
            from = end.a;
            to = start.a;
            fixed = start.b;
        }

        // đi xuống
        if (down) {
            from = start.a;
            to = end.a;
            fixed = start.b;
        }

        // qua trái
        if (left) {
            from = end.b;
            to = start.b;
            fixed = start.a;
        }

        // qua phải
        if (right) {
            from = start.b;
            to = end.b;
            fixed = start.a;
        }

        for (int i = from + 1; i < to; i++) {
            if (left || right) {
                if (!map[fixed][i].isEmpty()) {
                    throw new Exception("Không được phép nhảy qua đầu các quân khác!");
                }
            }

            if (up || down) {
                if (!map[i][fixed].isEmpty()) {
                    throw new Exception("Không được phép nhảy qua đầu các quân khác!");
                }
            }
        }
    }
}
