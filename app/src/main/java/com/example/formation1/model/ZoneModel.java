package com.example.formation1.model;

public class ZoneModel {
    private String id;
    private String adresse;
    private String date;
    private String etat;
    private String username;



    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public ZoneModel(String id,String adresse, String date, String etat, String username) {
        this.id =id;
        this.adresse = adresse;
        this.date = date;
        this.etat = etat;
        this.username = username;
    }
}
