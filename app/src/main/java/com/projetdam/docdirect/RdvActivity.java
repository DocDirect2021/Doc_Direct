package com.projetdam.docdirect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.projetdam.docdirect.commons.NodesNames;

public class RdvActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rdv);
        TextView tv=(TextView)findViewById(R.id.textView4);
        tv.setText(getIntent().getStringExtra(NodesNames.KEY_ID));
    }
}