package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;

import com.padassist.R;


public class UpToDateDialogFragment extends DialogFragment {

    private CheckBox showAgainCheck;
    private UpToDateDialogFragment.Preferences preferences;

    public interface Preferences {
        public void setShowAgain(boolean showAgain);

    }

    public static UpToDateDialogFragment newInstance(UpToDateDialogFragment.Preferences preferences) {
        UpToDateDialogFragment dialogFragment = new UpToDateDialogFragment();
        dialogFragment.setPreferences(preferences);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_up_to_date_dialog, null);
        showAgainCheck = (CheckBox) rootView.findViewById(R.id.showAgainCheck);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        builder.setTitle("Sync complete");
        builder.setMessage("Assets are up to date.");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                preferences.setShowAgain(showAgainCheck.isChecked());
                dismiss();
            }
        });
        return builder.create();
    }

    private void setPreferences(UpToDateDialogFragment.Preferences preferences){
        this.preferences = preferences;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
