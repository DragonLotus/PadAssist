package com.padassist.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.padassist.BroadcastReceivers.JustAnotherBroadcastReceiver;
import com.padassist.Data.LeaderSkillType;
import com.padassist.Data.OrbMatch;
import com.padassist.Data.Team;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;
import com.padassist.Util.Singleton;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Thomas on 7/11/2015.
 */
public class OrbMatchFragment extends Fragment {
    public static final String TAG = OrbMatchFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int additionalCombos = 0;
    private int maximumOrbs;
    private TextView editTeam, orbsLinkedValue, orbsPlusValue, emptyText;
    private EditText additionalComboValue;
    private Button addMatch, calculateButton, reset, options;
    private Spinner orbsLinked, orbsPlus, boardSize;
    private List<String> orbsLinkedItems, orbsPlusItems, boardSizeItems;
    private ArrayAdapter<String> orbsLinkedAdapter;
    private ArrayAdapter<String> orbsPlusAdapter;
    private ArrayAdapter<String> boardSizeAdapter;
    private CheckBox rowCheckBox, maxLeadMultiplierCheckBox, ignoreEnemyCheckBox, crossCheckBox;
    private RecyclerView orbMatches;
    private OrbMatchRecycler orbMatchRecycler;
    private boolean isRow, maxLeadMultiplier = false, hasEnemy = true, isCross;
    private OrbMatch orbMatch;
    private Team team;
//    private Enemy enemy;
    private Toast toast;
    private RadioGroup orbRadioGroup;
    private MyDialogFragment dialog;
    private RealmResults<OrbMatch> orbMatchList;
    private OrbMatchOptionsDialogFragment orbMatchOptionsDialogFragment;
    private Realm realm;
    private ArrayList<LeaderSkillType> minimumMatchLeaderSkills = new ArrayList<>();
    private int minimumMatch = 3;
    private SharedPreferences preferences;
    private NoDropDialogFragment disclaimerDialog;
    private boolean noDrop = false;
    private JustAnotherBroadcastReceiver broadcastReceiver;

    private MyDialogFragment.ResetLayout dialogFrag = new MyDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            additionalComboValue.clearFocus();
            realm.beginTransaction();
            orbMatchList.deleteAllFromRealm();
            realm.commitTransaction();
            orbMatchRecycler.notifyDataSetChanged();
            Log.d("OrbMatchFragment", "orbMatchList size is: " + orbMatchList.size());
            additionalComboValue.setText("" + (orbMatchList.size() + additionalCombos));
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "Matches Reset", Toast.LENGTH_SHORT);
            toast.show();
            emptyText.setVisibility(View.VISIBLE);
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
                    maximumOrbs = 20;
                    if (orbsLinked.getSelectedItemPosition() > 17) {
                        orbsLinked.setSelection(17);
                    }
                    if (orbsPlus.getSelectedItemPosition() > 20) {
                        orbsPlus.setSelection(20);
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
                    maximumOrbs = 30;
                    if (orbsLinked.getSelectedItemPosition() > 27) {
                        orbsLinked.setSelection(27);
                    }
                    if (orbsPlus.getSelectedItemPosition() > 30) {
                        orbsPlus.setSelection(30);
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
                    maximumOrbs = 42;
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
            updateSpinners();
            realm.beginTransaction();
            orbMatchList.deleteAllFromRealm();
            realm.commitTransaction();
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
            additionalComboValue.clearFocus();
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
                    if (!orbsPlus.isEnabled() && (maximumOrbs >= minimumMatch)) {
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
                Parcelable teamParcel = Parcels.wrap(team);
//                Parcelable enemyParcel = Parcels.wrap(enemy);
                if (Singleton.getInstance().isIgnoreEnemy()) {
//                    ((MainActivity) getActivity()).switchFragment(TeamDamageListFragment.newInstance(false, additionalCombos, teamParcel), TeamDamageListFragment.TAG, "good");
                } else {
//                    ((MainActivity) getActivity()).switchFragment(EnemyTargetFragment.newInstance(additionalCombos, teamParcel, enemyParcel), EnemyTargetFragment.TAG, "good");
                }
            }
        }
    };

    private View.OnClickListener addMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            if(orbMatchList.size() == 0){
                orbMatch = new OrbMatch(0, orbsLinked.getSelectedItemPosition() + minimumMatch, orbsPlus.getSelectedItemPosition(), getOrbColor(), isRow, isCross);
            } else {
                orbMatch = new OrbMatch(orbMatchList.get(orbMatchList.size() - 1).getMatchId() + 1,orbsLinked.getSelectedItemPosition() + minimumMatch, orbsPlus.getSelectedItemPosition(), getOrbColor(), isRow, isCross);
            }
            realm.beginTransaction();
            orbMatch = realm.copyToRealmOrUpdate(orbMatch);
            realm.commitTransaction();

            orbMatchList = realm.where(OrbMatch.class).findAllSorted("matchId");

            orbMatchRecycler.notifyItemInserted(orbMatchList.size() - 1);
            orbMatches.scrollToPosition(orbMatchList.size() - 1);
            emptyText.setVisibility(View.INVISIBLE);
            additionalComboValue.setText("" + (orbMatchList.size() + additionalCombos));
            if (noDrop) {
                maximumOrbs -= orbsLinked.getSelectedItemPosition() + minimumMatch;
                updateSpinners();
            }
