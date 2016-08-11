package com.padassist.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.padassist.Adapters.TeamListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.R;
import com.padassist.Util.Singleton;
import com.padassist.Util.TeamAlphabeticalComparator;
import com.padassist.Util.TeamFavoriteComparator;
import com.padassist.Util.TeamHelperAtkComparator;
import com.padassist.Util.TeamHelperElement1Comparator;
import com.padassist.Util.TeamHelperElement2Comparator;
import com.padassist.Util.TeamHelperHpComparator;
import com.padassist.Util.TeamHelperPlusAtkComparator;
import com.padassist.Util.TeamHelperPlusComparator;
import com.padassist.Util.TeamHelperPlusHpComparator;
import com.padassist.Util.TeamHelperPlusRcvComparator;
import com.padassist.Util.TeamHelperRarityComparator;
import com.padassist.Util.TeamHelperRcvComparator;
import com.padassist.Util.TeamHelperType1Comparator;
import com.padassist.Util.TeamHelperType2Comparator;
import com.padassist.Util.TeamHelperType3Comparator;
import com.padassist.Util.TeamLeaderAtkComparator;
import com.padassist.Util.TeamLeaderElement1Comparator;
import com.padassist.Util.TeamLeaderElement2Comparator;
import com.padassist.Util.TeamLeaderHpComparator;
import com.padassist.Util.TeamLeaderPlusAtkComparator;
import com.padassist.Util.TeamLeaderPlusComparator;
import com.padassist.Util.TeamLeaderPlusHpComparator;
import com.padassist.Util.TeamLeaderPlusRcvComparator;
import com.padassist.Util.TeamLeaderRarityComparator;
import com.padassist.Util.TeamLeaderRcvComparator;
import com.padassist.Util.TeamLeaderType1Comparator;
import com.padassist.Util.TeamLeaderType2Comparator;
import com.padassist.Util.TeamLeaderType3Comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;

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
    private RecyclerView teamListView;
    private ArrayList<Team> teamList;
    private ArrayList<Team> teamListAll;
    private TeamListRecycler teamListAdapter;
    private MenuItem searchMenuItem;
    protected SearchView searchView;
    private Button importButton;
    private Boolean loggedIn = false;
    private TextView savedTeams;
    private LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private SortPlusDialogFragment sortPlusDialogFragment;
    private TeamLoadDialogFragment teamLoadDialogFragment;
    private LoadTeamConfirmationDialogFragment loadTeamConfirmationDialogFragment;
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
    private Realm realm = Realm.getDefaultInstance();

//    private FastScroller fastScroller;

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
        getActivity().setTitle("Saved Teams");
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

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reverseList:
                reverseArrayList();
                break;
            case R.id.sortAlphabetical:
                sortArrayList(0);
                break;
            case R.id.sortId:
                sortArrayList(1);
                break;
            case R.id.sortElement:
                sortArrayList(2);
                break;
            case R.id.sortType:
                sortArrayList(3);
                break;
            case R.id.sortStat:
                sortArrayList(4);
                break;
            case R.id.sortRarity:
                sortArrayList(5);
                break;
            case R.id.sortAwakenings:
                sortArrayList(6);
                break;
            case R.id.sortPlus:
                sortArrayList(7);
                break;
            case R.id.sortFavorite:
                sortArrayList(8);
                break;
            case R.id.sortLevel:
                sortArrayList(9);
                break;
            case R.id.sortLead:
                sortArrayList(10);
                break;
            case R.id.sortHelper:
                sortArrayList(11);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_team_list, container, false);
        teamListView = (RecyclerView) rootView.findViewById(R.id.teamListView);
        importButton = (Button) rootView.findViewById(R.id.importButton);
        savedTeams = (TextView) rootView.findViewById(R.id.savedTeams);
//        fastScroller = (FastScroller) rootView.findViewById(R.id.fastScroller);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {

        }
        teamListAll = new ArrayList<>();
        teamListAll.addAll(realm.where(Team.class).greaterThan("teamId", 0).findAll());
        teamList = new ArrayList<>();
        if (teamListAll.size() != 0) {
            for (int i = 0; i < teamList.size(); i++) {
            }
        }
        if (!teamListAll.isEmpty()) {
            savedTeams.setVisibility(View.GONE);
        }
//        Collections.sort(teamList, teamLeaderElement1Comparator);
        teamListAdapter = new TeamListRecycler(getActivity(), teamList, teamListOnClickListener);
        teamListView.setAdapter(teamListAdapter);
        teamListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        importButton.setOnClickListener(buttonOnClickListener);
//        teamListView.setOnItemClickListener(teamListOnClickListener);

