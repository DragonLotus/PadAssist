package com.padassist.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.MonsterPagerAdapter;
import com.padassist.Adapters.SaveMonsterListRecycler;
import com.padassist.Data.Element;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.Graphics.FastScroller;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.MonsterAlphabeticalComparator;
import com.padassist.Util.MonsterAtkComparator;
import com.padassist.Util.MonsterAwakeningComparator;
import com.padassist.Util.MonsterElement1Comparator;
import com.padassist.Util.MonsterElement2Comparator;
import com.padassist.Util.MonsterFavoriteComparator;
import com.padassist.Util.MonsterHpComparator;
import com.padassist.Util.MonsterLevelComparator;
import com.padassist.Util.MonsterNumberComparator;
import com.padassist.Util.MonsterPlusAtkComparator;
import com.padassist.Util.MonsterPlusComparator;
import com.padassist.Util.MonsterPlusHpComparator;
import com.padassist.Util.MonsterPlusRcvComparator;
import com.padassist.Util.MonsterRarityComparator;
import com.padassist.Util.MonsterRcvComparator;
import com.padassist.Util.MonsterType1Comparator;
import com.padassist.Util.MonsterType2Comparator;
import com.padassist.Util.MonsterType3Comparator;
import com.padassist.Util.SaveMonsterListUtil;
import com.padassist.Util.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;


public class SaveMonsterListFragment extends SaveMonsterListUtil {
    public static final String TAG = SaveMonsterListFragment.class.getSimpleName();
    private Toast toast;
    private boolean replaceAll;
    private long replaceMonsterId;
    private int monsterPosition;
    private Monster monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
    private DeleteMonsterConfirmationDialogFragment deleteConfirmationDialog;

    private FastScroller fastScroller;

    public static SaveMonsterListFragment newInstance(boolean replaceAll, long replaceMonsterId, int monsterPosition) {
        SaveMonsterListFragment fragment = new SaveMonsterListFragment();
        Bundle args = new Bundle();
        args.putBoolean("replaceAll", replaceAll);
        args.putLong("replaceMonsterId", replaceMonsterId);
        args.putInt("monsterPosition", monsterPosition);
        fragment.setArguments(args);
        return fragment;
    }

    public SaveMonsterListFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        saveMonsterListRecycler = new SaveMonsterListRecycler(getActivity(), monsterList, monsterListView, monsterListOnClickListener, monsterListOnLongClickListener, deleteOnClickListener);
        monsterListView.setAdapter(saveMonsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityCreatedSpecific() {
        if(monsterListAll == null){
            monsterListAll = new ArrayList<>();
        }
        if (getArguments() != null) {
            replaceAll = getArguments().getBoolean("replaceAll");
            replaceMonsterId = getArguments().getLong("replaceMonsterId");
            monsterPosition = getArguments().getInt("monsterPosition");
        }
        Log.d("SaveMonsterList", "monsterPosition is: " + monsterPosition + " replaceMonsterId is: " + replaceMonsterId);
        Log.d("SaveMonsterList", "realm monsters is: " + realm.where(Monster.class).findAll());
        if (monsterPosition == 5) {
            monsterListAll.clear();
            monsterListAll.addAll(realm.where(Monster.class).equalTo("helper", true).findAll());
            monsterListAll.add(monsterZero);
        } else {
            monsterListAll.clear();
            monsterListAll.addAll(realm.where(Monster.class).equalTo("helper", false).findAll());
        }
        Log.d("SaveMonsterList", "MonsterListAll is: " + monsterListAll);
    }

    private View.OnClickListener monsterListOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            Team newTeam = realm.where(Team.class).equalTo("teamId", 0).findFirst();
            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && monsterPosition == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if (replaceAll) {
                    ArrayList<Team> teamList = new ArrayList<>();
                    RealmResults results = realm.where(Team.class).findAll();
                    teamList.addAll(results);
                    for(int i = 0; i < teamList.size(); i++){
                        for(int j = 0; j < teamList.get(i).getMonsters().size(); j++){
                            if(teamList.get(i).getMonsters().get(j).getMonsterId() == replaceMonsterId){
                                realm.beginTransaction();
                                teamList.get(i).setMonsters(j, saveMonsterListRecycler.getItem(position));
                                realm.commitTransaction();
                            }
                        }
                    }
                } else {
                    realm.beginTransaction();
                    switch (monsterPosition) {
                        case 0:
                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
                            break;
                        case 1:
                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
                            break;
                        case 2:
                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
                            break;
                        case 3:
                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
                            break;
                        case 4:
                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
                            break;
                        case 5:
                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
                            break;
                    }
                    realm.commitTransaction();
                }

//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("monster", saveMonsterListRecycler.getItem(position));
//                bundle.putLong("monsterId", saveMonsterListRecycler.getItem(position).getMonsterId());
//                intent.putExtras(bundle);

//                intent.putExtra("monster", saveMonsterListRecycler.getItem(position));
//                intent.putExtra("monsterId", saveMonsterListRecycler.getItem(position).getMonsterId());

//                Log.d("SaveMonsterList", "monster is: " + intent.getParcelableExtra("monster") + " extra is: " + intent.getExtras());
//                getActivity().setResult(Activity.RESULT_OK, intent);
//                getActivity().finish();

                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
//                ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position), Singleton.getInstance().getMonsterOverwrite()), MonsterPageFragment.TAG, "good");
            }
        }
    };

    private View.OnLongClickListener monsterListOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag(R.string.index);
