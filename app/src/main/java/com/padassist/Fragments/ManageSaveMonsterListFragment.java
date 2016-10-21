package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.SaveMonsterListBase;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class ManageSaveMonsterListFragment extends SaveMonsterListBase {
    public static final String TAG = ManageSaveMonsterListFragment.class.getSimpleName();
    private Toast toast;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;


    public static ManageSaveMonsterListFragment newInstance(boolean helper) {
        ManageSaveMonsterListFragment fragment = new ManageSaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("helper", helper);
        fragment.setArguments(args);
        return fragment;
    }

    public ManageSaveMonsterListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        if (isGrid) {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        } else {
            monsterListView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        }
        saveMonsterListRecycler = new SaveMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener,
                monsterListOnLongClickListener, deleteOnClickListener, isGrid, clearTextFocus);
        monsterListView.setAdapter(saveMonsterListRecycler);
    }

    @Override
    public void onActivityCreatedSpecific() {
        if(monsterListAll == null){
            monsterListAll = new ArrayList<>();
        } else {
            monsterListAll.clear();
        }
        if (getArguments() != null) {
            helper = getArguments().getBoolean("helper");
        }
            monsterListAll.addAll(realm.where(Monster.class).equalTo("helper", helper).findAll());
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            ((MainActivity) getActivity()).switchFragment(ManageMonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position).getMonsterId()), MonsterTabLayoutFragment.TAG, "good");
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

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
        public void resetLayout(int position) {
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
