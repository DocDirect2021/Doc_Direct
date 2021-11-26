package com.projetdam.docdirect;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.commons.ModelDoctor;

public class TempMisc {
    private static final String TAG = "TEMP DEMO";

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference collection = db.collection("doctors");

    public static void retrieveDb() {
        collection.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int count = 0;
                        ModelDoctor doc;
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            count++;
                            doc = documentSnapshot.toObject(ModelDoctor.class);
                            Log.i(TAG, count + " : " + doc.getDocumentID()
                                    + " / " + doc.getAddress()
                                    + " / " + doc.getCity()
                                    + " / " + doc.getName()
                                    + " / " + doc.getFirstName());
                            if (count >= 15) break;
                        }
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    }
                });
    }
}
