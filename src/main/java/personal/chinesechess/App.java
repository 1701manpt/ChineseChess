package personal.chinesechess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import personal.chinesechess.Models.ChessBoard;
import personal.chinesechess.Models.Point;

import java.io.InputStream;

public class App extends Application {
    private static final int[] BOARD_SIZE = {11, 10};
    private static final int[] MAP_SIZE = {10, 9};
    private static final double CELL_SIZE = 60.0;
    private final ChessBoard board = new ChessBoard();
    private Point selectedPointStart;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Canvas canvas = new Canvas(position(BOARD_SIZE[1]), position(BOARD_SIZE[0]));
        root.setCenter(canvas);

        canvas.setOnMouseClicked(this::handleMouseClick);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        board.initDefault();
        drawChessBoard(gc);

        Scene scene = new Scene(root, position(BOARD_SIZE[1]), position(BOARD_SIZE[0]));
        primaryStage.setTitle("Chinese Chess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawChessBoard(GraphicsContext gc) {
        Color borderColor = Color.web("#312109");
        Color cellColor = Color.web("#fcb13c");
        Color boardColor = Color.web("#cf5c00");

        gc.setStroke(borderColor);

        fillColor(gc, cellColor, boardColor);
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
            Point[][] map = board.getMap();
            Point point = findPositionClick(event.getX(), event.getY());
            if (selectedPointStart == null) {
                selectedPointStart = point;
                drawChessBoard(((Canvas) event.getSource()).getGraphicsContext2D());
            } else if (selectedPointStart != null) {
                try {
                    board.move(selectedPointStart, point);
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                } finally {
                    selectedPointStart = null;
                    drawChessBoard(((Canvas) event.getSource()).getGraphicsContext2D());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private Point findPositionClick(double xMouse, double yMouse) {
        int col = (int) (xMouse / position(1));
        int row = (int) (yMouse / position(1));

        double a = 0, b = 0;

        // góc trên trái
        if (xMouse / position(1) - col <= 0.4 && yMouse / position(1) - row <= 0.4) {
            b = position(col);
            a = position(row);
        }

        // góc dưới phải
        if (xMouse / position(1) - col >= 0.6 && yMouse / position(1) - row >= 0.6) {
            b = position(col + 1);
            a = position(row + 1);
        }

        // góc trái dưới
        if (xMouse / position(1) - col >= 0.6 && yMouse / position(1) - row <= 0.4) {
            b = position(col + 1);
            a = position(row);
        }

        // góc phải trên
        if (xMouse / position(1) - col <= 0.4 && yMouse / position(1) - row >= 0.6) {
            b = position(col);
            a = position(row + 1);
        }

        Point p = new Point();
        p.a = (int) (a / CELL_SIZE) - 1;
        p.b = (int) (b / CELL_SIZE) - 1;

        return p;
    }

    // vẽ quân cờ
    private void drawPiece(GraphicsContext gc, Point point, boolean isSelected) {
        Color pieceColor = Color.web("#fedba6");

        double i = 1;
        double n = i * 1 / 2;
        double x = position(point.b + 1) - position(n);
        double y = position(point.a + 1) - position(n);
        double d = position(i);

        if (isSelected) {
            double i1 = 0.9;
            double n1 = i1 * 1 / 2;
            double x1 = position(point.b + 1) - position(n1);
            double y1 = position(point.a + 1) - position(n1);
            double d1 = position(i1);
            gc.setFill(pieceColor);
            gc.fillOval(x1, y1, d1, d1);
        }

        String side = "";
        if (point.chessPiece.getSide() == 1) {
            side = "black";
        } else {
            side = "red";
        }

        String path = "/personal/chinesechess/Images/Pieces/" + side + "/" + side + "_" + point.chessPiece.getNameChinese().toLowerCase() + ".png";
        InputStream stream = getClass().getResourceAsStream(path);
        Image chessPieceImage = new Image(stream);

        gc.drawImage(chessPieceImage, x, y, d, d);
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
