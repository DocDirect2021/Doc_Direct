package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ModelTimeSlot {
    @DocumentId
    private String documentID;

    private String doctorId;
    private String patientId;
    private String date;
    private String startTime;
    private String endTime;
    private boolean visio;
    private String requestDate;
    private String firstname;
    private String name;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    private String speciality;

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public ModelTimeSlot() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ModelTimeSlot(String doctorId, String patientId, String date, String startTime, String endTime, boolean visio, String firstname, String name, String speciality) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.visio = visio;
        this.requestDate = LocalDateTime.now(ZoneId.of("Europe/Paris")).format(timeFormatter);
        this.firstname = firstname;
        this.name = name;
        this.speciality = speciality;
    }

    @Exclude
    public String getCreateId() {
        return "rdv_" + date.replaceAll("\\D", "") + "_" + startTime.replaceAll("\\D", "");
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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
}
