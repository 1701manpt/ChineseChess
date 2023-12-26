module personal.chinesechess {
    requires javafx.controls;
    requires javafx.fxml;


    opens personal.chinesechess to javafx.fxml;
    exports personal.chinesechess;
    exports personal.chinesechess.Models;
    opens personal.chinesechess.Models to javafx.fxml;
}