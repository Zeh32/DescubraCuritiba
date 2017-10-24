package com.example.jgaug.descubracuritiba;

public class Place {
    private String image;
    private String name;
    private String description;
    //Horário da visita
    //Imagem (thumbnail)

    public Place( String image, String name, String description ) {
        this.image = image;
        this.name = name;
        this.description = description;
    }

    public String getName( ) {
        return name;
    }

    public String getDescription( ) {
        return description;
    }

    public String getImage( ) { return image; }
}
