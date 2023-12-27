package personal.chinesechess.Models;

public class ChessPiece {
    // quân đỏ = 0, quân đen = 1
    protected String sideAlias;
    protected int side;

    public ChessPiece() {
    }

    public int getSide() {
        return side;
    }

    protected void setSide(int side) {
        this.side = side;
        sideAlias = side == 1 ? "+" : "-";
    }

    protected void move(Point[][] map, Point start, Point end) throws Exception {
        boolean if3 = map[end.a][end.b].chessPiece instanceof General;
    }

    protected void capture(Point[][] map, Point start, Point end) throws Exception {
        if (start.isEmpty()) {
            throw new Exception("Chỗ này không có quân cờ!");
        }

        if (end.isEmpty()) {
            throw new Exception("Chỗ này không có quân cờ để chiếm đóng!");
        }

        if (start.a == end.a && start.b == end.b) {
            throw new Exception("Quân cờ đã đứng tại vị trí này!");
        }

        boolean if1 = end.isEmpty();
        boolean if2 = end.chessPiece.side == start.chessPiece.side;

        if (!if1 && if2) {
            throw new Exception("Không thể di chuyển đến vị trí quân đồng minh đang đứng!");
        }
    }

    protected boolean isCheck(Point[][] map, Point start, Point generalPoint) {
        boolean isCheck = false;

        Point[] palace = getPalace(generalPoint);

        for (int i = 0; i < palace.length; i++) {
            try {
                if (palace[i].a == generalPoint.a && palace[i].b == generalPoint.b) {
                    capture(map, start, generalPoint);

                    isCheck = true;
                    return isCheck;
                }
            } catch (Exception e) {

            }
        }

        return isCheck;
    }

    private Point[] getPalace(Point generalPoint) {
        Point[] palace = new Point[9];

        if (generalPoint.chessPiece.side == 1) {
            int i = 0;
            int a = 0;
            int b = 3;
            while (a <= 2) {
                Point point = new Point();
                point.a = a;
                point.b = b;
                palace[i] = point;

                b++;
                i++;

                if (b > 5) {
                    b = 3;
                    a++;
                }
            }
        }

        if (generalPoint.chessPiece.side == 0) {
            int i = 0;
            int a = 7;
            int b = 3;
            while (a <= 9) {
                Point point = new Point();
                point.a = a;
                point.b = b;
                palace[i] = point;

                b++;
                i++;

                if (b > 5) {
                    b = 3;
                    a++;
                }
            }
        }

        return palace;
    }

    protected void log() {

    }

    public String getNameChinese() {
        if (this instanceof General) {
            return "General";
        }

        if (this instanceof Advisor) {
            return "Advisor";
        }

        if (this instanceof Elephant) {
            return "Elephant";
        }

        if (this instanceof Horse) {
            return "Horse";
        }

        if (this instanceof Chariot) {
            return "Chariot";
        }

        if (this instanceof Cannon) {
            return "Cannon";
        }

        if (this instanceof Soldier) {
            return "Soldier";
        }

        return "";
    }
}
