package com.projetdam.docdirect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projetdam.docdirect.commons.ModelTimeSlot;
import com.projetdam.docdirect.commons.NodesNames;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.rdvPatient.PatientRendezVousActivity;
import com.projetdam.docdirect.searchDoc.DetailActivity;

import java.util.ArrayList;

public class ConfirmeRdvActivity extends AppCompatActivity implements View.OnClickListener {
    /* var */
    private FirebaseAuth mAuth;
    FirebaseFirestore db ;
    CollectionReference consultations;
    CollectionReference patients;
    ModelTimeSlot slot;

    private ArrayList<RdvInformation> list;
    private TextView txtJour;
    private TextView txtStartTime;
    private ImageView imgDoc;
    private Button btnSaveRdv;
    private Button btnAnnuleRdv;
    TextView txtBanner;
    ImageView imgBanner;

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
        txtBanner = findViewById(R.id.txtBanner);
        imgBanner = findViewById(R.id.imgBanner);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirme_rdv);
        init();

        Intent intent = getIntent();
        txtJour.setText(intent.getStringExtra(NodesNames.KEY_JOUR));
        txtStartTime.setText(intent.getStringExtra(NodesNames.KEY_START_TIME));


        txtBanner.setText(getIntent().getStringExtra(NodesNames.KEY_NOM));
        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);

        Context context = getApplicationContext();
        Glide.with(context).load(getIntent().getStringExtra(NodesNames.KEY_AVATAR)).apply(options).fitCenter().circleCrop().override(50, 50).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgBanner);
        Glide.with(context).load(getIntent().getStringExtra(NodesNames.KEY_AVATAR)).apply(options).fitCenter().circleCrop().override(30, 30).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgDoc);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSaveRdv:
                //UtilsTimeSlot.saveRdv(consultations,patients,slot);
                //startActivity(new Intent(ConfirmeRdvActivity.this, ProfileActivity.class));
                break;
            case R.id.imgAnnuler:
                startActivity(new Intent(ConfirmeRdvActivity.this, DetailActivity.class));
                break;
            case R.id.btnAnnuleRdv:
                startActivity(new Intent(ConfirmeRdvActivity.this, PatientRendezVousActivity.class));
                break;
        }

    }
}