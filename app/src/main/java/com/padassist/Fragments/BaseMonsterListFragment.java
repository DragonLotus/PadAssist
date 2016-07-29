package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;


import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.BaseMonsterListUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;


public class BaseMonsterListFragment extends BaseMonsterListUtil {
    public static final String TAG = BaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private boolean replaceAll;
    private long replaceMonsterId;
    private Toast toast;

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
        }

        baseMonsterListRecycler = new BaseMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener);
        monsterListView.setAdapter(baseMonsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

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
                    if (Singleton.getInstance().getMonsterOverwrite() == 5) {
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
                    getActivity().getSupportFragmentManager().popBackStack();
                    if (replaceMonsterId == 0) {
                        ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(newMonster), MonsterPageFragment.TAG, "good");
                    }
                }
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
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
                    if (Singleton.getInstance().getMonsterOverwrite() == 5) {
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
                } else {
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
                }
                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
                ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(newMonster), MonsterPageFragment.TAG, "good");

            }
            return false;
        }
    };
}
