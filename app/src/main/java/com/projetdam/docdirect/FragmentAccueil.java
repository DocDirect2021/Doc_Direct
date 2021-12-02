package com.projetdam.docdirect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.NodesNames;

import java.util.ArrayList;
import java.util.Comparator;


public class FragmentAccueil extends Fragment implements OnMapReadyCallback, FilterFragment.NoticeDialogListener {

    private ImageButton imageButton;
    private SearchView rechercheView;
    private GoogleMap mMap;
    private ArrayList<ModelDoctor> listDoc;
    private boolean[] listeco;
    private AdapterDoctor adapterDoctor;
    private Query query;
    RecyclerView recyclerView;


    public void init() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        query = db.collection("doctors").whereEqualTo("city", "Paris");
        listeco =new boolean[getResources().getStringArray(R.array.specialites).length];



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
        listDoc = new ArrayList<ModelDoctor>();
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        imageButton = view.findViewById(R.id.imageButton);
        rechercheView = view.findViewById(R.id.rechercheView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);

       // FirestoreRecyclerOptions<ModelDoctor> products=new FirestoreRecyclerOptions.Builder<ModelDoctor>().setQuery(query,ModelDoctor.class).build();
        adapterDoctor = new AdapterDoctor(view.getContext(), listDoc);
        recyclerView.setAdapter(adapterDoctor);
                recyclerView.setLayoutManager(llm);
                adapterDoctor.setOnItemClickListener(new AdapterDoctor.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos) {
                        Intent intent = new Intent(getActivity(), DetailActivity.class);
                        intent.putExtra(NodesNames.KEY_NOM,listDoc.get(pos).getName());
                        intent.putExtra(NodesNames.KEY_PRENOM,listDoc.get(pos).getFirstname());
                        intent.putExtra(NodesNames.KEY_TELEPHONE,listDoc.get(pos).getPhone());
                        intent.putExtra(NodesNames.KEY_AVATAR,listDoc.get(pos).getAvatar().toString());
                        startActivity(intent);
                    }



                });


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                ModelDoctor md=(ModelDoctor)(marker.getTag());
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(NodesNames.KEY_NOM,md.getName());
                intent.putExtra(NodesNames.KEY_PRENOM,md.getFirstname());
                intent.putExtra(NodesNames.KEY_TELEPHONE,md.getPhone());
                intent.putExtra(NodesNames.KEY_AVATAR,md.getAvatar().toString());
                startActivity(intent);

            }
        });
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.activity_detail, null);

                TextView markerLabel = (TextView) v.findViewById(R.id.tvTitleDetail);
                TextView markerSpe =(TextView) v.findViewById(R.id.tvActeurDetail);
                ImageView markerImage=(ImageView) v.findViewById(R.id.ivAfficheDetail);
                ModelDoctor md=(ModelDoctor)(marker.getTag());
                markerLabel.setText(md.getName());
                markerSpe.setText(md.getSpeciality());

                RequestOptions options = new RequestOptions().centerCrop()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher);
                Context context = getContext();
                Glide.with(context).load(md.getAvatar()).apply(options).fitCenter().circleCrop().override(150,150).diskCacheStrategy(DiskCacheStrategy.ALL).into(markerImage);



                return v;
            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }
        });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return;
        }
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
                           ArrayList<Uri> al=AddSampleDatasToFirebase.addDatasToFireBase(getContext());

                            init();
                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        ModelDoctor doc = documentSnapshot.toObject(ModelDoctor.class);

                                        doc.setAvatar(al.get((listDoc.size())%al.size()));
                                        Location loc = new Location("d2");
                                        loc.setLatitude(doc.getGeoloc().getLatitude());
                                        loc.setLongitude(doc.getGeoloc().getLongitude());
                                        doc.setDistance(location.distanceTo(loc));
                                        listDoc.add(doc);




                                    }
                                    listDoc.sort(new Comparator<ModelDoctor>() {
                                        @Override
                                        public int compare(ModelDoctor modelDoctor, ModelDoctor t1) {
                                            Location loc1 = new Location("d1");
                                            loc1.setLatitude(modelDoctor.getGeoloc().getLatitude());
                                            loc1.setLongitude(modelDoctor.getGeoloc().getLongitude());

                                            Location loc2 = new Location("d2");
                                            loc2.setLatitude(t1.getGeoloc().getLatitude());
                                            loc2.setLongitude(t1.getGeoloc().getLongitude());
                                            return Float.compare(location.distanceTo(loc1), location.distanceTo(loc2));
                                        }
                                    });


                                    adapterDoctor.notifyDataSetChanged();

                                }
                            });

                        }
                    }

                });



        rechercheView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName()!=null&&doc.getName().contains(s)){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        Marker m=mMap.addMarker(new MarkerOptions().position(paris).title(doc.getCity()));
                        m.setTag(doc);
                    }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mMap.clear();
                for(ModelDoctor doc:listDoc)
                    if(doc.getName()!=null&&doc.getName().contains(s)){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        Marker m=mMap.addMarker(new MarkerOptions().position(paris).title(doc.getCity()));
                        m.setTag(doc);
                    }
                return false;
            }
        });




        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FilterFragment f = new FilterFragment();
                Bundle args = new Bundle();
                args.putBooleanArray("checked",listeco);
                f.setArguments(args);
                f.show(getChildFragmentManager(),"Filter");




            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
                mMap.clear();

                ArrayList<String> listec=new ArrayList<String>();
                FilterFragment ff=(FilterFragment) dialog;
                listeco=ff.getSelectedItems();
                for(int i=0;i<dialog.getResources().getStringArray(R.array.specialites).length;i++)
                    if(listeco[i])
                        listec.add(dialog.getResources().getStringArray(R.array.specialites)[i]);




                for(ModelDoctor doc:listDoc)
                    if(listec.contains(doc.getSpeciality())){
                        LatLng paris = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        Marker m=mMap.addMarker(new MarkerOptions().position(paris).title(doc.getName()+" "+doc.getFirstname()));
                        m.setTag(doc);
                    }

            }




    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}