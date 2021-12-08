package com.projetdam.docdirect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projetdam.docdirect.R;
import com.projetdam.docdirect.commons.ModelRecipient;
import com.projetdam.docdirect.commons.ModelSosMessage;

import java.util.ArrayList;

public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.RecipientViewHolder> {
    // vars globales
    Context context;
    ArrayList<ModelRecipient> recipients;

    public RecipientAdapter(Context context, ArrayList<ModelRecipient> recipients) {
        this.context = context;
        this.recipients = recipients;
    }

    @NonNull
    @Override
    public RecipientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_recipient, parent, false);
        return new RecipientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipientViewHolder holder, int position) {
        ModelRecipient recipient = recipients.get(position);
        holder.tvContactName.setText(recipient.getName());
        holder.tvContactEmail.setText(recipient.getEmail());
    }

    @Override
    public int getItemCount() {
        return recipients.size();
    }

    public class RecipientViewHolder extends RecyclerView.ViewHolder {
        TextView tvContactName, tvContactEmail;
        CheckBox chkAdded;

        public RecipientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContactName = itemView.findViewById(R.id.tvContactName);
            tvContactEmail = itemView.findViewById(R.id.tvContactEmail);
            chkAdded = itemView.findViewById(R.id.chkAdded);

            // checkbox click event handling
            chkAdded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean chk) {
                    if (chk) {
                        Toast.makeText(context, "C'est coché !", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
