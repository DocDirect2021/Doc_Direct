package com.projetdam.docdirect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import java.util.Comparator;
import java.util.concurrent.Executor;


public class FragmentAccueil extends Fragment implements OnMapReadyCallback, FilterFragment.NoticeDialogListener {

    private ImageButton imageButton;
    private SearchView rechercheView;
    private GoogleMap mMap;
    private FirebaseFirestore db;
    private ArrayList<ModelDoctor> listDoc;
    private boolean[] listeco;
    private RecyclerView recyclerView;
    private SupportMapFragment mapFragment;
    private AdapterDoctor adapterDoctor;
    private FusedLocationProviderClient fusedLocationClient;
    Query query;


    public void init() {

        db = FirebaseFirestore.getInstance();
        query = db.collection("doctors").whereEqualTo("city", "Paris");
        listeco =new boolean[getResources().getStringArray(R.array.specialites).length];;


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

    public FragmentAccueil(FusedLocationProviderClient fusedLocationClient) {
        this.fusedLocationClient = fusedLocationClient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Gol.addLog(emplacement, "onCreateView");
        // Inflate the layout for this fragment
        listDoc = new ArrayList<ModelDoctor>();
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        imageButton = view.findViewById(R.id.imageButton);
        rechercheView = view.findViewById(R.id.rechercheView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        adapterDoctor = new AdapterDoctor(view.getContext(), listDoc);
        recyclerView.setAdapter(adapterDoctor);
                recyclerView.setLayoutManager(llm);
                adapterDoctor.setOnItemClickListener(new AdapterDoctor.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos, View v) {
                        //playSong(pos);
                    }
                });





        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        }
        mMap.setMyLocationEnabled(true);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @SuppressLint({"NewApi", "NotifyDataSetChanged"})
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            LatLng paris = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 12.0f));
                            init();
                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        ModelDoctor doc = documentSnapshot.toObject(ModelDoctor.class);
                                        listDoc.add(doc);




                                    }
                                    listDoc.sort(new Comparator<ModelDoctor>() {
                                        @Override
                                        public int compare(ModelDoctor modelDoctor, ModelDoctor t1) {
                                            Location loc1 = new Location("d1");
                                            loc1.setLatitude(modelDoctor.getGeoloc().getLatitude());
                                            loc1.setLongitude(modelDoctor.getGeoloc().getLongitude());

                                            Location loc2 = new Location("d2");
                                            loc1.setLatitude(t1.getGeoloc().getLatitude());
                                            loc1.setLongitude(t1.getGeoloc().getLongitude());
                                            if (location.distanceTo(loc1) < location.distanceTo(loc2))
                                                return -1;
                                            else if (location.distanceTo(loc1) > location.distanceTo(loc2))
                                                return 1;
                                            else
                                                return 0;
                                        }
                                    });

                                    Log.i("tg", "onSuccess: " + listDoc.toString());
                                    adapterDoctor.notifyDataSetChanged();

                                }
                            });

                        }
                    }
                });







        FilterFragment f = new FilterFragment();

        Bundle args = new Bundle();
        args.putBooleanArray("checked",listeco);
        f.setArguments(args);





        rechercheView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName()!=null&&doc.getName().contains(s)){
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

                ArrayList<String> moTestArray =new ArrayList(Arrays.asList(dialog.getResources().getStringArray(R.array.specialites)));

                ArrayList<String> listec;
                listec=new ArrayList<String>();
                FilterFragment ff=(FilterFragment) dialog;
                listeco=ff.getSelectedItems();
                for(int i=0;i<dialog.getResources().getStringArray(R.array.specialites).length;i++)
                    if(listeco[i])
                        listec.add(dialog.getResources().getStringArray(R.array.specialites)[i]);




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