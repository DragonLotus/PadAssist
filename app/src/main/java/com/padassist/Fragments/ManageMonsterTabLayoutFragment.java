package com.padassist.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.padassist.Adapters.ManageMonsterPagerAdapter;
import com.padassist.BaseFragments.TabLayoutBase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageMonsterTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageMonsterTabLayoutFragment extends TabLayoutBase {

    private ManageMonsterPagerAdapter monsterPagerAdapter;

    // TODO: Rename and change types and number of parameters
    public static ManageMonsterTabLayoutFragment newInstance() {
        ManageMonsterTabLayoutFragment fragment = new ManageMonsterTabLayoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ManageMonsterTabLayoutFragment() {
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
        }
        monsterPagerAdapter = new ManageMonsterPagerAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.addOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
