package com.padassist.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Anthony on 7/13/2015.
 * lol right...
 */
public class ClearTeamConfirmationDialogFragment extends DialogFragment {
    public interface ResetLayout {
        public void resetLayout();
    }

    private ResetLayout reset;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Clear Team?");
        alertDialogBuilder.setMessage("Current changes will not be saved.");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reset.resetLayout();
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public static ClearTeamConfirmationDialogFragment newInstance(ResetLayout resetVariable) {
        ClearTeamConfirmationDialogFragment dialogFragment = new ClearTeamConfirmationDialogFragment();
        dialogFragment.setResetLayout(resetVariable);
        return dialogFragment;
    }

    public void setResetLayout(ResetLayout reset) {
        this.reset = reset;
    }


}
