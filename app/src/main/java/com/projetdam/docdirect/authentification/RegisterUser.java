package com.projetdam.docdirect.authentification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.projetdam.docdirect.MainActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.NodesNames;

import java.util.HashMap;
import java.util.Map;

    public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

        /** var **/
        private static final String TAG = "RegisterUser";

        private TextView banner, registerUser;
        private EditText edNom, edPrenom, edDatNaiss, edTelephone, edEmail, edPassword;
        private ProgressBar progressBar;
        private FirebaseAuth mAuth;
        FirebaseFirestore db ;
        private String userID;
        private DocumentReference noteRef;

        /** initialiser les widgets **/
        private void init() {
            registerUser = findViewById(R.id.registerUser);
            registerUser.setOnClickListener(this);
            edNom =findViewById(R.id.edNom);
            edPrenom = findViewById(R.id.edPrenom);
            edDatNaiss = findViewById(R.id.edDatNaiss);
            edTelephone = findViewById(R.id.edTelephone);
            edEmail = findViewById(R.id.edEmail);
            edPassword = findViewById(R.id.edPassword);
            progressBar = findViewById(R.id.progressBar);

            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();


        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register_user);
            init();


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.banner:
                    startActivity(new Intent(RegisterUser.this, MainActivity.class));
                    break;
                case R.id.registerUser:
                    registerUser();
                    break;
            }

        }

        private void registerUser() {
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String nom = edNom.getText().toString().trim();
            String prenom = edPrenom.getText().toString().trim();
            String dateNaiss = edDatNaiss.getText().toString().trim();
            String telephone = edTelephone.getText().toString().trim();

            if(nom.isEmpty()){
                edNom.setError("name is required !");
                edNom.requestFocus();
                return;
            }
            if(prenom.isEmpty()){
                edPrenom.setError("prenom is required !");
                edPrenom.requestFocus();
                return;
            }
            if(email.isEmpty()){
                edEmail.setError("email is required !");
                edEmail.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                edEmail.setError("entrer un email correct svp !");
                edEmail.requestFocus();
                return;
            }
            if(password.isEmpty()){
                edPassword.setError("password is required !");
                edPassword.requestFocus();
                return;
            }
            if(password.length() < 6){
                edPassword.setError("minimum 6 caractères pour le password !");
                edPassword.requestFocus();
                return;
            }
            if(dateNaiss.isEmpty()){
                edDatNaiss.setError("date naissance is required !");
                edDatNaiss.requestFocus();
                return;
            }
            if(telephone.isEmpty()){
                edTelephone.setError("telephone is required !");
                edTelephone.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Map<String, Object> user = new HashMap<>();
                                user.put(NodesNames.KEY_NOM, nom);
                                user.put(NodesNames.KEY_PRENOM, prenom);
                                user.put(NodesNames.KEY_DateNaiss, dateNaiss);
                                user.put(NodesNames.KEY_TELEPHONE, telephone);
                                user.put(NodesNames.KEY_EMAIL, email);
                                db = FirebaseFirestore.getInstance();
                                userID = mAuth.getCurrentUser().getUid();
                                noteRef = db.document("users/"+userID);

                                //Add a new document with ID Authentification
                                noteRef.set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + userID);
                                                Toast.makeText(RegisterUser.this, "utilisateur ajouter", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterUser.this, MainActivity.class));

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error adding document", e);
                                                Toast.makeText(RegisterUser.this, "Probleme : utilisateur non ajouter", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        });
                                // Add a new document with a generated ID
//                    db.collection("users")
//                            .add(user)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                                    Toast.makeText(RegisterUser.this, "utilisateur ajouter", Toast.LENGTH_SHORT).show();
//                                    progressBar.setVisibility(View.GONE);
//                                    startActivity(new Intent(RegisterUser.this, MainActivity.class));
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w(TAG, "Error adding document", e);
//                                    Toast.makeText(RegisterUser.this, "Probleme : utilisateur non ajouter", Toast.LENGTH_SHORT).show();
//                                    progressBar.setVisibility(View.GONE);
//                                }
//                            });
                            }
                        }
                    });
        }
    }
