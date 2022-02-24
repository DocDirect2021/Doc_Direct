package com.projetdam.docdirect.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.adapter.AdapterListRdv;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.ModelTimeSlot;

import java.util.ArrayList;

public class FragmentRdvList extends Fragment {
    private View rootView;
    private RecyclerView rcvRdvList;
    private AdapterListRdv adapter;

    private ArrayList<ModelTimeSlot> patientRdvs = new ArrayList<>();
    private final String patientId = AppSingleton.getInstance().getPatientId();
    private Query queryRdvs = AppSingleton.patients.document(patientId).collection("rdvs")
            .orderBy("rdvDate", Query.Direction.DESCENDING);

    public FragmentRdvList() {
        // Required empty public constructor
    }

    private void init() {
        rcvRdvList = rootView.findViewById(R.id.rcvRdvList);
        rcvRdvList.setLayoutManager(new LinearLayoutManager(getContext()));
//        rcvRdvList.setHasFixedSize(true);
        adapter = new AdapterListRdv(getContext(), patientRdvs);
        rcvRdvList.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        queryRdvs.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            try {
                                ModelTimeSlot rdv = snapshot.toObject(ModelTimeSlot.class);
                                patientRdvs.add(rdv);
                            } catch (Exception e) {
                                Toast.makeText(FragmentRdvList.this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_rdv_list, container, false);
        init();

        return rootView;
    }
}