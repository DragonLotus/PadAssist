package com.padassist.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.MonsterDamageListRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.Team;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;
import com.padassist.Util.DamageCalculationUtil;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamDamageListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDamageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDamageListFragment extends AbstractFragment {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private RecyclerView monsterListView;
    private EditText additionalComboValue, damageThresholdValue, damageImmunityValue, reductionValue;
    private MonsterDamageListRecycler monsterListAdapter;
    private Enemy enemy;
    private Team team;
    private Toast toast;
    private boolean hasEnemy;
    //private ArrayList<Monster> monsterList;
    private int additionalCombos, additionalCombosFragment, totalCombos = 0, totalDamage = 0, temp = 0;
    private TextView enemyHP, enemyHPValue, enemyHPPercent, enemyHPPercentValue, totalDamageValue, totalComboValue, hpRecoveredValue, targetReduction, targetAbsorb, damageThreshold, hasAwakenings, teamHpValue, damageImmunity, reductionPercent;
    private RadioGroup reductionRadioGroup;
    private Button monsterListToggle;
    private CheckBox redOrbReduction, blueOrbReduction, greenOrbReduction, lightOrbReduction, darkOrbReduction;
    private CheckBox absorbCheck, reductionCheck, damageThresholdCheck, damageImmunityCheck, hasAwakeningsCheck, activeUsedCheck;
    private RadioGroup absorbRadioGroup;
    private Button optionButton;
    private SeekBar teamHp;
    private DecimalFormat df = new DecimalFormat("#.##");
    private ExtraMultiplierDialogFragment extraMultiplierDialogFragment;

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.teamDamage, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                clearTextFocus();
                totalCombos += additionalCombosFragment;
                Log.d("Total Combo 1", "" + totalCombos);
                if (totalCombos < team.getOrbMatches().size()) {
                    totalCombos = team.getOrbMatches().size();
                }
                monsterListAdapter.setCombos(totalCombos);
                Log.d("Total Combo 2", "" + totalCombos);
                updateTextView();
                monsterListAdapter.notifyDataSetChanged();
                additionalComboValue.setText("0");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_team_damage_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
        additionalComboValue = (EditText) rootView.findViewById(R.id.additionalComboValue);
        monsterListToggle = (Button) rootView.findViewById(R.id.monsterListToggle);
        enemyHP = (TextView) rootView.findViewById(R.id.enemyHP);
        enemyHPValue = (TextView) rootView.findViewById(R.id.enemyHPValue);
        enemyHPPercent = (TextView) rootView.findViewById(R.id.enemyHPPercent);
        enemyHPPercentValue = (TextView) rootView.findViewById(R.id.enemyHPPercentValue);
        totalDamageValue = (TextView) rootView.findViewById(R.id.totalDamageValue);
        totalComboValue = (TextView) rootView.findViewById(R.id.totalComboValue);
        hpRecoveredValue = (TextView) rootView.findViewById(R.id.hpRecoveredValue);
        targetReduction = (TextView) rootView.findViewById(R.id.targetReduction);
        targetAbsorb = (TextView) rootView.findViewById(R.id.elementAbsorb);
        hasAwakenings = (TextView) rootView.findViewById(R.id.hasAwakenings);
        optionButton = (Button) rootView.findViewById(R.id.options);
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
        hasAwakeningsCheck = (CheckBox) rootView.findViewById(R.id.hasAwakeningsCheck);
        activeUsedCheck = (CheckBox) rootView.findViewById(R.id.activeUsedCheck);
        teamHp = (SeekBar) rootView.findViewById(R.id.teamHpSeekBar);
        teamHpValue = (TextView) rootView.findViewById(R.id.teamHpValue);
        reductionValue = (EditText) rootView.findViewById(R.id.reductionValue);
        damageImmunityValue = (EditText) rootView.findViewById(R.id.damageImmunityValue);
        damageImmunityCheck = (CheckBox) rootView.findViewById(R.id.damageImmunityCheck);
        damageImmunity = (TextView) rootView.findViewById(R.id.damageImmunity);
        reductionPercent = (TextView) rootView.findViewById(R.id.reductionPercent);
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
        for (int i = 0; i < team.getMonsters().size(); i++) {
            Log.d("Team Damage Log", "Monster is: " + team.getMonsters(i) + " Awakenings are: " + team.getMonsters(i).getAwokenSkills());
        }
        Log.d("Team Damage Log", "Orb Plus Awakenings: " + team.getOrbPlusAwakenings());
        Log.d("Team Damage Log", "Team Name is: " + team.getTeamName() + " Team id: " + team.getTeamId() + " Team overwrite id: " + team.getTeamIdOverwrite());
        if (hasEnemy) {
            temp = enemy.getCurrentHp();
            setReductionOrbs();
            setAbsorbOrbs();
            setDamageThreshold();
        }
        setCheckBoxes();
        totalCombos = additionalCombos + team.getOrbMatches().size();
        updateTextView();
        setupHpSeekBar();
        Log.d("totalCombos", String.valueOf(totalCombos));
        monsterListAdapter = new MonsterDamageListRecycler(getActivity(), hasEnemy, enemy, totalCombos, team, bindMonsterOnClickListener);
        monsterListView.setAdapter(monsterListAdapter);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        monsterListToggle.setOnClickListener(monsterListToggleOnClickListener);
        additionalComboValue.addTextChangedListener(additionalComboTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
//        monsterListView.setOnItemClickListener(bindMonsterOnClickListener);
        optionButton.setOnClickListener(optionsButtonOnClickListener);
        absorbCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        reductionCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        damageThresholdCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        damageThresholdValue.addTextChangedListener(damageThresholdWatcher);
        damageThresholdValue.setOnFocusChangeListener(editTextOnFocusChange);
        damageImmunityCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        damageImmunityValue.addTextChangedListener(damageImmunityWatcher);
        damageImmunityValue.setOnFocusChangeListener(editTextOnFocusChange);
        reductionValue.addTextChangedListener(reductionWatcher);
        redOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        blueOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        greenOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        darkOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        lightOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        absorbRadioGroup.setOnCheckedChangeListener(absorbOnCheckChangeListener);
        hasAwakeningsCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        activeUsedCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        teamHp.setOnSeekBarChangeListener(teamHpSeekBarListener);
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
                monsterListToggle.setText("Hide Damage Breakdown");
            } else if (monsterListView.getVisibility() == View.VISIBLE) {
                monsterListView.setVisibility(View.GONE);
                monsterListToggle.setText("Show Damage Breakdown");
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
            reductionValue.setVisibility(View.GONE);
            damageImmunityValue.setVisibility(View.GONE);
            damageImmunity.setVisibility(View.GONE);
            damageImmunityCheck.setVisibility(View.GONE);
            reductionPercent.setVisibility(View.GONE);
            RelativeLayout.LayoutParams z = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            z.addRule(RelativeLayout.BELOW, totalComboValue.getId());
            z.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            hasAwakeningsCheck.setLayoutParams(z);
            for (int i = 0; i < team.sizeMonsters(); i++) {
                if (team.getIsBound().get(i)) {
                } else {
                    totalDamage += team.getMonsters(i).getElement1Damage(team, totalCombos);
                    totalDamage += team.getMonsters(i).getElement2Damage(team, totalCombos);
                }
            }
        } else {
            enemy.setCurrentHp(temp);
            if (enemy.getHasDamageThreshold()) {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getIsBound().get(i)) {
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
                    if (team.getIsBound().get(i)) {
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
            } else if (enemy.hasDamageImmunity()){
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getIsBound().get(i)) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement1DamageImmunity(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement1DamageImmunity(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement1DamageImmunity(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement1DamageImmunity(team, enemy, totalCombos);
                            }
                        }
                    }
                }
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getIsBound().get(i)) {
                    } else {
                        if (enemy.getCurrentHp() - (team.getMonsters(i).getElement2DamageImmunity(team, enemy, totalCombos) + totalDamage) >= enemy.getTargetHp()) {
                            totalDamage = enemy.getCurrentHp() - enemy.getTargetHp();
                        } else {
                            if (team.getMonsters(i).getElement2DamageImmunity(team, enemy, totalCombos) < 0 && totalDamage >= enemy.getCurrentHp()) {
                                totalDamage = enemy.getCurrentHp() + team.getMonsters(i).getElement2DamageImmunity(team, enemy, totalCombos);
                            } else {
                                totalDamage += team.getMonsters(i).getElement2DamageImmunity(team, enemy, totalCombos);
                            }
                        }
                    }
                }
            }
            else if (enemy.getHasAbsorb()) {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getIsBound().get(i)) {
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
                    if (team.getIsBound().get(i)) {
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
                    if (team.getIsBound().get(i)) {
                    } else {
                        totalDamage += team.getMonsters(i).getElement1DamageReduction(team, enemy, totalCombos);
                        totalDamage += team.getMonsters(i).getElement2DamageReduction(team, enemy, totalCombos);
                    }
                }
            } else {
                for (int i = 0; i < team.sizeMonsters(); i++) {
                    if (team.getIsBound().get(i)) {
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
            if (totalDamage == 0 && enemy.getCurrentHp() == enemy.getTargetHp()) {
                enemy.setBeforeGravityHP(enemy.getCurrentHp());
            }
            enemyHPValue.setText(" " + String.valueOf(enemy.getCurrentHp()) + " ");
            enemyHPPercentValue.setText(String.valueOf(df.format((double) enemy.getCurrentHp() / enemy.getTargetHp() * 100) + "%"));
        }
        team.setTotalDamage(totalDamage);
        totalDamageValue.setText(" " + String.valueOf(totalDamage) + " ");
        hpRecoveredValue.setText(" " + String.valueOf((int) DamageCalculationUtil.hpRecovered(team, totalCombos)) + " ");
        totalComboValue.setText(String.valueOf(totalCombos));
        if (totalDamage < 0) {
            totalDamageValue.setTextColor(Color.parseColor("#FFBBBB"));
        } else {
            totalDamageValue.setTextColor(Color.parseColor("#BBBBBB"));
        }
        if(DamageCalculationUtil.hpRecovered(team, totalCombos) < 0){
            hpRecoveredValue.setTextColor(Color.parseColor("#c737fd"));
        } else {
            hpRecoveredValue.setTextColor(Color.parseColor("#FF9999"));
        }
    }

    private View.OnClickListener bindMonsterOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            clearTextFocus();
            if (team.getIsBound().get(position)) {
                team.getIsBound().set(position, false);
                Log.d("Bound", "monster " + position + " is unbound");
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Monster unbound", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                int counter = 0;
                if (team.hasAwakenings()) {
                    for (int i = 0; i < team.getMonsters(position).getCurrentAwakenings(); i++) {
                        if (team.getMonsters(position).getAwokenSkills().get(i) == 10) {
                            counter++;
                        }
                    }
                }
                if (counter == 2) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Monster cannot be bound", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    team.getIsBound().set(position, true);
                    Log.d("Bound", "monster " + position + " is bound");
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Monster bound", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            team.update();
            team.updateOrbs();
            Log.d("Orb Plus Awakenings", "" + team.getOrbPlusAwakenings(Element.LIGHT));
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
        }
    };

