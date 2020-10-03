package org.sloubi.model;

import java.util.ArrayList;

public class Bishop extends Piece {
    public Bishop(Color color) {
        super(color);
    }

    @Override
    public String toString() {
        return "♝";
    }

    @Override
    public ArrayList<Coordinate> getPossibleCoordinates() {
        ArrayList<Coordinate> destinations = new ArrayList<>();

        // On récupère les cases des diagonales
        for (Coordinate.Direction direction : Coordinate.Direction.diagonals()) {
            for (Coordinate c : coordinate.getByDirection(direction)) {
                Piece piece = Board.getInstance().getPiece(c);

                // Si la case est vide, le fou peut s'y déplacer
                if (piece == null) {
                    destinations.add(c);
                }
                // Si la case contient une pièce adverse, le fou peut s'y déplacer mais ne va pas plus loin
                else if (piece.getColor() != color) {
                    destinations.add(c);
                    break;
                }
                // Si la case contient une pièce du joueur, le fou ne peut pas aller plus loin
                else {
                    break;
                }
            }
        }

        return destinations;
    }
}
