package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.padassist.Adapters.TypeGridAdapter;
import com.padassist.Data.Element;
import com.padassist.Data.Team;
import com.padassist.Graphics.ExpandableHeightGridView;
import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class ExtraMultiplierDialogFragment extends DialogFragment {
    private RadioGroup elementRadioGroup;
    private CheckBox redOrb, blueOrb, greenOrb, lightOrb, darkOrb, enable, coopCheck, hasEnemyCheck;
    private ExpandableHeightGridView typeGrid;
    private EditText multiplier;
    private SaveTeam saveTeam;
    private Toast toast;
    private Team team;
    private ArrayList<Integer> typeList;
    private TypeGridAdapter typeGridAdapter;
    private Button clearButton;

    public interface SaveTeam {
        public void update();
    }

    public static ExtraMultiplierDialogFragment newInstance(SaveTeam saveTeam) {
        ExtraMultiplierDialogFragment dialogFragment = new ExtraMultiplierDialogFragment();
        dialogFragment.setSaveTeam(saveTeam);
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        View rootView = View.inflate(getActivity(), R.layout.fragment_extra_multiplier_dialog, null);
        elementRadioGroup = (RadioGroup) rootView.findViewById(R.id.elementRadioGroup);
        typeGrid = (ExpandableHeightGridView) rootView.findViewById(R.id.typeGrid);
        redOrb = (CheckBox) rootView.findViewById(R.id.redOrb);
        blueOrb = (CheckBox) rootView.findViewById(R.id.blueOrb);
        greenOrb = (CheckBox) rootView.findViewById(R.id.greenOrb);
        darkOrb = (CheckBox) rootView.findViewById(R.id.darkOrb);
        lightOrb = (CheckBox) rootView.findViewById(R.id.lightOrb);
        clearButton = (Button) rootView.findViewById(R.id.clearButton);
        multiplier = (EditText) rootView.findViewById(R.id.multiplier);
        enable = (CheckBox) rootView.findViewById(R.id.enable);
        coopCheck = (CheckBox) rootView.findViewById(R.id.coopCheck);
        hasEnemyCheck = (CheckBox) rootView.findViewById(R.id.hasEnemyCheck);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        typeList = new ArrayList<>();
        typeList.add(0);
        typeList.add(1);
        typeList.add(2);
        typeList.add(3);
        typeList.add(4);
        typeList.add(5);
        typeList.add(6);
        typeList.add(7);
        typeList.add(8);
        typeList.add(12);
        typeList.add(14);
        typeList.add(15);
        typeGridAdapter = new TypeGridAdapter(getActivity(), typeList, Singleton.getInstance().getExtraTypeMultiplier());
        typeGrid.setAdapter(typeGridAdapter);
        typeGrid.setExpanded(true);
        setEnable();
        multiplier.setText("" + Singleton.getInstance().getExtraDamageMultiplier());
        builder.setTitle("Options");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
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
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Singleton.getInstance().setCoopEnable(coopCheck.isChecked());
                    Singleton.getInstance().setHasEnemy(hasEnemyCheck.isChecked());
                    saveTeam.update();
                    Log.d("ExtraMultiplierDialog", "Singleton hasEnemy is: " + Singleton.getInstance().hasEnemy());
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
        }
        redOrb.setOnCheckedChangeListener(elementOnCheckedChangeListener);
        blueOrb.setOnCheckedChangeListener(elementOnCheckedChangeListener);
        greenOrb.setOnCheckedChangeListener(elementOnCheckedChangeListener);
        lightOrb.setOnCheckedChangeListener(elementOnCheckedChangeListener);
        darkOrb.setOnCheckedChangeListener(elementOnCheckedChangeListener);
        clearButton.setOnClickListener(clearOnClickListener);
        typeGrid.setOnItemClickListener(typeGridOnClickListener);
        multiplier.addTextChangedListener(multiplierTextWatcher);
        enable.setOnCheckedChangeListener(enableOnCheckedChangeListener);
    }

    private Button.OnClickListener clearOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == clearButton){
                Singleton.getInstance().getExtraElementMultiplier().clear();
                Singleton.getInstance().getExtraTypeMultiplier().clear();
                redOrb.setChecked(false);
                blueOrb.setChecked(false);
                greenOrb.setChecked(false);
                lightOrb.setChecked(false);
                darkOrb.setChecked(false);
                typeGridAdapter.notifyDataSetChanged();
            }
        }
    };

    private void setEnable(){
        if(Singleton.getInstance().isEnableMultiplier()){
            enable.setChecked(true);
            multiplier.setEnabled(true);
            for(int i = 0; i < elementRadioGroup.getChildCount(); i++){
                elementRadioGroup.getChildAt(i).setEnabled(true);
            }
            setElements();
            typeGridAdapter.notifyEnable(true);
        } else {
            enable.setChecked(false);
            multiplier.setEnabled(false);
            for(int i = 0; i < elementRadioGroup.getChildCount(); i++){
                elementRadioGroup.getChildAt(i).setEnabled(false);
            }
        }
        if(Singleton.getInstance().isCoopEnable()){
            coopCheck.setChecked(true);
        } else {
            coopCheck.setChecked(false);
        }
        if(Singleton.getInstance().hasEnemy()){
            hasEnemyCheck.setChecked(true);
        } else {
            hasEnemyCheck.setChecked(false);
        }
    }

    private void setElements() {
        for(int i = 0; i < Singleton.getInstance().getExtraElementMultiplier().size(); i++){
            switch (Singleton.getInstance().getExtraElementMultiplier().get(i)){
                case RED:
                    redOrb.setChecked(true);
                    break;
                case BLUE:
                    blueOrb.setChecked(true);
                    break;
                case GREEN:
                    greenOrb.setChecked(true);
                    break;
                case LIGHT:
                    lightOrb.setChecked(true);
                    break;
                case DARK:
                    darkOrb.setChecked(true);
                    break;

            }
        }
    }

    private GridView.OnItemClickListener typeGridOnClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            multiplier.clearFocus();
            Integer type;
            type = typeList.get(position);

            if (!Singleton.getInstance().getExtraTypeMultiplier().contains(type)) {
                Singleton.getInstance().getExtraTypeMultiplier().add(type);
            } else if (Singleton.getInstance().getExtraTypeMultiplier().contains(type)) {
                Singleton.getInstance().getExtraTypeMultiplier().remove(type);
            }
            typeGridAdapter.notifyDataSetChanged();
        }
    };

    public void setSaveTeam(SaveTeam saveTeam) {
        this.saveTeam = saveTeam;
    }

    private CompoundButton.OnCheckedChangeListener elementOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            multiplier.clearFocus();
            Element element = null;
            switch (buttonView.getId()) {
                case R.id.redOrb:
                    element = Element.RED;
                    break;
                case R.id.blueOrb:
                    element = Element.BLUE;
                    break;
                case R.id.greenOrb:
                    element = Element.GREEN;
                    break;
                case R.id.lightOrb:
                    element = Element.LIGHT;
                    break;
                case R.id.darkOrb:
                    element = Element.DARK;
                    break;
            }
            if (isChecked) {
                if (!Singleton.getInstance().getExtraElementMultiplier().contains(element)) {
                    Singleton.getInstance().getExtraElementMultiplier().add(element);
                }
            } else {
                if (Singleton.getInstance().getExtraElementMultiplier().contains(element)) {
                    Singleton.getInstance().getExtraElementMultiplier().remove(element);
                }
            }
        }
    };

    private CheckBox.OnCheckedChangeListener enableOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            multiplier.setEnabled(isChecked);
            redOrb.setEnabled(isChecked);
            blueOrb.setEnabled(isChecked);
            greenOrb.setEnabled(isChecked);
            lightOrb.setEnabled(isChecked);
            darkOrb.setEnabled(isChecked);
            setElements();
            typeGridAdapter.notifyEnable(isChecked);
            Singleton.getInstance().setEnableMultiplier(isChecked);
        }
    };

    private TextWatcher multiplierTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.toString().equals(""))
            {
                s = "0";
            }
            Singleton.getInstance().setExtraDamageMultiplier(Double.valueOf(s.toString()));
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void show(FragmentManager manager, Team team, String tag) {
        super.show(manager, tag);
        this.team = team;
    }
}