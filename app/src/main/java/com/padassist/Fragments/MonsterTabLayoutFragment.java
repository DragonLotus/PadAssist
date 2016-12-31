package com.padassist.Fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Data.Monster;
import com.padassist.BaseFragments.MonsterTabLayoutBase;

import org.parceler.ParcelPropertyConverter;
import org.parceler.Parcels;

import java.nio.charset.IllegalCharsetNameException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterTabLayoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterTabLayoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterTabLayoutFragment extends MonsterTabLayoutBase {
    public static final int SUB = 1;
    public static final int INHERIT = 2;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private MonsterPagerAdapter monsterPagerAdapter;
    private int selection;
    private Monster monster;

    // TODO: Rename and change types and number of parameters
    public static MonsterTabLayoutFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition) {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        args.putInt("selection", SUB);
        fragment.setArguments(args);
        return fragment;
    }

    public static MonsterTabLayoutFragment newInstance(Parcelable monster) {
        MonsterTabLayoutFragment fragment = new MonsterTabLayoutFragment();
        Bundle args = new Bundle();
        args.putParcelable("monster", monster);
        args.putInt("selection", INHERIT);
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterTabLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            selection = getArguments().getInt("selection");
            if (selection == SUB) {
                replaceAll = getArguments().getBoolean("replaceAll");
                replaceMonsterId = getArguments().getLong("replaceMonsterId");
                monsterPosition = getArguments().getInt("monsterPosition");
            } else if (selection == INHERIT) {
                monster = Parcels.unwrap(getArguments().getParcelable("monster"));
            }
        }
        if (selection == SUB) {
            monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), replaceAll, replaceMonsterId, monsterPosition);
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
            monsterPagerAdapter = new MonsterPagerAdapter(getChildFragmentManager(), monster);
            getActivity().setTitle("Select Inherit");
        }
        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (realm.where(Monster.class).equalTo("helper", true).findAll().size() < 1 && monsterPosition == 5 && replaceMonsterId == 0) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        } else if (realm.where(Monster.class).findAll().size() <= 1) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }


//        if(tabLayout.getSelectedTabPosition() == 0){
//            getActivity().setTitle("Saved Monsters");
//        } else if(tabLayout.getSelectedTabPosition() == 1) {
//            getActivity().setTitle("Create Monster");
//        }
        //    viewPager.addOnPageChangeListener(tabLayoutOnPageChangeListener);
    }

    private TabLayout.TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
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
            if (position == 0) {
                getActivity().setTitle("Saved Monsters");
            } else if (position == 1) {
                getActivity().setTitle("Create Monster");
            }
        }
    };

}
