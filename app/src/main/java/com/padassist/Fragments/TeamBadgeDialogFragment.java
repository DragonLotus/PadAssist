package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Toast;

import com.padassist.Adapters.TeamBadgeGridRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Team;
import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class TeamBadgeDialogFragment extends DialogFragment {
    private RecyclerView teamBadgeRecycler;
    private TeamBadgeGridRecycler teamBadgeGridAdapter;
    private SetTeamBadge setTeamBadge;
    private Team team;
    private Toast toast;
    private ArrayList<Integer> teamBadgeList;
    private int selected;

    public interface SetTeamBadge {
        void setBadge(int badge);
    }

    public static TeamBadgeDialogFragment newInstance(SetTeamBadge setTeamBadge, Team team) {
        TeamBadgeDialogFragment dialogFragment = new TeamBadgeDialogFragment();
        dialogFragment.setSetTeamBadge(setTeamBadge);
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        View rootView = View.inflate(getActivity(), R.layout.fragment_team_badge_dialog, null);
        teamBadgeRecycler = (RecyclerView) rootView.findViewById(R.id.teamBadgeRecyclerView);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setTitle("Select Team Badge");
        builder.setView(rootView).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                setTeamBadge.setBadge(selected);
                dialog.dismiss();
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
        }

        teamBadgeList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            teamBadgeList.add(i);
        }
        teamBadgeRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        teamBadgeGridAdapter = new TeamBadgeGridRecycler(getContext(), teamBadgeList, team.getTeamBadge(), selectOnClickListener);
        teamBadgeRecycler.setAdapter(teamBadgeGridAdapter);
    }

    private View.OnClickListener selectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selected = (int) v.getTag(R.string.index);
            teamBadgeGridAdapter.setSelected(selected);
        }
    };

    public void setSetTeamBadge(SetTeamBadge SetTeamBadge) {
        this.setTeamBadge = SetTeamBadge;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}