package com.padassist.Util;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Fragments.DeleteMonsterConfirmationDialogFragment;
import com.padassist.Fragments.LatentAwakeningDialogFragment;
import com.padassist.Fragments.MonsterRemoveDialogFragment;
import com.padassist.Fragments.MonsterTabLayoutFragment;
import com.padassist.Fragments.ReplaceAllConfirmationDialogFragment;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;

import io.realm.Realm;
import io.realm.RealmResults;

public abstract class MonsterPageBase extends Fragment {
    public static final String TAG = MonsterPageBase.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private View rootView;
    private OnFragmentInteractionListener mListener;
    protected TextView monsterName, monsterStatsHPBase, monsterStatsHPTotal,
            monsterStatsATKBase, monsterStatsATKTotal, monsterStatsRCVBase, monsterStatsRCVTotal,
            monsterStatsWeightedValue, monsterStatsTotalWeightedValue, rarity, monsterAwakenings,
            skill1Level, activeSkill1Name, activeSkill1Cooldown, activeSkill1Desc, skill2Level, activeSkill2Name,
            activeSkill2Cooldown, activeSkill2Desc, leaderSkillName, leaderSkillDesc;
    protected EditText monsterLevelValue, monsterStatsHPPlus, monsterStatsATKPlus, monsterStatsRCVPlus, monsterAwakeningsValue;
    protected Button monsterLevelMax, monsterStatsMax, monsterStatsHPMax, monsterStatsATKMax, monsterStatsRCVMax,
            monsterAwakeningsMax, monsterRemove, monsterStatsMaxAll, awakeningPlus, awakeningMinus, awakeningHolderMax,
            skill1Plus, skill1Minus, skill1Max, skill2Plus, skill2Minus, skill2Max;
    protected ImageView monsterPicture, rarityStar, type1, type2, type3, favorite, favoriteOutline, activeSkill1, activeSkill2, leaderSkill;
    private LinearLayout awakeningHolder, latentHolder;
    private RelativeLayout skill1Holder, skill2Holder, leaderSkillHolder;
    protected TableLayout table1;
    protected Monster monster;
    protected long monsterId;
    protected int position;
    private Toast toast;
    private MonsterRemoveDialogFragment monsterRemoveDialogFragment;
    private ReplaceAllConfirmationDialogFragment replaceConfirmationDialog;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;
    private LatentAwakeningDialogFragment latentAwakeningDialogFragment;
    private int level, hp, atk, rcv, awakening;
    protected Realm realm;

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.CURRENT_LEVEL) {
                if (statValue == 0) {
                    statValue = 1;
                }
                monster.setCurrentLevel(statValue);
                monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
                monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
                monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
                monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
                monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
                monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
                monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
                monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
                monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
                monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.ATK_STAT) {
                monster.setAtkPlus(statValue);
                monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.RCV_STAT) {
                monster.setRcvPlus(statValue);
                monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.HP_STAT) {
                monster.setHpPlus(statValue);
                monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.AWAKENINGS) {
                monster.setCurrentAwakenings(statValue);
                grayAwakenings();
            }
        }
    };

    private MyTextWatcher currentLevelWatcher = new MyTextWatcher(MyTextWatcher.CURRENT_LEVEL, changeStats);
    private MyTextWatcher hpPlusWatcher = new MyTextWatcher(MyTextWatcher.HP_STAT, changeStats);
    private MyTextWatcher atkPlusWatcher = new MyTextWatcher(MyTextWatcher.ATK_STAT, changeStats);
    private MyTextWatcher rcvPlusWatcher = new MyTextWatcher(MyTextWatcher.RCV_STAT, changeStats);
    private MyTextWatcher awakeningsWatcher = new MyTextWatcher(MyTextWatcher.AWAKENINGS, changeStats);

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
//                hideKeyboard(v);
                if (monsterAwakeningsValue.getText().toString().equals("")) {
                    monsterAwakeningsValue.setText("0");
                    monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
                    grayAwakenings();
                } else if (monsterLevelValue.getText().toString().equals("")) {
                    monsterLevelValue.setText("1");
                } else if (monsterStatsHPPlus.getText().toString().equals("")) {
                    monsterStatsHPPlus.setText("0");

                } else if (monsterStatsATKPlus.getText().toString().equals("")) {
                    monsterStatsATKPlus.setText("0");
                } else if (monsterStatsRCVPlus.getText().toString().equals("")) {
                    monsterStatsRCVPlus.setText("0");
                }
                setMonsterStats();
            }
        }
    };

    public MonsterPageBase() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monster_page, container, false);
        initTextView(rootView);
        monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
        type1 = (ImageView) rootView.findViewById(R.id.type1);
        type2 = (ImageView) rootView.findViewById(R.id.type2);
        type3 = (ImageView) rootView.findViewById(R.id.type3);
        favorite = (ImageView) rootView.findViewById(R.id.favorite);
        favoriteOutline = (ImageView) rootView.findViewById(R.id.favoriteOutline);
        monsterLevelMax = (Button) rootView.findViewById(R.id.monsterLevelMax);
        monsterStatsMax = (Button) rootView.findViewById(R.id.monsterStatsMax);
        monsterStatsHPMax = (Button) rootView.findViewById(R.id.monsterStatsHPMax);
        monsterStatsATKMax = (Button) rootView.findViewById(R.id.monsterStatsATKMax);
        monsterStatsRCVMax = (Button) rootView.findViewById(R.id.monsterStatsRCVMax);
        monsterAwakeningsMax = (Button) rootView.findViewById(R.id.monsterAwakeningsMax);
        monsterStatsMaxAll = (Button) rootView.findViewById(R.id.monsterStatsMaxAll);
        awakeningMinus = (Button) rootView.findViewById(R.id.awakeningMinus);
        awakeningPlus = (Button) rootView.findViewById(R.id.awakeningPlus);
        monsterRemove = (Button) rootView.findViewById(R.id.monsterRemove);
        monsterLevelValue = (EditText) rootView.findViewById(R.id.monsterLevelValue);
        monsterStatsHPPlus = (EditText) rootView.findViewById(R.id.monsterStatsHPPlus);
        monsterStatsATKPlus = (EditText) rootView.findViewById(R.id.monsterStatsATKPlus);
        monsterStatsRCVPlus = (EditText) rootView.findViewById(R.id.monsterStatsRCVPlus);
        monsterAwakeningsValue = (EditText) rootView.findViewById(R.id.monsterAwakeningsValue);
        awakeningHolder = (LinearLayout) rootView.findViewById(R.id.awakeningHolder);
        monsterAwakenings = (TextView) rootView.findViewById(R.id.monsterAwakenings);
        table1 = (TableLayout) rootView.findViewById(R.id.table1);
        latentHolder = (LinearLayout) rootView.findViewById(R.id.latentHolder);
        awakeningHolderMax = (Button) rootView.findViewById(R.id.awakeningHolderMax);
        skill1Level = (TextView) rootView.findViewById(R.id.skill1Level);
        activeSkill1Name = (TextView) rootView.findViewById(R.id.activeSkill1Name);
        activeSkill1Cooldown = (TextView) rootView.findViewById(R.id.activeSkill1Cooldown);
        activeSkill1Desc = (TextView) rootView.findViewById(R.id.activeSkill1Desc);
        skill2Level = (TextView) rootView.findViewById(R.id.skill2Level);
        activeSkill2Name = (TextView) rootView.findViewById(R.id.activeSkill2Name);
        activeSkill2Cooldown = (TextView) rootView.findViewById(R.id.activeSkill2Cooldown);
        activeSkill2Desc = (TextView) rootView.findViewById(R.id.activeSkill2Desc);
        skill1Plus = (Button) rootView.findViewById(R.id.skill1Plus);
        skill1Minus = (Button) rootView.findViewById(R.id.skill1Minus);
        skill1Max = (Button) rootView.findViewById(R.id.skill1Max);
        skill2Plus = (Button) rootView.findViewById(R.id.skill2Plus);
        skill2Minus = (Button) rootView.findViewById(R.id.skill2Minus);
        skill2Max = (Button) rootView.findViewById(R.id.skill2Max);
        activeSkill1 = (ImageView) rootView.findViewById(R.id.activeSkill1);
        activeSkill2 = (ImageView) rootView.findViewById(R.id.activeSkill2);
        leaderSkill = (ImageView) rootView.findViewById(R.id.leaderSkill);
        skill1Holder = (RelativeLayout) rootView.findViewById(R.id.skill1Holder);
        skill2Holder = (RelativeLayout) rootView.findViewById(R.id.skill2Holder);
        leaderSkillHolder = (RelativeLayout) rootView.findViewById(R.id.leaderSkillHolder);
        leaderSkillName = (TextView) rootView.findViewById(R.id.leaderSkillName);
        leaderSkillDesc = (TextView) rootView.findViewById(R.id.leaderSkillDesc);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("Monster Page Log", "Monster level1: " + monster.getCurrentLevel());
