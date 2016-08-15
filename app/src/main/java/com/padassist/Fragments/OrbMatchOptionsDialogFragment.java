package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.padassist.Adapters.TypeGridAdapter;
import com.padassist.Data.Element;
import com.padassist.Data.Team;
import com.padassist.Graphics.ExpandableHeightGridView;
import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class OrbMatchOptionsDialogFragment extends DialogFragment {
    private CheckBox enableHeart;
    private Toast toast;
    private Team team;

    public static OrbMatchOptionsDialogFragment newInstance() {
        OrbMatchOptionsDialogFragment dialogFragment = new OrbMatchOptionsDialogFragment();
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        View rootView = View.inflate(getActivity(), R.layout.fragment_orb_match_options_dialog, null);
        enableHeart = (CheckBox) rootView.findViewById(R.id.enableHeartCarry);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        setEnable();
        builder.setTitle("Options");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setHeartCarryOver(enableHeart.isChecked());
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    private void setEnable(){
        if(Singleton.getInstance().isHeartCarryOver()){
            enableHeart.setChecked(true);
        } else {
            enableHeart.setChecked(false);
        }
    }
    public void show(FragmentManager manager, Team team, String tag) {
        super.show(manager, tag);
        this.team = team;
    }
}