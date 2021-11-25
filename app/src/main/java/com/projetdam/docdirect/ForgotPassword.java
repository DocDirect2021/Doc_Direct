package com.projetdam.docdirect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    /** var **/
    private EditText edMailForgotPass;
    private Button btnResetPass;
    private ProgressBar progressBar;

    FirebaseAuth auth;
    /** initialiser les widgets **/
    private void init() {
        edMailForgotPass = findViewById(R.id.edMailForgotPass);
        btnResetPass = findViewById(R.id.btnResetPass);
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        String email = edMailForgotPass.getText().toString().trim();

        if(email.isEmpty()){
            edMailForgotPass.setError("Email doit etre renseigner !");
            edMailForgotPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edMailForgotPass.setError("entrer un email correct svp !");
            edMailForgotPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "un mail est envoyé pour renisialiser votre passeword",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "essayer encore une fois, un problème est survenu !",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}