//        monster = Team.getTeamById(0).getMonsters(Team.getTeamById(0).getMonsterOverwrite());
//        Log.d("Monster Page Log", "Monster is: " + monster);
//        Log.d("Monster Page Log", "Monster level2: " + monster.getCurrentLevel());

        Monster checkMonster = realm.where(Monster.class).equalTo("monsterId", monster.getMonsterId()).findFirst();

        if (deleteCheck()) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            if (monster.isFavorite() != checkMonster.isFavorite()) {
                monster.setFavorite(checkMonster.isFavorite());
                setFavorite();
            }
        }

//        if(monster.isValid()){
//            monster = realm.copyFromRealm(monster);
//        }

//        loadBackup();
//        showAwakenings();
//        grayAwakenings();
//        initializeEditTexts();
//        setImageViews();
//        setLatents();
//        updateMonster();
    }

    private boolean deleteCheck() {
        RealmResults<Monster> results = realm.where(Monster.class).findAll();
//        ArrayList<Monster> checkList = new ArrayList<>();
//        checkList.addAll(results);
        Boolean bounce = true;
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getMonsterId() == monster.getMonsterId()) {
                bounce = false;
                break;
            }
        }
        return bounce;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.searchGroup, true);
        menu.findItem(R.id.manageMonsters).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggleCoop:
                if (monster.setAwakenings().contains(30)) {
                    updateMonster();
                }
                break;
            case R.id.monsterList:
                ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(false, monster.getMonsterId(), position), MonsterTabLayoutFragment.TAG, "good");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        realm = Realm.getDefaultInstance();
        if (monster == null) {
            monster = getMonster();
        }
        disableStuff();
        initBackup();
        monsterPicture.setImageResource(monster.getMonsterPicture());
        monsterName.setText(monster.getName());
        setTextViews();
        setImageViews();
        setLatents();
        setFavorite();
        setSkillTextViews();
        monsterLevelValue.addTextChangedListener(currentLevelWatcher);
        monsterStatsHPPlus.addTextChangedListener(hpPlusWatcher);
        monsterStatsATKPlus.addTextChangedListener(atkPlusWatcher);
        monsterStatsRCVPlus.addTextChangedListener(rcvPlusWatcher);
        monsterAwakeningsValue.addTextChangedListener(awakeningsWatcher);

        monsterLevelValue.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsHPPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsATKPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsRCVPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterAwakeningsValue.setOnFocusChangeListener(editTextOnFocusChange);

