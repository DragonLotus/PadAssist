package com.padassist.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Spinner;

import com.padassist.Adapters.LatentSpinnerAdapter;
import com.padassist.Data.Monster;
import com.padassist.Data.RealmInt;
import com.padassist.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class LatentAwakeningDialogFragment extends DialogFragment {

    public interface ResetLatents {
        public void refreshLatents();
    }

    private Spinner latent1Spinner, latent2Spinner, latent3Spinner, latent4Spinner, latent5Spinner, latent6Spinner;
    private ArrayList<Integer> latents = new ArrayList<>();
    private LatentSpinnerAdapter latentSpinnerAdapter;
    private Monster monster;
    private ResetLatents setLatents;

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
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        if (latents.size() == 0) {
            for (int i = 0; i < 12; i++) {
                latents.add(i);
            }
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
            monster = getArguments().getParcelable("monster");
        }
        latentSpinnerAdapter = new LatentSpinnerAdapter(getActivity(), R.layout.latent_spinner_row, latents);
        latent1Spinner.setAdapter(latentSpinnerAdapter);
        latent2Spinner.setAdapter(latentSpinnerAdapter);
        latent3Spinner.setAdapter(latentSpinnerAdapter);
        latent4Spinner.setAdapter(latentSpinnerAdapter);
        latent5Spinner.setAdapter(latentSpinnerAdapter);
        latent6Spinner.setAdapter(latentSpinnerAdapter);
        latent1Spinner.setSelection(monster.getLatents().get(0).getValue());
        latent2Spinner.setSelection(monster.getLatents().get(1).getValue());
        latent3Spinner.setSelection(monster.getLatents().get(2).getValue());
        latent4Spinner.setSelection(monster.getLatents().get(3).getValue());
        latent5Spinner.setSelection(monster.getLatents().get(4).getValue());
        latent6Spinner.setSelection(monster.getLatents().get(5).getValue());
    }

    private void updateLatents(){
        monster.getLatents().set(0, new RealmInt(latent1Spinner.getSelectedItemPosition()));
        monster.getLatents().set(1, new RealmInt(latent2Spinner.getSelectedItemPosition()));
        monster.getLatents().set(2, new RealmInt(latent3Spinner.getSelectedItemPosition()));
        monster.getLatents().set(3, new RealmInt(latent4Spinner.getSelectedItemPosition()));
        monster.getLatents().set(4, new RealmInt(latent5Spinner.getSelectedItemPosition()));
        monster.getLatents().set(5, new RealmInt(latent6Spinner.getSelectedItemPosition()));
//        monster.save();
    }

    public void setLatents(ResetLatents setLatents){
        this.setLatents = setLatents;
    }

    public void show(FragmentManager manager, String tag, Monster monster) {
        super.show(manager, tag);
        this.monster = monster;
    }


}
