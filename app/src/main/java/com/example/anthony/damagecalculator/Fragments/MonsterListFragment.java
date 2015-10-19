package com.example.anthony.damagecalculator.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anthony.damagecalculator.Adapters.MonsterListAdapter;
import com.example.anthony.damagecalculator.Data.Element;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.Monster;
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
    private ListView monsterListView;
    private ArrayList<Monster> monsters;
    private MonsterListAdapter monsterListAdapter;
    private Button importButton, orbMatchButton;
    private Team team;
    private Enemy enemy;
    private Toast toast;
    private TeamSaveDialogFragment teamSaveDialogFragment;

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
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        importButton = (Button) rootView.findViewById(R.id.importButton);
        orbMatchButton = (Button) rootView.findViewById(R.id.orbMatchButton);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SavedInstanceState1", "SavedInstanceState: " + outState);
        outState.putParcelableArrayList("monsters", monsters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
        monsterListAdapter.updateList(monsters);
        team.update();
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
                Log.d("What is monsters", "Monsters: " + monsters.size() + " " + monsters);
                Monster monster1 = new Monster(1218);
                monster1.setMonsterId(1);
                monster1.setHpPlus(99);
                monster1.setAtkPlus(99);
                monster1.setRcvPlus(99);
                monster1.setCurrentLevel(99);
                monster1.setCurrentAwakenings(7);
                Monster monster2 = new Monster(1099);
                monster2.setMonsterId(2);
                monster2.setHpPlus(99);
                monster2.setAtkPlus(17);
                monster2.setRcvPlus(99);
                monster2.setCurrentLevel(99);
                monster2.setCurrentAwakenings(3);
                Monster monster3 = new Monster(1727);
                monster3.setMonsterId(3);
                monster3.setHpPlus(0);
                monster3.setAtkPlus(99);
                monster3.setRcvPlus(0);
                monster3.setCurrentLevel(99);
                monster3.setCurrentAwakenings(5);
                Monster monster4 = new Monster(1217);
                monster4.setMonsterId(4);
                monster4.setHpPlus(99);
                monster4.setAtkPlus(99);
                monster4.setRcvPlus(99);
                monster4.setCurrentLevel(99);
                monster4.setCurrentAwakenings(6);
                Monster monster5 = new Monster(1298);
                monster5.setMonsterId(5);
                monster5.setHpPlus(99);
                monster5.setAtkPlus(99);
                monster5.setRcvPlus(99);
                monster5.setCurrentLevel(99);
                monster5.setCurrentAwakenings(7);
                Monster monster6 = new Monster(2077);
                monster6.setMonsterId(6);
                monster6.setHpPlus(99);
                monster6.setAtkPlus(99);
                monster6.setRcvPlus(99);
                monster6.setCurrentLevel(99);
                monster6.setCurrentAwakenings(9);

                monsters.add(monster1);
                monsters.add(monster2);
                monsters.add(monster3);
                monsters.add(monster4);
                monsters.add(monster5);
                monsters.add(monster6);

                updateTeam();
                //team.setMonsters(monsters.get(0), monsters.get(1), monsters.get(2), monsters.get(3), monsters.get(4), monsters.get(5));

//                for (int i = 0; i < monsters.size(); i++) {
//                    monsters.get(i).save();
//                    Log.d("Awakening List3", "" + monsters.get(i).getAwokenSkills());
//                }
            }

