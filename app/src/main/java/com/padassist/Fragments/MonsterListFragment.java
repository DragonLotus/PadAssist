package com.padassist.Fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.padassist.Adapters.MonsterListRecycler;
import com.padassist.BroadcastReceivers.JustAnotherBroadcastReceiver;
import com.padassist.Data.Monster;
import com.padassist.Data.Team;
import com.padassist.MainActivity;
import com.padassist.R;
import com.padassist.Util.ImageResourceUtil;

import org.parceler.Parcels;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterListFragment extends Fragment {
    public static final String TAG = MonsterListFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private RecyclerView monsterListView;
    private RealmList<Monster> monsters;
    private MonsterListRecycler monsterListRecycler;
//    private Button importButton, orbMatchButton;
    private ImageView favorite, favoriteOutline;
    private TextView teamName;
    private ImageView teamBadge;
    private Team team;
//    private Enemy enemy;
    private Toast toast;
    private TeamSaveDialogFragment teamSaveDialogFragment;
    private TeamBadgeDialogFragment teamBadgeDialogFragment;
    private ClearTeamConfirmationDialogFragment clearTeamConfirmationDialogFragment;
    private Realm realm;
    private Monster monster0;
    private LoadTeamConfirmationDialogFragment loadTeamConfirmationDialogFragment;
    private JustAnotherBroadcastReceiver broadcastReceiver;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MonsterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterListFragment newInstance(Parcelable team) {
        MonsterListFragment fragment = new MonsterListFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        fragment.setArguments(args);
        return fragment;
    }
    public static MonsterListFragment newInstance() {
        MonsterListFragment fragment = new MonsterListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_monster_list, container, false);
        monsterListView = (RecyclerView) rootView.findViewById(R.id.monsterListView);
//        importButton = (Button) rootView.findViewById(R.id.importButton);
//        orbMatchButton = (Button) rootView.findViewById(R.id.orbMatchButton);
        teamName = (TextView) rootView.findViewById(R.id.teamName);
        favorite = (ImageView) rootView.findViewById(R.id.favorite);
        favoriteOutline = (ImageView) rootView.findViewById(R.id.favoriteOutline);
        teamBadge = (ImageView) rootView.findViewById(R.id.teamBadge);

        return rootView;
    }

    // TODO: Rename method, updateAwakenings argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        onDeselect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.setGroupVisible(R.id.saveTeamGroup, true);
        menu.findItem(R.id.saveTeam).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Parcelable teamParcel = Parcels.wrap(team);
        switch (item.getItemId()) {
            case R.id.teamOverview:
                ((MainActivity) getActivity()).switchFragment(TeamOverviewFragment.newInstance(teamParcel), TeamOverviewFragment.TAG, "good");
                break;
            case R.id.saveTeam:
                if (team.getMonsters(0).getMonsterId() == 0) {
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Enter a leader before saving", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (teamSaveDialogFragment == null) {
                        teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam, team.getTeamIdOverwrite());
                    }
                    if (!teamSaveDialogFragment.isAdded()) {
                        teamSaveDialogFragment.show(getActivity().getSupportFragmentManager(), team.getTeamIdOverwrite(), "Show Team Save Dialog");
                    }
                }
                break;
            case R.id.toggleCoop:
                team.setTeamStats(realm);
                monsterListRecycler.notifyDataSetChanged();
                break;
            case R.id.loadTeam:
                Monster monsterZero = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
