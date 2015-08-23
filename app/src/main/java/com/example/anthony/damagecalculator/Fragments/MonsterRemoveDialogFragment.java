package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;


import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;
public class MonsterRemoveDialogFragment extends DialogFragment {

    public interface RemoveMonster{
        public void removeMonsterDatabase();
        public void removeMonsterTeam();
    }

    private RadioGroup choiceRadioGroup;
    private RemoveMonster remove;

    public static MonsterRemoveDialogFragment newInstance(RemoveMonster removeMonster){
        MonsterRemoveDialogFragment dialogFragment = new MonsterRemoveDialogFragment();
        dialogFragment.setRemove(removeMonster);
        Log.d("remove check3", "" + removeMonster);
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View rootView = View.inflate(getActivity(), R.layout.fragment_monster_remove_dialog, null);
        choiceRadioGroup = (RadioGroup) rootView.findViewById(R.id.choiceRadioGroup);
        builder.setTitle("Remove Monster?");
        builder.setView(rootView)
                // Add action buttons
                .setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("remove check", "" + remove);
                        if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.removeTeam) {
                            remove.removeMonsterTeam();
                        } else if (choiceRadioGroup.getCheckedRadioButtonId() == R.id.removeDatabase) {
                            remove.removeMonsterDatabase();
                        } else {
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    public void setRemove(RemoveMonster remove) {
        Log.d("remove check2", "" + remove);
        this.remove = remove;
    }
}
