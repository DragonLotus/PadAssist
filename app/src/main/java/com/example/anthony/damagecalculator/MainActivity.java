package com.example.anthony.damagecalculator;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Fragments.BaseMonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.MonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.SaveMonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.TeamSaveDialogFragment;

import java.io.File;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link android.support.v4.view.ViewPager} that will host the section contents.
     */
    FrameLayout mViewPager;
    private Fragment mContent;
    private Enemy enemy;
    private Team team;
    private TeamSaveDialogFragment teamSaveDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            Configuration.Builder configBuilder = new Configuration.Builder(this);
            configBuilder.addModelClasses(Monster.class);
            configBuilder.addModelClasses(Team.class);
            configBuilder.addModelClass(BaseMonster.class);
            ActiveAndroid.initialize(configBuilder.create());
        } else {
            ActiveAndroid.initialize(this);
        }
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//      mViewPager = (ViewPager) findViewById(R.id.pager);
//      mViewPager.setAdapter(mSectionsPagerAdapter);
        enemy = new Enemy();
        if(Team.getTeamById(0) == null) {
            team = new Team();
        } else {
            team = Team.getTeamById(0);
        }
        if(BaseMonster.getMonsterId(0) == null){
            BaseMonster monster = new BaseMonster();
            monster.save();
        }
        if(Monster.getMonsterId(0) == null){
            Monster monster = new Monster(0);
            monster.save();
        }
        if(BaseMonster.getMonsterId(1218) == null){
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1218);
            monster.setMonsterPicture(R.drawable.monster_1218);
            monster.setName("Kirin of the Sacred Gleam, Sakuya");
            monster.setMaxLevel(99);
            monster.setAtkMax(1370);
            monster.setAtkMin(913);
            monster.setHpMax(3528);
            monster.setHpMin(1271);
            monster.setRcvMax(384);
            monster.setRcvMin(256);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(7);
            monster.setElement1(Element.LIGHT);
            monster.setElement2(Element.LIGHT);
            monster.addAwokenSkills(17);
            monster.addAwokenSkills(12);
            monster.addAwokenSkills(11);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(17);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(19);
            monster.setType1(5);
            monster.setType2(2);
            monster.save();
            Log.d("Main Activity Log", "Awakenings: " + monster.getAwokenSkills() + " Size: " + monster.getAwokenSkills().size());
        }
        Log.d("Main Activity Log", "Monster Name: " + BaseMonster.getMonsterId(1218).getName());
        Log.d("Main Activity Log", "Monster 1218 Awakenings: " + BaseMonster.getMonsterId(1218).getAwokenSkills() + " Size: " + BaseMonster.getMonsterId(1218).getAwokenSkills().size());


        switchFragment(MonsterListFragment.newInstance(team, enemy), MonsterListFragment.TAG);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Get the Default External Cache Directory
        File httpCacheDir = getExternalCacheDir();

        // Cache Size of 5MB
        long httpCacheSize = 5 * 1024 * 1024;

        try {
            // Install the custom Cache Implementation
//            HttpResponseCache.install(httpCacheDir, httpCacheSize);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.setGroupVisible(R.id.saveTeamGroup, false);
        menu.setGroupVisible(R.id.searchGroup, false);
//        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MonsterListFragment.TAG);
//        Log.d("What is fragment", "" + fragment);
//        if(fragment instanceof MonsterListFragment){
//            menu.setGroupVisible(R.id.saveTeamGroup, true);
//        }else {
//            menu.setGroupVisible(R.id.saveTeamGroup, false);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.saveTeam) {
            if (teamSaveDialogFragment == null) {
                teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam);
            }
            teamSaveDialogFragment.show(getSupportFragmentManager(), "Show Team Save Dialog");
        } else if (id == R.id.searchMonsters) {
            switchFragment(BaseMonsterListFragment.newInstance(), BaseMonsterListFragment.TAG);
        }

        return super.onOptionsItemSelected(item);
    }

    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
        @Override
        public void overwriteTeam() {
            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
            Team overwriteTeam = new Team(Team.getTeamById(0));
            overwriteTeam.setTeamName(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()).getTeamName());
            overwriteTeam.setTeamId(Team.getTeamById(0).getTeamIdOverwrite());
            Log.d("Main Activity Log", "Overwrite Team name is: " + overwriteTeam.getTeamName() + " Team id: " + overwriteTeam.getTeamId() + " Team ID overwrite: " + overwriteTeam.getTeamIdOverwrite());
            overwriteTeam.save();

        }

        @Override
        public void saveNewTeam(String teamName) {
            long teamId;
            if(Team.getAllTeams().size() == 0){
                teamId = 1;
            }else {
                teamId = Team.getAllTeams().get(Team.getAllTeams().size() - 1   ).getTeamId() + 1;
            }
            Team newTeam = new Team(Team.getTeamById(0));
            newTeam.setTeamName(teamName);
            newTeam.setTeamId(teamId);
            for(Monster monster: newTeam.getMonsters()) {
                Log.d("Monster", "MonsterPlus:" + monster.getTotalPlus());
                monster.save();
            }
            newTeam.save();
            Team teamZero = new Team(newTeam);
            teamZero.setTeamId(0);
            teamZero.setTeamIdOverwrite(newTeam.getTeamId());
            teamZero.save();
            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());

        }
    };


    /**
     * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
//         if (position == 0)
//         {
//            return MainFragment.newInstance(position + 1);
//         }
//         if (position == 2)
//         {
//            Monster kirin = new Monster();
//            return MonsterPageFragment.newInstance(kirin);
//         }
//         if (position == 1)
//         {
//            return EnemyTargetFragment.newInstance();
//         }
//         if(position == 3)
//         {
//            return MonsterListFragment.newInstance("thomas ??", "sup");
//         }
//         if(position == 4)
//         {
//            return TeamDamageListFragment.newInstance("yes", "?");
//         }

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return "Section 4".toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void switchFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.pager, fragment, tag);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
