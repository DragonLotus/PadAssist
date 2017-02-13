package com.padassist.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.GravityButtonAdapter;
import com.padassist.Adapters.GravityListAdapter;
import com.padassist.Adapters.TypeSpinnerAdapter;
import com.padassist.BroadcastReceivers.JustAnotherBroadcastReceiver;
import com.padassist.Data.BaseMonster;
import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.RealmElement;
import com.padassist.Data.RealmInt;
import com.padassist.Data.Team;
import com.padassist.Graphics.TooltipText;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.InflaterInputStream;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnemyTargetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnemyTargetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EnemyTargetFragment extends Fragment {
    public static final String TAG = EnemyTargetFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //    private Team team;
    private EditText targetHpValue, currentHpValue, targetDefenseValue, damageThresholdValue,
            damageImmunityValue, reductionValue, enemyNameEditText;
    private ImageView targetAbsorb, targetReduction, damageThreshold, damageImmunity,
            defenseBreakIcon, monsterPicture;
    private TextView percentHpValue;
    private TextView totalGravityValue, enemyName;
    private RadioGroup orbRadioGroup1, orbRadioGroup2, absorbRadioGroup, reductionRadioGroup;
    private RadioButton redOrb1, blueOrb1, greenOrb1, lightOrb1, darkOrb1;
    private RadioButton redOrb2, blueOrb2, greenOrb2, lightOrb2, darkOrb2;
    private Button gravityShowHideButton, clearButton, hpReset, calculate;
    private LinearLayout gravityHolder;
    private GravityListAdapter gravityListAdapter;
    private GravityButtonAdapter gravityButtonAdapter;
    private ListView gravityList;
    private GridView gravityButtonList;
    private Enemy enemy;
    private DecimalFormat df = new DecimalFormat("#.##");
    private OnFragmentInteractionListener mListener;
    private Toast toast;
    private Spinner defenseBreakSpinner, type1Spinner, type2Spinner, type3Spinner;
    private String[] defenseBreakItems;
    private TypeSpinnerAdapter typeSpinnerAdapter;
    private ArrayList<Integer> typeItems;
    private CheckBox absorbCheck, reductionCheck, damageThresholdCheck, redOrbReduction,
            blueOrbReduction, greenOrbReduction, lightOrbReduction, darkOrbReduction, redOrbAbsorb,
            blueOrbAbsorb, greenOrbAbsorb, lightOrbAbsorb, darkOrbAbsorb, damageImmunityCheck;
    //            redOrb2, blueOrb2, greenOrb2, lightOrb2, darkOrb2;
    private double defenseBreakValue = 1.0;
    private int defenseBeforeBreak;
    private long hpBeforeGravity;
    private int element2Selection = -1;
    private long targetHp;
    private long currentHp;
    private Realm realm;
    private JustAnotherBroadcastReceiver broadcastReceiver;
    private Parcelable enemyParcel;
    private GravityListAdapter.UpdateGravityPercent updateGravityPercent = new GravityListAdapter.UpdateGravityPercent() {
        @Override
        public void updatePercent() {
            double gravity = 1.0;
            for (int i = 0; i < gravityListAdapter.getCount(); i++) {
                gravity *= 1 - gravityListAdapter.getItem(i) / 100.0;
            }

            totalGravityValue.setText(Math.round((1 - gravity) * 100) + "%");
            enemy.setGravityPercent(gravity);
        }
    };
    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.TARGET_HP) {
                targetHp = statValue;
            } else if (statToChange == MyTextWatcher.CURRENT_HP) {
                currentHp = statValue;
                if (currentHp > targetHp) {
                    currentHpValue.setText(String.valueOf(targetHp));
//                    enemy.setBeforeGravityHP(enemy.getTargetHp());
                }
//                if (enemy.getBeforeGravityHP() * enemy.getGravityPercent() < 1 && enemy.getCurrentHp() == 0 && enemy.getBeforeGravityHP() != 0) {
//                    enemy.setCurrentHp(1);
//                    currentHpValue.setText("1");
//                }
            } else if (statToChange == MyTextWatcher.TARGET_DEFENSE) {
                enemy.setTargetDef(statValue);
            } else if (statToChange == MyTextWatcher.DAMAGE_THRESHOLD) {
                enemy.setDamageThreshold(statValue);
            } else if (statToChange == MyTextWatcher.REDUCTION_VALUE) {
//                enemy.setReductionValue(statValue);
                if (statValue > 100) {
//                    enemy.setReductionValue(100);
                    reductionValue.setText("100");
                }
            } else if (statToChange == MyTextWatcher.DAMAGE_IMMUNITY) {
                enemy.setDamageImmunity(statValue);
            }
            percentHpValue.setText(df.format(enemy.getPercentHp() * 100) + "%");

        }
    };

    private MyTextWatcher targetHPWatcher = new MyTextWatcher(MyTextWatcher.TARGET_HP, changeStats);
    private MyTextWatcher currentHPWatcher = new MyTextWatcher(MyTextWatcher.CURRENT_HP, changeStats);
    private MyTextWatcher targetDefenseWatcher = new MyTextWatcher(MyTextWatcher.TARGET_DEFENSE, changeStats);
    private MyTextWatcher damageThresholdWatcher = new MyTextWatcher(MyTextWatcher.DAMAGE_THRESHOLD, changeStats);
    private MyTextWatcher reductionValueWatcher = new MyTextWatcher(MyTextWatcher.REDUCTION_VALUE, changeStats);
    private MyTextWatcher damageImmunityWatcher = new MyTextWatcher(MyTextWatcher.DAMAGE_IMMUNITY, changeStats);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EnemyTargetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EnemyTargetFragment newInstance(int additionalCombos, Parcelable team, Parcelable enemy) {
        EnemyTargetFragment fragment = new EnemyTargetFragment();
        Bundle args = new Bundle();
        args.putInt("additionalCombos", additionalCombos);
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public static EnemyTargetFragment newInstance(Parcelable enemy) {
        EnemyTargetFragment fragment = new EnemyTargetFragment();
        Bundle args = new Bundle();
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public EnemyTargetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.searchGroup, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.monsterList:
//                updateMonster();
//                Parcelable enemyParcel = Parcels.wrap(enemy);
                ((MainActivity) getActivity()).switchFragment(EnemyListFragment.newInstance(enemyParcel), EnemyListFragment.TAG, "good");
                break;
            case R.id.addMonster:
                RealmResults<Enemy> realmResults = realm.where(Enemy.class).findAllSorted("enemyId");
                Enemy addEnemy = realm.copyFromRealm(enemy);
//                addEnemy.setOverwriteEnemyId(realmResults.get(realmResults.size() - 1).getEnemyId() + 1);
                addEnemy.setEnemyId(realmResults.get(realmResults.size() - 1).getEnemyId() + 1);
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(addEnemy);
                enemy.setOverwriteEnemyId(addEnemy.getEnemyId());
                realm.commitTransaction();
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Enemy added", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("EnemyTargetFrag", "addEnemy enemy is: " + enemy);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enemy_target, container, false);
        targetHpValue = (EditText) rootView.findViewById(R.id.targetHPValue);
        currentHpValue = (EditText) rootView.findViewById(R.id.currentHPValue);
        targetDefenseValue = (EditText) rootView.findViewById(R.id.targetDefenseValue);
        damageThresholdValue = (EditText) rootView.findViewById(R.id.damageThresholdValue);
        percentHpValue = (TextView) rootView.findViewById(R.id.percentHPValue);
        orbRadioGroup1 = (RadioGroup) rootView.findViewById(R.id.element1RadioGroup);
        orbRadioGroup2 = (RadioGroup) rootView.findViewById(R.id.element2RadioGroup);
        gravityList = (ListView) rootView.findViewById(R.id.gravityList);
        totalGravityValue = (TextView) rootView.findViewById(R.id.totalGravityValue);
        gravityButtonList = (GridView) rootView.findViewById(R.id.gravityButtonGrid);
        clearButton = (Button) rootView.findViewById(R.id.clearButton);
        gravityShowHideButton = (Button) rootView.findViewById(R.id.gravityShowHide);
        gravityHolder = (LinearLayout) rootView.findViewById(R.id.gravityHolder);
        hpReset = (Button) rootView.findViewById(R.id.hpReset);
//        calculate = (Button) rootView.findViewById(R.id.calculate);
        defenseBreakSpinner = (Spinner) rootView.findViewById(R.id.defenseBreakSpinner);
        absorbRadioGroup = (RadioGroup) rootView.findViewById(R.id.absorbOrbRadioGroup);
        reductionRadioGroup = (RadioGroup) rootView.findViewById(R.id.reductionOrbRadioGroup);
        absorbCheck = (CheckBox) rootView.findViewById(R.id.absorbCheck);
        reductionCheck = (CheckBox) rootView.findViewById(R.id.reductionCheck);
        damageThresholdCheck = (CheckBox) rootView.findViewById(R.id.damageThresholdCheck);
        redOrbReduction = (CheckBox) rootView.findViewById(R.id.redOrbReduction);
        blueOrbReduction = (CheckBox) rootView.findViewById(R.id.blueOrbReduction);
        greenOrbReduction = (CheckBox) rootView.findViewById(R.id.greenOrbReduction);
        lightOrbReduction = (CheckBox) rootView.findViewById(R.id.lightOrbReduction);
        darkOrbReduction = (CheckBox) rootView.findViewById(R.id.darkOrbReduction);
        redOrbAbsorb = (CheckBox) rootView.findViewById(R.id.redOrbAbsorb);
        blueOrbAbsorb = (CheckBox) rootView.findViewById(R.id.blueOrbAbsorb);
        greenOrbAbsorb = (CheckBox) rootView.findViewById(R.id.greenOrbAbsorb);
        lightOrbAbsorb = (CheckBox) rootView.findViewById(R.id.lightOrbAbsorb);
        darkOrbAbsorb = (CheckBox) rootView.findViewById(R.id.darkOrbAbsorb);
        redOrb1 = (RadioButton) rootView.findViewById(R.id.redOrb1);
        blueOrb1 = (RadioButton) rootView.findViewById(R.id.blueOrb1);
        greenOrb1 = (RadioButton) rootView.findViewById(R.id.greenOrb1);
        lightOrb1 = (RadioButton) rootView.findViewById(R.id.lightOrb1);
        darkOrb1 = (RadioButton) rootView.findViewById(R.id.darkOrb1);
        redOrb2 = (RadioButton) rootView.findViewById(R.id.redOrb2);
        blueOrb2 = (RadioButton) rootView.findViewById(R.id.blueOrb2);
        greenOrb2 = (RadioButton) rootView.findViewById(R.id.greenOrb2);
        lightOrb2 = (RadioButton) rootView.findViewById(R.id.lightOrb2);
        darkOrb2 = (RadioButton) rootView.findViewById(R.id.darkOrb2);
        type1Spinner = (Spinner) rootView.findViewById(R.id.type1Spinner);
        type2Spinner = (Spinner) rootView.findViewById(R.id.type2Spinner);
        type3Spinner = (Spinner) rootView.findViewById(R.id.type3Spinner);
        damageImmunityValue = (EditText) rootView.findViewById(R.id.damageImmunityValue);
        damageImmunityCheck = (CheckBox) rootView.findViewById(R.id.damageImmunityCheck);
        reductionValue = (EditText) rootView.findViewById(R.id.reductionValue);
        targetAbsorb = (ImageView) rootView.findViewById(R.id.elementAbsorb);
        targetReduction = (ImageView) rootView.findViewById(R.id.elementReduction);
        damageThreshold = (ImageView) rootView.findViewById(R.id.damageThreshold);
        damageImmunity = (ImageView) rootView.findViewById(R.id.damageImmunity);
        defenseBreakIcon = (ImageView) rootView.findViewById(R.id.spinnerIcon);
        monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
        enemyName = (TextView) rootView.findViewById(R.id.enemyName);
        enemyNameEditText = (EditText) rootView.findViewById(R.id.enemyNameEditText);
        return rootView;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
//            team = Parcels.unwrap(getArguments().getParcelable("team"));
//            enemy = Parcels.unwrap(getArguments().getParcelable("enemy"));
        }
        realm = Realm.getDefaultInstance();

        enemy = realm.where(Enemy.class).equalTo("enemyId", 0).findFirst();

        enemyName.setSelected(true);
        enemyName.setHorizontallyScrolling(true);

        clearButton.setOnClickListener(clearButtonOnClickListener);
        gravityShowHideButton.setOnClickListener(gravityShowHideOnClickListener);
        hpReset.setOnClickListener(hpResetOnClickListener);

        defenseBreakItems = new String[]{"0%", "25%", "50%", "75%", "100%"};
        ArrayAdapter<String> defenseBreakAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, defenseBreakItems);
        defenseBreakSpinner.setAdapter(defenseBreakAdapter);
        defenseBreakSpinner.setOnItemSelectedListener(defenseBreakOnItemSelectedListener);

        typeItems = new ArrayList<>();

        typeItems.add(-1);
        typeItems.add(0);
        typeItems.add(1);
        typeItems.add(2);
        typeItems.add(3);
        typeItems.add(4);
        typeItems.add(5);
        typeItems.add(6);
        typeItems.add(7);
        typeItems.add(8);
        typeItems.add(12);
        typeItems.add(14);
        typeItems.add(15);

        typeSpinnerAdapter = new TypeSpinnerAdapter(getActivity(), R.layout.type_spinner_row, typeItems);
