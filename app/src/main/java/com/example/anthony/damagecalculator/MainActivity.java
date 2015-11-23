package com.example.anthony.damagecalculator;

import android.app.SearchManager;
import android.content.Context;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.support.v7.widget.SearchView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.LeaderSkill;
import com.example.anthony.damagecalculator.Data.LeaderSkillType;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.Fragments.AbstractFragment;
import com.example.anthony.damagecalculator.Fragments.MonsterListFragment;
import com.example.anthony.damagecalculator.Fragments.MonsterTabLayoutFragment;
import com.example.anthony.damagecalculator.Fragments.TeamListFragment;
import com.example.anthony.damagecalculator.Fragments.TeamSaveDialogFragment;
import com.example.anthony.damagecalculator.Threads.ParseMonsterDatabaseThread;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.io.File;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Singleton.getInstance().setContext(getApplicationContext());
        ParseMonsterDatabaseThread parseMonsterDatabaseThread = new ParseMonsterDatabaseThread();
        parseMonsterDatabaseThread.start();
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
        if (savedInstanceState != null) {
            mContent = (AbstractFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
        }
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
//      mViewPager = (ViewPager) findViewById(R.id.pager);
//      mViewPager.setAdapter(mSectionsPagerAdapter);
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
        if (BaseMonster.getMonsterId(1218) == null) {
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
            monster.setRarity(7);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.getEvolutions().add(Long.valueOf(2077));
            monster.setLeaderSkill("Test");
            monster.save();
            Log.d("Main Activity Log", "Awakenings: " + monster.getAwokenSkills() + " Size: " + monster.getAwokenSkills().size() + " " + monster.getName() + " " + monster.getId());
            Log.d("Main Activity Log", "Leader skill is: " + monster.getLeaderSkill());
        }
        if (BaseMonster.getMonsterId(1099) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1099);
            monster.setMonsterPicture(R.drawable.monster_1099);
            monster.setName("Crimson Lotus Mistress, Echidna");
            monster.setMaxLevel(99);
            monster.setAtkMax(1349);
            monster.setAtkMin(452);
            monster.setHpMax(1277);
            monster.setHpMin(672);
            monster.setRcvMax(650);
            monster.setRcvMin(406);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(3);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.RED);
            monster.addAwokenSkills(11);
            monster.addAwokenSkills(3);
            monster.addAwokenSkills(2);
            monster.setType1(3);
            monster.setRarity(6);
            monster.setTeamCost(23);
            monster.setXpCurve(4000000);
            monster.getEvolutions().add(Long.valueOf(201));
            monster.save();
        }
        if (BaseMonster.getMonsterId(201) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(201);
            monster.setMonsterPicture(R.drawable.monster_201);
            monster.setName("Empress of Serpents, Echidna");
            monster.setMaxLevel(99);
            monster.setAtkMax(949);
            monster.setAtkMin(452);
            monster.setHpMax(1277);
            monster.setHpMin(672);
            monster.setRcvMax(650);
            monster.setRcvMin(406);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(3);
            monster.setElement1(Element.RED);
            monster.addAwokenSkills(11);
            monster.addAwokenSkills(3);
            monster.addAwokenSkills(2);
            monster.setType1(3);
            monster.setRarity(5);
            monster.setTeamCost(13);
            monster.setXpCurve(4000000);
            monster.getEvolutions().add(Long.valueOf(1099));
            monster.save();
        }
        if (BaseMonster.getMonsterId(1727) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1727);
            monster.setMonsterPicture(R.drawable.monster_1727);
            monster.setName("Divine Law Goddess, Valkyrie Rose");
            monster.setMaxLevel(99);
            monster.setAtkMax(1389);
            monster.setAtkMin(996);
            monster.setHpMax(2308);
            monster.setHpMin(1593);
            monster.setRcvMax(656);
            monster.setRcvMin(438);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(5);
            monster.setElement1(Element.LIGHT);
            monster.setElement2(Element.GREEN);
            monster.addAwokenSkills(1);
            monster.addAwokenSkills(17);
            monster.addAwokenSkills(2);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(17);
            monster.setType1(3);
            monster.setType2(5);
            monster.setRarity(8);
            monster.setTeamCost(45);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test3");
            monster.save();
        }
        if (BaseMonster.getMonsterId(1217) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1217);
            monster.setMonsterPicture(R.drawable.monster_1217);
            monster.setName("Keeper of the Sacred Texts, Metatron");
            monster.setMaxLevel(99);
            monster.setAtkMax(1330);
            monster.setAtkMin(582);
            monster.setHpMax(2454);
            monster.setHpMin(981);
            monster.setRcvMax(863);
            monster.setRcvMin(345);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(6);
            monster.setElement1(Element.LIGHT);
            monster.setElement2(Element.BLUE);
            monster.addAwokenSkills(9);
            monster.addAwokenSkills(10);
            monster.addAwokenSkills(10);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(20);
            monster.setType1(5);
            monster.setType2(3);
            monster.setRarity(7);
            monster.setTeamCost(60);
            monster.setXpCurve(5000000);
            monster.setLeaderSkill("Test5");
            monster.save();
        }
        if (BaseMonster.getMonsterId(1298) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1298);
            monster.setMonsterPicture(R.drawable.monster_1298);
            monster.setName("Devoted Miko Goddess, Kushinadahime");
            monster.setMaxLevel(99);
            monster.setAtkMax(1282);
            monster.setAtkMin(629);
            monster.setHpMax(2805);
            monster.setHpMin(1042);
            monster.setRcvMax(640);
            monster.setRcvMin(339);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(7);
            monster.setElement1(Element.GREEN);
            monster.setElement2(Element.GREEN);
            monster.addAwokenSkills(16);
            monster.addAwokenSkills(20);
            monster.addAwokenSkills(2);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(19);
            monster.addAwokenSkills(24);
            monster.addAwokenSkills(9);
            monster.setType1(5);
            monster.setType2(3);
            monster.setRarity(7);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test2");
            monster.save();
        }
        if (BaseMonster.getMonsterId(2077) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(2077);
            monster.setMonsterPicture(R.drawable.monster_2077);
            monster.setName("Green Gleaming Star Kirin, Sakuya");
            monster.setMaxLevel(99);
            monster.setAtkMax(1370);
            monster.setAtkMin(913);
            monster.setHpMax(4028);
            monster.setHpMin(1271);
            monster.setRcvMax(384);
            monster.setRcvMin(256);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(9);
            monster.setElement1(Element.LIGHT);
            monster.setElement2(Element.GREEN);
            monster.addAwokenSkills(17);
            monster.addAwokenSkills(12);
            monster.addAwokenSkills(11);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(17);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(19);
            monster.addAwokenSkills(13);
            monster.addAwokenSkills(27);
            monster.setType1(5);
            monster.setType2(2);
            monster.setType3(4);
            monster.setRarity(8);
            monster.setTeamCost(45);
            monster.setXpCurve(4000000);
            monster.getEvolutions().add(Long.valueOf(1218));
            monster.setLeaderSkill("Test4");
            monster.save();
        }
        if (BaseMonster.getMonsterId(1954) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1954);
            monster.setMonsterPicture(R.drawable.monster_1954);
            monster.setName("Awoken Shiva");
            monster.setMaxLevel(99);
            monster.setAtkMax(1902);
            monster.setAtkMin(968);
            monster.setHpMax(3731);
            monster.setHpMin(1586);
            monster.setRcvMax(53);
            monster.setRcvMin(83);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(8);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.RED);
            monster.addAwokenSkills(19);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(14);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(19);
            monster.setType1(6);
            monster.setType2(7);
            monster.setRarity(7);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test6");
            monster.save();
        }
        if (BaseMonster.getMonsterId(1728) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1728);
            monster.setMonsterPicture(R.drawable.monster_1728);
            monster.setName("Phoenix Goddess, Valkyrie Femme");
            monster.setMaxLevel(99);
            monster.setAtkMax(1489);
            monster.setAtkMin(996);
            monster.setHpMax(2508);
            monster.setHpMin(1593);
            monster.setRcvMax(656);
            monster.setRcvMin(438);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(5);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.LIGHT);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(22);
            monster.addAwokenSkills(2);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(27);
            monster.setType1(3);
            monster.setType2(5);
            monster.setType3(6);
            monster.setRarity(8);
            monster.setTeamCost(45);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test3");
            monster.save();
        }
        if (BaseMonster.getMonsterId(1726) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1726);
            monster.setMonsterPicture(R.drawable.monster_1726);
            monster.setName("Awoken Hinokagutsuchi");
            monster.setMaxLevel(99);
            monster.setAtkMax(1610);
            monster.setAtkMin(743);
            monster.setHpMax(4120);
            monster.setHpMin(1230);
            monster.setRcvMax(91);
            monster.setRcvMin(195);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(8);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.DARK);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(22);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(18);
            monster.addAwokenSkills(9);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(14);
            monster.setType1(4);
            monster.setType2(2);
            monster.setRarity(7);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.save();
        }
        if (BaseMonster.getMonsterId(2009) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(2009);
            monster.setMonsterPicture(R.drawable.monster_2009);
            monster.setName("Awoken Horus");
            monster.setMaxLevel(99);
            monster.setAtkMax(1324);
            monster.setAtkMin(749);
            monster.setHpMax(3375);
            monster.setHpMin(1202);
            monster.setRcvMax(570);
            monster.setRcvMin(380);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(8);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.DARK);
            monster.addAwokenSkills(14);
            monster.addAwokenSkills(14);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(19);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(27);
            monster.setType1(7);
            monster.setType2(5);
            monster.setRarity(7);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test7");
            monster.save();
        }
        if (BaseMonster.getMonsterId(2288) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(2288);
            monster.setMonsterPicture(R.drawable.monster_2288);
            monster.setName("Resting Norn, Urd");
            monster.setMaxLevel(99);
            monster.setAtkMax(1688);
            monster.setAtkMin(938);
            monster.setHpMax(3715);
            monster.setHpMin(1086);
            monster.setRcvMax(122);
            monster.setRcvMin(37);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(5);
            monster.setElement1(Element.BLUE);
            monster.setElement2(Element.RED);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(15);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(15);
            monster.setType1(5);
            monster.setType2(2);
            monster.setRarity(6);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test8");
            monster.getEvolutions().add(Long.valueOf(2289));
            monster.save();
        }
        if (BaseMonster.getMonsterId(2289) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(2289);
            monster.setMonsterPicture(R.drawable.monster_2289);
            monster.setName("Paradise Norn, Urd");
            monster.setMaxLevel(99);
            monster.setAtkMax(1688);
            monster.setAtkMin(938);
            monster.setHpMax(3715);
            monster.setHpMin(1086);
            monster.setRcvMax(122);
            monster.setRcvMin(37);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(5);
            monster.setElement1(Element.BLUE);
            monster.setElement2(Element.RED);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(15);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(15);
            monster.setType1(5);
            monster.setType2(2);
            monster.setRarity(6);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test8");
            monster.getEvolutions().add(Long.valueOf(2288));
            monster.save();
        }
        if (BaseMonster.getMonsterId(2094) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(2094);
            monster.setMonsterPicture(R.drawable.monster_2094);
            monster.setName("Phoenix Rider, Valen");
            monster.setMaxLevel(99);
            monster.setAtkMax(1508);
            monster.setAtkMin(782);
            monster.setHpMax(2290);
            monster.setHpMin(916);
            monster.setRcvMax(353);
            monster.setRcvMin(196);
            monster.setAtkScale(1);
            monster.setHpScale(1);
            monster.setRcvScale(1);
            monster.setMaxAwakenings(5);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.BLUE);
            monster.addAwokenSkills(22);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(21);
            monster.setType1(6);
            monster.setType2(3);
            monster.setRarity(6);
            monster.setTeamCost(35);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Blank");
            monster.save();
        }

        if (BaseMonster.getMonsterId(1317) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1317);
            monster.setMonsterPicture(R.drawable.monster_1317);
            monster.setName("King Mastering");
            monster.setMaxLevel(99);
            monster.setAtkMax(1300);
            monster.setAtkMin(500);
            monster.setHpMax(4500);
            monster.setHpMin(1000);
            monster.setRcvMax(200);
            monster.setRcvMin(50);
            monster.setAtkScale(1.5);
            monster.setHpScale(1.5);
            monster.setRcvScale(1.5);
            monster.setMaxAwakenings(2);
            monster.setElement1(Element.RED);
            monster.setElement2(Element.RED);
            monster.addAwokenSkills(13);
            monster.addAwokenSkills(14);
            monster.setType1(2);
            monster.setRarity(6);
            monster.setTeamCost(60);
            monster.setXpCurve(4000000);
            monster.setLeaderSkill("Test9");
            monster.save();
        }

        if (BaseMonster.getMonsterId(1848) == null) {
            BaseMonster monster = new BaseMonster();
            monster.setMonsterId(1848);
            monster.setMonsterPicture(R.drawable.monster_1848);
            monster.setName("Angel of Secret Destiny, Elia");
            monster.setMaxLevel(99);
            monster.setAtkMax(1523);
            monster.setAtkMin(435);
            monster.setHpMax(2651);
            monster.setHpMin(589);
            monster.setRcvMax(614);
            monster.setRcvMin(186);
            monster.setAtkScale(1.5);
            monster.setHpScale(1.5);
            monster.setRcvScale(1.5);
            monster.setMaxAwakenings(6);
            monster.setElement1(Element.LIGHT);
            monster.setElement2(Element.LIGHT);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(27);
            monster.addAwokenSkills(21);
            monster.addAwokenSkills(28);
            monster.addAwokenSkills(8);
            monster.addAwokenSkills(8);
            monster.setType1(5);
            monster.setType2(3);
            monster.setRarity(7);
            monster.setTeamCost(99);
            monster.setXpCurve(6000000);
            monster.setLeaderSkill("Blank");
            monster.save();
        }
