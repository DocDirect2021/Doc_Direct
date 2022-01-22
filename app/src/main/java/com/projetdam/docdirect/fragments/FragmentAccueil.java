package com.projetdam.docdirect.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.adapter.AdapterDoctor;
import com.projetdam.docdirect.commons.AddSampleDatasToFirebase;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.searchDoc.DetailActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class FragmentAccueil extends Fragment implements OnMapReadyCallback, FragmentFilter.NoticeDialogListener {

    private RecyclerView rcvDoctorList;
    private ImageButton filterButton;
    private SearchView rechercheView;
    private GoogleMap mMap;
    private ArrayList<ModelDoctor> listDoc;
    private AdapterDoctor adapterDoctor;
    private Query query = AppSingleton.doctors.whereEqualTo("city", "Paris");
    private String[] specialties;
    private boolean[] listChk;

    private void init() {
        specialties = getResources().getStringArray(R.array.specialites);
        listChk = new boolean[specialties.length];
    }

    public FragmentAccueil() {
    }     // Required empty public constructor

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        init();

        // Inflate the layout for this fragment
        listDoc = new ArrayList<ModelDoctor>();
        View view = inflater.inflate(R.layout.fragment_accueil, container, false);
        filterButton = view.findViewById(R.id.imageButton);
        rechercheView = view.findViewById(R.id.rechercheView);
        rcvDoctorList = view.findViewById(R.id.rcvDoctorList);
        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        adapterDoctor = new AdapterDoctor(view.getContext(), listDoc);
        rcvDoctorList.setAdapter(adapterDoctor);
        rcvDoctorList.setLayoutManager(llm);
        adapterDoctor.setOnItemClickListener(new AdapterDoctor.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                AppSingleton.getInstance().setPickedDoctor(listDoc.get(pos));
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentFilter fragFilter = new FragmentFilter();
                Bundle args = new Bundle();
                args.putBooleanArray("checked", listChk);
                args.putStringArray("specialties", specialties);
                fragFilter.setArguments(args);
                fragFilter.show(getChildFragmentManager(), "Filter");
            }
        });

        rechercheView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;    // traité avant sur onQueryTextChange
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mMap.clear();
                if (s.isEmpty()) return false;
                LatLng coordo;
                for (ModelDoctor doc : listDoc)
                    if (doc.getName() != null && doc.getName().contains(s.toUpperCase())) {
                        coordo = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                        Marker m = mMap.addMarker(new MarkerOptions()
                                .position(coordo)
                                .title(doc.getCity())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        m.setTag(doc);
                    }
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
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

        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull Marker marker) {
                AppSingleton.getInstance().setPickedDoctor((ModelDoctor) (marker.getTag()));
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                startActivity(intent);
            }
        });

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Nullable
            @Override
            public View getInfoContents(@NonNull Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.activity_detail, null);

                TextView tvNom = v.findViewById(R.id.tvTitleDetail);
                TextView tvSkill = v.findViewById(R.id.tvSkill);
                ImageView ivAvatar = v.findViewById(R.id.ivAfficheDetail);
                TextView tvAdresse = v.findViewById(R.id.tvActeurDetail);
                TextView tvVille = v.findViewById(R.id.tvVille);

                ModelDoctor md = (ModelDoctor) (marker.getTag());
                tvNom.setText(MessageFormat.format("Dr {0} {1}", md.getFirstname(), md.getName()));
                tvSkill.setText(md.getSpeciality());
                tvAdresse.setText(MessageFormat.format("{0} {1}", md.getHousenumber(), md.getStreet()));
                tvVille.setText(MessageFormat.format("{0} {1}", md.getPostcode(), md.getCity()));

                RequestOptions options = new RequestOptions().centerCrop()
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher);
                Glide.with(getContext()).load(md.getAvatar()).apply(options).fitCenter().circleCrop().override(80, 80)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(ivAvatar);

                return v;
            }

            @Nullable
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null;
            }
        });

        mMap.setMyLocationEnabled(true);

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @SuppressLint({"NewApi", "NotifyDataSetChanged"})
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            LatLng coordo = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordo, 12.0f));
                            ArrayList<Uri> avatars = AddSampleDatasToFirebase.addDatasToFireBase(getContext());

                            query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                        ModelDoctor doc = documentSnapshot.toObject(ModelDoctor.class);

                                        doc.setAvatar(avatars.get((listDoc.size()) % avatars.size()));
                                        Location loc = new Location("d2");
                                        loc.setLatitude(doc.getGeoloc().getLatitude());
                                        loc.setLongitude(doc.getGeoloc().getLongitude());
                                        doc.setDistance(location.distanceTo(loc));
                                        listDoc.add(doc);
                                    }

                                    listDoc.sort(new Comparator<ModelDoctor>() {
                                        @Override
                                        public int compare(ModelDoctor doc1, ModelDoctor doc2) {
                                            Location loc1 = new Location("d1");
                                            loc1.setLatitude(doc1.getGeoloc().getLatitude());
                                            loc1.setLongitude(doc1.getGeoloc().getLongitude());

                                            Location loc2 = new Location("d2");
                                            loc2.setLatitude(doc2.getGeoloc().getLatitude());
                                            loc2.setLongitude(doc2.getGeoloc().getLongitude());

                                            return Float.compare(location.distanceTo(loc1), location.distanceTo(loc2));
                                        }
                                    });
                                    adapterDoctor.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        mMap.clear();

        ArrayList<String> listSpe = new ArrayList<String>();
        FragmentFilter fragFilter = (FragmentFilter) dialog;
        listChk = fragFilter.getSelectedItems();
        for (int i = 0; i < specialties.length; i++)
            if (listChk[i]) listSpe.add(specialties[i]);

        for (ModelDoctor doc : listDoc)
            if (listSpe.contains(doc.getSpeciality())) {
                LatLng coordo = new LatLng(doc.getGeoloc().getLatitude(), doc.getGeoloc().getLongitude());
                Marker m = mMap.addMarker(
                        new MarkerOptions()
                                .position(coordo)
//                                .title(doc.getName() + "~" + doc.getFirstname())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                );
                m.setTag(doc);
            }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }
}