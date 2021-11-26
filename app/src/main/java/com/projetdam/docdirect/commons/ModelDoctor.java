package com.projetdam.docdirect.commons;

import com.google.firebase.firestore.GeoPoint;

public class ModelDoctor {
    private String name;
    private String mail;
    private String phone;
    private GeoPoint geoloc;

    public GeoPoint getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(GeoPoint geoloc) {
        this.geoloc = geoloc;
    }

    private String speciality;
    private int lat;
    private int lon;
    private int likes;

    public ModelDoctor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
