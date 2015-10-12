package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;


import com.example.anthony.damagecalculator.Adapters.EvolutionSpinnerAdapter;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;

public class MonsterRemoveDialogFragment extends DialogFragment {

    public interface RemoveMonster {
        public void removeMonsterDatabase();

        public void removeMonsterTeam();

        public void favoriteMonster(boolean favorite);

        public void replaceAllTeam();

        public void replaceMonster();

        public void evolveMonster(long baseMonsterId);

    }

    private RadioGroup choiceRadioGroup;
    private CheckBox favorite;
    private RemoveMonster remove;
    private static boolean favoriteBoolean;
    private Spinner evolutionSpinner;
    private ArrayList<Long> evolutions = new ArrayList<>();
    private EvolutionSpinnerAdapter evolutionSpinnerAdapter;
    private int evolutionPosition;
    //private Long[] evolutions;

    public static MonsterRemoveDialogFragment newInstance(RemoveMonster removeMonster, boolean favorite) {
        MonsterRemoveDialogFragment dialogFragment = new MonsterRemoveDialogFragment();
        dialogFragment.setRemove(removeMonster);
        Log.d("remove check3", "" + removeMonster);
        favoriteBoolean = favorite;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_monster_remove_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        favorite = (CheckBox) rootView.findViewById(R.id.favoriteCheck);
        evolutionSpinner = (Spinner) rootView.findViewById(R.id.evolutionSpinner);
        builder.setTitle("Monster Options");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("remove check", "" + remove);
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.removeTeam) {
                            remove.removeMonsterTeam();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.removeDatabase) {
                            remove.removeMonsterDatabase();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.replaceAllTeams) {
                            remove.replaceAllTeam();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.replaceTeam) {
                            remove.replaceMonster();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.evolveMonster) {
                            remove.evolveMonster(evolutions.get(evolutionPosition));
                        } else {
                            dialog.dismiss();
                        }
                        Log.d("Monster Remove Dialog", "favorite is: " + favorite.isChecked());
                        remove.favoriteMonster(favorite.isChecked());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("Monster Remove Dialog", "favoriteBoolean is: " + favoriteBoolean);
        favorite.setChecked(favoriteBoolean);
        if (favoriteBoolean) {
            choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(false);
        } else {
            choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(true);
        }
        favorite.setOnCheckedChangeListener(favoriteCheckListener);
        setupEvolutions();
        evolutionSpinnerAdapter = new EvolutionSpinnerAdapter(getActivity(), R.layout.evolution_spinner_row, R.id.monsterName, evolutions);
        evolutionSpinner.setAdapter(evolutionSpinnerAdapter);
        evolutionSpinner.setOnItemSelectedListener(evolutionSpinnerListener);
        choiceRadioGroup.setOnCheckedChangeListener(radioGroupCheckChangeListener);

    }

    private CheckBox.OnCheckedChangeListener favoriteCheckListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(false);
            } else {
                choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 1).setEnabled(true);
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener radioGroupCheckChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.evolveMonster:
                    evolutionSpinner.setEnabled(true);
                    break;
                default:
                    evolutionSpinner.setEnabled(false);
                    break;
            }
        }
    };

    public void setRemove(RemoveMonster remove) {
        Log.d("remove check2", "" + remove);
        this.remove = remove;
    }


    private void setupEvolutions() {
        if(evolutions.size() == 1){
            choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 2).setEnabled(false);
        }else {
            choiceRadioGroup.getChildAt(choiceRadioGroup.getChildCount() - 2).setEnabled(true);
            Monster evolveMonster = Monster.getMonsterId(Team.getTeamById(0).getMonsters().get(Team.getTeamById(0).getMonsterOverwrite()).getMonsterId());
            evolutions = evolveMonster.getEvolutions();
            evolutions.add(0, Long.valueOf(0));
            evolutionSpinner.setEnabled(false);
        }
//        evolutions = new Long[evolveMonster.getEvolutions().size()];
//        for (int i = 0; i < evolutions.length; i++){
//            evolutions[i] = evolveMonster.getEvolutions().get(i);
//        }
//        Log.d("Monster Remove Dialog", "evolutions is: " + evolutions);
    }

    private Spinner.OnItemSelectedListener evolutionSpinnerListener = new Spinner.OnItemSelectedListener(){
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            evolutionPosition = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    public void show(FragmentManager manager, String tag, boolean favorite) {
        super.show(manager, tag);
        favoriteBoolean = favorite;
    }
}
