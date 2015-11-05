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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

public class TeamLoadDialogFragment extends DialogFragment {

    private RadioGroup choiceRadioGroup;
    private CheckBox favorite;
    private EditText teamName;
    private LoadTeam loadTeam;
    private Toast toast;
    private Team team;

    public interface LoadTeam {
        public void loadTeam();

        public void deleteTeam();

        public void editTeam(String teamName);

        public void favoriteTeam(boolean favorite);
    }

    public static TeamLoadDialogFragment newInstance(LoadTeam loadTeam, Team team) {
        TeamLoadDialogFragment dialogFragment = new TeamLoadDialogFragment();
        dialogFragment.setLoadTeam(loadTeam);
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_team_load_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        teamName = (EditText) rootView.findViewById(R.id.teamName);
        favorite = (CheckBox) rootView.findViewById(R.id.favorite);
        builder.setTitle("Team Options");
        builder.setView(rootView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
            Button positiveButton = (Button) dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.loadTeam) {
                        loadTeam.loadTeam();
                        dismiss();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.deleteTeam) {
                        loadTeam.deleteTeam();
                        dismiss();
                    } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.editTeam) {
                        if (!teamName.getText().toString().equals("")) {
                            Log.d("Team Name", "" + teamName.getText());
                            loadTeam.editTeam(teamName.getText().toString());
                            dismiss();
                        } else {
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(getActivity(), "Please enter a team name", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                    loadTeam.favoriteTeam(favorite.isChecked());
                }
            });
        }
    }

    private CheckBox.OnCheckedChangeListener favoriteCheckListener = new CheckBox.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(false);
            } else {
                choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(true);
            }
        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            team = getArguments().getParcelable("team");
        }
        teamName.setText(team.getTeamName());
        choiceRadioGroup.setOnCheckedChangeListener(choiceOnCheckedChangeListener);
        favorite.setOnCheckedChangeListener(favoriteCheckListener);
    }

    private RadioGroup.OnCheckedChangeListener choiceOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.editTeam) {
                teamName.setEnabled(true);
            } else {
                teamName.setEnabled(false);
            }
        }
    };

    public void setLoadTeam(LoadTeam loadTeam) {
        this.loadTeam = loadTeam;
    }

    public void show(FragmentManager manager, String tag, Team team) {
        super.show(manager, tag);
        this.team = team;
    }

}