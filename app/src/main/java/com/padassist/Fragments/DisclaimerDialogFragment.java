package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;

import com.padassist.R;


public class DisclaimerDialogFragment extends DialogFragment {

    private CheckBox showAgainCheck;
    private DisclaimerDialogFragment.Preferences preferences;

    public interface Preferences {
        public void setShowAgain(boolean showAgain);

    }

    public static DisclaimerDialogFragment newInstance(DisclaimerDialogFragment.Preferences preferences) {
        DisclaimerDialogFragment dialogFragment = new DisclaimerDialogFragment();
        dialogFragment.setPreferences(preferences);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View rootView = View.inflate(getActivity(), R.layout.fragment_disclaimer_dialog, null);
        showAgainCheck = (CheckBox) rootView.findViewById(R.id.showAgainCheck);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        builder.setTitle("Disclaimer");
        builder.setMessage("This application and its authors are not affiliated with GungHo Online. " +
                "We take no responsibility for the discrepancies that might occur between PadAssist and Puzzle & Dragons or for your actions taken within the game. " +
                "Use this application at your own risk.\n\n" +
                "All related images are registered trademarks and owned by GungHo Online Entertainment, Inc.");
        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                preferences.setShowAgain(showAgainCheck.isChecked());
                dismiss();
            }
        });
        return builder.create();
    }

    private void setPreferences(DisclaimerDialogFragment.Preferences preferences){
        this.preferences = preferences;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
