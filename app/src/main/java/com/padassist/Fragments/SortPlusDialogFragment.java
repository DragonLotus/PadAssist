package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.RadioGroup;

import com.padassist.R;

public class SortPlusDialogFragment extends DialogFragment {

    public interface SortBy{
        public void sortTotal();
        public void sortHp();
        public void sortAtk();
        public void sortRcv();
    }

    private RadioGroup choiceRadioGroup;
    private SortBy sortBy;

    public static SortPlusDialogFragment newInstance(SortBy sortBy){
        SortPlusDialogFragment dialogFragment = new SortPlusDialogFragment();
        dialogFragment.setSortBy(sortBy);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_sort_plus_dialog, null);
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
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.rcv) {
                            sortBy.sortRcv();
                        }  else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.total) {
                            sortBy.sortTotal();
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
