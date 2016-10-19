package com.padassist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Enemy;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.RealmLeaderSkillType;
import com.padassist.Data.Team;
import com.padassist.Fragments.AboutDialogFragment;
import com.padassist.Fragments.CloseDialogFragment;
import com.padassist.Fragments.DisclaimerDialogFragment;
import com.padassist.Fragments.ManageMonsterTabLayoutFragment;
import com.padassist.Fragments.MonsterListFragment;
import com.padassist.Fragments.NotWiFiDialogFragment;
import com.padassist.Fragments.ThreeProgressDialog;
import com.padassist.Fragments.UnableToConnectDialogFragment;
import com.padassist.Fragments.LoadingFragment;
import com.padassist.Fragments.UpToDateDialogFragment;
import com.padassist.Util.Migration;
import com.padassist.Util.Singleton;

import java.io.File;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


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
    private Fragment mContent;
    private Enemy enemy;
    private Team team;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private AboutDialogFragment aboutDialogFragment;
    private UnableToConnectDialogFragment unableToConnectDialogFragment;
    private NotWiFiDialogFragment notWiFiDialogFragment;
    private SharedPreferences preferences;
    private DisclaimerDialogFragment disclaimerDialog;
    private CloseDialogFragment closeDialogFragment;
    private ProgressDialog progressDialog;
    private ThreeProgressDialog threeProgressDialog;
    private UpToDateDialogFragment upToDateDialogFragment;
    private Toast toast;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.getInstance().setContext(getApplicationContext());

//        RealmConfiguration config = new RealmConfiguration.Builder(this)
//                .schemaVersion(4)
//                .migration(new Migration())
//                .build();
//        Realm.setDefaultConfiguration(config);

//        Log.d("MainActivity", "config schema is: " + config.getSchemaVersion());

        realm = Realm.getDefaultInstance();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        preferences.edit().putString("BaseMonsterList", "").apply();
//        Log.d("MainActivity", "BaseMonsterlist is empty: " + preferences.getString("BaseMonsterList", "").isEmpty());
//        preferences.edit().putInt("version", 1).apply();
        Log.d("MainActivity", "Version is: " + preferences.getInt("version", 1));
        if (preferences.getBoolean("firstRun", true) || BuildConfig.VERSION_CODE > preferences.getInt("version", 1) || realm.where(BaseMonster.class).findAll().size() <= 1) {
            if (preferences.getBoolean("firstRun", true)) {
                preferences.edit().putInt("monsterVersion", 0).apply();
                preferences.edit().putInt("leaderSkillVersion", 0).apply();
                preferences.edit().putInt("activeSkillVersion", 0).apply();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, new LoadingFragment())
                    .commit();

            preferences.edit().putBoolean("firstRun", false).apply();
            preferences.edit().putInt("version", BuildConfig.VERSION_CODE).apply();
            Log.d("MainActivity", "Version is: " + preferences.getInt("version", 1));
        }

