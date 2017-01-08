package com.padassist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioTrack;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.*;
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
import com.google.firebase.storage.StreamDownloadTask;
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
import com.padassist.Fragments.MainTabLayoutFragment;
import com.padassist.Fragments.ManageMonsterTabLayoutFragment;
import com.padassist.Fragments.MonsterListFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.Fragments.NotWiFiDialogFragment;
import com.padassist.Fragments.ThreeProgressDialog;
import com.padassist.Fragments.UnableToConnectDialogFragment;
import com.padassist.Fragments.LoadingFragment;
import com.padassist.Fragments.UpToDateDialogFragment;
import com.padassist.Fragments.UpdateAvailableDialogFragment;
import com.padassist.Threads.ParseMonsterDatabaseThread;
import com.padassist.Util.Migration;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.RealmSchema;


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
    private AboutDialogFragment aboutDialogFragment;
    private UnableToConnectDialogFragment unableToConnectDialogFragment;
    private NotWiFiDialogFragment notWiFiDialogFragment;
    private SharedPreferences preferences;
    private DisclaimerDialogFragment disclaimerDialog;
    private CloseDialogFragment closeDialogFragment;
    private ProgressDialog progressDialog;
    private ThreeProgressDialog threeProgressDialog;
    private UpToDateDialogFragment upToDateDialogFragment;
    private UpdateAvailableDialogFragment updateAvailableDialogFragment;
    private Toast toast;
    private Realm realm;
    private FirebaseDatabase database;
    private int monsterDifference = 0;
    ArrayList<Long> missingImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.getInstance().setContext(getApplicationContext());

        realm = Realm.getDefaultInstance();
        database = FirebaseDatabase.getInstance();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

//        preferences.edit().putInt("monsterVersion", 2).apply();

//        preferences.edit().putLong("schemaVersion", 4).apply();

        Log.d("MainActivity", "Schema version preferences is: " + preferences.getLong("schemaVersion", 4) + " schema version is: " + realm.getConfiguration().getSchemaVersion());
        if (preferences.getLong("schemaVersion", 4) < realm.getConfiguration().getSchemaVersion()) {

            updateConstants();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, new LoadingFragment())
                    .commit();

            preferences.edit().putLong("schemaVersion", realm.getConfiguration().getSchemaVersion()).apply();
        }