//            Team newTeam = new Team(Team.getTeamById(0));
//            if (saveMonsterListRecycler.getItem(position).getMonsterId() == 0 && Singleton.getInstance().getMonsterOverwrite() == 0) {
//                if (toast != null) {
//                    toast.cancel();
//                }
//                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
//                toast.show();
//            } else {
//                if (replaceAll) {
//                    ArrayList<Team> teamList = (ArrayList) Team.getAllTeamsAndZero();
//                    Team replaceTeam;
//                    for (int i = 0; i < teamList.size(); i++) {
//                        replaceTeam = teamList.get(i);
//                        for (int j = 0; j < replaceTeam.getMonsters().size(); j++) {
//                            if (replaceTeam.getMonsters().get(j).getMonsterId() == replaceMonsterId) {
//                                replaceTeam.setMonsters(j, saveMonsterListRecycler.getItem(position));
//                            }
//                        }
//                        replaceTeam.save();
//                    }
//                } else {
//
//                    switch (Singleton.getInstance().getMonsterOverwrite()) {
//                        case 0:
//                            newTeam.setLead(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 1:
//                            newTeam.setSub1(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 2:
//                            newTeam.setSub2(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 3:
//                            newTeam.setSub3(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 4:
//                            newTeam.setSub4(saveMonsterListRecycler.getItem(position));
//                            break;
//                        case 5:
//                            newTeam.setHelper(saveMonsterListRecycler.getItem(position));
//                            break;
//                    }
//                    newTeam.save();
//                }
//                getActivity().getSupportFragmentManager().popBackStack(MonsterListFragment.TAG, 0);
//                ((MainActivity)getActivity()).switchFragment(MonsterPageFragment.newInstance(saveMonsterListRecycler.getItem(position), Singleton.getInstance().getMonsterOverwrite()), MonsterPageFragment.TAG, "good");
//            }
            return true;
        }
    };


    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.string.index);
            if (deleteConfirmationDialog == null) {
                deleteConfirmationDialog = DeleteMonsterConfirmationDialogFragment.newInstance(deleteMonster, position);
            }
            deleteConfirmationDialog.show(getChildFragmentManager(), position, "Delete Confirmation");
        }
    };

    protected DeleteMonsterConfirmationDialogFragment.ResetLayout deleteMonster = new DeleteMonsterConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout(final int position) {
            ArrayList<Team> teamList = new ArrayList<>();
            RealmResults results = realm.where(Team.class).findAll();
            teamList.addAll(results);
            final long monsterId = saveMonsterListRecycler.getItem(position).getMonsterId();
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
//            for(int i = 0; i < monsterListAll.size(); i++){
//                if(monsterListAll.get(i).getMonsterId() == monsterId){
//                    monsterListAll.remove(i);
//                }
//            }
            Log.d("SaveMonsterList", "Team zero is: " + realm.where(Team.class).equalTo("teamId", 0).findFirst().getMonsters());
            monsterListAll.clear();
            monsterListAll.addAll(realm.where(Monster.class).findAll());
            monsterList.remove(position);
            saveMonsterListRecycler.notifyItemRemoved(position);
            saveMonsterListRecycler.notifyDataSetChanged(monsterList);
            saveMonsterListRecycler.setExpandedPosition(-1);
            emptyCheck();
        }
    };
}
