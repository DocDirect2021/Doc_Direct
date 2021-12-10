package com.projetdam.docdirect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.projetdam.docdirect.ConfirmeRdvActivity;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.ModelHistRdv;
import com.projetdam.docdirect.commons.ModelTimeSlot;
import com.projetdam.docdirect.commons.UtilsTimeSlot;
import com.projetdam.docdirect.rdvPatient.PatientRendezVousActivity;
import com.projetdam.docdirect.searchDoc.DetailActivity;

import java.util.ArrayList;

public class AdapterHistoRdv extends RecyclerView.Adapter<AdapterHistoRdv.AdapterHistoRdvHolder> {

    private ArrayList<ModelHistRdv> listHistRdv;
    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ModelHistRdv HistRdv;
    private Context context;
    private String doctorId;


    public AdapterHistoRdv(Context context, ArrayList<ModelHistRdv> listHistRdv) {
        this.listHistRdv = listHistRdv;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterHistoRdvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_historique_rdv, parent, false);
        AdapterHistoRdvHolder HistoRdvHolder = new AdapterHistoRdvHolder(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        HistoRdvHolder.imgAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "test " + String.valueOf(HistoRdvHolder.getBindingAdapterPosition() + "" + HistoRdvHolder.txtStartTime.getText()), Toast.LENGTH_SHORT).show();
                String timeArdv = HistoRdvHolder.txtStartTime.getText().toString();
                String dateArdv = HistoRdvHolder.txtJour.getText().toString();
                String docIdArdv = HistoRdvHolder.txtNameDoc.getText().toString();
                String docSpArdv = HistoRdvHolder.txtSpecialite.getText().toString();

                builder.setTitle("Votre rdv : " + HistRdv.getDate())
                        .setMessage("à : " + HistRdv.getStartTime() + " avec le Dr " + HistRdv.getName());
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Rendez-vous annulé !", Toast.LENGTH_LONG).show();
                        annulerRdv(HistRdv.getDate(), HistRdv.getStartTime());
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Rendez-vous pas anuulé !", Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        return HistoRdvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoRdvHolder holder, int position) {
        HistRdv = listHistRdv.get(position);
        //holder.imgDoc.setImageResource(listHistRdv.get(position).getAvatar());
        holder.txtJour.setText(HistRdv.getDate());
        holder.txtNameDoc.setText(HistRdv.getFirstname() + " " + HistRdv.getName());
        holder.txtSpecialite.setText(HistRdv.getSpeciality());
        holder.txtStartTime.setText(HistRdv.getStartTime());
        //holder.imgAnnuler.setImageDrawable(Drawable.createFromPath("ic_delete"));

    }

    @Override
    public int getItemCount() {

        return listHistRdv.size();
    }


    class AdapterHistoRdvHolder extends RecyclerView.ViewHolder {
        private TextView txtJour;
        private TextView txtStartTime;
        // private ImageView imgDoc;
        private TextView txtNameDoc;
        private TextView txtSpecialite;
        private ImageView imgAnnuler;

        public AdapterHistoRdvHolder(@NonNull View itemView) {
            super(itemView);
            txtJour = itemView.findViewById(R.id.txtJour);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtNameDoc = itemView.findViewById(R.id.txtNameDoc);
            txtSpecialite = itemView.findViewById(R.id.txtSpecialite);
            // imgDoc = itemView.findViewById(R.id.imgDoc);
            imgAnnuler = itemView.findViewById(R.id.imgAnnuler);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void annulerRdv(String date, String hour) {
        ModelTimeSlot rdv =
                new ModelTimeSlot(HistRdv.getDoctorId(), patientId, HistRdv.getDate(), HistRdv.getStartTime(), "", false,HistRdv.getFirstname(), HistRdv.getName(),HistRdv.getSpeciality());
        UtilsTimeSlot.annulRdv(rdv);
    }

}
