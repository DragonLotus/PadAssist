package com.padassist.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.BaseMonsterAlphabeticalComparator;
import com.padassist.Util.BaseMonsterAtkComparator;
import com.padassist.Util.BaseMonsterAwakeningComparator;
import com.padassist.Util.BaseMonsterElement1Comparator;
import com.padassist.Util.BaseMonsterElement2Comparator;
import com.padassist.Util.BaseMonsterHpComparator;
import com.padassist.Util.BaseMonsterNumberComparator;
import com.padassist.Util.BaseMonsterRarityComparator;
import com.padassist.Util.BaseMonsterRcvComparator;
import com.padassist.Util.BaseMonsterType1Comparator;
import com.padassist.Util.BaseMonsterType2Comparator;
import com.padassist.Util.BaseMonsterType3Comparator;
import com.padassist.Util.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BaseMonsterListFragment extends AbstractFragment {
    public static final String TAG = BaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private boolean replaceAll;
    private long replaceMonsterId;
    private RecyclerView monsterListView;
    private ArrayList<BaseMonster> monsterList;
    private ArrayList<BaseMonster> monsterListAll;
    private BaseMonsterListRecycler baseMonsterListAdapter;
    private MenuItem searchMenuItem;
    private Toast toast;
    private SortElementDialogFragment sortElementDialogFragment;
    private SortTypeDialogFragment sortTypeDialogFragment;
    private SortStatsDialogFragment sortStatsDialogFragment;
    private Comparator<BaseMonster> monsterNumberComparator = new BaseMonsterNumberComparator();
    private Comparator<BaseMonster> monsterElement1Comparator = new BaseMonsterElement1Comparator();
    private Comparator<BaseMonster> monsterElement2Comparator = new BaseMonsterElement2Comparator();
    private Comparator<BaseMonster> monsterType1Comparator = new BaseMonsterType1Comparator();
    private Comparator<BaseMonster> monsterType2Comparator = new BaseMonsterType2Comparator();
    private Comparator<BaseMonster> monsterType3Comparator = new BaseMonsterType3Comparator();
    private Comparator<BaseMonster> monsterAlphabeticalComparator = new BaseMonsterAlphabeticalComparator();
    private Comparator<BaseMonster> monsterHpComparator = new BaseMonsterHpComparator();
    private Comparator<BaseMonster> monsterAtkComparator = new BaseMonsterAtkComparator();
    private Comparator<BaseMonster> monsterRcvComparator = new BaseMonsterRcvComparator();
    private Comparator<BaseMonster> monsterRarityComparator = new BaseMonsterRarityComparator();
    private Comparator<BaseMonster> monsterAwakeningComparator = new BaseMonsterAwakeningComparator();

   private FastScroller fastScroller;

    public static BaseMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId) {
        BaseMonsterListFragment fragment = new BaseMonsterListFragment();
        Bundle args = new Bundle();
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putBoolean("replaceAll", replaceAll);
        fragment.setArguments(args);
        return fragment;
    }

    public BaseMonsterListFragment() {
    }

    @Override
        public void onDestroy() {
        super.onDestroy();
        if(searchMenuItem != null){
            if(MenuItemCompat.isActionViewExpanded(searchMenuItem)){
                MenuItemCompat.collapseActionView(searchMenuItem);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_base_monster_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
        fastScroller = (FastScroller) rootView.findViewById(R.id.fastScroller);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.sortGroup, true);
        menu.findItem(R.id.search).setVisible(true);
        searchMenuItem = menu.findItem(R.id.search);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }
        monsterListAll = (ArrayList) BaseMonster.getAllMonsters();
        if (monsterList == null) {
            monsterList = new ArrayList<>();
            monsterList.addAll(monsterListAll);
        }
//        Log.d("Base Monster List Log", "monsterList is before: " + monsterList);
//        for (int i = 0; i < monsterList.size(); i++) {
//            Log.d("Base Monster List Log", "Monster Type 1 before is: " + monsterList.get(i).getType1());
//        }
//        searchFilter("");
//        Collections.sort(monsterList, monsterNumberComparator);
//        sortMethod = 1;
//        Log.d("Base Monster List Log", "monsterList is after: " + monsterList);
//        for (int i = 0; i < monsterList.size(); i++) {
//            Log.d("Base Monster List Log", "Monster Type 1 after is: " + monsterList.get(i).getType1());
//        }
        //disableStuff();
        baseMonsterListAdapter = new BaseMonsterListRecycler(getActivity(), monsterList, expandChange, monsterListOnClickListener, monsterListOnLongClickListener);
        monsterListView.setAdapter(baseMonsterListAdapter);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fastScroller.setRecyclerView(monsterListView);
//        monsterListView.setOnItemClickListener(monsterListOnClickListener);
    }

    private BaseMonsterListRecycler.ExpandChange expandChange = new BaseMonsterListRecycler.ExpandChange() {
        @Override
        public void onExpandChange(int expandedPosition) {
//            if(expandedPosition >= 0){
//                fastScroller.resizeScrollBar(true, FastScroller.BASE_MONSTER_LIST);
//            }else{
//                fastScroller.resizeScrollBar(false, FastScroller.BASE_MONSTER_LIST);
//            }
        }
    };

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = new Team(Team.getTeamById(0));
            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
            if (monsterList.get(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (monsterList.get(position).getMonsterId() == 0) {
                    newMonster.setMonsterId(0);
                } else if (Monster.getAllMonsters().size() == 0) {
                    newMonster.setMonsterId(1);
                    newMonster.save();
                } else {
                    newMonster.setMonsterId(Monster.getAllMonsters().get(Monster.getAllMonsters().size() - 1).getMonsterId() + 1);
                    if(Singleton.getInstance().getMonsterOverwrite() == 5){
                        newMonster.setHelper(true);
                    }
                    newMonster.save();
                }
                if (replaceAll) {
                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
                    Team replaceTeam;
                    for (int i = 0; i < teamList.size(); i++) {
                        replaceTeam = teamList.get(i);
                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                replaceTeam.setMonsters(j, newMonster);
                            }
                        }
                        replaceTeam.save();
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                    //getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Log.d("Base Monster Log", "New Monster Id: " + newMonster.getMonsterId());
                    if (newMonster.getMonsterId() == 0) {
                        switch (Singleton.getInstance().getMonsterOverwrite()) {
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
                    } else {
                        switch (Singleton.getInstance().getMonsterOverwrite()) {
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
                    if (replaceMonsterId == 0){
                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
                    }
                }
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener(){
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = new Team(Team.getTeamById(0));
            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
            if (monsterList.get(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (monsterList.get(position).getMonsterId() == 0) {
                    newMonster.setMonsterId(0);
                } else if (Monster.getAllMonsters().size() == 0) {
                    newMonster.setMonsterId(1);
                    newMonster.save();
                } else {
                    newMonster.setMonsterId(Monster.getAllMonsters().get(Monster.getAllMonsters().size() - 1).getMonsterId() + 1);
                    if(Singleton.getInstance().getMonsterOverwrite() == 5){
                        newMonster.setHelper(true);
                    }
                    newMonster.save();
                }
                if (replaceAll) {
                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
                    Team replaceTeam;
                    for (int i = 0; i < teamList.size(); i++) {
                        replaceTeam = teamList.get(i);
                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                replaceTeam.setMonsters(j, newMonster);
                            }
                        }
                        replaceTeam.save();
                    }
                    getActivity().getSupportFragmentManager().popBackStack();
                    //getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Log.d("Base Monster Log", "New Monster Id: " + newMonster.getMonsterId());
                    if (newMonster.getMonsterId() == 0) {
                        switch (Singleton.getInstance().getMonsterOverwrite()) {
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
                    } else {
                        switch (Singleton.getInstance().getMonsterOverwrite()) {
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
                    if (replaceMonsterId == 0){
                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
                    }
                }
            }
            return false;
        }
    };

//    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Team newTeam = new Team(Team.getTeamById(0));
//            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
//            if (monsterList.get(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (monsterList.get(position).getMonsterId() == 0) {
//                    newMonster.setMonsterId(0);
//                } else if (Monster.getAllMonsters().size() == 0) {
//                    newMonster.setMonsterId(1);
//                    newMonster.save();
//                } else {
//                    newMonster.setMonsterId(Monster.getAllMonsters().get(Monster.getAllMonsters().size() - 1).getMonsterId() + 1);
//                    if(Singleton.getInstance().getMonsterOverwrite() == 5){
//                        newMonster.setHelper(true);
//                    }
//                    newMonster.save();
//                }
//                if (replaceAll) {
//                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//                    Team replaceTeam;
//                    for (int i = 0; i < teamList.size(); i++) {
//                        replaceTeam = teamList.get(i);
//                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
//                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
//                                replaceTeam.setMonsters(j, newMonster);
//                            }
//                        }
//                        replaceTeam.save();
//                    }
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    //getActivity().getSupportFragmentManager().popBackStack();
//                } else {
//                    Log.d("Base Monster Log", "New Monster Id: " + newMonster.getMonsterId());
//                    if (newMonster.getMonsterId() == 0) {
//                        switch (Singleton.getInstance().getMonsterOverwrite()) {
//                            case 0:
//                                newTeam.setLead(Monster.getMonsterId(0));
//                                break;
//                            case 1:
//                                newTeam.setSub1(Monster.getMonsterId(0));
//                                break;
//                            case 2:
//                                newTeam.setSub2(Monster.getMonsterId(0));
//                                break;
//                            case 3:
//                                newTeam.setSub3(Monster.getMonsterId(0));
//                                break;
//                            case 4:
//                                newTeam.setSub4(Monster.getMonsterId(0));
//                                break;
//                            case 5:
//                                newTeam.setHelper(Monster.getMonsterId(0));
//                                break;
//                        }
//                    } else {
//                        switch (Singleton.getInstance().getMonsterOverwrite()) {
//                            case 0:
//                                newTeam.setLead(newMonster);
//                                break;
//                            case 1:
//                                newTeam.setSub1(newMonster);
//                                break;
//                            case 2:
//                                newTeam.setSub2(newMonster);
//                                break;
//                            case 3:
//                                newTeam.setSub3(newMonster);
//                                break;
//                            case 4:
//                                newTeam.setSub4(newMonster);
//                                break;
//                            case 5:
//                                newTeam.setHelper(newMonster);
//                                break;
//                        }
//                    }
//                    newTeam.save();
//                    Log.d("Base Monster Log", "Team is: " + newTeam.getMonsters());
//                    Log.d("Base Monster Log", "Sub 4 Level is: " + newTeam.getSub4().getCurrentLevel());
//                    getActivity().getSupportFragmentManager().popBackStack();
//                    if (replaceMonsterId == 0){
//                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
//                    }
//                }
//            }
//
//        }
//    };
//
//    private void disableStuff(){
//        for (int i = 0; i < monsterList.size(); i++){
//            if(monsterList.get(i).getMonsterId() == Team.getTeamById(0).getLead().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub1().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub2().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub3().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getSub4().getMonsterId() || monsterList.get(i).getMonsterId() == Team.getTeamById(0).getHelper().getMonsterId()){
//                monsterList.remove(i);
//            }
//        }
//    }


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
            Singleton.getInstance().setBaseSortMethod(201);
            Collections.sort(monsterList, monsterElement1Comparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortElement2() {
            Singleton.getInstance().setBaseSortMethod(202);
            Collections.sort(monsterList, monsterElement2Comparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }
    };

    private SortTypeDialogFragment.SortBy sortByType = new SortTypeDialogFragment.SortBy() {
        @Override
        public void sortType1() {
            Singleton.getInstance().setBaseSortMethod(301);
            Collections.sort(monsterList, monsterType1Comparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType2() {
            Singleton.getInstance().setBaseSortMethod(302);
            Collections.sort(monsterList, monsterType2Comparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortType3() {
            Singleton.getInstance().setBaseSortMethod(303);
            Collections.sort(monsterList, monsterType3Comparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }
    };

    private SortStatsDialogFragment.SortBy sortByStats = new SortStatsDialogFragment.SortBy() {
        @Override
        public void sortHp() {
            Singleton.getInstance().setBaseSortMethod(401);
            Collections.sort(monsterList, monsterHpComparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortAtk() {
            Singleton.getInstance().setBaseSortMethod(402);
            Collections.sort(monsterList, monsterAtkComparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }

        @Override
        public void sortRcv() {
            Singleton.getInstance().setBaseSortMethod(403);
            Collections.sort(monsterList, monsterRcvComparator);
            baseMonsterListAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void sortArrayList(int sortMethod) {
        Singleton.getInstance().setBaseSortMethod(sortMethod);
        Log.d("Base Monster List", "sortArrayList sortMethod is: " + sortMethod);
        Log.d("Base Monster List", "monsterList is: " + monsterList);
        switch (sortMethod) {
            case 0:
                Collections.sort(monsterList, monsterAlphabeticalComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 1:
                Collections.sort(monsterList, monsterNumberComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
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
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 6:
                Collections.sort(monsterList, monsterAwakeningComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 201:
                Collections.sort(monsterList, monsterElement1Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 202:
                Collections.sort(monsterList, monsterElement2Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 301:
                Collections.sort(monsterList, monsterType1Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 302:
                Collections.sort(monsterList, monsterType2Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 303:
                Collections.sort(monsterList, monsterType3Comparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 401:
                Collections.sort(monsterList, monsterHpComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 402:
                Collections.sort(monsterList, monsterAtkComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 403:
                Collections.sort(monsterList, monsterRcvComparator);
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void reverseArrayList() {
        switch (Singleton.getInstance().getBaseSortMethod()) {
            case 202:
                element2Reverse();
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 302:
                type2Reverse();
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            case 303:
                type3Reverse();
                baseMonsterListAdapter.notifyDataSetChanged();
                break;
            default:
                defaultReverse();
                baseMonsterListAdapter.notifyDataSetChanged();
                break;

        }
    }

    private void defaultReverse() {
        Collections.reverse(monsterList);
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
        }
    }

    private void element2Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
        }
    }

    private void type2Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
        }
    }

    private void type3Reverse() {
        ArrayList<BaseMonster> sorting = new ArrayList<>();
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
        if (monsterList.contains(BaseMonster.getMonsterId(0))) {
            monsterList.remove(BaseMonster.getMonsterId(0));
            monsterList.add(0, BaseMonster.getMonsterId(0));
        }
    }

    @Override
    public void searchFilter(String query) {
        if (baseMonsterListAdapter != null) {
            if (query != null && query.length() > 0) {
                if (!monsterList.isEmpty()) {
                    monsterList.clear();
                }
                filterMonsterName(query);
                filterMonsterType(query);
                filterMonsterNumber(query);
//                filterMonsterElement(query);
            } else {
                monsterList.clear();
                monsterList.addAll(monsterListAll);
            }
            sortArrayList(Singleton.getInstance().getBaseSortMethod());
            baseMonsterListAdapter.setExpandedPosition(-1);
//            if(fastScroller != null){
//                fastScroller.resizeScrollBar(baseMonsterListAdapter.expanded(), FastScroller.BASE_MONSTER_LIST);
//            }
        }
    }

    private void filterMonsterName(String query) {
        Log.d("Save Monster List", "monsterList filter name is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
        for (BaseMonster monster : monsterListAll) {
            if (monster.getName().toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
        Log.d("Save Monster List", "monsterList after filter name is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
    }

    private void filterMonsterNumber(String query) {
        for (BaseMonster monster : monsterListAll) {
            if (String.valueOf(monster.getMonsterId()).toLowerCase().contains(query.toLowerCase()) && !monsterList.contains(monster)) {
                monsterList.add(monster);
            }
        }
    }

    private void filterMonsterType(String query) {
        Log.d("Save Monster List", "monsterList filter type is: " + monsterList + " monsterListAll is: " + monsterListAll + " query is: " + query);
        for (BaseMonster monster : monsterListAll) {
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
        for (BaseMonster monster : monsterListAll) {
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
