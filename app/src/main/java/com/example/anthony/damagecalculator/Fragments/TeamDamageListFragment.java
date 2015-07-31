package com.example.anthony.damagecalculator.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.anthony.damagecalculator.Adapters.MonsterDamageListAdapter;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamDamageListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamDamageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamDamageListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ListView monsterListView;
    private MonsterDamageListAdapter monsterListAdapter;
    private boolean hasEnemy;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeamDamageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeamDamageListFragment newInstance(String param1, String param2) {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public static TeamDamageListFragment newInstance(boolean hasEnemy)
    {
        TeamDamageListFragment fragment = new TeamDamageListFragment();
        Bundle args = new Bundle();
        args.putBoolean("hasEnemy", hasEnemy);
        fragment.setArguments(args);
        return fragment;
    }

    public TeamDamageListFragment() {
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
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_team_damage_list, container, false);
        monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() != null) {
            hasEnemy = getArguments().getBoolean("hasEnemy");
        }
        monsterListAdapter = new MonsterDamageListAdapter(getActivity(), R.layout.monster_damage_row, new ArrayList<Monster>());
        monsterListView.setAdapter(monsterListAdapter);
        Monster kirin = new Monster();
        kirin.setCurrentAtk(69);
        kirin.setCurrentHp(69);
        kirin.setCurrentRcv(69);
        kirin.setCurrentAwakenings(7);
        kirin.setAtkPlus(99);
        kirin.setHpPlus(99);
        kirin.setRcvPlus(99);
        monsterListAdapter.add(kirin);
        monsterListAdapter.add(kirin);
        monsterListAdapter.add(kirin);
        monsterListAdapter.add(kirin);
        monsterListAdapter.add(kirin);
        monsterListAdapter.add(kirin);
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

}
