package com.example.anthony.damagecalculator.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
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

import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterPageFragment extends AbstractFragment {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private TextView monsterName, monsterStatsHPBase, monsterStatsHPTotal, monsterStatsATKBase, monsterStatsATKTotal, monsterStatsRCVBase, monsterStatsRCVTotal, monsterStatsWeightedValue, monsterStatsTotalWeightedValue, rarity, monsterAwakenings;
    private EditText monsterLevelValue, monsterStatsHPPlus, monsterStatsATKPlus, monsterStatsRCVPlus, monsterAwakeningsValue;
    private Button monsterLevelMax, monsterStatsMax, monsterStatsHPMax, monsterStatsATKMax, monsterStatsRCVMax, monsterAwakeningsMax, monsterRemove, monsterStatsMaxAll, awakeningPlus, awakeningMinus, awakeningHolderMax;
    private ImageView monsterPicture, rarityStar, type1, type2, type3, favorite, favoriteOutline;
    private LinearLayout awakeningHolder, latentHolder;
    private TableLayout table1;
    private Monster monster;
    private Toast toast;
    private MonsterRemoveDialogFragment monsterRemoveDialogFragment;
    private ReplaceAllConfirmationDialogFragment replaceConfirmationDialog;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;
    private LatentAwakeningDialogFragment latentAwakeningDialogFragment;
    private int level, hp, atk, rcv, awakening;

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {

            Log.d("Monster Page Log", "Monster level8: " + monster.getCurrentLevel());
            Log.d("Monster Page Log", "Edit Text Level5: " + monsterLevelValue.getText());
            Log.d("Monster Page Log", "Stat Value: " + statValue);
            if (statToChange == MyTextWatcher.CURRENT_LEVEL) {
                if (statValue == 0) {
                    statValue = 1;
                }
                Log.d("Monster Page Log", "Monster level9: " + monster.getCurrentLevel());
                Log.d("Monster Page Log", "Edit Text Level5: " + monsterLevelValue.getText());
                Log.d("Monster Page Log", "Stat Value2: " + statValue);
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
//                monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
//                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.ATK_STAT) {
                monster.setAtkPlus(statValue);
                monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
//                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.RCV_STAT) {
                monster.setRcvPlus(statValue);
                monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
//                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.HP_STAT) {
                monster.setHpPlus(statValue);
                monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
//                monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
            } else if (statToChange == MyTextWatcher.AWAKENINGS) {
                monster.setCurrentAwakenings(statValue);
                if (monster.getCurrentAwakenings() > monster.getMaxAwakenings()) {
                    monster.setCurrentAwakenings(monster.getMaxAwakenings());
                    monsterAwakeningsValue.setText(Integer.toString(monster.getCurrentAwakenings()));
                }
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
                hideKeyboard(v);
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
                monsterStats();
            }
        }
    };

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonsterPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterPageFragment newInstance() {
        MonsterPageFragment fragment = new MonsterPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterPageFragment() {
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

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("Monster Page Log", "Monster level1: " + monster.getCurrentLevel());
//        monster = Team.getTeamById(0).getMonsters(Team.getTeamById(0).getMonsterOverwrite());
//        Log.d("Monster Page Log", "Monster is: " + monster);
//        Log.d("Monster Page Log", "Monster level2: " + monster.getCurrentLevel());
        loadBackup();
        showAwakenings();
        grayAwakenings();
        initializeEditTexts();
        setImageViews();
        setLatents();
        monsterStats();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.searchGroup, true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
        }
        monster = Team.getTeamById(0).getMonsters(Singleton.getInstance().getMonsterOverwrite());
        disableStuff();
        initBackup();
        Log.d("Monster Page Log", "Monster awakenings " + monster.getAwokenSkills());
        Log.d("Monster Page Log", "Monster level3: " + monster.getCurrentLevel());
        Log.d("Monster Page Log", "Edit Text Level: " + monsterLevelValue.getText());
        monsterPicture.setImageResource(monster.getMonsterPicture());
        monsterName.setText(monster.getName());
        initializeEditTexts();
        monsterStats();
        Log.d("Monster Page Log", "Monster level4: " + monster.getCurrentLevel());
        Log.d("Monster Page Log", "Edit Text Level2: " + monsterLevelValue.getText());
        monsterLevelValue.addTextChangedListener(currentLevelWatcher);
        monsterStatsHPPlus.addTextChangedListener(hpPlusWatcher);
        monsterStatsATKPlus.addTextChangedListener(atkPlusWatcher);
        monsterStatsRCVPlus.addTextChangedListener(rcvPlusWatcher);
        monsterAwakeningsValue.addTextChangedListener(awakeningsWatcher);

        Log.d("Monster Page Log", "Monster level5: " + monster.getCurrentLevel());
        Log.d("Monster Page Log", "Edit Text Level3: " + monsterLevelValue.getText());
        monsterLevelValue.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsHPPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsATKPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterStatsRCVPlus.setOnFocusChangeListener(editTextOnFocusChange);
        monsterAwakeningsValue.setOnFocusChangeListener(editTextOnFocusChange);

        monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
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
        monsterRemove.setOnClickListener(maxButtons);

        Log.d("Monster Page Log", "Monster level6: " + monster.getCurrentLevel());
        Log.d("Monster Page Log", "Edit Text Level4: " + monsterLevelValue.getText());

        rarity.setText("" + monster.getRarity());
        rarityStar.setColorFilter(0xFFD4D421);

        monsterPicture.setOnClickListener(monsterPictureOnClickListener);
        latentHolder.setOnClickListener(latentOnClickListener);

        monsterName.setSelected(true);

        Log.d("MonsterPageLog", "Monster type 1 is: " + monster.getType1());
        //rootView.getViewTreeObserver().addOnGlobalLayoutListener(rootListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("Monster Page Log", "Monster level7: " + monster.getCurrentLevel());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        monster.save();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        Log.d("What is awakening", "awakening detach: " + monster.getCurrentAwakenings() + " " + monster);
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
 //       monsterStatsWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsWeightedValue);
 //       monsterStatsTotalWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsTotalWeightedValue);
        rarity = (TextView) rootView.findViewById(R.id.rarity);
        rarityStar = (ImageView) rootView.findViewById(R.id.rarityStar);

    }

    private View.OnClickListener awakeningButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            monsterAwakeningsValue.clearFocus();
            if (v.equals(awakeningMinus)) {
                if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) > 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) <= monster.getMaxAwakenings()) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() - 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() - 1));
                }
                setTextViewValues();
                Log.d("Monster Page Log", "Monster Attack: " + monster.getTotalAtk());
            } else if (v.equals(awakeningPlus)) {
                if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) >= 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) < monster.getMaxAwakenings()) {
                    //monster.setCurrentAwakenings(monster.getCurrentAwakenings() + 1);
                    monsterAwakeningsValue.setText("" + (monster.getCurrentAwakenings() + 1));

                }
                setTextViewValues();
                Log.d("Monster Page Log", "Monster Attack: " + monster.getTotalAtk());
            }
            grayAwakenings();

        }
    };

    private View.OnClickListener maxButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearTextFocus();
            if (v.equals(monsterLevelMax)) {
                monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Level maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsMax)) {
                monsterStatsHPPlus.setText("99");
                monsterStatsATKPlus.setText("99");
                monsterStatsRCVPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Maxed all +", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsHPMax)) {
                monsterStatsHPPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "HP + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsATKMax)) {
                monsterStatsATKPlus.setText("99");
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "ATK + maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterStatsRCVMax)) {
                monsterStatsRCVPlus.setText("99");
                monsterStats();
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
                grayAwakenings();
                monsterStats();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "All stats maxed", Toast.LENGTH_SHORT);
                toast.show();
            } else if (v.equals(monsterRemove)) {
                Log.d("Monster Page Log", "Monster favorite before Dialog is: " + monster.isFavorite());
                if (monsterRemoveDialogFragment == null) {
                    monsterRemoveDialogFragment = MonsterRemoveDialogFragment.newInstance(removeMonster, monster);
                }
                monsterRemoveDialogFragment.show(getChildFragmentManager(), "Show Remove Monster", monster);
            }
        }
    };

    private View.OnClickListener monsterPictureOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monster.isFavorite()) {
                monster.setFavorite(false);
                setFavorite();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster unfavorited", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                monster.setFavorite(true);
                setFavorite();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster favorited", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    public void showAwakenings() {
        // Show max # of awakenings
        Log.d("Monster Page Fragment", "Monster Awakenings in Show Awakenings is: " + monster.getAwokenSkills() + "Monster Max Awakenings is: " + monster.getMaxAwakenings());
        for(int i = 0; i < 9; i++){
            if(i >= monster.getMaxAwakenings()){
                awakeningHolder.getChildAt(i).setVisibility(View.GONE);
            }else {
                awakeningHolder.getChildAt(i).setVisibility(View.VISIBLE);
            }
        }
    }

    //Gray out depending on monsterAwakeningsValue
    //@TargetApi(11)
    public void grayAwakenings() {
        for (int j = 0; j < monster.getCurrentAwakenings(); j++) {
            switch (monster.getAwokenSkills().get(j)) {
                case 1:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_1);
                    break;
                case 2:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_2);
                    break;
                case 3:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_3);
                    break;
                case 4:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_4);
                    break;
                case 5:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_5);
                    break;
                case 6:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_6);
                    break;
                case 7:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_7);
                    break;
                case 8:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_8);
                    break;
                case 9:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_9);
                    break;
                case 10:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_10);
                    break;
                case 11:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_11);
                    break;
                case 12:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_12);
                    break;
                case 13:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_13);
                    break;
                case 14:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_14);
                    break;
                case 15:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_15);
                    break;
                case 16:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_16);
                    break;
                case 17:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_17);
                    break;
                case 18:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_18);
                    break;
                case 19:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_19);
                    break;
                case 20:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_20);
                    break;
                case 21:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_21);
                    break;
                case 22:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_22);
                    break;
                case 23:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_23);
                    break;
                case 24:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_24);
                    break;
                case 25:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_25);
                    break;
                case 26:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_26);
                    break;
                case 27:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_27);
                    break;
                case 28:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_28);
                    break;
                case 29:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_29);
                    break;
                case 30:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_30);
                    break;
                case 31:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_31);
                    break;
                case 32:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_32);
                    break;
                case 33:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_33);
                    break;
                case 34:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_34);
                    break;
                case 35:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_35);
                    break;
                case 36:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_36);
                    break;
                case 37:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_37);
                    break;
            }
        }

        for (int j = monster.getCurrentAwakenings(); j < monster.getMaxAwakenings(); j++) {
            switch (monster.getAwokenSkills().get(j)) {
                case 1:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_1_disabled);
                    break;
                case 2:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_2_disabled);
                    break;
                case 3:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_3_disabled);
                    break;
                case 4:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_4_disabled);
                    break;
                case 5:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_5_disabled);
                    break;
                case 6:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_6_disabled);
                    break;
                case 7:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_7_disabled);
                    break;
                case 8:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_8_disabled);
                    break;
                case 9:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_9_disabled);
                    break;
                case 10:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_10_disabled);
                    break;
                case 11:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_11_disabled);
                    break;
                case 12:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_12_disabled);
                    break;
                case 13:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_13_disabled);
                    break;
                case 14:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_14_disabled);
                    break;
                case 15:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_15_disabled);
                    break;
                case 16:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_16_disabled);
                    break;
                case 17:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_17_disabled);
                    break;
                case 18:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_18_disabled);
                    break;
                case 19:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_19_disabled);
                    break;
                case 20:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_20_disabled);
                    break;
                case 21:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_21_disabled);
                    break;
                case 22:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_22_disabled);
                    break;
                case 23:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_23_disabled);
                    break;
                case 24:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_24_disabled);
                    break;
                case 25:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_25_disabled);
                    break;
                case 26:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_26_disabled);
                    break;
                case 27:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_27_disabled);
                    break;
                case 28:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_28_disabled);
                    break;
                case 29:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_29_disabled);
                    break;
                case 30:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_30_disabled);
                    break;
                case 31:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_31_disabled);
                    break;
                case 32:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_32_disabled);
                    break;
                case 33:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_33_disabled);
                    break;
                case 34:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_34_disabled);
                    break;
                case 35:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_35_disabled);
                    break;
                case 36:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_36_disabled);
                    break;
                case 37:
                    awakeningHolder.getChildAt(j).setBackgroundResource(R.drawable.awakening_37_disabled);
                    break;
            }
        }
