package com.projetdam.docdirect.commons;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UtilsTimeSlot {
    private static final String TAG = "TEST TEST";

    private static final ZoneOffset zoneOffset = ZoneOffset.of("+01:00");

    public static LocalDateTime getDateTime(Timestamp timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp.getSeconds(), 0, zoneOffset);
    }

    public static Timestamp getTimestamp(LocalDateTime dateTime) {
        return new Timestamp(dateTime.toEpochSecond(zoneOffset), 0);
    }

    public static String formatHeure(LocalTime h) {
        String hh = String.format("%02d", h.getHour());
        String mm = String.format("%02d", h.getMinute());
        return hh + ":" + mm;
    }

    public static void saveRdv(ModelTimeSlot slot) {
        String patientId = slot.getPatientId();
        String doctorId = slot.getDoctorId();
        String rdvId = slot.getCreateId();

        // fix : doc vide pour forcer la création du document
        Map<String, Object> noData = new HashMap<>();
        AppSingleton.consultations.document(doctorId).set(noData);

        // Ajout rdv dans la collection consultations
        AppSingleton.consultations.document(doctorId).collection("slots").document(rdvId).set(slot)
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
        AppSingleton.patients.document(patientId).collection("rdvs").document(rdvId).set(slot)
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

    public static void annulRdv(ModelTimeSlot slot) {
        String patientId = slot.getPatientId();
        String doctorId = slot.getDoctorId();
        String rdvId = slot.getCreateId();

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
                        Log.e("onSuccess update", "delete the doc");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("onFailure update", "delete the doc ", e);

                    }
                });
    }

}
