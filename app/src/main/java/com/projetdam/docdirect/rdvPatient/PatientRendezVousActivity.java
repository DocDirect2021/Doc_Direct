package com.projetdam.docdirect.rdvPatient;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.adapter.PatientRdvAdapter;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.ModelTimeSlot;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected void onStart() {
        super.onStart();
        mList = new ArrayList<>();
        ModelTimeSlot rdv =
                new ModelTimeSlot(doctor.getDoctorId(), patientId, "", "", "", false, doctor.getFirstname(),doctor.getName(),doctor.getSpeciality());

        /****/
        Map<String, ArrayList<String>> myMaps = new HashMap<String, ArrayList<String>>();
        CollectionReference consultations = FirebaseFirestore.getInstance().collection("consultations");
        String doctorId = rdv.getDoctorId();


        Query docRef = consultations.document(doctorId).collection("slots");

        docRef.whereEqualTo("patientId","").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String key="";
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot : snapshotList)
                {

                    key = snapshot.getData().get("date").toString();

                    if (myMaps.containsKey(key)) {
                        myMaps.get(key).add(snapshot.getData().get("startTime").toString());
                    }else{
                        ArrayList<String> val = new ArrayList<>();
                        val.add(snapshot.getData().get("startTime").toString());
                        myMaps.put(key, val);
                    }
                }
                for (Map.Entry<String, ArrayList<String>> me : myMaps.entrySet()) {

                    String jour = me.getKey();
                    mList.add(new RdvInformation(jour,myMaps));
                }
                adapter = new PatientRdvAdapter(PatientRendezVousActivity.this, mList, doctor, patientId);
                recyclerViewRdv.setAdapter(adapter);
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LOGGER", "get failed with ", e);

                    }
                });
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

    }

}