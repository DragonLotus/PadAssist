package com.padassist.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Anthony on 7/13/2015.
 */
public class ReplaceAllConfirmationDialogFragment extends DialogFragment {
    public interface ResetLayout {
        public void resetLayout();
    }

    private ResetLayout reset;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Overwrite monster?");
        alertDialogBuilder.setMessage("This will overwrite all instances of the current monster with the selected monster. This monster will not be deleted.");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reset.resetLayout();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public static ReplaceAllConfirmationDialogFragment newInstance(ResetLayout resetVariable) {
        ReplaceAllConfirmationDialogFragment dialogFragment = new ReplaceAllConfirmationDialogFragment();
        dialogFragment.setResetLayout(resetVariable);
        return dialogFragment;
    }

    public void setResetLayout(ResetLayout reset) {
        this.reset = reset;
    }


}
