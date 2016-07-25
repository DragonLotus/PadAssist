package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class DisclaimerDialogFragment extends DialogFragment {
    public interface Dismiss{
        public void dismiss();
    }

    private Boolean showing;
    private Dismiss dismiss;

    public static DisclaimerDialogFragment newInstance(Dismiss dismiss) {
        DisclaimerDialogFragment dialogFragment = new DisclaimerDialogFragment();
        dialogFragment.setDismiss(dismiss);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Disclaimer");
        builder.setMessage("This application and its authors are not affiliated with GungHo Online. " +
                "We take no responsibility for the disparities that might occur between PadAssist and Puzzle & Dragons or for your actions taken within the game. " +
                "Use this application at your own risk.\n\n" +
                "All related images are registered trademarks and owned by GungHo Online Entertainment, Inc.");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss.dismiss();
                dismiss();
            }
        });
        showing = true;
        return builder.create();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        showing = false;
    }

    public void setDismiss(Dismiss dismiss) {
        this.dismiss = dismiss;
    }

    public Boolean getShowing() {
        return showing;
    }

    public void setShowing(Boolean showing) {
        this.showing = showing;
    }
}
