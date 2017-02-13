package com.padassist.Adapters;


import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padassist.Data.Monster;
import com.padassist.Fragments.BaseMonsterListFragment;
import com.padassist.Fragments.EnemyListFragment;
import com.padassist.Fragments.EnemyTargetFragment;
import com.padassist.Fragments.ManageMonsterTabLayoutFragment;
import com.padassist.Fragments.MonsterListFragment;
import com.padassist.Fragments.OrbMatchFragment;
import com.padassist.Fragments.SaveMonsterListFragment;
import com.padassist.Fragments.TeamDamageListFragment;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/10/2015.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] {"Monsters", "Team", "Orb Matches", "Enemy", "Damage Calculations" };
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private SharedPreferences preferences;

    public MainPagerAdapter(FragmentManager fragmentManager, Parcelable team, Parcelable enemy){
        super(fragmentManager);
        fragmentList.add(ManageMonsterTabLayoutFragment.newInstance());
        fragmentList.add(MonsterListFragment.newInstance(team));
        fragmentList.add(OrbMatchFragment.newInstance(team));
        fragmentList.add(EnemyTargetFragment.newInstance(enemy));
        fragmentList.add(TeamDamageListFragment.newInstance(team, enemy));
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
