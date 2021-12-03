package com.projetdam.docdirect.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.projetdam.docdirect.ConfirmeRdvActivity;
import com.projetdam.docdirect.MainActivity;
import com.projetdam.docdirect.PatientRendezVousActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.authentification.ForgotPassword;
import com.projetdam.docdirect.authentification.RegisterUser;
import com.projetdam.docdirect.commons.NodesNames;
import com.projetdam.docdirect.commons.RdvInformation;

import java.util.ArrayList;
import java.util.List;

public class PatientRdvAdapter extends RecyclerView.Adapter<PatientRdvAdapter.PatientRdvsHolder> {


    private List<RdvInformation> mList;
    private List<String> list = new ArrayList<>();
    private RdvInformation model;
    private Intent intent;

    public PatientRdvAdapter(List<RdvInformation> mList) {
        this.mList = mList;

    }

    @NonNull
    @Override
    public PatientRdvsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_patient_rdv, parent, false);
        return new PatientRdvsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRdvsHolder holder, int position) {
        model = mList.get(position);
        holder.dateAppointement.setText(model.getJour());

        holder.mTxt1.setText(model.getNastedList().get(0));
        holder.mTxt2.setText(model.getNastedList().get(1));
        holder.mTxt3.setText(model.getNastedList().get(2));
        holder.mTxt4.setText(model.getNastedList().get(3));
        holder.mTxt5.setText(model.getNastedList().get(4));
        holder.mTxt6.setText(model.getNastedList().get(5));
        holder.mTxt7.setText(model.getNastedList().get(6));
        holder.mTxt8.setText(model.getNastedList().get(7));
        holder.mTxt9.setText(model.getNastedList().get(8));
        holder.mTxt10.setText(model.getNastedList().get(9));
        holder.mTxt11.setText(model.getNastedList().get(10));
        holder.mTxt12.setText(model.getNastedList().get(11));
        holder.mTxt13.setText(model.getNastedList().get(12));
        holder.mTxt14.setText(model.getNastedList().get(13));
        holder.mTxt15.setText(model.getNastedList().get(14));
        holder.mTxt16.setText(model.getNastedList().get(15));
        holder.mTxt17.setText(model.getNastedList().get(16));
        holder.mTxt18.setText(model.getNastedList().get(17));
        holder.mTxt19.setText(model.getNastedList().get(18));
        holder.mTxt20.setText(model.getNastedList().get(19));
        holder.mTxt21.setText(model.getNastedList().get(20));
        holder.mTxt22.setText(model.getNastedList().get(21));
        holder.mTxt23.setText(model.getNastedList().get(22));
        holder.mTxt24.setText(model.getNastedList().get(23));

        boolean isExpandable = model.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setExpandable(!model.isExpandable());
                list = model.getNastedList();
                notifyItemChanged(holder.getBindingAdapterPosition());//.getAdapterPosition()

            }
        });
    }


    @Override
    public int getItemCount() {

        return mList.size();
    }


    public class PatientRdvsHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private LinearLayout expandableLayout;
        private TextView dateAppointement;
        private ImageView mArrowImage;
        private Button mTxt1;
        private Button mTxt2;
        private Button mTxt3;
        private Button mTxt4;
        private Button mTxt5;
        private Button mTxt6;
        private Button mTxt7;
        private Button mTxt8;
        private Button mTxt9;
        private Button mTxt10;
        private Button mTxt11;
        private Button mTxt12;
        private Button mTxt13;
        private Button mTxt14;
        private Button mTxt15;
        private Button mTxt16;
        private Button mTxt17;
        private Button mTxt18;
        private Button mTxt19;
        private Button mTxt20;
        private Button mTxt21;
        private Button mTxt22;
        private Button mTxt23;
        private Button mTxt24;


        public PatientRdvsHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.lyAfficheAppointement);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            dateAppointement = itemView.findViewById(R.id.dateAppointement);
            mArrowImage = itemView.findViewById(R.id.mArrowImage);
            mTxt1 = itemView.findViewById(R.id.btnItem1);
            mTxt2 = itemView.findViewById(R.id.btnItem2);
            mTxt3 = itemView.findViewById(R.id.btnItem3);
            mTxt4 = itemView.findViewById(R.id.btnItem4);
            mTxt5 = itemView.findViewById(R.id.btnItem5);
            mTxt6 = itemView.findViewById(R.id.btnItem6);
            mTxt7 = itemView.findViewById(R.id.btnItem7);
            mTxt8 = itemView.findViewById(R.id.btnItem8);
            mTxt9 = itemView.findViewById(R.id.btnItem9);
            mTxt10 = itemView.findViewById(R.id.btnItem10);
            mTxt11 = itemView.findViewById(R.id.btnItem11);
            mTxt12 = itemView.findViewById(R.id.btnItem12);
            mTxt13 = itemView.findViewById(R.id.btnItem13);
            mTxt14 = itemView.findViewById(R.id.btnItem14);
            mTxt15 = itemView.findViewById(R.id.btnItem15);
            mTxt16 = itemView.findViewById(R.id.btnItem16);
            mTxt17 = itemView.findViewById(R.id.btnItem17);
            mTxt18 = itemView.findViewById(R.id.btnItem18);
            mTxt19 = itemView.findViewById(R.id.btnItem19);
            mTxt20 = itemView.findViewById(R.id.btnItem20);
            mTxt21 = itemView.findViewById(R.id.btnItem21);
            mTxt22 = itemView.findViewById(R.id.btnItem22);
            mTxt23 = itemView.findViewById(R.id.btnItem23);
            mTxt24 = itemView.findViewById(R.id.btnItem24);

            itemView.findViewById(R.id.btnItem1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e(NodesNames.KEY_START_TIME, "9:30");
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(0));
                    v.getContext().startActivity(intent);
                }
            });

            itemView.findViewById(R.id.btnItem2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(1));
                    v.getContext().startActivity(intent);
                }
            });
            itemView.findViewById(R.id.btnItem3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(2));
                    v.getContext().startActivity(intent);
                }
            });
            itemView.findViewById(R.id.btnItem4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(3));
                    v.getContext().startActivity(intent);
                }
            });
            itemView.findViewById(R.id.btnItem5).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(4));
                    v.getContext().startActivity(intent);
                }
            });
            itemView.findViewById(R.id.btnItem6).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ConfirmeRdvActivity.class);
                    intent.putExtra(NodesNames.KEY_JOUR, model.getJour());
                    intent.putExtra(NodesNames.KEY_START_TIME, model.getNastedList().get(5));
                    v.getContext().startActivity(intent);
                }
            });

        }

    }
}