//        typeSpinnerAdapter.setDropDownViewResource(R.layout.type_spinner_dropdown_row);
        type1Spinner.setAdapter(typeSpinnerAdapter);
        type2Spinner.setAdapter(typeSpinnerAdapter);
        type3Spinner.setAdapter(typeSpinnerAdapter);


        gravityList.setOnTouchListener(listViewScroll);
        gravityButtonList.setOnTouchListener(listViewScroll);

//        calculate.setOnClickListener(calculateOnClickListener);

        targetAbsorb.setOnClickListener(tooltipOnClickListener);
        targetReduction.setOnClickListener(tooltipOnClickListener);
        damageThreshold.setOnClickListener(tooltipOnClickListener);
        damageImmunity.setOnClickListener(tooltipOnClickListener);
        defenseBreakIcon.setOnClickListener(tooltipOnClickListener);

        monsterPicture.setOnClickListener(enemyPortraitOnClickListener);

//        orbRadioGroup1.setOnCheckedChangeListener(enemyElement1OnCheckedChangeListener);

        targetHpValue.setOnFocusChangeListener(editTextOnFocusChange);
        currentHpValue.setOnFocusChangeListener(editTextOnFocusChange);
        targetDefenseValue.setOnFocusChangeListener(editTextOnFocusChange);

        targetHpValue.addTextChangedListener(targetHPWatcher);
        currentHpValue.addTextChangedListener(currentHPWatcher);
