package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.BaseMonsterListBase;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class BaseMonsterListFragment extends BaseMonsterListBase {
    public static final String TAG = BaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private Toast toast;
    private Realm realm = Realm.getDefaultInstance();

    public static BaseMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition) {
        BaseMonsterListFragment fragment = new BaseMonsterListFragment();
        Bundle args = new Bundle();
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putBoolean("replaceAll", replaceAll);
        args.putInt("monsterPosition", monsterPosition);
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
            monsterPosition = getArguments().getInt("monsterPosition");
        }

        if (isGrid) {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        } else {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }

        baseMonsterListRecycler = new BaseMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener, isGrid);
        monsterListView.setAdapter(baseMonsterListRecycler);

    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            Log.d("BaseMonsterList", "newTeam is: " + newTeam);
            Monster newMonster;
            if (monsterList.get(position).getMonsterId() == 0 && monsterPosition == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (monsterList.get(position).getMonsterId() == 0) {
                    newMonster = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
                } else {
//                    RealmResults<Monster> results = realm.where(Monster.class).findAllSorted("monsterId");
//                    Log.d("BaseMonsterList", "results is: " + results);
//                    long lastMonsterId = results.get(results.size() - 1).getMonsterId();
                    long lastMonsterId = realm.where(Monster.class).findAllSorted("monsterId").last().getMonsterId();
                    Log.d("BaseMonsterList", "monsterListAll is: " + realm.where(Monster.class).findAll());
                    Log.d("BaseMonsterList", "lastMonsterId is: " + lastMonsterId);
                    newMonster = new Monster(monsterList.get(position).getMonsterId());
                    if (monsterPosition == 5) {
                        newMonster.setHelper(true);
                    }
                    newMonster.setMonsterId(lastMonsterId + 1);
                    realm.beginTransaction();
                    newMonster = realm.copyToRealm(newMonster);
                    realm.commitTransaction();
                    Log.d("BaseMonsterList", "newMonster Id: " + newMonster.getMonsterId());
                }
                Log.d("BaseMonsterList", "newMonster valid: " + newMonster.isValid());
                if (replaceAll) {
                    ArrayList<Team> teamList = new ArrayList<>();
                    RealmResults results = realm.where(Team.class).findAll();
                    teamList.addAll(results);
                    for (int i = 0; i < teamList.size(); i++) {
                        for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                            if (teamList.get(i).getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                realm.beginTransaction();
                                teamList.get(i).setMonsters(j, newMonster);
                                realm.commitTransaction();
                            }
                        }
                    }
                } else {
                    realm.beginTransaction();
                    switch (monsterPosition) {
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
                    realm.commitTransaction();
                }
                Log.d("BaseMonsterList", "newTeam monsters is: " + newTeam.getMonsters());
                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
                if (newMonster.getMonsterId() != 0) {
                    ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(newMonster.getMonsterId(), monsterPosition), MonsterPageFragment.TAG, "good");
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster created", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position =  ((RecyclerView.ViewHolder)v.getTag()).getAdapterPosition();
            Team newTeam = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            Monster newMonster;
            if (monsterList.get(position).getMonsterId() == 0 && monsterPosition == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (monsterList.get(position).getMonsterId() == 0) {
                    newMonster = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
                } else {
                    RealmResults<Monster> results = realm.where(Monster.class).findAllSorted("monsterId");
                    long lastMonsterId = results.get(results.size() - 1).getMonsterId();
                    newMonster = new Monster(monsterList.get(position).getMonsterId());
                    if (monsterPosition == 5) {
                        newMonster.setHelper(true);
                    }
                    newMonster.setMonsterId(lastMonsterId + 1);
                    realm.beginTransaction();
                    newMonster = realm.copyToRealm(newMonster);
                    realm.commitTransaction();
                }
                if (replaceAll) {
                    ArrayList<Team> teamList = new ArrayList<>();
                    RealmResults results = realm.where(Team.class).findAll();
                    teamList.addAll(results);
                    for (int i = 0; i < teamList.size(); i++) {
                        for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                            if (teamList.get(i).getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                realm.beginTransaction();
                                teamList.get(i).setMonsters(j, newMonster);
                                realm.commitTransaction();
                            }
                        }
                    }
                } else {
                    realm.beginTransaction();
                    switch (monsterPosition) {
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
                    realm.commitTransaction();
                }
                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
                if (newMonster.getMonsterId() != 0) {
                    ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(newMonster.getMonsterId(), monsterPosition), MonsterPageFragment.TAG, "good");
                } else {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
            return false;
        }
    };
}
