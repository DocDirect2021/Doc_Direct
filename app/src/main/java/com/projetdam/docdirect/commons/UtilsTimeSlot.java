package com.projetdam.docdirect.commons;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilsTimeSlot {
    private static final String TAG = "TEST TEST";

    private static final CollectionReference consultations = AppSingleton.consultations;
    private static final CollectionReference patients = AppSingleton.patients;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> createSlots(String startTime, String endTime, int duration) {
        ArrayList<String> hours = new ArrayList<>();

        LocalTime t1 = LocalTime.parse(startTime);
        LocalTime t2 = LocalTime.parse(endTime);

        for (LocalTime t = t1; t.toSecondOfDay() < t2.toSecondOfDay(); t = t.plusMinutes(duration)) {
            hours.add(formatHeure(t));
        }
        return hours;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatHeure(LocalTime h) {
        String hh = String.format("%02d", h.getHour());
        String mm = String.format("%02d", h.getMinute());
        return hh + ":" + mm;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void saveRdv(ModelTimeSlot slot) {
        String patientId = slot.getPatientId();
        String doctorId = slot.getDoctorId();
        String rdvId = slot.getCreateId();

        // fix : doc vide pour forcer la création du document
        Map<String, Object> noData = new HashMap<>();
        consultations.document(doctorId).set(noData);

        // Ajout rdv dans la collection consultations
        consultations.document(doctorId).collection("slots").document(rdvId).set(slot)
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
        patients.document(patientId).collection("rdvs").document(rdvId).set(slot)
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void annulRdv(ModelTimeSlot slot) {
        String patientId = slot.getPatientId();
        String doctorId = slot.getDoctorId();
        String rdvId = slot.getCreateId();

        consultations.document(doctorId).collection("slots").document(rdvId).delete()
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

        patients.document(patientId).collection("rdvs").document(rdvId).delete()
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
