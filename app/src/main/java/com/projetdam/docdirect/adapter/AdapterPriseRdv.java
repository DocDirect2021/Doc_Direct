package com.projetdam.docdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.ModelDaySlots;

import java.util.ArrayList;

public class AdapterPriseRdv extends RecyclerView.Adapter<AdapterPriseRdv.DaySlotViewHolder> {
    Context context;
    ArrayList<ModelDaySlots> hours;

    public AdapterPriseRdv(Context context, ArrayList<ModelDaySlots> hours) {
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

    @Override
    public void onBindViewHolder(@NonNull DaySlotViewHolder holder, int position) {
        ModelDaySlots daySlots = hours.get(position);
        ArrayList<String> slots = daySlots.getSlots();
        int slotCount = slots.size();
        int slotCountBy3 = (slotCount - 1) / 3;
        holder.tvDate.setText(daySlots.getDate());

        for (int i = 0; i <= slotCountBy3; i++) {
            TableRow tableRow = (TableRow) LayoutInflater.from(context)
                    .inflate(R.layout.row_three_slots, null);
            Button button1 = tableRow.findViewById(R.id.btnSlot1);
            Button button2 = tableRow.findViewById(R.id.btnSlot2);
            Button button3 = tableRow.findViewById(R.id.btnSlot3);
            int j = 3 * i;
            if (j < slotCount) {
                button1.setText(slots.get(j));
            } else {
                button1.setVisibility(View.INVISIBLE);
            }
            if (++j < slotCount) {
                button2.setText(slots.get(j));
            } else {
                button2.setVisibility(View.INVISIBLE);
            }
            if (++j < slotCount) {
                button3.setText(slots.get(j));
            } else {
                button3.setVisibility(View.INVISIBLE);
            }
            holder.table_slots.addView(tableRow);
        }
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public class DaySlotViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TableLayout table_slots;
        private final TableRow tableRow;

        public DaySlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            table_slots = itemView.findViewById(R.id.table_slots);
            tableRow = table_slots.findViewById(R.id.txtDocId);
        }
    }
}
