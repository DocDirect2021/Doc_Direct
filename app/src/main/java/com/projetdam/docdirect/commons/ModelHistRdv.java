package com.projetdam.docdirect.commons;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class ModelHistRdv {



    private String doctorId;
    private String patientId;
    private String date;
    private String startTime;
    private String endTime;
    private boolean visio;
    private String requestDate;
    private String name;
    private String mail;
    private String phone;
    private String city;
    private String speciality;
    private String firstname;
    private String street;
    private int postcode;
    private int housenumber;
    private Uri avatar;
    private int likes;
    ArrayList<ModelHistRdv> listHistRdv;
    ModelHistRdv listHistRdv2;


    public ModelHistRdv() {
    }

//    public ModelHistRdv(ArrayList<ModelHistRdv> listHistRdv) {
//        this.listHistRdv = listHistRdv;
//    }
    public ModelHistRdv(ModelHistRdv listHistRdv2) {
        this.listHistRdv2 = listHistRdv2;
    }
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isVisio() {
        return visio;
    }

    public void setVisio(boolean visio) {
        this.visio = visio;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public Uri getAvatar() {
        return avatar;
    }

    public void setAvatar(Uri avatar) {
        this.avatar = avatar;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<ModelHistRdv> getListHistRdv() {
        return listHistRdv;
    }

    public void setListHistRdv(ArrayList<ModelHistRdv> listHistRdv) {
        this.listHistRdv = listHistRdv;
    }





}
