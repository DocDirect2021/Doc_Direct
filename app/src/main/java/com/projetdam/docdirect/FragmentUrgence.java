package com.projetdam.docdirect;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentUrgence extends Fragment {
    private static final String TAG = "TEST TEST TEST";

    private ModelSosMessage msg = new ModelSosMessage();
    private ArrayList<ModelRecipient> recipients = new ArrayList<>();

    private Button btnSaveMsg, btnSendMsg;
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
        btnSendMsg = rootView.findViewById(R.id.btnSendMsg);
        recyclerView = rootView.findViewById(R.id.rcvRecipients);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_urgence, container, false);

        init();
        if (checkPermission()) {
            queryContacts();
        }

        btnSaveMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMessage();
                Collections.sort(recipients);
                adapter.notifyDataSetChanged();
            }
        });

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(llm);
        adapter = new RecipientAdapter(rootView.getContext(), recipients);
        recyclerView.setAdapter(adapter);

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

                            HashSet<Long> ids = new HashSet<Long>((Collection<? extends Long>) documentSnapshot.get("sos_contact_id"));
                            msg.setContactIds(ids);

                            // on coche les contacts selectionnés
                            ModelRecipient recipient;
                            for (int i = 0; i < recipients.size(); i++) {
                                recipient = recipients.get(i);
                                Long id = recipient.getContactId();
                                if (ids.contains(id)) {
                                    recipient.setChecked(true);
                                }
                            }
                            Collections.sort(recipients);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void saveMessage() {
        msg.setSosText(etMessage.getText().toString());
        msg.saveMessage();

        msg.saveRecipients(adapter.getSelectedIds());

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
                        new ModelRecipient(cursor.getString(1), "", cursor.getString(2), cursor.getLong(0))
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

    private void sendMail() {
        String to = "pont.du.pont@yopmail.com";
        String subject = "SOS";
        String text = etMessage.getText().toString();

        ArrayList<String> recip = new ArrayList<>();
        for (ModelRecipient r : recipients) {
            if (r.isChecked()) {
                recip.add(r.getEmail());
            }
        }
        String[] sendTo = recip.toArray(new String[0]);

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, sendTo);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, text);
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Client Email :"));
    }
}