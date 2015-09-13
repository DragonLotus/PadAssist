package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

public class TeamLoadDialogFragment extends DialogFragment {

    private LoadTeam loadTeam;

    public interface LoadTeam {
        public void loadTeam();
    }

    public static TeamLoadDialogFragment newInstance(LoadTeam loadTeam) {
        TeamLoadDialogFragment dialogFragment = new TeamLoadDialogFragment();
        dialogFragment.setLoadTeam(loadTeam);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Load Team");
        builder.setMessage("Load the selected team?");
        builder.setPositiveButton("Load", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                loadTeam.loadTeam();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    public void setLoadTeam(LoadTeam loadTeam){
        this.loadTeam = loadTeam;
    }

}