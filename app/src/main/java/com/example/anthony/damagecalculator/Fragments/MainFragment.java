package com.example.anthony.damagecalculator.Fragments;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Adapters.OrbMatchAdapter;
import com.example.anthony.damagecalculator.Adapters.OrbMatchRecycler;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Threads.DownloadPadApi;
import com.example.anthony.damagecalculator.Util.Singleton;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/11/2015.
 */
public class MainFragment extends AbstractFragment {
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int additionalCombos = 0;
    private TextView editTeam, orbsLinkedValue, orbsPlusValue;
    private EditText additionalComboValue;
    private Button addMatch, calculateButton, reset;
    private SeekBar orbsLinked, orbsPlus;
    private CheckBox rowCheckBox, maxLeadMultiplierCheckBox, ignoreEnemyCheckBox;
    private RecyclerView orbMatches;
    private OrbMatchAdapter orbMatchAdapter;
    private OrbMatchRecycler orbMatchRecycler;
    private boolean isRow, maxLeadMultiplier = false, hasEnemy = true;
    private OrbMatch orbMatch;
    private Team team;
    private Enemy enemy;
    private Toast toast;
    private RadioGroup orbRadioGroup;
    private MyDialogFragment dialog;
    private ArrayList<OrbMatch> orbMatchList;

    private MyDialogFragment.ResetLayout dialogFrag = new MyDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            additionalComboValue.clearFocus();
//            orbRadioGroup.check(R.id.redOrb);
//            orbsLinked.setProgress(0);
//            orbsPlus.setProgress(0);
//            rowCheckBox.setEnabled(false);
//            rowCheckBox.setChecked(false);
//            ignoreEnemyCheckBox.setChecked(false);
//            maxLeadMultiplierCheckBox.setChecked(false);
//            additionalComboValue.setText("0");
            //team.clearOrbMatches();
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
            }
        }
    };


    private SeekBar.OnSeekBarChangeListener orbsLinkedSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            orbsLinkedValue.setText("" + (progress + 3));
            orbsPlus.setMax(progress + 3);
            if ((progress + 3) >= 6 && (progress + 3) != 7) {
                rowCheckBox.setEnabled(true);
            } else {
                rowCheckBox.setEnabled(false);
                rowCheckBox.setChecked(false);
            }
            if ((progress + 3) >= 26) {
                rowCheckBox.setEnabled(false);
                rowCheckBox.setChecked(true);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            additionalComboValue.clearFocus();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

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

    private Element getOrbColor() {
        int radioGroupId = orbRadioGroup.getCheckedRadioButtonId();
        switch (radioGroupId) {
            case R.id.redOrb:
                return Element.RED;
            case R.id.blueOrb:
                return Element.BLUE;
            case R.id.greenOrb:
                return Element.GREEN;
            case R.id.lightOrb:
                return Element.LIGHT;
            case R.id.darkOrb:
                return Element.DARK;
            case R.id.heartOrb:
                return Element.HEART;
        }
        return Element.RED;
    }

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
                    ((MainActivity) getActivity()).switchFragment(TeamDamageListFragment.newInstance(false, additionalCombos, team), TeamDamageListFragment.TAG);
                } else {
                    ((MainActivity) getActivity()).switchFragment(EnemyTargetFragment.newInstance(additionalCombos, team, enemy), EnemyTargetFragment.TAG);
                }
            }
        }
    };

    private View.OnClickListener addMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            additionalComboValue.clearFocus();
            orbMatch = new OrbMatch(orbsLinked.getProgress() + 3, orbsPlus.getProgress(), getOrbColor(), isRow);
            orbMatchRecycler.add(orbMatch);
            orbMatchRecycler.notifyDataSetChanged();
            Log.d("Orb Match Log", "Orb Match List is: " + orbMatchList + " orbMatches size is: " + orbMatches.getChildCount());
