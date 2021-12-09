package com.projetdam.docdirect.commons;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ModelSosMessage {
    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private DocumentReference patientDocument = UtilsTimeSlot.patients.document(patientId);

    public DocumentReference getPatientDocument() {
        return patientDocument;
    }

    //    private ArrayList<Integer> contactIds = new ArrayList<>();
    private HashSet<Long> contactIds = new HashSet<>();
    private ArrayList<ModelRecipient> recipients;
    private String sosText;

    public ModelSosMessage() {
    }

    public String getSosText() {
        return sosText;
    }

    public void setSosText(String sosText) {
        this.sosText = sosText;
    }

    public HashSet<Long> getContactIds() {
        return contactIds;
    }

    public void setContactIds(HashSet<Long> contactIds) {
        this.contactIds = contactIds;
    }

    public ArrayList<ModelRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<ModelRecipient> recipients) {
        this.recipients = recipients;
    }

    public void saveMessage() {
        List<Long> arr = new ArrayList<>(contactIds);
        Map<String, Object> ids = new HashMap<>();
        ids.put("sos_contact_id", arr);
        patientDocument.update(ids);

        Map<String, Object> text = new HashMap<>();
        text.put("sos_text", sosText);
        patientDocument.update(text);

    }

    public String getMessage() {
        // TODO: 06/12/2021 A revoir !!!!! 
        String text = "";

        patientDocument.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String text = documentSnapshot.getString("sos_text");

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return text;
    }
}
