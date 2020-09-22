package model;

public class Square {
    private final Color color;
    private final Coordinate coordinate;
    private Piece piece;
    private boolean selected = false;

    public enum Color {
        Light, Dark
    }

    public Square(Color color, Coordinate coordinate) {
        this.color = color;
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        if (piece != null)
            piece.setCoordinate(coordinate);
    }

    public void removePiece() {
        piece = null;
    }

    public Color getColor() {
        return color;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
