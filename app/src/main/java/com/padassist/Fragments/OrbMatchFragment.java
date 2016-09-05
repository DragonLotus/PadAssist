package com.padassist.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputBinding;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.OrbMatchRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Enemy;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;
import com.padassist.Util.Singleton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Thomas on 7/11/2015.
 */
public class OrbMatchFragment extends Fragment {
    public static final String TAG = OrbMatchFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int additionalCombos = 0;
    private TextView editTeam, orbsLinkedValue, orbsPlusValue, emptyText;
    private EditText additionalComboValue;
    private Button addMatch, calculateButton, reset, options;
    private Spinner orbsLinked, orbsPlus, boardSize;
    private List<String> orbsLinkedItems, orbsPlusItems, boardSizeItems;
    private CheckBox rowCheckBox, maxLeadMultiplierCheckBox, ignoreEnemyCheckBox, crossCheckBox;
    private RecyclerView orbMatches;
    private OrbMatchRecycler orbMatchRecycler;
    private boolean isRow, maxLeadMultiplier = false, hasEnemy = true, isCross;
    private OrbMatch orbMatch;
    private Team team;
    private Enemy enemy;
    private Toast toast;
    private RadioGroup orbRadioGroup;
    private MyDialogFragment dialog;
    private ArrayList<OrbMatch> orbMatchList;
    private OrbMatchOptionsDialogFragment orbMatchOptionsDialogFragment;
    private Realm realm;
    private ArrayList<LeaderSkillType> minimumMatchLeaderSkills = new ArrayList<>();
    int minimumMatch = 3;