//        monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
        showAwakenings();
        grayAwakenings();
        awakeningMinus.setOnClickListener(awakeningButtons);
        awakeningPlus.setOnClickListener(awakeningButtons);

        monsterLevelMax.setOnClickListener(maxButtons);
        monsterStatsMax.setOnClickListener(maxButtons);
        monsterStatsHPMax.setOnClickListener(maxButtons);
        monsterStatsATKMax.setOnClickListener(maxButtons);
        monsterStatsRCVMax.setOnClickListener(maxButtons);
        monsterAwakeningsMax.setOnClickListener(maxButtons);
        monsterStatsMaxAll.setOnClickListener(maxButtons);
        skill1Max.setOnClickListener(maxButtons);

        rarity.setText("" + monster.getRarity());
        rarityStar.setColorFilter(0xFFD4D421);

        monsterPicture.setOnClickListener(monsterPictureOnClickListener);
        latentHolder.setOnClickListener(latentOnClickListener);

        monsterName.setHorizontallyScrolling(true);
        monsterName.setSelected(true);

        activeSkill1Name.setHorizontallyScrolling(true);
        activeSkill1Name.setSelected(true);

        activeSkill2Name.setHorizontallyScrolling(true);
        activeSkill2Name.setSelected(true);

        leaderSkillName.setHorizontallyScrolling(true);
        leaderSkillName.setSelected(true);

        skill1Minus.setOnClickListener(activeSkillPlusMinusOnClickListener);
        skill1Plus.setOnClickListener(activeSkillPlusMinusOnClickListener);

        //rootView.getViewTreeObserver().addOnGlobalLayoutListener(rootListener);

        getActivity().setTitle("Modify Monster");
    }

    public abstract Monster getMonster();

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!deleteCheck()) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(monster);
            realm.commitTransaction();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void initTextView(View rootView) {
        monsterName = (TextView) rootView.findViewById(R.id.monsterName);
        monsterStatsHPBase = (TextView) rootView.findViewById(R.id.monsterStatsHPBase);
        monsterStatsHPTotal = (TextView) rootView.findViewById(R.id.monsterStatsHPTotal);
        monsterStatsATKBase = (TextView) rootView.findViewById(R.id.monsterStatsATKBase);
        monsterStatsATKTotal = (TextView) rootView.findViewById(R.id.monsterStatsATKTotal);
        monsterStatsRCVBase = (TextView) rootView.findViewById(R.id.monsterStatsRCVBase);
        monsterStatsRCVTotal = (TextView) rootView.findViewById(R.id.monsterStatsRCVTotal);
        monsterStatsWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsWeightedValue);
        monsterStatsTotalWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsTotalWeightedValue);
        rarity = (TextView) rootView.findViewById(R.id.rarity);
        rarityStar = (ImageView) rootView.findViewById(R.id.rarityStar);

    }

    private View.OnClickListener awakeningButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            monsterAwakeningsValue.clearFocus();
            if (v.equals(awakeningMinus)) {
                if (monster.getCurrentAwakenings() > 0 && monster.getCurrentAwakenings() <= 9) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() - 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() - 1));
                }
                setTextViewValues();
            } else if (v.equals(awakeningPlus)) {
                if (monster.getCurrentAwakenings() >= 0 && monster.getCurrentAwakenings() < 9) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() + 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() + 1));

                }
                setTextViewValues();
            }
            grayAwakenings();
        }
    };

    private View.OnClickListener activeSkillPlusMinusOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(skill1Minus)) {
                if (monster.getActiveSkillLevel() > 1) {
                    monster.setActiveSkillLevel(monster.getActiveSkillLevel() - 1);
                    setActive1Values();
                }
            } else if (v.equals(skill1Plus)) {
                if (monster.getActiveSkillLevel() < monster.getActiveSkill().getMaxLevel()) {
                    monster.setActiveSkillLevel(monster.getActiveSkillLevel() + 1);
                    setActive1Values();
                }
            }
        }
    };


    private View.OnClickListener maxButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearTextFocus();
            if (v.equals(monsterLevelMax)) {
                monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
                updateMonster();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Level maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsMax)) {
                monsterStatsHPPlus.setText("99");
                monsterStatsATKPlus.setText("99");
                monsterStatsRCVPlus.setText("99");
                updateMonster();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Maxed all +", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsHPMax)) {
                monsterStatsHPPlus.setText("99");
                updateMonster();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "HP + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsATKMax)) {
                monsterStatsATKPlus.setText("99");
                updateMonster();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "ATK + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsRCVMax)) {
                monsterStatsRCVPlus.setText("99");
                updateMonster();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "RCV + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterAwakeningsMax)) {
                monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
                monster.setCurrentAwakenings(monster.getMaxAwakenings());
                grayAwakenings();
                setTextViewValues();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Awakenings maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsMaxAll)) {
                monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
                monsterStatsHPPlus.setText("99");
                monsterStatsATKPlus.setText("99");
                monsterStatsRCVPlus.setText("99");
                monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
                monster.setCurrentAwakenings(monster.getMaxAwakenings());
                monster.setActiveSkillLevel(monster.getActiveSkill().getMaxLevel());
                monster.setActiveSkill2Level(monster.getActiveSkill().getMaxLevel());
                grayAwakenings();
                updateMonster();
                setSkillTextViews();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "All stats maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(skill1Max)) {
                monster.setActiveSkillLevel(monster.getActiveSkill().getMaxLevel());
                setActive1Values();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Skill maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(skill2Max)) {
                monster.setActiveSkill2Level(monster.getActiveSkill2().getMaxLevel());
                setActive2Values();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Skill 2 maxed", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    private View.OnClickListener monsterPictureOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monster.isFavorite()) {
                monster.setFavorite(false);
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster unfavorited", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                monster.setFavorite(true);
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster favorited", Toast.LENGTH_SHORT);
                toast.show();
            }
            setFavorite();
        }
    };

    public void showAwakenings() {
        // Show max # of awakenings
        for (int i = 0; i < 9; i++) {
            if (i >= monster.getMaxAwakenings()) {
                awakeningHolder.getChildAt(i).setVisibility(View.GONE);
            } else {
                awakeningHolder.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }

    //Gray out depending on monsterAwakeningsValue
    //@TargetApi(11)
    public void grayAwakenings() {
        if (monster.getCurrentAwakenings() < monster.getMaxAwakenings()) {
            for (int j = 0; j < monster.getCurrentAwakenings(); j++) {
                awakeningHolder.getChildAt(j).setBackgroundResource(ImageResourceUtil.monsterAwakening(monster.getAwokenSkills().get(j).getValue()));
            }
            for (int j = monster.getCurrentAwakenings(); j < monster.getMaxAwakenings(); j++) {
                awakeningHolder.getChildAt(j).setBackgroundResource(ImageResourceUtil.monsterAwakeningDisabled(monster.getAwokenSkills().get(j).getValue()));
            }
        } else {
            for (int j = 0; j < monster.getMaxAwakenings(); j++) {
                awakeningHolder.getChildAt(j).setBackgroundResource(ImageResourceUtil.monsterAwakening(monster.getAwokenSkills().get(j).getValue()));
            }
        }
    }

    protected void setImageViews() {
        type1.setImageResource(ImageResourceUtil.monsterType(monster.getType1()));
        if (monster.getType2() >= 0) {
            type2.setImageResource(ImageResourceUtil.monsterType(monster.getType2()));
            type2.setVisibility(View.VISIBLE);
        } else {
            type2.setVisibility(View.INVISIBLE);
        }
        if (monster.getType3() >= 0) {
            type3.setImageResource(ImageResourceUtil.monsterType(monster.getType3()));
            type3.setVisibility(View.VISIBLE);
        } else {
            type3.setVisibility(View.INVISIBLE);
        }
        favorite.setColorFilter(0xFFFFAADD);
        setFavorite();
    }

    public void setFavorite() {
        if (monster.isFavorite()) {
            favorite.setVisibility(View.VISIBLE);
            favoriteOutline.setVisibility(View.VISIBLE);
        } else {
            favorite.setVisibility(View.INVISIBLE);
            favoriteOutline.setVisibility(View.INVISIBLE);
        }
    }

    public void updateMonster() {
        //Update method because our TextWatcher no work
        setMonsterStats();
        setTextViewValues();
    }

    public void setSkillTextViews() {
        if (monster.getActiveSkillString().equals("Blank")) {
            activeSkill1Cooldown.setVisibility(View.GONE);
            activeSkill1Desc.setVisibility(View.GONE);
            skill1Level.setVisibility(View.GONE);
            skill1Plus.setVisibility(View.GONE);
            skill1Minus.setVisibility(View.GONE);
            skill1Max.setVisibility(View.GONE);
            activeSkill1Name.setText("None");
        } else {
            activeSkill1Desc.setText(monster.getActiveSkill().getDescription());
            setActive1Values();
            activeSkill1Name.setText(monster.getActiveSkillString());
        }
        if (monster.getLeaderSkillString().equals("Blank")) {
            leaderSkillDesc.setVisibility(View.GONE);
            leaderSkillName.setText("None");
        } else {
            leaderSkillDesc.setText(monster.getLeaderSkill().getDescription());
            leaderSkillName.setText(monster.getLeaderSkillString());
        }
        if (skill2Holder.getVisibility() == View.VISIBLE) {
            activeSkill2Name.setText(monster.getActiveSkill2String());
            activeSkill2Desc.setText(monster.getActiveSkill2().getDescription());
            setActive2Values();
        }
    }

    protected void setActive1Values() {
        if (monster.getActiveSkillLevel() == monster.getActiveSkill().getMaxLevel()) {
            skill1Level.setText("Skill Level: Max");
            activeSkill1Cooldown.setText("(CD " + monster.getActiveSkill().getMinimumCooldown() + " (Max))");
        } else {
            skill1Level.setText("Skill Level: " + monster.getActiveSkillLevel());
            activeSkill1Cooldown.setText("(CD " + (monster.getActiveSkill().getMaximumCooldown() - monster.getActiveSkillLevel() + 1) + ")");
        }
    }

    protected void setActive2Values() {
        if (monster.getActiveSkill2Level() == monster.getActiveSkill2().getMaxLevel()) {
            skill2Level.setText("Skill 2 Level: Max");
            activeSkill2Cooldown.setText("(CD " + monster.getActiveSkill2().getMinimumCooldown() + " (Max))");
        } else {
            skill2Level.setText("Skill 2 Level: " + monster.getActiveSkill2Level());
            activeSkill2Cooldown.setText("(CD " + (monster.getActiveSkill2().getMaximumCooldown() - monster.getActiveSkill2Level() + 1) + ")");
        }
    }

    protected void setTextViews() {
        setTextViewValues();
        initializeEditTexts();
    }

    protected void setMonsterStats() {
        monster.setCurrentLevel(Integer.parseInt(monsterLevelValue.getText().toString()));
        monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
        monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
        monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
        monster.setHpPlus(Integer.valueOf(monsterStatsHPPlus.getText().toString()));
        monster.setAtkPlus(Integer.valueOf(monsterStatsATKPlus.getText().toString()));
        monster.setRcvPlus(Integer.valueOf(monsterStatsRCVPlus.getText().toString()));
    }

    protected void setTextViewValues() {
        monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
        monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
        monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
        monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
        monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
        monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
        monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
        monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
    }

    protected void initializeEditTexts() {
        monsterLevelValue.setText(String.valueOf(monster.getCurrentLevel()));
        monsterStatsHPPlus.setText(String.valueOf(monster.getHpPlus()));
        monsterStatsATKPlus.setText(String.valueOf(monster.getAtkPlus()));
        monsterStatsRCVPlus.setText(String.valueOf(monster.getRcvPlus()));
        monsterAwakeningsValue.setText(String.valueOf(monster.getCurrentAwakenings()));
    }

    protected void initBackup() {
        level = monster.getCurrentLevel();
        hp = monster.getHpPlus();
        atk = monster.getAtkPlus();
        rcv = monster.getRcvPlus();
        awakening = monster.getCurrentAwakenings();
    }

    protected void loadBackup() {
        if (monster.getMonsterId() == 0) {
            monster.setCurrentLevel(1);
            monster.setHpPlus(0);
            monster.setAtkPlus(0);
            monster.setRcvPlus(0);
            monster.setCurrentAwakenings(0);
        } else {
            monster.setCurrentLevel(level);
            monster.setHpPlus(hp);
            monster.setAtkPlus(atk);
            monster.setRcvPlus(rcv);
            monster.setCurrentAwakenings(awakening);
        }
    }

    protected void disableStuff() {
        if (monster.getMonsterId() == 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        if (monster.getType1() == 0 || monster.getType1() == 14) {
            monsterStatsHPPlus.setEnabled(false);
            monsterStatsATKPlus.setEnabled(false);
            monsterStatsRCVPlus.setEnabled(false);
            monsterAwakeningsValue.setEnabled(false);
            monsterStatsMax.setEnabled(false);
            monsterStatsHPMax.setEnabled(false);
            monsterStatsATKMax.setEnabled(false);
            monsterStatsRCVMax.setEnabled(false);
            monsterAwakeningsMax.setEnabled(false);
            awakeningPlus.setEnabled(false);
            awakeningMinus.setEnabled(false);
            monsterStatsMaxAll.setEnabled(false);
            latentHolder.setVisibility(View.GONE);
        } else {
            monsterStatsHPPlus.setEnabled(true);
            monsterStatsATKPlus.setEnabled(true);
            monsterStatsRCVPlus.setEnabled(true);
            monsterAwakeningsValue.setEnabled(true);
            monsterStatsMax.setEnabled(true);
            monsterStatsHPMax.setEnabled(true);
            monsterStatsATKMax.setEnabled(true);
            monsterStatsRCVMax.setEnabled(true);
            monsterAwakeningsMax.setEnabled(true);
            awakeningPlus.setEnabled(true);
            awakeningMinus.setEnabled(true);
            monsterStatsMaxAll.setEnabled(true);
            latentHolder.setVisibility(View.VISIBLE);
        }
        if (monster.getMaxAwakenings() == 0) {
            monsterAwakenings.setVisibility(View.GONE);
            awakeningHolder.setVisibility(View.GONE);
            awakeningPlus.setVisibility(View.GONE);
            awakeningMinus.setVisibility(View.GONE);
            monsterAwakeningsMax.setVisibility(View.GONE);
            monsterAwakeningsValue.setVisibility(View.GONE);
            RelativeLayout.LayoutParams z = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            z.addRule(RelativeLayout.BELOW, table1.getId());
            z.addRule(RelativeLayout.ALIGN_TOP, awakeningHolderMax.getId());
            z.addRule(RelativeLayout.ALIGN_BOTTOM, awakeningHolderMax.getId());
            z.addRule(RelativeLayout.CENTER_HORIZONTAL);
            z.addRule(RelativeLayout.CENTER_VERTICAL);
            latentHolder.setLayoutParams(z);
        } else {
            monsterAwakenings.setVisibility(View.VISIBLE);
            awakeningHolder.setVisibility(View.VISIBLE);
            awakeningPlus.setVisibility(View.VISIBLE);
            awakeningMinus.setVisibility(View.VISIBLE);
            monsterAwakeningsMax.setVisibility(View.VISIBLE);
            monsterAwakeningsValue.setVisibility(View.VISIBLE);
        }
//        if(monster.getMonsterId() == 0){
//            monsterLevelMax.setEnabled(false);
//            monsterStatsMax.setEnabled(false);
//            monsterStatsHPMax.setEnabled(false);
//            monsterStatsATKMax.setEnabled(false);
//            monsterStatsRCVMax.setEnabled(false);
//            monsterAwakeningsMax.setEnabled(false);
//            monsterStatsMaxAll.setEnabled(false);
//            monsterRemove.setEnabled(false);
//            monsterLevelValue.setEnabled(false);
//            monsterStatsHPPlus.setEnabled(false);
//            monsterStatsATKPlus.setEnabled(false);
//            monsterStatsRCVPlus.setEnabled(false);
//            monsterAwakeningsValue.setEnabled(false);
//        }
    }

    protected void awakeningsCheck() {
        if (monster.getMaxAwakenings() > 0 && awakeningHolder.getVisibility() == View.GONE) {
            monsterAwakenings.setVisibility(View.VISIBLE);
            awakeningHolder.setVisibility(View.VISIBLE);
            awakeningPlus.setVisibility(View.VISIBLE);
            awakeningMinus.setVisibility(View.VISIBLE);
            monsterAwakeningsMax.setVisibility(View.VISIBLE);
            monsterAwakeningsValue.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams z = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            z.addRule(RelativeLayout.BELOW, awakeningHolder.getId());
            z.addRule(RelativeLayout.CENTER_HORIZONTAL);
            z.addRule(RelativeLayout.CENTER_VERTICAL);
            latentHolder.setLayoutParams(z);
        }
    }

    protected void clearTextFocus() {
        monsterLevelValue.clearFocus();
        monsterStatsHPPlus.clearFocus();
        monsterStatsATKPlus.clearFocus();
        monsterStatsRCVPlus.clearFocus();
        monsterAwakeningsValue.clearFocus();
    }

    private View.OnClickListener latentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (latentAwakeningDialogFragment == null) {
                latentAwakeningDialogFragment = LatentAwakeningDialogFragment.newInstance(setLatents, monster);
            }
            if (!latentAwakeningDialogFragment.isAdded()) {
                latentAwakeningDialogFragment.show(getChildFragmentManager(), "LAATENTSNTSNTS", monster);
            }
        }
    };

    private LatentAwakeningDialogFragment.ResetLatents setLatents = new LatentAwakeningDialogFragment.ResetLatents() {
        @Override
        public void refreshLatents() {
            setLatents();
            setTextViewValues();
        }
    };

    private void setLatents() {
        for (int i = 0; i < monster.getLatents().size(); i++) {
            latentHolder.getChildAt(i).setBackgroundResource(ImageResourceUtil.monsterLatent(monster.getLatents().get(i).getValue()));
        }
    }

    protected void evolveMonsterBase(long baseMonsterId) {
        if (baseMonsterId != 0) {
            String previousActiveSkill = monster.getActiveSkillString();
            monster.setBaseMonster(realm.where(BaseMonster.class).equalTo("monsterId", baseMonsterId).findFirst());
            if (!previousActiveSkill.equals(monster.getActiveSkillString())) {
                monster.setActiveSkillLevel(1);
            }
            monster.setIndices();
            setSkillTextViews();
            showAwakenings();
            grayAwakenings();
            updateMonster();
            setImageViews();
            rarity.setText("" + monster.getRarity());
            monsterName.setText(monster.getName());
            monsterPicture.setImageResource(monster.getMonsterPicture());
            awakeningsCheck();
        }
    }

//    public void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//    }

//   private ViewTreeObserver.OnGlobalLayoutListener rootListener = new ViewTreeObserver.OnGlobalLayoutListener()
//   {
//      @Override
//      public void onGlobalLayout()
//      {
//
//         if (getResources().getConfiguration().keyboardHidden == Configuration.KEYBOARDHIDDEN_YES)
//         {
//            ((ViewGroup)rootView).getFocusedChild().clearFocus();
//            Log.d("is this real?", "what da");
//
//         }
//      }
//   };


}
