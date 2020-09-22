package model;

import java.util.HashMap;

public class BoardMap extends HashMap<String, Square> {
    public Square get(Coordinate coordinate) {
        return get(coordinate.toString());
    }

    public void put(Coordinate coordinate, Square square) {
        put(coordinate.toString(), square);
    }

    public Piece getPiece(String coord) {
        return get(coord).getPiece();
    }

    public Piece getPiece(Coordinate coord) {
        return getPiece(coord.toString());
    }

    public void movePiece(Coordinate from, Coordinate destination) {
        Piece piece = getPiece(from);

        // Promotion en dame
        // Si c'est un pion qui arrive sur la dernière rangée
        if (piece.getClass() == Pawn.class && destination.isLastRow(piece.getColor())) {
            piece = new Queen(piece.getColor());
        }

        // Roque
        if (piece.getClass() == King.class && from.between(destination).size() == 1) {
            Coordinate rookFrom = new Coordinate(destination.getFile() == 'g' ? 'h' : 'a', destination.getRow());
            Coordinate rookDestination = new Coordinate(destination.getFile() == 'g' ? 'f' : 'd', destination.getRow());

            // Déplacement de la tour
            Piece rook = getPiece(rookFrom);
            get(rookFrom).removePiece();
            get(rookDestination).setPiece(rook);
        }

        get(from).removePiece();
        get(destination).setPiece(piece);
    }
}
