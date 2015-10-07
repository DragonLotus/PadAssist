package com.example.anthony.damagecalculator.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.anthony.damagecalculator.Adapters.BaseMonsterListAdapter;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.BaseMonsterAlphabeticalComparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterElement1Comparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterElement2Comparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterNumberComparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterType1Comparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterType2Comparator;
import com.example.anthony.damagecalculator.Util.BaseMonsterType3Comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BaseMonsterListFragment extends AbstractFragment {
    public static final String TAG = BaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private ListView monsterListView;
    private ArrayList<BaseMonster> monsterList;
    private BaseMonsterListAdapter baseMonsterListAdapter;
    private Comparator<BaseMonster> monsterNumberComparator = new BaseMonsterNumberComparator();
    private Comparator<BaseMonster> monsterElement1Comparator = new BaseMonsterElement1Comparator();
    private Comparator<BaseMonster> monsterElement2Comparator = new BaseMonsterElement2Comparator();
    private Comparator<BaseMonster> monsterType1Comparator = new BaseMonsterType1Comparator();
    private Comparator<BaseMonster> monsterType2Comparator = new BaseMonsterType2Comparator();
    private Comparator<BaseMonster> monsterType3Comparator = new BaseMonsterType3Comparator();
    private Comparator<BaseMonster> monsterAlphabeticalComparator = new BaseMonsterAlphabeticalComparator();

    public static BaseMonsterListFragment newInstance() {
        BaseMonsterListFragment fragment = new BaseMonsterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public BaseMonsterListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_monster_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.sortGroup, true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        monsterList = (ArrayList) BaseMonster.getAllMonsters();
        Log.d("Base Monster List Log", "monsterList is before: " + monsterList);
        for (int i = 0; i < monsterList.size(); i++){
            Log.d("Base Monster List Log", "Monster Type 1 before is: " + monsterList.get(i).getType1());
        }
        Collections.sort(monsterList, monsterNumberComparator);
        //Collections.sort(monsterList, monsterElement1Comparator);
        //Collections.sort(monsterList, monsterElement2Comparator);
        //Collections.sort(monsterList, monsterType1Comparator);
        //Collections.sort(monsterList, monsterType2Comparator);
        //Collections.sort(monsterList, monsterType3Comparator);
        //Collections.sort(monsterList, monsterAlphabeticalComparator);
        Log.d("Base Monster List Log", "monsterList is after: " + monsterList);
        for (int i = 0; i < monsterList.size(); i++){
            Log.d("Base Monster List Log", "Monster Type 1 after is: " + monsterList.get(i).getType1());
        }
        //disableStuff();
        baseMonsterListAdapter = new BaseMonsterListAdapter(getActivity(), R.layout.base_monster_list_row, monsterList);
        monsterListView.setAdapter(baseMonsterListAdapter);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);
    }

    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Team newTeam = new Team(Team.getTeamById(0));
            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
            if(monsterList.get(position).getMonsterId() == 0){
                newMonster.setMonsterId(0);
            }else if (Monster.getAllMonsters().size() == 0){
                newMonster.setMonsterId(1);
                newMonster.save();
            }else  {
                newMonster.setMonsterId(Monster.getAllMonsters().get(Monster.getAllMonsters().size() - 1).getMonsterId() + 1);
                newMonster.save();
            }
            Log.d("Base Monster Log", "New Monster Id: " + newMonster.getMonsterId());
            if (newMonster.getMonsterId()== 0){
                switch (newTeam.getMonsterOverwrite()){
                    case 0:
                        newTeam.setLead(Monster.getMonsterId(0));
                        break;
                    case 1:
                        newTeam.setSub1(Monster.getMonsterId(0));
                        break;
                    case 2:
                        newTeam.setSub2(Monster.getMonsterId(0));
                        break;
                    case 3:
                        newTeam.setSub3(Monster.getMonsterId(0));
                        break;
                    case 4:
                        newTeam.setSub4(Monster.getMonsterId(0));
                        break;
                    case 5:
                        newTeam.setHelper(Monster.getMonsterId(0));
                        break;
                }
            }else {
                switch (newTeam.getMonsterOverwrite()) {
                    case 0:
                        newTeam.setLead(newMonster);
                        break;
                    case 1:
                        newTeam.setSub1(newMonster);
                        break;
                    case 2:
                        newTeam.setSub2(newMonster);
                        break;
                    case 3:
                        newTeam.setSub3(newMonster);
                        break;
                    case 4:
                        newTeam.setSub4(newMonster);
                        break;
                    case 5:
                        newTeam.setHelper(newMonster);
                        break;
                }
            }
            newTeam.save();
            Log.d("Base Monster Log", "Team is: " + newTeam.getMonsters());
            Log.d("Base Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };
//
//    private void disableStuff(){
//        for (int i = 0; i < monsterList.size(); i++){
//            if(monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()){
//                monsterList.remove(i);
//            }
//        }
//    }
//

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
    public void sortArrayList(int sortMethod) {
        switch(sortMethod){
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 2:
                Collections.sort(monsterList, monsterElement1Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 3:
                Collections.sort(monsterList, monsterElement2Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 4:
                Collections.sort(monsterList, monsterType1Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 5:
                Collections.sort(monsterList, monsterType2Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 6:
                Collections.sort(monsterList, monsterType3Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
        }
    }
}
