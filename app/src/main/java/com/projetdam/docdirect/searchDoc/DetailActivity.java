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
import com.projetdam.docdirect.PriseRdvActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.VisioActivity;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.ModelDoctor;

public class DetailActivity extends AppCompatActivity {

    ImageView ivAvatar;
    TextView tvNom, tvSkill, tvAdresse, tvVille, tvLikes;
    Uri affiche;
    Button btnRdv, btnVisio;
    ModelDoctor doctor = AppSingleton.getInstance().getPickedDoctor();

    public void init() {
        ivAvatar = findViewById(R.id.ivAfficheDetail);
        tvNom = findViewById(R.id.tvTitleDetail);
        tvSkill = findViewById(R.id.tvSkill);
        tvAdresse = findViewById(R.id.tvActeurDetail);
        tvVille = findViewById(R.id.tvVille);
        tvLikes = findViewById(R.id.tvAnneeDetail);
        btnRdv = findViewById(R.id.button2);
        btnVisio = findViewById(R.id.button3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
//        doctor = getIntent().getParcelableExtra("doctor");

        String titre = "Dr " + doctor.getFirstname() + " " + doctor.getName();
        affiche = doctor.getAvatar();

        tvSkill.setText(doctor.getSpeciality());
        tvAdresse.setText(doctor.getHousenumber() + " " + doctor.getStreet());
        tvVille.setText(doctor.getPostcode() + " " + doctor.getCity());
        tvNom.setText(titre);

        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);
        Context context = ivAvatar.getContext();
        Glide.with(context).load(affiche).apply(options).fitCenter().circleCrop().override(350, 350).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivAvatar);
        btnRdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, PriseRdvActivity.class);
//                intent.putExtra("doctor", doctor);
                startActivity(intent);
            }
        });

        btnVisio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vIntent = new Intent(DetailActivity.this, VisioActivity.class);
                startActivity(vIntent);
            }
        });
    }
}