//         if(ignoreEnemyCheckBox.isChecked()){
//            calculateButton.setEnabled(true);
//         }
        }
    };

    private Button.OnClickListener resetOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if (orbMatchRecycler.getItemCount() == 0){
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "No matches to reset", Toast.LENGTH_SHORT);
                toast.show();
            }else {
                if (dialog == null) {
                    dialog = MyDialogFragment.newInstance(dialogFrag);
                }
                dialog.show(getChildFragmentManager(), "Thomas Likes Big Butts And He Cannot Lie");
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
                hideKeyboard(v);
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
            orbsLinked.setProgress(orbMatchList.get(position).getOrbsLinked() - 3);
            orbsPlus.setProgress(orbMatchList.get(position).getNumOrbPlus());
            rowCheckBox.setChecked(orbMatchList.get(position).isRow());
            switch(orbMatchList.get(position).getElement()){
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

    private ListView.OnItemClickListener orbMatchListViewOnClickListener = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            orbsLinked.setProgress(orbMatchList.get(position).getOrbsLinked() - 3);
            orbsPlus.setProgress(orbMatchList.get(position).getNumOrbPlus());
            rowCheckBox.setChecked(orbMatchList.get(position).isRow());
            switch(orbMatchList.get(position).getElement()){
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
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static MainFragment newInstance(Team team, Enemy enemy) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        rowCheckBox = (CheckBox) rootView.findViewById(R.id.rowCheckBox);
        maxLeadMultiplierCheckBox = (CheckBox) rootView.findViewById(R.id.maxLeadMultiplierCheckBox);
        ignoreEnemyCheckBox = (CheckBox) rootView.findViewById(R.id.ignoreEnemyCheckBox);
        orbsLinked = (SeekBar) rootView.findViewById(R.id.orbsLinkedSpinner);
        orbsPlus = (SeekBar) rootView.findViewById(R.id.orbsPlusSpinner);
        addMatch = (Button) rootView.findViewById(R.id.addMatch);
        calculateButton = (Button) rootView.findViewById(R.id.calculateButton);
        reset = (Button) rootView.findViewById(R.id.reset);
        orbMatches = (RecyclerView) rootView.findViewById(R.id.orbMatches);
        orbRadioGroup = (RadioGroup) rootView.findViewById(R.id.orbRadioGroup);
        //editTeam = (TextView) rootView.findViewById(R.id.editTeam);
        orbsLinkedValue = (TextView) rootView.findViewById(R.id.orbsLinkedValue);
        orbsPlusValue = (TextView) rootView.findViewById(R.id.orbsPlusValue);
        additionalComboValue = (EditText) rootView.findViewById(R.id.additionalComboValue);
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
        for(int i = 0; i < team.getMonsters().size(); i++){
            Log.d("Orb Match Log","Monster is: " + team.getMonsters(i) + " Awakenings are: " + team.getMonsters(i).getAwokenSkills());
        }
        Log.d("Orb Match Log", "Orb Plus Awakenings: " + team.getOrbPlusAwakenings());
        Log.d("Orb Match Log", "Team Name is: " + team.getTeamName() + " Team id: " + team.getTeamId() + " Team overwrite id: " + team.getTeamIdOverwrite());
        new DownloadPadApi().start();
        orbsLinked.setOnSeekBarChangeListener(orbsLinkedSeekBarChangeListener);
        orbsPlus.setOnSeekBarChangeListener(orbsPlusSeekBarChangeListener);
        rowCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        maxLeadMultiplierCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        ignoreEnemyCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
        addMatch.setOnClickListener(addMatchOnClickListener);
        reset.setOnClickListener(resetOnClickListener);
        if(OrbMatch.getAllOrbMatches().size() == 0){
            orbMatchList = new ArrayList<>();
        } else {
            orbMatchList = (ArrayList)OrbMatch.getAllOrbMatches();
        }
        orbMatchRecycler = new OrbMatchRecycler(getActivity(), orbMatchList, orbMatchOnClickListener);
        orbMatches.setAdapter(orbMatchRecycler);
        orbMatches.setLayoutManager(new LinearLayoutManager(getActivity()));
        additionalComboValue.addTextChangedListener(additionalComboTextWatcher);
        additionalComboValue.setOnFocusChangeListener(editTextOnFocusChange);
        calculateButton.setOnClickListener(calculateOnClickListener);

 //       orbMatches.setOnItemClickListener(orbMatchListViewOnClickListener);

        ignoreEnemyCheckBox.setChecked(Singleton.getInstance().isIgnoreEnemy());

        //Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Orb Match Log", "I am destroy view.");
        Log.d("Orb Match Log", "Load Orb Matches Before: " + OrbMatch.getAllOrbMatches());
        Log.d("Orb Match Log", "Orb Match List is: " + orbMatchList);
        for(int i = 0; i < OrbMatch.getAllOrbMatches().size(); i++){
            Log.d("Orb Match Log", "i is: " + i);
            Log.d("Orb Match Log", "Current Match is: " + OrbMatch.getAllOrbMatches().get(i) + " and is it in orbMatchList?: " + orbMatchList.contains(OrbMatch.getAllOrbMatches().get(i)));
            if(!orbMatchList.contains(OrbMatch.getAllOrbMatches().get(i))){
                OrbMatch.getAllOrbMatches().get(i).delete();
                i -= 1;
            }
        }
        if (orbMatchList.size() == 0){
            OrbMatch.deleteAllOrbMatches();
        }
        for (int i = 0; i < orbMatchList.size(); i++){
            if(!OrbMatch.getAllOrbMatches().contains(orbMatchList.get(i))){
                orbMatchList.get(i).save();
            }
        }
        team.updateOrbs();
        Log.d("Orb Match Log", "Load Orb Matches After: " + OrbMatch.getAllOrbMatches());
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
