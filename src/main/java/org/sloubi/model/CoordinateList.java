package org.sloubi.model;

import java.util.ArrayList;

public class CoordinateList extends ArrayList<Coordinate> {

    /**
     * Les cases qui n'existent pas ne sont pas ajoutées
     * @param coordinate Coordonnées à ajouter
     * @return Ajouté avec succès ?
     */
    @Override
    public boolean add(Coordinate coordinate) {
        if (coordinate.isValid())
            return super.add(coordinate);
        return false;
    }
}
