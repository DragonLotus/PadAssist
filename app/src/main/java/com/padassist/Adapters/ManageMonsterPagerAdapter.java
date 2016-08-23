package com.padassist.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padassist.Fragments.BaseMonsterListFragment;
import com.padassist.Fragments.ManageBaseMonsterListFragment;
import com.padassist.Fragments.ManageSaveMonsterListFragment;
import com.padassist.Fragments.SaveMonsterListFragment;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/10/2015.
 */
public class ManageMonsterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] {"Saved Monsters", "Saved Helpers", "New Monster" };
    private Context context;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    public ManageMonsterPagerAdapter(FragmentManager fragmentManager, Context context){
        super(fragmentManager);
        fragmentList.add(ManageSaveMonsterListFragment.newInstance(false));
        fragmentList.add(ManageSaveMonsterListFragment.newInstance(true));
        fragmentList.add(ManageBaseMonsterListFragment.newInstance());
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
            return tabTitles[position];
    }
}
