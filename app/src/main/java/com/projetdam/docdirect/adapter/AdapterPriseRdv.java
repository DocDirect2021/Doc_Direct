package com.projetdam.docdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
                btnSlot[j].setText(slots.get(j + 3 * i));
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

    public class DaySlotViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TableLayout table_slots;

        public DaySlotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            table_slots = itemView.findViewById(R.id.table_slots);
        }
    }
}