//        targetDefenseValue.addTextChangedListener(targetDefenseWatcher);
//
        reductionValue.addTextChangedListener(reductionValueWatcher);
        reductionValue.setOnFocusChangeListener(editTextOnFocusChange);
        absorbCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
        reductionCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);

        damageThresholdCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);
//        damageThresholdValue.addTextChangedListener(damageThresholdWatcher);
        damageThresholdValue.setOnFocusChangeListener(editTextOnFocusChange);
//        damageImmunityValue.addTextChangedListener(damageImmunityWatcher);
        damageImmunityValue.setOnFocusChangeListener(editTextOnFocusChange);
        damageImmunityCheck.setOnCheckedChangeListener(checkBoxOnChangeListener);

//        redOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
//        blueOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
//        greenOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
//        darkOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
//        lightOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);

//        redOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
//        blueOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
//        greenOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
//        darkOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
//        lightOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);

        redOrb2.setOnClickListener(element2OnClickListener);
        blueOrb2.setOnClickListener(element2OnClickListener);
        greenOrb2.setOnClickListener(element2OnClickListener);
        darkOrb2.setOnClickListener(element2OnClickListener);
        lightOrb2.setOnClickListener(element2OnClickListener);

        enemyName.setOnClickListener(enemyNameOnClickListener);
        enemyNameEditText.setOnFocusChangeListener(enemyNameEditTextOnFocusChangeListener);
        enemyNameEditText.addTextChangedListener(enemyNameEditTextWatcher);

//        type1Spinner.setOnItemSelectedListener(typeOnItemSelectedListener);
//        type2Spinner.setOnItemSelectedListener(typeOnItemSelectedListener);
//        type3Spinner.setOnItemSelectedListener(typeOnItemSelectedListener);

        onSelect();

        Parcelable enemyParcel = Parcels.wrap(enemy);
//        gravityListAdapter = new GravityListAdapter(getActivity(), R.layout.gravity_list_row, enemyParcel, updateGravityPercent, enemy.getGravityArrayList());
//        gravityList.setAdapter(gravityListAdapter);
//        gravityList.setOnItemClickListener(gravityRemoveOnClickListener);
//        gravityButtonAdapter = new GravityButtonAdapter(getActivity(), R.layout.gravity_button_grid, new ArrayList<Integer>());
//        gravityButtonList.setAdapter(gravityButtonAdapter);
//        gravityButtonList.setOnItemClickListener(gravityButtonOnClickListener);
//        gravityButtonInit();

//        updateGravityPercent.updatePercent();


        setReductionOrbs();
        setAbsorbOrbs();
        setDamageThreshold();
        setEnemyElement();
        setSpinner();
        targetHp = enemy.getTargetHp();
        currentHp = enemy.getCurrentHp();
        defenseBeforeBreak = enemy.getBeforeDefenseBreak();
        enemyName.setText(enemy.getEnemyName());
        enemyNameEditText.setText(enemy.getEnemyName());
        monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(enemy.getMonsterIdPicture()));
//            currentHpValue.setText(String.valueOf((int) (enemy.getBeforeGravityHP() * enemy.getGravityPercent())));
        currentHpValue.setText(String.valueOf(currentHp));
        targetHpValue.setText(String.valueOf(targetHp));
//            totalGravityValue.setText();
        targetDefenseValue.setText(String.valueOf(enemy.getTargetDef()));
        reductionValue.setText(String.valueOf(enemy.getReductionValue()));
    }

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDeselect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
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

    private View.OnClickListener enemyNameOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            enemyNameEditText.setVisibility(View.VISIBLE);
            enemyNameEditText.requestFocus();
