package com.projetdam.docdirect.searchDoc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.VisioActivity;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.NodesNames;
import com.projetdam.docdirect.rdvPatient.PatientRendezVousActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView ivAvatar;
    TextView tvNom;
    TextView tvAdresse;
    TextView tvLikes;
    Uri affiche;
    Button rdv, visio;
    ModelDoctor doctor;

    String titre;

    public void init() {
        ivAvatar = findViewById(R.id.ivAfficheDetail);
        tvNom = findViewById(R.id.tvTitleDetail);
        tvAdresse = findViewById(R.id.tvActeurDetail);
        tvLikes = findViewById(R.id.tvAnneeDetail);
        rdv = findViewById(R.id.button2);
        visio = findViewById(R.id.button3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        Intent intent = getIntent();
        doctor = intent.getParcelableExtra("doctor");

        titre = "Dr. " + doctor.getName() + " " + doctor.getFirstname();
        affiche = doctor.getAvatar();

        tvAdresse.setText(doctor.getStreet());
        tvNom.setText(titre);

        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);
        Context context = ivAvatar.getContext();
        Glide.with(context).load(affiche).apply(options).fitCenter().circleCrop().override(350, 350).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivAvatar);
        rdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nintent = new Intent(DetailActivity.this, PatientRendezVousActivity.class);
                nintent.putExtra("doctor", doctor);
                startActivity(nintent);
            }
        });

        visio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vintent = new Intent(DetailActivity.this, VisioActivity.class);
                startActivity(vintent);
            }
        });
    }
}