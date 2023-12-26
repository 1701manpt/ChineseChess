package personal.chinesechess.Models;

public class ChessBoard {
    private final int a = 9;
    private final int b = 8;
    // bàn cờ tướng
    private final Point[][] map = new Point[a + 1][b + 1];
    private Point generalOneSidePoint = new Point();
    private Point generalZeroSidePoint = new Point();

    public ChessBoard() {

    }

    public void initDefault() {
        int a = 0;
        int b = 0;
        while (a <= 9) {
            Point pointStart = new Point();

            // quân chốt
            if ((a == 3 || a == 6) && (b == 0 || b == 2 || b == 4 || b == 6 || b == 8)) {
                pointStart.chessPiece = new Soldier();
            }

            // quân pháo
            if ((a == 2 || a == 7) && (b == 1 || b == 7)) {
                pointStart.chessPiece = new Cannon();
            }

            if (a == 0 || a == 9) {
                // quân xe
                if (b == 0 || b == 8) {
                    pointStart.chessPiece = new Chariot();
                }

                // quân mã
                if (b == 1 || b == 7) {
                    pointStart.chessPiece = new Horse();
                }

                // quân tượng
                if (b == 2 || b == 6) {
                    pointStart.chessPiece = new Elephant();
                }

                // quân sĩ
                if (b == 3 || b == 5) {
                    pointStart.chessPiece = new Advisor();
                }

                // quân tướng
                if (b == 4) {
                    pointStart.chessPiece = new General();
                }
            }

            if (!pointStart.isEmpty()) {
                // quân đen
                if (a <= 4) {
                    pointStart.chessPiece.setSide(1);
                }
                // quân đỏ
                else {
                    pointStart.chessPiece.setSide(0);
                }
            }

            pointStart.a = a;
            pointStart.b = b;
            map[a][b] = pointStart;

            b++;

            if (b > 8) {
                b = 0;
                a++;
            }
        }

        generalOneSidePoint.chessPiece = new General();
        generalZeroSidePoint.chessPiece = new General();
        generalOneSidePoint.chessPiece.setSide(1);
        generalZeroSidePoint.chessPiece.setSide(0);
        generalOneSidePoint.a = 0;
        generalOneSidePoint.b = 4;
        generalZeroSidePoint.a = 9;
        generalZeroSidePoint.b = 4;
    }

    public void print() {
        System.out.println();
        for (int i = 0; i < 66; i++) {
            System.out.print("=");
        }
        System.out.println();
        System.out.print("            ");
        for (int i = 0; i <= b; i++) {
            System.out.print("  " + i + "   ");
        }
        System.out.println();
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.println();
            System.out.println();
            System.out.print(i + "           ");
            for (int j = 0; j < map[i].length; j++) {
                Point currentPoint = map[i][j];

                currentPoint.log();
            }
        }

        System.out.println();
    }

    public void move(Point start, Point end) throws Exception {
        try {
            if (!isMoveOnMap(start, end)) {
                throw new Exception("Đi ra ngoài bàn cờ rồi kìa!");
            }

            Point pointStart = map[start.a][start.b];
            Point pointEnd = map[end.a][end.b];

            if (pointStart.isEmpty()) {
                throw new Exception("Chỗ này không có quân cờ!");
            }

            // nếu quân cờ không thể di chuyển hoặc không thể chiếm đóng vị trí của quân cờ khác thì ném ra lỗi
            // dừng ngay tại câu điều kiện này
            if (pointEnd.isEmpty()) {
                pointStart.chessPiece.move(map, pointStart, pointEnd);
            } else {
                pointStart.chessPiece.capture(map, pointStart, pointEnd);
            }

            // cho cờ di chuyển đến vị trí mới
            pointEnd.chessPiece = pointStart.chessPiece;
            // đặt rỗng cho vị trí cũ
            pointStart.chessPiece = null;

            if (pointEnd.chessPiece instanceof General) {
                updateGeneralPoint(pointEnd);
            }

            boolean isCheck = false;

            if (pointEnd.chessPiece.side == 1) {
                isCheck = pointEnd.chessPiece.isCheck(map, pointEnd, generalZeroSidePoint);
            }

            if (pointEnd.chessPiece.side == 0) {
                isCheck = pointEnd.chessPiece.isCheck(map, pointEnd, generalOneSidePoint);
            }

            if (isCheck) {
                System.out.println("Chiếu tướng!!!");
            }
        } catch (Exception e) {
            throw new Exception("Nước đi không hợp lệ: " + e.getMessage());
        }
    }

    public boolean isMoveOnMap(Point start, Point end) {
        boolean if1 = start.a >= 0 && start.a <= a && start.b >= 0 && start.b <= b;
        boolean if2 = end.a >= 0 && end.a <= a && end.b >= 0 && end.b <= b;

        return if1 && if2;
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public Point[][] getMap() {
        return map;
    }

    private void updateGeneralPoint(Point pointStart) {
        if (pointStart.chessPiece.side == 1) {
            generalOneSidePoint = pointStart;
        }

        if (pointStart.chessPiece.side == 0) {
            generalZeroSidePoint = pointStart;
        }
    }
}