//            enemyNameEditText.setText(enemy.getEnemyName());
            enemyName.setVisibility(View.INVISIBLE);
            InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(enemyNameEditText, 0);
            Log.d("EnemyTargetFrag", "Enemy name on click is: " + enemyNameEditText.getText());
        }
    };

    private View.OnFocusChangeListener enemyNameEditTextOnFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (!b) {
                hideKeyboard(view);
                enemyName.setVisibility(View.VISIBLE);
                enemyName.setText(enemy.getEnemyName());
                enemyNameEditText.setText(enemy.getEnemyName());
                enemyNameEditText.setVisibility(View.INVISIBLE);
            }
        }
    };

    private TextWatcher enemyNameEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.toString().equals("")) {
                enemy.setEnemyName((realm.where(BaseMonster.class).equalTo("monsterId", enemy.getMonsterIdPicture()).findFirst()).getName());
            } else {
//                enemy.setEnemyName(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private View.OnClickListener enemyPortraitOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            updateMonster();
            ((MainActivity) getActivity()).switchFragment(MonsterPortraitListFragment.newInstance(enemyParcel), EnemyListFragment.TAG, "good");
        }
    };

    private GridView.OnItemClickListener gravityButtonOnClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            if (enemy.getBeforeGravityHP() * enemy.getGravityPercent() < 1 && enemy.getCurrentHp() == 1 || enemy.getCurrentHp() == 0 || enemy.getTargetHp() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Additional gravities will have no effect", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                clearTextFocus();
                gravityListAdapter.add(gravityButtonAdapter.getItem(position));
                gravityListAdapter.notifyDataSetChanged();
                gravityList.smoothScrollToPosition(gravityListAdapter.getCount() - 1);
                enemy.setCurrentHp((int) (enemy.getBeforeGravityHP() * enemy.getGravityPercent()));
                currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));
            }

        }
    };

    private void gravityButtonInit() {
        int[] gravityInts = {10, 15, 20, 25, 30, 35, 45};
        for (int i = 0; i < gravityInts.length; i++) {
            gravityButtonAdapter.add(gravityInts[i]);
        }
    }

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
                if (targetDefenseValue.getText().toString().equals("")) {
//                    targetDefenseValue.setText("0");
                } else if (damageThresholdValue.getText().toString().equals("")) {
//                    damageThresholdValue.setText("0");
                }

                if (v.equals(targetHpValue)) {
                    if (targetHpValue.getText().toString().equals("")) {
                        targetHpValue.setText(String.valueOf(targetHp));
                        percentHpValue.setText(String.valueOf(getPercentHp()));
                    }
                    currentHpValue.setText(String.valueOf(currentHp));
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Current HP set", Toast.LENGTH_LONG);
                    toast.show();
                } else if (v.equals(currentHpValue)) {
                    if (currentHpValue.getText().toString().equals("")) {
                        currentHpValue.setText(String.valueOf(targetHp));
                        percentHpValue.setText(String.valueOf(enemy.getPercentHp()));
                    }
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Initial HP set", Toast.LENGTH_LONG);
                    toast.show();
                } else if (v.equals(targetDefenseValue)) {
                    defenseBeforeBreak = Integer.valueOf(targetDefenseValue.getText().toString());
                    targetDefenseValue.setText(String.valueOf((int) (defenseBeforeBreak * defenseBreakValue)));
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Initial defense set", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }
    };

    private ListView.OnItemClickListener gravityRemoveOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            clearTextFocus();
            gravityListAdapter.remove(gravityListAdapter.getItem(position));
            enemy.setCurrentHp((int) (enemy.getBeforeGravityHP() * enemy.getGravityPercent()));
            currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));
        }
    };

    private Button.OnClickListener clearButtonOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (enemy.getCurrentHp() == 0) {
            } else {
                clearTextFocus();
                gravityListAdapter.clear();
                enemy.setCurrentHp((int) (enemy.getBeforeGravityHP() * enemy.getGravityPercent()));
                currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Gravities cleared", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

    private Button.OnClickListener gravityShowHideOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (gravityHolder.getVisibility() == View.GONE) {
                gravityHolder.setVisibility(View.VISIBLE);
                gravityShowHideButton.setText("Hide");
            } else if (gravityHolder.getVisibility() == View.VISIBLE) {
                gravityHolder.setVisibility(View.GONE);
                gravityShowHideButton.setText("Show");
            }
        }
    };

    private Spinner.OnItemSelectedListener defenseBreakOnItemSelectedListener = new Spinner.OnItemSelectedListener() {
        //Don't forget to set enemy Target Defense when calculating
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            clearTextFocus();
            if (position == 0) {
                defenseBreakValue = 1.0;
            } else if (position == 1) {
                defenseBreakValue = .75;
            } else if (position == 2) {
                defenseBreakValue = .50;
            } else if (position == 3) {
                defenseBreakValue = .25;
            } else if (position == 4) {
                defenseBreakValue = 0;
            }
            targetDefenseValue.setText(String.valueOf((int) (defenseBeforeBreak * defenseBreakValue)));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private Button.OnClickListener hpResetOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
//            gravityListAdapter.clear();
            currentHpValue.setText(String.valueOf(targetHp));
//            enemy.setBeforeGravityHP(enemy.getCurrentHp());
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "HP reset to 100% and gravities cleared", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private CompoundButton.OnCheckedChangeListener checkBoxOnChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            clearTextFocus();
            if (buttonView.equals(absorbCheck)) {
//                enemy.setHasAbsorb(isChecked);
//                setAbsorbOrbs();
                if (!isChecked) {
                    redOrbAbsorb.setChecked(false);
                    blueOrbAbsorb.setChecked(false);
                    greenOrbAbsorb.setChecked(false);
                    lightOrbAbsorb.setChecked(false);
                    darkOrbAbsorb.setChecked(false);
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(false);
                    }
                } else {
                    for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                        absorbRadioGroup.getChildAt(i).setEnabled(true);
                    }
                }
            } else if (buttonView.equals(reductionCheck)) {
//                enemy.setHasReduction(isChecked);
//                setReductionOrbs();
                if (!isChecked) {
                    redOrbReduction.setChecked(false);
                    blueOrbReduction.setChecked(false);
                    greenOrbReduction.setChecked(false);
                    lightOrbReduction.setChecked(false);
                    darkOrbReduction.setChecked(false);
                    for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                        reductionRadioGroup.getChildAt(i).setEnabled(false);
                    }
                    reductionValue.setEnabled(false);
                } else {
                    for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                        reductionRadioGroup.getChildAt(i).setEnabled(true);
                    }
                    reductionValue.setEnabled(true);
                }
            } else if (buttonView.equals(damageThresholdCheck)) {
//                enemy.setHasDamageThreshold(isChecked);
                if (isChecked) {
                    damageThresholdValue.setEnabled(true);
                    damageImmunityValue.setEnabled(false);
                    damageImmunityCheck.setChecked(false);
                } else {
                    damageThresholdValue.setEnabled(false);
                }
            } else if (buttonView.equals(damageImmunityCheck)) {
//                enemy.setHasDamageImmunity(isChecked);
                if (isChecked) {
                    damageImmunityValue.setEnabled(true);
                    damageThresholdValue.setEnabled(false);
                    damageThresholdCheck.setChecked(false);
                } else {
                    damageImmunityValue.setEnabled(false);
                }
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener enemyElement1OnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            clearTextFocus();
            int radioChecked = group.getCheckedRadioButtonId();
            switch (radioChecked) {
                case R.id.redOrb1:
                    enemy.setTargetElement1(0);
                    break;
                case R.id.blueOrb1:
                    enemy.setTargetElement1(1);
                    break;
                case R.id.greenOrb1:
                    enemy.setTargetElement1(2);
                    break;
                case R.id.lightOrb1:
                    enemy.setTargetElement1(3);
                    break;
                case R.id.darkOrb1:
                    enemy.setTargetElement1(4);
                    break;
            }
        }
    };

