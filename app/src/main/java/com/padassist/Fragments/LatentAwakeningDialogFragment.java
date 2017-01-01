package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.padassist.Adapters.LatentSpinnerAdapter;
import com.padassist.Comparators.NumberComparator;
import com.padassist.Data.Monster;
import com.padassist.Data.RealmInt;
import com.padassist.R;

import org.parceler.Parcels;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class LatentAwakeningDialogFragment extends DialogFragment {

    public interface ResetLatents {
        public void refreshLatents();
    }

    private Spinner latent1Spinner, latent2Spinner, latent3Spinner, latent4Spinner, latent5Spinner, latent6Spinner;
    private ArrayList<Integer> latents = new ArrayList<>();
    //    private ArrayList<Integer> tempLatents = new ArrayList<>();
    private LatentSpinnerAdapter latentSpinnerAdapter;
    private Monster monster;
    private ResetLatents setLatents;
    private Toast toast;
    private ArrayList<Integer> balancedLatents = new ArrayList<>();
    private ArrayList<Integer> physicalLatents = new ArrayList<>();
    private ArrayList<Integer> healerLatents = new ArrayList<>();
    private ArrayList<Integer> dragonLatents = new ArrayList<>();
    private ArrayList<Integer> godLatents = new ArrayList<>();
    private ArrayList<Integer> attackerLatents = new ArrayList<>();
    private ArrayList<Integer> devilLatents = new ArrayList<>();
    private ArrayList<Integer> machineLatents = new ArrayList<>();
    private NumberComparator numberComparator =  new NumberComparator();

    public static LatentAwakeningDialogFragment newInstance(ResetLatents setLatents, Parcelable monster) {
        LatentAwakeningDialogFragment dialogFragment = new LatentAwakeningDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("monster", monster);
        dialogFragment.setLatents(setLatents);
//        favoriteBoolean = favorite;
//        monsterEvolve = monster;
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_latent_awakening_dialog, null);
        latent1Spinner = (Spinner) rootView.findViewById(R.id.latent1Spinner);
        latent2Spinner = (Spinner) rootView.findViewById(R.id.latent2Spinner);
        latent3Spinner = (Spinner) rootView.findViewById(R.id.latent3Spinner);
        latent4Spinner = (Spinner) rootView.findViewById(R.id.latent4Spinner);
        latent5Spinner = (Spinner) rootView.findViewById(R.id.latent5Spinner);
        latent6Spinner = (Spinner) rootView.findViewById(R.id.latent6Spinner);
        try {
            int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getContext().getResources().getDisplayMetrics());
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(latent1Spinner);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(latent2Spinner);
            android.widget.ListPopupWindow popupWindow3 = (android.widget.ListPopupWindow) popup.get(latent3Spinner);
            android.widget.ListPopupWindow popupWindow4 = (android.widget.ListPopupWindow) popup.get(latent4Spinner);
            android.widget.ListPopupWindow popupWindow5 = (android.widget.ListPopupWindow) popup.get(latent5Spinner);
            android.widget.ListPopupWindow popupWindow6 = (android.widget.ListPopupWindow) popup.get(latent6Spinner);
            popupWindow1.setHeight(px);
            popupWindow2.setHeight(px);
            popupWindow3.setHeight(px);
            popupWindow4.setHeight(px);
            popupWindow5.setHeight(px);
            popupWindow6.setHeight(px);
        } catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        builder.setTitle("Choose Latent Awakenings");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        updateLatents();
                        setLatents.refreshLatents();
                        dialog.dismiss();
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
        if (getArguments() != null) {
            monster = Parcels.unwrap(getArguments().getParcelable("monster"));
        }