    private MyDialogFragment.ResetLayout dialogFrag = new MyDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            additionalComboValue.clearFocus();
            orbMatchRecycler.clear();
            orbMatchRecycler.notifyDataSetChanged();
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "Matches Reset", Toast.LENGTH_SHORT);
            toast.show();
            dialog.dismiss();
        }
    };

    private CompoundButton.OnCheckedChangeListener rowCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            additionalComboValue.clearFocus();
            if (buttonView.equals(rowCheckBox)) {
                isRow = isChecked;
                if (crossCheckBox.isChecked()) {
                    crossCheckBox.setOnCheckedChangeListener(null);
                    crossCheckBox.toggle();
                    isCross = !isChecked;
                    crossCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
                }
            } else if (buttonView.equals(maxLeadMultiplierCheckBox)) {
                maxLeadMultiplier = isChecked;
            } else if (buttonView.equals(ignoreEnemyCheckBox)) {
                Singleton.getInstance().setIgnoreEnemy(isChecked);
                if (isChecked) {
                    calculateButton.setText("Calculate");
//               if(orbMatches.getCount() == 0){
//                  calculateButton.setEnabled(false);
//               }
                } else {
                    calculateButton.setText("Enemy Attributes");
//               calculateButton.setEnabled(true);
                }
            } else if (buttonView.equals(crossCheckBox)) {
                isCross = isChecked;
                if (rowCheckBox.isChecked()) {
                    rowCheckBox.setOnCheckedChangeListener(null);
                    rowCheckBox.toggle();
                    isRow = !isChecked;
                    rowCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
                }
            }
        }
    };

    private Spinner.OnItemSelectedListener orbsLinkedSpinnerSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (orbsPlus.getSelectedItemPosition() > position + minimumMatch) {
                orbsPlus.setSelection(position + minimumMatch);
            }
            orbsPlusItems.clear();
            for (int i = 0; i <= orbsLinked.getSelectedItemPosition() + minimumMatch; i++) {
                orbsPlusItems.add("" + i);
            }
            switch (boardSize.getSelectedItemPosition()) {
                case 0:
                    if ((position + minimumMatch) >= 5 && (position + minimumMatch) != 6) {
                        rowCheckBox.setEnabled(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    if ((position + minimumMatch) >= 17) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    }
                    break;
                case 1:
                    if ((position + minimumMatch) >= 6 && (position + minimumMatch) != 7) {
                        rowCheckBox.setEnabled(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    if ((position + minimumMatch) >= 26) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    }
                    break;
                case 2:
                    if ((position + minimumMatch) >= 7 && (position + minimumMatch) != 8) {
                        rowCheckBox.setEnabled(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    if ((position + minimumMatch) >= 37) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    }
                    break;
            }
            if (position + minimumMatch == 5) {
                crossCheckBox.setEnabled(true);
            } else {
                crossCheckBox.setChecked(false);
                crossCheckBox.setEnabled(false);
            }
            if (getOrbColor() == 6 || getOrbColor() == 7 || getOrbColor() == 8) {
                rowCheckBox.setChecked(false);
                rowCheckBox.setEnabled(false);
                crossCheckBox.setChecked(false);
                crossCheckBox.setEnabled(false);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private Spinner.OnItemSelectedListener boardSizeSpinnerSelectedListener = new Spinner.OnItemSelectedListener() {
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    if (orbsLinked.getSelectedItemPosition() > 17) {
                        orbsLinked.setSelection(17);
                    }
                    if (orbsPlus.getSelectedItemPosition() > 20) {
                        orbsPlus.setSelection(20);
                    }
                    orbsLinkedItems.clear();
                    orbsPlusItems.clear();
                    for (int i = minimumMatch; i < 21; i++) {
                        orbsLinkedItems.add("" + i);
                    }
                    for (int i = 0; i < orbsLinked.getSelectedItemPosition() + minimumMatch + 1; i++) {
                        orbsPlusItems.add("" + i);
                    }
                    Singleton.getInstance().setBoardSize(0);
                    if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 17 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 5 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 6) {
                        rowCheckBox.setEnabled(true);
                    } else if (orbsLinked.getSelectedItemPosition() + minimumMatch >= 17) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    break;
                case 1:
                    if (orbsLinked.getSelectedItemPosition() > 27) {
                        orbsLinked.setSelection(27);
                    }
                    if (orbsPlus.getSelectedItemPosition() > 30) {
                        orbsPlus.setSelection(30);
                    }
                    orbsLinkedItems.clear();
                    orbsPlusItems.clear();
                    for (int i = minimumMatch; i < 31; i++) {
                        orbsLinkedItems.add("" + i);
                    }
                    for (int i = 0; i < orbsLinked.getSelectedItemPosition() + minimumMatch + 1; i++) {
                        orbsPlusItems.add("" + i);
                    }
                    Singleton.getInstance().setBoardSize(1);
                    if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 26 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 6 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 7) {
                        rowCheckBox.setEnabled(true);
                    } else if (orbsLinked.getSelectedItemPosition() + minimumMatch >= 26) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    break;
                case 2:
                    orbsLinkedItems.clear();
                    orbsPlusItems.clear();
                    for (int i = minimumMatch; i < 43; i++) {
                        orbsLinkedItems.add("" + i);
                    }
                    for (int i = 0; i < orbsLinked.getSelectedItemPosition() + minimumMatch; i++) {
                        orbsPlusItems.add("" + i);
                    }
                    Singleton.getInstance().setBoardSize(2);
                    if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 37 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 7 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 8) {
                        rowCheckBox.setEnabled(true);
                    } else if (orbsLinked.getSelectedItemPosition() + minimumMatch >= 37) {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(true);
                    } else {
                        rowCheckBox.setEnabled(false);
                        rowCheckBox.setChecked(false);
                    }
                    break;
            }
            orbMatchRecycler.clear();
            orbMatchRecycler.notifyDataSetChanged();
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "Board Size Changed", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private SeekBar.OnSeekBarChangeListener orbsPlusSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            orbsPlusValue.setText("" + (progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            additionalComboValue.clearFocus();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private int getOrbColor() {
        int radioGroupId = orbRadioGroup.getCheckedRadioButtonId();
        switch (radioGroupId) {
            case R.id.redOrb:
                return 0;
            case R.id.blueOrb:
                return 1;
            case R.id.greenOrb:
                return 2;
            case R.id.lightOrb:
                return 3;
            case R.id.darkOrb:
                return 4;
            case R.id.heartOrb:
                return 5;
            case R.id.jammerOrb:
                return 6;
            case R.id.poisonOrb:
                return 7;
            case R.id.mortalPoisonOrb:
                return 8;
        }
        return radioGroupId;
    }

    private RadioGroup.OnCheckedChangeListener orbRadioGroupOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.jammerOrb:
                    orbsPlus.setSelection(0);
                    orbsPlus.setEnabled(false);
                    rowCheckBox.setChecked(false);
                    rowCheckBox.setEnabled(false);
                    break;
                case R.id.poisonOrb:
                    orbsPlus.setSelection(0);
                    orbsPlus.setEnabled(false);
                    rowCheckBox.setChecked(false);
                    rowCheckBox.setEnabled(false);
                    break;
                case R.id.mortalPoisonOrb:
                    orbsPlus.setSelection(0);
                    orbsPlus.setEnabled(false);
                    rowCheckBox.setChecked(false);
                    rowCheckBox.setEnabled(false);
                    break;
                default:
                    if (!orbsPlus.isEnabled()) {
                        orbsPlus.setEnabled(true);
                    }
                    if (!rowCheckBox.isEnabled()) {
                        switch (boardSize.getSelectedItemPosition()) {
                            case 0:
                                if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 17 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 5 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 6) {
                                    rowCheckBox.setEnabled(true);
                                }
                                break;
                            case 1:
                                if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 26 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 6 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 7) {
                                    rowCheckBox.setEnabled(true);
                                }
                                break;
                            case 2:
                                if ((orbsLinked.getSelectedItemPosition() + minimumMatch) < 37 && (orbsLinked.getSelectedItemPosition() + minimumMatch) >= 7 && (orbsLinked.getSelectedItemPosition() + minimumMatch) != 8) {
                                    rowCheckBox.setEnabled(true);
                                }
                                break;
                        }
                    }
                    break;
            }
        }
    };

    private View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            if (orbMatches.getChildCount() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "No orb matches", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (Singleton.getInstance().isIgnoreEnemy()) {
                    ((MainActivity) getActivity()).switchFragment(TeamDamageListFragment.newInstance(false, additionalCombos, team), TeamDamageListFragment.TAG, "good");
                } else {
                    ((MainActivity) getActivity()).switchFragment(EnemyTargetFragment.newInstance(additionalCombos, team, enemy), EnemyTargetFragment.TAG, "good");
                }
            }
        }
    };

    private View.OnClickListener addMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            orbMatch = new OrbMatch(orbsLinked.getSelectedItemPosition() + minimumMatch, orbsPlus.getSelectedItemPosition(), getOrbColor(), isRow, isCross);