//         if(ignoreEnemyCheckBox.isChecked()){
//            calculateButton.setEnabled(true);
//         }
        }
    };

    private View.OnClickListener removeMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            int position = (int) v.getTag(R.string.index);
            if (noDrop) {
                maximumOrbs += orbMatchList.get(position).getOrbsLinked();
                if (!addMatch.isEnabled()) {
                    addMatch.setEnabled(true);
                    orbsLinked.setEnabled(true);
                    orbsPlus.setEnabled(true);
                }
                updateSpinners();
            }
            realm.beginTransaction();
            orbMatchList.deleteFromRealm(position);
            realm.commitTransaction();
            orbMatchRecycler.notifyDataSetChanged();
            if (orbMatchList.size() == 0) {
                emptyText.setVisibility(View.VISIBLE);
            }

            Log.d("OrbMatchFragment", "orbMatchList size is: " + orbMatchList.size());

            additionalComboValue.setText("" + (orbMatchList.size() + additionalCombos));

            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(getActivity(), "Match Removed", Toast.LENGTH_SHORT);
            toast.show();
        }
    };

    private Button.OnClickListener resetOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            additionalComboValue.clearFocus();
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
            additionalComboValue.clearFocus();
            if (orbMatchOptionsDialogFragment == null) {
                orbMatchOptionsDialogFragment = OrbMatchOptionsDialogFragment.newInstance();
            }
            if (!orbMatchOptionsDialogFragment.isAdded()) {
                orbMatchOptionsDialogFragment.show(getChildFragmentManager(), team, "Options");
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

//    private MyTextWatcher additionalComboTextWatcher = new MyTextWatcher(MyTextWatcher.ADDITIONAL_COMBOS, changeStats);

    private TextWatcher totalCombosTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().equals("")) {
                additionalCombos = Integer.valueOf(s.toString()) - orbMatchList.size();
                Singleton.getInstance().setAdditionalCombos(additionalCombos);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                hideKeyboard(v);
                if ((additionalComboValue.getText().toString().equals(""))) {
                    additionalComboValue.setText("" + orbMatchList.size());
                } else if (Integer.valueOf(additionalComboValue.getText().toString()) < orbMatchList.size()) {
                    additionalComboValue.setText("" + orbMatchList.size());
                }
            }
        }
    };

    private View.OnClickListener orbMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if(orbsLinked.isEnabled()){

                orbsLinked.setSelection(orbMatchList.get(position).getOrbsLinked() - minimumMatch);
            }
