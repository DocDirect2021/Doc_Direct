package com.projetdam.docdirect;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
import com.projetdam.docdirect.commons.NodesNames;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.util.ArrayList;
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
        TextView tv=findViewById(R.id.tvDocRdv);
        ImageView iv=findViewById(R.id.ivDocRdv);
        tv.setText(getIntent().getStringExtra(NodesNames.KEY_NOM));
        RequestOptions options = new RequestOptions().centerCrop()
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher);
        Context context = iv.getContext();
        Glide.with(context).load(getIntent().getStringExtra(NodesNames.KEY_AVATAR)).apply(options).fitCenter().circleCrop().override(50,50).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv);

        mList = new ArrayList<>();
        // get appointement patient
        ArrayList<String> nestedList = UtilsTimeSlot.getSlots("09:30", "18:15", 20);

        for (String hr : nestedList) {
            Log.i(TAG, "onCreate: " + hr);
        }
        mList.add(new RdvInformation(nestedList, "30/11/2021"));
        mList.add(new RdvInformation(nestedList, "01/12/2021"));
        adapter = new PatientRdvAdapter(mList);
        recyclerViewRdv.setAdapter(adapter);
        //setOnClickListner();

    }


//    private void setOnClickListner() {
//        listener = new PatientRdvAdapter.RecyclerViewClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                v.findViewById(R.id.btnItem1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(listener != null ){
//                            switch (v.getId()) {
//                                case R.id.btnItem1:
//                                    //Intent intent = new Intent(getApplicationContext(), ConfirmeRdvActivity.class);
//                                    Intent intent = new Intent(PatientRendezVousActivity.this, ConfirmeRdvActivity.class);
//                                    intent.putExtra("jour", mList.get(position).getJour());
//                                    intent.putExtra("startTime", mList.get(position).getNastedList().get(0));
//                                    startActivity(intent);
//                                    break;
//                                case R.id.btnItem2:
//                                    break;
//                            }
//
//                        }
//
//                    }
//                });
//                Toast.makeText(PatientRendezVousActivity.this, "je suis ds le setOnClickListner" + position, Toast.LENGTH_SHORT).show();
//
//
//            }
//        };
//
//    }
}