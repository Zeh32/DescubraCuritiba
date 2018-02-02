package com.example.jgaug.descubracuritiba.Helpers;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by eadcn on 02/02/2018.
 */

public class Itinerário implements Serializable {
    public ArrayList<Dia> getDias() {
        return dias;
    }

    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }

    private ArrayList<Dia> dias;
}
