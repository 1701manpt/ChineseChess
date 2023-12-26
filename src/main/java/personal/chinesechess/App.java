package personal.chinesechess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import personal.chinesechess.Models.ChessBoard;
import personal.chinesechess.Models.Point;

public class App extends Application {

    private static final int[] BOARD_SIZE = {11, 10};
    private static final int[] MAP_SIZE = {10, 9};
    private static final double CELL_SIZE = 60.0;
    private final ChessBoard board = new ChessBoard();
    private Point selectedPointStart;
    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(CELL_SIZE * BOARD_SIZE[1], CELL_SIZE * BOARD_SIZE[0]);
        root.setCenter(canvas);

        canvas.setOnMouseClicked(this::handleMouseClick);

        gc = canvas.getGraphicsContext2D();
        board.initDefault();
        drawChessBoard(gc);

        Scene scene = new Scene(root, CELL_SIZE * BOARD_SIZE[1], CELL_SIZE * BOARD_SIZE[0]);
        primaryStage.setTitle("Chinese Chess Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawChessBoard(GraphicsContext gc) {
        Color borderColor = Color.web("#312109");
        Color squareColor = Color.web("#fcb13c");
        Color boardColor = Color.web("#cf5c00");

        gc.setStroke(borderColor);

        fillColor(gc, squareColor, boardColor);
        drawRow(gc);
        drawColumn(gc);
        drawPalace(gc);

        printChessboard(gc);
    }

    // xếp quân cờ vào bàn cờ
    private void printChessboard(GraphicsContext gc) {
        Point[][] map = board.getMap();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 9; col++) {
                if (!map[row][col].isEmpty()) {
                    boolean isSelected = false;
                    if (selectedPointStart != null) {
                        isSelected = row == selectedPointStart.a && col == selectedPointStart.b;
                    }
                    drawPiece(gc, map[row][col], isSelected);
                }
            }
        }
    }

    private void handleMouseClick(MouseEvent event) {
        try {
            int col = (int) (event.getX() / position(1));
            int row = (int) (event.getY() / position(1));

            double a = 0, b = 0;

            // Di chuyển quân cờ đã chọn đến vị trí mới
            if (event.getX() / position(1) - col <= 0.4 && event.getY() / position(1) - row <= 0.4) {
                b = position(col);
                a = position(row);
            }

            if (event.getX() / position(1) - col >= 0.6 && event.getY() / position(1) - row >= 0.6) {
                b = position(col + 1);
                a = position(row + 1);
            }

            if (event.getX() / position(1) - col >= 0.6 && event.getY() / position(1) - row <= 0.4) {
                b = position(col + 1);
                a = position(row);
            }

            if (event.getX() / position(1) - col <= 0.4 && event.getY() / position(1) - row >= 0.6) {
                b = position(col);
                a = position(row + 1);
            }

            Point[][] map = board.getMap();
            Point point = new Point();
            point.b = (int) (b / CELL_SIZE) - 1;
            point.a = (int) (a / CELL_SIZE) - 1;
            if (selectedPointStart == null) {
                selectedPointStart = new Point();
                selectedPointStart.b = point.b;
                selectedPointStart.a = point.a;
            } else if (selectedPointStart != null) {
                try {
                    board.move(selectedPointStart, point);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                } finally {
                    selectedPointStart = null;
                }

                // Vẽ lại bàn cờ
            }
            drawChessBoard(((Canvas) event.getSource()).getGraphicsContext2D());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // vẽ quân cờ
    private void drawPiece(GraphicsContext gc, Point point, boolean isSelected) {
        Color pieceColor = Color.web("#fedba6");
        double n = (double) 4 / 10;
        double i = (double) 4 / 5;
        double x = position(point.b + 1) - position(n);
        double y = position(point.a + 1) - position(n);
        double d = position(i);

        if (isSelected) {
            gc.setFill(Color.GRAY);
        } else {
            gc.setFill(pieceColor);
        }

        gc.fillOval(x, y, d, d);

        if (point.chessPiece.getSide() == 1) {
            gc.setFill(Color.BLACK);
        } else {
            gc.setFill(Color.RED);
        }

        gc.fillText(point.chessPiece.getNameChinese(), x, y + position(n));
    }

    // tô màu cho bàn cờ
    private void fillColor(GraphicsContext gc, Color squareColor, Color boardColor) {
        // Tô màu
        for (int row = 0; row < BOARD_SIZE[0]; row++) {
            for (int col = 0; col < BOARD_SIZE[1]; col++) {
                if (row >= 1 && col >= 1 && row <= 9 && col <= 8) {
                    gc.setFill(squareColor);
                } else {
                    gc.setFill(boardColor);
                }

                gc.fillRect(position(col), position(row), position(1), position(1));
            }
        }
    }

    // Vẽ các đường ngang
    private void drawRow(GraphicsContext gc) {
        for (int row = 1; row <= MAP_SIZE[0]; row++) {
            gc.strokeLine(position(1), position(row), position(MAP_SIZE[1]), position(row));
        }
    }

    // Vẽ các đường dọc
    private void drawColumn(GraphicsContext gc) {
        for (int col = 1; col <= MAP_SIZE[1]; col++) {
            if (col == 1 || col == MAP_SIZE[1]) {
                gc.strokeLine(position(col), position(1), position(col), position(MAP_SIZE[0]));
            } else {
                gc.strokeLine(position(col), position(1), position(col), position(5));
                gc.strokeLine(position(col), position(6), position(col), position(MAP_SIZE[0]));
            }
        }
    }

    // vẽ các đường chéo trong cung
    private void drawPalace(GraphicsContext gc) {
        gc.strokeLine(position(4), position(1), position(6), position(3));
        gc.strokeLine(position(6), position(1), position(4), position(3));
        gc.strokeLine(position(4), position(8), position(6), position(10));
        gc.strokeLine(position(6), position(8), position(4), position(10));
    }

    private double position(double n) {
        return CELL_SIZE * n;
    }
}