//        BaseMonster newMonster = BaseMonster.getMonsterId(1218);
//        Log.d("Main Activity Log", "Monster Name: " + newMonster.getName());
//        Log.d("Main Activity Log", "Monster 1218 Awakenings: " + newMonster.getId() + " name: " + newMonster.getName() + " " + newMonster.getAwokenSkills() + " Size: " + newMonster.getAwokenSkills().size());

        LeaderSkill blankLeaderSkill = new LeaderSkill();
        blankLeaderSkill.setName("Blank");
        blankLeaderSkill.setHpSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.setAtkSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.setRcvSkillType(LeaderSkillType.BLANK);
        blankLeaderSkill.save();

        LeaderSkill leaderSkill = new LeaderSkill();
        leaderSkill.setAtkSkillType(LeaderSkillType.MATCH_ELEMENT);
        leaderSkill.setName("Test");
//        leaderSkill.addAtkData(1.2);
//        leaderSkill.addAtkData(1.3);
//        leaderSkill.addAtkData(1.5);
//        leaderSkill.addAtkData(2.0);
//        leaderSkill.addAtkData(2.5);
//        leaderSkill.addAtkData(3.);
//        leaderSkill.addAtkData(3.5);
//        leaderSkill.addAtkData(4.0);
//        leaderSkill.addAtkData(4.5);
        leaderSkill.addAtkData(5.);