//            orbMatchList.add(0, orbMatch);
            orbMatchList.add(orbMatch);
            orbMatchRecycler.notifyItemInserted(orbMatchList.size() - 1);
            orbMatches.scrollToPosition(orbMatchList.size() - 1);
            emptyText.setVisibility(View.INVISIBLE);
//         if(ignoreEnemyCheckBox.isChecked()){
//            calculateButton.setEnabled(true);
//         }
        }
    };

    private View.OnClickListener removeMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            orbMatchList.remove(position);
            orbMatchRecycler.notifyDataSetChanged();
            if (orbMatchList.size() == 0) {
                emptyText.setVisibility(View.VISIBLE);
            }

            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "Match Removed", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private Button.OnClickListener resetOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if (orbMatchRecycler.getItemCount() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "No matches to reset", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (dialog == null) {
                    dialog = MyDialogFragment.newInstance(dialogFrag);
                }
                if (!dialog.isAdded()) {
                    dialog.show(getChildFragmentManager(), "String");
                }
            }

        }
    };

    private Button.OnClickListener optionsOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (orbMatchOptionsDialogFragment == null) {
                orbMatchOptionsDialogFragment = orbMatchOptionsDialogFragment.newInstance(team);
            }
            if (!orbMatchOptionsDialogFragment.isAdded()) {
                orbMatchOptionsDialogFragment.show(getChildFragmentManager(), "Options");
            }
        }
    };

    private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats() {
        @Override
        public void changeMonsterAttribute(int statToChange, int statValue) {
            if (statToChange == MyTextWatcher.ADDITIONAL_COMBOS) {
                additionalCombos = statValue;
            }
        }
    };

    private MyTextWatcher additionalComboTextWatcher = new MyTextWatcher(MyTextWatcher.ADDITIONAL_COMBOS, changeStats);

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
//                hideKeyboard(v);
                if ((additionalComboValue.getText().toString().equals(""))) {
                    additionalComboValue.setText("0");
                }
            }
        }
    };

    private View.OnClickListener orbMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if ((orbMatchList.get(position).getOrbsLinked() - minimumMatch) > orbsLinkedItems.size()) {
                boardSize.setSelection(boardSize.getSelectedItemPosition() + 1);
                Singleton.getInstance().setBoardSize(boardSize.getSelectedItemPosition());
            } else {
                orbsLinked.setSelection(orbMatchList.get(position).getOrbsLinked() - minimumMatch);
            }
            if (orbMatchList.get(position).getNumOrbPlus() > orbsPlusItems.size() - 1) {
                orbsPlusItems.clear();
                for (int i = 0; i <= orbsLinkedItems.size() + minimumMatch; i++){
                    orbsPlusItems.add("" + i);
                }
                orbsPlus.setSelection(orbMatchList.get(position).getNumOrbPlus());
            } else {
                orbsPlus.setSelection(orbMatchList.get(position).getNumOrbPlus());
            }
            rowCheckBox.setChecked(orbMatchList.get(position).isRow());
            crossCheckBox.setChecked(orbMatchList.get(position).isCross());
            Log.d("OrbMatchList", "orbMatchList element is: " + orbMatchList.get(position));
            switch (orbMatchList.get(position).getElement()) {
                case RED:
                    orbRadioGroup.check(R.id.redOrb);
                    break;
                case BLUE:
                    orbRadioGroup.check(R.id.blueOrb);
                    break;
                case GREEN:
                    orbRadioGroup.check(R.id.greenOrb);
                    break;
                case LIGHT:
                    orbRadioGroup.check(R.id.lightOrb);
                    break;
                case DARK:
                    orbRadioGroup.check(R.id.darkOrb);
                    break;
                case HEART:
                    orbRadioGroup.check(R.id.heartOrb);
                    break;
                case JAMMER:
                    orbRadioGroup.check(R.id.jammerOrb);
                    break;
                case POISON:
                    orbRadioGroup.check(R.id.poisonOrb);
                    break;
                case MORTAL_POISON:
                    orbRadioGroup.check(R.id.mortalPoisonOrb);
                    break;
            }
        }
    };

    private ListView.OnItemClickListener orbMatchListViewOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            orbsLinked.setSelection(orbMatchList.get(position).getOrbsLinked() - 3);
            orbsPlus.setSelection(orbMatchList.get(position).getNumOrbPlus());
            rowCheckBox.setChecked(orbMatchList.get(position).isRow());
            switch (orbMatchList.get(position).getElement()) {
                case RED:
                    orbRadioGroup.check(R.id.redOrb);
                    break;
                case BLUE:
                    orbRadioGroup.check(R.id.blueOrb);
                    break;
                case GREEN:
                    orbRadioGroup.check(R.id.greenOrb);
                    break;
                case LIGHT:
                    orbRadioGroup.check(R.id.lightOrb);
                    break;
                case DARK:
                    orbRadioGroup.check(R.id.darkOrb);
                    break;
                case HEART:
                    orbRadioGroup.check(R.id.heartOrb);
                    break;
            }
        }
    };

