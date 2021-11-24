package com.projetdam.docdirect;

import androidx.fragment.app.FragmentActivity;

import android.media.metrics.LogSessionId;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseFirestore db;
    private CollectionReference docRef;
    private ArrayList<ModelDoctor> listDoc;



    public void init()
    {
        db= FirebaseFirestore.getInstance();
        listDoc=new ArrayList<ModelDoctor>();
        docRef=db.collection("doctors");




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    ModelDoctor doc=documentSnapshot.toObject(ModelDoctor.class);
                    Double lat=doc.getGeoloc().getLatitude();
                    Double log=doc.getGeoloc().getLongitude();
                    Log.i("cc", "onSuccess: "+doc.getGeoloc());
                    LatLng pt = new LatLng(lat, log);
                    mMap.addMarker(new MarkerOptions().position(pt).title("Doc"));
                    listDoc.add(doc);

                }

            }
        });
         //Add a marker in Paris and move the camera
        LatLng paris = new LatLng(48.864716, 2.349014);
        mMap.addMarker(new MarkerOptions().position(paris).title("Marker in Paris"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));

    }
}