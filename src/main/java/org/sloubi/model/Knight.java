package org.sloubi.model;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Coordinate> getPossibleCoordinates() {
        ArrayList<Coordinate> destinations = new ArrayList<>();
        for (Coordinate c : coordinate.knightMoves()) {
            Piece piece = Board.getInstance().getPiece(c);
            // Si la case contient une pièce du joueur, le cavalier ne peut pas s'y déplacer
            if (piece == null || piece.getColor() != color) {
                destinations.add(c);
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return "♞";
    }
}
