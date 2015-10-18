package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Adapters.AwakeningGridAdapter;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

public class TeamOverviewFragment extends AbstractFragment {
    public static final String TAG = TeamOverviewFragment.class.getSimpleName();
    private TextView teamHpValue, teamRcvValue;
    private GridView awakenings;
    private AwakeningGridAdapter awakeningGridAdapter;
    private Team team;
    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types and number of parameters
    public static TeamOverviewFragment newInstance(Team team) {
        TeamOverviewFragment fragment = new TeamOverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team_overview, container, false);
        teamHpValue = (TextView) rootView.findViewById(R.id.teamHpValue);
        teamRcvValue = (TextView) rootView.findViewById(R.id.teamRcvValue);
        awakenings = (GridView) rootView.findViewById(R.id.awakenings);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
        }
        awakeningGridAdapter = new AwakeningGridAdapter(getActivity(), team.getAwakenings());
        awakenings.setAdapter(awakeningGridAdapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void reverseArrayList() {

    }

    @Override
    public void sortArrayList(int sortMethod) {

    }

    @Override
    public void searchFilter(String query) {

    }
}