//        leaderSkill.addAtkData(5.5);
//        leaderSkill.addAtkData(6.);
//        leaderSkill.addAtkData(6.5);
//        leaderSkill.addAtkData(7.);
//        leaderSkill.addAtkData(7.5);
//        leaderSkill.addAtkData(8.);
//        leaderSkill.addAtkData(8.5);
//        leaderSkill.addAtkData(9.);
//        leaderSkill.addAtkData(9.5);
//        leaderSkill.addAtkData(10.);
//        leaderSkill.addAtkData(11.);
//        leaderSkill.addAtkData(12.);
//        leaderSkill.addAtkData(13.);
//        leaderSkill.addAtkData(14.);
//        leaderSkill.addAtkData(15.);
//        leaderSkill.addAtkData(1.2);
        //leaderSkill.addAtkElement(0);
        //leaderSkill.addAtkType(3);
        leaderSkill.addAtkType(5);
        leaderSkill.addAtkElement(3);
//        leaderSkill.addAtkElement(2);
        leaderSkill.setComboMin(4);
        leaderSkill.setComboMax(4);
        leaderSkill.setComboMin2(2);
        leaderSkill.setComboMax2(2);
        leaderSkill.addMatchmonsters((long) 1099);
        leaderSkill.addMatchmonsters((long) 1217);
        leaderSkill.addMatchElements(Element.RED);
        leaderSkill.addMatchElements(Element.BLUE);
        leaderSkill.addMatchElements(Element.GREEN);
        leaderSkill.addMatchElements(Element.LIGHT);
