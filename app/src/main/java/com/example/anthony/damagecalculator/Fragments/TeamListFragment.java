package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Adapters.TeamListAdapter;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamListFragment extends Fragment {
    public static final String TAG = TeamListFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView teamListView;
    private ArrayList<Team> teams;
    private TeamListAdapter teamListAdapter;
    private Button importButton;
    private Team team;
    private Enemy enemy;
    private Boolean loggedIn=false;
    private TextView savedTeams;
    private LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
    private TeamLoadDialogFragment teamLoadDialogFragment = new TeamLoadDialogFragment();

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamListFragment newInstance(Team team, Enemy enemy) {
        TeamListFragment fragment = new TeamListFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team_list, container, false);
        teamListView = (ListView) rootView.findViewById(R.id.teamListView);
        importButton = (Button) rootView.findViewById(R.id.importButton);
        savedTeams = (TextView) rootView.findViewById(R.id.savedTeams);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
        }
        teams = (ArrayList) Team.getAllTeams();
        if(teams.size() != 0){
            Log.d("Team log", "Team name: " + teams.get(0).getTeamName() + ", Team Monsters: " + teams.get(0).getMonsters() + ", Team Id: " + teams.get(0).getTeamId());
        }
        if(!teams.isEmpty()){
            savedTeams.setVisibility(View.GONE);
        }
        teamListAdapter = new TeamListAdapter(getActivity(), R.layout.team_list_row, teams);
        teamListView.setAdapter(teamListAdapter);
        importButton.setOnClickListener(buttonOnClickListener);
        teamListView.setOnItemClickListener(teamListOnClickListener);
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(importButton)) {
                if (!loggedIn) {
                    //loginDialogFragment.newInstance();
                    loginDialogFragment.show(getChildFragmentManager(), "Show Login Dialog Fragment");
                } else {
                    //Do Nothing
                }
            }
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private ListView.OnItemClickListener teamListOnClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (teamLoadDialogFragment == null) {
                teamLoadDialogFragment = teamLoadDialogFragment.newInstance(loadTeam);
            }
            teamLoadDialogFragment.show(getSupportFragmentManager(), "Show Team Load dialog");
        }
    };

    private TeamLoadDialogFragment.LoadTeam loadTeam = new TeamLoadDialogFragment.LoadTeam(){
        @Override
        public void loadTeam() {
            teamListView.getChildAt(0);
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
