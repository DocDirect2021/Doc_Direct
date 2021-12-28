package com.projetdam.docdirect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.projetdam.docdirect.adapter.AdapterPriseRdv;
import com.projetdam.docdirect.commons.ModelDaySlots;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.util.ArrayList;

public class PriseRdvActivity extends AppCompatActivity {
    RecyclerView rcvTimeSlots;
    TextView tvDocName;
    ImageView ivDocPhoto;

    ModelDoctor doctor;
    ArrayList<ModelDaySlots> daySlots = new ArrayList<>();
    AdapterPriseRdv adapter;

    void init() {
        tvDocName = findViewById(R.id.tvDocName);
        ivDocPhoto = findViewById(R.id.ivDocPhoto);

        rcvTimeSlots = findViewById(R.id.rcvTimeSlots);
        rcvTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        rcvTimeSlots.setHasFixedSize(true);
        adapter = new AdapterPriseRdv(this, daySlots);
        rcvTimeSlots.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prise_rdv);

        daySlots.add(new ModelDaySlots("01/01/2022",
                UtilsTimeSlot.createSlots("09:00", "12:10", 20)));
        daySlots.add(new ModelDaySlots("02/01/2022",
                UtilsTimeSlot.createSlots("09:30", "15:10", 20)));
        daySlots.add(new ModelDaySlots("03/01/2022",
                UtilsTimeSlot.createSlots("09:00", "12:00", 20)));
        init();

        Intent intent = getIntent();
        doctor = intent.getParcelableExtra("doctor");
        tvDocName.setText(String.format("%s %s", doctor.getName(), doctor.getFirstname()));
    }
}