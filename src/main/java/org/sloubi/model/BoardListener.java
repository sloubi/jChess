package org.sloubi.model;

public interface BoardListener {
    void playerChanged(Piece.Color color);
    void playerInCheck(Piece.Color color);
    void playerInCheckmate(Piece.Color color);
    void newGame();
}
