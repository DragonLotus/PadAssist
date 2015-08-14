package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Adapters.MonsterDamageListAdapter;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamDamageListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDamageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDamageListFragment extends Fragment {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private ListView monsterListView;
    private EditText additionalComboValue, damageThresholdValue;
    private MonsterDamageListAdapter monsterListAdapter;
    private Enemy enemy;
    private Team team;
    private Toast toast;
    private boolean hasEnemy;
    //private ArrayList<Monster> monsterList;
    private int additionalCombos, additionalCombosFragment, totalCombos = 0, totalDamage = 0, temp = 0;
    private TextView monsterListToggle, enemyHP, enemyHPValue, enemyHPPercent, enemyHPPercentValue, totalDamageValue, totalComboValue, hpRecoveredValue, targetReduction, targetAbsorb, damageThreshold;
    private RadioGroup reductionRadioGroup;
    private CheckBox redOrbReduction, blueOrbReduction, greenOrbReduction, lightOrbReduction, darkOrbReduction;
    private CheckBox absorbCheck, reductionCheck, damageThresholdCheck;
    private RadioGroup absorbRadioGroup;
    private Button recalculateButton;
    private DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamDamageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDamageListFragment newInstance(String param1, String param2) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static TeamDamageListFragment newInstance(boolean hasEnemy, int additionalCombos, Team team, Enemy enemy) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putBoolean("hasEnemy", hasEnemy);
        args.putInt("additionalCombos", additionalCombos);
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public static TeamDamageListFragment newInstance(boolean hasEnemy, int additionalCombos, Team team) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putBoolean("hasEnemy", hasEnemy);
        args.putInt("additionalCombos", additionalCombos);
        args.putParcelable("team", team);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamDamageListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_team_damage_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        additionalComboValue = (EditText) rootView.findViewById(R.id.additionalComboValue);
        monsterListToggle = (TextView) rootView.findViewById(R.id.monsterListToggle);
        enemyHP = (TextView) rootView.findViewById(R.id.enemyHP);
        enemyHPValue = (TextView) rootView.findViewById(R.id.enemyHPValue);
        enemyHPPercent = (TextView) rootView.findViewById(R.id.enemyHPPercent);
        enemyHPPercentValue = (TextView) rootView.findViewById(R.id.enemyHPPercentValue);
        totalDamageValue = (TextView) rootView.findViewById(R.id.totalDamageValue);
        totalComboValue = (TextView) rootView.findViewById(R.id.totalComboValue);
        hpRecoveredValue = (TextView) rootView.findViewById(R.id.hpRecoveredValue);
        targetReduction = (TextView) rootView.findViewById(R.id.targetReduction);
        targetAbsorb = (TextView) rootView.findViewById(R.id.elementAbsorb);
        recalculateButton = (Button) rootView.findViewById(R.id.recalculateButton);
        reductionRadioGroup = (RadioGroup) rootView.findViewById(R.id.reductionOrbRadioGroup);
        absorbRadioGroup = (RadioGroup) rootView.findViewById(R.id.absorbOrbRadioGroup);
        redOrbReduction = (CheckBox) rootView.findViewById(R.id.redOrbReduction);
        blueOrbReduction = (CheckBox) rootView.findViewById(R.id.blueOrbReduction);
        greenOrbReduction = (CheckBox) rootView.findViewById(R.id.greenOrbReduction);
        darkOrbReduction = (CheckBox) rootView.findViewById(R.id.darkOrbReduction);
        lightOrbReduction = (CheckBox) rootView.findViewById(R.id.lightOrbReduction);
        reductionCheck = (CheckBox) rootView.findViewById(R.id.reductionCheck);
        damageThresholdValue = (EditText) rootView.findViewById(R.id.damageThresholdValue);
        damageThresholdCheck = (CheckBox) rootView.findViewById(R.id.damageThresholdCheck);
        damageThreshold = (TextView) rootView.findViewById(R.id.damageThreshold);
        absorbCheck = (CheckBox) rootView.findViewById(R.id.absorbCheck);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            hasEnemy = getArguments().getBoolean("hasEnemy");
            additionalCombos = getArguments().getInt("additionalCombos");
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
        }
        if (hasEnemy) {
            temp = enemy.getCurrentHp();
            setReductionOrbs();
            setAbsorbOrbs();
            setDamageThreshold();
        }
        totalCombos = additionalCombos + team.sizeOrbMatches();
        updateTextView();
        Log.d("totalCombos", String.valueOf(totalCombos));
        monsterListAdapter = new MonsterDamageListAdapter(getActivity(), R.layout.monster_damage_row, hasEnemy, enemy, totalCombos, team);
        monsterListView.setAdapter(monsterListAdapter);
        monsterListToggle.setOnClickListener(monsterListToggleOnClickListener);
        additionalComboValue.addTextChangedListener(additionalComboTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
        monsterListView.setOnItemClickListener(bindMonsterOnClickListener);
        recalculateButton.setOnClickListener(recalculateButtonOnClickListener);
        absorbCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        reductionCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        damageThresholdCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        damageThresholdValue.addTextChangedListener(damageThresholdWatcher);
        damageThresholdValue.setOnFocusChangeListener(editTextOnFocusChange);

        redOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        blueOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        greenOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        darkOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        lightOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);

        absorbRadioGroup.setOnCheckedChangeListener(absorbOnCheckChangeListener);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private View.OnClickListener monsterListToggleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monsterListView.getVisibility() == View.GONE) {
                monsterListView.setVisibility(View.VISIBLE);
                monsterListToggle.setText("Hide Monster Damage Breakdown");
            } else if (monsterListView.getVisibility() == View.VISIBLE) {
                monsterListView.setVisibility(View.GONE);
                monsterListToggle.setText("Show Monster Damage Breakdown");
            }
        }
    };

    public void updateTextView() {
        totalDamage = 0;
        if (!hasEnemy) {
            enemyHP.setVisibility(View.GONE);
            enemyHPValue.setVisibility(View.GONE);
            enemyHPPercent.setVisibility(View.GONE);
            enemyHPPercentValue.setVisibility(View.GONE);
            reductionRadioGroup.setVisibility(View.GONE);
            targetReduction.setVisibility(View.GONE);
            targetAbsorb.setVisibility(View.GONE);
            absorbRadioGroup.setVisibility(View.GONE);
            damageThresholdCheck.setVisibility(View.GONE);
            damageThresholdValue.setVisibility(View.GONE);
            damageThreshold.setVisibility(View.GONE);
            reductionCheck.setVisibility(View.GONE);
            absorbCheck.setVisibility(View.GONE);
            for (int i = 0; i < team.sizeMonsters(); i++) {
                if (team.getMonsters(i).isBound()) {
                } else {
                    totalDamage += team.getMonsters(i).getElement1Damage(team, totalCombos);
                    totalDamage += team.getMonsters(i).getElement2Damage(team, totalCombos);
                }
            }
        } else {
            enemy.setCurrentHp(temp);
            if (enemy.getHasDamageThreshold()) {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement1DamageThreshold(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement1DamageThreshold(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement1DamageThreshold(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement1DamageThreshold(team, enemy, totalCombos);
                            }
                        }
                    }
                }
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement2DamageThreshold(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement2DamageThreshold(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement2DamageThreshold(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement2DamageThreshold(team, enemy, totalCombos);
                            }
                        }
                    }
                }
            } else if (enemy.getHasAbsorb()) {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement1DamageAbsorb(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement1DamageAbsorb(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement1DamageAbsorb(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement1DamageAbsorb(team, enemy, totalCombos);
                            }
                        }
                    }
                }
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement2DamageAbsorb(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement2DamageAbsorb(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement2DamageAbsorb(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement2DamageAbsorb(team, enemy, totalCombos);
                            }
                        }

                    }
                }
            } else if (enemy.getHasReduction()) {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        totalDamage += team.getMonsters(i).getElement1DamageReduction(team, enemy, totalCombos);
                        totalDamage += team.getMonsters(i).getElement2DamageReduction(team, enemy, totalCombos);
                    }
                }
            } else {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getMonsters(i).isBound()) {
                    } else {
                        totalDamage += team.getMonsters(i).getElement1DamageEnemy(team, enemy, totalCombos);
                        totalDamage += team.getMonsters(i).getElement2DamageEnemy(team, enemy, totalCombos);
                    }
                }
            }
            //Need to set colors of each enemy element stuff
            setTextColors();
            Log.d("Damage Current HP", "" + enemy.getCurrentHp());
            Log.d("Damage Before HP", "" + enemy.getBeforeGravityHP());
            enemy.setCurrentHp(enemy.getCurrentHp() - totalDamage);
            Log.d("Damage Current HP2", "" + enemy.getCurrentHp());
            Log.d("Damage Before HP2", "" + enemy.getBeforeGravityHP());
            if (enemy.getCurrentHp() != temp) {
                enemy.setBeforeGravityHP(enemy.getCurrentHp());
                enemy.setIsDamaged(true);
            }
            if(totalDamage == 0 && enemy.getCurrentHp() == enemy.getTargetHp()){
                enemy.setBeforeGravityHP(enemy.getCurrentHp());
            }
            enemyHPValue.setText(String.valueOf(enemy.getCurrentHp()) + " ");
            enemyHPPercentValue.setText(String.valueOf(df.format((double) enemy.getCurrentHp() / enemy.getTargetHp() * 100) + "%"));
        }
        team.setTotalDamage(totalDamage);
        totalDamageValue.setText(String.valueOf(totalDamage) + " ");
        hpRecoveredValue.setText(String.valueOf((int) DamageCalculationUtil.hpRecovered(team.getTeamRcv(), team.getOrbMatches(), totalCombos)) + " ");
        totalComboValue.setText(String.valueOf(totalCombos));
    }

    private ListView.OnItemClickListener bindMonsterOnClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            additionalComboValue.clearFocus();
            if (team.getMonsters(position).isBound()) {
                team.getMonsters(position).setIsBound(false);
                Log.d("Bound", "monster " + position + " is unbound");
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster unbound", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                team.getMonsters(position).setIsBound(true);
                Log.d("Bound", "monster " + position + " is bound");
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster bound", Toast.LENGTH_SHORT);
                toast.show();
            }
            team.update();
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
        }
    };

    private void setTextColors() {
        if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.RED)) {
            enemyHPValue.setTextColor(Color.parseColor("#FF0000"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
            enemyHPValue.setTextColor(Color.parseColor("#4444FF"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
            enemyHPValue.setTextColor(Color.parseColor("#00CC00"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
            enemyHPValue.setTextColor(Color.parseColor("#F0F000"));
        } else if (enemy.getTargetColor().equals(com.example.anthony.damagecalculator.Data.Color.DARK)) {
            enemyHPValue.setTextColor(Color.parseColor("#AA00FF"));
        }
        if (totalDamage < 0) {
            totalDamageValue.setTextColor(Color.parseColor("#FFBBBB"));
        }
    }

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.ADDITIONAL_COMBOS) {
                additionalCombosFragment = statValue;
            }
            if (statToChange == MyTextWatcher.DAMAGE_THRESHOLD) {
                enemy.setDamageThreshold(statValue);
            }
        }
    };

    private MyTextWatcher additionalComboTextWatcher = new MyTextWatcher(MyTextWatcher.ADDITIONAL_COMBOS, changeStats);
    private MyTextWatcher damageThresholdWatcher = new MyTextWatcher(MyTextWatcher.DAMAGE_THRESHOLD, changeStats);

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
                if ((additionalComboValue.getText().toString().equals(""))) {
                    additionalComboValue.setText("0");
                } else if (damageThresholdValue.getText().toString().equals("")) {
                    damageThresholdValue.setText("0");
                    enemy.setDamageThreshold(0);
                }
            }
        }
    };

    private Button.OnClickListener recalculateButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            totalCombos += additionalCombosFragment;
            Log.d("Total Combo 1", "" + totalCombos);
            if (totalCombos < team.sizeOrbMatches()) {
                totalCombos = team.sizeOrbMatches();
            }
            monsterListAdapter.setCombos(totalCombos);
            Log.d("Total Combo 2", "" + totalCombos);
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
            additionalComboValue.setText("0");
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void setReductionOrbs() {
        if (enemy.getHasReduction()) {
            reductionCheck.setChecked(true);
            for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                reductionRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.RED)) {
                redOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.BLUE)) {
                blueOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.GREEN)) {
                greenOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.DARK)) {
                darkOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(com.example.anthony.damagecalculator.Data.Color.LIGHT)) {
                lightOrbReduction.setChecked(true);
            }

        }

    }

    private void setAbsorbOrbs() {
        absorbCheck.setChecked(enemy.getHasAbsorb());
        if (enemy.getHasAbsorb()) {
            for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                absorbRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.RED) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.redOrbAbsorb);
            } else if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.BLUE) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.blueOrbAbsorb);
            } else if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.GREEN) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.greenOrbAbsorb);
            } else if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.LIGHT) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.lightOrbAbsorb);
            } else if (enemy.getAbsorb() == com.example.anthony.damagecalculator.Data.Color.DARK) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.darkOrbAbsorb);
            }
        }

    }

    private void setDamageThreshold() {
        if (enemy.getHasDamageThreshold()) {
            damageThresholdValue.setEnabled(true);
            damageThresholdCheck.setChecked(true);
            damageThresholdValue.setText(String.valueOf(enemy.getDamageThreshold()));
        }
    }

    private CompoundButton.OnCheckedChangeListener checkBoxOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.equals(damageThresholdCheck)) {
                enemy.setHasDamageThreshold(isChecked);
                if (isChecked) {
                    damageThresholdValue.setEnabled(true);
                } else {
                    damageThresholdValue.clearFocus();
                    damageThresholdValue.setEnabled(false);
                }
            } else if (buttonView.equals(reductionCheck)) {
                enemy.setHasReduction(isChecked);
                if (isChecked) {
                    for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                        reductionRadioGroup.getChildAt(i).setEnabled(true);
                    }
                } else {
                    redOrbReduction.setChecked(false);
                    blueOrbReduction.setChecked(false);
                    greenOrbReduction.setChecked(false);
                    lightOrbReduction.setChecked(false);
                    darkOrbReduction.setChecked(false);
                    for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                        reductionRadioGroup.getChildAt(i).setEnabled(false);
                    }
                    setElementReduction(isChecked, buttonView.getId());
                }
            } else if (buttonView.equals(absorbCheck)) {
                enemy.setHasAbsorb(isChecked);
                if (isChecked) {
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(true);
                    }
                } else {
                    absorbRadioGroup.clearCheck();
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.HEART);
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(false);
                    }
                }
            }
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
        }
    };


    private CompoundButton.OnCheckedChangeListener reductionCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setElementReduction(isChecked, buttonView.getId());
        }
    };

    private void setElementReduction(boolean isChecked, int buttonId) {
        com.example.anthony.damagecalculator.Data.Color color = null;
        switch (buttonId) {
            case R.id.redOrbReduction:
                color = com.example.anthony.damagecalculator.Data.Color.RED;
                break;
            case R.id.blueOrbReduction:
                color = com.example.anthony.damagecalculator.Data.Color.BLUE;
                break;
            case R.id.greenOrbReduction:
                color = com.example.anthony.damagecalculator.Data.Color.GREEN;
                break;
            case R.id.darkOrbReduction:
                color = com.example.anthony.damagecalculator.Data.Color.DARK;
                break;
            case R.id.lightOrbReduction:
                color = com.example.anthony.damagecalculator.Data.Color.LIGHT;
                break;
        }

        if (isChecked) {
            if (!enemy.containsReduction(color)) {
                enemy.addReduction(color);
            }
        } else {
            if (enemy.containsReduction(color)) {
                enemy.removeReduction(color);
            }
        }
        updateTextView();
        monsterListAdapter.notifyDataSetChanged();
    }

    private RadioGroup.OnCheckedChangeListener absorbOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int radioChecked = group.getCheckedRadioButtonId();
            switch (radioChecked) {
                case R.id.redOrbAbsorb:
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.RED);
                    break;
                case R.id.blueOrbAbsorb:
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.BLUE);
                    break;
                case R.id.greenOrbAbsorb:
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.GREEN);
                    break;
                case R.id.lightOrbAbsorb:
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.LIGHT);
                    break;
                case R.id.darkOrbAbsorb:
                    enemy.setAbsorb(com.example.anthony.damagecalculator.Data.Color.DARK);
                    break;
            }
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
            Log.d("absorb", "" + enemy.getAbsorb());
        }
    };

}
