package org.sloubi.model;

// file : horizontal : a to h
// row : vertical 1 to 8

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate {
    private final char file;
    private final int row;

    /**
     * Enumération pour calculer les cases adjacentes
     */
    public enum Direction {
        // Lignes horizontales et verticales
        N(0, 1), E(1, 0), W(-1, 0), S(0, -1),
        // Diagonales
        NW(-1, 1), NE(1, 1), SW(-1, -1), SE(1, -1);

        private final int dFile;
        private final int dRow;

        Direction(int dFile, int dRow) {
            this.dFile = dFile;
            this.dRow = dRow;
        }

        public static Direction[] lines() {
            return new Direction[] {N, S, E, W};
        }

        public static Direction[] diagonals() {
            return new Direction[] {NW, NE, SW, SE};
        }
    }

    public Coordinate(char file, int row) {
        this.file = file;
        this.row = row;
    }

    public Coordinate(String c) {
        this.file = c.charAt(0);
        this.row = Character.getNumericValue(c.charAt(1));
    }

    public char getFile() {
        return file;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return String.format("%s%s", file, row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return file == that.file &&
                row == that.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(file, row);
    }

    public Coordinate getForwardLine(Piece.Color color, int nb) {
        if (color == Piece.Color.White) {
            return new Coordinate(file, row + nb);
        }
        else {
            return new Coordinate(file, row - nb);
        }
    }

    public ArrayList<Coordinate> getForwardDiagonal(Piece.Color color) {
        CoordinateList coordinates = new CoordinateList();

        if (color == Piece.Color.White) {
            coordinates.add(new Coordinate((char)(file + 1), row + 1));
            coordinates.add(new Coordinate((char)(file - 1), row + 1));
        }
        else {
            coordinates.add(new Coordinate((char)(file + 1), row - 1));
            coordinates.add(new Coordinate((char)(file - 1), row - 1));
        }

        return coordinates;
    }

    /**
     * Récupère les coordonnées des cases dans la direction demandés (en allant jusqu'à l'extrémité de l'échiquier)
     * @return Liste des coordonnées
     */
    public ArrayList<Coordinate> getByDirection(Direction direction) {
        return getByDirection(direction, 9);
    }

    /**
     * Récupère les coordonnées des cases dans la direction demandés (en allant jusqu'à l'extrémité de l'échiquier)
     * @param direction Direction souhaitée
     * @param limit Nombre de coordonnées à récupérer
     * @return Liste des coordonnées
     */
    public ArrayList<Coordinate> getByDirection(Direction direction, int limit) {
        CoordinateList squares = new CoordinateList();

        int i = 1;
        Coordinate c;
        do {
            c = new Coordinate((char) (file + i * direction.dFile), row + i * direction.dRow);
            i++;
        } while (squares.add(c) && i < limit);

        return squares;
    }

    public ArrayList<Coordinate> knightMoves() {
        CoordinateList squares = new CoordinateList();

        squares.add(new Coordinate((char)(file + 1), row + 2));
        squares.add(new Coordinate((char)(file + 1), row - 2));
        squares.add(new Coordinate((char)(file - 1), row + 2));
        squares.add(new Coordinate((char)(file - 1), row - 2));
        squares.add(new Coordinate((char)(file + 2), row + 1));
        squares.add(new Coordinate((char)(file + 2), row - 1));
        squares.add(new Coordinate((char)(file - 2), row + 1));
        squares.add(new Coordinate((char)(file - 2), row - 1));

        return squares;
    }

    public boolean isValid() {
        return file >= 'a' && file <= 'h' && row >= 1 && row <= 8;
    }

    /**
     * Récupère les coordonnées des cases entre 2 cases
     * Si les cases ne sont pas alignés (ni verticalement, ni horizontalement, ni diagonalement), on ne renvoie rien
     * @param other Autre case
     * @return Les coordonnées des cases entre les 2 cases
     */
    public ArrayList<Coordinate> between(Coordinate other) {
        CoordinateList coordinates = new CoordinateList();

        // Si les 2 points sont alignés (verticalement, horizontalement ou diagonalement)
        if (file == other.getFile() || row == other.getRow() || file - other.getFile() == row - other.getRow()) {

            int direction = file > other.getFile() || row > other.getRow() ? -1 : 1;

            for (int i = 1; i < 8; i++) {
                char newFile = other.getFile() != file ? (char)(file + i * direction) : file;
                int newRow = other.getRow() != row ? row + i * direction : row;
                Coordinate c = new Coordinate(newFile, newRow);

                if (c.equals(other)) break;

                coordinates.add(c);
            }
        }

        return coordinates;
    }

    public boolean isLastRow(Piece.Color color) {
        return color == Piece.Color.Black && row == 1 || color == Piece.Color.White && row == 8;
    }

    public static int getFirstRow(Piece.Color color) {
        return color == Piece.Color.Black ? 8 : 1;
    }
}

