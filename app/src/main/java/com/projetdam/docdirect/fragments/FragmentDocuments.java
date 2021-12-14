package com.projetdam.docdirect.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;


import com.projetdam.docdirect.R;

public class FragmentDocuments extends Fragment {

    private ImageView scannedImageView;


    private static final int REQUEST_CODE = 99;
    public FragmentDocuments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_documents, container, false);






        return v;
    }

    




}