//        for(int i = 0; i < monster.getLatents().size(); i++){
//            tempLatents.add(monster.getLatents().get(i).getValue());
//        }
        if (latents.size() == 0) {
            for (int i = 0; i < 13; i++) {
                latents.add(i);
            }
        }
        setLatentArrays();
        for(int i = 0; i < monster.getTypes().size(); i++){
            switch(monster.getTypes().get(i)){
                case 1:
                    for(int j = 0; j < balancedLatents.size(); j++){
                        if(!latents.contains(balancedLatents.get(j))){
                            latents.add(balancedLatents.get(j));
                        }
                    }
                    break;
                case 2:
                    for(int j = 0; j < physicalLatents.size(); j++){
                        if(!latents.contains(physicalLatents.get(j))){
                            latents.add(physicalLatents.get(j));
                        }
                    }
                    break;
                case 3:
                    for(int j = 0; j < healerLatents.size(); j++){
                        if(!latents.contains(healerLatents.get(j))){
                            latents.add(healerLatents.get(j));
                        }
                    }
                    break;
                case 4:
                    for(int j = 0; j < dragonLatents.size(); j++){
                        if(!latents.contains(dragonLatents.get(j))){
                            latents.add(dragonLatents.get(j));
                        }
                    }
                    break;
                case 5:
                    for(int j = 0; j < godLatents.size(); j++){
                        if(!latents.contains(godLatents.get(j))){
                            latents.add(godLatents.get(j));
                        }
                    }
                    break;
                case 6:
                    for(int j = 0; j < attackerLatents.size(); j++){
                        if(!latents.contains(attackerLatents.get(j))){
                            latents.add(attackerLatents.get(j));
                        }
                    }
                    break;
                case 7:
                    for(int j = 0; j < devilLatents.size(); j++){
                        if(!latents.contains(devilLatents.get(j))){
                            latents.add(devilLatents.get(j));
                        }
                    }
                    break;
                case 8:
                    for(int j = 0; j < machineLatents.size(); j++){
                        if(!latents.contains(machineLatents.get(j))){
                            latents.add(machineLatents.get(j));
                        }
                    }
                    break;
            }
        }
        Collections.sort(latents, numberComparator);

        latentSpinnerAdapter = new LatentSpinnerAdapter(getActivity(), R.layout.latent_spinner_row, latents);
        latent1Spinner.setAdapter(latentSpinnerAdapter);
        latent2Spinner.setAdapter(latentSpinnerAdapter);
        latent3Spinner.setAdapter(latentSpinnerAdapter);
        latent4Spinner.setAdapter(latentSpinnerAdapter);
        latent5Spinner.setAdapter(latentSpinnerAdapter);
        latent6Spinner.setAdapter(latentSpinnerAdapter);
        latent1Spinner.setSelection(latents.indexOf(monster.getLatents().get(0).getValue()), false);
        latent2Spinner.setSelection(latents.indexOf(monster.getLatents().get(1).getValue()), false);
        latent3Spinner.setSelection(latents.indexOf(monster.getLatents().get(2).getValue()), false);
        latent4Spinner.setSelection(latents.indexOf(monster.getLatents().get(3).getValue()), false);
        latent5Spinner.setSelection(latents.indexOf(monster.getLatents().get(4).getValue()), false);
        latent6Spinner.setSelection(latents.indexOf(monster.getLatents().get(5).getValue()), false);
        if(monster.getLatents().get(0).getValue() > 11){
            if(monster.getLatents().get(0).getValue() == monster.getLatents().get(1).getValue()){
                latent2Spinner.setEnabled(false);
            } else {
                latent2Spinner.setEnabled(true);
            }
        }
        if(monster.getLatents().get(1).getValue() > 11 && latent2Spinner.isEnabled()){
            if (monster.getLatents().get(1).getValue() == monster.getLatents().get(2).getValue()){
                latent3Spinner.setEnabled(false);
            } else {
                latent3Spinner.setEnabled(true);
            }
        }
        if (monster.getLatents().get(2).getValue() > 11 && latent3Spinner.isEnabled()){
            if (monster.getLatents().get(2).getValue() == monster.getLatents().get(3).getValue()){
                latent4Spinner.setEnabled(false);
            } else {
                latent4Spinner.setEnabled(true);
            }
        }
        if (monster.getLatents().get(3).getValue() > 11 && latent4Spinner.isEnabled()){
            if (monster.getLatents().get(3).getValue() == monster.getLatents().get(4).getValue()){
                latent5Spinner.setEnabled(false);
            } else {
                latent5Spinner.setEnabled(true);
            }
        }
        if (monster.getLatents().get(4).getValue() > 11 && latent5Spinner.isEnabled()){
            if (monster.getLatents().get(4).getValue() == monster.getLatents().get(5).getValue()){
                latent6Spinner.setEnabled(false);
            } else {
                latent6Spinner.setEnabled(true);
            }
        }
        if (monster.getTotalPlus() == 297 && latent6Spinner.isEnabled()) {
            latent6Spinner.setEnabled(true);
        } else {
            latent6Spinner.setEnabled(false);
        }
        latent1Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        latent2Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        latent3Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        latent4Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        latent5Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        latent6Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
    }

    private Spinner.OnItemSelectedListener spinnerOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (adapterView.getId() == latent1Spinner.getId()) {
                latent1Spinner.setOnItemSelectedListener(null);
                latent2Spinner.setOnItemSelectedListener(null);
                if (latents.get(position) > 11) {
                    if (latents.get(latent2Spinner.getSelectedItemPosition()) > 11 && latent2Spinner.isEnabled()) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                        toast.show();
                        latent1Spinner.setSelection(0, false);
                    } else {
                        latent2Spinner.setEnabled(false);
                        latent2Spinner.setSelection(position, false);
//                            tempLatents.set(0, latents.get(latent1Spinner.getSelectedItemPosition()));
//                            tempLatents.set(1, latents.get(latent2Spinner.getSelectedItemPosition()));
                    }
                } else {
                    latent2Spinner.setEnabled(true);
                    if (latents.get(latent2Spinner.getSelectedItemPosition()) > 11 && latent3Spinner.isEnabled()) {
                        if (monster.getLatents().get(1).getValue() > 11) {
                            latent2Spinner.setSelection(0, false);
                        } else {
                            latent2Spinner.setSelection(monster.getLatents().get(1).getValue(), false);
                        }
                    }
                }
                latent1Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent2Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            } else if (adapterView.getId() == latent2Spinner.getId()) {
                latent2Spinner.setOnItemSelectedListener(null);
                latent3Spinner.setOnItemSelectedListener(null);
                if (latents.get(position) > 11) {
                    if (latents.get(latent3Spinner.getSelectedItemPosition()) > 11 && latent3Spinner.isEnabled()) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                        toast.show();
                        latent2Spinner.setSelection(0, false);
                    } else {
                        latent3Spinner.setEnabled(false);
                        latent3Spinner.setSelection(position, false);
                    }
                } else {
                    latent3Spinner.setEnabled(true);
                    if (latents.get(latent3Spinner.getSelectedItemPosition()) > 11 && latent4Spinner.isEnabled()) {
                        if (monster.getLatents().get(2).getValue() > 11) {
                            latent3Spinner.setSelection(0, false);
                        } else {
                            latent3Spinner.setSelection(latents.indexOf(monster.getLatents().get(2).getValue()), false);
                        }
                    }
                }
                latent2Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent3Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            } else if (adapterView.getId() == latent3Spinner.getId()) {
                latent3Spinner.setOnItemSelectedListener(null);
                latent4Spinner.setOnItemSelectedListener(null);
                if (latents.get(position) > 11) {
                    if (latents.get(latent4Spinner.getSelectedItemPosition()) > 11 && latent4Spinner.isEnabled()) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                        toast.show();
                        latent3Spinner.setSelection(0, false);
                    } else {
                        latent4Spinner.setEnabled(false);
                        latent4Spinner.setSelection(position, false);
                    }
                } else {
                    latent4Spinner.setEnabled(true);
                    if (latents.get(latent4Spinner.getSelectedItemPosition()) > 11 && latent5Spinner.isEnabled()) {
                        if (monster.getLatents().get(3).getValue() > 11) {
                            latent4Spinner.setSelection(0, false);
                        } else {
                            latent4Spinner.setSelection(latents.indexOf(monster.getLatents().get(3).getValue()), false);
                        }
                    }
                }
                latent3Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent4Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            } else if (adapterView.getId() == latent4Spinner.getId()) {
                latent4Spinner.setOnItemSelectedListener(null);
                latent5Spinner.setOnItemSelectedListener(null);
                if (latents.get(position) > 11) {
                    if (latents.get(latent5Spinner.getSelectedItemPosition()) > 11 && latent5Spinner.isEnabled()) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                        toast.show();
                        latent4Spinner.setSelection(0, false);
                    } else {
                        latent5Spinner.setEnabled(false);
                        latent5Spinner.setSelection(position, false);
                    }
                } else {
                    latent5Spinner.setEnabled(true);
                    if (monster.getTotalPlus() == 297) {
                        if (latents.get(latent5Spinner.getSelectedItemPosition()) > 11 && latent6Spinner.isEnabled()) {
                            if (monster.getLatents().get(4).getValue() > 11) {
                                latent5Spinner.setSelection(0, false);
                            } else {
                                latent5Spinner.setSelection(latents.indexOf(monster.getLatents().get(4).getValue()), false);
                            }
                        }
                    } else {
                        if (latents.get(latent5Spinner.getSelectedItemPosition()) > 11) {
                            if (monster.getLatents().get(4).getValue() > 11) {
                                latent5Spinner.setSelection(0, false);
                            } else {
                                latent5Spinner.setSelection(latents.indexOf(monster.getLatents().get(4).getValue()), false);
                            }
                        }
                    }

                }
                latent4Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent5Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            } else if (adapterView.getId() == latent5Spinner.getId()) {
                latent4Spinner.setOnItemSelectedListener(null);
                latent5Spinner.setOnItemSelectedListener(null);
                latent6Spinner.setOnItemSelectedListener(null);
                if (monster.getTotalPlus() == 297) {
                    if (latents.get(position) > 11) {
                        if (latents.get(latent6Spinner.getSelectedItemPosition()) > 11 && latent6Spinner.isEnabled()) {
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                            toast.show();
                            latent5Spinner.setSelection(0, false);
                        } else {
                            latent6Spinner.setEnabled(false);
                            latent6Spinner.setSelection(position, false);
                        }
                    } else {
                        latent6Spinner.setEnabled(true);
                        if (latents.get(latent6Spinner.getSelectedItemPosition()) > 11) {
                            if (monster.getLatents().get(5).getValue() > 11) {
                                latent6Spinner.setSelection(0, false);
                            } else {
                                latent6Spinner.setSelection(latents.indexOf(monster.getLatents().get(5).getValue()), false);
                            }
                        }
                    }
                } else {
                    if (latents.get(position) > 11) {
                        if (latent4Spinner.isEnabled()) {
                            latent4Spinner.setSelection(position, false);
                            latent5Spinner.setEnabled(false);
                        } else {
                            latent5Spinner.setEnabled(true);
                            if (toast != null) {
                                toast.cancel();
                            }
                            toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                            toast.show();
                            latent5Spinner.setSelection(0, false);
                        }
                    }
                }
                latent4Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent5Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent6Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            } else if (adapterView.getId() == latent6Spinner.getId()) {
                latent5Spinner.setOnItemSelectedListener(null);
                latent6Spinner.setOnItemSelectedListener(null);
                if (latents.get(position) > 11) {
                    if (latent5Spinner.isEnabled()) {
                        latent5Spinner.setSelection(position, false);
                        latent6Spinner.setEnabled(false);

                    } else {
                        latent6Spinner.setEnabled(true);
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(getActivity(), "Not enough space for latent", Toast.LENGTH_SHORT);
                        toast.show();
                        latent6Spinner.setSelection(0, false);
                    }
                }
                latent5Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
                latent6Spinner.setOnItemSelectedListener(spinnerOnItemSelected);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void updateLatents() {
        monster.getLatents().set(0, new RealmInt(latents.get(latent1Spinner.getSelectedItemPosition())));
        monster.getLatents().set(1, new RealmInt(latents.get(latent2Spinner.getSelectedItemPosition())));
        monster.getLatents().set(2, new RealmInt(latents.get(latent3Spinner.getSelectedItemPosition())));
        monster.getLatents().set(3, new RealmInt(latents.get(latent4Spinner.getSelectedItemPosition())));
        monster.getLatents().set(4, new RealmInt(latents.get(latent5Spinner.getSelectedItemPosition())));
        monster.getLatents().set(5, new RealmInt(latents.get(latent6Spinner.getSelectedItemPosition())));
//        monster.save();
    }

    private void setLatentArrays(){
        balancedLatents.add(13);
        balancedLatents.add(14);
        balancedLatents.add(15);
        balancedLatents.add(16);
        balancedLatents.add(17);
        balancedLatents.add(18);
        balancedLatents.add(19);
        balancedLatents.add(20);
        devilLatents.add(13);
        machineLatents.add(13);
        machineLatents.add(17);
        healerLatents.add(14);
        healerLatents.add(18);
        godLatents.add(15);
        attackerLatents.add(15);
        attackerLatents.add(19);
        dragonLatents.add(16);
        dragonLatents.add(20);
        physicalLatents.add(16);
        physicalLatents.add(20);
    }

    public void setLatents(ResetLatents setLatents) {
        this.setLatents = setLatents;
    }

    public void show(FragmentManager manager, String tag, Monster monster) {
        super.show(manager, tag);
//        this.monster = monster;
    }


}
