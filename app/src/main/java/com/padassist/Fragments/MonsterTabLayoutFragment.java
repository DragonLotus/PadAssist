package com.padassist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Data.Monster;
import com.padassist.BaseFragments.TabLayoutBase;
import com.padassist.Data.Team;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterTabLayoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterTabLayoutFragment extends TabLayoutBase {
    public static final int SUB = 1;
    public static final int INHERIT = 2;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private MonsterPagerAdapter monsterPagerAdapter;
    private int selection;
    private Monster monster;
    private Team team;

    // TODO: Rename and change types and number of parameters
    public static MonsterTabLayoutFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition, Parcelable team) {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        args.putParcelable("team", team);
        args.putInt("selection", SUB);
        fragment.setArguments(args);
        return fragment;
    }

    public static MonsterTabLayoutFragment newInstance(Parcelable monster, Parcelable team) {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelable("monster", monster);
        args.putParcelable("team", team);
        args.putInt("selection", INHERIT);
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public int getSelection() {
        return MONSTERS;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            selection = getArguments().getInt("selection");
            team = Parcels.unwrap(getArguments().getParcelable("team"));
            if (selection == SUB) {
                replaceAll = getArguments().getBoolean("replaceAll");
                replaceMonsterId = getArguments().getLong("replaceMonsterId");
                monsterPosition = getArguments().getInt("monsterPosition");
            } else if (selection == INHERIT) {
                monster = Parcels.unwrap(getArguments().getParcelable("monster"));
            }
        }
        if (selection == SUB) {
            monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), replaceAll, replaceMonsterId, monsterPosition, team);
            switch (monsterPosition) {
                case 0:
                    getActivity().setTitle("Replace Leader");
                    break;
                case 1:
                    getActivity().setTitle("Replace Sub 1");
                    break;
                case 2:
                    getActivity().setTitle("Replace Sub 2");
                    break;
                case 3:
                    getActivity().setTitle("Replace Sub 3");
                    break;
                case 4:
                    getActivity().setTitle("Replace Sub 4");
                    break;
                case 5:
                    getActivity().setTitle("Replace Helper");
            }
        } else if (selection == INHERIT) {
            monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), monster, team);
            getActivity().setTitle("Select Inherit");
        }
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        if (realm.where(Monster.class).equalTo("helper", true).findAll().size() < 1 && monsterPosition == 5 && replaceMonsterId == 0) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        } else if (realm.where(Monster.class).findAll().size() <= 1) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }

        viewPager.addOnPageChangeListener(pageChangeListener);

    }
    private TabLayout.TabLayoutOnPageChangeListener pageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
            Intent i = new Intent("REFRESH");
            lbm.sendBroadcast(i);
        }
    };

}
