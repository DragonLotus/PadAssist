package com.padassist.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.BaseFragments.SaveMonsterListBase;

import org.parceler.Parcels;

import java.util.ArrayList;

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

    private FastScroller fastScroller;

    public static SaveMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        args.putInt("selection", SUB);
        fragment.setArguments(args);
        return fragment;
    }

    public static SaveMonsterListFragment newInstance(Parcelable monster){
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putParcelable("monster", monster);
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

        if(selection == SUB){
            saveMonsterListRecycler = new SaveMonsterListRecycler(getContext(), monsterList, monsterListView, monsterListOnClickListener,
                    monsterListOnLongClickListener, deleteOnClickListener, isGrid, clearTextFocus);
        } else if (selection == INHERIT){
            saveMonsterListRecycler = new SaveMonsterListRecycler(getContext(), monsterList, monsterListView, inheritOnClickListener,
                    inheritOnLongClickListener, deleteOnClickListener, isGrid, clearTextFocus);
        }

        monsterListView.setAdapter(saveMonsterListRecycler);

    }

    @Override
    public void onActivityCreatedSpecific() {
        if (monsterListAll == null) {
            monsterListAll = new ArrayList<>();
        }
        if (getArguments() != null) {
            selection = getArguments().getInt("selection");
            if(selection == SUB){
                replaceAll = getArguments().getBoolean("replaceAll");
                replaceMonsterId = getArguments().getLong("replaceMonsterId");
                monsterPosition = getArguments().getInt("monsterPosition");
            } else if(selection == MonsterTabLayoutFragment.INHERIT){
                monster = Parcels.unwrap(getArguments().getParcelable("monster"));
            }
        }

        monsterListAll.clear();

        if(selection == SUB){
            if (monsterPosition == 5) {
                helper = true;
            } else {
                helper = false;
            }
            monsterListAll.addAll(realm.where(Monster.class).equalTo("helper", helper).findAll());
        } else if(selection == MonsterTabLayoutFragment.INHERIT){
            monsterListAll.addAll(realm.where(Monster.class).equalTo("baseMonster.inheritable", true).equalTo("helper", false).notEqualTo("monsterId", monster.getMonsterId()).findAll());
            monsterListAll.add(0, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
        }

    }

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

    private void inheritMonster(Monster monster, Monster inheritMonster){
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

                getActivity().getSupportFragmentManager().popBackStack(MainTabLayoutFragment.TAG, 0);
                Parcelable monsterParcel = Parcels.wrap(saveMonsterListRecycler.getItem(position));
                ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position).getMonsterId(), monsterPosition, monsterParcel), MonsterPageFragment.TAG, "good");
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position =  ((RecyclerView.ViewHolder)v.getTag()).getAdapterPosition();
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
