package org.sloubi.model;

import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    // TODO gestion de la prise en passant
    @Override
    public ArrayList<Coordinate> getPossibleCoordinates() {
        ArrayList<Coordinate> destinations = new ArrayList<>();

        // On regarde si la case devant est libre
        Coordinate newCoord = coordinate.getForwardLine(color, 1);
        if (Board.getInstance().isEmpty(newCoord)) {
            destinations.add(newCoord);
        }

        // Si le pion n'a pas encore bougé, il a le droit d'avancer de 2 cases
        newCoord = coordinate.getForwardLine(color, 2);
        if (isInitialPosition() && Board.getInstance().isEmpty(newCoord)) {
            destinations.add(newCoord);
        }

        // Le pion mange en diagonale, seulement devant lui
        for (Coordinate c : coordinate.getForwardDiagonal(color)) {
            Piece piece = Board.getInstance().getPiece(c);
            if (piece != null && piece.getColor() != color) {
                destinations.add(c);
            }
        }

        return destinations;
    }

    @Override
    public String toString() {
        return "♟";
    }
}
