package com.example.anthony.damagecalculator.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
 * {@link TeamListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamListFragment extends Fragment {
    public static final String TAG = TeamListFragment.class.getSimpleName();
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
    private Boolean loggedIn = false;
    private LoginDialogFragment loginDialogFragment = new LoginDialogFragment();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamListFragment newInstance(Team team, Enemy enemy) {
        TeamListFragment fragment = new TeamListFragment();
        Bundle args = new Bundle();
        args.putParcelable("team", team);
        args.putParcelable("enemy", enemy);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_list, container, false);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("SavedInstanceState1", "SavedInstanceState: " + outState);
        outState.putParcelableArrayList("monsters", monsters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        team.saveMonsters();
//        for(int i = 0; i < monsters.size(); i++) {
//            monsters.get(i).save();
//        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null){
            team = getArguments().getParcelable("team");
            enemy = getArguments().getParcelable("enemy");
        }
        Log.d("SavedInstanceState2", "SavedInstanceState: " + savedInstanceState);
        if(savedInstanceState != null){
            monsters = savedInstanceState.getParcelableArrayList("monsters");
        }else {
            monsters = (ArrayList) Monster.getAllMonsters();
            Log.d("What is monsters", "Monsters: " + monsters.size() + " " + monsters);
            if(monsters == null || monsters.size() == 0) {
                monsters = new ArrayList<Monster>();
                Monster monster1 = new Monster();
                monster1.setMonsterId(1);
                monster1.setName("Kirin of the Sacred Gleam, Sakuya #1");
                monster1.setElement2(Element.RED);
                monster1.setHpPlus(0);
                monster1.setAtkPlus(0);
                monster1.setRcvPlus(0);
                monster1.setCurrentAwakenings(1);
                Monster monster2 = new Monster();
                monster2.setMonsterId(2);
                monster2.setName("Kirin of the Sacred Gleam, Sakuya #2");
                monster2.setElement2(Element.BLUE);
                monster2.setHpPlus(22);
                monster2.setAtkPlus(22);
                monster2.setRcvPlus(22);
                monster2.setCurrentAwakenings(2);
                Monster monster3 = new Monster();
                monster3.setMonsterId(3);
                monster3.setName("Kirin of the Sacred Gleam, Sakuya #3");
                monster3.setElement2(Element.GREEN);
                monster3.setHpPlus(33);
                monster3.setAtkPlus(33);
                monster3.setRcvPlus(33);
                monster3.setCurrentAwakenings(3);
                Monster monster4 = new Monster();
                monster4.setMonsterId(4);
                monster4.setName("Kirin of the Sacred Gleam, Sakuya #4");
                monster4.setElement2(Element.DARK);
                monster4.setHpPlus(44);
                monster4.setAtkPlus(44);
                monster4.setRcvPlus(44);
                monster4.setCurrentAwakenings(4);
                Monster monster5 = new Monster();
                monster5.setMonsterId(5);
                monster5.setName("Kirin of the Sacred Gleam, Sakuya #5");
                monster5.setHpPlus(55);
                monster5.setAtkPlus(55);
                monster5.setRcvPlus(55);
                monster5.setCurrentAwakenings(5);
                Monster monster6 = new Monster();
                monster6.setMonsterId(6);
                monster6.setName("Kirin of the Sacred Gleam, Sakuya #6");
                monster6.setCurrentLevel(99);
                monster6.setCurrentAwakenings(7);

                monsters.add(monster1);
                monsters.add(monster2);
                monsters.add(monster3);
                monsters.add(monster4);
                monsters.add(monster5);
                monsters.add(monster6);
            }
            for(int i = 0; i < monsters.size(); i++){
                Log.d("Monster name", "" + monsters.get(i).getName());
                Log.d("Monster id", "" + monsters.get(i).getMonsterId());
            }
        }
        if(team.getMonsters() != null){
            monsters = team.getMonsters();
        }else{
            updateTeam();
        }
        monsterListAdapter = new MonsterListAdapter(getActivity(), R.layout.monster_list_row, monsters);
        monsterListView.setAdapter(monsterListAdapter);
        importButton.setOnClickListener(buttonOnClickListener);
        orbMatchButton.setOnClickListener(buttonOnClickListener);
        monsterListView.setOnItemClickListener(monsterListOnClickListener);

    }

    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.equals(importButton)) {
                if (!loggedIn) {
                    loginDialogFragment.newInstance();
                    loginDialogFragment.show(getChildFragmentManager(), "Show Login Dialog Fragment");
                } else {
                    //Import team list here
                }
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
            ((MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance(team.getMonsters(position)), MonsterPageFragment.TAG);
        }
    };

    public void updateTeam(){
        team.setMonsters(monsters);
        team.update();
        team.setTeamId(1);
    }
}
