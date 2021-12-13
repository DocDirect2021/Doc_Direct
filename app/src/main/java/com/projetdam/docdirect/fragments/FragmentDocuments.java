package com.projetdam.docdirect.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.IOException;


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

        Intent intent = new Intent(getActivity(),DocActivity.class);
        intent.putExtra(ScanActivity.EXTRA_BRAND_IMG_RES, R.drawable.ic_crop_white_24dp); // Set image for title icon - optional
        intent.putExtra(ScanActivity.EXTRA_TITLE, "Crop Document"); // Set title in action Bar - optional
        intent.putExtra(ScanActivity.EXTRA_ACTION_BAR_COLOR, R.color.white); // Set title color - optional
        intent.putExtra(ScanActivity.EXTRA_LANGUAGE, "en"); // Set language - optional
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE,ScanConstants.OPEN_CAMERA);
        startActivity(intent);
        int REQUEST_CODE = 99;
        startActivityForResult(intent, REQUEST_CODE);




        return v;
    }

    




}