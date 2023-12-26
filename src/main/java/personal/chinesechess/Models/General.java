package personal.chinesechess.Models;

public class General extends ChessPiece {
    public void log() {
        System.out.print("  1" + super.sideAlias + "  ");
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
        boolean if1 = super.side == 1 && end.a <= 2 && end.b >= 3 && end.b <= 5;
        boolean if2 = super.side == 0 && end.a >= 7 && end.b >= 3 && end.b <= 5;

        boolean downUp = Math.abs(start.a - end.a) == 1 && start.b == end.b;
        boolean leftRight = start.a == end.a && Math.abs(start.b - end.b) == 1;

        if (!(downUp || leftRight)) {
            throw new Exception("Chỉ đi từng ô một, đi ngang hoặc dọc!");
        }

        if (!if1 && !if2) {
            throw new Exception("Luôn luôn ở trong phạm vi cung, không được ra ngoài!");
        }
    }
}
