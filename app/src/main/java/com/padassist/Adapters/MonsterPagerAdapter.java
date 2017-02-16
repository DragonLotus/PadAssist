package com.padassist.Adapters;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Fragments.BaseMonsterListFragment;
import com.padassist.Fragments.SaveMonsterListFragment;
import com.padassist.Util.Singleton;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/10/2015.
 */
public class MonsterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Saved Monsters", "New Monster" };
    private String tabTitlesHelper[] = new String[] {"Saved Helpers", "New Helper" };
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
//    private Fragment baseMonsterListFragment = BaseMonsterListFragment.newInstance();
//    private Fragment saveMonsterListFragment = SaveMonsterListFragment.newInstance();

    public MonsterPagerAdapter(FragmentManager fragmentManager, boolean replaceAll, long replaceMonsterId, int monsterPosition, Team team){
        super(fragmentManager);
        Parcelable teamParcel = Parcels.wrap(team);
        fragmentList.add(SaveMonsterListFragment.newInstance(replaceAll, replaceMonsterId, monsterPosition, teamParcel));
        fragmentList.add(BaseMonsterListFragment.newInstance(replaceAll, replaceMonsterId, monsterPosition));
    }
    public MonsterPagerAdapter(FragmentManager fragmentManager, Monster monster, Team team){
        super(fragmentManager);
        Parcelable monsterParcel = Parcels.wrap(monster);
        Parcelable teamParcel = Parcels.wrap(team);
        fragmentList.add(SaveMonsterListFragment.newInstance(monsterParcel, teamParcel));
        fragmentList.add(BaseMonsterListFragment.newInstance(monsterParcel));
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(Singleton.getInstance().getMonsterOverwrite() == 5){
            return tabTitlesHelper[position];
        } else {
            return tabTitles[position];
        }
    }
}
