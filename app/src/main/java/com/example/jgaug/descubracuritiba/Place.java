package com.example.jgaug.descubracuritiba;

public class Place {
    private String name;
    private String description;
    //Horário da visita
    //Imagem (thumbnail)

    public Place( String name, String description ) {
        this.name = name;
        this.description = description;
    }

    public String getName( ) {
        return name;
    }

    public String getDescription( ) {
        return description;
    }
}
