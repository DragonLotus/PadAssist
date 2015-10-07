package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;


import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;
public class SortElementDialogFragment extends DialogFragment {

    public interface SortBy{
        public void sortElement1();
        public void sortElement2();
    }

    private RadioGroup choiceRadioGroup;
    private SortBy sortBy;

    public static SortElementDialogFragment newInstance(SortBy sortBy){
        SortElementDialogFragment dialogFragment = new SortElementDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_element_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        builder.setTitle("Sort by Element");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.element1) {
                            sortBy.sortElement1();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.element2) {
                            sortBy.sortElement2();
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }
}
