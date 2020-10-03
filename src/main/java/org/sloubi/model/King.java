package org.sloubi.model;

import java.util.ArrayList;
import java.util.Map;

public class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public ArrayList<Coordinate> getPossibleCoordinates() {
        ArrayList<Coordinate> destinations = new ArrayList<>();

        // On récupère les cases des diagonales, horizontale, verticale
        for (Coordinate.Direction direction : Coordinate.Direction.values()) {
            for (Coordinate c : coordinate.getByDirection(direction, 1)) {
                Piece piece = Board.getInstance().getPiece(c);

                // Si la case est vide ou contient une pièce adverse, le roi peut s'y déplacer
                if (piece == null || piece.getColor() != color) {
                    destinations.add(c);
                }
            }
        }

        if (canCastle('a')) {
            destinations.add(new Coordinate('c', Coordinate.getFirstRow(color)));
        }
        if (canCastle('h')) {
            destinations.add(new Coordinate('g', Coordinate.getFirstRow(color)));
        }

        return destinations;
    }

    @Override
    public String toString() {
        return "♚";
    }

    /**
     * Once in every game, each king can make a special move, known as castling.
     * Castling consists of moving the king two squares along the first rank toward a rook (that is on the player's first rank)
     * and then placing the rook on the last square that the king just crossed.
     * Castling is permissible if the following conditions are met:
        Neither the king nor the rook has previously moved during the game.
        There are no pieces between the king and the rook.
        The king cannot be in check, nor can the king pass through any square that is under attack by an enemy piece,
           or move to a square that would result in check.
     */
    public boolean canCastle(char rookFile) {
        // On vérifie que le roi n'ait pas bougé
        if (!isInitialPosition())
            return false;

        // Récupération des coordonnées de la tour choisie
        Coordinate rookCoordinate = new Coordinate(rookFile, Coordinate.getFirstRow(color));

        // On vérifie que la tour n'ait pas bougée
        Piece rook = Board.getInstance().getPiece(rookCoordinate);
        if (rook == null || !rook.isInitialPosition()) {
            return false;
        }

        // On vérifie qu'il n'y ait pas de pièces entre les 2
        ArrayList<Coordinate> coordinatesBetween = coordinate.between(rookCoordinate);
        for (Coordinate c : coordinatesBetween) {
            if (Board.getInstance().getPiece(c) != null) {
                return false;
            }
        }

        // On vérifie que le roi ne soit pas en échec
        if (Board.getInstance().isInCheck(color))
            return false;

        // On vérifie que les 2 cases de déplacement du roi ne soient pas attaquées
        Coordinate c1, c2;
        if (rookFile == 'h') {
            c1 = new Coordinate('f', Coordinate.getFirstRow(color));
            c2 = new Coordinate('g', Coordinate.getFirstRow(color));
        }
        else {
            c1 = new Coordinate('d', Coordinate.getFirstRow(color));
            c2 = new Coordinate('c', Coordinate.getFirstRow(color));
        }

        for (Map.Entry<String, Square> entry : Board.getInstance().getSquares().entrySet()) {
            Square square = entry.getValue();
            Piece piece = square.getPiece();
            if (piece != null && piece.getColor() != color && (piece.canMoveTo(c1) || piece.canMoveTo(c2))) {
                return false;
            }
        }

        return true;
    }
}
