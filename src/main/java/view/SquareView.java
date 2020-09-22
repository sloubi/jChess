package view;

import model.Square;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SquareView extends Rectangle2D.Double {

    private static final int squareWidth = 40;
    private static final int squareHeight = 40;
    private final Color dark = new Color(181, 136, 99);//new Color(80, 80, 80);
    private final Color light = new Color(240, 217, 181);//new Color(100, 100, 100);
    private final Square square;

    public SquareView(Square square) {
        this.square = square;

        int x = file2X(square.getCoordinate().getFile());
        int y = row2Y(square.getCoordinate().getRow());

        setRect(x, y, squareWidth, squareHeight);
    }

    public Color getColor() {
        return square.getColor() == Square.Color.Dark ? dark : light;
    }

    public static char x2File(int x) {
        int inter = x == 0 ? 0 : x / squareWidth;
        return (char)(inter + 97);
    }

    public static int y2Row(int y) {
        int inter = y == 0 ? 0 : y / squareHeight;
        return (inter - 8) * -1;
    }

    public static int file2X(char file) {
        return ((int)file - 97) * squareWidth;
    }

    public static int row2Y(int row) {
        return (row - 8) * -1 * squareHeight;
    }
}
