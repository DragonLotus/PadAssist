package com.padassist.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.BaseFragments.MonsterPageBase;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ManageMonsterPageFragment extends MonsterPageBase {
    public static final String TAG = ManageMonsterPageFragment.class.getSimpleName();

    private Toast toast;
    private MonsterManageDialogFragment monsterManageDialogFragment;
    private ReplaceAllConfirmationDialogFragment replaceConfirmationDialog;
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;

    public static ManageMonsterPageFragment newInstance(long monsterId) {
        ManageMonsterPageFragment fragment = new ManageMonsterPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putLong("monsterId", monsterId);
        return fragment;
    }

    public ManageMonsterPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            monsterId = getArguments().getLong("monsterId");
        }
        monsterRemove.setOnClickListener(maxButtons);

    }

    public Monster getMonster(){
        if(getArguments() != null){
//            return getArguments().getParcelable("monster");
            monsterId = getArguments().getLong("monsterId");
            Log.d("ManageMonsterPage", "monsterId is: " + monsterId);
            Monster monster = realm.where(Monster.class).equalTo("monsterId", monsterId).findFirst();
            monster = realm.copyFromRealm(monster);
            Log.d("ManageMonsterPage", "monster is: " + monster);
            return monster;
        } else return null;

    }

    private View.OnClickListener maxButtons = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (monsterManageDialogFragment == null) {
                monsterManageDialogFragment = MonsterManageDialogFragment.newInstance(removeMonster, monster);
            }
            if(!monsterManageDialogFragment.isAdded()){
                monsterManageDialogFragment.show(getChildFragmentManager(), "Show Remove Monster", monster);
            }
        }
    };

    private MonsterManageDialogFragment.RemoveMonster removeMonster = new MonsterManageDialogFragment.RemoveMonster() {
        @Override
        public void removeMonsterDatabase() {
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, 0);
            }
            if(!deleteConfirmationDialog.isAdded()){
                deleteConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
            }
//            monsterManageDialogFragment.dismiss();
        }

        @Override
        public void favoriteMonster(boolean favorite) {
            monster.setFavorite(favorite);
            setFavorite();
        }

        @Override
        public void replaceAllTeam() {
            if (replaceConfirmationDialog == null) {
                replaceConfirmationDialog = ReplaceAllConfirmationDialogFragment.newInstance(replaceAllMonster);
            }
            if(!replaceConfirmationDialog.isAdded()){
                replaceConfirmationDialog.show(getChildFragmentManager(), "Monster Replace All");
            }
        }

        @Override
        public void evolveMonster(long baseMonsterId) {
            evolveMonsterBase(baseMonsterId);
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
            ArrayList<Team> teamList = new ArrayList<>();
            RealmResults results = realm.where(Team.class).findAll();
            teamList.addAll(results);
            final long monsterId = monster.getMonsterId();
            Log.d("SaveMonsterList", "teamlist is: " + teamList);
            for (int i = 0; i < teamList.size(); i++) {
                for (int j = 0; j < teamList.get(i).getMonsters().size(); j++) {
                    if (teamList.get(i).getMonsters().get(j).getMonsterId() == monsterId) {
                        realm.beginTransaction();
                        teamList.get(i).setMonsters(j, realm.where(Monster.class).equalTo("monsterId", 0).findFirst());
                        realm.commitTransaction();
                        Log.d("SaveMonsterList", "team " + i + " monsters is: " + teamList.get(i).getMonsters());
                    }
                }
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(Monster.class).equalTo("monsterId", monsterId).findFirst().deleteFromRealm();
                }
            });
            getActivity().getSupportFragmentManager().popBackStack();
        }
    };


}
