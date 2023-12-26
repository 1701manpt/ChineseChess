package personal.chinesechess.Models;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            ChessBoard board = new ChessBoard();
            board.initDefault();
            board.print();
            System.out.println();

            while (true) {
                try {
                    // Tạo một đối tượng BufferedReader để đọc dữ liệu từ bàn phím
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    // Hiển thị thông báo yêu cầu người dùng nhập dữ liệu
                    System.out.println("Chọn quân cờ để đi (x:y) => ");

                    // Đọc một dòng dữ liệu từ bàn phím và lưu vào biến
                    String userInput = reader.readLine();

                    System.out.println("Vị trí đến (x:y) => ");

                    // Đọc một dòng dữ liệu từ bàn phím và lưu vào biến
                    String userInput1 = reader.readLine();

                    boolean if1 = userInput.isEmpty() || !userInput.contains(":");
                    boolean if2 = userInput1.isEmpty() || !userInput1.contains(":");
                    boolean if3 = userInput.split(":").length > 2 || userInput1.split(":").length > 2;

                    Point start, end;

                    if (if1 || if2) {
                        throw new Exception("Sai cú pháp");
                    }

                    if (if3) {
                        throw new Exception("Sai cú pháp");
                    }

                    try {
                        start = new Point();
                        end = new Point();

                        start.a = Integer.parseInt(userInput.split(":")[0]);
                        start.b = Integer.parseInt(userInput.split(":")[1]);
                        end.a = Integer.parseInt(userInput1.split(":")[0]);
                        end.b = Integer.parseInt(userInput1.split(":")[1]);
                    } catch (Exception e) {
                        throw new Exception("Sai cú pháp");
                    }

                    try {
                        board.move(start, end);
                        board.print();
                    } catch (Exception e) {
                        System.out.println();
                        System.err.println(e.getMessage());
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}