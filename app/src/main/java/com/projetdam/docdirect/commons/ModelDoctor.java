package com.projetdam.docdirect.commons;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.GeoPoint;

public class ModelDoctor {
    @DocumentId
    private String documentID;

    private String name;
    private String firstname;
    private String mail;
    private String phone;
    private GeoPoint geoloc;
    private String speciality;
    private int lat;
    private int lon;
    private int likes;
    private String street;
    private int housenumber;
    private String city;
    private int postcode;

    public String getAddress() {
        return housenumber + " " + street;
    }

    public String getStreet() {
        return street;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GeoPoint getGeoloc() {
        return geoloc;
    }

    public void setGeoloc(GeoPoint geoloc) {
        this.geoloc = geoloc;
    }

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