//            Log.d("Monster Check Preemptive", "" + team.getMonsters());
//            for (int i = 0; i < team.getMonsters().size(); i++) {
//                Log.d("Monster Check name", "" + team.getMonsters().get(i).getName());
//                Log.d("Monster Check id", "" + team.getMonsters().get(i).getMonsterId());
//                team.getMonsters().get(i).save();
//            }
//            team.setTeamId(5);
//            team.save();
//
//            Team test = Team.getTeamById(5);
//            Log.d("Monster Check2 Preemptive", "" + test.getMonsters());
//            for (int i = 0; i < team.getMonsters().size(); i++) {
//                Log.d("Monster Check2 name", "" + test.getMonsters().get(i).getName());
//                Log.d("Monster Check2 id", "" + test.getMonsters().get(i).getMonsterId());
//            }

            team.update();
            monsters = team.getMonsters();
            Log.d("Is monsters null 1", "" + monsters);
        }

        Log.d("Is monsters null 4", "" + monsters);
        Log.d("monster attack", "" + monsters.get(0).getTotalAtk());
        Log.d("Awakening List2", "" + monsters.get(0).getAwokenSkills());
        if (monsters != null) {
            for (int i = 0; i < monsters.size(); i++) {
                Log.d("Monster name", "" + monsters.get(i).getName());
                Log.d("Monster id", "" + monsters.get(i).getMonsterId());
            }
//            updateTeam();
        }
        Log.d("Is monsters null 2", "" + monsters);
        monsterListAdapter = new MonsterListAdapter(getActivity(), R.layout.monster_list_row, monsters);
        monsterListView.setAdapter(monsterListAdapter);
        importButton.setOnClickListener(importButtonOnClickListener);
        orbMatchButton.setOnClickListener(orbMatchOnClickListener);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);
        Log.d("TeamId", "" + team.getTeamId());
    }

    private View.OnClickListener importButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            ((MainActivity) getActivity()).switchFragment(TeamListFragment.newInstance(), TeamListFragment.TAG);
            team.updateLeaderSkills();
            team.save();
            ((MainActivity) getActivity()).switchFragment(TeamOverviewFragment.newInstance(team), TeamOverviewFragment.TAG);
        }
    };

    private View.OnClickListener orbMatchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("Team Health", String.valueOf(team.getTeamHealth()));
            Log.d("Team RCV", String.valueOf(team.getTeamRcv()));
            if (team.getLead().getMonsterId() == 0){
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

    public void updateList() {
        monsterListAdapter.notifyDataSetChanged();
    }

    private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            team.setMonsterOverwrite(position);
            team.save();
            Log.d("Monster List Log", "Team ID is: " + team.getTeamId() + " Monster Overwrite is: " + team.getMonsterOverwrite());
            if (monsters.get(position).getMonsterId() == 0) {
                ((MainActivity) getActivity()).switchFragment(MonsterTabLayoutFragment.newInstance(false, 0), MonsterTabLayoutFragment.TAG);
            }else {
                ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(), MonsterPageFragment.TAG);
            }
        }
    };

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
        team.save();
    }

    @Override
    public void sortArrayList(int sortMethod) {

    }

    @Override
    public void reverseArrayList() {
//        if (teamSaveDialogFragment == null) {
//            teamSaveDialogFragment = teamSaveDialogFragment.newInstance(saveTeam);
//        }
//        teamSaveDialogFragment.show(getActivity().getSupportFragmentManager(), "Show Team Save Dialog");
    }

//    private TeamSaveDialogFragment.SaveTeam saveTeam = new TeamSaveDialogFragment.SaveTeam() {
//        @Override
//        public void overwriteTeam() {
//            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
//            Team overwriteTeam = new Team(Team.getTeamById(0));
//            overwriteTeam.setTeamName(Team.getTeamById(overwriteTeam.getTeamIdOverwrite()).getTeamName());
//            overwriteTeam.setTeamId(Team.getTeamById(0).getTeamIdOverwrite());
//            Log.d("Main Activity Log", "Overwrite Team name is: " + overwriteTeam.getTeamName() + " Team id: " + overwriteTeam.getTeamId() + " Team ID overwrite: " + overwriteTeam.getTeamIdOverwrite());
//            overwriteTeam.save();
//
//        }
//
//        @Override
//        public void saveNewTeam(String teamName) {
//            long teamId;
//            if (Team.getAllTeams().size() == 0) {
//                teamId = 1;
//            } else {
//                teamId = Team.getAllTeams().get(Team.getAllTeams().size() - 1).getTeamId() + 1;
//            }
//            Team newTeam = new Team(Team.getTeamById(0));
//            newTeam.setTeamName(teamName);
//            newTeam.setTeamId(teamId);
//            for (Monster monster : newTeam.getMonsters()) {
//                Log.d("Monster", "MonsterPlus:" + monster.getTotalPlus());
//                monster.save();
//            }
//            newTeam.save();
//            Team teamZero = new Team(newTeam);
//            teamZero.setTeamId(0);
//            teamZero.setTeamIdOverwrite(newTeam.getTeamId());
//            teamZero.save();
//            Log.d("Main Activity Log", "Team name is: " + Team.getTeamById(0).getTeamName() + " Team id: " + Team.getTeamById(0).getTeamId() + " Team ID overwrite: " + Team.getTeamById(0).getTeamIdOverwrite());
//
//        }
//    };

    @Override
    public void searchFilter(String query) {

    }
}
