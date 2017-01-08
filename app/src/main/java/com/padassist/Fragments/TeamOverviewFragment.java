package com.padassist.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.padassist.Adapters.AwakeningGridAdapter;
import com.padassist.Adapters.MonsterSpecificAwakeningsListAdapter;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.ExpandableHeightGridView;
import com.padassist.Graphics.TooltipText;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;

public class TeamOverviewFragment extends Fragment {
    public static final String TAG = TeamOverviewFragment.class.getSimpleName();
    private TextView teamHpValue, teamRcvValue, utilityAwakeningText, damageAwakeningText, monsterSpecificText;
    private ListView monsterSpecific;
    private ExpandableHeightGridView utilityAwakenings, damageAwakenings;
    //    private RecyclerView utilityAwakenings, damageAwakenings;
    private ArrayList<Monster> monsterList;
    private ArrayList<Integer> utilityAwakeningList, damageAwakeningList, awakeningListAll, latentListAll, latentUtilityAwakeningList, latentDamageAwakeningList;
    private ArrayList<Integer> utilityFilter = new ArrayList<>();
    private ArrayList<Integer> latentUtilityFilter = new ArrayList<>();
    private ArrayList<Integer> damageFilter = new ArrayList<>();
    private ArrayList<Integer> monsterSpecificFilter = new ArrayList<>();
    private ArrayList<Integer> latentMonsterSpecificFilter = new ArrayList<>();
    private AwakeningGridAdapter utilityAwakeningGridAdapter, damageAwakeningGridAdapter;
    private MonsterSpecificAwakeningsListAdapter monsterSpecificAwakeningsListAdapter;
    private Team team;
    private OnFragmentInteractionListener mListener;
    private TextView teamCostValue;
    private CheckBox hasLeaderSkillCheck1, hasLeaderSkillCheck2;
    private ImageView hasLeaderSkill, teamBadge;
    private TeamBadgeDialogFragment teamBadgeDialogFragment;
    private Realm realm;

