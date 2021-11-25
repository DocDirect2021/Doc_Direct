package com.projetdam.docdirect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * var
     **/

    private TextView register, forgotPassword;
    private static final String TAG = "Main Activity";
    private EditText edEmailSignUp, edPasswordSignUp;
    private Button btSignIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    /**
     * Initialisation des composants
     **/
    public void init() {

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        btSignIn = findViewById(R.id.btSignIn);
        btSignIn.setOnClickListener(this);

        edEmailSignUp = findViewById(R.id.edEmailSignUp);
        edPasswordSignUp = findViewById(R.id.edPasswordSignUp);

        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // test ph
        TempMisc.retrieveDb();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(MainActivity.this, RegisterUser.class));
                break;
            case R.id.btSignIn:
                userLogin();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                break;
        }

    }

    private void userLogin() {
        String email = edEmailSignUp.getText().toString().trim();
        String password = edPasswordSignUp.getText().toString().trim();

        if (email.isEmpty()) {
            edEmailSignUp.setError("Email doit etre renseigner !");
            edEmailSignUp.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmailSignUp.setError("entrer un email correct svp !");
            edEmailSignUp.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edPasswordSignUp.setError("Email doit etre renseigner !");
            edPasswordSignUp.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edPasswordSignUp.setError("6 caractère min pour password !");
            edPasswordSignUp.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //if(user.isEmailVerified()){
                    // redirection vers la page profil
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    //}
                    //else{
//                        user.sendEmailVerification();
//                        Toast.makeText(MainActivity.this, "verifiez votre email", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(MainActivity.this, "Connexion echoué, merci de vérifier vos identifiants", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

