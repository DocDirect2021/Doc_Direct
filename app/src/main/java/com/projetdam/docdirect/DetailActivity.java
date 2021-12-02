package com.projetdam.docdirect;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projetdam.docdirect.commons.NodesNames;

public class DetailActivity extends AppCompatActivity {

    ImageView ivAvatar;
    TextView tvNom;
    TextView tvAdresse;
    TextView tvLikes;
    Uri affiche;
    Button rdv,visio;

    String titre,acteurs,synopsis,iddoc;
    int Likes;

    public void init() {
        ivAvatar = findViewById(R.id.ivAfficheDetail);
        tvNom = findViewById(R.id.tvTitleDetail);
        tvAdresse = findViewById(R.id.tvActeurDetail);
        tvLikes = findViewById(R.id.tvAnneeDetail);
        rdv=findViewById(R.id.button2);
        visio=findViewById(R.id.button3);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        Intent intent=getIntent();

        titre="Dr. "+intent.getStringExtra(NodesNames.KEY_NOM)+" "+intent.getStringExtra(NodesNames.KEY_PRENOM);
        tvNom.setText(titre);
        tvAdresse.setText(intent.getStringExtra(NodesNames.KEY_TELEPHONE));
        affiche= Uri.parse(intent.getStringExtra(NodesNames.KEY_AVATAR));
        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);
        Context context = ivAvatar.getContext();
        Glide.with(context).load(affiche).apply(options).fitCenter().circleCrop().override(350,350).diskCacheStrategy(DiskCacheStrategy.ALL).into(ivAvatar);
    rdv.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent nintent = new Intent(DetailActivity.this, RdvActivity.class);
            nintent.putExtra(NodesNames.KEY_ID,getIntent().getStringExtra(NodesNames.KEY_ID));
            startActivity(nintent);

        }
    });


    }
}