    // TODO: Rename and change types and number of parameters
    public static TeamOverviewFragment newInstance(Parcelable team) {
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
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.toggleCoop:
                team.setTeamStats(realm);
                setTeamStats();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        hasLeaderSkill = (ImageView) rootView.findViewById(R.id.hasLeaderSkill);
        teamBadge = (ImageView) rootView.findViewById(R.id.teamBadge);
        hasLeaderSkillCheck1 = (CheckBox) rootView.findViewById(R.id.hasLeaderSkillCheck1);
        hasLeaderSkillCheck2 = (CheckBox) rootView.findViewById(R.id.hasLeaderSkillCheck2);
        teamCostValue = (TextView) rootView.findViewById(R.id.teamCostValue);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = Parcels.unwrap(getArguments().getParcelable("team"));
        }
        realm = Realm.getDefaultInstance();
        awakeningListAll = team.getAwakenings();
        latentListAll = team.getLatents();
        utilityAwakeningList = new ArrayList<>();
        latentUtilityAwakeningList = new ArrayList<>();
        damageAwakeningList = new ArrayList<>();
        latentDamageAwakeningList = new ArrayList<>();
        monsterList = new ArrayList<>();
        monsterList.addAll(team.getMonsters());
        setTeamStats();
        setupAwakeningFilters();
        trimAwakenings();
        setTeamBadge();
        setCheckBoxes();
        utilityAwakeningGridAdapter = new AwakeningGridAdapter(getActivity(), team.getMonsters(), utilityAwakeningList, latentUtilityAwakeningList, false, team.getTeamBadge());
        utilityAwakenings.setAdapter(utilityAwakeningGridAdapter);
//        utilityAwakenings.setHasFixedSize(true);
//        utilityAwakenings.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        utilityAwakenings.setExpanded(true);
        damageAwakeningGridAdapter = new AwakeningGridAdapter(getActivity(), team.getMonsters(), damageAwakeningList, latentDamageAwakeningList, false, team.getTeamBadge());
        damageAwakenings.setAdapter(damageAwakeningGridAdapter);
//        damageAwakenings.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        damageAwakenings.setExpanded(true);
        monsterSpecificAwakeningsListAdapter = new MonsterSpecificAwakeningsListAdapter(getActivity(), R.layout.monster_specific_awakening_list_row, monsterList, monsterSpecificFilter, latentMonsterSpecificFilter, team.getTeamBadge());
        monsterSpecific.setAdapter(monsterSpecificAwakeningsListAdapter);
        setListViewHeightBasedOnChildren(monsterSpecific);
        getActivity().setTitle("Team Overview");
        teamBadge.setOnClickListener(teamBadgeOnClickListener);
        hasLeaderSkillCheck1.setOnCheckedChangeListener(checkBoxOnChangeListener);
        hasLeaderSkillCheck2.setOnCheckedChangeListener(checkBoxOnChangeListener);
        hasLeaderSkill.setOnClickListener(tooltipOnClickListener);
    }

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
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
        utilityFilter.add(29);
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
        monsterSpecificFilter.add(30);
        monsterSpecificFilter.add(31);
        monsterSpecificFilter.add(32);
        monsterSpecificFilter.add(33);
        monsterSpecificFilter.add(34);
        monsterSpecificFilter.add(35);
        monsterSpecificFilter.add(36);
        monsterSpecificFilter.add(37);
        monsterSpecificFilter.add(38);
        monsterSpecificFilter.add(39);
        monsterSpecificFilter.add(40);
        monsterSpecificFilter.add(41);
        monsterSpecificFilter.add(42);
        monsterSpecificFilter.add(43);
        latentUtilityFilter.add(4);
        latentUtilityFilter.add(6);
        latentUtilityFilter.add(7);
        latentUtilityFilter.add(8);
        latentUtilityFilter.add(9);
        latentUtilityFilter.add(10);
        latentMonsterSpecificFilter.add(1);
        latentMonsterSpecificFilter.add(2);
        latentMonsterSpecificFilter.add(3);
        latentMonsterSpecificFilter.add(5);
        latentMonsterSpecificFilter.add(11);
        latentMonsterSpecificFilter.add(12);
        latentMonsterSpecificFilter.add(13);
        latentMonsterSpecificFilter.add(14);
        latentMonsterSpecificFilter.add(15);
        latentMonsterSpecificFilter.add(16);
        latentMonsterSpecificFilter.add(17);
        latentMonsterSpecificFilter.add(18);
        latentMonsterSpecificFilter.add(19);
        latentMonsterSpecificFilter.add(20);
    }

    private void trimAwakenings() {
        for (int i = 0; i < awakeningListAll.size(); i++) {
            if (utilityFilter.contains(awakeningListAll.get(i))) {
                utilityAwakeningList.add(awakeningListAll.get(i));
            } else if (damageFilter.contains(awakeningListAll.get(i))) {
                damageAwakeningList.add(awakeningListAll.get(i));
            }
        }

        for (int i = 0; i < latentListAll.size(); i++) {
            if (latentUtilityFilter.contains(latentListAll.get(i))) {
                latentUtilityAwakeningList.add(latentListAll.get(i));
            }
        }
//        if (monsterList.size() == 0) {
//            monsterList.addAll(team.getMonsters());
//        }
        ArrayList<Integer> tempAwakenings = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            if (!latentMonsterSpecificFilter.contains(monsterList.get(i).getLatents().get(0).getValue()) && !latentMonsterSpecificFilter.contains(monsterList.get(i).getLatents().get(1).getValue()) && !latentMonsterSpecificFilter.contains(monsterList.get(i).getLatents().get(2).getValue()) && !latentMonsterSpecificFilter.contains(monsterList.get(i).getLatents().get(3).getValue()) && !latentMonsterSpecificFilter.contains(monsterList.get(i).getLatents().get(4).getValue())) {
                if (monsterList.get(i).getCurrentAwakenings() < monsterList.get(i).getMaxAwakenings()) {
                    for (int j = 0; j < monsterList.get(i).getCurrentAwakenings(); j++) {
                        if (monsterSpecificFilter.contains(monsterList.get(i).getAwokenSkills(j))) {
                            tempAwakenings.add(monsterList.get(i).getAwokenSkills(j));
                        }
                    }
                } else {
                    for (int j = 0; j < monsterList.get(i).getMaxAwakenings(); j++) {
                        if (monsterSpecificFilter.contains(monsterList.get(i).getAwokenSkills(j))) {
                            tempAwakenings.add(monsterList.get(i).getAwokenSkills(j));
                        }
                    }
                }

                if (tempAwakenings.size() == 0) {
                    monsterList.remove(i);
                    i--;
                } else {
                    tempAwakenings.clear();
                }
            }
        }
        if (utilityAwakeningList.size() == 0 && latentUtilityAwakeningList.size() == 0) {
            utilityAwakenings.setVisibility(View.GONE);
            utilityAwakeningText.setText("No Utility Awakenings!");
        } else {
            utilityAwakenings.setVisibility(View.VISIBLE);
            utilityAwakeningText.setText("Utility Awakenings");
        }
        if (damageAwakeningList.size() == 0) {
            damageAwakenings.setVisibility(View.GONE);
            damageAwakeningText.setText("No Damage Awakenings!");
        } else {
            damageAwakenings.setVisibility(View.VISIBLE);
            damageAwakeningText.setText("Damage Awakenings");
        }
        if (monsterList.size() == 0) {
            monsterSpecific.setVisibility(View.GONE);
            monsterSpecificText.setText("No Monster Specific Awakenings!");
        } else {
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

    private void setTeamStats() {
//        int teamHp = 0;
//        double teamRcv = 0;
//        Log.d("Team Overview", "Leadskill is: " + team.getLeadSkill());
//        for(int i = 0; i < team.getMonsters().size(); i++){
//            teamHp += DamageCalculationUtil.monsterHpCalc(team.getMonsters(i), team);
//            teamRcv += DamageCalculationUtil.monsterRcvCalc(team.getMonsters(i), team);
//        }
        teamHpValue.setText(String.valueOf(team.getTeamHealth()));
        teamRcvValue.setText(String.valueOf(team.getTeamRcv()));
        if(team.getTeamBadge() == 10 || team.getTeamBadge() == 12 || team.getTeamBadge() == 14){
            teamCostValue.setText(String.valueOf(team.getTeamCost()) + "(+300)");
        } else if(team.getTeamBadge() == 11){
            teamCostValue.setText(String.valueOf(team.getTeamCost()) + "(+400)");
        } else {
            teamCostValue.setText(String.valueOf(team.getTeamCost()));
        }
    }

    private void setTeamBadge(){
        teamBadge.setImageResource(ImageResourceUtil.teamBadge(team.getTeamBadge()));
    }

    private void setCheckBoxes() {
        hasLeaderSkillCheck1.setChecked(Singleton.getInstance().hasLeaderSkill());
        hasLeaderSkillCheck2.setChecked(Singleton.getInstance().hasHelperSkill());
    }

    private View.OnClickListener teamBadgeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (teamBadgeDialogFragment == null) {
                teamBadgeDialogFragment = TeamBadgeDialogFragment.newInstance(setTeamBadge);
            }
            if (!teamBadgeDialogFragment.isAdded()) {
                teamBadgeDialogFragment.show(getActivity().getSupportFragmentManager(), team, "Team Badge Dialog");
            }
        }
    };

    private TeamBadgeDialogFragment.SetTeamBadge setTeamBadge = new TeamBadgeDialogFragment.SetTeamBadge() {
        @Override
        public void setBadge(int badge) {
            realm.beginTransaction();
            team.setTeamBadge(badge);
            if(team.getIsBound().get(0) && badge == 8){
                team.getIsBound().set(0, false);
            }
            realm.commitTransaction();
            setTeamBadge();
            team.setTeamStats(realm);
            setTeamStats();
            utilityAwakeningGridAdapter.updateTeamBadge(team.getTeamBadge());
        }
    };

    private CompoundButton.OnCheckedChangeListener checkBoxOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.equals(hasLeaderSkillCheck1)){
                Singleton.getInstance().setHasLeaderSkill(isChecked);
            } else if (buttonView.equals(hasLeaderSkillCheck2)){
                Singleton.getInstance().setHasHelperSkill(isChecked);
            }
            team.setTeamStats(realm);
            setTeamStats();
        }
    };

    private View.OnClickListener tooltipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            TooltipText tooltipText;
            switch (id) {
                case R.id.hasLeaderSkill:
                    tooltipText = new TooltipText(getContext(), "Enable Leader skills for leader and helper");
                    break;
                default:
                    tooltipText = new TooltipText(getContext(), "How did you get this tooltip?");
            }
            tooltipText.show(v);
        }
    };

}
