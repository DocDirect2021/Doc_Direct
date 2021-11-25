package com.projetdam.docdirect;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class FragmentAccueil extends Fragment {



    public FragmentAccueil() {
        // Required empty public constructor
    }


        // Variable emplacement
        private static final String emplacement
                = FragmentAccueil.class.getSimpleName();

        @Override
        public void onAttach(@NonNull @Nullable Context context) {
            super.onAttach(context);
            //Gol.addLog(emplacement, "onAttach");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Gol.addLog(emplacement, "onCreateView");
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_accueil, container, false);
            TextView txtNom = view.findViewById(R.id.txtNom);
            return view;
        }

        @Override
        public void onStart() {
            // Gol.addLog(emplacement, "onStart");
            super.onStart();
        }

        @Override
        public void onResume() {
            // Gol.addLog(emplacement, "onResume");
            super.onResume();
        }

        @Override
        public void onPause() {
            //Gol.addLog(emplacement, "onPause");
            super.onPause();
        }

        @Override
        public void onStop() {
            //Gol.addLog(emplacement, "onStop");
            super.onStop();
        }

        @Override
        public void onDestroyView() {
            //Gol.addLog(emplacement, "onDestroyView");
            super.onDestroyView();
        }

        @Override
        public void onDestroy() {
            //Gol.addLog(emplacement, "onDestroy");
            super.onDestroy();
        }

        @Override
        public void onDetach() {
            //Gol.addLog(emplacement, "onDetach");
            super.onDetach();
        }
    }