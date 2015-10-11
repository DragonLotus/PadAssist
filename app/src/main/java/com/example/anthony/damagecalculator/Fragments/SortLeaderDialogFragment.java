package com.example.anthony.damagecalculator.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;

import com.example.anthony.damagecalculator.R;

public class SortLeaderDialogFragment extends DialogFragment {

    public interface SortBy {
        public void sortElement();

        public void sortType();

        public void sortStats();

        public void sortPlus();

        public void sortRarity();
    }

    private RadioGroup choiceRadioGroup;
    private SortBy sortBy;

    public static SortLeaderDialogFragment newInstance(SortBy sortBy) {
        SortLeaderDialogFragment dialogFragment = new SortLeaderDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_leader_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        builder.setTitle("Sort by Leader attributes");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.element) {
                            sortBy.sortElement();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.type) {
                            sortBy.sortType();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.stats) {
                            sortBy.sortStats();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.plus) {
                            sortBy.sortPlus();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.rarity) {
                            sortBy.sortRarity();
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
