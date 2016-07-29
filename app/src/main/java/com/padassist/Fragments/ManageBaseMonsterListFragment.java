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

        baseMonsterListAdapter = new BaseMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener);
        monsterListView.setAdapter(baseMonsterListAdapter);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            return true;
        }
    };
}
