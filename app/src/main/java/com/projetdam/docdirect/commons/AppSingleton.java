package com.projetdam.docdirect.commons;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AppSingleton {
    private static AppSingleton instance = null;
    private String patientId = null;
    private ModelDoctor pickedDoctor = null;

    public static final CollectionReference doctors
            = FirebaseFirestore.getInstance().collection("doctors");
    public static final CollectionReference consultations
            = FirebaseFirestore.getInstance().collection("consultations");
    public static final CollectionReference patients
            = FirebaseFirestore.getInstance().collection("users");

    private AppSingleton() {
    }

    public static AppSingleton getInstance() {
        if (instance == null) {
            /* Synchronized block :
               in order to prevent multithreading or running this method twice at the same time
            */
            synchronized (AppSingleton.class) {
                instance = new AppSingleton();
            }
        }
        return instance;
    }

    public String getPatientId() {
        if (patientId == null || patientId.isEmpty())
            throw new NullPointerException("patientId not set.");
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public ModelDoctor getPickedDoctor() {
        return pickedDoctor;
    }

    public void setPickedDoctor(ModelDoctor pickedDoctor) {
        this.pickedDoctor = pickedDoctor;
    }
}