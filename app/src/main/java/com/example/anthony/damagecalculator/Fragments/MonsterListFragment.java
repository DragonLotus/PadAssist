package com.example.anthony.damagecalculator.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Adapters.MonsterListAdapter;
import com.example.anthony.damagecalculator.Adapters.MonsterListRecycler;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.BaseMonster;
import com.example.anthony.damagecalculator.Data.Team;
import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterListFragment extends AbstractFragment {
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
    private ArrayList<Monster> monsters;
    private MonsterListAdapter monsterListAdapter;
    private MonsterListRecycler monsterListRecycler;
    private Button importButton, orbMatchButton;
    private ImageView favorite, favoriteOutline;
    private TextView teamName;
    private Team team;
    private Enemy enemy;
    private Toast toast;
    private TeamSaveDialogFragment teamSaveDialogFragment;
    private ClearTeamConfirmationDialogFragment clearTeamConfirmationDialogFragment;
    private Monster monster0 = Monster.getMonsterId(0);

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MonsterListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MonsterListFragment newInstance(Team team, Enemy enemy) {
        MonsterListFragment fragment = new MonsterListFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public MonsterListFragment() {
        // Required empty public constructor
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
        importButton = (Button) rootView.findViewById(R.id.importButton);
        orbMatchButton = (Button) rootView.findViewById(R.id.orbMatchButton);
        teamName = (TextView) rootView.findViewById(R.id.teamName);
        favorite = (ImageView) rootView.findViewById(R.id.favorite);
        favoriteOutline = (ImageView) rootView.findViewById(R.id.favoriteOutline);
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        switch(item.getItemId()){
            case R.id.saveTeam:
                if(team.getMonsters(0).getMonsterId() == 0){
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getActivity(), "Enter a leader before saving", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    if (teamSaveDialogFragment == null) {
                        teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam, team.getTeamIdOverwrite());
                    }
                    teamSaveDialogFragment.show(getActivity().getSupportFragmentManager(),team.getTeamIdOverwrite(), "Show Team Save Dialog");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SavedInstanceState1", "SavedInstanceState: " + outState);
        outState.putParcelableArrayList("monsters", monsters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MonsterListFragment", "On Destroy Monster 0 awakenings: " + monsters.get(0).getAwokenSkills());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Monster Log", "Database Team 0 name before is: " + Team.getTeamById(0).getTeamName() + " Overwrite id is: " + Team.getTeamById(0).getTeamIdOverwrite());
        team = Team.getTeamById(0);
        Log.d("Monster Log", "Database Team 0 name after is: " + Team.getTeamById(0).getTeamName() + " Overwrite id is: " + Team.getTeamById(0).getTeamIdOverwrite());
        Log.d("Monster Log", "Current Team 0 name is: " + team.getTeamName() + " TeamIdOverwrite is: " + team.getTeamIdOverwrite());
        for (int i = 0; i < team.getMonsters().size(); i++) {
            Log.d("Monster Log", "Monster name: " + team.getMonsters(i).getName() + " Monster id: " + team.getMonsters(i).getMonsterId());
        }
        monsters = team.getMonsters();
//        monsterListAdapter.notifyDataSetChanged();
//        monsterListAdapter.updateList(monsters);
        monsterListRecycler.updateList(monsters);
        team.update();
        team.updateLeaderSkills();
        team.setTeamStats();
        teamName.setText(team.getTeamName());
        if (team.isFavorite()) {
            favorite.setVisibility(View.VISIBLE);
        } else {
            favorite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
        }

        Log.d("SavedInstanceState2", "SavedInstanceState: " + savedInstanceState);
        if (savedInstanceState != null) {
            monsters = savedInstanceState.getParcelableArrayList("monsters");
        } else {
            monsters = team.getMonsters();
            Log.d("Monster Check stuff", "" + monsters + " what else " + team.getMonsters());
            Log.d("Team name", "Team Name is: " + team.getTeamName() + " Team id: " + team.getTeamId() + " Team overwrite id: " + team.getTeamIdOverwrite());
            if (monsters == null || monsters.size() == 0 || monsters.contains(null)) {
                monsters = new ArrayList<Monster>();
                for (int i = 0; i < 6; i++) {
                    monsters.add(monster0);
                }

                updateTeam();
            }

            team.update();
            monsters = team.getMonsters();
        }

        if (monsters != null) {
            for (int i = 0; i < monsters.size(); i++) {
                Log.d("Monster name", "" + monsters.get(i).getName());
                Log.d("Monster id", "" + monsters.get(i).getMonsterId());
            }
//            updateTeam();
        }
        Log.d("Is monsters null 2", "" + monsters);
        //monsterListAdapter = new MonsterListAdapter(getActivity(), R.layout.monster_list_row, monsters);
        //monsterListView.setAdapter(monsterListAdapter);
        //monsterListView.setOnItemClickListener(monsterListOnClickListener);
        monsterListRecycler = new MonsterListRecycler(getActivity(), monsters);
        monsterListView.setAdapter(monsterListRecycler);
        monsterListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        importButton.setOnClickListener(importButtonOnClickListener);
        orbMatchButton.setOnClickListener(orbMatchOnClickListener);
        favorite.setColorFilter(0xFFFFAADD);
        favoriteOutline.setOnClickListener(favoriteOnClickListener);
        Log.d("TeamId", "" + team.getTeamId());
    }

    private View.OnClickListener importButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            ((MainActivity) getActivity()).switchFragment(TeamListFragment.newInstance(), TeamListFragment.TAG);
//            team.updateLeaderSkills();
//            team.save();
            ((MainActivity) getActivity()).switchFragment(TeamOverviewFragment.newInstance(team), TeamOverviewFragment.TAG);
        }
    };

    private View.OnClickListener orbMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Team Health", String.valueOf(team.getTeamHealth()));
            Log.d("Team RCV", String.valueOf(team.getTeamRcv()));
            Log.d("MonsterListFragment", "Button press Monster 0 BaseMonster is: " + monsters.get(0).getBaseMonster());
            if (team.getLead().getMonsterId() == 0) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "Leader cannot be empty", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                team.updateLeaderSkills();
                team.save();
                ((MainActivity) getActivity()).switchFragment(MainFragment.newInstance(team, enemy), MainFragment.TAG);
            }
        }
    };

    private View.OnClickListener favoriteOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("MonsterListTag", "Team Id is: " + team.getTeamId() + " Team Overwrite Id is: " + team.getTeamIdOverwrite() + " Team Name is: " + team.getTeamName() + " is favorite: " + team.isFavorite());
            if(team.getTeamIdOverwrite() != 0){
                if(!team.isFavorite()){
                    team.setFavorite(true);
                    favorite.setVisibility(View.VISIBLE);
                } else {
                    team.setFavorite(false);
                    favorite.setVisibility(View.INVISIBLE);
                }
                Team overwriteTeam = new Team(team);
                overwriteTeam.setTeamId(team.getTeamIdOverwrite());
                Log.d("MonsterListTag", "Team Id is: " + team.getTeamId() + " Team Overwrite Id is: " + team.getTeamIdOverwrite() + " Team Name is: " + team.getTeamName() + " is favorite: " + team.isFavorite());
                team.save();
                overwriteTeam.save();
            }

        }
    };

    public void updateList() {
        monsterListAdapter.notifyDataSetChanged();
    }

