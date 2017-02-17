package com.padassist.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Constants;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.BaseFragments.SaveMonsterListBase;
import com.padassist.Util.ScoreMonsterUtil;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.padassist.Fragments.MonsterTabLayoutFragment.INHERIT;
import static com.padassist.Fragments.MonsterTabLayoutFragment.SUB;


public class SaveMonsterListFragment extends SaveMonsterListBase {
    public static final String TAG = SaveMonsterListFragment.class.getSimpleName();
    private Toast toast;
    private boolean replaceAll;
    private long replaceMonsterId;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;
    private int monsterPosition;
    private ArrayList<Integer> teamAwakeningsSansReplace;
    private ArrayList<Integer> awakeningCount;
    private ArrayList<Double> elementDamage;
    private ArrayList<Double> elementDamageReplace;
    private double totalDamage;
    private double totalDamageReplace;

    private FastScroller fastScroller;

    public static SaveMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition, Parcelable team) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        args.putParcelable("team", team);
        args.putInt("selection", SUB);
        fragment.setArguments(args);
        return fragment;
    }

    public static SaveMonsterListFragment newInstance(Parcelable monster, Parcelable team) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putParcelable("monster", monster);
        args.putParcelable("team", team);
        args.putInt("selection", MonsterTabLayoutFragment.INHERIT);
        fragment.setArguments(args);
        return fragment;
    }

    public SaveMonsterListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (isGrid) {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        } else {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }

        if (selection == SUB) {
            saveMonsterListRecycler = new SaveMonsterListRecycler(getContext(), monsterList, monsterListView, monsterListOnClickListener,
                    monsterListOnLongClickListener, deleteOnClickListener, expandOnClickListener, isGrid, clearTextFocus);
        } else if (selection == INHERIT) {
            saveMonsterListRecycler = new SaveMonsterListRecycler(getContext(), monsterList, monsterListView, inheritOnClickListener,
                    inheritOnLongClickListener, deleteOnClickListener, expandOnClickListener, isGrid, clearTextFocus);
        }

        monsterListView.setAdapter(saveMonsterListRecycler);

    }

    @Override
    public void onActivityCreatedSpecific() {
        if (monsterListAll == null) {
            monsterListAll = new ArrayList<>();
        }
        if (teamAwakeningsSansReplace == null) {
            teamAwakeningsSansReplace = new ArrayList<>();
        }
        awakeningCount = new ArrayList<>();
        elementDamage = new ArrayList<>();
        elementDamageReplace = new ArrayList<>();
        for (int i = 0; i <= Constants.numOfAwakenings; i++) {
            awakeningCount.add(0);
        }
        for (int i = 0; i < 5; i++) {
            elementDamage.add(0d);
            elementDamageReplace.add(0d);
        }
        if (getArguments() != null) {
            selection = getArguments().getInt("selection");
            team = Parcels.unwrap(getArguments().getParcelable("team"));
            if (selection == SUB) {
                replaceAll = getArguments().getBoolean("replaceAll");
                replaceMonsterId = getArguments().getLong("replaceMonsterId");
                monsterPosition = getArguments().getInt("monsterPosition");
            } else if (selection == MonsterTabLayoutFragment.INHERIT) {
                monster = Parcels.unwrap(getArguments().getParcelable("monster"));
            }
        }

        monsterListAll.clear();
        if (selection == SUB) {
            teamAwakeningsSansReplace.clear();
            totalDamage = 0;
            for (int i = 0; i < team.getMonsters().size(); i++) {
                if (i != monsterPosition) {
                    for (int j = 0; j < team.getMonsters().get(i).getCurrentAwakenings(); j++) {
                        teamAwakeningsSansReplace.add(team.getMonsters().get(i).getAwokenSkills(j));
                    }
                    switch (team.getMonsters().get(i).getElement1Int()) {
                        case 0:
                            elementDamage.set(0, elementDamage.get(0) + team.getMonsters().get(i).getTotalAtk());
                            totalDamage += team.getMonsters().get(i).getTotalAtk();
                            break;
                        case 1:
                            elementDamage.set(1, elementDamage.get(1) + team.getMonsters().get(i).getTotalAtk());
                            totalDamage += team.getMonsters().get(i).getTotalAtk();
                            break;
                        case 2:
                            elementDamage.set(2, elementDamage.get(2) + team.getMonsters().get(i).getTotalAtk());
                            totalDamage += team.getMonsters().get(i).getTotalAtk();
                            break;
                        case 3:
                            elementDamage.set(3, elementDamage.get(3) + team.getMonsters().get(i).getTotalAtk());
                            totalDamage += team.getMonsters().get(i).getTotalAtk();
                            break;
                        case 4:
                            elementDamage.set(4, elementDamage.get(4) + team.getMonsters().get(i).getTotalAtk());
                            totalDamage += team.getMonsters().get(i).getTotalAtk();
                            break;
                    }
                    if (team.getMonsters().get(i).getElement1Int() == team.getMonsters().get(i).getElement2Int()) {
                        switch (team.getMonsters().get(i).getElement2Int()) {
                            case 0:
                                elementDamage.set(0, elementDamage.get(0) + (double) team.getMonsters().get(i).getTotalAtk() / 10);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 10;
                                break;
                            case 1:
                                elementDamage.set(1, elementDamage.get(1) + (double) team.getMonsters().get(i).getTotalAtk() / 10);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 10;
                                break;
                            case 2:
                                elementDamage.set(2, elementDamage.get(2) + (double) team.getMonsters().get(i).getTotalAtk() / 10);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 10;
                                break;
                            case 3:
                                elementDamage.set(3, elementDamage.get(3) + (double) team.getMonsters().get(i).getTotalAtk() / 10);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 10;
                                break;
                            case 4:
                                elementDamage.set(4, elementDamage.get(4) + (double) team.getMonsters().get(i).getTotalAtk() / 10);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 10;
                                break;
                        }
                    } else {
                        switch (team.getMonsters().get(i).getElement2Int()) {
                            case 0:
                                elementDamage.set(0, elementDamage.get(0) + (double) team.getMonsters().get(i).getTotalAtk() / 3);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 3;
                                break;
                            case 1:
                                elementDamage.set(1, elementDamage.get(1) + (double) team.getMonsters().get(i).getTotalAtk() / 3);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 3;
                                break;
                            case 2:
                                elementDamage.set(2, elementDamage.get(2) + (double) team.getMonsters().get(i).getTotalAtk() / 3);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 3;
                                break;
                            case 3:
                                elementDamage.set(2, elementDamage.get(3) + (double) team.getMonsters().get(i).getTotalAtk() / 3);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 3;
                                break;
                            case 4:
                                elementDamage.set(4, elementDamage.get(4) + (double) team.getMonsters().get(i).getTotalAtk() / 3);
                                totalDamage += team.getMonsters().get(i).getTotalAtk() / 3;
                                break;
                        }
                    }

                }
            }
            Collections.sort(teamAwakeningsSansReplace);

            int counter = 0;
            for (int i = 0; i < teamAwakeningsSansReplace.size(); i++) {
                if (i == 0) {
                    counter++;
                } else {
                    if (!teamAwakeningsSansReplace.get(i).equals(teamAwakeningsSansReplace.get(i - 1))) {
                        awakeningCount.set(teamAwakeningsSansReplace.get(i - 1) - 1, counter);
                        counter = 1;
                    } else {
                        counter++;
                    }
                }
                if (i == teamAwakeningsSansReplace.size() - 1) {
                    awakeningCount.set(teamAwakeningsSansReplace.get(i) - 1, counter);
                }
            }
            if (monsterPosition == 5) {
                helper = true;
                monsterListAll.add(0, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            } else {
                helper = false;
            }
            monsterListAll.addAll(realm.where(Monster.class).equalTo("helper", helper).findAll());
        } else if (selection == MonsterTabLayoutFragment.INHERIT) {
            monsterListAll.addAll(realm.where(Monster.class).equalTo("baseMonster.inheritable", true).equalTo("helper", false).notEqualTo("monsterId", monster.getMonsterId()).findAll());
            monsterListAll.add(0, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
        }

    }

    private View.OnClickListener expandOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            searchView.clearFocus();
            int position = (int) view.getTag(R.string.index);
            Monster selectedMonster = saveMonsterListRecycler.getItem(position);
            if (selection == SUB && monsterPosition != 0 && monsterPosition != 5) {
                elementDamageReplace.clear();
                elementDamageReplace.addAll(elementDamage);
                totalDamageReplace = totalDamage;

                switch (selectedMonster.getElement1Int()) {
                    case 0:
                        elementDamageReplace.set(0, elementDamageReplace.get(0) + selectedMonster.getTotalAtk());
                        totalDamageReplace += selectedMonster.getTotalAtk();
                        break;
                    case 1:
                        elementDamageReplace.set(1, elementDamageReplace.get(1) + selectedMonster.getTotalAtk());
                        totalDamageReplace += selectedMonster.getTotalAtk();
                        break;
                    case 2:
                        elementDamageReplace.set(2, elementDamageReplace.get(2) + selectedMonster.getTotalAtk());
                        totalDamageReplace += selectedMonster.getTotalAtk();
                        break;
                    case 3:
                        elementDamageReplace.set(3, elementDamageReplace.get(3) + selectedMonster.getTotalAtk());
                        totalDamageReplace += selectedMonster.getTotalAtk();
                        break;
                    case 4:
                        elementDamageReplace.set(4, elementDamageReplace.get(4) + selectedMonster.getTotalAtk());
                        totalDamageReplace += selectedMonster.getTotalAtk();
                        break;
                }
                if (selectedMonster.getElement1Int() == selectedMonster.getElement2Int()) {
                    switch (selectedMonster.getElement2Int()) {
                        case 0:
                            elementDamageReplace.set(0, elementDamageReplace.get(0) + (double) selectedMonster.getTotalAtk() / 10);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 10;
                            break;
                        case 1:
                            elementDamageReplace.set(1, elementDamageReplace.get(1) + (double) selectedMonster.getTotalAtk() / 10);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 10;
                            break;
                        case 2:
                            elementDamageReplace.set(2, elementDamageReplace.get(2) + (double) selectedMonster.getTotalAtk() / 10);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 10;
                            break;
                        case 3:
                            elementDamageReplace.set(3, elementDamageReplace.get(3) + (double) selectedMonster.getTotalAtk() / 10);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 10;
                            break;
                        case 4:
                            elementDamageReplace.set(4, elementDamageReplace.get(4) + (double) selectedMonster.getTotalAtk() / 10);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 10;
                            break;
                    }
                } else {
                    switch (selectedMonster.getElement2Int()) {
                        case 0:
                            elementDamageReplace.set(0, elementDamageReplace.get(0) + (double) selectedMonster.getTotalAtk() / 3);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 3;
                            break;
                        case 1:
                            elementDamageReplace.set(1, elementDamageReplace.get(1) + (double) selectedMonster.getTotalAtk() / 3);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 3;
                            break;
                        case 2:
                            elementDamageReplace.set(2, elementDamageReplace.get(2) + (double) selectedMonster.getTotalAtk() / 3);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 3;
                            break;
                        case 3:
                            elementDamageReplace.set(2, elementDamageReplace.get(3) + (double) selectedMonster.getTotalAtk() / 3);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 3;
                            break;
                        case 4:
                            elementDamageReplace.set(4, elementDamageReplace.get(4) + (double) selectedMonster.getTotalAtk() / 3);
                            totalDamageReplace += selectedMonster.getTotalAtk() / 3;
                            break;
                    }
                }

                for (int i = 0; i < elementDamageReplace.size(); i++) {
                    elementDamageReplace.set(i, elementDamageReplace.get(i) / totalDamageReplace);
                }

                Log.d("SaveMonsterListFrag", "Team awakenings are: " + teamAwakeningsSansReplace);
                Log.d("SaveMonsterListFrag", "Awakening count is: " + awakeningCount);
                Log.d("SaveMonsterListFrag", "Monster score is: " + ScoreMonsterUtil.scoreMonster(team, awakeningCount, elementDamageReplace, selectedMonster));
            }
            saveMonsterListRecycler.expandItem(position);
        }
    };

    private View.OnClickListener inheritOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag(R.string.index);
            inheritMonster(monster, monsterList.get(position));
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private View.OnLongClickListener inheritOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            int position = (int) view.getTag(R.string.index);
            inheritMonster(monster, monsterList.get(position));
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        }
    };

    private void inheritMonster(Monster monster, Monster inheritMonster) {
        monster.setMonsterInherit(realm.copyFromRealm(inheritMonster));
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && monsterPosition == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (replaceAll) {
                    ArrayList<Team> teamList = new ArrayList<>();
                    RealmResults results = realm.where(Team.class).findAll();
                    teamList.addAll(results);
                    for (int i = 0; i < teamList.size(); i++) {
                        for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                            if (teamList.get(i).getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                realm.beginTransaction();
                                teamList.get(i).setMonsters(j, saveMonsterListRecycler.getItem(position));
                                realm.commitTransaction();
                            }
                        }
                    }
                } else {
                    realm.beginTransaction();
                    Monster monster = realm.copyToRealmOrUpdate(saveMonsterListRecycler.getItem(position));
                    switch (monsterPosition) {
                        case 0:
                            newTeam.setLead(monster);
                            break;
                        case 1:
                            newTeam.setSub1(monster);
                            break;
                        case 2:
                            newTeam.setSub2(monster);
                            break;
                        case 3:
                            newTeam.setSub3(monster);
                            break;
                        case 4:
                            newTeam.setSub4(monster);
                            break;
                        case 5:
                            newTeam.setHelper(monster);
                            break;
                    }
                    realm.commitTransaction();
                }

                for (int i = 0; i < newTeam.getMonsters().size(); i++) {
                    Log.d("SaveMonsterListFragment", "monster " + i + " is : " + newTeam.getMonsters().get(i));
                }

                getActivity().getSupportFragmentManager().popBackStack(MainTabLayoutFragment.TAG, 0);