//        leaderSkill.addMatchElements(Element.LIGHT);
//        leaderSkill.addMatchElements(Element.LIGHT);
        //leaderSkill.addMatchElements(Element.DARK);
//        leaderSkill.addMatchElements(Element.HEART);
        leaderSkill.getMatchElements2().add(Element.HEART);
//        leaderSkill.getMatchElements2().add(Element.HEART);
//        leaderSkill.getHpPercent().add(20);
//        leaderSkill.getHpPercent().add(0);
//        leaderSkill.getHpPercent().add(50);
//        leaderSkill.getHpPercent().add(20);
        leaderSkill.save();
        Log.d("Main Activity Log", "Match Elements 2 is: " + LeaderSkill.getLeaderSkill("Test").getMatchElements2());

        LeaderSkill leaderSkill2 = new LeaderSkill();
        leaderSkill2.setAtkSkillType(LeaderSkillType.COMBO);
        leaderSkill2.setName("Test2");
        leaderSkill2.addAtkData(1.5);
        leaderSkill2.addAtkData(2.0);
        leaderSkill2.addAtkData(2.5);
        leaderSkill2.addAtkData(3.);
        leaderSkill2.addAtkData(3.5);
        leaderSkill2.addAtkData(4.0);
        leaderSkill2.addAtkData(4.5);
        leaderSkill2.addAtkData(5.);
        leaderSkill2.addAtkData(5.5);
        leaderSkill2.addAtkData(6.);
        leaderSkill2.addAtkData(6.5);
        leaderSkill2.addAtkData(7.);
        leaderSkill2.addAtkData(7.5);
        leaderSkill2.addAtkData(8.);
        leaderSkill2.addAtkData(8.5);
        leaderSkill2.addAtkData(9.);
        leaderSkill2.addAtkData(9.5);
        leaderSkill2.addAtkData(10.);
        leaderSkill2.setComboMin(3);
        leaderSkill2.setComboMax(20);
        leaderSkill2.save();

        LeaderSkill leaderSkill3 = new LeaderSkill();
        leaderSkill3.setAtkSkillType(LeaderSkillType.FLAT);
        leaderSkill3.setName("Test3");
        leaderSkill3.addAtkData(3.);
        leaderSkill3.addAtkType(3);
        leaderSkill3.addAtkType(5);
        leaderSkill3.save();

        LeaderSkill leaderSkill4 = new LeaderSkill();
        leaderSkill4.setAtkSkillType(LeaderSkillType.MATCH_ELEMENT_ACTIVE);
        leaderSkill4.setName("Test4");
        leaderSkill4.addAtkData(5.);
        leaderSkill4.addAtkData(1.2);
        leaderSkill4.setComboMin(4);
        leaderSkill4.setComboMax(4);
        leaderSkill4.addAtkType(5);
        leaderSkill4.addMatchElements(Element.RED);
        leaderSkill4.addMatchElements(Element.BLUE);
        leaderSkill4.addMatchElements(Element.GREEN);
        leaderSkill4.addMatchElements(Element.LIGHT);
        leaderSkill4.save();

        LeaderSkill leaderSkill5 = new LeaderSkill();
        leaderSkill5.setAtkSkillType(LeaderSkillType.HP_FLAT);
        leaderSkill5.setHpSkillType(LeaderSkillType.FLAT);
        leaderSkill5.setName("Test5");
        leaderSkill5.addHpData(1.25);
        leaderSkill5.addHpType(3);
        leaderSkill5.addAtkData(3.5);
        leaderSkill5.addAtkType(3);
        leaderSkill5.getHpPercent().add(100);
        leaderSkill5.getHpPercent().add(50);
        leaderSkill5.save();
        Log.d("Main Activity Log", "HpPercent is: " + LeaderSkill.getLeaderSkill("Test5").getHpPercent());

        LeaderSkill leaderSkill6 = new LeaderSkill();
        leaderSkill6.setAtkSkillType(LeaderSkillType.COMBO_FLAT);
        leaderSkill6.setRcvSkillType(LeaderSkillType.FLAT);
        leaderSkill6.setName("Test6");
        leaderSkill6.addAtkData(2.);
        leaderSkill6.addAtkData(2.5);
        leaderSkill6.addAtkData(3.);
        leaderSkill6.addAtkData(1.5);
        leaderSkill6.addRcvData(1.5);
        leaderSkill6.setComboMin(4);
        leaderSkill6.setComboMax(6);
        leaderSkill6.addAtkElement(0);
        leaderSkill6.addRcvElement(0);
        leaderSkill6.save();

        LeaderSkill leaderSkill7 = new LeaderSkill();
        leaderSkill7.setAtkSkillType(LeaderSkillType.MATCH_ELEMENT_ACTIVE);
        leaderSkill7.setName("Test7");
        leaderSkill7.addAtkData(4.);
        leaderSkill7.addAtkData(4.5);
        leaderSkill7.addAtkData(1.5);
        leaderSkill7.setComboMin(4);
        leaderSkill7.setComboMax(5);
        leaderSkill7.addAtkElement(0);
        leaderSkill7.addAtkElement(1);
        leaderSkill7.addAtkElement(2);
        leaderSkill7.addAtkElement(3);
        leaderSkill7.addAtkElement(4);
        leaderSkill7.addMatchElements(Element.RED);
        leaderSkill7.addMatchElements(Element.BLUE);
        leaderSkill7.addMatchElements(Element.GREEN);
        leaderSkill7.addMatchElements(Element.LIGHT);
        leaderSkill7.addMatchElements(Element.DARK);
        leaderSkill7.save();

        LeaderSkill leaderSkill8 = new LeaderSkill();
        leaderSkill8.setName("Test8");
        leaderSkill8.setAtkSkillType(LeaderSkillType.HP_FLAT);
        leaderSkill8.setRcvSkillType(LeaderSkillType.FLAT);
        leaderSkill8.addAtkData(3.);
        leaderSkill8.addAtkData(3.15);
        leaderSkill8.addAtkData(3.3);
        leaderSkill8.addAtkElement(1);
        leaderSkill8.addRcvData(1.35);
        leaderSkill8.addRcvElement(0);
        leaderSkill8.getHpPercent().add(50);
        leaderSkill8.getHpPercent().add(0);
        leaderSkill8.getHpPercent().add(100);
        leaderSkill8.getHpPercent().add(50);
        leaderSkill8.getHpPercent().add(100);
        leaderSkill8.getHpPercent().add(100);
        leaderSkill8.save();

        LeaderSkill leaderSkill9 = new LeaderSkill();
        leaderSkill9.setName("Test9");
        leaderSkill9.setHpSkillType(LeaderSkillType.FLAT);
        leaderSkill9.setAtkSkillType(LeaderSkillType.FLAT);
        leaderSkill9.addHpData(2.5);
        leaderSkill9.addAtkData(1.5);
        leaderSkill9.addHpType(2);
        leaderSkill9.addAtkType(2);
        leaderSkill9.save();

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
        menu.findItem(R.id.search).setVisible(false);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
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
//            mContent.reverseArrayList();
        } else if (id == R.id.loadTeam) {
            switchFragment(TeamListFragment.newInstance(), TeamListFragment.TAG);
        } else if (id == R.id.monsterList) {
            switchFragment(MonsterTabLayoutFragment.newInstance(false, 1), MonsterTabLayoutFragment.TAG);
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

    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
        @Override
        public void overwriteTeam() {
            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
            Team overwriteTeam = new Team(Team.getTeamById(0));
//            if(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()) != null){
//                overwriteTeam.setTeamName(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()).getTeamName());
                overwriteTeam.setTeamId(Team.getTeamById(0).getTeamIdOverwrite());
                Log.d("Main Activity Log", "Overwrite Team name is: " + overwriteTeam.getTeamName() + " Team id: " + overwriteTeam.getTeamId() + " Team ID overwrite: " + overwriteTeam.getTeamIdOverwrite());
                overwriteTeam.save();
//            } else {
//                overwriteTeam.setTeamId(overwriteTeam.getTeamIdOverwrite());
//                overwriteTeam.save();
//            }

        }

        @Override
        public void saveNewTeam(String teamName) {
            long teamId;
            if (Team.getAllTeams().size() == 0) {
                teamId = 1;
            } else {
                teamId = Team.getAllTeams().get(Team.getAllTeams().size() - 1).getTeamId() + 1;
            }
            Team newTeam = new Team(Team.getTeamById(0));
            newTeam.setTeamName(teamName);
            newTeam.setTeamId(teamId);
            for (Monster monster : newTeam.getMonsters()) {
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
