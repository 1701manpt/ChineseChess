package personal.chinesechess.Models;

public enum ChessAlias {
    CHARIOT("chariot"),
    HORSE("horse"),
    EMPTY("empty");

    public final String value;

    ChessAlias(String value) {
        this.value = value;
    }
}
