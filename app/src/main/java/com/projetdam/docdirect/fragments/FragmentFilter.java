package com.projetdam.docdirect.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.projetdam.docdirect.R;

public class FragmentFilter extends DialogFragment {

    public FragmentFilter() {
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
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

    public boolean[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(boolean[] selectedItems) {
        this.selectedItems = selectedItems;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        boolean[] checkedItems = args.getBooleanArray("checked");
        String[] specialties = args.getStringArray("specialties");

        selectedItems = new boolean[specialties.length];

        // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set the dialog title
        builder.setTitle(R.string.choisir_spe)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(specialties, checkedItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                // If the user checked the item, add it to the selected items
                                selectedItems[which] = isChecked;
                            }
                        })
                // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                        listener.onDialogPositiveClick(FragmentFilter.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(FragmentFilter.this);
                    }
                });
        View view = getActivity().getLayoutInflater().inflate(R.layout.view_filter, null);
        builder.setView(view);

        return builder.create();
    }

}