//        preferences.edit().putBoolean("showDisclaimer", true).apply();
        Log.d("MainActivity", "Version is: " + preferences.getBoolean("showDisclaimer", true));
        if (preferences.getBoolean("showDisclaimer", true)) {
            disclaimerDialog = DisclaimerDialogFragment.newInstance(new DisclaimerDialogFragment.Preferences() {
                @Override
                public void setShowAgain(boolean showAgain) {
                    if (!showAgain) {
                        preferences.edit().putBoolean("showDisclaimer", true).apply();
                    } else {
                        preferences.edit().putBoolean("showDisclaimer", false).apply();
                    }
                }
            });
            disclaimerDialog.show(getSupportFragmentManager(), "Show Disclaimer");
        }

        Log.d("Total", "Total Monsters: " + realm.where(BaseMonster.class).findAll().size() + " Total Leader Skills: " + realm.where(LeaderSkill.class).findAll().size() + " Total Active Skills: " + realm.where(ActiveSkill.class).findAll().size());
        if (savedInstanceState != null) {
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        enemy = new Enemy();

        if (realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst() == null) {
            BaseMonster monster = new BaseMonster();
            realm.beginTransaction();
            realm.copyToRealm(monster);
            realm.commitTransaction();
        }

        if (realm.where(Monster.class).equalTo("monsterId", 0).findFirst() == null) {
            Monster monster = new Monster(0);
            realm.beginTransaction();
            realm.copyToRealm(monster);
            realm.commitTransaction();
        }
        if (realm.where(Team.class).equalTo("teamId", 0).findFirst() == null) {
            team = new Team();
            team.setLead(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            team.setSub1(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            team.setSub2(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            team.setSub3(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            team.setSub4(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            team.setHelper(realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
            realm.beginTransaction();
            team = realm.copyToRealmOrUpdate(team);
            realm.commitTransaction();
        } else {
            team = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            for (int i = 0; i < team.getMonsters().size(); i++) {
                if (team.getMonsters().get(i) == null) {
                    realm.beginTransaction();
                    team.setMonsters(i, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
                    realm.commitTransaction();
                }
            }
        }

        if (realm.where(OrbMatch.class).findAll().size() != 0) {
            RealmResults<OrbMatch> orbMatchList;
            orbMatchList = realm.where(OrbMatch.class).findAll();
            int i = 0;
            while (Singleton.getInstance().getBoardSize() == 1 && i < orbMatchList.size()) {
                if (orbMatchList.get(i).getOrbsLinked() > 30 || (orbMatchList.get(i).getOrbsLinked() == 7 && orbMatchList.get(i).isRow())) {
                    Singleton.getInstance().setBoardSize(2);
                } else if (orbMatchList.get(i).getOrbsLinked() == 5 && orbMatchList.get(i).isRow()) {
                    Singleton.getInstance().setBoardSize(0);
                }
                i++;
            }
        }

        if (realm.where(LeaderSkill.class).equalTo("name", "Blank").findFirst() == null) {
            LeaderSkill blankLeaderSkill = new LeaderSkill();
            blankLeaderSkill.setName("Blank");
            blankLeaderSkill.setHpSkillType(new RealmLeaderSkillType(0));
            blankLeaderSkill.setAtkSkillType(new RealmLeaderSkillType(0));
            blankLeaderSkill.setRcvSkillType(new RealmLeaderSkillType(0));
            realm.beginTransaction();
            realm.copyToRealm(blankLeaderSkill);
            realm.commitTransaction();
        }

        if (realm.where(ActiveSkill.class).equalTo("name", "Blank").findFirst() == null) {
            ActiveSkill blankActiveSkill = new ActiveSkill();
            realm.beginTransaction();
            realm.copyToRealm(blankActiveSkill);
            realm.commitTransaction();
        }

        switchFragment(MonsterListFragment.newInstance(team, enemy), MonsterListFragment.TAG, "good");


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
    protected void onResume() {
        super.onResume();
        if (enemy == null) {
            enemy = new Enemy();
        }
        if (Realm.getDefaultInstance().getConfiguration() == null) {
            Realm.setDefaultConfiguration(new RealmConfiguration.Builder(this)
                    .schemaVersion(4)
                    .migration(new Migration())
                    .build());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
//                Bundle bundle = data.getExtras();
//                Monster monster = bundle.getParcelable("monster");
//                long monsterId = bundle.getLong("monsterId");
                Monster monster = data.getExtras().getParcelable("monster");
                long monsterId = data.getExtras().getLong("monsterId");
                Log.d("MainActivity", "monsterId is: " + monsterId);
                Log.d("MainActivity", "monster is: " + monster + " extra is: " + data.getExtras());
                Log.d("MainActivity", "monster name is: " + monster.getName());
//                switchFragment(MonsterPageFragment.newInstance(monster, Singleton.getInstance().getMonsterOverwrite()), MonsterPageFragment.TAG, "good");
            }
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
        menu.findItem(R.id.manageMonsters).setVisible(false);
        MenuItem toggleCoop = menu.findItem(R.id.toggleCoop);
        toggleCoop.setTitle(Singleton.getInstance().isCoopEnable() ? "Toggle Co-op off" : "Toggle Co-op on");
//        MenuItem toggleGrid = menu.findItem(R.id.toggleGrid);
//        toggleGrid.setTitle(preferences.getBoolean("isGrid", true) ? "Toggle Grid off" : "Toggle Grid on");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            if (aboutDialogFragment == null) {
//                aboutDialogFragment = new AboutDialogFragment();
//                aboutDialogFragment.show(getSupportFragmentManager(), "yes");
//            }
//        } else if (id == R.id.toggleCoop) {
//            Boolean isEnable = !Singleton.getInstance().isCoopEnable();
//            Singleton.getInstance().setCoopEnable(isEnable);
//            if (isEnable) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(this, "Co-op on", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(this, "Co-op off", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//            item.setTitle(isEnable ? "Toggle Co-op off" : "Toggle Co-op on");
//        } else if (id == R.id.saveTeam) {
//
//        } else if (id == R.id.loadTeam) {
//
//        } else if (id == R.id.monsterList) {
////            switchFragment(MonsterTabLayoutFragment.newInstance(false, 1, Singleton.getInstance().getMonsterOverwrite()), MonsterTabLayoutFragment.TAG, "good");
//        } else if (id == R.id.manageMonsters) {
//            switchFragment(ManageMonsterTabLayoutFragment.newInstance(), ManageMonsterTabLayoutFragment.TAG, "good");
//        }

        switch (id) {
            case R.id.action_settings:
                if (aboutDialogFragment == null) {
                    aboutDialogFragment = new AboutDialogFragment();
                }
                if(!aboutDialogFragment.isAdded()){
                    aboutDialogFragment.show(getSupportFragmentManager(), "yes");
                }
                break;
            case R.id.toggleCoop:
                Boolean isEnable = !Singleton.getInstance().isCoopEnable();
                Singleton.getInstance().setCoopEnable(isEnable);
                if (isEnable) {
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
                break;
            case R.id.manageMonsters:
                switchFragment(ManageMonsterTabLayoutFragment.newInstance(), ManageMonsterTabLayoutFragment.TAG, "good");
                break;
            case R.id.updateCheck:
                ConnectivityManager cm =
                        (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                Log.d("MainActivity", "isConnected: " + isConnected);
                if (isConnected) {
                    boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
                    if (isWiFi) {
                        syncDatabase();
                    } else {
                        if (notWiFiDialogFragment == null) {
                            notWiFiDialogFragment = new NotWiFiDialogFragment();
                        }
                        if(!notWiFiDialogFragment.isAdded()){
                            notWiFiDialogFragment.show(getSupportFragmentManager(), "yes");
                        }
                    }
                } else {
                    if (unableToConnectDialogFragment == null) {
                        unableToConnectDialogFragment = new UnableToConnectDialogFragment();
                    }
                    if(!unableToConnectDialogFragment.isAdded()){
                        unableToConnectDialogFragment.show(getSupportFragmentManager(), "yes");
                    }
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

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

    public void switchFragment(Fragment fragment, String tag, String string) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mContent = fragment;
        transaction.replace(R.id.pager, mContent, tag);
        transaction.addToBackStack(tag).commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
            if (closeDialogFragment == null) {
                closeDialogFragment = CloseDialogFragment.newInstance(closeApplication);
            }
            if (!closeDialogFragment.isAdded()) {
                closeDialogFragment.show(getSupportFragmentManager(), "Close");
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    private void syncDatabase() {
        showProgressDialog();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        DatabaseReference monsterVersionReference = database.getReference("monster_version");
        monsterVersionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("monsterVersion", 1)) {
                    StorageReference storageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/data/monsters.json");

                    File monstersFile = new File(getApplicationContext().getFilesDir(), "monsters.json");
                    storageReference.getFile(monstersFile)
                            .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    threeProgressDialog.setProgressBar1((int) progress);
//                                    progressDialog.setProgress((int)progress);
                                    Log.d("MainActivity", "Monster Progress is: " + progress);
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("MainActivity", "Monster Version Preferences before: " + preferences.getInt("monsterVersion", 1));
                                    preferences.edit().putInt("monsterVersion", dataSnapshot.getValue(Integer.class)).apply();
                                    if (threeProgressDialog.getProgress2() == 100 && threeProgressDialog.getProgress3() == 100) {
                                        hideProgressDialog(true);
                                    }
                                }
                            });
                } else {
                    threeProgressDialog.setProgressBar1(100);
                    if (threeProgressDialog.getProgress2() == 100 && threeProgressDialog.getProgress3() == 100) {
                        hideProgressDialog(false);
                    }
                }
                Log.d("MainActivity", "Monster Version Preferences after: " + preferences.getInt("monsterVersion", 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference leaderSkillVersionReference = database.getReference("leader_skill_version");
        leaderSkillVersionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("leaderSkillVersion", 1)) {
                    StorageReference storageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/data/leader_skills.json");

                    File leaderSkillsFile = new File(getApplicationContext().getFilesDir(), "leader_skills.json");
                    storageReference.getFile(leaderSkillsFile)
                            .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    threeProgressDialog.setProgressBar2((int) progress);
//                                progressDialog.setProgress((int)progress);
                                    Log.d("MainActivity", "Leader Skill Progress is: " + progress);
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("MainActivity", "Leader Skill Version Preferences before: " + preferences.getInt("leaderSkillVersion", 1));
                                    preferences.edit().putInt("leaderSkillVersion", dataSnapshot.getValue(Integer.class)).apply();
                                    if (threeProgressDialog.getProgress1() == 100 && threeProgressDialog.getProgress3() == 100) {
                                        hideProgressDialog(true);
                                    }
                                }
                            });
                } else {
                    threeProgressDialog.setProgressBar2(100);
                    if (threeProgressDialog.getProgress1() == 100 && threeProgressDialog.getProgress3() == 100) {
                        hideProgressDialog(false);
                    }
                }
                Log.d("MainActivity", "Leader Skill Version Preferences after: " + preferences.getInt("leaderSkillVersion", 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference activeSkillVersionReference = database.getReference("active_skill_version");
        activeSkillVersionReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("activeSkillVersion", 1)) {
                    StorageReference storageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/data/active_skills.json");

                    File leaderSkillsFile = new File(getApplicationContext().getFilesDir(), "active_skills.json");
                    storageReference.getFile(leaderSkillsFile)
                            .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    threeProgressDialog.setProgressBar3((int) progress);
//                                    progressDialog.setProgress((int)progress);
                                    Log.d("MainActivity", "Active Skill Progress is: " + progress);
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Log.d("MainActivity", "Active Skill Version Preferences before: " + preferences.getInt("activeSkillVersion", 1));
                                    preferences.edit().putInt("activeSkillVersion", dataSnapshot.getValue(Integer.class)).apply();
                                    if (threeProgressDialog.getProgress1() == 100 && threeProgressDialog.getProgress2() == 100) {
                                        hideProgressDialog(true);
                                    }
                                }
                            });
                } else {
                    threeProgressDialog.setProgressBar3(100);
                    if (threeProgressDialog.getProgress1() == 100 && threeProgressDialog.getProgress2() == 100) {
                        hideProgressDialog(false);
                    }
                }
                Log.d("MainActivity", "Active Skill Version Preferences after: " + preferences.getInt("activeSkillVersion", 1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberOfMonstersReference = database.getReference("num_of_monsters");
        numberOfMonstersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Constants.numOfMonsters = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberOfLeaderSkillsReference = database.getReference("num_of_leader_skills");
        numberOfLeaderSkillsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Constants.numOfLeaderSkills = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberOfActiveSkillsReference = database.getReference("num_of_active_skills");
        numberOfActiveSkillsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Constants.numOfActiveSkills = dataSnapshot.getValue(int.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showProgressDialog() {
//        if(progressDialog == null){
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setCancelable(true);
//            progressDialog.setIndeterminate(false);
//            progressDialog.setMax(100);
//            progressDialog.setProgress(0);
//        }
//        progressDialog.setTitle(title);
//        progressDialog.show();

        if (threeProgressDialog == null) {
            threeProgressDialog = ThreeProgressDialog.newInstance();
        }
        threeProgressDialog.show(getFragmentManager(), "ThreeProgressDialog");
    }

    private void hideProgressDialog(boolean parse) {
//        if(progressDialog != null && progressDialog.isShowing()){
//            progressDialog.dismiss();
//        }
        if (threeProgressDialog != null && threeProgressDialog.isAdded()) {
            threeProgressDialog.dismiss();
        }
        if(parse){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, new LoadingFragment())
                    .commit();
        } else {
            if (upToDateDialogFragment == null) {
                upToDateDialogFragment = new UpToDateDialogFragment();
            }
            if(!upToDateDialogFragment.isAdded()){
                upToDateDialogFragment.show(getSupportFragmentManager(), "yes");
            }
        }
    }

    private CloseDialogFragment.CloseApplication closeApplication = new CloseDialogFragment.CloseApplication() {
        @Override
        public void quit() {
            finish();
        }
    };
}