//    private ListView.OnItemClickListener bindMonsterOnClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            clearTextFocus();
//            if (team.getIsBound().get(position)) {
//                team.getIsBound().set(position, false);
//                Log.d("Bound", "monster " + position + " is unbound");
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Monster unbound", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                int counter = 0;
//                if (team.hasAwakenings()) {
//                    for (int i = 0; i < team.getMonsters(position).getCurrentAwakenings(); i++) {
//                        if (team.getMonsters(position).getAwokenSkills().get(i) == 10) {
//                            counter++;
//                        }
//                    }
//                }
//                if (counter == 2) {
//                    if (toast != null) {
//                        toast.cancel();
//                    }
//                    toast = Toast.makeText(getActivity(), "Monster cannot be bound", Toast.LENGTH_SHORT);
//                    toast.show();
//                } else {
//                    team.getIsBound().set(position, true);
//                    Log.d("Bound", "monster " + position + " is bound");
//                    if (toast != null) {
//                        toast.cancel();
//                    }
//                    toast = Toast.makeText(getActivity(), "Monster bound", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
//            }
//            team.update();
//            team.updateOrbs();
//            Log.d("Orb Plus Awakenings", "" + team.getOrbPlusAwakenings(Element.LIGHT));
//            updateTextView();
//            monsterListAdapter.notifyDataSetChanged();
//        }
//    };

    private void setTextColors() {
        if (enemy.getTargetElement().equals(Element.RED)) {
            enemyHPValue.setTextColor(Color.parseColor("#FF0000"));
        } else if (enemy.getTargetElement().equals(Element.BLUE)) {
            enemyHPValue.setTextColor(Color.parseColor("#4444FF"));
        } else if (enemy.getTargetElement().equals(Element.GREEN)) {
            enemyHPValue.setTextColor(Color.parseColor("#00CC00"));
        } else if (enemy.getTargetElement().equals(Element.LIGHT)) {
            enemyHPValue.setTextColor(Color.parseColor("#F0F000"));
        } else if (enemy.getTargetElement().equals(Element.DARK)) {
            enemyHPValue.setTextColor(Color.parseColor("#AA00FF"));
        }
    }

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.ADDITIONAL_COMBOS) {
                additionalCombosFragment = statValue;
            } else if (statToChange == MyTextWatcher.DAMAGE_THRESHOLD) {
                enemy.setDamageThreshold(statValue);
            } else if (statToChange == MyTextWatcher.DAMAGE_IMMUNITY) {
                enemy.setDamageImmunity(statValue);
            } else if (statToChange == MyTextWatcher.REDUCTION_VALUE) {
                enemy.setReductionValue(statValue);
                if(statValue > 100){
                    enemy.setReductionValue(100);
                    reductionValue.setText("100");
                }
            }
        }
    };

    private MyTextWatcher additionalComboTextWatcher = new MyTextWatcher(MyTextWatcher.ADDITIONAL_COMBOS, changeStats);
    private MyTextWatcher damageThresholdWatcher = new MyTextWatcher(MyTextWatcher.DAMAGE_THRESHOLD, changeStats);
    private MyTextWatcher damageImmunityWatcher = new MyTextWatcher(MyTextWatcher.DAMAGE_IMMUNITY, changeStats);
    private MyTextWatcher reductionWatcher = new MyTextWatcher(MyTextWatcher.REDUCTION_VALUE, changeStats);

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

    private Button.OnClickListener optionsButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clearTextFocus();
            if (extraMultiplierDialogFragment == null) {
                extraMultiplierDialogFragment = extraMultiplierDialogFragment.newInstance(saveTeam, team);
            }
            extraMultiplierDialogFragment.show(getActivity().getSupportFragmentManager(), team, "Show extra multiplier Dialog");
        }
    };

    ExtraMultiplierDialogFragment.SaveTeam saveTeam = new ExtraMultiplierDialogFragment.SaveTeam() {
        @Override
        public void update() {
            //Update shit into Team.
        }
    };

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void setReductionOrbs() {
        reductionValue.setText(String.valueOf(enemy.getReductionValue()));
        if (enemy.getHasReduction()) {
            reductionCheck.setChecked(true);
            reductionValue.setEnabled(true);
            for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                reductionRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.containsReduction(Element.RED)) {
                redOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(Element.BLUE)) {
                blueOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(Element.GREEN)) {
                greenOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(Element.DARK)) {
                darkOrbReduction.setChecked(true);
            }
            if (enemy.containsReduction(Element.LIGHT)) {
                lightOrbReduction.setChecked(true);
            }
        } else {
            reductionValue.setEnabled(false);
            reductionCheck.setChecked(false);
        }

    }

    private void setAbsorbOrbs() {
        absorbCheck.setChecked(enemy.getHasAbsorb());
        if (enemy.getHasAbsorb()) {
            for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                absorbRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.getAbsorb() == Element.RED) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.redOrbAbsorb);
            } else if (enemy.getAbsorb() == Element.BLUE) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.blueOrbAbsorb);
            } else if (enemy.getAbsorb() == Element.GREEN) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.greenOrbAbsorb);
            } else if (enemy.getAbsorb() == Element.LIGHT) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.lightOrbAbsorb);
            } else if (enemy.getAbsorb() == Element.DARK) {
                Log.d("enemyAbsorb", "" + enemy.getAbsorb());
                absorbRadioGroup.check(R.id.darkOrbAbsorb);
            }
        }

    }

    private void setDamageThreshold() {
        damageThresholdValue.setText(String.valueOf(enemy.getDamageThreshold()));
        damageImmunityValue.setText(String.valueOf(enemy.getDamageImmunity()));
        if (enemy.getHasDamageThreshold()) {
            damageThresholdValue.setEnabled(true);
            damageThresholdCheck.setChecked(true);
        }
        if (enemy.hasDamageImmunity()) {
            damageImmunityCheck.setChecked(true);
            damageImmunityValue.setEnabled(true);
        }
    }

    private CompoundButton.OnCheckedChangeListener checkBoxOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            clearTextFocus();
            if (buttonView.equals(damageThresholdCheck)) {
                enemy.setHasDamageThreshold(isChecked);
                if (isChecked) {
                    damageThresholdValue.setEnabled(true);
                    damageImmunityValue.clearFocus();
                    damageImmunityValue.setEnabled(false);
                    damageImmunityCheck.setChecked(false);
                } else {
                    damageThresholdValue.clearFocus();
                    damageThresholdValue.setEnabled(false);
                }
            } else if (buttonView.equals(damageImmunityCheck)){
                enemy.setHasDamageImmunity(isChecked);
                Log.d("TeamDamageListTag", "Damage Immunity is: " + enemy.hasDamageImmunity());
                if (isChecked) {
                    damageImmunityValue.setEnabled(true);
                    damageThresholdValue.clearFocus();
                    damageThresholdValue.setEnabled(false);
                    damageThresholdCheck.setChecked(false);
                } else {
                    damageImmunityValue.clearFocus();
                    damageImmunityValue.setEnabled(false);
                }

            } else if (buttonView.equals(reductionCheck)) {
                enemy.setHasReduction(isChecked);
                if (isChecked) {
                    for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                        reductionRadioGroup.getChildAt(i).setEnabled(true);
                    }
                    reductionValue.setEnabled(true);
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
                    reductionValue.setEnabled(false);
                }
            } else if (buttonView.equals(absorbCheck)) {
                enemy.setHasAbsorb(isChecked);
                if (isChecked) {
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(true);
                    }
                } else {
                    absorbRadioGroup.clearCheck();
                    enemy.setAbsorb(Element.BLANK);
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(false);
                    }
                }
            } else if (buttonView.equals(hasAwakeningsCheck)) {
                Log.d("hasAwakenings1", "" + team.hasAwakenings());
                team.setHasAwakenings(isChecked);
                team.update();
                Log.d("hasAwakenings2", "" + team.hasAwakenings());
                Log.d("Orb Plus Awakenings", "" + team.getOrbPlusAwakenings(Element.LIGHT));
            } else if (buttonView.equals(activeUsedCheck)) {
                Log.d("Team Damage List Log", "Active Skill Used Before: " + team.isActiveSkillUsed());
                team.isActiveSkillUsed(isChecked);
                Log.d("Team Damage List Log", "Active Skill Used After: " + team.isActiveSkillUsed());
                team.update();
            }
