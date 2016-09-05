package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;

import com.padassist.R;


public class CloseDialogFragment extends DialogFragment {

    private CheckBox showAgainCheck;
    private CloseApplication closeApplication;

    public interface CloseApplication {
        public void quit();

    }

    public static CloseDialogFragment newInstance(CloseApplication closeApplication) {
        CloseDialogFragment dialogFragment = new CloseDialogFragment();
        dialogFragment.setCloseApplication(closeApplication);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Close Application?");
//        builder.setMessage("Durr");
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dismiss();
                closeApplication.quit();
            }
        });
        return builder.create();
    }

    private void setCloseApplication(CloseApplication closeApplication){
        this.closeApplication = closeApplication;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
