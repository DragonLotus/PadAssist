package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;

import com.padassist.R;


public class NoDropDialogFragment extends DialogFragment {

    private CheckBox showAgainCheck;
    private NoDropDialogFragment.Preferences preferences;

    public interface Preferences {
        public void setShowAgain(boolean showAgain);

    }

    public static NoDropDialogFragment newInstance(NoDropDialogFragment.Preferences preferences) {
        NoDropDialogFragment dialogFragment = new NoDropDialogFragment();
        dialogFragment.setPreferences(preferences);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_disclaimer_dialog, null);
        showAgainCheck = (CheckBox) rootView.findViewById(R.id.showAgainCheck);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        builder.setTitle("No Skyfall Disclaimer");
        builder.setMessage("No skyfalls currently has no error checking. Therefore, it is possible to have impossible combos. Please match accordingly for the most accurate results.");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                preferences.setShowAgain(showAgainCheck.isChecked());
                dismiss();
            }
        });
        return builder.create();
    }

    private void setPreferences(NoDropDialogFragment.Preferences preferences){
        this.preferences = preferences;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
