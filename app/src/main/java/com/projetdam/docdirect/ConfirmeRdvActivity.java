package com.projetdam.docdirect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.projetdam.docdirect.authentification.ForgotPassword;
import com.projetdam.docdirect.authentification.RegisterUser;
import com.projetdam.docdirect.commons.NodesNames;
import com.projetdam.docdirect.commons.RdvInformation;

import java.util.ArrayList;

public class ConfirmeRdvActivity extends AppCompatActivity implements View.OnClickListener {
    /* var */
    private RecyclerView recyclerView;
    private ArrayList<RdvInformation> list;
    private TextView txtJour;
    private TextView txtStartTime;
    private ImageView imgDoc;
    private Button btnSaveRdv;
    private Button btnAnnuleRdv;
    private Uri myUri;

    /**
     * Initialisation des composants
     **/
    public void init() {
         txtJour = findViewById(R.id.txtJour);
         txtStartTime = findViewById(R.id.txtStartTime);
        btnSaveRdv = findViewById(R.id.btnSaveRdv);
        imgDoc = findViewById(R.id.imgDoc);
        btnSaveRdv = findViewById(R.id.btnSaveRdv);
        btnAnnuleRdv = findViewById(R.id.btnAnnuleRdv);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirme_rdv);
        init();
        Intent intent=getIntent();
        txtJour.setText(getIntent().getStringExtra(NodesNames.KEY_JOUR));
        txtStartTime.setText(getIntent().getStringExtra(NodesNames.KEY_START_TIME));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveRdv:
                //startActivity(new Intent(ConfirmeRdvActivity.this, ProfileActivity.class));
                break;
            case R.id.imgBack:
                startActivity(new Intent(ConfirmeRdvActivity.this, ProfileActivity.class));
                break;
            case R.id.btnAnnuleRdv:
                startActivity(new Intent(ConfirmeRdvActivity.this, PatientRendezVousActivity.class));
                break;
        }

    }
}