//        fastScroller.setRecyclerView(teamListView);
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

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private View.OnClickListener teamListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectedTeam = (int) v.getTag(R.string.index);
            if (teamLoadDialogFragment == null) {
                teamLoadDialogFragment = TeamLoadDialogFragment.newInstance(loadTeam, teamList.get(selectedTeam));
            }
            teamLoadDialogFragment.show(getChildFragmentManager(), "Show Team Load dialog", teamList.get(selectedTeam));
        }
    };

    private TeamLoadDialogFragment.LoadTeam loadTeam = new TeamLoadDialogFragment.LoadTeam() {
        @Override
        public void loadTeam() {
            Team teamZero = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            Monster monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
            Team loadTeam = teamList.get(selectedTeam);
            loadTeam = realm.copyFromRealm(loadTeam);

            if (teamZero.getTeamIdOverwrite() == 0) {
                ArrayList<Monster> zeroMonsterList = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    zeroMonsterList.add(monsterZero);
                }
                if (teamZero.getMonsters().equals(zeroMonsterList)) {
                    loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
                    loadTeam.setTeamId(0);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(loadTeam);
                    realm.commitTransaction();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    if (loadTeamConfirmationDialogFragment == null) {
                        loadTeamConfirmationDialogFragment = LoadTeamConfirmationDialogFragment.newInstance(loadTeamConfirmation);
                    }
                    loadTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Load Team Confirmation");
                }
            } else if (realm.where(Team.class).equalTo("teamId", teamZero.getTeamIdOverwrite()).findFirst() != null) {
                if (!teamZero.getMonsters().equals(realm.where(Team.class).equalTo("teamId", teamZero.getTeamIdOverwrite()).findFirst().getMonsters()) || !teamZero.getTeamName().equals(realm.where(Team.class).equalTo("teamId", teamZero.getTeamIdOverwrite()).findFirst().getTeamName())) {
                    if (loadTeamConfirmationDialogFragment == null) {
                        loadTeamConfirmationDialogFragment = LoadTeamConfirmationDialogFragment.newInstance(loadTeamConfirmation);
                    }
                    loadTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Load Team Confirmation");
                } else {
                    loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
                    loadTeam.setTeamId(0);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(loadTeam);
                    realm.commitTransaction();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            } else {
                loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
                loadTeam.setTeamId(0);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(loadTeam);
                realm.commitTransaction();
                getActivity().getSupportFragmentManager().popBackStack();
            }

        }

        @Override
        public void editTeam(String teamName) {
            Team editTeam = teamList.get(selectedTeam);
            Log.d("TeamList", "Is edit team valid: " + editTeam.isValid());
            editTeam = realm.copyFromRealm(editTeam);
            editTeam.setTeamName(teamName);
            realm.beginTransaction();
            if (realm.where(Team.class).equalTo("teamId", 0).findFirst().getTeamIdOverwrite() == editTeam.getTeamId()) {
                realm.where(Team.class).equalTo("teamId", 0).findFirst().setTeamName(teamName);
            }
            realm.copyToRealmOrUpdate(editTeam);
            realm.commitTransaction();
            teamListAdapter.notifyItemChanged(selectedTeam);
        }

        @Override
        public void deleteTeam() {
            final Team deleteTeam = teamList.get(selectedTeam);
            teamList.remove(selectedTeam);
            teamListAdapter.notifyDataSetChanged();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(deleteTeam).deleteFromRealm();
                }
            });
            if (teamList.size() == 0) {
                savedTeams.setVisibility(View.VISIBLE);
            } else {
                savedTeams.setVisibility(View.GONE);
            }
        }

        @Override
        public void favoriteTeam(boolean favorite) {
            if (selectedTeam < teamListAdapter.getItemCount() && teamListAdapter.getItemCount() != 0) {
                realm.beginTransaction();
                teamListAdapter.getItem(selectedTeam).setFavorite(favorite);
                realm.commitTransaction();
                teamListAdapter.notifyItemChanged(selectedTeam);
            }
        }
    };

    private LoadTeamConfirmationDialogFragment.ResetLayout loadTeamConfirmation = new LoadTeamConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            Team loadTeam = teamList.get(selectedTeam);
            loadTeam.setTeamIdOverwrite(loadTeam.getTeamId());
            loadTeam.setTeamId(0);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(loadTeam);
            realm.commitTransaction();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };


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
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1001);
                Collections.sort(teamList, teamLeaderElement1Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1101);
                Collections.sort(teamList, teamHelperElement1Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1002);
                Collections.sort(teamList, teamLeaderElement2Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
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
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1003);
                Collections.sort(teamList, teamLeaderType1Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1103);
                Collections.sort(teamList, teamHelperType1Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1004);
                Collections.sort(teamList, teamLeaderType2Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1104);
                Collections.sort(teamList, teamHelperType2Comparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1005);
                Collections.sort(teamList, teamLeaderType3Comparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
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
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1006);
                Collections.sort(teamList, teamLeaderHpComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1106);
                Collections.sort(teamList, teamHelperHpComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1007);
                Collections.sort(teamList, teamLeaderAtkComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1107);
                Collections.sort(teamList, teamHelperAtkComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1008);
                Collections.sort(teamList, teamLeaderRcvComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
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
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1009);
                Collections.sort(teamList, teamLeaderPlusComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1109);
                Collections.sort(teamList, teamHelperPlusComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1010);
                Collections.sort(teamList, teamLeaderPlusHpComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1110);
                Collections.sort(teamList, teamHelperPlusHpComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1011);
                Collections.sort(teamList, teamLeaderPlusAtkComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1111);
                Collections.sort(teamList, teamHelperPlusAtkComparator);
                sortHelperDialogFragment.dismiss();
            }
            teamListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            if (sortLeaderDialogFragment != null) {
                Singleton.getInstance().setTeamSortMethod(1012);
                Collections.sort(teamList, teamLeaderPlusRcvComparator);
                sortLeaderDialogFragment.dismiss();
            } else if (sortHelperDialogFragment != null) {
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
//            if(fastScroller!=null){
//                fastScroller.resizeScrollBar(teamListAdapter.expanded(), FastScroller.TEAM_LIST);
//            }
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
        for (Team team : teamListAll) {
            for (Monster monster : team.getMonsters()) {
                if (monster.getName().toLowerCase().contains(query.toLowerCase())) {
                    hasMonster = true;
                }
            }
            if (!teamList.contains(team) && hasMonster) {
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
