package com.projetdam.docdirect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

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
import java.util.Arrays;


public class SearchActivity extends FragmentActivity implements OnMapReadyCallback,FilterFragment.NoticeDialogListener {

    private ImageButton imageButton;
    private SearchView rechercheView;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private FirebaseFirestore db;
    private CollectionReference docRef;
    private ArrayList<ModelDoctor> listDoc;
    private ArrayList listeco;
    FilterFragment f;

    public void init()
    {
        db= FirebaseFirestore.getInstance();
        listDoc=new ArrayList<ModelDoctor>();
        docRef=db.collection("doctors");
        imageButton=findViewById(R.id.imageButton);
        rechercheView=findViewById(R.id.rechercheView);
        f=new FilterFragment();
        listeco=new ArrayList<Integer>();

        docRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                    ModelDoctor doc=documentSnapshot.toObject(ModelDoctor.class);
                    listDoc.add(doc);

                }

            }
        });



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        init();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);







    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;

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
                        mMap.addMarker(new MarkerOptions().position(paris).title(""));
                    }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName().contains(s)){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        mMap.addMarker(new MarkerOptions().position(paris).title(""));
                    }
                return false;
            }
        });




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();

                args.putIntegerArrayList("checked",listeco);
                f.setArguments(args);
                f.show(getSupportFragmentManager(),"Filter");


            }
        });


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        mMap.clear();
        ArrayList moTestArray =new ArrayList(Arrays.asList(getResources().getStringArray(R.array.specialites)));

        ArrayList<String> listec;
        listec=new ArrayList<>();
        FilterFragment ff=(FilterFragment) dialog;
        listeco=ff.getSelectedItems();
        for(Object i:ff.getSelectedItems())
            listec.add((String)moTestArray.get((Integer)i));
       Log.i("TAG", "onDialogPositiveClick: "+moTestArray.toString());
        for(ModelDoctor doc:listDoc)
            if(listec.contains(doc.getSpeciality())){
                LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                mMap.addMarker(new MarkerOptions().position(paris).title(""));
            }


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}