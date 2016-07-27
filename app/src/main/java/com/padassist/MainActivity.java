package com.padassist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Enemy;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;
import com.padassist.Fragments.AboutDialogFragment;
import com.padassist.Fragments.AbstractFragment;
import com.padassist.Fragments.MonsterListFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.Fragments.TeamListFragment;
import com.padassist.Fragments.TeamSaveDialogFragment;
import com.padassist.Util.Singleton;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

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
    private AbstractFragment mContent;
    private Enemy enemy;
    private Team team;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private TeamSaveDialogFragment teamSaveDialogFragment;
    private AboutDialogFragment aboutDialogFragment;
    private SharedPreferences preferences;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.getInstance().setContext(getApplicationContext());
        if (android.os.Build.VERSION.SDK_INT >= 20) {
            Configuration.Builder configBuilder = new Configuration.Builder(this);
            configBuilder.addModelClasses(Monster.class);
            configBuilder.addModelClasses(Team.class);
            configBuilder.addModelClass(BaseMonster.class);
            configBuilder.addModelClass(LeaderSkill.class);
            configBuilder.addModelClass(OrbMatch.class);
            ActiveAndroid.initialize(configBuilder.create());
        } else {
            ActiveAndroid.initialize(this);
        }
        preferences = getSharedPreferences("SharedPreferences", MODE_PRIVATE);

//        preferences.edit().putInt("version", 1).apply();
        if(preferences.getBoolean("firstRun", true) || BuildConfig.VERSION_CODE > preferences.getInt("version", 1)){
            Intent loadIntent = new Intent(this, LoadingScreenActivity.class);
            startActivity(loadIntent);
            preferences.edit().putBoolean("firstRun", false).apply();
            preferences.edit().putInt("version", BuildConfig.VERSION_CODE).apply();
        }
//            ParseMonsterDatabaseThread parseMonsterDatabaseThread = new ParseMonsterDatabaseThread();
//            parseMonsterDatabaseThread.start();
//        if(BuildConfig.VERSION_CODE != SharedPreferencesUtil.loadPreferenceInt(Constants.VERSION)) {
//            ParseMonsterDatabaseThread parseMonsterDatabaseThread = new ParseMonsterDatabaseThread();
//            parseMonsterDatabaseThread.start();
//        }

        Log.d("Total", "Total Monsters: " + BaseMonster.getAllMonsters().size() + " Total Leader Skills: " + LeaderSkill.getAllLeaderSkills().size());
        if (savedInstanceState != null) {
            mContent = (AbstractFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        enemy = new Enemy();
        if (Team.getTeamById(0) == null) {
            team = new Team();
        } else {
            team = Team.getTeamById(0);
        }
        if (BaseMonster.getMonsterId(0) == null) {
            BaseMonster monster = new BaseMonster();
            monster.save();
        }
        if (Monster.getMonsterId(0) == null) {
            Monster monster = new Monster(0);
            monster.save();
        }

        if(OrbMatch.getAllOrbMatches().size() != 0){
            ArrayList<OrbMatch> orbMatchList;
            orbMatchList = (ArrayList) OrbMatch.getAllOrbMatches();
            int i = 0;
            while (Singleton.getInstance().getBoardSize() == 1 && i < orbMatchList.size()) {
                if (orbMatchList.get(i).getOrbsLinked() > 30 || (orbMatchList.get(i).getOrbsLinked() == 7 && orbMatchList.get(i).isRow())) {
                    Singleton.getInstance().setBoardSize(2);
                } else if(orbMatchList.get(i).getOrbsLinked() == 5 && orbMatchList.get(i).isRow()){
                    Singleton.getInstance().setBoardSize(0);
                }
                i++;
            }
        }

        LeaderSkill blankLeaderSkill = new LeaderSkill();
        blankLeaderSkill.setName("Blank");
        blankLeaderSkill.setHpSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.setAtkSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.setRcvSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.save();
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
        menu.setGroupVisible(R.id.sortGroup, false);
        menu.setGroupVisible(R.id.sortMoreGroup, false);
        menu.setGroupVisible(R.id.sortTeam, false);
        menu.setGroupVisible(R.id.teamDamage, false);
        menu.findItem(R.id.search).setVisible(false);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                mContent.searchFilter(newText);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//                imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
                return true;
            }
        });
        MenuItem toggleCoop = menu.findItem(R.id.toggleCoop);
        toggleCoop.setTitle(Singleton.getInstance().isCoopEnable() ? "Toggle Co-op off" : "Toggle Co-op on");
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
            if(aboutDialogFragment == null){
                aboutDialogFragment = new AboutDialogFragment();
                aboutDialogFragment.show(getSupportFragmentManager(), "yes");
            }
        } else if(id == R.id.toggleCoop){
            Boolean isEnable = !Singleton.getInstance().isCoopEnable();
            Singleton.getInstance().setCoopEnable(isEnable);
            if(isEnable){
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(this, "Co-op on", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(this, "Co-op off", Toast.LENGTH_SHORT);
                toast.show();
            }
            item.setTitle(isEnable ? "Toggle Co-op off" : "Toggle Co-op on");
        } else if (id == R.id.saveTeam) {
//            if (teamSaveDialogFragment == null) {
//                teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam);
//            }
//            teamSaveDialogFragment.show(getSupportFragmentManager(), "Show Team Save Dialog");
//            mContent.reverseArrayList();
        } else if (id == R.id.loadTeam) {
            switchFragment(TeamListFragment.newInstance(), TeamListFragment.TAG);
        } else if (id == R.id.monsterList) {
            switchFragment(MonsterTabLayoutFragment.newInstance(false, 1, 99), MonsterTabLayoutFragment.TAG);
        } else if (id == R.id.reverseList) {
            mContent.reverseArrayList();
        } else if (id == R.id.sortAlphabetical) {
            mContent.sortArrayList(0);
        } else if (id == R.id.sortId) {
            mContent.sortArrayList(1);
        } else if (id == R.id.sortElement) {
            mContent.sortArrayList(2);
        } else if (id == R.id.sortType) {
            mContent.sortArrayList(3);
        } else if (id == R.id.sortStat) {
            mContent.sortArrayList(4);
        } else if (id == R.id.sortRarity) {
            mContent.sortArrayList(5);
        } else if (id == R.id.sortAwakenings) {
            mContent.sortArrayList(6);
        } else if (id == R.id.sortPlus) {
            mContent.sortArrayList(7);
        } else if (id == R.id.sortFavorite) {
            mContent.sortArrayList(8);
        } else if (id == R.id.sortLevel) {
            mContent.sortArrayList(9);
        } else if (id == R.id.sortLead) {
            mContent.sortArrayList(10);
        } else if (id == R.id.sortHelper) {
            mContent.sortArrayList(11);
        }

        return super.onOptionsItemSelected(item);
    }

