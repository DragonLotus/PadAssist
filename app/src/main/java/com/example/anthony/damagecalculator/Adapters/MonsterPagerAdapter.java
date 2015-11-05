package com.example.anthony.damagecalculator.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Fragments.AbstractFragment;
import com.example.anthony.damagecalculator.Fragments.BaseMonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.SaveMonsterListFragment;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/10/2015.
 */
public class MonsterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"Saved Monsters", "New Monster" };
    private String tabTitlesHelper[] = new String[] {"Saved Helpers", "New Helper" };
    private Context context;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
//    private Fragment baseMonsterListFragment = BaseMonsterListFragment.newInstance();
//    private Fragment saveMonsterListFragment = SaveMonsterListFragment.newInstance();

    public MonsterPagerAdapter(FragmentManager fragmentManager, Context context, boolean replaceAll, long replaceMonsterId){
        super(fragmentManager);
        fragmentList.add(SaveMonsterListFragment.newInstance(replaceAll, replaceMonsterId));
        fragmentList.add(BaseMonsterListFragment.newInstance(replaceAll, replaceMonsterId));
        this.context = context;
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
