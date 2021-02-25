package com.example.database;

/**
 * Clase que representa los mensajes con tematica COVID de la app
 * para guardarlos y mostrar al usuario.
 */
public class COVID_Message {

    private int id;
    private String text;



    public COVID_Message(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
