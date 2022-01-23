package com.projetdam.docdirect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.HelperTime;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.ModelTimeSlot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterListRdv extends RecyclerView.Adapter<AdapterListRdv.RdvViewHolder> {
    private final Context context;
    private ArrayList<ModelTimeSlot> patientRdvs = new ArrayList<>();

    public AdapterListRdv(Context context, ArrayList<ModelTimeSlot> patientRdvs) {
        this.context = context;
        this.patientRdvs = patientRdvs;
    }

    @NonNull
    @Override
    public RdvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_historique_rdv, parent, false);

        return new RdvViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RdvViewHolder holder, int position) {
        // set holder
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return patientRdvs.size();
    }

    private AlertDialog.Builder getBuilder(ModelTimeSlot rdv, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Votre rdv : " + "date")
                .setMessage("à " + "hour" + " avec le Dr " + "doctor");

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Rendez-vous annulé !", Toast.LENGTH_SHORT).show();
                rdv.delete();
                patientRdvs.remove(pos);
                notifyItemRemoved(pos);
            }
        });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Rendez-vous conservé !", Toast.LENGTH_SHORT).show();
            }
        });

        return builder;
    }

    public class RdvViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtJour, txtStartTime, txtNameDoc, txtSpeciality;
        private final ImageView imgDoc, imgAnnuler;

        public RdvViewHolder(@NonNull View itemView) {
            super(itemView);

            txtJour = itemView.findViewById(R.id.txtJour);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtNameDoc = itemView.findViewById(R.id.txtNameDoc);
            txtSpeciality = itemView.findViewById(R.id.txtSpecialite);
            imgDoc = itemView.findViewById(R.id.imgDoc);
            imgAnnuler = itemView.findViewById(R.id.imgAnnuler);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setData(int position) {
            ModelTimeSlot rdv = patientRdvs.get(position);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime rdvDT = HelperTime.getDateTime(rdv.getRdvDate());
            String date = rdvDT.toLocalDate().format(DateTimeFormatter.ofPattern("E dd/MM/yyyy", Locale.FRANCE));
            String time = rdvDT.toLocalTime().toString();
            txtJour.setText(date);
            txtStartTime.setText(time);
            imgDoc.setImageResource(R.drawable.doctor);

            AppSingleton.doctors.document(rdv.getDoctorId()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            ModelDoctor doctor = documentSnapshot.toObject(ModelDoctor.class);
                            String docName = "Dr " + doctor.getFirstname() + " " + doctor.getName();
                            txtNameDoc.setText(docName);
                            txtSpeciality.setText(doctor.getSpeciality());
                        }
                    });
            if (rdvDT.isBefore(now)) {
                imgAnnuler.setVisibility(View.INVISIBLE);
            } else {
                imgAnnuler.setVisibility(View.VISIBLE);
                imgAnnuler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog dialog = getBuilder(rdv, position).create();
                        dialog.show();
                    }
                });
            }
        }
    }
}
