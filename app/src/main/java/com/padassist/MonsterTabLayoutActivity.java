package com.padassist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

//import com.activeandroid.ActiveAndroid;
//import com.activeandroid.Configuration;
import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;
import com.padassist.Fragments.BaseMonsterListFragment;
import com.padassist.Fragments.SaveMonsterListFragment;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class MonsterTabLayoutActivity extends AppCompatActivity {

    public static final String TAG = MonsterTabLayoutActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private MonsterPagerAdapter monsterPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras() != null){
            replaceAll = getIntent().getExtras().getBoolean("replaceAll");
            replaceMonsterId = getIntent().getExtras().getLong("replaceMonsterId");
            monsterPosition = getIntent().getExtras().getInt("monsterPosition");
        }
//        if (android.os.Build.VERSION.SDK_INT >= 20) {
//            Configuration.Builder configBuilder = new Configuration.Builder(this);
//            configBuilder.addModelClasses(Monster.class);
//            configBuilder.addModelClasses(Team.class);
//            configBuilder.addModelClass(BaseMonster.class);
//            configBuilder.addModelClass(LeaderSkill.class);
//            configBuilder.addModelClass(OrbMatch.class);
//            ActiveAndroid.initialize(configBuilder.create());
//        } else {
//            ActiveAndroid.initialize(this);
//        }

        setContentView(R.layout.fragment_monster_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        monsterPagerAdapter = new MonsterPagerAdapter(getSupportFragmentManager(), replaceAll, replaceMonsterId, monsterPosition);

        viewPager.setAdapter(monsterPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        if(Monster.getAllHelperMonsters().size() < 1 && monsterPosition == 5 && replaceMonsterId == 0){
//            tabLayout.getTabAt(1).select();
//        }else if(Monster.getAllSavedMonsters().size() <= 1){
//            tabLayout.getTabAt(1).select();
//        }

        switch (monsterPosition) {
            case 0:
                setTitle("Replace Leader");
                break;
            case 1:
                setTitle("Replace Sub 1");
                break;
            case 2:
                setTitle("Replace Sub 2");
                break;
            case 3:
                setTitle("Replace Sub 3");
                break;
            case 4:
                setTitle("Replace Sub 4");
                break;
            case 5:
                setTitle("Replace Helper");
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        return true;
    }





//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_monster_tab_layout, container, false);
//
//        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);
//        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
//        // Inflate the layout for this fragment
//        return rootView;
//    }



}
