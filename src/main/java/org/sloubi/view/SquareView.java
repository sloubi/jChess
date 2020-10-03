package org.sloubi.view;

import org.sloubi.model.Square;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SquareView extends Rectangle2D.Double {

    public static final int squareWidth = 80;
    public static final int squareHeight = 80;
    private final Color dark = new Color(181, 136, 99);
    private final Color light = new Color(240, 217, 181);
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
