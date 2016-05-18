package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.anthony.damagecalculator.Adapters.SaveMonsterListAdapter;
import com.example.anthony.damagecalculator.Adapters.SaveMonsterListRecycler;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Graphics.FastScroller;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.MonsterAlphabeticalComparator;
import com.example.anthony.damagecalculator.Util.MonsterAtkComparator;
import com.example.anthony.damagecalculator.Util.MonsterAwakeningComparator;
import com.example.anthony.damagecalculator.Util.MonsterElement1Comparator;
import com.example.anthony.damagecalculator.Util.MonsterElement2Comparator;
import com.example.anthony.damagecalculator.Util.MonsterFavoriteComparator;
import com.example.anthony.damagecalculator.Util.MonsterHpComparator;
import com.example.anthony.damagecalculator.Util.MonsterLevelComparator;
import com.example.anthony.damagecalculator.Util.MonsterNumberComparator;
import com.example.anthony.damagecalculator.Util.MonsterPlusAtkComparator;
import com.example.anthony.damagecalculator.Util.MonsterPlusComparator;
import com.example.anthony.damagecalculator.Util.MonsterPlusHpComparator;
import com.example.anthony.damagecalculator.Util.MonsterPlusRcvComparator;
import com.example.anthony.damagecalculator.Util.MonsterRarityComparator;
import com.example.anthony.damagecalculator.Util.MonsterRcvComparator;
import com.example.anthony.damagecalculator.Util.MonsterType1Comparator;
import com.example.anthony.damagecalculator.Util.MonsterType2Comparator;
import com.example.anthony.damagecalculator.Util.MonsterType3Comparator;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SaveMonsterListFragment extends AbstractFragment {
    public static final String TAG = SaveMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private RecyclerView monsterListView;
    private ArrayList<Monster> monsterList;
    private ArrayList<Monster> monsterListAll;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private SaveMonsterListAdapter saveMonsterListAdapter;
    private SaveMonsterListRecycler saveMonsterListRecycler;
    private Toast toast;
    private TextView savedMonsters;
    private boolean replaceAll;
    private long replaceMonsterId;
    private Monster monsterZero = Monster.getMonsterId(0);
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private SortPlusDialogFragment sortPlusDialogFragment;
    private Comparator<Monster> monsterNumberComparator = new MonsterNumberComparator();
    private Comparator<Monster> monsterElement1Comparator = new MonsterElement1Comparator();
    private Comparator<Monster> monsterElement2Comparator = new MonsterElement2Comparator();
    private Comparator<Monster> monsterType1Comparator = new MonsterType1Comparator();
    private Comparator<Monster> monsterType2Comparator = new MonsterType2Comparator();
    private Comparator<Monster> monsterType3Comparator = new MonsterType3Comparator();
    private Comparator<Monster> monsterAlphabeticalComparator = new MonsterAlphabeticalComparator();
    private Comparator<Monster> monsterHpComparator = new MonsterHpComparator();
    private Comparator<Monster> monsterAtkComparator = new MonsterAtkComparator();
    private Comparator<Monster> monsterRcvComparator = new MonsterRcvComparator();
    private Comparator<Monster> monsterRarityComparator = new MonsterRarityComparator();
    private Comparator<Monster> monsterAwakeningComparator = new MonsterAwakeningComparator();
    private Comparator<Monster> monsterPlusComparator = new MonsterPlusComparator();
    private Comparator<Monster> monsterPlusHpComparator = new MonsterPlusHpComparator();
    private Comparator<Monster> monsterPlusAtkComparator = new MonsterPlusAtkComparator();
    private Comparator<Monster> monsterPlusRcvComparator = new MonsterPlusRcvComparator();
    private Comparator<Monster> monsterLevelComparator = new MonsterLevelComparator();
    private Comparator<Monster> monsterFavoriteComparator = new MonsterFavoriteComparator();

    private FastScroller fastScroller;

    public static SaveMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        fragment.setArguments(args);
        return fragment;
    }

    public SaveMonsterListFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchMenuItem != null) {
            if (MenuItemCompat.isActionViewExpanded(searchMenuItem)) {
                MenuItemCompat.collapseActionView(searchMenuItem);
            }
        }
        Log.d("Save Monster List Log" , "Singleton sortMethod is: " + Singleton.getInstance().getSaveSortMethod());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.sortGroup, true);
        menu.setGroupVisible(R.id.sortMoreGroup, true);
        menu.findItem(R.id.search).setVisible(true);
        searchMenuItem = menu.findItem(R.id.search);
