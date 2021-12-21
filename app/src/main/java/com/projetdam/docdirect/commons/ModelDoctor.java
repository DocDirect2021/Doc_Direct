package com.projetdam.docdirect.commons;

import com.google.firebase.firestore.DocumentId;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.GeoPoint;

public class ModelDoctor implements Parcelable {
    @DocumentId
    private String documentID;

    public String getDoctorId() {
        return documentID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    private String name;
    private String mail;
    private String phone;
    private String city;
    private String speciality;
    private String firstname;
    private String street;
    private int postcode;
    private int housenumber;
    private int likes;
    private GeoPoint geoloc;
    private Uri avatar;
    private float distance;

    public int getDistance() {
        return Math.round(distance);
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Uri getAvatar() {
        return avatar;
    }

    public void setAvatar(Uri avatar) {
        this.avatar = avatar;
    }

    public ModelDoctor() {
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

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(documentID);
        dest.writeString(name);
        dest.writeString(mail);
        dest.writeString(phone);
        dest.writeString(city);
        dest.writeString(speciality);
        dest.writeString(firstname);
        dest.writeString(street);
        dest.writeInt(postcode);
        dest.writeInt(housenumber);
        dest.writeInt(likes);
        dest.writeParcelable(avatar, flags);
        dest.writeFloat(distance);
    }

    protected ModelDoctor(Parcel in) {
        documentID = in.readString();
        name = in.readString();
        mail = in.readString();
        phone = in.readString();
        city = in.readString();
        speciality = in.readString();
        firstname = in.readString();
        street = in.readString();
        postcode = in.readInt();
        housenumber = in.readInt();
        likes = in.readInt();
        avatar = in.readParcelable(Uri.class.getClassLoader());
        distance = in.readFloat();
    }

    public static final Creator<ModelDoctor> CREATOR = new Creator<ModelDoctor>() {
        @Override
        public ModelDoctor createFromParcel(Parcel in) {
            return new ModelDoctor(in);
        }

        @Override
        public ModelDoctor[] newArray(int size) {
            return new ModelDoctor[size];
        }
    };

}
