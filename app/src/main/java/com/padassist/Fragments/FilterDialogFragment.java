package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.padassist.Adapters.AwakeningGridFilterAdapter;
import com.padassist.Adapters.TypeGridAdapter;
import com.padassist.Data.Element;
import com.padassist.Graphics.ExpandableHeightGridView;
import com.padassist.R;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class FilterDialogFragment extends DialogFragment {
    private RadioGroup element1RadioGroup, element2RadioGroup;
    private CheckBox redOrb1, blueOrb1, greenOrb1, lightOrb1, darkOrb1, redOrb2, blueOrb2, greenOrb2, lightOrb2, darkOrb2, requirementCheck;
    private ExpandableHeightGridView typeGrid;
    private ExpandableHeightGridView awakeningsGrid;
    private SaveTeam saveTeam;
    private Toast toast;
    private ArrayList<Integer> typeList;
    private TypeGridAdapter typeGridAdapter;
    private ArrayList<Integer> awakeningsList;
    private AwakeningGridFilterAdapter awakeningsGridAdapter;
    private Button clearButton;

    public interface SaveTeam {
        public void update();
    }

    public static FilterDialogFragment newInstance(SaveTeam saveTeam) {
        FilterDialogFragment dialogFragment = new FilterDialogFragment();
        dialogFragment.setSaveTeam(saveTeam);
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        View rootView = View.inflate(getActivity(), R.layout.fragment_filter_dialog, null);
        element1RadioGroup = (RadioGroup) rootView.findViewById(R.id.element1RadioGroup);
        element2RadioGroup = (RadioGroup) rootView.findViewById(R.id.element2RadioGroup);
        typeGrid = (ExpandableHeightGridView) rootView.findViewById(R.id.typeGrid);
        awakeningsGrid = (ExpandableHeightGridView) rootView.findViewById(R.id.awakeningsGrid);
        redOrb1 = (CheckBox) rootView.findViewById(R.id.redOrb1);
        blueOrb1 = (CheckBox) rootView.findViewById(R.id.blueOrb1);
        greenOrb1 = (CheckBox) rootView.findViewById(R.id.greenOrb1);
        lightOrb1 = (CheckBox) rootView.findViewById(R.id.lightOrb1);
        darkOrb1 = (CheckBox) rootView.findViewById(R.id.darkOrb1);
        redOrb2 = (CheckBox) rootView.findViewById(R.id.redOrb2);
        blueOrb2 = (CheckBox) rootView.findViewById(R.id.blueOrb2);
        greenOrb2 = (CheckBox) rootView.findViewById(R.id.greenOrb2);
        lightOrb2 = (CheckBox) rootView.findViewById(R.id.lightOrb2);
        darkOrb2 = (CheckBox) rootView.findViewById(R.id.darkOrb2);
        clearButton = (Button) rootView.findViewById(R.id.clearButton);
        requirementCheck = (CheckBox) rootView.findViewById(R.id.requirements);
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
        typeGridAdapter = new TypeGridAdapter(getActivity(), typeList, Singleton.getInstance().getFilterTypes());
        typeGrid.setAdapter(typeGridAdapter);
        typeGrid.setExpanded(true);
        typeGridAdapter.notifyEnable(true);
        awakeningsList = new ArrayList<>();
        for(int i = 1; i < 43; i++){
            awakeningsList.add(i);
        }
        awakeningsGridAdapter = new AwakeningGridFilterAdapter(getActivity(), awakeningsList, Singleton.getInstance().getFilterAwakenings());
        awakeningsGrid.setAdapter(awakeningsGridAdapter);
        awakeningsGrid.setExpanded(true);
        setElements();
        builder.setTitle("Filter Monsters");
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
                    saveTeam.update();
                    Log.d("FilterDialog", "Singleton filter elements 1 is: " + Singleton.getInstance().getFilterElement1());
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
        }
        redOrb1.setOnCheckedChangeListener(element1OnCheckedChangeListener);
        blueOrb1.setOnCheckedChangeListener(element1OnCheckedChangeListener);
        greenOrb1.setOnCheckedChangeListener(element1OnCheckedChangeListener);
        lightOrb1.setOnCheckedChangeListener(element1OnCheckedChangeListener);
        darkOrb1.setOnCheckedChangeListener(element1OnCheckedChangeListener);
        redOrb2.setOnCheckedChangeListener(element2OnCheckedChangeListener);
        blueOrb2.setOnCheckedChangeListener(element2OnCheckedChangeListener);
        greenOrb2.setOnCheckedChangeListener(element2OnCheckedChangeListener);
        lightOrb2.setOnCheckedChangeListener(element2OnCheckedChangeListener);
        darkOrb2.setOnCheckedChangeListener(element2OnCheckedChangeListener);
        clearButton.setOnClickListener(clearOnClickListener);
        typeGrid.setOnItemClickListener(typeGridOnClickListener);
        awakeningsGrid.setOnItemClickListener(awakeningGridOnClickListener);
    }

    private Button.OnClickListener clearOnClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == clearButton){
                Singleton.getInstance().getFilterElement1().clear();
                Singleton.getInstance().getFilterElement2().clear();
                Singleton.getInstance().getFilterTypes().clear();
                Singleton.getInstance().getFilterAwakenings().clear();
                redOrb1.setChecked(false);
                blueOrb1.setChecked(false);
                greenOrb1.setChecked(false);
                lightOrb1.setChecked(false);
                darkOrb1.setChecked(false);
                redOrb2.setChecked(false);
                blueOrb2.setChecked(false);
                greenOrb2.setChecked(false);
                lightOrb2.setChecked(false);
                darkOrb2.setChecked(false);
                typeGridAdapter.notifyDataSetChanged();
            }
        }
    };

    private void setEnable(){

    }

    private void setElements() {
        for(int i = 0; i < Singleton.getInstance().getFilterElement1().size(); i++){
            switch (Singleton.getInstance().getFilterElement1().get(i)){
                case RED:
                    redOrb1.setChecked(true);
                    break;
                case BLUE:
                    blueOrb1.setChecked(true);
                    break;
                case GREEN:
                    greenOrb1.setChecked(true);
                    break;
                case LIGHT:
                    lightOrb1.setChecked(true);
                    break;
                case DARK:
                    darkOrb1.setChecked(true);
                    break;

            }
        }
        for(int i = 0; i < Singleton.getInstance().getFilterElement2().size(); i++){
            switch (Singleton.getInstance().getFilterElement2().get(i)){
                case RED:
                    redOrb2.setChecked(true);
                    break;
                case BLUE:
                    blueOrb2.setChecked(true);
                    break;
                case GREEN:
                    greenOrb2.setChecked(true);
                    break;
                case LIGHT:
                    lightOrb2.setChecked(true);
                    break;
                case DARK:
                    darkOrb2.setChecked(true);
                    break;

            }
        }
//        if (Singleton.getInstance().getFilterElement1().size() != 0) {
//            if (Singleton.getInstance().getFilterElement1().contains(Element.RED)) {
//                redOrb1.setChecked(true);
//            } else {
//                redOrb1.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement1().contains(Element.BLUE)) {
//                blueOrb1.setChecked(true);
//            } else {
//                blueOrb1.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement1().contains(Element.GREEN)) {
//                greenOrb1.setChecked(true);
//            } else {
//                greenOrb1.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement1().contains(Element.LIGHT)) {
//                lightOrb1.setChecked(true);
//            } else {
//                lightOrb1.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement1().contains(Element.DARK)) {
//                darkOrb1.setChecked(true);
//            } else {
//                darkOrb1.setChecked(false);
//            }
//        }
//        if (Singleton.getInstance().getFilterElement2().size() != 0) {
//            if (Singleton.getInstance().getFilterElement2().contains(Element.RED)) {
//                redOrb2.setChecked(true);
//            } else {
//                redOrb2.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement2().contains(Element.BLUE)) {
//                blueOrb2.setChecked(true);
//            } else {
//                blueOrb2.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement2().contains(Element.GREEN)) {
//                greenOrb2.setChecked(true);
//            } else {
//                greenOrb2.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement2().contains(Element.LIGHT)) {
//                lightOrb2.setChecked(true);
//            } else {
//                lightOrb2.setChecked(false);
//            }
//            if (Singleton.getInstance().getFilterElement2().contains(Element.DARK)) {
//                darkOrb2.setChecked(true);
//            } else {
//                darkOrb2.setChecked(false);
//            }
//        }
    }

    private GridView.OnItemClickListener typeGridOnClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Integer type;
            type = typeList.get(position);

            if (!Singleton.getInstance().getFilterTypes().contains(type)) {
                Singleton.getInstance().getFilterTypes().add(type);
            } else if (Singleton.getInstance().getFilterTypes().contains(type)) {
                Singleton.getInstance().getFilterTypes().remove(type);
            }
            typeGridAdapter.notifyDataSetChanged();
        }
    };

    private GridView.OnItemClickListener awakeningGridOnClickListener = new GridView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Integer awakening;
            awakening = awakeningsList.get(position);

            if (!Singleton.getInstance().getFilterAwakenings().contains(awakening)) {
                Singleton.getInstance().getFilterAwakenings().add(awakening);
            } else if (Singleton.getInstance().getFilterAwakenings().contains(awakening)) {
                Singleton.getInstance().getFilterAwakenings().remove(awakening);
            }
            awakeningsGridAdapter.notifyDataSetChanged();
        }
    };

    public void setSaveTeam(SaveTeam saveTeam) {
        this.saveTeam = saveTeam;
    }

    private CompoundButton.OnCheckedChangeListener element1OnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Element element = null;
            switch (buttonView.getId()) {
                case R.id.redOrb1:
                    element = Element.RED;
                    break;
                case R.id.blueOrb1:
                    element = Element.BLUE;
                    break;
                case R.id.greenOrb1:
                    element = Element.GREEN;
                    break;
                case R.id.lightOrb1:
                    element = Element.LIGHT;
                    break;
                case R.id.darkOrb1:
                    element = Element.DARK;
                    break;
            }
            if (isChecked) {
                if (!Singleton.getInstance().getFilterElement1().contains(element)) {
                    Singleton.getInstance().getFilterElement1().add(element);
                }
            } else {
                if (Singleton.getInstance().getFilterElement1().contains(element)) {
                    Singleton.getInstance().getFilterElement1().remove(element);
                }
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener element2OnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Element element = null;
            switch (buttonView.getId()) {
                case R.id.redOrb2:
                    element = Element.RED;
                    break;
                case R.id.blueOrb2:
                    element = Element.BLUE;
                    break;
                case R.id.greenOrb2:
                    element = Element.GREEN;
                    break;
                case R.id.lightOrb2:
                    element = Element.LIGHT;
                    break;
                case R.id.darkOrb2:
                    element = Element.DARK;
                    break;
            }
            if (isChecked) {
                if (!Singleton.getInstance().getFilterElement2().contains(element)) {
                    Singleton.getInstance().getFilterElement2().add(element);
                }
            } else {
                if (Singleton.getInstance().getFilterElement2().contains(element)) {
                    Singleton.getInstance().getFilterElement2().remove(element);
                }
            }
        }
    };

    private CheckBox.OnCheckedChangeListener enableOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            redOrb1.setEnabled(isChecked);
            blueOrb1.setEnabled(isChecked);
            greenOrb1.setEnabled(isChecked);
            lightOrb1.setEnabled(isChecked);
            darkOrb1.setEnabled(isChecked);
            redOrb2.setEnabled(isChecked);
            blueOrb2.setEnabled(isChecked);
            greenOrb2.setEnabled(isChecked);
            lightOrb2.setEnabled(isChecked);
            darkOrb2.setEnabled(isChecked);
            setElements();
            typeGrid.setClickable(isChecked);
        }
    };

    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }
}