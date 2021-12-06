package com.projetdam.docdirect;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.projetdam.docdirect.commons.ModelSosMessage;

public class FragmentUrgence extends Fragment {

    ModelSosMessage msg = new ModelSosMessage();

    private Button btnSaveMsg;
    private EditText etMessage;

    private View rootView;

    public FragmentUrgence() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_urgence, container, false);

        etMessage = rootView.findViewById(R.id.etMessage);
        btnSaveMsg = rootView.findViewById(R.id.btnSaveMsg);

        btnSaveMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        msg.getPatientDocument().get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String text;
                            text = documentSnapshot.getString("sos_text");
                            etMessage.setText(text);
                        }
                    }
                });

    }

    private void saveMessage() {
        msg.setSosText(etMessage.getText().toString());
        msg.saveMessage();

        Toast.makeText(this.getContext(), "Message enregistré...", Toast.LENGTH_SHORT).show();
    }
}