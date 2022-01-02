package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Modèle pour document Firestore
 **/
@RequiresApi(api = Build.VERSION_CODES.O)
public class ModelTimeSlot {
    @DocumentId
    private String documentID;

    private String doctorId;
    private String patientId;
    private String date;
    private String startTime;
    private boolean visio;
    private Timestamp rdvDate;
    private Timestamp requestDate;

    private String endTime;

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public ModelTimeSlot() {
    }

    public ModelTimeSlot(String doctorId, String patientId, String date, String startTime, String endTime, boolean visio, String a, String b, String c) {
        LocalDateTime time = LocalDateTime.parse(date + " " + startTime, timeFormatter);
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.requestDate = Timestamp.now();
        this.rdvDate = new Timestamp(time.toEpochSecond(ZoneOffset.of("+01:00")), 0);
    }

    public ModelTimeSlot(String doctorId, String patientId, LocalDate date, LocalTime time, boolean visio) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        this.startTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        this.visio = visio;
        this.requestDate = Timestamp.now();
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        this.rdvDate = new Timestamp(dateTime.toEpochSecond(ZoneOffset.of("+01:00")), 0);
    }

    @Exclude
    public String getCreateId() {
        return "rdv_" + date.replaceAll("\\D", "") + "_" + startTime.replaceAll("\\D", "");
    }

    public Timestamp getRdvDate() {
        return rdvDate;
    }

    public void setRdvDate(Timestamp rdvDate) {
        this.rdvDate = rdvDate;
    }

    public String getDocumentID() {
        return documentID;
    }

    @Exclude
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

    @Exclude
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Exclude
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

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }

    public void save() {
        String rdvId = getCreateId();
        // fix : doc vide pour forcer la création du document
        Map<String, Object> noData = new HashMap<>();
        AppSingleton.consultations.document(doctorId).set(noData);

        // Ajout rdv dans la collection consultations
        AppSingleton.consultations.document(doctorId).collection("slots").document(rdvId).set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        // Ajout rdv dans les rdvs du patient
        AppSingleton.patients.document(patientId).collection("rdvs").document(rdvId).set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void delete() {
        String rdvId = getCreateId();

        AppSingleton.consultations.document(doctorId).collection("slots").document(rdvId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        AppSingleton.patients.document(patientId).collection("rdvs").document(rdvId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
