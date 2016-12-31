package com.padassist.Adapters;


import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padassist.Data.Monster;
import com.padassist.Fragments.BaseMonsterListFragment;
import com.padassist.Fragments.SaveMonsterListFragment;
import com.padassist.Util.Singleton;

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

    public MonsterPagerAdapter(FragmentManager fragmentManager, boolean replaceAll, long replaceMonsterId, int monsterPosition){
        super(fragmentManager);
        fragmentList.add(SaveMonsterListFragment.newInstance(replaceAll, replaceMonsterId, monsterPosition));
        fragmentList.add(BaseMonsterListFragment.newInstance(replaceAll, replaceMonsterId, monsterPosition));
    }
    public MonsterPagerAdapter(FragmentManager fragmentManager, Monster monster){
        super(fragmentManager);
        Parcelable monsterParcel = Parcels.wrap(monster);
        fragmentList.add(SaveMonsterListFragment.newInstance(monsterParcel));
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
