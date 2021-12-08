package com.projetdam.docdirect;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.projetdam.docdirect.adapter.RecipientAdapter;
import com.projetdam.docdirect.commons.ModelRecipient;
import com.projetdam.docdirect.commons.ModelSosMessage;

import java.util.ArrayList;

public class FragmentUrgence extends Fragment {
    private static final String TAG = "TEST TEST TEST";

    private ModelSosMessage msg = new ModelSosMessage();
    private ArrayList<ModelRecipient> recipients;

    private Button btnSaveMsg;
    private EditText etMessage;
    private RecyclerView recyclerView;

    private View rootView;

    private RecipientAdapter adapter;

    public FragmentUrgence() {
        // Required empty public constructor
    }

    private void init() {
        etMessage = rootView.findViewById(R.id.etMessage);
        btnSaveMsg = rootView.findViewById(R.id.btnSaveMsg);
        recyclerView = rootView.findViewById(R.id.rcvRecipients);

        recipients = new ArrayList<>();
        Log.i(TAG, "init: " + recipients.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_urgence, container, false);

        init();

        btnSaveMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(llm);
        adapter = new RecipientAdapter(rootView.getContext(), recipients);
        recyclerView.setAdapter(adapter);

        if (checkPermission()) {
            queryContacts();
        }
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

    private void queryContacts() {
        String[] Projection = new String[]{
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.CommonDataKinds.Email.DISPLAY_NAME_PRIMARY,
                ContactsContract.CommonDataKinds.Email.ADDRESS};

        Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

        ArrayList<String> nameList = new ArrayList<>();
        ContentResolver cr = getContext().getContentResolver();

        Cursor cursor = cr.query(
                uri,
                Projection,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                recipients.add(
                        new ModelRecipient(cursor.getString(1), "", cursor.getString(2), cursor.getInt(0))
                );
            }
        }
    }


    private boolean checkPermission() {
        int READ_CONTACTS = ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.READ_CONTACTS);
        if (READ_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 0);
            return false;
        }
        return true;
    }
}