//            updateTextView();
//            monsterListAdapter.notifyDataSetChanged();
        }
    };

    private CompoundButton.OnCheckedChangeListener reductionCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setElementReduction(isChecked, buttonView.getId());
        }
    };

    private void setElementReduction(boolean isChecked, int buttonId) {
        clearTextFocus();
        Element element = null;
        switch (buttonId) {
            case R.id.redOrbReduction:
                element = Element.RED;
                break;
            case R.id.blueOrbReduction:
                element = Element.BLUE;
                break;
            case R.id.greenOrbReduction:
                element = Element.GREEN;
                break;
            case R.id.darkOrbReduction:
                element = Element.DARK;
                break;
            case R.id.lightOrbReduction:
                element = Element.LIGHT;
                break;
        }

        if (isChecked) {
            if (!enemy.containsReduction(element)) {
                enemy.addReduction(element);
            }
        } else {
            if (enemy.containsReduction(element)) {
                enemy.removeReduction(element);
            }
        }
//        updateTextView();
//        monsterListAdapter.notifyDataSetChanged();
    }

    private void setCheckBoxes() {
        hasAwakeningsCheck.setChecked(team.hasAwakenings());
        activeUsedCheck.setChecked(team.isActiveSkillUsed());
    }

    private RadioGroup.OnCheckedChangeListener absorbOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            clearTextFocus();
            int radioChecked = group.getCheckedRadioButtonId();
            switch (radioChecked) {
                case R.id.redOrbAbsorb:
                    enemy.setAbsorb(Element.RED);
                    break;
                case R.id.blueOrbAbsorb:
                    enemy.setAbsorb(Element.BLUE);
                    break;
                case R.id.greenOrbAbsorb:
                    enemy.setAbsorb(Element.GREEN);
                    break;
                case R.id.lightOrbAbsorb:
                    enemy.setAbsorb(Element.LIGHT);
                    break;
                case R.id.darkOrbAbsorb:
                    enemy.setAbsorb(Element.DARK);
                    break;
            }
