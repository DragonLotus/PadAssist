package com.padassist.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.MonsterPageUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;

public class ManageMonsterPageFragment extends MonsterPageUtil {
    public static final String TAG = ManageMonsterPageFragment.class.getSimpleName();

    private Toast toast;
    private MonsterManageDialogFragment monsterManageDialogFragment;
    private ReplaceAllConfirmationDialogFragment replaceConfirmationDialog;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;

    public static ManageMonsterPageFragment newInstance(Monster monster) {
        ManageMonsterPageFragment fragment = new ManageMonsterPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putParcelable("monster", monster);
        return fragment;
    }

    public ManageMonsterPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            monster = getArguments().getParcelable("monster");
        }
        monsterRemove.setOnClickListener(maxButtons);

    }

    public Monster getMonster(){
        if(getArguments() != null){
            return getArguments().getParcelable("monster");
        } else return null;
    }

    private View.OnClickListener maxButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monsterManageDialogFragment == null) {
                monsterManageDialogFragment = MonsterManageDialogFragment.newInstance(removeMonster, monster);
            }
            monsterManageDialogFragment.show(getChildFragmentManager(), "Show Remove Monster", monster);

        }
    };

    private MonsterManageDialogFragment.RemoveMonster removeMonster = new MonsterManageDialogFragment.RemoveMonster() {
        @Override
        public void removeMonsterDatabase() {
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, 0);
            }
            deleteConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
            monsterManageDialogFragment.dismiss();
        }

        @Override
        public void favoriteMonster(boolean favorite) {
            monster.setFavorite(favorite);
            monster.save();
            setFavorite();
        }

        @Override
        public void replaceAllTeam() {
            if (replaceConfirmationDialog == null) {
                replaceConfirmationDialog = ReplaceAllConfirmationDialogFragment.newInstance(replaceAllMonster);
            }
            replaceConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
        }

        @Override
        public void evolveMonster(long baseMonsterId) {
            if (baseMonsterId != 0) {
                monster.setBaseMonster(BaseMonster.getMonsterId(baseMonsterId));
                monster.save();
                rarity.setText("" + monster.getRarity());
                initBackup();
                monsterPicture.setImageResource(monster.getMonsterPicture());
                monsterName.setText(monster.getName());
                showAwakenings();
                grayAwakenings();
                initializeEditTexts();
                setImageViews();
                monsterStats();
            }
        }
    };

    private ReplaceAllConfirmationDialogFragment.ResetLayout replaceAllMonster = new ReplaceAllConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(true, monster.getMonsterId(), 99), MonsterTabLayoutFragment.TAG, "good");
            replaceConfirmationDialog.dismiss();
        }
    };

    private DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout(int position) {
            ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
            Team newTeam;
            for (int i = 0; i < teamList.size(); i++) {
                newTeam = teamList.get(i);
                for (int j = 0; j < newTeam.getMonsters().size(); j++) {
                    if (newTeam.getMonsters().get(j).getMonsterId() == monster.getMonsterId()) {
                        newTeam.setMonsters(j, Monster.getMonsterId(0));
                    }
                }
                newTeam.save();
            }
            monster.delete();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };


}
