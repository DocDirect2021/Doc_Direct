package com.projetdam.docdirect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projetdam.docdirect.adapter.PatientRdvAdapter;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class PatientRendezVousActivity extends AppCompatActivity {
    private static final String TAG = "TEST TEST TEST TEST";

    private RecyclerView recyclerViewRdv;
    private TextView tv;
    private ImageView iv;

    private List<RdvInformation> mList;
    private PatientRdvAdapter adapter;
    private ModelDoctor doctor;

    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private void init() {
        recyclerViewRdv = findViewById(R.id.rclPatient_appointements);
        recyclerViewRdv.setHasFixedSize(true);
        recyclerViewRdv.setLayoutManager(new LinearLayoutManager(this));
        tv = findViewById(R.id.tvDocRdv);
        iv = findViewById(R.id.ivDocRdv);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_rendez_vous);
        init();

        Intent intent = getIntent();
        doctor = intent.getParcelableExtra("doctor");
        tv.setText(doctor.getName() + " " + doctor.getFirstname());
        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);
        Context context = iv.getContext();
        Glide.with(context).load(doctor.getAvatar()).apply(options).fitCenter().circleCrop().override(50, 50).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);

        mList = new ArrayList<>();
        // create appointment list
        ArrayList<String> nestedList = UtilsTimeSlot.createSlots("09:30", "18:15", 20);

        LocalDate today = LocalDate.now();     //Today
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        LocalDate f = today.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        for (int i = 0; i < 7; i++) {
            LocalDate dateRdv = today.plusDays(i);// Next days
            if (!f.isEqual(LocalDate.from(dateRdv))) {
                String text = dateRdv.format(formatters);
                mList.add(new RdvInformation(nestedList, text));
            }
        }
        adapter = new PatientRdvAdapter(PatientRendezVousActivity.this, mList, doctor, patientId);
        recyclerViewRdv.setAdapter(adapter);
    }

}