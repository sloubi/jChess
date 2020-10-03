package org.sloubi.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public abstract class Piece {
    protected Coordinate coordinate;
    protected final Color color;
    protected Coordinate initialPosition;

    public enum Color {
        Black, White;

        public Color toggle() {
            return this == White ? Black : White;
        }

        public String translate() { return this == White ? "Blanc" : "Noir";  }
    }

    public Piece(Color color) {
        this.color = color;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        if (initialPosition == null)
            initialPosition = coordinate;
        
        this.coordinate = coordinate;
    }

    public Color getColor() {
        return color;
    }

    /**
     * Renvoie les coordonnées possibles de déplacement de la pièce
     * Sans prendre en compte les échecs
     * @return
     */
    protected abstract ArrayList<Coordinate> getPossibleCoordinates();

    /**
     * Renvoie les coordonnées possibles de déplacement de la pièce
     * En prenant en compte les échecs
     * @return
     */
    public ArrayList<Coordinate> getPossibleMoves() {
        Coordinate from = coordinate;
        CoordinateList destinations = new CoordinateList();

        for (Coordinate to : getPossibleCoordinates()) {
            // Backup
            Piece fromPiece = Board.getInstance().getPiece(from);
            Piece toPiece = Board.getInstance().getPiece(to);

            // Move
            Board.getInstance().getSquare(from).removePiece();
            Board.getInstance().getSquare(to).setPiece(fromPiece);

            // Pas en situation d'échec
            if (!Board.getInstance().isInCheck(color)) {
                destinations.add(to);
            }

            // On annule le mouvement
            Board.getInstance().getSquare(from).setPiece(fromPiece);
            Board.getInstance().getSquare(to).setPiece(toPiece);
        }

        return destinations;
    }

    public boolean canMoveTo(Coordinate destination) {
        return getPossibleCoordinates().contains(destination);
    }

    public boolean isInitialPosition() {
        return initialPosition.equals(coordinate);
    }

    public Image getImage() {
        Image img = null;
        try {
            String filename = color.toString().toLowerCase().charAt(0) + "-" + getClass().getSimpleName().toLowerCase();
            InputStream is = Piece.class.getResourceAsStream("/images/" + filename + ".png");
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}