//    private View.OnClickListener element2OnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()){
//                case R.id.redOrb2:
//                    if(enemy.getTargetElement().get(1).getValue() == 0){
//                        enemy.setTargetElement2(-1);
//                        orbRadioGroup2.clearCheck();
//                    } else {
//                        enemy.setTargetElement2(0);
//                    }
//                    break;
//                case R.id.blueOrb2:
//                    if(enemy.getTargetElement().get(1).getValue() == 1){
//                        enemy.setTargetElement2(-1);
//                        orbRadioGroup2.clearCheck();
//                    } else {
//                        enemy.setTargetElement2(1);
//                    }
//                    break;
//                case R.id.greenOrb2:
//                    if(enemy.getTargetElement().get(1).getValue() == 2){
//                        enemy.setTargetElement2(-1);
//                        orbRadioGroup2.clearCheck();
//                    } else {
//                        enemy.setTargetElement2(2);
//                    }
//                    break;
//                case R.id.lightOrb2:
//                    if(enemy.getTargetElement().get(1).getValue() == 3){
//                        enemy.setTargetElement2(-1);
//                        orbRadioGroup2.clearCheck();
//                    } else {
//                        enemy.setTargetElement2(3);
//                    }
//                    break;
//                case R.id.darkOrb2:
//                    if(enemy.getTargetElement().get(1).getValue() == 4){
//                        enemy.setTargetElement2(-1);
//                        orbRadioGroup2.clearCheck();
//                    } else {
//                        enemy.setTargetElement2(4);
//                    }
//                    break;
//            }
//        }
//    };

    private View.OnClickListener element2OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.redOrb2:
                    if (element2Selection == 0) {
                        element2Selection = -1;
                        orbRadioGroup2.clearCheck();
                    } else {
                        element2Selection = 0;
                    }
                    break;
                case R.id.blueOrb2:
                    if (element2Selection == 1) {
                        element2Selection = -1;
                        orbRadioGroup2.clearCheck();
                    } else {
                        element2Selection = 1;
                    }
                    break;
                case R.id.greenOrb2:
                    if (element2Selection == 2) {
                        element2Selection = -1;
                        orbRadioGroup2.clearCheck();
                    } else {
                        element2Selection = 2;
                    }
                    break;
                case R.id.lightOrb2:
                    if (element2Selection == 3) {
                        element2Selection = -1;
                        orbRadioGroup2.clearCheck();
                    } else {
                        element2Selection = 3;
                    }
                    break;
                case R.id.darkOrb2:
                    if (element2Selection == 4) {
                        element2Selection = -1;
                        orbRadioGroup2.clearCheck();
                    } else {
                        element2Selection = 4;
                    }
                    break;
            }
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
        int element = -1;
        Boolean contains = false;
        switch (buttonId) {
            case R.id.redOrbReduction:
                element = 0;
                break;
            case R.id.blueOrbReduction:
                element = 1;
                break;
            case R.id.greenOrbReduction:
                element = 2;
                break;
            case R.id.lightOrbReduction:
                element = 3;
                break;
            case R.id.darkOrbReduction:
                element = 4;
                break;
        }

        if (isChecked) {
            for (int i = 0; i < enemy.getReduction().size(); i++) {
                if (enemy.getReduction().get(i).getValue() == element && !contains) {
                    contains = true;
                }
            }
            if (element >= 0 && !contains) {
                enemy.getReduction().add(new RealmElement(element));
            }
        } else {
            for (int i = 0; i < enemy.getReduction().size(); i++) {
                if (enemy.getReduction().get(i).getValue() == element) {
                    enemy.getReduction().remove(i);
                    break;
                }
            }
        }
    }

    private CompoundButton.OnCheckedChangeListener absorbCheckedChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setElementAbsorb(isChecked, buttonView.getId());
        }
    };

    private void setElementAbsorb(boolean isChecked, int buttonId) {
        clearTextFocus();
        int element = -1;
        Boolean contains = false;
        switch (buttonId) {
            case R.id.redOrbAbsorb:
                element = 0;
                break;
            case R.id.blueOrbAbsorb:
                element = 1;
                break;
            case R.id.greenOrbAbsorb:
                element = 2;
                break;
            case R.id.lightOrbAbsorb:
                element = 3;
                break;
            case R.id.darkOrbAbsorb:
                element = 4;
                break;
        }

        if (isChecked) {
            for (int i = 0; i < enemy.getAbsorb().size(); i++) {
                if (enemy.getAbsorb().get(i).getValue() == element && !contains) {
                    contains = true;
                }
            }
            if (element >= 0 && !contains) {
                enemy.getAbsorb().add(new RealmElement(element));
            }
        } else {
            for (int i = 0; i < enemy.getAbsorb().size(); i++) {
                if (enemy.getAbsorb().get(i).getValue() == element) {
                    enemy.getAbsorb().remove(i);
                    break;
                }
            }
        }
    }

    private void setReductionOrbs() {
        if (enemy.isHasReduction()) {
            reductionCheck.setChecked(true);
            for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                reductionRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.reductionContains(Element.RED)) {
                redOrbReduction.setChecked(true);
            } else {
                redOrbReduction.setChecked(false);
            }
            if (enemy.reductionContains(Element.BLUE)) {
                blueOrbReduction.setChecked(true);
            } else {
                blueOrbReduction.setChecked(false);
            }
            if (enemy.reductionContains(Element.GREEN)) {
                greenOrbReduction.setChecked(true);
            } else {
                greenOrbReduction.setChecked(false);
            }
            if (enemy.reductionContains(Element.DARK)) {
                darkOrbReduction.setChecked(true);
            } else {
                darkOrbReduction.setChecked(false);
            }
            if (enemy.reductionContains(Element.LIGHT)) {
                lightOrbReduction.setChecked(true);
            } else {
                lightOrbReduction.setChecked(false);
            }
        } else {
            reductionValue.setEnabled(false);
            reductionCheck.setChecked(false);
//            clearReduction();
        }
    }

    private void setAbsorbOrbs() {
        if (enemy.isHasAbsorb()) {
            absorbCheck.setChecked(true);
            for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                absorbRadioGroup.getChildAt(i).setEnabled(true);
            }
            if (enemy.absorbContains(Element.RED)) {
                redOrbAbsorb.setChecked(true);
            } else {
                redOrbAbsorb.setChecked(false);
            }
            if (enemy.absorbContains(Element.BLUE)) {
                blueOrbAbsorb.setChecked(true);
            } else {
                blueOrbAbsorb.setChecked(false);
            }
            if (enemy.absorbContains(Element.GREEN)) {
                greenOrbAbsorb.setChecked(true);
            } else {
                greenOrbAbsorb.setChecked(false);
            }
            if (enemy.absorbContains(Element.DARK)) {
                darkOrbAbsorb.setChecked(true);
            } else {
                darkOrbAbsorb.setChecked(false);
            }
            if (enemy.absorbContains(Element.LIGHT)) {
                lightOrbAbsorb.setChecked(true);
            } else {
                lightOrbAbsorb.setChecked(false);
            }

        } else {
            absorbCheck.setChecked(false);
//            clearAbsorb();
        }
    }

    private void clearAbsorb() {
        redOrbAbsorb.setOnCheckedChangeListener(null);
        blueOrbAbsorb.setOnCheckedChangeListener(null);
        greenOrbAbsorb.setOnCheckedChangeListener(null);
        lightOrbAbsorb.setOnCheckedChangeListener(null);
        darkOrbAbsorb.setOnCheckedChangeListener(null);
        redOrbAbsorb.setChecked(false);
        blueOrbAbsorb.setChecked(false);
        greenOrbAbsorb.setChecked(false);
        lightOrbAbsorb.setChecked(false);
        darkOrbAbsorb.setChecked(false);
        redOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
        blueOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
        greenOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
        darkOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
        lightOrbAbsorb.setOnCheckedChangeListener(absorbCheckedChangedListener);
        for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
            absorbRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void clearReduction() {
        redOrbReduction.setOnCheckedChangeListener(null);
        blueOrbReduction.setOnCheckedChangeListener(null);
        greenOrbReduction.setOnCheckedChangeListener(null);
        darkOrbReduction.setOnCheckedChangeListener(null);
        lightOrbReduction.setOnCheckedChangeListener(null);
        redOrbReduction.setChecked(false);
        blueOrbReduction.setChecked(false);
        greenOrbReduction.setChecked(false);
        lightOrbReduction.setChecked(false);
        darkOrbReduction.setChecked(false);
        redOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        blueOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        greenOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        darkOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        lightOrbReduction.setOnCheckedChangeListener(reductionCheckedChangedListener);
        for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
            reductionRadioGroup.getChildAt(i).setEnabled(false);
        }
    }

    private void setDamageThreshold() {
        damageThresholdValue.setText(String.valueOf(enemy.getDamageThreshold()));
        damageImmunityValue.setText(String.valueOf(enemy.getDamageImmunity()));
        if (enemy.isHasDamageThreshold()) {
            damageThresholdValue.setEnabled(true);
            damageThresholdCheck.setChecked(true);
        } else {
            damageThresholdValue.setEnabled(false);
            damageThresholdCheck.setChecked(false);
        }
        if (enemy.hasDamageImmunity()) {
            damageImmunityValue.setEnabled(true);
            damageImmunityCheck.setChecked(true);
        } else {
            damageImmunityValue.setEnabled(false);
            damageImmunityCheck.setChecked(false);
        }
    }

    private void setEnemyElement() {
        switch (enemy.getTargetElement().get(0).getElement()) {
            case RED:
                orbRadioGroup1.check(redOrb1.getId());
                break;
            case BLUE:
                orbRadioGroup1.check(blueOrb1.getId());
                break;
            case GREEN:
                orbRadioGroup1.check(greenOrb1.getId());
                break;
            case LIGHT:
                orbRadioGroup1.check(lightOrb1.getId());
                break;
            case DARK:
                orbRadioGroup1.check(darkOrb1.getId());
                break;
        }
        switch (enemy.getTargetElement().get(1).getValue()) {
            case 0:
                orbRadioGroup2.check(redOrb2.getId());
                element2Selection = 0;
                break;
            case 1:
                orbRadioGroup2.check(blueOrb2.getId());
                element2Selection = 1;
                break;
            case 2:
                orbRadioGroup2.check(greenOrb2.getId());
                element2Selection = 2;
                break;
            case 3:
                orbRadioGroup2.check(lightOrb2.getId());
                element2Selection = 3;
                break;
            case 4:
                orbRadioGroup2.check(darkOrb2.getId());
                element2Selection = 4;
                break;
            default:
                orbRadioGroup2.clearCheck();
                element2Selection = -1;
                break;

        }

    }

    private void clearTextFocus() {
        targetHpValue.clearFocus();
        currentHpValue.clearFocus();
        targetDefenseValue.clearFocus();
        damageThresholdValue.clearFocus();
        damageImmunityValue.clearFocus();
        reductionValue.clearFocus();
    }

    private void setSpinner() {
        switch (enemy.getTypes().get(0).getValue()) {
            case 0:
                type1Spinner.setSelection(1);
                break;
            case 1:
                type1Spinner.setSelection(2);
                break;
            case 2:
                type1Spinner.setSelection(3);
                break;
            case 3:
                type1Spinner.setSelection(4);
                break;
            case 4:
                type1Spinner.setSelection(5);
                break;
            case 5:
                type1Spinner.setSelection(6);
                break;
            case 6:
                type1Spinner.setSelection(7);
                break;
            case 7:
                type1Spinner.setSelection(8);
                break;
            case 8:
                type1Spinner.setSelection(9);
                break;
            case 12:
                type1Spinner.setSelection(10);
                break;
            case 14:
                type1Spinner.setSelection(11);
                break;
            case 15:
                type1Spinner.setSelection(12);
                break;
            default:
                type1Spinner.setSelection(0);
                break;
        }
        switch (enemy.getTypes().get(1).getValue()) {
            case 0:
                type2Spinner.setSelection(1);
                break;
            case 1:
                type2Spinner.setSelection(2);
                break;
            case 2:
                type2Spinner.setSelection(3);
                break;
            case 3:
                type2Spinner.setSelection(4);
                break;
            case 4:
                type2Spinner.setSelection(5);
                break;
            case 5:
                type2Spinner.setSelection(6);
                break;
            case 6:
                type2Spinner.setSelection(7);
                break;
            case 7:
                type2Spinner.setSelection(8);
                break;
            case 8:
                type2Spinner.setSelection(9);
                break;
            case 12:
                type2Spinner.setSelection(10);
                break;
            case 14:
                type2Spinner.setSelection(11);
                break;
            case 15:
                type2Spinner.setSelection(12);
                break;
            default:
                type2Spinner.setSelection(0);
                break;
        }
        switch (enemy.getTypes().get(2).getValue()) {
            case 0:
                type3Spinner.setSelection(1);
                break;
            case 1:
                type3Spinner.setSelection(2);
                break;
            case 2:
                type3Spinner.setSelection(3);
                break;
            case 3:
                type3Spinner.setSelection(4);
                break;
            case 4:
                type3Spinner.setSelection(5);
                break;
            case 5:
                type3Spinner.setSelection(6);
                break;
            case 6:
                type3Spinner.setSelection(7);
                break;
            case 7:
                type3Spinner.setSelection(8);
                break;
            case 8:
                type3Spinner.setSelection(9);
                break;
            case 12:
                type3Spinner.setSelection(10);
                break;
            case 14:
                type3Spinner.setSelection(11);
                break;
            case 15:
                type3Spinner.setSelection(12);
                break;
            default:
                type3Spinner.setSelection(0);
                break;
        }
    }

    private Spinner.OnItemSelectedListener typeOnItemSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent == type1Spinner) {
                enemy.getTypes().set(0, new RealmInt(typeItems.get(type1Spinner.getSelectedItemPosition())));
            } else if (parent == type2Spinner) {
                enemy.getTypes().set(1, new RealmInt(typeItems.get(type2Spinner.getSelectedItemPosition())));
            } else if (parent == type3Spinner) {
                enemy.getTypes().set(2, new RealmInt(typeItems.get(type3Spinner.getSelectedItemPosition())));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // http://stackoverflow.com/a/14577399 magic.
    private ListView.OnTouchListener listViewScroll = new ListView.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                case MotionEvent.ACTION_UP:
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }
            v.onTouchEvent(event);
            return true;
        }
    };

    private View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Parcelable teamParcel = Parcels.wrap(team);
            Parcelable enemyParcel = Parcels.wrap(enemy);
