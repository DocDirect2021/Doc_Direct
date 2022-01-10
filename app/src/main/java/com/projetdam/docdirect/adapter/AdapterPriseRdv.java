package com.projetdam.docdirect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.ModelDayPlanner;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.ModelTimeSlot;

import java.time.LocalTime;
import java.util.ArrayList;

public class AdapterPriseRdv extends RecyclerView.Adapter<AdapterPriseRdv.DaySlotViewHolder> {
    Context context;
    ArrayList<ModelDayPlanner> hours;
    ModelTimeSlot rdv;
    private final ModelDoctor doctor = AppSingleton.getInstance().getPickedDoctor();
    private final String patientId = AppSingleton.getInstance().getPatientId();

    public AdapterPriseRdv(Context context, ArrayList<ModelDayPlanner> hours) {
        this.context = context;
        this.hours = hours;
    }

    @NonNull
    @Override
    public DaySlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_prise_rdv, parent, false);
        return new DaySlotViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DaySlotViewHolder holder, int position) {
        ModelDayPlanner daySlots = hours.get(position);
        ArrayList<String> slots = daySlots.getOpenSlots();
        int slotCount = slots.size();
        int slotCountBy3 = (slotCount - 1) / 3;
        String date = daySlots.getDisplayDate();
        holder.tvDate.setText(date);

        Button[] btnSlot = new Button[3];
        for (int i = 0; i <= slotCountBy3; i++) {
            TableRow tableRow = (TableRow) LayoutInflater.from(context)
                    .inflate(R.layout.row_three_slots, null);
            btnSlot[0] = tableRow.findViewById(R.id.btnSlot1);
            btnSlot[1] = tableRow.findViewById(R.id.btnSlot2);
            btnSlot[2] = tableRow.findViewById(R.id.btnSlot3);
            for (int j = 0; j < 3; j++) {
                if (j + 3 * i >= slotCount) {
                    btnSlot[j].setVisibility(View.INVISIBLE);
                    continue;
                }
                String hour = slots.get(j + 3 * i);
                btnSlot[j].setText(hour);
                btnSlot[j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String hour = ((Button) v).getText().toString();
                        rdv = new ModelTimeSlot(doctor.getDoctorId(), patientId, daySlots.getDate(), LocalTime.parse(hour), false);
                        AlertDialog dialog = getBuilder(date, hour).create();
                        dialog.show();
                    }
                });
            }
            holder.table_slots.addView(tableRow);
        }

        holder.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int img;
                if (holder.table_slots.getVisibility() != View.GONE) {
                    holder.table_slots.setVisibility(View.GONE);
                    img = R.drawable.ic_keyboard_arrow_down;
                } else {
                    holder.table_slots.setVisibility(View.VISIBLE);
                    img = R.drawable.ic_keyboard_arrow_up;
                }
                ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0, img, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    private AlertDialog.Builder getBuilder(String date, String hour) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Votre rdv : " + date)
                .setMessage("à " + hour + " avec le Dr " + doctor.getName() + " " + doctor.getFirstname());

        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Rendez-vous confirmé !", Toast.LENGTH_LONG).show();
                rdv.save();
            }
        });

        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Rendez-vous annulé !", Toast.LENGTH_LONG).show();
            }
        });

        return builder;
    }

    public static class DaySlotViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TableLayout table_slots;


        public DaySlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            table_slots = itemView.findViewById(R.id.table_slots);
        }
    }
}