//    public void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
//    }

    public static OrbMatchFragment newInstance(Team team, Enemy enemy) {
        OrbMatchFragment fragment = new OrbMatchFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public OrbMatchFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View rootView = inflater.inflate(R.layout.fragment_orb_match, container, false);
        rowCheckBox = (CheckBox) rootView.findViewById(R.id.rowCheckBox);
        crossCheckBox = (CheckBox) rootView.findViewById(R.id.crossCheckBox);
        ignoreEnemyCheckBox = (CheckBox) rootView.findViewById(R.id.ignoreEnemyCheckBox);
        orbsLinked = (Spinner) rootView.findViewById(R.id.orbsLinkedSpinner);
        orbsPlus = (Spinner) rootView.findViewById(R.id.orbsPlusSpinner);
        boardSize = (Spinner) rootView.findViewById(R.id.boardSizeSpinner);
        addMatch = (Button) rootView.findViewById(R.id.addMatch);
        calculateButton = (Button) rootView.findViewById(R.id.calculateButton);
        reset = (Button) rootView.findViewById(R.id.reset);
        orbMatches = (RecyclerView) rootView.findViewById(R.id.orbMatches);
        orbRadioGroup = (RadioGroup) rootView.findViewById(R.id.elementRadioGroup);
        additionalComboValue = (EditText) rootView.findViewById(R.id.additionalComboValue);
        emptyText = (TextView) rootView.findViewById(R.id.emptyText);
        options = (Button) rootView.findViewById(R.id.options);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Write your code here
        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
        }
        realm = Realm.getDefaultInstance();
        rowCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        crossCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        ignoreEnemyCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        addMatch.setOnClickListener(addMatchOnClickListener);
        reset.setOnClickListener(resetOnClickListener);

        minimumMatchLeaderSkills.add(LeaderSkillType.MINIMUM_MATCH_COMBO_FLAT);
        minimumMatchLeaderSkills.add(LeaderSkillType.MINIMUM_MATCH_INDIAN_FLAT);
        minimumMatchLeaderSkills.add(LeaderSkillType.MINIMUM_MATCH_MATCH_ELEMENT_FLAT);
        minimumMatchLeaderSkills.add(LeaderSkillType.MINIMUM_MATCH_ORB_LINK_FLAT);
        if (minimumMatchLeaderSkills.contains(team.getLeadSkill().getAtkSkillType().getValue()) || minimumMatchLeaderSkills.contains(team.getHelperSkill().getAtkSkillType().getValue())) {
            if (team.getLeadSkill().getMinimumMatch() > minimumMatch) {
                minimumMatch = team.getLeadSkill().getMinimumMatch();
            }
            if (team.getHelperSkill().getMinimumMatch() > minimumMatch) {
                minimumMatch = team.getHelperSkill().getMinimumMatch();
            }
            Log.d("OrbMatchFragment", "minimumMatch is: " + minimumMatch);
        }

        if (orbMatchList == null) {
            orbMatchList = new ArrayList<>();
        } else {
            orbMatchList.clear();
        }
        if (realm.where(OrbMatch.class).greaterThanOrEqualTo("orbsLinked", minimumMatch).findAll().size() != 0) {
            orbMatchList.addAll(realm.where(OrbMatch.class).greaterThanOrEqualTo("orbsLinked", minimumMatch).findAll());
            emptyText.setVisibility(View.INVISIBLE);
        } else {
            emptyText.setVisibility(View.VISIBLE);
        }
        orbMatchRecycler = new OrbMatchRecycler(getActivity(), orbMatchList, orbMatchOnClickListener, removeMatchOnClickListener);
        orbMatches.setAdapter(orbMatchRecycler);
        orbMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        additionalComboValue.addTextChangedListener(additionalComboTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
        calculateButton.setOnClickListener(calculateOnClickListener);

        ignoreEnemyCheckBox.setChecked(Singleton.getInstance().isIgnoreEnemy());

        orbRadioGroup.setOnCheckedChangeListener(orbRadioGroupOnCheckChangeListener);

        orbsLinkedItems = new ArrayList<String>();
        orbsPlusItems = new ArrayList<>();

        int maxOrbs = 30;
        switch (Singleton.getInstance().getBoardSize()){
            case 0:
                maxOrbs = 20;
                break;
            case 1:
                maxOrbs = 30;
                break;
            case 2:
                maxOrbs = 42;
                break;
        }

        for (int i = minimumMatch; i <= maxOrbs; i++) {
            orbsLinkedItems.add("" + i);
        }
        for (int i = 0; i <= minimumMatch; i++) {
            orbsPlusItems.add("" + i);
        }


        boardSizeItems = new ArrayList<>();
        boardSizeItems.add("5x4");
        boardSizeItems.add("6x5");
        boardSizeItems.add("7x6");
        ArrayAdapter<String> orbsLinkedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, orbsLinkedItems);
        orbsLinked.setAdapter(orbsLinkedAdapter);
        ArrayAdapter<String> orbsPlusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, orbsPlusItems);
        orbsPlus.setAdapter(orbsPlusAdapter);
        ArrayAdapter<String> boardSizeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, boardSizeItems);
        boardSize.setAdapter(boardSizeAdapter);

        boardSize.setSelection(Singleton.getInstance().getBoardSize(), false);
        orbsLinked.setOnItemSelectedListener(orbsLinkedSpinnerSelectedListener);
        boardSize.setOnItemSelectedListener(boardSizeSpinnerSelectedListener);

        options.setOnClickListener(optionsOnClickListener);

        getActivity().setTitle("Set Orb Matches");
        //Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        for (int i = 0; i < OrbMatch.getAllOrbMatches().size(); i++) {
