package com.projetdam.docdirect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.ModelTimeSlot;
import com.projetdam.docdirect.commons.RdvInformation;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.util.List;

public class PatientRdvAdapter extends RecyclerView.Adapter<PatientRdvAdapter.PatientRdvsHolder> {

    private Context parentContext;
    private ModelDoctor doctor;
    private String patientId;
    private List<RdvInformation> mList;
    private RdvInformation rdvInfo;
    private Intent intent;

    public PatientRdvAdapter(Context context, List<RdvInformation> mList, ModelDoctor doctor, String patientId) {
        this.parentContext = context;
        this.mList = mList;
        this.doctor = doctor;
        this.patientId = patientId;
    }

    @NonNull
    @Override
    public PatientRdvsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parentContext);
        View view = inflater.inflate(R.layout.row_patient_rdv, parent, false);
        return new PatientRdvsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientRdvsHolder holder, int position) {
        rdvInfo = mList.get(position);
        holder.dateAppointement.setText(rdvInfo.getJour());

        for (int i = 0; i < holder.btnHour.length; i++) {
            holder.btnHour[i].setText(rdvInfo.getNastedList().get(i));
        }

        boolean isExpandable = rdvInfo.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable) {
            holder.mArrowImage.setImageResource(R.drawable.ic_keyboard_arrow_up);
        } else {
            holder.mArrowImage.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdvInfo.setExpandable(!rdvInfo.isExpandable());
                notifyItemChanged(holder.getBindingAdapterPosition()); //.getAdapterPosition()
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
        private Button[] btnHour = new Button[24];

        public PatientRdvsHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.lyAfficheAppointement);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            dateAppointement = itemView.findViewById(R.id.dateAppointement);
            mArrowImage = itemView.findViewById(R.id.mArrowImage);

            btnHour[0] = itemView.findViewById(R.id.btnItem1);
            btnHour[1] = itemView.findViewById(R.id.btnItem2);
            btnHour[2] = itemView.findViewById(R.id.btnItem3);
            btnHour[3] = itemView.findViewById(R.id.btnItem4);
            btnHour[4] = itemView.findViewById(R.id.btnItem5);
            btnHour[5] = itemView.findViewById(R.id.btnItem6);
            btnHour[6] = itemView.findViewById(R.id.btnItem7);
            btnHour[7] = itemView.findViewById(R.id.btnItem8);
            btnHour[8] = itemView.findViewById(R.id.btnItem9);
            btnHour[9] = itemView.findViewById(R.id.btnItem10);
            btnHour[10] = itemView.findViewById(R.id.btnItem11);
            btnHour[11] = itemView.findViewById(R.id.btnItem12);
            btnHour[12] = itemView.findViewById(R.id.btnItem13);
            btnHour[13] = itemView.findViewById(R.id.btnItem14);
            btnHour[14] = itemView.findViewById(R.id.btnItem15);
            btnHour[15] = itemView.findViewById(R.id.btnItem16);
            btnHour[16] = itemView.findViewById(R.id.btnItem17);
            btnHour[17] = itemView.findViewById(R.id.btnItem18);
            btnHour[18] = itemView.findViewById(R.id.btnItem19);
            btnHour[19] = itemView.findViewById(R.id.btnItem20);
            btnHour[20] = itemView.findViewById(R.id.btnItem21);
            btnHour[21] = itemView.findViewById(R.id.btnItem22);
            btnHour[22] = itemView.findViewById(R.id.btnItem23);
            btnHour[23] = itemView.findViewById(R.id.btnItem24);

            AlertDialog.Builder builder = new AlertDialog.Builder(parentContext);
            for (int i = 0; i < btnHour.length; i++) {
                btnHour[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hour = ((Button) v).getText().toString();

                        builder.setTitle("Votre rdv : " + rdvInfo.getJour())
                                .setMessage("à : " + hour + " avec le Dr " + doctor.getName());
                        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(parentContext, "Rendez-vous confirmé !", Toast.LENGTH_LONG).show();
                                confirmRdv(rdvInfo.getJour(), hour);
                            }
                        });
                        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(parentContext, "Rendez-vous annulé !", Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void confirmRdv(String date, String hour) {
        ModelTimeSlot rdv =
                new ModelTimeSlot(doctor.getDoctorId(), patientId, date, hour, "", false);
        UtilsTimeSlot.saveRdv(rdv);
    }
}