//            updateTextView();
//            monsterListAdapter.notifyDataSetChanged();
            Log.d("absorb", "" + enemy.getAbsorb());
        }
    };

    private void setupHpSeekBar() {
        int position = 0;
        if(team.getLeadSkill().getHpPercent().isEmpty()){
            if (team.getHelperSkill().getHpPercent().isEmpty()){
                team.setTeamHp(100);
            }else {
                for(int i = 0; i < team.getHelperSkill().getAtkData().size(); i++){
                    if (i == 0){
                        position = i;
                    }else {
                        if (team.getHelperSkill().getAtkData().get(i) > team.getHelperSkill().getAtkData().get(i-1)){
                            position = i;
                        }
                    }
                }
                team.setTeamHp(team.getHelperSkill().getHpPercent().get(0 + 2*position));
            }
        } else {
            for(int i = 0; i < team.getLeadSkill().getAtkData().size(); i++){
                if (i == 0){
                    position = i;
                }else {
                    if (team.getLeadSkill().getAtkData().get(i) > team.getLeadSkill().getAtkData().get(i-1)){
                        position = i;
                    }
                }
            }
            team.setTeamHp(team.getLeadSkill().getHpPercent().get(0 + 2*position));
        }
        team.save();
        teamHp.setProgress(team.getTeamHp());
        updateTextView();
        teamHpValue.setText("" + teamHp.getProgress());
    }

    private SeekBar.OnSeekBarChangeListener teamHpSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                team.setTeamHp(progress);
            }
            teamHpValue.setText("" + progress);
            team.save();
            Log.d("Team Damage Log", "hp is: " + team.getTeamHp());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d("Team Damage Log", "I am stop tracking touch");
//            team.update();
            updateTextView();
            monsterListAdapter.notifyDataSetChanged();
        }
    };

    private void clearTextFocus() {
        additionalComboValue.clearFocus();
        damageThresholdValue.clearFocus();
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
}
