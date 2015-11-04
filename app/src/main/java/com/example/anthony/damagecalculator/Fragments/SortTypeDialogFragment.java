package com.example.anthony.damagecalculator.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.example.anthony.damagecalculator.R;

public class SortTypeDialogFragment extends DialogFragment {

    public interface SortBy{
        public void sortType1();
        public void sortType2();
        public void sortType3();
    }

    private RadioGroup choiceRadioGroup;
    private SortBy sortBy;

    public static SortTypeDialogFragment newInstance(SortBy sortBy){
        SortTypeDialogFragment dialogFragment = new SortTypeDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_type_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        builder.setTitle("Sort by Type");
        builder.setMessage("Default order is Balanced, Physical, Healer, Dragon, God, Attacker, Devil, Machine, Evo Material, Awoken Material, Enhance Material");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.type1) {
                            sortBy.sortType1();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.type2) {
                            sortBy.sortType2();
                        }else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.type3) {
                            sortBy.sortType3();
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