//            ((MainActivity) getActivity()).switchFragment(TeamDamageListFragment.newInstance(true, additionalCombos, teamParcel, enemyParcel), TeamDamageListFragment.TAG, "good");
        }
    };

    private View.OnClickListener tooltipOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            TooltipText tooltipText;
            switch (id) {
                case R.id.elementAbsorb:
                    tooltipText = new TooltipText(getContext(), "Enemy will absorb all selected elements");
                    break;
                case R.id.elementReduction:
                    tooltipText = new TooltipText(getContext(), "Enemy will reduce a percentage amount of damage from all selected elements");
                    break;
                case R.id.damageThreshold:
                    tooltipText = new TooltipText(getContext(), "Enemy will absorb any hit over the specified value");
                    break;
                case R.id.damageImmunity:
                    tooltipText = new TooltipText(getContext(), "Enemy will take 0 damage for any hit over the specified value");
                    break;
                case R.id.spinnerIcon:
                    tooltipText = new TooltipText(getContext(), "Enemy defense will be reduced by this value");
                    break;
                default:
                    tooltipText = new TooltipText(getContext(), "How did you get this tooltip?");
            }
            tooltipText.show(v);
        }
    };

    public double getPercentHp() {
        if (targetHp == 0) {
            return 0;
        }
        return (double) currentHp / targetHp;
    }

    @Override
    public void onResume() {
        broadcastReceiver = new JustAnotherBroadcastReceiver(receiverMethods);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("REFRESH_ENEMY"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ONDESELECT_ENEMY"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private JustAnotherBroadcastReceiver.receiverMethods receiverMethods = new JustAnotherBroadcastReceiver.receiverMethods() {
        @Override
        public void onReceiveMethod(Intent intent) {
            switch (intent.getAction()) {
                case "ONDESELECT_ENEMY":
                    onDeselect();
                    break;
                case "REFRESH_ENEMY":
                    onSelect();
                    break;
            }
        }
    };

    public void onSelect() {
        clearTextFocus();
//        enemy = realm.where(Enemy.class).equalTo("enemyId", 0).findFirst();
//        enemy = realm.copyFromRealm(enemy);
//        realm.beginTransaction();
//        if (enemy.isDamaged()) {
////            enemy.clearGravityList();
////            if(gravityListAdapter !=  null){
////                gravityListAdapter.notifyDataSetChanged();
////            }
//            enemy.setDamaged(false);
//        }
//        realm.commitTransaction();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            setReductionOrbs();
            setAbsorbOrbs();
            setDamageThreshold();
            setEnemyElement();
            setSpinner();
            targetHp = enemy.getTargetHp();
            currentHp = enemy.getCurrentHp();
            defenseBeforeBreak = enemy.getBeforeDefenseBreak();
            enemyName.setText(enemy.getEnemyName());
            enemyNameEditText.setText(enemy.getEnemyName());
            monsterPicture.setImageBitmap(ImageResourceUtil.getMonsterPicture(enemy.getMonsterIdPicture()));
//            currentHpValue.setText(String.valueOf((int) (enemy.getBeforeGravityHP() * enemy.getGravityPercent())));
            currentHpValue.setText(String.valueOf(currentHp));
            targetHpValue.setText(String.valueOf(targetHp));
//            totalGravityValue.setText();
            targetDefenseValue.setText(String.valueOf(enemy.getTargetDef()));
            reductionValue.setText(String.valueOf(enemy.getReductionValue()));
        }
    }

    public void onDeselect() {
        updateMonster();
    }

    private void updateMonster() {
        realm.beginTransaction();
        enemy.setCurrentHp(Integer.valueOf(currentHpValue.getText().toString()));
        enemy.setTargetHp(Integer.valueOf(targetHpValue.getText().toString()));
        enemy.setTargetDef(Integer.valueOf(targetDefenseValue.getText().toString()));
        enemy.setBeforeDefenseBreak(defenseBeforeBreak);
        enemy.getTypes().set(0, new RealmInt(typeItems.get(type1Spinner.getSelectedItemPosition())));
        enemy.getTypes().set(1, new RealmInt(typeItems.get(type2Spinner.getSelectedItemPosition())));
        enemy.getTypes().set(2, new RealmInt(typeItems.get(type3Spinner.getSelectedItemPosition())));
        enemy.getTargetElement().set(0, new RealmElement(orbRadioGroup1.indexOfChild(orbRadioGroup1.findViewById(orbRadioGroup1.getCheckedRadioButtonId()))));
        enemy.getTargetElement().set(1, new RealmElement(element2Selection));
        enemy.getAbsorb().clear();
        if (absorbCheck.isChecked()) {
            enemy.setHasAbsorb(true);
            for (int i = 0; i < absorbRadioGroup.getChildCount(); i++) {
                switch (absorbRadioGroup.getChildAt(i).getId()) {
                    case R.id.redOrbAbsorb:
                        if (((CheckBox) absorbRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getAbsorb().add(new RealmElement(0));
                        }
                        break;
                    case R.id.blueOrbAbsorb:
                        if (((CheckBox) absorbRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getAbsorb().add(new RealmElement(1));
                        }
                        break;
                    case R.id.greenOrbAbsorb:
                        if (((CheckBox) absorbRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getAbsorb().add(new RealmElement(2));
                        }
                        break;
                    case R.id.lightOrbAbsorb:
                        if (((CheckBox) absorbRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getAbsorb().add(new RealmElement(3));
                        }
                        break;
                    case R.id.darkOrbAbsorb:
                        if (((CheckBox) absorbRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getAbsorb().add(new RealmElement(4));
                        }
                        break;
                }
            }
        } else {
            enemy.setHasAbsorb(false);
        }
        enemy.getReduction().clear();
        if (reductionCheck.isChecked()) {
            enemy.setHasReduction(true);
            for (int i = 0; i < reductionRadioGroup.getChildCount(); i++) {
                switch (reductionRadioGroup.getChildAt(i).getId()) {
                    case R.id.redOrbReduction:
                        if (((CheckBox) reductionRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getReduction().add(new RealmElement(0));
                        }
                        break;
                    case R.id.blueOrbReduction:
                        if (((CheckBox) reductionRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getReduction().add(new RealmElement(1));
                        }
                        break;
                    case R.id.greenOrbReduction:
                        if (((CheckBox) reductionRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getReduction().add(new RealmElement(2));
                        }
                        break;
                    case R.id.lightOrbReduction:
                        if (((CheckBox) reductionRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getReduction().add(new RealmElement(3));
                        }
                        break;
                    case R.id.darkOrbReduction:
                        if (((CheckBox) reductionRadioGroup.getChildAt(i)).isChecked()) {
                            enemy.getReduction().add(new RealmElement(4));
                        }
                        break;
                }
            }
        } else {
            enemy.setHasReduction(false);
        }
        enemy.setReductionValue(Integer.valueOf(reductionValue.getText().toString()));
        enemy.setDamageThreshold(Integer.valueOf(damageThresholdValue.getText().toString()));
        if (damageThresholdCheck.isChecked()) {
            enemy.setHasDamageThreshold(true);
        } else {
            enemy.setHasDamageThreshold(false);
        }
        enemy.setDamageImmunity(Integer.valueOf(damageImmunityValue.getText().toString()));
        if (damageImmunityCheck.isChecked()) {
            enemy.setHasDamageImmunity(true);
        } else {
            enemy.setHasDamageImmunity(false);
        }
        if ((double) enemy.getCurrentHp() / (double) enemy.getTargetHp() > .5) {
            enemy.setCurrentElement(enemy.getTargetElement().get(0));
        } else {
            if (enemy.getTargetElement().get(0).getValue() > -1) {
                enemy.setCurrentElement(enemy.getTargetElement().get(1));
            }
        }
        if (realm.where(Enemy.class).equalTo("enemyId", enemy.getOverwriteEnemyId()).findFirst() != null) {
            Enemy overwriteEnemy = realm.copyFromRealm(enemy);
            overwriteEnemy.setEnemyId(enemy.getOverwriteEnemyId());
            realm.copyToRealmOrUpdate(overwriteEnemy);
        }
//        enemy.setEnemyId(0);
//        realm.copyToRealmOrUpdate(enemy);
        realm.commitTransaction();
        enemyParcel = Parcels.wrap(enemy);
    }
}
