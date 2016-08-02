package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.SaveMonsterListUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;


public class ManageSaveMonsterListFragment extends SaveMonsterListUtil {
    public static final String TAG = ManageSaveMonsterListFragment.class.getSimpleName();
    private Toast toast;
    private Monster monsterZero = Monster.getMonsterId(0);
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;


    public static ManageSaveMonsterListFragment newInstance() {
        ManageSaveMonsterListFragment fragment = new ManageSaveMonsterListFragment();
        Bundle args = new Bundle();
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

        saveMonsterListRecycler = new SaveMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener, deleteOnClickListener);
        monsterListView.setAdapter(saveMonsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreatedSpecific() {
            monsterListAll = (ArrayList) Monster.getAllSavedMonsters();
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            ((MainActivity) getActivity()).switchFragment(ManageMonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position)), MonsterTabLayoutFragment.TAG, "good");
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
            deleteConfirmationDialog.show(getChildFragmentManager(), position, "Delete Confirmation");
        }
    };

    protected DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout(int position) {
            ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
            Team newTeam;
            for (int i = 0; i < teamList.size(); i++) {
                newTeam = teamList.get(i);
                for (int j = 0; j < newTeam.getMonsters().size(); j++) {
                    if (newTeam.getMonsters().get(j).getMonsterId() == saveMonsterListRecycler.getItem(position).getMonsterId()) {
                        newTeam.setMonsters(j, Monster.getMonsterId(0));
                    }
                }
                newTeam.save();
            }
            Monster.getMonsterId(saveMonsterListRecycler.getItem(position).getMonsterId()).delete();
            for(int i = 0; i < monsterListAll.size(); i++){
                if(monsterListAll.get(i).getMonsterId() == saveMonsterListRecycler.getItem(position).getMonsterId()){
                    monsterListAll.remove(i);
                }
            }
            monsterList.remove(position);
            saveMonsterListRecycler.notifyItemRemoved(position);
            saveMonsterListRecycler.notifyDataSetChanged(monsterList);
            saveMonsterListRecycler.setExpandedPosition(-1);
            emptyCheck();
        }
    };

}
