package com.projetdam.docdirect;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.adapter.PatientRdvAdapter;
import com.projetdam.docdirect.authentification.ForgotPassword;
import com.projetdam.docdirect.authentification.RegisterUser;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PatientRendezVousActivity extends AppCompatActivity {
    private static final String TAG = "TEST TEST TEST TEST";
    private RecyclerView recyclerViewRdv;
   // private PatientRdvAdapter.RecyclerViewClickListener listener;
    private List<RdvInformation> mList;
    private PatientRdvAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rendez_vous);


        recyclerViewRdv = findViewById(R.id.rclPatient_appointements);
        recyclerViewRdv.setHasFixedSize(true);
        recyclerViewRdv.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();
        // get appointement patient
        ArrayList<String> nestedList = UtilsTimeSlot.getSlots("09:30", "18:15", 20);
        for (String hr : nestedList) {

            Log.i(TAG, "onCreate: " + hr);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();     //Today

        for (int i= 0; i<7;i++) {
            LocalDate dateRdv = today.plusDays(i);
            LocalDate formatDateTime = LocalDate.parse(dateRdv.toString(), formatter);
            mList.add(new RdvInformation(nestedList, ""+ formatDateTime));
        }
//        mList.add(new RdvInformation(nestedList, "30/11/2021"));
//        mList.add(new RdvInformation(nestedList, "01/12/2021"));
        adapter = new PatientRdvAdapter(mList);
        recyclerViewRdv.setAdapter(adapter);

    }

}