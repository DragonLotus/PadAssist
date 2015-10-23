package com.example.anthony.damagecalculator.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.anthony.damagecalculator.Adapters.AwakeningGridAdapter;
import com.example.anthony.damagecalculator.Adapters.MonsterSpecificAwakeningsListAdapter;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Graphics.ExpandableHeightGridView;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.util.ArrayList;

public class TeamOverviewFragment extends AbstractFragment {
    public static final String TAG = TeamOverviewFragment.class.getSimpleName();
    private TextView teamHpValue, teamRcvValue, utilityAwakeningText, damageAwakeningText, monsterSpecificText;
    private ListView monsterSpecific;
    private ExpandableHeightGridView utilityAwakenings, damageAwakenings;
    private ArrayList<Monster> monsterList;
    private ArrayList<Integer> utilityAwakeningList, damageAwakeningList, awakeningListAll;
    private ArrayList<Integer> utilityFilter = new ArrayList<>();
    private ArrayList<Integer> damageFilter = new ArrayList<>();
    private ArrayList<Integer> monsterSpecificFilter = new ArrayList<>();
    private AwakeningGridAdapter utilityAwakeningGridAdapter, damageAwakeningGridAdapter;
    private MonsterSpecificAwakeningsListAdapter monsterSpecificAwakeningsListAdapter;
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
        utilityAwakeningText = (TextView) rootView.findViewById(R.id.utilityAwakeningText);
        damageAwakeningText = (TextView) rootView.findViewById(R.id.damageAwakeningText);
        monsterSpecificText = (TextView) rootView.findViewById(R.id.monsterSpecificText);
        utilityAwakenings = (ExpandableHeightGridView) rootView.findViewById(R.id.utilityAwakenings);
        damageAwakenings = (ExpandableHeightGridView) rootView.findViewById(R.id.damageAwakenings);
        monsterSpecific = (ListView) rootView.findViewById(R.id.monsterSpecific);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
        }
        awakeningListAll = team.getAwakenings();
        utilityAwakeningList = new ArrayList<>();
        damageAwakeningList = new ArrayList<>();
        monsterList = new ArrayList<>();
        monsterList.addAll(team.getMonsters());
        setTeamStats();
        setupAwakeningFilters();
        trimAwakenings();
        utilityAwakeningGridAdapter = new AwakeningGridAdapter(getActivity(), utilityAwakeningList);
        utilityAwakenings.setAdapter(utilityAwakeningGridAdapter);
        utilityAwakenings.setExpanded(true);
        damageAwakeningGridAdapter = new AwakeningGridAdapter(getActivity(), damageAwakeningList);
        damageAwakenings.setAdapter(damageAwakeningGridAdapter);
        damageAwakenings.setExpanded(true);
        monsterSpecificAwakeningsListAdapter = new MonsterSpecificAwakeningsListAdapter(getActivity(), R.layout.monster_specific_awakening_list_row, monsterList);
        monsterSpecific.setAdapter(monsterSpecificAwakeningsListAdapter);
        setListViewHeightBasedOnChildren(monsterSpecific);
        Log.d("Team Overview Log", "Utility filter is: " + utilityFilter);
        Log.d("Team Overview Log", "Damage filter is: " + damageFilter);
        Log.d("Team Overview Log", "Monster Specific filter is: " + monsterSpecificFilter);
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

    private void setupAwakeningFilters() {
        utilityFilter.add(4);
        utilityFilter.add(5);
        utilityFilter.add(6);
        utilityFilter.add(7);
        utilityFilter.add(8);
        utilityFilter.add(9);
        utilityFilter.add(11);
        utilityFilter.add(12);
        utilityFilter.add(13);
        utilityFilter.add(19);
        utilityFilter.add(20);
        utilityFilter.add(21);
        utilityFilter.add(28);
        damageFilter.add(14);
        damageFilter.add(15);
        damageFilter.add(16);
        damageFilter.add(17);
        damageFilter.add(18);
        damageFilter.add(22);
        damageFilter.add(23);
        damageFilter.add(24);
        damageFilter.add(25);
        damageFilter.add(26);
        monsterSpecificFilter.add(1);
        monsterSpecificFilter.add(2);
        monsterSpecificFilter.add(3);
        monsterSpecificFilter.add(10);
        monsterSpecificFilter.add(27);
    }

    private void trimAwakenings() {
        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (utilityFilter.contains(awakeningListAll.get(i))) {
                utilityAwakeningList.add(awakeningListAll.get(i));
            } else if (damageFilter.contains(awakeningListAll.get(i))) {
                damageAwakeningList.add(awakeningListAll.get(i));
            }
        }
//        if (monsterList.size() == 0) {
//            monsterList.addAll(team.getMonsters());
//        }
        ArrayList<Integer> tempAwakenings = new ArrayList<>();
        for(int i = 0; i < monsterList.size(); i++){
            for(int j = 0; j < monsterList.get(i).getCurrentAwakenings(); j++){
                if (monsterSpecificFilter.contains(monsterList.get(i).getAwokenSkills(j))){
                    tempAwakenings.add(monsterList.get(i).getAwokenSkills(j));
                }
            }
            if(tempAwakenings.size() == 0){
                monsterList.remove(i);
                i--;
            }else {
                tempAwakenings.clear();
            }
        }
        if(utilityAwakeningList.size() == 0){
            utilityAwakenings.setVisibility(View.GONE);
            utilityAwakeningText.setText("No Utility Awakenings!");
        }else {
            utilityAwakenings.setVisibility(View.VISIBLE);
            utilityAwakeningText.setText("Utility Awakenings");
        }
        if(damageAwakeningList.size() == 0){
            damageAwakenings.setVisibility(View.GONE);
            damageAwakeningText.setText("No Damage Awakenings!");
        }else {
            damageAwakenings.setVisibility(View.VISIBLE);
            damageAwakeningText.setText("Damage Awakenings");
        }
        if(monsterList.size() == 0){
            monsterSpecific.setVisibility(View.GONE);
            monsterSpecificText.setText("No Monster Specific Awakenings!");
        }else {
            monsterSpecific.setVisibility(View.VISIBLE);
            monsterSpecificText.setText("Monster Specific Awakenings");
        }
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setTeamStats(){
//        int teamHp = 0;
//        double teamRcv = 0;
//        Log.d("Team Overview", "Leadskill is: " + team.getLeadSkill());
//        for(int i = 0; i < team.getMonsters().size(); i++){
//            teamHp += DamageCalculationUtil.monsterHpCalc(team.getMonsters(i), team);
//            teamRcv += DamageCalculationUtil.monsterRcvCalc(team.getMonsters(i), team);
//        }
        teamHpValue.setText(String.valueOf(team.getTeamHealth()));
        teamRcvValue.setText(String.valueOf(team.getTeamRcv()));
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