//        int i = 0;
//        View view = null;
//        for (i = 0; i < monster.getCurrentAwakenings(); i++) {
//            view = awakeningHolder.getChildAt(i);
//
//            view.setAlpha(1);
//        }
//        for (i = monster.getCurrentAwakenings(); i < monster.getMaxAwakenings(); i++) {
//            view = awakeningHolder.getChildAt(i);
//
//            view.setAlpha((float) .5);
//        }
    }

    private void setImageViews() {
        switch (monster.getType1()) {
            case 0:
                type1.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                type1.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                type1.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                type1.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                type1.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                type1.setImageResource(R.drawable.type_god);
                break;
            case 6:
                type1.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                type1.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                type1.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                type1.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                type1.setVisibility(View.INVISIBLE);
                break;
            case 14:
                type1.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                type1.setVisibility(View.GONE);
                break;
        }
        switch (monster.getType2()) {
            case 0:
                type2.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                type2.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                type2.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                type2.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                type2.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                type2.setImageResource(R.drawable.type_god);
                break;
            case 6:
                type2.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                type2.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                type2.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                type2.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                type2.setVisibility(View.INVISIBLE);
                break;
            case 14:
                type2.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                type2.setVisibility(View.GONE);
                break;
        }
        switch (monster.getType3()) {
            case 0:
                type3.setImageResource(R.drawable.type_evo_material);
                break;
            case 1:
                type3.setImageResource(R.drawable.type_balanced);
                break;
            case 2:
                type3.setImageResource(R.drawable.type_physical);
                break;
            case 3:
                type3.setImageResource(R.drawable.type_healer);
                break;
            case 4:
                type3.setImageResource(R.drawable.type_dragon);
                break;
            case 5:
                type3.setImageResource(R.drawable.type_god);
                break;
            case 6:
                type3.setImageResource(R.drawable.type_attacker);
                break;
            case 7:
                type3.setImageResource(R.drawable.type_devil);
                break;
            case 8:
                type3.setImageResource(R.drawable.type_machine);
                break;
            case 12:
                type3.setImageResource(R.drawable.type_awoken);
                break;
            case 13:
                type3.setVisibility(View.INVISIBLE);
                break;
            case 14:
                type3.setImageResource(R.drawable.type_enhance_material);
                break;
            default:
                type3.setVisibility(View.GONE);
                break;
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

    public void monsterStats() {
        //Update method because our TextWatcher no work
        setMonsterStats();
        setTextViewValues();

    }

    public void setMonsterStats() {
        monster.setCurrentLevel(Integer.parseInt(monsterLevelValue.getText().toString()));
        monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
        monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
        monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
        monster.setHpPlus(Integer.valueOf(monsterStatsHPPlus.getText().toString()));
        monster.setAtkPlus(Integer.valueOf(monsterStatsATKPlus.getText().toString()));
        monster.setRcvPlus(Integer.valueOf(monsterStatsRCVPlus.getText().toString()));
    }

    public void setTextViewValues() {
        Log.d("What are the values", "Values: " + monster.getCurrentHp() + " " + monster.getCurrentAtk() + " " + monster.getCurrentRcv());
        monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
        monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
        monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
        monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
        monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
        monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
//        monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
//        monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));
    }

    public void initializeEditTexts() {
        monsterLevelValue.setText(String.valueOf(monster.getCurrentLevel()));
        monsterStatsHPPlus.setText(String.valueOf(monster.getHpPlus()));
        monsterStatsATKPlus.setText(String.valueOf(monster.getAtkPlus()));
        monsterStatsRCVPlus.setText(String.valueOf(monster.getRcvPlus()));
        monsterAwakeningsValue.setText(String.valueOf(monster.getCurrentAwakenings()));
    }

    public void initBackup() {
        level = monster.getCurrentLevel();
        hp = monster.getHpPlus();
        atk = monster.getAtkPlus();
        rcv = monster.getRcvPlus();
        awakening = monster.getCurrentAwakenings();
    }

    public void loadBackup() {
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

    public void disableStuff() {
        if (monster.getMonsterId() == 0) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
        if (monster.getType1() == 0 || monster.getType1() == 14){
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
        if(monster.getMaxAwakenings() == 0){
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
        }else {
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

    public void clearTextFocus() {
        monsterLevelValue.clearFocus();
        monsterStatsHPPlus.clearFocus();
        monsterStatsATKPlus.clearFocus();
        monsterStatsRCVPlus.clearFocus();
        monsterAwakeningsValue.clearFocus();
    }

    private MonsterRemoveDialogFragment.RemoveMonster removeMonster = new MonsterRemoveDialogFragment.RemoveMonster() {
        @Override
        public void removeMonsterDatabase() {
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster);
            }
            deleteConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
            monsterRemoveDialogFragment.dismiss();
        }

        @Override
        public void removeMonsterTeam() {
            if (Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Log.d("Monster Page Log", "Position is: " + Singleton.getInstance().getMonsterOverwrite() + " " + Monster.getMonsterId(0) + " Monster name: " + Monster.getMonsterId(0).getName());
                Team newTeam = new Team(Team.getTeamById(0));
                Log.d("Monster Page Log", "Monster Overwrite is: " + Singleton.getInstance().getMonsterOverwrite());
                switch (Singleton.getInstance().getMonsterOverwrite()) {
                    case 0:
                        newTeam.setLead(Monster.getMonsterId(0));
                        break;
                    case 1:
                        newTeam.setSub1(Monster.getMonsterId(0));
                        break;
                    case 2:
                        newTeam.setSub2(Monster.getMonsterId(0));
                        break;
                    case 3:
                        newTeam.setSub3(Monster.getMonsterId(0));
                        break;
                    case 4:
                        newTeam.setSub4(Monster.getMonsterId(0));
                        break;
                    case 5:
                        newTeam.setHelper(Monster.getMonsterId(0));
                        break;
                }
                for (int i = 0; i < newTeam.getMonsters().size(); i++) {
                    Log.d("Monster Page Log", "Monster name: " + newTeam.getMonsters(i).getName() + " Monster id: " + newTeam.getMonsters(i).getMonsterId());
                }
                newTeam.save();
//            monster = Monster.getMonsterId(0);
//            monsterName.setText(monster.getName());
//            monsterPicture.setImageResource(monster.getMonsterPicture());
//            setTextViewValues();
//            initializeEditTexts();
//            showAwakenings();
                monsterRemoveDialogFragment.dismiss();
//            getChildFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }

        @Override
        public void favoriteMonster(boolean favorite) {
            Log.d("Monster Page Log", "favorite is: " + favorite);
            monster.setFavorite(favorite);
            monster.save();
            setFavorite();
            Log.d("Monster Page Log", "Monster favorite is: " + monster.isFavorite());
        }

        @Override
        public void replaceAllTeam() {
            if (replaceConfirmationDialog == null) {
                replaceConfirmationDialog = ReplaceAllConfirmationDialogFragment.newInstance(replaceAllMonster);
            }
            replaceConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
        }

        @Override
        public void replaceMonster() {
            ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(false, 1, 99), MonsterTabLayoutFragment.TAG);
            monsterRemoveDialogFragment.dismiss();
        }

        @Override
        public void evolveMonster(long baseMonsterId) {
            if (baseMonsterId != 0){
                monster.setBaseMonster(BaseMonster.getMonsterId(baseMonsterId));
                if(monster.getCurrentAwakenings() > monster.getMaxAwakenings()){
                    monster.setCurrentAwakenings(monster.getMaxAwakenings());
                }
                monster.save();
                rarity.setText("" + monster.getRarity());
                Log.d("Monster Page Log", "Monster attack is: " + monster.getTotalAtk() + " Monster max awakenings is: " + monster.getMaxAwakenings() + " Monster rarity is: " + monster.getRarity());
                initBackup();
                monsterPicture.setImageResource(monster.getMonsterPicture());
                monsterName.setText(monster.getName());
                showAwakenings();
                grayAwakenings();
                initializeEditTexts();
                setImageViews();
                monsterStats();
            }
        }
    };

    private ReplaceAllConfirmationDialogFragment.ResetLayout replaceAllMonster = new ReplaceAllConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(true, monster.getMonsterId(), 99), MonsterTabLayoutFragment.TAG);
            replaceConfirmationDialog.dismiss();
        }
    };

    private DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
            Team newTeam;
            for (int i = 0; i < teamList.size(); i++) {
                newTeam = teamList.get(i);
                for (int j = 0; j < newTeam.getMonsters().size(); j++) {
                    if (newTeam.getMonsters().get(j).getMonsterId() == monster.getMonsterId()) {
                        newTeam.setMonsters(j, Monster.getMonsterId(0));
                    }
                }
                newTeam.save();
            }
            Log.d("Monster Page Log", "monsterId is: " + monster.getMonsterId());
            monster.delete();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };

    private View.OnClickListener latentOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if (latentAwakeningDialogFragment == null) {
                latentAwakeningDialogFragment = LatentAwakeningDialogFragment.newInstance(setLatents, monster);
            }
            latentAwakeningDialogFragment.show(getChildFragmentManager(), "LAATENTSNTSNTS", monster);
            Log.d("MonsterPageFragment", "Latent Awakenings are: " + monster.getLatents());
        }
    };

    private LatentAwakeningDialogFragment.ResetLatents setLatents = new LatentAwakeningDialogFragment.ResetLatents(){
        @Override
        public void refreshLatents() {
            setLatents();
            setTextViewValues();
        }
    };

    private void setLatents(){
        for (int i = 0; i < monster.getLatents().size(); i++){
            switch(monster.getLatents().get(i)){
                case 0:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_blank);
                    break;
                case 1:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_1);
                    break;
                case 2:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_2);
                    break;
                case 3:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_3);
                    break;
                case 4:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_4);
                    break;
                case 5:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_5);
                    break;
                case 6:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_6);
                    break;
                case 7:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_7);
                    break;
                case 8:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_8);
                    break;
                case 9:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_9);
                    break;
                case 10:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_10);
                    break;
                case 11:
                    latentHolder.getChildAt(i).setBackgroundResource(R.drawable.latent_awakening_11);
                    break;
            }
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }


    @Override
    public void sortArrayList(int sortMethod) {

    }

    @Override
    public void reverseArrayList() {

    }

    @Override
    public void searchFilter(String query) {

    }

//   private ViewTreeObserver.OnGlobalLayoutListener rootListener = new ViewTreeObserver.OnGlobalLayoutListener()
//   {
//      @Override
//      public void onGlobalLayout()
//      {
//
//         if (getResources().getConfiguration().keyboardHidden == Configuration.KEYBOARDHIDDEN_YES)
//         {
//            if(((ViewGroup)rootView).getFocusedChild() == null)
//            {
//               Log.d("FKTHOMSA", "DAM U THOMAS");
//            }
//            ((ViewGroup)rootView).getFocusedChild().clearFocus();
//            Log.d("is this real?", "what da");
//
//         }
//      }
//   };


}