//            if ((orbMatchList.get(position).getOrbsLinked() - minimumMatch) > orbsLinkedItems.size()) {
//                boardSize.setSelection(boardSize.getSelectedItemPosition() + 1);
//                Singleton.getInstance().setBoardSize(boardSize.getSelectedItemPosition());
//            } else {
//            orbsLinked.setSelection(orbMatchList.get(position).getOrbsLinked() - minimumMatch);
//            }
            if(orbsPlus.isEnabled()){
                if (orbMatchList.get(position).getNumOrbPlus() > orbsPlusItems.size() - 1) {
                    orbsPlusItems.clear();
                    for (int i = 0; i <= orbsLinkedItems.size() + minimumMatch; i++) {
                        orbsPlusItems.add("" + i);
                    }
                    orbsPlus.setSelection(orbMatchList.get(position).getNumOrbPlus());
                } else {
                    orbsPlus.setSelection(orbMatchList.get(position).getNumOrbPlus());
                }
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

    public void hideKeyboard(View view) {
//        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static OrbMatchFragment newInstance(Parcelable team, Parcelable enemy) {
        OrbMatchFragment fragment = new OrbMatchFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public static OrbMatchFragment newInstance(Parcelable team) {
        OrbMatchFragment fragment = new OrbMatchFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
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
    public void onDestroy() {
        super.onDestroy();
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
            team = Parcels.unwrap(getArguments().getParcelable("team"));
//            enemy = Parcels.unwrap(getArguments().getParcelable("enemy"));
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

        preferences = PreferenceManager.getDefaultSharedPreferences(Singleton.getInstance().getContext());

        orbMatchList = realm.where(OrbMatch.class).findAllSorted("matchId");
        orbsLinkedItems = new ArrayList<>();
        orbsPlusItems = new ArrayList<>();

        boardSizeItems = new ArrayList<>();
        boardSizeItems.add("5x4");
        boardSizeItems.add("6x5");
        boardSizeItems.add("7x6");

//        onSelect();

        orbMatchRecycler = new OrbMatchRecycler(getActivity(), orbMatchList, orbMatchOnClickListener, removeMatchOnClickListener);
        orbMatches.setAdapter(orbMatchRecycler);
        orbMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        additionalComboValue.addTextChangedListener(totalCombosTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
        calculateButton.setOnClickListener(calculateOnClickListener);

        ignoreEnemyCheckBox.setChecked(Singleton.getInstance().isIgnoreEnemy());

        orbRadioGroup.setOnCheckedChangeListener(orbRadioGroupOnCheckChangeListener);
        orbsLinkedAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, orbsLinkedItems);
        orbsLinked.setAdapter(orbsLinkedAdapter);
        orbsPlusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, orbsPlusItems);
        orbsPlus.setAdapter(orbsPlusAdapter);
        boardSizeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, boardSizeItems);
        boardSize.setAdapter(boardSizeAdapter);

        boardSize.setSelection(Singleton.getInstance().getBoardSize(), false);

        orbsLinked.setOnItemSelectedListener(orbsLinkedSpinnerSelectedListener);
        boardSize.setOnItemSelectedListener(boardSizeSpinnerSelectedListener);

        options.setOnClickListener(optionsOnClickListener);

//        getActivity().setTitle("Set Orb Matches");
        //Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDeselect();
    }

    private void updateSpinners() {
        if (maximumOrbs < minimumMatch) {
            orbsLinked.setEnabled(false);
            orbsPlus.setEnabled(false);
            addMatch.setEnabled(false);
        } else {
            if (orbsLinked.getSelectedItemPosition() > (maximumOrbs - minimumMatch)) {
                orbsLinked.setSelection(maximumOrbs - minimumMatch);
            }
            if (orbsPlus.getSelectedItemPosition() > maximumOrbs) {
                orbsPlus.setSelection(maximumOrbs);
            }

            orbsLinkedItems.clear();
            orbsPlusItems.clear();

            for (int i = minimumMatch; i <= maximumOrbs; i++) {
                orbsLinkedItems.add("" + i);
            }
            for (int i = 0; i < orbsLinked.getSelectedItemPosition() + minimumMatch + 1; i++) {
                orbsPlusItems.add("" + i);
            }
        }
        orbsPlusAdapter.notifyDataSetChanged();
        orbsLinkedAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new JustAnotherBroadcastReceiver(receiverMethods);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("REFRESH_ORB_MATCH"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ONDESELECT_ORB_MATCH"));
    }

    private JustAnotherBroadcastReceiver.receiverMethods receiverMethods = new JustAnotherBroadcastReceiver.receiverMethods() {
        @Override
        public void onReceiveMethod(Intent intent) {
            switch(intent.getAction()){
                case "ONDESELECT_ORB_MATCH":
                    onDeselect();
                    break;
                case "REFRESH_ORB_MATCH":
                    onSelect();
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    public void onSelect(){
        Log.d("OrbMatchFrag", "Additional combos is: " + Singleton.getInstance().getAdditionalCombos());
        additionalCombos = Singleton.getInstance().getAdditionalCombos();
        if(team.getLeadSkill() != null){
            if(minimumMatchLeaderSkills.contains(team.getLeadSkill().getAtkSkillType().getLeaderSkillType())){
                if (team.getLeadSkill().getMinimumMatch() > minimumMatch) {
                    minimumMatch = team.getLeadSkill().getMinimumMatch();
                }
            }
        }
        if(team.getHelperSkill() != null){
            if(minimumMatchLeaderSkills.contains(team.getHelperSkill().getAtkSkillType().getLeaderSkillType())){
                if (team.getHelperSkill().getMinimumMatch() > minimumMatch) {
                    minimumMatch = team.getHelperSkill().getMinimumMatch();
                }
            }
        }

        realm.beginTransaction();
        for (int i = 0; i < orbMatchList.size(); i++){
            if(orbMatchList.get(i).getOrbsLinked() < minimumMatch){
                orbMatchList.deleteFromRealm(i);
            }
        }
        if(team.getLeadSkill() !=  null){
            if(team.getLeadSkill().getBoardSize() == 2){
                Singleton.getInstance().setBoardSize(2);
                boardSize.setEnabled(false);
                for(int i = 0; i < orbMatchList.size(); i++){
                    if(orbMatchList.get(i).getOrbsLinked() == 6 || orbMatchList.get(i).getOrbsLinked() == 5 || orbMatchList.get(i).getOrbsLinked() == 8){
                        if (orbMatchList.get(i).isRow()){
                            orbMatchList.deleteFromRealm(i);
                            i--;
                        }
                    }
                }
            } else if(team.getHelperSkill() != null){
                if(team.getHelperSkill().getBoardSize() == 2){
                    Singleton.getInstance().setBoardSize(2);
                    boardSize.setEnabled(false);
                    for(int i = 0; i < orbMatchList.size(); i++){
                        if(orbMatchList.get(i).getOrbsLinked() == 6 || orbMatchList.get(i).getOrbsLinked() == 5 || orbMatchList.get(i).getOrbsLinked() == 8){
                            if (orbMatchList.get(i).isRow()){
                                orbMatchList.deleteFromRealm(i);
                                i--;
                            }
                        }
                    }
                } else {
                    boardSize.setEnabled(true);
                }
            } else {
                boardSize.setEnabled(true);
            }
        }
//        if(team.getLeadSkill().getBoardSize() == 2 || team.getHelperSkill().getBoardSize() == 2){
//            Singleton.getInstance().setBoardSize(2);
//            boardSize.setEnabled(false);
//            for(int i = 0; i < orbMatchList.size(); i++){
//                if(orbMatchList.get(i).getOrbsLinked() == 6 || orbMatchList.get(i).getOrbsLinked() == 5 || orbMatchList.get(i).getOrbsLinked() == 8){
//                    if (orbMatchList.get(i).isRow()){
//                        orbMatchList.deleteFromRealm(i);
//                        i--;
//                    }
//                }
//            }
//        } else {
//            boardSize.setEnabled(true);
//        }

        switch (Singleton.getInstance().getBoardSize()) {
            case 0:
                maximumOrbs = 20;
                break;
            case 1:
                maximumOrbs = 30;
                break;
            case 2:
                maximumOrbs = 42;
                break;
        }

        if (team.getTeamBadge() == 13) {
            noDrop = true;
        } else if(team.getLeadSkill() != null){
            if(team.getLeadSkill().isNoSkyfall()){
                noDrop = true;
            }
        } else if(team.getHelperSkill() != null){
            if(team.getHelperSkill().isNoSkyfall()){
                noDrop = true;
            }
        }

        if (noDrop && preferences.getBoolean("showNoDropDisclaimer", true)) {
            disclaimerDialog = NoDropDialogFragment.newInstance(new NoDropDialogFragment.Preferences() {
                @Override
                public void setShowAgain(boolean showAgain) {
                    if (!showAgain) {
                        preferences.edit().putBoolean("showNoDropDisclaimer", true).apply();
                    } else {
                        preferences.edit().putBoolean("showNoDropDisclaimer", false).apply();
                    }
                }
            });
            disclaimerDialog.show(getChildFragmentManager(), "Show Disclaimer");
        }

        if(noDrop){
            for (int i = 0; i < orbMatchList.size(); i++) {
                if (maximumOrbs - orbMatchList.get(i).getOrbsLinked() >= 0) {
                    maximumOrbs -= orbMatchList.get(i).getOrbsLinked();
                } else {
                    orbMatchList.deleteFromRealm(i);
                }
            }
            additionalComboValue.setEnabled(false);
        }
        realm.commitTransaction();

        if (maximumOrbs < minimumMatch) {
            orbsLinkedItems.add("" + minimumMatch);
            for (int i = 0; i <= minimumMatch; i++) {
                orbsPlusItems.add("" + i);
            }
        }

        if(orbMatchList.size() == 0){
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.INVISIBLE);
        }

        updateSpinners();
        additionalComboValue.setText("" + (orbMatchList.size() + additionalCombos));
        boardSize.setSelection(Singleton.getInstance().getBoardSize(), false);
    }

    public void onDeselect(){
        Log.d("OrbMatchFrag", "onDeselect");
//        team.getOrbMatches().clear();
//        team.getOrbMatches().addAll(orbMatchList);
        orbMatchList = realm.where(OrbMatch.class).findAllSorted("matchId");
        team.updateOrbs(orbMatchList);
        if (orbMatchList.size() != 0) {
            team.setAtkMultiplierArrays(orbMatchList, orbMatchList.size() + additionalCombos);
        }
        team.setHpRcvMultiplierArrays(orbMatchList, orbMatchList.size() + additionalCombos);

        Log.d("OrbMatchFragment", "OrbMatch realm is: " + realm.where(OrbMatch.class).findAll());
        Log.d("OrbMatchFragment", "additional combos is: " + Singleton.getInstance().getAdditionalCombos());
        Log.d("OrbMatchFragment", "rcvMultiplier is: " + team.getRcvMultiplier());
    }

}
