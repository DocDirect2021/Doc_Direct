package com.projetdam.docdirect.commons;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelSosMessage {
    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private CollectionReference sosMessages = FirebaseFirestore.getInstance().collection("sos_messages");
    private DocumentReference patientDocument = sosMessages.document(patientId);

    public DocumentReference getPatientDocument() {
        return patientDocument;
    }

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

    public ArrayList<ModelRecipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<ModelRecipient> recipients) {
        this.recipients = recipients;
    }

    public void saveMessage() {
        Map<String, Object> text = new HashMap<>();
        text.put("sos_text", sosText);
        patientDocument.set(text);

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
