package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eadcn on 02/02/2018.
 */

public class Dia implements Serializable {
    public ArrayList<Place> getListPlaces() {
        return listPlaces;
    }

    public void setListPlaces(ArrayList<Place> listPlaces) {
        this.listPlaces = listPlaces;
    }

    private ArrayList<Place> listPlaces;
}
