package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.padassist.Adapters.BaseMonsterListRecycler;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.BaseMonsterListUtil;


public class ManageBaseMonsterListFragment extends BaseMonsterListUtil {
    public static final String TAG = ManageBaseMonsterListFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private Toast toast;

    public static ManageBaseMonsterListFragment newInstance() {
        ManageBaseMonsterListFragment fragment = new ManageBaseMonsterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ManageBaseMonsterListFragment() {
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
        baseMonsterListRecycler = new BaseMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener, isGrid);
        monsterListView.setAdapter(baseMonsterListRecycler);
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Monster newMonster = new Monster(monsterList.get(position).getMonsterId());
            long lastMonsterId = realm.where(Monster.class).findAllSorted("monsterId").last().getMonsterId();
            newMonster.setMonsterId(lastMonsterId + 1);
            realm.beginTransaction();
            realm.copyToRealm(newMonster);
            realm.commitTransaction();
            ((MainActivity) getActivity()).switchFragment(ManageMonsterPageFragment.newInstance(newMonster), MonsterTabLayoutFragment.TAG, "good");
//
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    };
}