//                Parcelable teamParcel = Parcels.wrap(team);
                if(team.getTeamIdOverwrite() == 0){
                    boolean notEqual = false;
                    for (int i = 0; i < monsters.size(); i++){
                        if(!monsters.get(i).equals(monsterZero)){
                            notEqual = true;
                        }
                    }
                    if(notEqual){
                        if (loadTeamConfirmationDialogFragment == null) {
                            loadTeamConfirmationDialogFragment = LoadTeamConfirmationDialogFragment.newInstance(loadTeamConfirmation);
                        }
                        if (!loadTeamConfirmationDialogFragment.isAdded()) {
                            loadTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Load Team Confirmation");
                        }
                    } else {
                        ((MainActivity)getActivity()).switchFragment(TeamListFragment.newInstance(teamParcel), TeamListFragment.TAG, "good");
                    }
                } else {
                    Team teamOverwrite = realm.where(Team.class).equalTo("teamId", team.getTeamIdOverwrite()).findFirst();
                    if(teamOverwrite != null){
                        if(!team.getMonsters().equals(teamOverwrite.getMonsters()) || !team.getTeamName().equals(teamOverwrite.getTeamName()) || team.getTeamBadge() != teamOverwrite.getTeamBadge()){
                            if (loadTeamConfirmationDialogFragment == null) {
                                loadTeamConfirmationDialogFragment = LoadTeamConfirmationDialogFragment.newInstance(loadTeamConfirmation);
                            }
                            if (!loadTeamConfirmationDialogFragment.isAdded()) {
                                loadTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Load Team Confirmation");
                            }
                        } else {
                            ((MainActivity)getActivity()).switchFragment(TeamListFragment.newInstance(teamParcel), TeamListFragment.TAG, "good");
                        }
                    } else {
                        boolean notEqual = false;
                        for (int i = 0; i < monsters.size(); i++){
                            if(!monsters.get(i).equals(monsterZero)){
                                notEqual = true;
                            }
                        }
                        if(notEqual){
                            if (loadTeamConfirmationDialogFragment == null) {
                                loadTeamConfirmationDialogFragment = LoadTeamConfirmationDialogFragment.newInstance(loadTeamConfirmation);
                            }
                            if (!loadTeamConfirmationDialogFragment.isAdded()) {
                                loadTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Load Team Confirmation");
                            }
                        } else {
                            ((MainActivity)getActivity()).switchFragment(TeamListFragment.newInstance(teamParcel), TeamListFragment.TAG, "good");
                        }
                    }

                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private LoadTeamConfirmationDialogFragment.ResetLayout loadTeamConfirmation = new LoadTeamConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            loadTeamConfirmationDialogFragment.dismiss();
            Parcelable teamParcel = Parcels.wrap(team);
            ((MainActivity)getActivity()).switchFragment(TeamListFragment.newInstance(teamParcel), TeamListFragment.TAG, "good");
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("monsters", monsters);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = Parcels.unwrap(getArguments().getParcelable("team"));
        }

        realm = Realm.getDefaultInstance();
        monster0 = realm.where(Monster.class).equalTo("monsterId", 0).findFirst();
        team = realm.where(Team.class).equalTo("teamId", 0).findFirst();
        monsters = team.getMonsters();

//        if (savedInstanceState != null) {
//            monsters = savedInstanceState.getParcelableArrayList("monsters");
//        } else {
//            monsters = team.getMonsters();
//            if (monsters == null || monsters.size() == 0 || monsters.contains(null)) {
//                monsters = new ArrayList<Monster>();
//                for (int i = 0; i < 6; i++) {
//                    monsters.add(monster0);
//                }
//
//                updateTeam();
//            }
//
//            team.updateAwakenings();
//            monsters = team.getMonsters();
//        }

        teamName.setText(team.getTeamName());
        if (team.isFavorite()) {
            favorite.setVisibility(View.VISIBLE);
        } else {
            favorite.setVisibility(View.INVISIBLE);
        }
        setTeamBadge();
        teamName.setSelected(true);
        teamName.setHorizontallyScrolling(true);

        monsterListRecycler = new MonsterListRecycler(getActivity(), monsters, team);
        monsterListView.setAdapter(monsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        importButton.setOnClickListener(importButtonOnClickListener);
//        orbMatchButton.setOnClickListener(orbMatchOnClickListener);
        favorite.setColorFilter(0xFFFFAADD);
        favoriteOutline.setOnClickListener(favoriteOnClickListener);
        teamBadge.setOnClickListener(teamBadgeOnClickListener);
//        getActivity().setTitle("Set Team");
    }

    private View.OnClickListener importButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            ((MainActivity) getActivity()).switchFragment(TeamListFragment.newInstance(), TeamListFragment.TAG);
