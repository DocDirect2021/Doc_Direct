package com.projetdam.docdirect;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.commons.ModelDoctor;


import java.util.ArrayList;
import java.util.Arrays;


public class FragmentAccueil extends Fragment implements OnMapReadyCallback,FilterFragment.NoticeDialogListener {

    private ImageButton imageButton;
    private SearchView rechercheView;
    private GoogleMap mMap;
    private FirebaseFirestore db;
    private ArrayList<ModelDoctor> listDoc;
    private ArrayList listeco;
    RecyclerView recyclerView;
    SupportMapFragment mapFragment;


    public void init() {

        db = FirebaseFirestore.getInstance();
        listDoc = new ArrayList<ModelDoctor>();
        Query query=db.collection("doctors").whereEqualTo("city","Paris");
        listeco = new ArrayList<Integer>();
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ModelDoctor doc = documentSnapshot.toObject(ModelDoctor.class);
                    listDoc.add(doc);

                }

            }
        });

    }



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
            imageButton=view.findViewById(R.id.imageButton);
            rechercheView=view.findViewById(R.id.rechercheView);
            recyclerView=view.findViewById(R.id.recyclerView);



            init();
            AdapterDoctor adapterSong=new AdapterDoctor(view.getContext(),listDoc);
            recyclerView.setAdapter(adapterSong);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            adapterSong.setOnItemClickListener(new AdapterDoctor.OnItemClickListener() {
                @Override
                public void onItemClick(int pos, View v) {
                    //playSong(pos);
                }
            });
            mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        FilterFragment f = new FilterFragment();

        Bundle args = new Bundle();
        args.putIntegerArrayList("checked",listeco);
        f.setArguments(args);


        LatLng paris = new LatLng(48.864716, 2.349014);

        mMap.addMarker(new MarkerOptions().position(paris).title("Marker in Paris"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 12.0f));



        rechercheView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName().contains(s)){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        mMap.addMarker(new MarkerOptions().position(paris).title(doc.getCity()));
                    }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName()!=null&&doc.getName().contains(s)){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        mMap.addMarker(new MarkerOptions().position(paris).title(doc.getCity()));
                    }
                return false;
            }
        });




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                f.show(getChildFragmentManager(),"Filter");




            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
                mMap.clear();

                ArrayList moTestArray =new ArrayList(Arrays.asList(dialog.getResources().getStringArray(R.array.specialites)));

                ArrayList<String> listec;
                listec=new ArrayList<>();
                FilterFragment ff=(FilterFragment) dialog;
                listeco=ff.getSelectedItems();
                for(Object i:ff.getSelectedItems())
                    listec.add((String)moTestArray.get((Integer)i));

                for(ModelDoctor doc:listDoc)
                    if(listec.contains(doc.getSpeciality())){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        Marker m=mMap.addMarker(new MarkerOptions().position(paris).title(doc.getName()+" "+doc.getFirstname()));

                    }

            }




    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}