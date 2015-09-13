package com.example.anthony.damagecalculator.Fragments;

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
    private ListView monsterListView;
    private ArrayList<Monster> monsters;
    private MonsterListAdapter monsterListAdapter;
    private Button importButton, orbMatchButton;
    private Team team;
    private Enemy enemy;

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
            Log.d("What is monsters", "Monsters: " + monsters.size() + " " + monsters + " " + team.getLead());
            if (monsters == null || monsters.size() == 0 || monsters.contains(null)) {
                monsters = new ArrayList<Monster>();
                Log.d("What is monsters", "Monsters: " + monsters.size() + " " + monsters);
                Monster monster1 = new Monster();
                monster1.setMonsterId(1);
                monster1.setMonsterPicture(R.drawable.monster_1218);
                monster1.setName("Kirin of the Sacred Gleam, Sakuya #1");
                monster1.setMaxLevel(99);
                monster1.setAtkMax(1370);
                monster1.setAtkMin(913);
                monster1.setHpMax(3528);
                monster1.setHpMin(1271);
                monster1.setRcvMax(384);
                monster1.setRcvMin(256);
                monster1.setAtkScale(1);
                monster1.setHpScale(1);
                monster1.setRcvScale(1);
                monster1.setMaxAwakenings(7);
                monster1.setElement1(Element.LIGHT);
                monster1.setElement2(Element.RED);
                monster1.addAwokenSkills(17);
                monster1.addAwokenSkills(12);
                monster1.addAwokenSkills(11);
                monster1.addAwokenSkills(28);
                monster1.addAwokenSkills(17);
                monster1.addAwokenSkills(21);
                monster1.addAwokenSkills(19);
                monster1.setHpPlus(11);
                monster1.setAtkPlus(11);
                monster1.setRcvPlus(11);
                monster1.setCurrentLevel(1);
                monster1.setCurrentAwakenings(1);
                Monster monster2 = new Monster();
                monster2.setMonsterId(2);
                monster2.setMonsterPicture(R.drawable.monster_1218);
                monster2.setName("Kirin of the Sacred Gleam, Sakuya #2");
                monster2.setMaxLevel(99);
                monster2.setAtkMax(1370);
                monster2.setAtkMin(913);
                monster2.setHpMax(3528);
                monster2.setHpMin(1271);
                monster2.setRcvMax(384);
                monster2.setRcvMin(256);
                monster2.setAtkScale(1);
                monster2.setHpScale(1);
                monster2.setRcvScale(1);
                monster2.setMaxAwakenings(7);
                monster2.setElement1(Element.LIGHT);
                monster2.setElement2(Element.BLUE);
                monster2.addAwokenSkills(17);
                monster2.addAwokenSkills(12);
                monster2.addAwokenSkills(11);
                monster2.addAwokenSkills(28);
                monster2.addAwokenSkills(17);
                monster2.addAwokenSkills(21);
                monster2.addAwokenSkills(19);
                monster2.setHpPlus(22);
                monster2.setAtkPlus(22);
                monster2.setRcvPlus(22);
                monster2.setCurrentLevel(22);
                monster2.setCurrentAwakenings(2);
                Monster monster3 = new Monster();
                monster3.setMonsterId(3);
                monster3.setMonsterPicture(R.drawable.monster_1218);
                monster3.setName("Kirin of the Sacred Gleam, Sakuya #3");
                monster3.setMaxLevel(99);
                monster3.setAtkMax(1370);
                monster3.setAtkMin(913);
                monster3.setHpMax(3528);
                monster3.setHpMin(1271);
                monster3.setRcvMax(384);
                monster3.setRcvMin(256);
                monster3.setAtkScale(1);
                monster3.setHpScale(1);
                monster3.setRcvScale(1);
                monster3.setMaxAwakenings(7);
                monster3.setElement1(Element.LIGHT);
                monster3.setElement2(Element.GREEN);
                monster3.addAwokenSkills(17);
                monster3.addAwokenSkills(12);
                monster3.addAwokenSkills(11);
                monster3.addAwokenSkills(28);
                monster3.addAwokenSkills(17);
                monster3.addAwokenSkills(21);
                monster3.addAwokenSkills(19);
                monster3.setHpPlus(33);
                monster3.setAtkPlus(33);
                monster3.setRcvPlus(33);
                monster3.setCurrentLevel(33);
                monster3.setCurrentAwakenings(3);
                Monster monster4 = new Monster();
                monster4.setMonsterId(4);
                monster4.setMonsterPicture(R.drawable.monster_1218);
                monster4.setName("Kirin of the Sacred Gleam, Sakuya #4");
                monster4.setMaxLevel(99);
                monster4.setAtkMax(1370);
                monster4.setAtkMin(913);
                monster4.setHpMax(3528);
                monster4.setHpMin(1271);
                monster4.setRcvMax(384);
                monster4.setRcvMin(256);
                monster4.setAtkScale(1);
                monster4.setHpScale(1);
                monster4.setRcvScale(1);
                monster4.setMaxAwakenings(7);
                monster4.setElement1(Element.LIGHT);
                monster4.setElement2(Element.DARK);
                monster4.addAwokenSkills(17);
                monster4.addAwokenSkills(12);
                monster4.addAwokenSkills(11);
                monster4.addAwokenSkills(28);
                monster4.addAwokenSkills(17);
                monster4.addAwokenSkills(21);
                monster4.addAwokenSkills(19);
                monster4.setHpPlus(44);
                monster4.setAtkPlus(44);
                monster4.setRcvPlus(44);
                monster4.setCurrentLevel(44);
                monster4.setCurrentAwakenings(4);
                Monster monster5 = new Monster();
                monster5.setMonsterId(5);
                monster5.setMonsterPicture(R.drawable.monster_1218);
                monster5.setName("Kirin of the Sacred Gleam, Sakuya #5");
                monster5.setMaxLevel(99);
                monster5.setAtkMax(1370);
                monster5.setAtkMin(913);
                monster5.setHpMax(3528);
                monster5.setHpMin(1271);
                monster5.setRcvMax(384);
                monster5.setRcvMin(256);
                monster5.setAtkScale(1);
                monster5.setHpScale(1);
                monster5.setRcvScale(1);
                monster5.setMaxAwakenings(7);
                monster5.setElement1(Element.LIGHT);
                monster5.setElement2(Element.DARK);
                monster5.addAwokenSkills(17);
                monster5.addAwokenSkills(12);
                monster5.addAwokenSkills(11);
                monster5.addAwokenSkills(28);
                monster5.addAwokenSkills(17);
                monster5.addAwokenSkills(21);
                monster5.addAwokenSkills(19);
                monster5.setHpPlus(55);
                monster5.setAtkPlus(55);
                monster5.setRcvPlus(55);
                monster5.setCurrentLevel(55);
                monster5.setCurrentAwakenings(5);
                Monster monster6 = new Monster();
                monster6.setMonsterId(6);
                monster6.setMonsterPicture(R.drawable.monster_1218);
                monster6.setName("Kirin of the Sacred Gleam, Sakuya #6");
                monster6.setMaxLevel(99);
                monster6.setAtkMax(1370);
                monster6.setAtkMin(913);
                monster6.setHpMax(3528);
                monster6.setHpMin(1271);
                monster6.setRcvMax(384);
                monster6.setRcvMin(256);
                monster6.setAtkScale(1);
                monster6.setHpScale(1);
                monster6.setRcvScale(1);
                monster6.setMaxAwakenings(7);
                monster6.setElement1(Element.LIGHT);
                monster6.setElement2(Element.DARK);
                monster6.addAwokenSkills(17);
                monster6.addAwokenSkills(12);
                monster6.addAwokenSkills(11);
                monster6.addAwokenSkills(28);
                monster6.addAwokenSkills(17);
                monster6.addAwokenSkills(21);
                monster6.addAwokenSkills(19);
                monster6.setHpPlus(99);
                monster6.setAtkPlus(99);
                monster6.setRcvPlus(99);
                monster6.setCurrentLevel(99);
                monster6.setCurrentAwakenings(7);

                monsters.add(monster1);
                monsters.add(monster2);
                monsters.add(monster3);
                monsters.add(monster4);
                monsters.add(monster5);
                monsters.add(monster6);

                //team.setMonsters(monsters.get(0), monsters.get(1), monsters.get(2), monsters.get(3), monsters.get(4), monsters.get(5));

                for (int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).save();
                    Log.d("Awakening List3", "" + monsters.get(i).getAwokenSkills());
                }
                Log.d("Is monsters null 3", "" + monsters);
            }
            for (int i = 0; i < monsters.size(); i++) {
                Log.d("Monster name", "" + monsters.get(i).getName());
                Log.d("Monster id", "" + monsters.get(i).getMonsterId());
            }
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
            updateTeam();
        }
        Log.d("Is monsters null 2", "" + monsters);
        monsterListAdapter = new MonsterListAdapter(getActivity(), R.layout.monster_list_row, monsters);
        monsterListView.setAdapter(monsterListAdapter);
        importButton.setOnClickListener(buttonOnClickListener);
        orbMatchButton.setOnClickListener(buttonOnClickListener);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);
        Log.d("TeamId", "" + team.getTeamId());
    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(importButton)) {
                ((MainActivity) getActivity()).switchFragment(TeamListFragment.newInstance(team, enemy), TeamListFragment.TAG);
            }
            if (v.equals(orbMatchButton)) {
                Log.d("Team Health", String.valueOf(team.getTeamHealth()));
                Log.d("Team RCV", String.valueOf(team.getTeamRcv()));
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
            ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(monsters.get(position)), MonsterPageFragment.TAG);
        }
    };

    public void updateTeam() {
        Log.d("What is monster id 0", "" + Monster.getMonsterId(monsters.get(0).getMonsterId()));
        Log.d("Monsters update team", "" + monsters);
        Log.d("Is monsters null team2", "" + team.getMonsters());
        team.setMonsters(monsters.get(0), monsters.get(1), monsters.get(2), monsters.get(3), monsters.get(4), monsters.get(5));
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
}
