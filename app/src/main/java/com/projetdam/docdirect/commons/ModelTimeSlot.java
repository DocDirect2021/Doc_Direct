package com.projetdam.docdirect.commons;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public ModelTimeSlot() {
    }

    public ModelTimeSlot(String doctorId, String patientId, String date, String startTime, String endTime, boolean visio, String a, String b, String c) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.requestDate = LocalDateTime.now(ZoneId.of("Europe/Paris")).format(timeFormatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ModelTimeSlot(String doctorId, String patientId, String date, String startTime, String endTime, boolean visio) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.visio = visio;
        this.requestDate = LocalDateTime.now(ZoneId.of("Europe/Paris")).format(timeFormatter);
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
