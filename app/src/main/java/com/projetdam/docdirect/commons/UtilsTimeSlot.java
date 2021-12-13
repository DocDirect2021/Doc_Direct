package com.projetdam.docdirect.commons;

import android.os.Build;
import android.text.style.LocaleSpan;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilsTimeSlot {
    private static final String TAG = "TEST TEST";
    private static LocalTime time;

    public static CollectionReference doctors = FirebaseFirestore.getInstance().collection("doctors");
    public static CollectionReference consultations = FirebaseFirestore.getInstance().collection("consultations");
    public static CollectionReference patients = FirebaseFirestore.getInstance().collection("users");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> createSlots(String startTime, String endTime, int duration) {
        ArrayList<String> hours = new ArrayList<>();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime t1 = LocalTime.parse(startTime, timeFormatter);
        LocalTime t2 = LocalTime.parse(endTime, timeFormatter);

        for (LocalTime t = t1; t.toSecondOfDay() < t2.toSecondOfDay(); t = t.plusMinutes(duration)) {
            hours.add(t.format(timeFormatter));
        }
        return hours;




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

       DocumentReference docRef =  consultations.document(doctorId).collection("slots").document(rdvId);
        Map<String, Object> map = new HashMap<>();
        map.put("patientId", "");
        docRef.update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.e("onSuccess update", "update the doc with field patientId");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("onFailure update", "update the doc with field patientId ", e);

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
