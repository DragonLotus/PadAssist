package com.example.anthony.damagecalculator.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Fragments.AbstractFragment;
import com.example.anthony.damagecalculator.Fragments.BaseMonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.SaveMonsterListFragment;

/**
 * Created by DragonLotus on 10/10/2015.
 */
public class MonsterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {"New Monster", "Saved Monsters" };
    private Context context;
    private Fragment baseMonsterListFragment = BaseMonsterListFragment.newInstance();
    private Fragment saveMonsterListFragment = SaveMonsterListFragment.newInstance();

    public MonsterPagerAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return baseMonsterListFragment;
        }else {
            return saveMonsterListFragment;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
