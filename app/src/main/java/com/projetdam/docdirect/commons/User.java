package com.projetdam.docdirect.commons;

public class User {
    public String nom;
    public  String prenom;
    public  String dateNaiss;
    public  String telephone;
    public  String email;

    public User(){

    }

    public User(String nom, String prenom, String dateNaiss, String telephone, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.telephone = telephone;
        this.email = email;
    }
}
