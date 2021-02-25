package com.example.database;

/**
 * Clase que representa los datos de los usuarios que van a usar la App
 */
public class User {

    private String email, name, surname, password;
    private int score;

    public User(String email, String name, String surname, String password, int score) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
