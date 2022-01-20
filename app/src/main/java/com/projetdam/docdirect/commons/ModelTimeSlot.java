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
    private String documentID;      // Firestore document Id
    private String doctorId;        // Firestore string
    private String patientId;       // Firestore string
    private Timestamp rdvDate;      // Firestore timestamp
    private Timestamp requestDate;  // Firestore timestamp
    private boolean visio;          // Firestore boolean

    private String date;
    private String startTime;

    public ModelTimeSlot() {
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
        String rdvId = documentID;

        // Supression rdv dans la collection consultations
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

        // Suppression rdv dans les rdvs du patient
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