//    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            team.setMonsterOverwrite(position);
//            team.save();
//            Log.d("Monster List Log", "Team ID is: " + team.getTeamId() + " Monster Overwrite is: " + team.getMonsterOverwrite());
//            if (monsters.get(position).getMonsterId() == 0) {
//                ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(false, 0), MonsterTabLayoutFragment.TAG);
//            }else {
//                ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
//            }
//        }
//    };

    public void updateTeam() {
        Log.d("What is monster id 0", "" + Monster.getMonsterId(monsters.get(0).getMonsterId()));
        Log.d("Monsters update team", "" + monsters);
        Log.d("Is monsters null team2", "" + team.getMonsters());
        team.setMonsters(monsters.get(0), monsters.get(1), monsters.get(2), monsters.get(3), monsters.get(4), monsters.get(5));
        for (Monster monster : team.getMonsters()) {
            monster.save();
        }
        Log.d("Is monsters null team", "" + team.getMonsters());
        Log.d("Monsters update team", "" + monsters);
        Log.d("Lead", "" + team.getLead());
        Log.d("sub1", "" + team.getSub1());
        Log.d("sub2", "" + team.getSub2());
        Log.d("sub3", "" + team.getSub3());
        Log.d("sub4", "" + team.getSub4());
        Log.d("helper", "" + team.getHelper());
        team.update();
        team.updateLeaderSkills();
        team.setTeamStats();
        team.save();
    }

    @Override
    public void sortArrayList(int sortMethod) {

    }

    @Override
    public void reverseArrayList() {
        Log.d("MonsterListTag", "FAK");
//        Log.d("MonsterListTag", "teamSaveDialogFragment is: " + teamSaveDialogFragment);
//        if (teamSaveDialogFragment == null) {
//            teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam);
//        }
//        teamSaveDialogFragment.show(getActivity().getSupportFragmentManager(), "Show Team Save Dialog");
    }

    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
        @Override
        public void overwriteTeam() {
            Log.d("MonsterListTag", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
            Team overwriteTeam = new Team(team);
            overwriteTeam.setTeamId(team.getTeamIdOverwrite());
            Log.d("MonsterListTag", "Overwrite Team name is: " + overwriteTeam.getTeamName() + " Team id: " + overwriteTeam.getTeamId() + " Team ID overwrite: " + overwriteTeam.getTeamIdOverwrite());
            overwriteTeam.save();
        }

        @Override
        public void saveNewTeam(String teamNameString) {
            long teamId;
            if (Team.getAllTeams().size() == 0) {
                teamId = 1;
            } else {
                teamId = Team.getAllTeams().get(Team.getAllTeams().size() - 1).getTeamId() + 1;
            }
            Team newTeam = new Team(team);
            newTeam.setTeamName(teamNameString);
            newTeam.setTeamId(teamId);
            newTeam.setFavorite(false);
            for (Monster monster : newTeam.getMonsters()) {
                Log.d("MonsterListTag", "MonsterPlus:" + monster.getTotalPlus());
                monster.save();
            }
            newTeam.save();
            Team teamZero = new Team(newTeam);
            teamZero.setTeamId(0);
            teamZero.setTeamIdOverwrite(teamId);
            teamZero.save();
            Log.d("MonsterListTag", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
            teamName.setText(teamNameString);
            favorite.setVisibility(View.INVISIBLE);
        }

        @Override
        public void clearTeam() {
            Log.d("MonsterListTag", "Team Monsters is: " + team.getMonsters() + " TeamIdOverwrite is: " + team.getTeamIdOverwrite() + " teamOverwrite monsters is: " + Team.getTeamById(team.getTeamIdOverwrite()).getMonsters());
            if(team.getTeamIdOverwrite() == 0){
                for(int i = 0; i < monsters.size(); i++){
                    if(!monsters.get(i).equals(monster0)){
                        if (clearTeamConfirmationDialogFragment == null) {
                            clearTeamConfirmationDialogFragment = ClearTeamConfirmationDialogFragment.newInstance(clearTeam);
                        }
                        clearTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Monster Replace All");
                        teamSaveDialogFragment.dismiss();
                    }
                }
            }else if(!team.getMonsters().equals(Team.getTeamById(team.getTeamIdOverwrite()).getMonsters()) || !team.getTeamName().equals(Team.getTeamById(team.getTeamIdOverwrite()).getTeamName())){
                Log.d("MonsterListTag", "Overwrite check");
                if (clearTeamConfirmationDialogFragment == null) {
                    clearTeamConfirmationDialogFragment = ClearTeamConfirmationDialogFragment.newInstance(clearTeam);
                }
                clearTeamConfirmationDialogFragment.show(getChildFragmentManager(), "Monster Replace All");
                teamSaveDialogFragment.dismiss();
            } else {
                Team newTeam = new Team();
                team = newTeam;
                for (int i = 0; i < 6; i++) {
                    monsters.set(i,monster0);
                }
                updateTeam();
                monsterListRecycler.updateList(team.getMonsters());
                team.save();
                teamName.setText(team.getTeamName());
                favorite.setVisibility(View.INVISIBLE);
            }
        }
    };

    private ClearTeamConfirmationDialogFragment.ResetLayout clearTeam = new ClearTeamConfirmationDialogFragment.ResetLayout() {
        @Override
        public void resetLayout() {
            Team newTeam = new Team();
            team = newTeam;
            for (int i = 0; i < 6; i++) {
                monsters.set(i,monster0);
            }
            updateTeam();
            monsterListRecycler.updateList(team.getMonsters());
            team.save();
            teamName.setText(team.getTeamName());
            favorite.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    public void searchFilter(String query) {

    }
}
