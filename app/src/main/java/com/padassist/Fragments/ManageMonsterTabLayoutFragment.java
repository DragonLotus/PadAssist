package com.padassist.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.padassist.Adapters.ManageMonsterPagerAdapter;
import com.padassist.Util.MonsterTabLayoutUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageMonsterTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageMonsterTabLayoutFragment extends MonsterTabLayoutUtil {

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        monsterPagerAdapter = new ManageMonsterPagerAdapter(getChildFragmentManager(), getActivity());
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        if(tabLayout.getSelectedTabPosition() == 0){
            getActivity().setTitle("Saved Monsters");
        } else if(tabLayout.getSelectedTabPosition() == 1) {
            getActivity().setTitle("Create Monster");
        }
        viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
    }

    private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
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
            if(position == 0){
                getActivity().setTitle("Saved Monsters");
            } else if(position == 1) {
                getActivity().setTitle("Create Monster");
            }
        }
    };

}