//    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
//        @Override
//        public void overwriteTeam() {
//            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
//            Team overwriteTeam = new Team(Team.getTeamById(0));
////            if(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()) != null){
////                overwriteTeam.setTeamName(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()).getTeamName());
//                overwriteTeam.setTeamId(Team.getTeamById(0).getTeamIdOverwrite());
//                Log.d("Main Activity Log", "Overwrite Team name is: " + overwriteTeam.getTeamName() + " Team id: " + overwriteTeam.getTeamId() + " Team ID overwrite: " + overwriteTeam.getTeamIdOverwrite());
//                overwriteTeam.save();
////            } else {
////                overwriteTeam.setTeamId(overwriteTeam.getTeamIdOverwrite());
////                overwriteTeam.save();
////            }
//
//        }
//
//        @Override
//        public void saveNewTeam(String teamName) {
//            long teamId;
//            if (Team.getAllTeams().size() == 0) {
//                teamId = 1;
//            } else {
//                teamId = Team.getAllTeams().get(Team.getAllTeams().size() - 1).getTeamId() + 1;
//            }
//            Team newTeam = new Team(Team.getTeamById(0));
//            newTeam.setTeamName(teamName);
//            newTeam.setTeamId(teamId);
//            for (Monster monster : newTeam.getMonsters()) {
//                Log.d("Monster", "MonsterPlus:" + monster.getTotalPlus());
//                monster.save();
//            }
//            newTeam.save();
//            Team teamZero = new Team(newTeam);
//            teamZero.setTeamId(0);
//            teamZero.setTeamIdOverwrite(newTeam.getTeamId());
//            teamZero.save();
//            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
//        }
//    };


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
//            return OrbMatchFragment.newInstance(position + 1);
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

    public void switchFragment(AbstractFragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mContent = fragment;
        transaction.replace(R.id.pager, mContent, tag);
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
