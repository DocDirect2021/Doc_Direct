package com.projetdam.docdirect.commons;

import android.os.Build;
import android.text.style.LocaleSpan;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UtilsTimeSlot {
    private static final String TAG = "TEST TEST";
    private static LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> getSlots(String startTime, String endTime, int duration) {
        ArrayList<String> hours = new ArrayList<>();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime t1 = LocalTime.parse(startTime, timeFormatter);
        LocalTime t2 = LocalTime.parse(endTime, timeFormatter);

        for (LocalTime t = t1; t.toSecondOfDay() < t2.toSecondOfDay(); t = t.plusMinutes(duration)) {
            hours.add(t.format(timeFormatter));
        }
        return hours;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void saveSlot(CollectionReference consultation, String doctorID, ModelTimeSlot slot) {

        // fix : doc vide pour forcer la création du document
        Map<String, Object> noData = new HashMap<>();
        consultation.document(doctorID).set(noData);

        consultation.document(doctorID).collection("slots").document(slot.getCreateId()).set(slot)
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