//        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_monster_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
        savedMonsters = (TextView) rootView.findViewById(R.id.savedMonsters);
        fastScroller = (FastScroller) rootView.findViewById(R.id.fastScroller);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }
//        monsterList = (ArrayList) Monster.getAllMonsters();
        if(Singleton.getInstance().getMonsterOverwrite() == 5){
            monsterListAll = (ArrayList) Monster.getAllHelperMonsters();
            monsterListAll.add(monsterZero);
        } else {
            monsterListAll = (ArrayList) Monster.getAllSavedMonsters();
        }
        if (monsterList == null) {
            monsterList = new ArrayList<>();
        }
        if (monsterListAll.size() == 1) {
            if(monsterListAll.get(0).getMonsterId() != 0){
                monsterListAll.add(0, monsterZero);
                savedMonsters.setVisibility(View.GONE);
                monsterListView.setVisibility(View.VISIBLE);
            }else {
                savedMonsters.setVisibility(View.VISIBLE);
                monsterListView.setVisibility(View.GONE);
            }
        } else {
            savedMonsters.setVisibility(View.GONE);
            monsterListView.setVisibility(View.VISIBLE);
        }
//        searchFilter("");
        Log.d("Save Monster List", "onActivityCreated monsterList is: " + monsterList + " monsterListAll is: " + monsterListAll);
        //disableStuff();
//        Collections.sort(monsterList, monsterFavoriteComparator);
//        sortMethod = Singleton.getInstance().getSaveSortMethod();
//        saveMonsterListRecycler = new SaveMonsterListAdapter(getActivity(), R.layout.save_monster_list_row, monsterList);
        saveMonsterListRecycler = new SaveMonsterListRecycler(getActivity(), monsterList, expandChange, monsterListOnClickListener, monsterListOnLongClickListener);
        monsterListView.setAdapter(saveMonsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        monsterListView.setOnItemClickListener(monsterListOnClickListener);
        Log.d("Save Monster List", "onActivityCreated After monsterList is: " + monsterList + " monsterListAll is: " + monsterListAll);
        fastScroller.setRecyclerView(monsterListView);
    }

    private SaveMonsterListRecycler.ExpandChange expandChange = new SaveMonsterListRecycler.ExpandChange() {
        @Override
        public void onExpandChange(int expandedPosition) {
//            if(expandedPosition >= 0){
//                fastScroller.resizeScrollBar(true, FastScroller.SAVE_MONSTER_LIST);
//            }else{
//                fastScroller.resizeScrollBar(false, FastScroller.SAVE_MONSTER_LIST);
//            }
        }
    };

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("saveMonsterListTag","view is: " + v + " R.string.index is: " + v.getTag(R.string.index));
            int position = (int) v.getTag(R.string.index);
            Team newTeam = new Team(Team.getTeamById(0));
            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (replaceAll) {
                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
                    Team replaceTeam;
                    for (int i = 0; i < teamList.size(); i++) {
                        replaceTeam = teamList.get(i);
                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
                            }
                        }
                        replaceTeam.save();
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {

                    switch (Singleton.getInstance().getMonsterOverwrite()) {
                        case 0:
                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
                            break;
                        case 1:
                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
                            break;
                        case 2:
                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
                            break;
                        case 3:
                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
                            break;
                        case 4:
                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
                            break;
                        case 5:
                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
                            break;
                    }
                    newTeam.save();
                    Log.d("Save Monster Log", "Team is: " + newTeam.getMonsters());
                    Log.d("Save Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
                    getActivity().getSupportFragmentManager().popBackStack();
//                    if (replaceMonsterId == 0){
//                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
//                    }
                }
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = new Team(Team.getTeamById(0));
            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (replaceAll) {
                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
                    Team replaceTeam;
                    for (int i = 0; i < teamList.size(); i++) {
                        replaceTeam = teamList.get(i);
                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
                            }
                        }
                        replaceTeam.save();
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {

                    switch (Singleton.getInstance().getMonsterOverwrite()) {
                        case 0:
                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
                            break;
                        case 1:
                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
                            break;
                        case 2:
                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
                            break;
                        case 3:
                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
                            break;
                        case 4:
                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
                            break;
                        case 5:
                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
                            break;
                    }
                    newTeam.save();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
            return true;
        }
    };

