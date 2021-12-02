package com.projetdam.docdirect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projetdam.docdirect.authentification.ForgotPassword;
import com.projetdam.docdirect.authentification.RegisterUser;
import com.projetdam.docdirect.commons.RdvInformation;

import java.util.ArrayList;

public class ConfirmeRdvActivity extends AppCompatActivity implements View.OnClickListener {
    /* var */
    private RecyclerView recyclerView;
    private ArrayList<RdvInformation> list;
    private TextView txtJour;
    private TextView txtStartTime;
    private Button btnSaveRdv;

    /**
     * Initialisation des composants
     **/
    public void init() {
         txtJour = findViewById(R.id.txtJour);
         txtStartTime = findViewById(R.id.txtStartTime);
        btnSaveRdv = findViewById(R.id.btnSaveRdv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirme_rdv);
        init();
        txtJour.setText(getIntent().getStringExtra("jour"));
        txtStartTime.setText(getIntent().getStringExtra("startTime"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(ConfirmeRdvActivity.this, RegisterUser.class));
                break;
        }

    }
}