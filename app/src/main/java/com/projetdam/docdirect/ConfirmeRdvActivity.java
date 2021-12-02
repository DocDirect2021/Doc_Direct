package com.projetdam.docdirect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.projetdam.docdirect.commons.RdvInformation;

import java.util.ArrayList;

public class ConfirmeRdvActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<RdvInformation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirme_rdv);


        TextView txtJour = findViewById(R.id.txtJour);
        TextView txtStartTime = findViewById(R.id.txtStartTime);

        txtJour.setText(getIntent().getStringExtra("jour"));
        txtStartTime.setText(getIntent().getStringExtra("startTime"));


    }
}