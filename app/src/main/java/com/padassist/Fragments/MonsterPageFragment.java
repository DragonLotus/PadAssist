package com.padassist.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Data.BaseMonster;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.TextWatcher.MyTextWatcher;
import com.padassist.Util.DamageCalculationUtil;
import com.padassist.Util.MonsterPageUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterPageFragment extends MonsterPageUtil {
    public static final String TAG = MonsterPageFragment.class.getSimpleName();

    private Toast toast;
    private MonsterRemoveDialogFragment monsterRemoveDialogFragment;
    private ReplaceAllConfirmationDialogFragment replaceConfirmationDialog;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;
    private int position;

    public static MonsterPageFragment newInstance(Monster monster, int position) {
        MonsterPageFragment fragment = new MonsterPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putParcelable("monster", monster);
        args.putInt("position", position);
        return fragment;
    }

    public MonsterPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            monster = getArguments().getParcelable("monster");
            position = getArguments().getInt("position");
        }
        Log.d("MonsterPage", "monster is: " + monster);
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
            if (monsterRemoveDialogFragment == null) {
                monsterRemoveDialogFragment = MonsterRemoveDialogFragment.newInstance(removeMonster, monster);
            }
            monsterRemoveDialogFragment.show(getChildFragmentManager(), "Show Remove Monster", monster);

        }
    };

    private MonsterRemoveDialogFragment.RemoveMonster removeMonster = new MonsterRemoveDialogFragment.RemoveMonster() {
        @Override
        public void removeMonsterDatabase() {
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, 0);
            }
            deleteConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
            monsterRemoveDialogFragment.dismiss();
        }

        @Override
        public void removeMonsterTeam() {
            if (Singleton.getInstance().getMonsterOverwrite() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Team newTeam = new Team(Team.getTeamById(0));
                switch (Singleton.getInstance().getMonsterOverwrite()) {
                    case 0:
                        newTeam.setLead(Monster.getMonsterId(0));
                        break;
                    case 1:
                        newTeam.setSub1(Monster.getMonsterId(0));
                        break;
                    case 2:
                        newTeam.setSub2(Monster.getMonsterId(0));
                        break;
                    case 3:
                        newTeam.setSub3(Monster.getMonsterId(0));
                        break;
                    case 4:
                        newTeam.setSub4(Monster.getMonsterId(0));
                        break;
                    case 5:
                        newTeam.setHelper(Monster.getMonsterId(0));
                        break;
                }
                for (int i = 0; i < newTeam.getMonsters().size(); i++) {
                }
                newTeam.save();
                monsterRemoveDialogFragment.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
            }
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
        public void replaceMonster() {
            ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(false, 1, position), MonsterTabLayoutFragment.TAG, "good");
            monsterRemoveDialogFragment.dismiss();
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
            ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(true, monster.getMonsterId(), position), MonsterTabLayoutFragment.TAG, "good");
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
