package personal.chinesechess.Models;

public class Soldier extends ChessPiece {
    public void log() {
        System.out.print("  7" + super.sideAlias + "  ");
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
        boolean isCrossedRiver = false;
        boolean up = false;
        boolean leftRight = false;

        if (super.side == 1) {
            isCrossedRiver = start.a > 4;
            up = end.a - start.a > 0 && start.b == end.b;
            leftRight = start.a == end.a && Math.abs(start.b - end.b) > 0;
        }

        if (super.side == 0) {
            isCrossedRiver = start.a <= 4;
            up = start.a - end.a > 0 && start.b == end.b;
            leftRight = start.a == end.a && Math.abs(start.b - end.b) > 0;
        }

        boolean if1 = !isCrossedRiver && !up;
        boolean if2 = isCrossedRiver && !(up || leftRight);
        boolean if3 = up && Math.abs(start.a - end.a) != 1;
        boolean if4 = leftRight && Math.abs(start.b - end.b) != 1;

        if (if1) {
            throw new Exception("Chưa qua sông, chỉ được đi thẳng!");
        }

        if (if2) {
            throw new Exception("Đã qua sông thì được đi ngang hoặc thẳng, không được đi lùi!");
        }

        if (if3 || if4) {
            throw new Exception("Chỉ đi 1 bước!");
        }
    }
}
