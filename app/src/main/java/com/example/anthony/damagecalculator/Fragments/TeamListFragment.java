package com.example.anthony.damagecalculator.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Adapters.TeamListAdapter;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.Singleton;
import com.example.anthony.damagecalculator.Util.TeamAlphabeticalComparator;
import com.example.anthony.damagecalculator.Util.TeamFavoriteComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperAtkComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperElement1Comparator;
import com.example.anthony.damagecalculator.Util.TeamHelperElement2Comparator;
import com.example.anthony.damagecalculator.Util.TeamHelperHpComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperPlusAtkComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperPlusComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperPlusHpComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperPlusRcvComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperRarityComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperRcvComparator;
import com.example.anthony.damagecalculator.Util.TeamHelperType1Comparator;
import com.example.anthony.damagecalculator.Util.TeamHelperType2Comparator;
import com.example.anthony.damagecalculator.Util.TeamHelperType3Comparator;
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
    private ArrayList<Team> teamList;
    private ArrayList<Team> teamListAll;
    private TeamListAdapter teamListAdapter;
    private MenuItem searchMenuItem;
    private Button importButton;
    private Boolean loggedIn = false;
    private TextView savedTeams;
    private LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private SortPlusDialogFragment sortPlusDialogFragment;
    private TeamLoadDialogFragment teamLoadDialogFragment;
    private int selectedTeam;
    private OnFragmentInteractionListener mListener;
    private SortLeaderDialogFragment sortLeaderDialogFragment;
    private SortHelperDialogFragment sortHelperDialogFragment;
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
    private Comparator<Team> teamHelperElement1Comparator = new TeamHelperElement1Comparator();
    private Comparator<Team> teamHelperElement2Comparator = new TeamHelperElement2Comparator();
    private Comparator<Team> teamHelperType1Comparator = new TeamHelperType1Comparator();
    private Comparator<Team> teamHelperType2Comparator = new TeamHelperType2Comparator();
    private Comparator<Team> teamHelperType3Comparator = new TeamHelperType3Comparator();
    private Comparator<Team> teamHelperHpComparator = new TeamHelperHpComparator();
    private Comparator<Team> teamHelperAtkComparator = new TeamHelperAtkComparator();
    private Comparator<Team> teamHelperRcvComparator = new TeamHelperRcvComparator();
    private Comparator<Team> teamHelperPlusComparator = new TeamHelperPlusComparator();
    private Comparator<Team> teamHelperPlusHpComparator = new TeamHelperPlusHpComparator();
    private Comparator<Team> teamHelperPlusAtkComparator = new TeamHelperPlusAtkComparator();
    private Comparator<Team> teamHelperPlusRcvComparator = new TeamHelperPlusRcvComparator();
    private Comparator<Team> teamHelperRarityComparator = new TeamHelperRarityComparator();

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
    public void onDestroy() {
        super.onDestroy();
        if (searchMenuItem != null) {
            if (MenuItemCompat.isActionViewExpanded(searchMenuItem)) {
                MenuItemCompat.collapseActionView(searchMenuItem);
            }
        }
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
        menu.findItem(R.id.search).setVisible(true);
        searchMenuItem = menu.findItem(R.id.search);
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
        teamListAll = (ArrayList) Team.getAllTeams();
        teamList = new ArrayList<>();
        if (teamListAll.size() != 0) {
            for (int i = 0; i < teamList.size(); i++) {
                Log.d("Team log", "Team name: " + teamList.get(i).getTeamName() + ", Team Monsters: " + teamList.get(i).getMonsters() + ", Team Id: " + teamList.get(i).getTeamId());
            }
        }
        if (!teamListAll.isEmpty()) {
            savedTeams.setVisibility(View.GONE);
        }
//        Collections.sort(teamList, teamLeaderElement1Comparator);
        teamListAdapter = new TeamListAdapter(getActivity(), R.layout.team_list_row, teamList);
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
                teamLoadDialogFragment = TeamLoadDialogFragment.newInstance(loadTeam, teamList.get(position));
            }

            teamLoadDialogFragment.show(getChildFragmentManager(), "Show Team Load dialog", teamList.get(position));
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
            teamList = (ArrayList) Team.getAllTeams();
            teamListAdapter.updateList(teamList);
        }

        @Override
        public void deleteTeam() {
//            Team.deleteTeam(teamListAdapter.getItem(selectedTeam).getTeamId());
            Team deleteTeam = Team.getTeamById(teamListAdapter.getItem(selectedTeam).getTeamId());
            Log.d("Team List Log", "Delete Team Name is: " + deleteTeam.getTeamName() + "Team id is: " + deleteTeam.getTeamId());
            deleteTeam.delete();
            teamList.remove(selectedTeam);
            Log.d("Team List Log", "Team size is: " + teamList.size());
            teamListAdapter.notifyDataSetChanged();
            if (teamList.size() == 0) {
                savedTeams.setVisibility(View.VISIBLE);
            } else {
                savedTeams.setVisibility(View.GONE);
            }
        }

        @Override
        public void favoriteTeam(boolean favorite) {
            teamListAdapter.getItem(selectedTeam).setFavorite(favorite);
            teamListAdapter.getItem(selectedTeam).save();
            Log.d("TeamListFragment", "Team 0 is favorite: " + teamList.get(0).isFavorite());
            teamListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void sortArrayList(int sortMethod) {
        Singleton.getInstance().setTeamSortMethod(sortMethod);
        switch (sortMethod) {
            case 0:
                Collections.sort(teamList, teamAlphabeticalComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 8:
                Collections.sort(teamList, teamFavoriteComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 10:
                if (sortLeaderDialogFragment == null) {
                    sortLeaderDialogFragment = SortLeaderDialogFragment.newInstance(sortByLead);
                }
                sortLeaderDialogFragment.show(getChildFragmentManager(), "Sort by Lead");
                break;
            case 11:
                if (sortHelperDialogFragment == null) {
                    sortHelperDialogFragment = SortHelperDialogFragment.newInstance(sortByHelper);
                }
                sortHelperDialogFragment.show(getChildFragmentManager(), "Sort by Lead");
                break;
            case 1001:
                Collections.sort(teamList, teamLeaderElement1Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1002:
                Collections.sort(teamList, teamLeaderElement2Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1003:
                Collections.sort(teamList, teamLeaderType1Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1004:
                Collections.sort(teamList, teamLeaderType2Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1005:
                Collections.sort(teamList, teamLeaderType3Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1006:
                Collections.sort(teamList, teamLeaderHpComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1007:
                Collections.sort(teamList, teamLeaderAtkComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1008:
                Collections.sort(teamList, teamLeaderRcvComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1009:
                Collections.sort(teamList, teamLeaderPlusComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1010:
                Collections.sort(teamList, teamLeaderPlusHpComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1011:
                Collections.sort(teamList, teamLeaderPlusAtkComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1012:
                Collections.sort(teamList, teamLeaderPlusRcvComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1013:
                Collections.sort(teamList, teamLeaderRarityComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1101:
                Collections.sort(teamList, teamHelperElement1Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1102:
                Collections.sort(teamList, teamHelperElement2Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1103:
                Collections.sort(teamList, teamHelperType1Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1104:
                Collections.sort(teamList, teamHelperType2Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1105:
                Collections.sort(teamList, teamHelperType3Comparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1106:
                Collections.sort(teamList, teamHelperHpComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1107:
                Collections.sort(teamList, teamHelperAtkComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1108:
                Collections.sort(teamList, teamHelperRcvComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1109:
                Collections.sort(teamList, teamHelperPlusComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1110:
                Collections.sort(teamList, teamHelperPlusHpComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1111:
                Collections.sort(teamList, teamHelperPlusAtkComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1112:
                Collections.sort(teamList, teamHelperPlusRcvComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
            case 1113:
                Collections.sort(teamList, teamHelperRarityComparator);
                teamListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void reverseArrayList() {
        switch (Singleton.getInstance().getTeamSortMethod()) {
            default:
                Collections.reverse(teamList);
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
            Singleton.getInstance().setTeamSortMethod(1013);
            Collections.sort(teamList, teamHelperRarityComparator);
            teamListAdapter.notifyDataSetChanged();
            sortLeaderDialogFragment.dismiss();
        }
    };

    private SortHelperDialogFragment.SortBy sortByHelper = new SortHelperDialogFragment.SortBy() {
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
            Singleton.getInstance().setTeamSortMethod(1113);
            Collections.sort(teamList, teamHelperRarityComparator);
            teamListAdapter.notifyDataSetChanged();
            sortHelperDialogFragment.dismiss();
        }
    };

    private SortElementDialogFragment.SortBy sortByElement = new SortElementDialogFragment.SortBy() {
        @Override
        public void sortElement1() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1001);
                Collections.sort(teamList, teamLeaderElement1Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1101);
                Collections.sort(teamList, teamHelperElement1Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1002);
                Collections.sort(teamList, teamLeaderElement2Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1102);
                Collections.sort(teamList, teamHelperElement2Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1003);
                Collections.sort(teamList, teamLeaderType1Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1103);
                Collections.sort(teamList, teamHelperType1Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1004);
                Collections.sort(teamList, teamLeaderType2Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1104);
                Collections.sort(teamList, teamHelperType2Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1005);
                Collections.sort(teamList, teamLeaderType3Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1105);
                Collections.sort(teamList, teamHelperType3Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1006);
                Collections.sort(teamList, teamLeaderHpComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1106);
                Collections.sort(teamList, teamHelperHpComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1007);
                Collections.sort(teamList, teamLeaderAtkComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1107);
                Collections.sort(teamList, teamHelperAtkComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1008);
                Collections.sort(teamList, teamLeaderRcvComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1108);
                Collections.sort(teamList, teamHelperRcvComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }
    };

    private SortPlusDialogFragment.SortBy sortByPlus = new SortPlusDialogFragment.SortBy() {
        @Override
        public void sortTotal() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1009);
                Collections.sort(teamList, teamLeaderPlusComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1109);
                Collections.sort(teamList, teamHelperPlusComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1010);
                Collections.sort(teamList, teamLeaderPlusHpComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1110);
                Collections.sort(teamList, teamHelperPlusHpComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1011);
                Collections.sort(teamList, teamLeaderPlusAtkComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1111);
                Collections.sort(teamList, teamHelperPlusAtkComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            if(sortLeaderDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1012);
                Collections.sort(teamList, teamLeaderPlusRcvComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null){
                Singleton.getInstance().setTeamSortMethod(1112);
                Collections.sort(teamList, teamHelperPlusRcvComparator);
                sortHelperDialogFragment.dismiss();
            }
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
        if (teamListAdapter != null) {
            if (query != null && query.length() > 0) {
                if (!teamList.isEmpty()) {
                    teamList.clear();
                }
                filterTeamName(query);
                filterContainsMonster(query);
            } else {
                teamList.clear();
                teamList.addAll(teamListAll);
            }
            sortArrayList(Singleton.getInstance().getTeamSortMethod());
        }
    }

    private void filterTeamName(String query) {
        for (Team team : teamListAll) {
            if (team.getTeamName().toLowerCase().contains(query.toLowerCase()) && !teamList.contains(team)) {
                teamList.add(team);
            }
        }
    }

    private void filterContainsMonster(String query) {
        boolean hasMonster = false;
        for(Team team : teamListAll){
            for(Monster monster : team.getMonsters()){
                if(monster.getName().toLowerCase().contains(query.toLowerCase())){
                    hasMonster = true;
                }
            }
            if(!teamList.contains(team) && hasMonster){
                teamList.add(team);
                hasMonster = false;
            }
        }
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
