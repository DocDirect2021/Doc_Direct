package com.projetdam.docdirect;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class FilterFragment extends DialogFragment {

    public FilterFragment() {
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    NoticeDialogListener listener;
    private boolean[] checkeditems;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(
                     " must implement NoticeDialogListener");
        }

    }



    private ArrayList selectedItems;

    public ArrayList getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ArrayList selectedItems) {
        this.selectedItems = selectedItems;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        selectedItems=new ArrayList();
        checkeditems= new boolean[getResources().getStringArray(R.array.specialites).length];
        Bundle args=getArguments();
        ArrayList<Integer> chkditm=args.getIntegerArrayList("checked");
        chkditm.toArray(new Integer[0]);
        for(int i=0;i<getResources().getStringArray(R.array.specialites).length;i++)
            if(chkditm.contains(i))
                checkeditems[i]=true;
            else
                checkeditems[i]=false;

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
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    selectedItems.remove(which);
                                }
                            }
                        })
                // Set the action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the selectedItems results somewhere
                        // or return them to the component that opened the dialog
                        listener.onDialogPositiveClick(FilterFragment.this);

                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(FilterFragment.this);
                    }
                });

        return builder.create();
    }


}
