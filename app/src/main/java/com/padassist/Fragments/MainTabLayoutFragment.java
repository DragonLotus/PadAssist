package com.padassist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.padassist.Adapters.MainPagerAdapter;
import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.BaseFragments.TabLayoutBase;
import com.padassist.Data.Enemy;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.R;

import org.parceler.Parcels;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainTabLayoutFragment extends TabLayoutBase {
    public static final String TAG = MainTabLayoutFragment.class.getSimpleName();
    private MainPagerAdapter mainPagerAdapter;
    private Team team;
    private Enemy enemy;
    private DecimalFormat df = new DecimalFormat("#.##");
    private Menu optionsMenu;

    // TODO: Rename and change types and number of parameters
    public static MainTabLayoutFragment newInstance(Parcelable team, Parcelable enemy) {
        MainTabLayoutFragment fragment = new MainTabLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public MainTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public int getSelection() {
        return MAIN;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        optionsMenu = menu;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(tabLayout.getSelectedTabPosition());
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        switch (tabLayout.getSelectedTabPosition()){
            case 0:
                break;
            case 1:
                menu.setGroupVisible(R.id.sortGroup, false);
                menu.setGroupVisible(R.id.sortMoreGroup, false);
                menu.findItem(R.id.search).setVisible(false);
                break;
            case 2:
                menu.setGroupVisible(R.id.sortGroup, false);
                menu.setGroupVisible(R.id.sortMoreGroup, false);
                menu.findItem(R.id.search).setVisible(false);
                break;
            case 3:
                menu.setGroupVisible(R.id.sortGroup, false);
                menu.setGroupVisible(R.id.sortMoreGroup, false);
                menu.findItem(R.id.search).setVisible(false);
                break;
            case 4:
                menu.setGroupVisible(R.id.sortGroup, false);
                menu.setGroupVisible(R.id.sortMoreGroup, false);
                menu.findItem(R.id.search).setVisible(false);
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = Parcels.unwrap(getArguments().getParcelable("team"));
            enemy = Parcels.unwrap(getArguments().getParcelable("enemy"));
        }
        Parcelable teamParcel = Parcels.wrap(team);
        Parcelable enemyParcel = Parcels.wrap(enemy);
        mainPagerAdapter = new MainPagerAdapter(getChildFragmentManager(), teamParcel, enemyParcel);
        viewPager.setAdapter(mainPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        viewPager.setOffscreenPageLimit(5);

        tabLayout.addOnTabSelectedListener(viewPagerOnTabSelectedListener);

    }

    private TabLayout.OnTabSelectedListener viewPagerOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
            Intent i;
            viewPager.setCurrentItem(tab.getPosition());
            setTitle(tab.getPosition());
            switch (tab.getPosition()){
                case 1:
                    i = new Intent("REFRESH_MONSTER_LIST");
                    lbm.sendBroadcast(i);
                case 2:
                    i = new Intent("REFRESH_ORB_MATCH");
                    lbm.sendBroadcast(i);
                    break;
                case 3:
                    i = new Intent("REFRESH_ENEMY");
                    lbm.sendBroadcast(i);
                    break;
                case 4:
                    i = new Intent("REFRESH_TEAM_DAMAGE");
                    lbm.sendBroadcast(i);
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
                LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getActivity());
                Intent i;
            switch (tab.getPosition()){
                case 1:
                    i = new Intent("ONDESELECT_MONSTER_LIST");
                    lbm.sendBroadcast(i);
                case 2:
                    i = new Intent("ONDESELECT_ORB_MATCH");
                    lbm.sendBroadcast(i);
                    break;
                case 3:
                    i = new Intent("ONDESELECT_ENEMY");
                    lbm.sendBroadcast(i);
                    break;
                case 4:
                    i = new Intent("ONDESELECT_TEAM_DAMAGE");
                    lbm.sendBroadcast(i);
                    break;
            }
        }
    };

    private void setTitle(int position){
        switch (position) {
            case 0:
                getActivity().setTitle("Monsters");
                break;
            case 1:
                getActivity().setTitle("Set Team");
                break;
            case 2:
                getActivity().setTitle("Set Orb Matches");
                break;
            case 3:
                getActivity().setTitle("Set Enemy");
                break;
            case 4:
                getActivity().setTitle("Team Damage");
                break;
        }
    }

}
