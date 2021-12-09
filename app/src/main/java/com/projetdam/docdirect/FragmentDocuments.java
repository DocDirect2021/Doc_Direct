package com.projetdam.docdirect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;


public class FragmentDocuments extends Fragment {



    public FragmentDocuments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_documents, container, false);

        Intent intent = new Intent(getActivity(), ScanActivity.class);
        intent.putExtra(ScanActivity.EXTRA_BRAND_IMG_RES, R.drawable.ic_crop_white_24dp); // Set image for title icon - optional
        intent.putExtra(ScanActivity.EXTRA_TITLE, "Crop Document"); // Set title in action Bar - optional
        intent.putExtra(ScanActivity.EXTRA_ACTION_BAR_COLOR, R.color.white); // Set title color - optional
        intent.putExtra(ScanActivity.EXTRA_LANGUAGE, "en"); // Set language - optional
        startActivity(intent);
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_CAMERA;
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivity(intent);


        return v;
    }
}