//    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Team newTeam = new Team(Team.getTeamById(0));
//            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (replaceAll) {
//                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//                    Team replaceTeam;
//                    for (int i = 0; i < teamList.size(); i++) {
//                        replaceTeam = teamList.get(i);
//                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
//                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
//                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
//                            }
//                        }
//                        replaceTeam.save();
//                    }
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    getActivity().getSupportFragmentManager().popBackStack();
//                } else {
//
//
//                    switch (Singleton.getInstance().getMonsterOverwrite()) {
//                        case 0:
//                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 1:
//                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 2:
//                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 3:
//                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 4:
//                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 5:
//                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
//                            break;
//                    }
//                    newTeam.save();
//                    Log.d("Save Monster Log", "Team is: " + newTeam.getMonsters());
//                    Log.d("Save Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
//                    getActivity().getSupportFragmentManager().popBackStack();
////                    if (replaceMonsterId == 0){
////                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
////                    }
//                }
//            }
//
//        }
//    };

    private void disableStuff() {
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()) {
                monsterList.remove(i);
            }
        }
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

    private SortElementDialogFragment.SortBy sortByElement = new SortElementDialogFragment.SortBy() {
        @Override
        public void sortElement1() {
            Singleton.getInstance().setSaveSortMethod(201);
            Collections.sort(monsterList, monsterElement1Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            Singleton.getInstance().setSaveSortMethod(202);
            Collections.sort(monsterList, monsterElement2Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            Singleton.getInstance().setSaveSortMethod(301);
            Collections.sort(monsterList, monsterType1Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            Singleton.getInstance().setSaveSortMethod(302);
            Collections.sort(monsterList, monsterType2Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            Singleton.getInstance().setSaveSortMethod(303);
            Collections.sort(monsterList, monsterType3Comparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            Singleton.getInstance().setSaveSortMethod(401);
            Collections.sort(monsterList, monsterHpComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setSaveSortMethod(402);
            Collections.sort(monsterList, monsterAtkComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setSaveSortMethod(403);
            Collections.sort(monsterList, monsterRcvComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    private SortPlusDialogFragment.SortBy sortByPlus = new SortPlusDialogFragment.SortBy() {
        @Override
        public void sortTotal() {
            Singleton.getInstance().setSaveSortMethod(701);
            Collections.sort(monsterList, monsterPlusComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            Singleton.getInstance().setSaveSortMethod(702);
            Collections.sort(monsterList, monsterPlusHpComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setSaveSortMethod(703);
            Collections.sort(monsterList, monsterPlusAtkComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setSaveSortMethod(704);
            Collections.sort(monsterList, monsterPlusRcvComparator);
            saveMonsterListRecycler.notifyDataSetChanged();
        }
    };

    @Override
    public void sortArrayList(int sortMethod) {
        Singleton.getInstance().setSaveSortMethod(sortMethod);
        Log.d("Save Monster List", "monsterList before sort is:  " + monsterList);
        switch (sortMethod) {
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 2:
                if (sortElementDialogFragment == null) {
                    sortElementDialogFragment = SortElementDialogFragment.newInstance(sortByElement);
                }
                sortElementDialogFragment.show(getChildFragmentManager(), "Sort by Element");
                break;
            case 3:
                if (sortTypeDialogFragment == null) {
                    sortTypeDialogFragment = SortTypeDialogFragment.newInstance(sortByType);
                }
                sortTypeDialogFragment.show(getChildFragmentManager(), "Sort by Type");
                break;
            case 4:
                if (sortStatsDialogFragment == null) {
                    sortStatsDialogFragment = SortStatsDialogFragment.newInstance(sortByStats);
                }
                sortStatsDialogFragment.show(getChildFragmentManager(), "Sort by Stats");
                break;
            case 5:
                Collections.sort(monsterList, monsterRarityComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 6:
                Collections.sort(monsterList, monsterAwakeningComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 7:
                if (sortPlusDialogFragment == null) {
                    sortPlusDialogFragment = SortPlusDialogFragment.newInstance(sortByPlus);
                }
                sortPlusDialogFragment.show(getChildFragmentManager(), "Sort by Plus");
                break;
            case 8:
                Collections.sort(monsterList, monsterFavoriteComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 9:
                Collections.sort(monsterList, monsterLevelComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 201:
                Collections.sort(monsterList, monsterElement1Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 202:
                Collections.sort(monsterList, monsterElement2Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 301:
                Collections.sort(monsterList, monsterType1Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                Collections.sort(monsterList, monsterType2Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                Collections.sort(monsterList, monsterType3Comparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 401:
                Collections.sort(monsterList, monsterHpComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 402:
                Collections.sort(monsterList, monsterAtkComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 403:
                Collections.sort(monsterList, monsterRcvComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 701:
                Collections.sort(monsterList, monsterPlusComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 702:
                Collections.sort(monsterList, monsterPlusHpComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 703:
                Collections.sort(monsterList, monsterPlusAtkComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 704:
                Collections.sort(monsterList, monsterPlusRcvComparator);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;

        }
        Log.d("Save Monster List", "monsterList after sort is:  " + monsterList);
    }

    @Override
    public void reverseArrayList() {
        switch (Singleton.getInstance().getSaveSortMethod()) {
            case 202:
                element2Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 302:
                type2Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            case 303:
                type3Reverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;
            default:
                defaultReverse();
                saveMonsterListRecycler.setMonsterList(monsterList);
                saveMonsterListRecycler.notifyDataSetChanged();
                break;

        }
    }

    private void defaultReverse() {
        Collections.reverse(monsterList);
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void element2Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getElement2Int() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void type2Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getType2() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    private void type3Reverse() {
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (monsterList.get(i).getType3() >= 0) {
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for (int i = 0; i < sorting.size(); i++) {
            monsterList.add(i, sorting.get(i));
        }
        if (monsterList.contains(monsterZero)) {
            monsterList.remove(monsterZero);
            monsterList.add(0, monsterZero);
        }
    }

    @Override
    public void searchFilter(String query) {
        if (saveMonsterListRecycler != null) {
            Log.d("Save Monster List", "monsterList initial query is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
            if (query != null && query.length() > 0) {
                Log.d("Save Monster List", "monsterList before query is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
                if (!monsterList.isEmpty()) {
                    monsterList.clear();
                }
                Log.d("Save Monster List", "monsterList after clear is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
                filterMonsterName(query);
                filterMonsterType(query);
                filterMonsterNumber(query);
//                filterMonsterElement(query);
                Log.d("Save Monster List", "monsterList after query is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
            } else {
                Log.d("Save Monster List", "monsterList if query is nothing is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
                monsterList.clear();
                monsterList.addAll(monsterListAll);
            }
            sortArrayList(Singleton.getInstance().getSaveSortMethod());
            Log.d("Save Monster List", "saveMonsterListRecycler is: " + saveMonsterListRecycler.getMonsterList() + " query is: " + query);
//            saveMonsterListRecycler.notifyDataSetChanged(monsterList);

            Log.d("Save Monster List", "monsterList is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
            saveMonsterListRecycler.setExpandedPosition(-1);

        }


//        if(saveMonsterListRecycler!=null){
//            Log.d("Save Monster List", "Query is: " + query);
//            saveMonsterListRecycler.getFilter().filter(query);
//            monsterList = saveMonsterListRecycler.getMonsterList();
//            Log.d("Save Monster List", "monsterList is search filter:  " + monsterList);
//        }
    }

    private void filterMonsterName(String query) {
        Log.d("Save Monster List", "monsterList filter name is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
        for (Monster monster : monsterListAll) {
            if (monster.getName().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
        Log.d("Save Monster List", "monsterList after filter name is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
    }

    private void filterMonsterNumber(String query) {
        for (Monster monster : monsterListAll) {
            if (String.valueOf(monster.getBaseMonsterId()).toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterType(String query) {
        Log.d("Save Monster List", "monsterList filter type is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
        for (Monster monster : monsterListAll) {
            if (monster.getType1String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            } else if (monster.getType2String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            } else if (monster.getType3String().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
        Log.d("Save Monster List", "monsterList after filter type is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
    }

    private void filterMonsterElement(String query) {
        for (Monster monster : monsterListAll) {
            if (query.toLowerCase().equals("fire") || query.toLowerCase().equals("red")) {
                Log.d("Save Monster List", "monsterList entering FIIRERERE");
                if (monster.getElement1().equals(Element.RED) || monster.getElement2().equals(Element.RED) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("water") || query.toLowerCase().equals("blue")) {
                if (monster.getElement1().equals(Element.BLUE) || monster.getElement2().equals(Element.BLUE) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("wood") || query.toLowerCase().equals("green")) {
                if (monster.getElement1().equals(Element.GREEN) || monster.getElement2().equals(Element.GREEN) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("light") || query.toLowerCase().equals("yellow")) {
                if (monster.getElement1().equals(Element.LIGHT) || monster.getElement2().equals(Element.LIGHT) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            } else if (query.toLowerCase().equals("dark") || query.toLowerCase().equals("purple")) {
                if (monster.getElement1().equals(Element.DARK) || monster.getElement2().equals(Element.DARK) && !monsterList.contains(monster)) {
                    monsterList.add(monster);
                }
            }
        }
    }
}