//            if (!orbMatchList.contains(OrbMatch.getAllOrbMatches().get(i))) {
//                OrbMatch.getAllOrbMatches().get(i).delete();
//                i -= 1;
//            }
//        }
//        if (orbMatchList.size() == 0) {
//            OrbMatch.deleteAllOrbMatches();
//        }
//        for (int i = 0; i < orbMatchList.size(); i++) {
//            if (!OrbMatch.getAllOrbMatches().contains(orbMatchList.get(i))) {
//                orbMatchList.get(i).save();
//            }
//        }
//        team.updateOrbs();
        realm.beginTransaction();
        if (orbMatchList.size() == 0) {
            realm.where(OrbMatch.class).findAll().deleteAllFromRealm();
        } else if (realm.where(OrbMatch.class).findAll().size() == 0) {
            for (int i = 0; i < orbMatchList.size(); i++) {
                orbMatchList.get(i).setMatchId(i);
                realm.copyToRealm(orbMatchList.get(i));
            }
        } else {
            RealmResults<OrbMatch> results = realm.where(OrbMatch.class).findAllSorted("matchId");
            for (int i = 0; i < results.size(); i++) {
                if (!orbMatchList.contains(results.get(i))) {
                    results.get(i).deleteFromRealm();
                }
            }
            results = realm.where(OrbMatch.class).findAllSorted("matchId");
            long lastMatchId;
            if (results.size() == 0) {
                lastMatchId = -1;
            } else {
                lastMatchId = results.get(results.size() - 1).getMatchId();
            }
            for (int i = 0; i < orbMatchList.size(); i++) {
                if ((orbMatchList.get(i).getMatchId() == 0)) {
                    orbMatchList.get(i).setMatchId(lastMatchId + 1);
                    lastMatchId++;
                    realm.copyToRealm(orbMatchList.get(i));
                }
            }
        }
        realm.commitTransaction();
        team.setOrbMatches();
        team.updateOrbs();
        if (orbMatchList.size() != 0) {
            team.setAtkMultiplierArrays(orbMatchList.size() + additionalCombos);
        }
    }

}
