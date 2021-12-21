package com.projetdam.docdirect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.ModelHistRdv;
import com.projetdam.docdirect.commons.ModelTimeSlot;
import com.projetdam.docdirect.commons.UtilsTimeSlot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdapterHistoRdv extends RecyclerView.Adapter<AdapterHistoRdv.AdapterHistoRdvHolder> {

    private ArrayList<ModelHistRdv> listHistRdv;
    private String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ModelHistRdv HistRdv;
    private Context context;

    int position;

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
                String docnameArdv = HistoRdvHolder.txtNameDoc.getText().toString();
                String docSpArdv = HistoRdvHolder.txtSpecialite.getText().toString();
                String docId = HistoRdvHolder.docId.getText().toString();

                position = HistoRdvHolder.getBindingAdapterPosition();

                builder.setTitle("Votre rdv : " + HistRdv.getDate())
                        .setMessage("à : " + HistRdv.getStartTime() + " avec le Dr " + HistRdv.getName());
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Votre rendez-vous est annulé !", Toast.LENGTH_LONG).show();
                        annulerRdv(docId, dateArdv, timeArdv);
                        listHistRdv.remove(position);
                        notifyItemRemoved(position);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AdapterHistoRdvHolder holder, int position) {
        HistRdv = listHistRdv.get(position);
        holder.txtJour.setText(HistRdv.getDate());
        holder.txtNameDoc.setText(HistRdv.getFirstname() + " " + HistRdv.getName());
        holder.txtSpecialite.setText(HistRdv.getSpeciality());
        holder.txtStartTime.setText(HistRdv.getStartTime());
        holder.docId.setText(HistRdv.getDoctorId());
        String dateRdv = HistRdv.getDate() + " " + HistRdv.getStartTime();

        //if dateRdv > dateJour afficher le bouton imgAnnule
        LocalDateTime today = LocalDateTime.now();     //Today
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String todaytxt = today.format(formatters);
        String stringToday = todaytxt.replaceAll("-", "/");
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd h:m");
        try {
            if (formatDate.parse(stringToday).before(formatDate.parse(dateRdv))) {
                holder.imgAnnuler.setVisibility(View.VISIBLE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

        private TextView docId;

        public AdapterHistoRdvHolder(@NonNull View itemView) {
            super(itemView);
            txtJour = itemView.findViewById(R.id.txtJour);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtNameDoc = itemView.findViewById(R.id.txtNameDoc);
            txtSpecialite = itemView.findViewById(R.id.txtSpecialite);
            // imgDoc = itemView.findViewById(R.id.imgDoc);
            imgAnnuler = itemView.findViewById(R.id.imgAnnuler);
            docId = itemView.findViewById(R.id.txtDocId);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void annulerRdv(String docId, String date, String hour) {
        ModelTimeSlot rdv =
                new ModelTimeSlot(docId, patientId, date, hour, "", false, "", "", "");
        Log.e("onSuccess update", docId);
        UtilsTimeSlot.annulRdv(rdv);
//        listHistRdv.remove(position);
//        notifyItemRemoved(position);
    }

}
