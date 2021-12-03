package com.projetdam.docdirect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;


public class FragmentCompte extends Fragment {

    /** var **/

    private EditText txtNom;
    private EditText txtPrenom;
    private EditText txtPhone;
    private EditText dn;
    private EditText mail;
    private Button update;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser firebaseUser;
    private String userID;
    private DocumentReference docref;
    View view;





    public FragmentCompte() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_compte, container, false);
        txtNom = view.findViewById(R.id.edNom);
        txtPrenom=view.findViewById(R.id.edPrenom);
        txtPhone=view.findViewById(R.id.edTelephone);
        mail=view.findViewById(R.id.edEmail);
        dn=view.findViewById(R.id.edDatNaiss);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        userID=firebaseUser.getUid();
        update=view.findViewById(R.id.updateUser);
        docref=db.collection("users").document(userID);


docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

        txtNom.setText(task.getResult().getString("nom"));
        txtPrenom.setText(task.getResult().getString("prenom"));
        txtPhone.setText(task.getResult().getString("telephone"));
        mail.setText(task.getResult().getString("email"));
        dn.setText(task.getResult().getString("dateNAiss"));

    }
});


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();

            }
        });




        return view;
    }

    public void updateProfile(){
        Map<String,String> profile=new HashMap<>();
        profile.put("nom",txtNom.getText().toString());
        profile.put("prenom",txtPrenom.getText().toString());
        profile.put("telephone",txtPhone.getText().toString());
        profile.put("email",mail.getText().toString());
        profile.put("dateNAiss",dn.getText().toString());

docref.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void unused) {
        Toast.makeText(getActivity(),"profile updated",Toast.LENGTH_SHORT);
    }
});

    }



}