package com.projetdam.docdirect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.Objects;

public class FilterFragment extends DialogFragment  {



    public FilterFragment() {
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    NoticeDialogListener listener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
           // throw new ClassCastException(" must implement NoticeDialogListener");
        }

    }



    private boolean[] selectedItems;
    private boolean[] checkeditems;

    public boolean[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(boolean []selectedItems) {
        this.selectedItems = selectedItems;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedItems=new boolean[getResources().getStringArray(R.array.specialites).length];

        Bundle args=getArguments();
        checkeditems=args.getBooleanArray("checked");

          // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.choisir_spe)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(R.array.specialites, checkeditems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                  // If the user checked the item, add it to the selected items
                                    selectedItems[which]=isChecked;
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                        listener.onDialogPositiveClick(FilterFragment.this);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(FilterFragment.this);
                    }
                });
        View v = getActivity().getLayoutInflater().inflate(R.layout.activity_filter,null);
        builder.setView(v);


        return builder.create();
    }


}