//            team.updateLeaderSkills();
//            team.save();
        }
    };

    private View.OnClickListener orbMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (team.getLead().getMonsterId() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
//                team.updateLeaderSkills();
//                team.save();

//                Parcelable enemyParcel = Parcels.wrap(enemy);
//                ((MainActivity) getActivity()).switchFragment(OrbMatchFragment.newInstance(teamParcel, enemyParcel), OrbMatchFragment.TAG, "good");
            }
        }
    };

    private View.OnClickListener favoriteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (team.getTeamIdOverwrite() != 0) {
                realm.beginTransaction();
                if (!team.isFavorite()) {
                    team.setFavorite(true);
                    favorite.setVisibility(View.VISIBLE);
                } else {
                    team.setFavorite(false);
                    favorite.setVisibility(View.INVISIBLE);
                }
//                Team overwriteTeam = realm.copyFromRealm(team);
//                overwriteTeam.setTeamId(team.getTeamIdOverwrite());
                realm.commitTransaction();

            }

        }
    };

    private View.OnClickListener teamBadgeOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (teamBadgeDialogFragment == null) {
                teamBadgeDialogFragment = TeamBadgeDialogFragment.newInstance(setTeamBadge);
            }
            if (!teamBadgeDialogFragment.isAdded()) {
                teamBadgeDialogFragment.show(getActivity().getSupportFragmentManager(), team, "Team Badge Dialog");
            }
        }
    };

    private TeamBadgeDialogFragment.SetTeamBadge setTeamBadge = new TeamBadgeDialogFragment.SetTeamBadge() {
        @Override
        public void setBadge(int badge) {
            realm.beginTransaction();
            team.setTeamBadge(badge);
            if(team.getIsBound().get(0) && badge == 8){
                team.getIsBound().set(0, false);
            }
            realm.commitTransaction();
            setTeamBadge();
        }
    };

    private void setTeamBadge(){
        teamBadge.setImageResource(ImageResourceUtil.teamBadge(team.getTeamBadge()));
    }

    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
        @Override
        public void overwriteTeam() {
            Team overwriteTeam = realm.copyFromRealm(team);
            overwriteTeam.setTeamId(team.getTeamIdOverwrite());
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(overwriteTeam);
            realm.commitTransaction();
        }

        @Override
        public void saveNewTeam(String teamNameString) {
            RealmResults<Team> results = realm.where(Team.class).findAllSorted("teamId");
            long newTeamId = results.get(results.size() - 1).getTeamId() + 1;
            realm.beginTransaction();
            team.setTeamIdOverwrite(newTeamId);
            team.setTeamName(teamNameString);
            Team newTeam = realm.copyFromRealm(team);
            newTeam.setTeamId(newTeamId);
            realm.copyToRealmOrUpdate(newTeam);
            realm.commitTransaction();
            teamName.setText(teamNameString);
        }

        @Override
        public void clearTeam() {
            Team teamOverwrite = realm.where(Team.class).equalTo("teamId", team.getTeamIdOverwrite()).findFirst();
            if (team.getTeamIdOverwrite() == 0) {
                for (int i = 0; i < monsters.size(); i++) {
                    if (!monsters.get(i).equals(monster0)) {
                        teamSaveDialogFragment.dismiss();
                        if (clearTeamConfirmationDialogFragment == null) {
                            clearTeamConfirmationDialogFragment = ClearTeamConfirmationDialogFragment.newInstance(clearTeam);
                        }
                        if (!clearTeamConfirmationDialogFragment.isAdded()) {
                            clearTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Clear confirmation");
                        }
                        break;
                    }
                }
            } else if(teamOverwrite != null){
                if (!team.getMonsters().equals(teamOverwrite.getMonsters()) || !team.getTeamName().equals(teamOverwrite.getTeamName()) || team.getTeamBadge() != teamOverwrite.getTeamBadge()) {
                    teamSaveDialogFragment.dismiss();
                    if (clearTeamConfirmationDialogFragment == null) {
                        clearTeamConfirmationDialogFragment = ClearTeamConfirmationDialogFragment.newInstance(clearTeam);
                    }
                    if (!clearTeamConfirmationDialogFragment.isAdded()) {
                        clearTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Clear confirmation");
                    }
                } else {
                    clearTeamMethod();
                }
            } else {
                clearTeamMethod();
            }
        }
    };

    private ClearTeamConfirmationDialogFragment.ResetLayout clearTeam = new ClearTeamConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            clearTeamMethod();
            clearTeamConfirmationDialogFragment.dismiss();
        }
    };

    private void clearTeamMethod(){
        Log.d("MonsterListFragment", "monster0 is: " + monster0);
        realm.beginTransaction();
        team.initializeMonsters(monster0);
        monsterListRecycler.notifyDataSetChanged();
        team.setTeamName("Untitled Team");
        team.setTeamIdOverwrite(0);
        team.setFavorite(false);
        team.setTeamBadge(0);
        realm.commitTransaction();
        teamName.setText(team.getTeamName());
        favorite.setVisibility(View.INVISIBLE);
        teamBadge.setImageResource(R.drawable.team_badge_nothing);
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new JustAnotherBroadcastReceiver(receiverMethods);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("REFRESH_MONSTER_LIST"));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter("ONDESELECT_ORB_MATCH"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    private JustAnotherBroadcastReceiver.receiverMethods receiverMethods = new JustAnotherBroadcastReceiver.receiverMethods() {
        @Override
        public void onReceiveMethod(Intent intent) {
            switch(intent.getAction()){
                case "ONDESELECT_MONSTER_LIST":
                    onDeselect();
                    break;
                case "REFRESH_MONSTER_LIST":
                    onSelect();
                    break;
            }
        }
    };

    public void onSelect(){
        Log.d("MonsterListFragment", "onSelect");
    }

    public void onDeselect(){
    }

}
