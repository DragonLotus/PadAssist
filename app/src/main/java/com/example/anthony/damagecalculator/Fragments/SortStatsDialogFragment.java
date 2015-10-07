package com.example.anthony.damagecalculator.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;

import com.example.anthony.damagecalculator.R;

public class SortStatsDialogFragment extends DialogFragment {

    public interface SortBy{
        public void sortHp();
        public void sortAtk();
        public void sortRcv();
    }

    private RadioGroup choiceRadioGroup;
    private SortBy sortBy;

    public static SortStatsDialogFragment newInstance(SortBy sortBy){
        SortStatsDialogFragment dialogFragment = new SortStatsDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_stats_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        builder.setTitle("Sort by Stats");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.hp) {
                            sortBy.sortHp();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.atk) {
                            sortBy.sortAtk();
                        }else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.rcv) {
                            sortBy.sortRcv();
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
