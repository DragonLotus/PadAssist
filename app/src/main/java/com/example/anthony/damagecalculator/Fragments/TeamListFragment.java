package com.example.anthony.damagecalculator.Fragments;

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
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.TeamAlphabeticalComparator;
import com.example.anthony.damagecalculator.Util.TeamFavoriteComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderAtkComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderElement1Comparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderElement2Comparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderHpComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderPlusAtkComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderPlusComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderPlusHpComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderPlusRcvComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderRarityComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderRcvComparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderType1Comparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderType2Comparator;
import com.example.anthony.damagecalculator.Util.TeamLeaderType3Comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamListFragment extends AbstractFragment {
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
    private Boolean loggedIn = false;
    private TextView savedTeams;
    private LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private SortPlusDialogFragment sortPlusDialogFragment;
    private TeamLoadDialogFragment teamLoadDialogFragment;
    private int selectedTeam, sortMethod;
    private OnFragmentInteractionListener mListener;
    private SortLeaderDialogFragment sortLeaderDialogFragment;
    private TeamAlphabeticalComparator teamAlphabeticalComparator = new TeamAlphabeticalComparator();
    private Comparator<Team> teamLeaderElement1Comparator = new TeamLeaderElement1Comparator();
    private Comparator<Team> teamLeaderElement2Comparator = new TeamLeaderElement2Comparator();
    private Comparator<Team> teamLeaderType1Comparator = new TeamLeaderType1Comparator();
    private Comparator<Team> teamLeaderType2Comparator = new TeamLeaderType2Comparator();
    private Comparator<Team> teamLeaderType3Comparator = new TeamLeaderType3Comparator();
    private Comparator<Team> teamLeaderHpComparator = new TeamLeaderHpComparator();
    private Comparator<Team> teamLeaderAtkComparator = new TeamLeaderAtkComparator();
    private Comparator<Team> teamLeaderRcvComparator = new TeamLeaderRcvComparator();
    private Comparator<Team> teamLeaderPlusComparator = new TeamLeaderPlusComparator();
    private Comparator<Team> teamLeaderPlusHpComparator = new TeamLeaderPlusHpComparator();
    private Comparator<Team> teamLeaderPlusAtkComparator = new TeamLeaderPlusAtkComparator();
    private Comparator<Team> teamLeaderPlusRcvComparator = new TeamLeaderPlusRcvComparator();
    private Comparator<Team> teamLeaderRarityComparator = new TeamLeaderRarityComparator();
    private Comparator<Team> teamFavoriteComparator = new TeamFavoriteComparator();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamListFragment newInstance() {
        TeamListFragment fragment = new TeamListFragment();
        Bundle args = new Bundle();
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
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.sortAlphabetical).setVisible(true);
        menu.findItem(R.id.reverseList).setVisible(true);
        menu.setGroupVisible(R.id.sortTeam, true);
        menu.findItem(R.id.sortFavorite).setVisible(true);
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
        if (getArguments() != null) {

        }
        teams = (ArrayList) Team.getAllTeams();
        if (teams.size() != 0) {
            for (int i = 0; i < teams.size(); i++) {
                Log.d("Team log", "Team name: " + teams.get(i).getTeamName() + ", Team Monsters: " + teams.get(i).getMonsters() + ", Team Id: " + teams.get(i).getTeamId());
            }
        }
        if (!teams.isEmpty()) {
            savedTeams.setVisibility(View.GONE);
        }
        Collections.sort(teams, teamLeaderElement1Comparator);
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

    private ListView.OnItemClickListener teamListOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedTeam = position;
            Log.d("Team log", "Selected team position: " + selectedTeam);
            if (teamLoadDialogFragment == null) {
                teamLoadDialogFragment = TeamLoadDialogFragment.newInstance(loadTeam);
            }

            teamLoadDialogFragment.show(getChildFragmentManager(), "Show Team Load dialog");
        }
    };

    private TeamLoadDialogFragment.LoadTeam loadTeam = new TeamLoadDialogFragment.LoadTeam() {
        @Override
        public void loadTeam() {
            Team loadTeam = new Team(teamListAdapter.getItem(selectedTeam));
            Log.d("Team Log", "Team Name: " + loadTeam.getTeamName());
            loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
            loadTeam.setTeamId(0);
            loadTeam.save();
        }

        @Override
        public void editTeam(String teamName) {
            Team loadTeam = Team.getTeamById(teamListAdapter.getItem(selectedTeam).getTeamId());
            loadTeam.setTeamName(teamName);
            loadTeam.save();
            teams = (ArrayList) Team.getAllTeams();
            teamListAdapter.updateList(teams);
        }

        public void deleteTeam() {
//            Team.deleteTeam(teamListAdapter.getItem(selectedTeam).getTeamId());
            Team deleteTeam = Team.getTeamById(teamListAdapter.getItem(selectedTeam).getTeamId());
            Log.d("Team List Log", "Delete Team Name is: " + deleteTeam.getTeamName() + "Team id is: " + deleteTeam.getTeamId());
            deleteTeam.delete();
            teams.remove(selectedTeam);
            Log.d("Team List Log", "Team size is: " + teams.size());
            teamListAdapter.notifyDataSetChanged();
            if (teams.size() == 0) {
                savedTeams.setVisibility(View.VISIBLE);
            } else {
                savedTeams.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void sortArrayList(int sortMethod) {
        this.sortMethod = sortMethod;
        switch (sortMethod) {
            case 0:
                Collections.sort(teams, teamAlphabeticalComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 8:
                Collections.sort(teams, teamFavoriteComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 10:
                if (sortLeaderDialogFragment == null) {
                    sortLeaderDialogFragment = SortLeaderDialogFragment.newInstance(sortByLead);
                }
                sortLeaderDialogFragment.show(getChildFragmentManager(), "Sort by Lead");
                break;
            case 11:
                Collections.sort(teams, teamAlphabeticalComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void reverseArrayList() {
        switch (sortMethod) {
            default:
                Collections.reverse(teams);
                teamListAdapter.notifyDataSetChanged();
                break;
        }
    }

    private SortLeaderDialogFragment.SortBy sortByLead = new SortLeaderDialogFragment.SortBy() {
        @Override
        public void sortElement() {
            if (sortElementDialogFragment == null) {
                sortElementDialogFragment = SortElementDialogFragment.newInstance(sortByElement);
            }
            sortElementDialogFragment.show(getChildFragmentManager(), "Sort by Element");
        }

        @Override
        public void sortType() {
            if (sortTypeDialogFragment == null) {
                sortTypeDialogFragment = SortTypeDialogFragment.newInstance(sortByType);
            }
            sortTypeDialogFragment.show(getChildFragmentManager(), "Sort by Type");
        }

        @Override
        public void sortStats() {
            if (sortStatsDialogFragment == null) {
                sortStatsDialogFragment = SortStatsDialogFragment.newInstance(sortByStats);
            }
            sortStatsDialogFragment.show(getChildFragmentManager(), "Sort by Stats");
        }

        @Override
        public void sortPlus() {
            if (sortPlusDialogFragment == null) {
                sortPlusDialogFragment = SortPlusDialogFragment.newInstance(sortByPlus);
            }
            sortPlusDialogFragment.show(getChildFragmentManager(), "Sort by Plus");
        }

        @Override
        public void sortRarity() {
            sortMethod = 1001;
            Collections.sort(teams, teamLeaderRarityComparator);
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortElementDialogFragment.SortBy sortByElement = new SortElementDialogFragment.SortBy() {
        @Override
        public void sortElement1() {
            sortMethod = 201;
            Collections.sort(teams, teamLeaderElement1Comparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            sortMethod = 202;
            Collections.sort(teams, teamLeaderElement2Comparator);
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            sortMethod = 301;
            Collections.sort(teams, teamLeaderType1Comparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            sortMethod = 302;
            Collections.sort(teams, teamLeaderType2Comparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            sortMethod = 303;
            Collections.sort(teams, teamLeaderType3Comparator);
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            sortMethod = 401;
            Collections.sort(teams, teamLeaderHpComparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            sortMethod = 402;
            Collections.sort(teams, teamLeaderAtkComparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            sortMethod = 403;
            Collections.sort(teams, teamLeaderRcvComparator);
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortPlusDialogFragment.SortBy sortByPlus = new SortPlusDialogFragment.SortBy() {
        @Override
        public void sortTotal() {
            sortMethod = 701;
            Collections.sort(teams, teamLeaderPlusComparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            sortMethod = 702;
            Collections.sort(teams, teamLeaderPlusHpComparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            sortMethod = 703;
            Collections.sort(teams, teamLeaderPlusAtkComparator);
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            sortMethod = 704;
            Collections.sort(teams, teamLeaderPlusRcvComparator);
            teamListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void searchFilter(String query) {

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