//        if(realm.where(BaseMonster.class).equalTo("monsterId", -3).findFirst() != null){
//            realm.beginTransaction();
//            realm.where(BaseMonster.class).equalTo("monsterId", -3).findFirst().deleteFromRealm();
//            realm.commitTransaction();
//        }
//        File image = new File(getFilesDir(), "monster_images/monster_-3.png");
//        Log.d("MainActivity", "Does image exist: " + image.exists());
//        if(image.exists()){
//            image.delete();
//        }
//        Log.d("MainActivity", "Does image exist: " + image.exists());
//        Log.d("MainActivity", "image results size is: " + realm.where(BaseMonster.class).findAll().size());

        DatabaseReference appVersion = database.getReference("current_app_version");
        appVersion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > BuildConfig.VERSION_CODE) {
                    if (updateAvailableDialogFragment == null) {
                        updateAvailableDialogFragment = new UpdateAvailableDialogFragment();
                    }
                    if (!updateAvailableDialogFragment.isAdded()) {
                        updateAvailableDialogFragment.show(getSupportFragmentManager(), "yes");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Log.d("MainActivity", "Version is: " + preferences.getInt("version", 1));
        if (preferences.getBoolean("firstRun", true)) {
            syncDatabase(true);
            preferences.edit().putBoolean("firstRun", false).apply();
        } else if (preferences.getInt("monsterVersion", 1) <= 1) {
            syncDatabase(true);
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
//        enemy = new Enemy();

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

        if (realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst() == null) {
            BaseMonster monster = new BaseMonster();
            monster.setLeaderSkill(realm.where(LeaderSkill.class).equalTo("name", monster.getLeaderSkillString()).findFirst());
            monster.setActiveSkill(realm.where(ActiveSkill.class).equalTo("name", monster.getActiveSkillString()).findFirst());
            monster.setType1String(ParseMonsterDatabaseThread.getTypeString(monster.getType1()));
            monster.setType2String(ParseMonsterDatabaseThread.getTypeString(monster.getType2()));
            monster.setType3String(ParseMonsterDatabaseThread.getTypeString(monster.getType3()));
            monster.setMonsterIdString(String.valueOf(monster.getMonsterId()));
            realm.beginTransaction();
            realm.copyToRealm(monster);
            realm.commitTransaction();
        } else {
            BaseMonster monster = realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst();
            if(monster.getLeaderSkill() == null){
                realm.beginTransaction();
                monster.setLeaderSkill(realm.where(LeaderSkill.class).equalTo("name", monster.getLeaderSkillString()).findFirst());
                monster.setActiveSkill(realm.where(ActiveSkill.class).equalTo("name", monster.getActiveSkillString()).findFirst());
                monster.setType1String(ParseMonsterDatabaseThread.getTypeString(monster.getType1()));
                monster.setType2String(ParseMonsterDatabaseThread.getTypeString(monster.getType2()));
                monster.setType3String(ParseMonsterDatabaseThread.getTypeString(monster.getType3()));
                monster.setMonsterIdString(String.valueOf(monster.getMonsterId()));
                realm.commitTransaction();
            }
        }

        if (realm.where(Monster.class).equalTo("monsterId", 0).findFirst() == null) {
            Monster monster = new Monster(realm.where(BaseMonster.class).equalTo("monsterId", 0).findFirst());
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
            RealmResults<OrbMatch> orbMatchResults = realm.where(OrbMatch.class).findAll();

            team.getOrbMatches().addAll(realm.copyFromRealm(orbMatchResults));
        }

//        realm.beginTransaction();
//        realm.where(Enemy.class).findAll().deleteAllFromRealm();
//        realm.commitTransaction();

        if(realm.where(Enemy.class).equalTo("enemyId", 0).findFirst() == null){
            enemy = new Enemy(2078L);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(enemy);
            realm.commitTransaction();
        } else {
            enemy = realm.where(Enemy.class).equalTo("enemyId", 0).findFirst();
            enemy = realm.copyFromRealm(enemy);
        }

        Parcelable enemyParcel = Parcels.wrap(enemy);
        Parcelable teamParcel = Parcels.wrap(team);

//        switchFragment(MonsterListFragment.newInstance(enemyParcel), MonsterListFragment.TAG, "good");
        setTitle("Set Team");
        switchFragment(MainTabLayoutFragment.newInstance(teamParcel, enemyParcel), MainTabLayoutFragment.TAG, "good");


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
//        if (Realm.getDefaultInstance().getConfiguration() == null) {
//            Realm.init(this);
//            Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
//                    .schemaVersion(5)
//                    .migration(new Migration())
//                    .build());
//        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        DatabaseReference monsterVersion = database.getReference("monster_version");
        monsterVersion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("monsterVersion", 1)) {
                    if (toast == null) {
                        toast = Toast.makeText(getApplicationContext(), "Database Update Available", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference leaderSkillVersion = database.getReference("leader_skill_version");
        leaderSkillVersion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("leaderSkillVersion", 1)) {
                    if (toast == null) {
                        toast = Toast.makeText(getApplicationContext(), "Database Update Available", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference activeSkillVersion = database.getReference("active_skill_version");
        activeSkillVersion.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(int.class) > preferences.getInt("activeSkillVersion", 1)) {
                    if (toast == null) {
                        toast = Toast.makeText(getApplicationContext(), "Database Update Available", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

        switch (id) {
            case R.id.action_settings:
                if (aboutDialogFragment == null) {
                    aboutDialogFragment = new AboutDialogFragment();
                }
                if (!aboutDialogFragment.isAdded()) {
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
                        syncDatabase(false);
                    } else {
                        if (notWiFiDialogFragment == null) {
                            notWiFiDialogFragment = NotWiFiDialogFragment.newInstance(syncDatabase);
                        }
                        if (!notWiFiDialogFragment.isAdded()) {
                            notWiFiDialogFragment.show(getSupportFragmentManager(), "yes");
                        }
                    }
                } else {
                    if (unableToConnectDialogFragment == null) {
                        unableToConnectDialogFragment = new UnableToConnectDialogFragment();
                    }
                    if (!unableToConnectDialogFragment.isAdded()) {
                        unableToConnectDialogFragment.show(getSupportFragmentManager(), "yes");
                    }
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private NotWiFiDialogFragment.SyncDatabase syncDatabase = new NotWiFiDialogFragment.SyncDatabase() {
        @Override
        public void accept() {
            syncDatabase(false);
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

    private void syncDatabase(final boolean downloadEverything) {
        showProgressDialog();
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        updateConstants();

        Log.d("MainActivity", "downloadEverything is: " + downloadEverything + ", monsterDifference is: " + monsterDifference);

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
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    preferences.edit().putInt("monsterVersion", dataSnapshot.getValue(Integer.class)).apply();

                                    StorageReference monsterImageReference;
                                    final File monsterImage;
                                    File folder = new File(getFilesDir(), "monster_images");
                                    if (!folder.exists()) {
                                        folder.mkdir();
                                    }

                                    missingImages = new ArrayList<>();
                                    File imageCheck;

                                    RealmResults<BaseMonster> results = realm.where(BaseMonster.class).findAll();
                                    for (BaseMonster monster : results) {
                                        imageCheck = new File(getFilesDir(), "monster_images/monster_" + monster.getMonsterId() + ".png");
                                        if (!imageCheck.exists() && monster.getMonsterId() != 0) {
                                            missingImages.add(monster.getMonsterId());
                                        }
                                    }
//
////                                    ExecutorService taskExecutor = Executors.newFixedThreadPool(2);
////                                    int i = 0;
////                                    while(i < results.size()){
////                                        monsterImageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/monster_images/monster_" + i + ".png");
////                                        monsterImage = new File(getApplicationContext().getFilesDir(), "monster_images/monster_" + i + ".png");
////                                        taskExecutor.execute(monsterImageReference.getFile(monsterImage)
////                                                .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
////                                                    @Override
////                                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
////                                                    }
////                                                })
////                                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
////                                                    @Override
////                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
////                                                    }
////                                                }));
////                                    }

                                    Log.d("MainActivity", "missingImages.size: " + missingImages.size() + " downloadEverything is: " + downloadEverything);
                                    if (downloadEverything || missingImages.size() > 25 || monsterDifference > 25) {
                                        monsterImageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/monster_images/monster_images.zip");
                                        monsterImage = new File(getFilesDir(), "monster_images/monster_images.zip");
                                        monsterImageReference.getFile(monsterImage)
                                                .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                                        threeProgressDialog.setProgressBar1((int) progress);
                                                    }
                                                })
                                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        byte[] buffer = new byte[1024];

                                                        try {
                                                            File folder = new File(getFilesDir(), "monster_images");
                                                            if (!folder.exists()) {
                                                                folder.mkdir();
                                                            }
                                                            ZipInputStream zis = new ZipInputStream(new FileInputStream(monsterImage.getAbsolutePath()));
                                                            ZipEntry ze = zis.getNextEntry();

                                                            while (ze != null) {
                                                                String fileName = ze.getName();
                                                                File newFile = new File(folder + File.separator + fileName);

                                                                new File(newFile.getParent()).mkdirs();

                                                                FileOutputStream fos = new FileOutputStream(newFile);

                                                                int len;
                                                                while ((len = zis.read(buffer)) > 0) {
                                                                    fos.write(buffer, 0, len);
                                                                }

                                                                fos.close();
                                                                ze = zis.getNextEntry();
                                                            }
                                                            zis.closeEntry();
                                                            zis.close();
                                                            monsterImage.delete();
                                                            if (threeProgressDialog.getProgress2() == 100 && threeProgressDialog.getProgress3() == 100) {
                                                                hideProgressDialog(true);
                                                            }

                                                        } catch (FileNotFoundException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException ex) {
                                                            ex.printStackTrace();
                                                        }


                                                    }
                                                });
                                        monsterDifference = 0;
                                    } else {
                                        for (int i = 0; i < missingImages.size(); i++) {
                                            monsterImageReference = storage.getReferenceFromUrl("gs://padassist-7b3cf.appspot.com/monster_images/all/monster_" + missingImages.get(i) + ".png");
                                            imageCheck = new File(getApplicationContext().getFilesDir(), "monster_images/monster_" + missingImages.get(i) + ".png");

                                            monsterImageReference.getFile(imageCheck)
                                                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        }
                                                    })
                                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        }
                                                    });
                                        }
                                        if (threeProgressDialog.getProgress2() == 100 && threeProgressDialog.getProgress3() == 100) {
                                            hideProgressDialog(true);
                                        }
                                    }

                                }
                            });
                } else {
                    threeProgressDialog.setProgressBar1(100);
                    if (threeProgressDialog.getProgress2() == 100 && threeProgressDialog.getProgress3() == 100) {
                        hideProgressDialog(false);
                    }
                }
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
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
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
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
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
        if (!threeProgressDialog.isAdded()) {
            threeProgressDialog.show(getFragmentManager(), "ThreeProgressDialog");
            threeProgressDialog.setCancelable(false);
        }
    }

    private void hideProgressDialog(boolean parse) {
//        if(progressDialog != null && progressDialog.isShowing()){
//            progressDialog.dismiss();
//        }
        if (threeProgressDialog != null && threeProgressDialog.isAdded()) {
            threeProgressDialog.dismiss();
        }
        if (parse) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.pager, new LoadingFragment(), LoadingFragment.TAG)
                    .addToBackStack(LoadingFragment.TAG)
                    .commit();

        } else {
            if (upToDateDialogFragment == null) {
                upToDateDialogFragment = UpToDateDialogFragment.newInstance(forceSync);
            }
            if (!upToDateDialogFragment.isAdded()) {
                upToDateDialogFragment.show(getSupportFragmentManager(), "yes");
            }
        }
    }

    private UpToDateDialogFragment.Preferences forceSync = new UpToDateDialogFragment.Preferences() {
        @Override
        public void setShowAgain(boolean showAgain) {
            preferences.edit().putInt("monsterVersion", 1).apply();
            preferences.edit().putInt("leaderSkillVersion", 1).apply();
            preferences.edit().putInt("activeSkillVersion", 1).apply();
            syncDatabase(showAgain);
        }
    };

    private void updateConstants() {
        DatabaseReference numberOfMonstersReference = database.getReference("num_of_monsters");
        numberOfMonstersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (preferences.getInt("numOfMonsters", 1) != dataSnapshot.getValue(int.class)) {
                    monsterDifference = dataSnapshot.getValue(int.class) - preferences.getInt("numOfMonsters", 1);
                }
                preferences.edit().putInt("numOfMonsters", dataSnapshot.getValue(int.class)).apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberOfLeaderSkillsReference = database.getReference("num_of_leader_skills");
        numberOfLeaderSkillsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferences.edit().putInt("numOfLeaderSkills", dataSnapshot.getValue(int.class)).apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference numberOfActiveSkillsReference = database.getReference("num_of_active_skills");
        numberOfActiveSkillsReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                preferences.edit().putInt("numOfActiveSkills", dataSnapshot.getValue(int.class)).apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private CloseDialogFragment.CloseApplication closeApplication = new CloseDialogFragment.CloseApplication() {
        @Override
        public void quit() {
            finish();
        }
    };
}
