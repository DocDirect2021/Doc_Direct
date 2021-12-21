package com.projetdam.docdirect.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.adapter.AdapterHistoRdv;
import com.projetdam.docdirect.commons.ModelHistRdv;

import java.util.ArrayList;
import java.util.List;

public class FragmentRendezVous extends Fragment {

    /*   VAR */
    private RecyclerView histoRdvRecyView;
    private ArrayList<ModelHistRdv> dataHolder = new ArrayList<>();
    private ModelHistRdv HistRdv = new ModelHistRdv();
    private AdapterHistoRdv adapter;
    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private View view;

    public FragmentRendezVous() {
    }

    @Override
    public void onAttach(@NonNull @Nullable Context context) {
        super.onAttach(context);
        //Gol.addLog(emplacement, "onAttach");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onStart() {
        super.onStart();
        //ArrayList<ModelHistRdv> listHistRdv = new ArrayList<>();

        CollectionReference patients = FirebaseFirestore.getInstance().collection("users");
        Query docRef = patients.document(patientId).collection("rdvs");
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : snapshotList) {
                    HistRdv = snapshot.toObject(ModelHistRdv.class);
                    dataHolder.add(HistRdv);

                }

                //dataHolder.add(new ModelHistRdv(listHistRdv));
                adapter = new AdapterHistoRdv(getContext(), dataHolder);
                histoRdvRecyView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LOGGER", "get failed with ", e);

                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_rendez_vous, container, false);
        histoRdvRecyView = view.findViewById(R.id.histoRdvRecyView);
        histoRdvRecyView.setHasFixedSize(true);
        histoRdvRecyView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}