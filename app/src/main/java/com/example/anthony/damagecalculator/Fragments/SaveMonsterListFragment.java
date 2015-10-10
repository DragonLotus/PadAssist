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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.anthony.damagecalculator.Adapters.SaveMonsterListAdapter;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SaveMonsterListFragment extends AbstractFragment {
    public static final String TAG = SaveMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private int sortMethod;
    private ListView monsterListView;
    private ArrayList<Monster> monsterList;
    private SaveMonsterListAdapter saveMonsterListAdapter;
    private Toast toast;
    private TextView savedMonsters;
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

    public static SaveMonsterListFragment newInstance() {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SaveMonsterListFragment() {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_monster_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        savedMonsters = (TextView) rootView.findViewById(R.id.savedMonsters);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        monsterList = (ArrayList) Monster.getAllMonsters();
        if(monsterList.size() == 0){
            savedMonsters.setVisibility(View.VISIBLE);
        }else{
            savedMonsters.setVisibility(View.GONE);
        }
        //disableStuff();
        saveMonsterListAdapter = new SaveMonsterListAdapter(getActivity(), R.layout.save_monster_list_row, monsterList);
        monsterListView.setAdapter(saveMonsterListAdapter);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);
    }

    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Team newTeam = new Team(Team.getTeamById(0));
            if(monsterList.get(position).getMonsterId() == 0 && newTeam.getMonsterOverwrite() == 0){
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                switch (newTeam.getMonsterOverwrite()) {
                    case 0:
                        newTeam.setLead(monsterList.get(position));
                        break;
                    case 1:
                        newTeam.setSub1(monsterList.get(position));
                        break;
                    case 2:
                        newTeam.setSub2(monsterList.get(position));
                        break;
                    case 3:
                        newTeam.setSub3(monsterList.get(position));
                        break;
                    case 4:
                        newTeam.setSub4(monsterList.get(position));
                        break;
                    case 5:
                        newTeam.setHelper(monsterList.get(position));
                        break;
                }
                newTeam.save();
                Log.d("Save Monster Log", "Team is: " + newTeam.getMonsters());
                Log.d("Save Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    };

    private void disableStuff(){
        for (int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()){
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

    private SortElementDialogFragment.SortBy sortByElement = new SortElementDialogFragment.SortBy(){
        @Override
        public void sortElement1() {
            sortMethod = 201;
            Collections.sort(monsterList, monsterElement1Comparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            sortMethod = 202;
            Collections.sort(monsterList, monsterElement2Comparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy(){
        @Override
        public void sortType1() {
            sortMethod = 301;
            Collections.sort(monsterList, monsterType1Comparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            sortMethod = 302;
            Collections.sort(monsterList, monsterType2Comparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            sortMethod = 303;
            Collections.sort(monsterList, monsterType3Comparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            sortMethod = 401;
            Collections.sort(monsterList, monsterHpComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            sortMethod = 402;
            Collections.sort(monsterList, monsterAtkComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            sortMethod = 403;
            Collections.sort(monsterList, monsterRcvComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }
    };

    private SortPlusDialogFragment.SortBy sortByPlus = new SortPlusDialogFragment.SortBy() {
        @Override
        public void sortTotal() {
            sortMethod = 701;
            Collections.sort(monsterList, monsterPlusComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortHp() {
            sortMethod = 702;
            Collections.sort(monsterList, monsterPlusHpComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            sortMethod = 703;
            Collections.sort(monsterList, monsterPlusAtkComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            sortMethod = 704;
            Collections.sort(monsterList, monsterPlusRcvComparator);
            saveMonsterListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void sortArrayList(int sortMethod) {
        this.sortMethod = sortMethod;
        switch(sortMethod){
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                saveMonsterListAdapter.notifyDataSetChanged();
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
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 6:
                Collections.sort(monsterList, monsterAwakeningComparator);
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 7:
                if (sortPlusDialogFragment == null) {
                    sortPlusDialogFragment = SortPlusDialogFragment.newInstance(sortByPlus);
                }
                sortPlusDialogFragment.show(getChildFragmentManager(), "Sort by Plus");
                break;
            case 8:
                Collections.sort(monsterList, monsterFavoriteComparator);
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 9:
                Collections.sort(monsterList, monsterLevelComparator);
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void reverseArrayList() {
        switch(sortMethod){
            case 202:
                element2Reverse();
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 302:
                type2Reverse();
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            case 303:
                type3Reverse();
                saveMonsterListAdapter.notifyDataSetChanged();
                break;
            default:
                defaultReverse();
                saveMonsterListAdapter.notifyDataSetChanged();
                break;

        }
    }

    private void defaultReverse(){
        Collections.reverse(monsterList);
        monsterList.remove(Monster.getMonsterId(0));
        monsterList.add(0, Monster.getMonsterId(0));
    }

    private void element2Reverse(){
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getElement2Int() >= 0){
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for(int i = 0; i < sorting.size(); i++){
            monsterList.add(i, sorting.get(i));
        }
        monsterList.remove(Monster.getMonsterId(0));
        monsterList.add(0, Monster.getMonsterId(0));
    }

    private void type2Reverse(){
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getType2() >= 0){
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for(int i = 0; i < sorting.size(); i++){
            monsterList.add(i, sorting.get(i));
        }
        monsterList.remove(Monster.getMonsterId(0));
        monsterList.add(0, Monster.getMonsterId(0));
    }

    private void type3Reverse(){
        ArrayList<Monster> sorting = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++){
            if(monsterList.get(i).getType3() >= 0){
                sorting.add(monsterList.get(i));
                monsterList.remove(i);
                i--;
            }
        }
        Collections.reverse(sorting);
        for(int i = 0; i < sorting.size(); i++){
            monsterList.add(i, sorting.get(i));
        }
        monsterList.remove(Monster.getMonsterId(0));
        monsterList.add(0, Monster.getMonsterId(0));
    }
}
