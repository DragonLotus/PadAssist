package com.padassist.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

/**
 * Created by Anthony on 7/13/2015.
 */
public class DeleteMonsterConfirmationDialogFragment extends DialogFragment {
    public interface ResetLayout {
        public void resetLayout(int position);
    }

    private ResetLayout reset;
    private int position;

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Delete monster?");
        alertDialogBuilder.setMessage("This will delete all instances of the saved monster. This action cannot be undone.");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reset.resetLayout(position);
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }

    public static DeleteMonsterConfirmationDialogFragment newInstance(ResetLayout resetVariable, int position) {
        DeleteMonsterConfirmationDialogFragment dialogFragment = new DeleteMonsterConfirmationDialogFragment();
        dialogFragment.setResetLayout(resetVariable, position);
        return dialogFragment;
    }

    public void setResetLayout(ResetLayout reset, int position) {
        this.reset = reset;
        this.position = position;
    }

    public void show(FragmentManager manager, int position, String tag) {
        super.show(manager, tag);
        this.position = position;
    }
}
