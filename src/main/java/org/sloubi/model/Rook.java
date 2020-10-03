package org.sloubi.model;

import java.util.ArrayList;

public class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Coordinate> getPossibleCoordinates() {
        ArrayList<Coordinate> destinations = new ArrayList<>();

        // On récupère les cases des lignes verticales et horizontales
        for (Coordinate.Direction direction : Coordinate.Direction.lines()) {
            for (Coordinate c : coordinate.getByDirection(direction)) {
                Piece piece = Board.getInstance().getPiece(c);

                // Si la case est vide, la tour peut s'y déplacer
                if (piece == null) {
                    destinations.add(c);
                }
                // Si la case contient une pièce adverse, la tour peut s'y déplacer mais ne va pas plus loin
                else if (piece.getColor() != color) {
                    destinations.add(c);
                    break;
                }
                // Si la case contient une pièce du joueur, la tour ne peut pas aller plus loin
                else {
                    break;
                }
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return "♜";
    }
}