//                Parcelable monsterParcel = Parcels.wrap(saveMonsterListRecycler.getItem(position));
//                ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position).getMonsterId(), monsterPosition, monsterParcel), MonsterPageFragment.TAG, "good");
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && monsterPosition == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (replaceAll) {
                    ArrayList<Team> teamList = new ArrayList<>();
                    RealmResults results = realm.where(Team.class).findAll();
                    teamList.addAll(results);
                    for (int i = 0; i < teamList.size(); i++) {
                        for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                            if (teamList.get(i).getMonsters().get(j).getMonsterId() == replaceMonsterId) {
                                realm.beginTransaction();
                                teamList.get(i).setMonsters(j, saveMonsterListRecycler.getItem(position));
                                realm.commitTransaction();
                            }
                        }
                    }
                } else {
                    realm.beginTransaction();
                    Monster monster = realm.copyToRealmOrUpdate(saveMonsterListRecycler.getItem(position));
                    switch (monsterPosition) {
                        case 0:
                            newTeam.setLead(monster);
                            break;
                        case 1:
                            newTeam.setSub1(monster);
                            break;
                        case 2:
                            newTeam.setSub2(monster);
                            break;
                        case 3:
                            newTeam.setSub3(monster);
                            break;
                        case 4:
                            newTeam.setSub4(monster);
                            break;
                        case 5:
                            newTeam.setHelper(monster);
                            break;
                    }
                    realm.commitTransaction();
                }
                getActivity().getSupportFragmentManager().popBackStack(MainTabLayoutFragment.TAG, 0);
            }
            return true;
        }
    };


    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, position);
            }
            if (!deleteConfirmationDialog.isAdded()) {
                deleteConfirmationDialog.show(getChildFragmentManager(), position, "Delete Confirmation");
            }
        }
    };

    protected DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout(final int position) {
            ArrayList<Team> teamList = new ArrayList<>();
            RealmResults results = realm.where(Team.class).findAll();
            teamList.addAll(results);
            final long monsterId = saveMonsterListRecycler.getItem(position).getMonsterId();
            for (int i = 0; i < teamList.size(); i++) {
                for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                    if (teamList.get(i).getMonsters().get(j).getMonsterId() == monsterId) {
                        realm.beginTransaction();
                        teamList.get(i).setMonsters(j, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
                        realm.commitTransaction();
                    }
                }
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(Monster.class).equalTo("monsterId", monsterId).findFirst().deleteFromRealm();
                }
            });
            monsterListAll.clear();
            monsterListAll.addAll(realm.where(Monster.class).findAll());
            monsterList.remove(position);
            saveMonsterListRecycler.notifyItemRemoved(position);
            saveMonsterListRecycler.notifyDataSetChanged(monsterList);
            saveMonsterListRecycler.setExpandedPosition(-1);
            emptyCheck();
        }
    };
}
