package org.sloubi.view;

import org.sloubi.model.Board;
import org.sloubi.model.Coordinate;
import org.sloubi.model.Piece;

public class BoardAscii {

    public void displayAscii() {
        for (int y = 8; y >= 1; y--) {
            for (char x = 'a'; x <= 'h'; x++) {
                System.out.print("+---");
            }
            System.out.println("+");

            for (char x = 'a'; x <= 'h'; x++) {
                System.out.print("|");
                Piece piece = Board.getInstance().getPiece(new Coordinate(x, y));
                if (piece != null) {
                    System.out.print(" " + piece + " ");
                }
                else {
                    System.out.print("   ");
                }
            }

            // Bordure de droite
            System.out.print("|");
            System.out.println();
        }

        // Bordure du bas
        for (char x = 'a'; x <= 'h'; x++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
}
