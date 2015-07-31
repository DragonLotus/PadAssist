package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.anthony.damagecalculator.Adapters.MonsterListAdapter;
import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;
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
public class TeamListFragment extends Fragment
{
   // TODO: Rename parameter arguments, choose names that match
   // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   private static final String ARG_PARAM1 = "param1";
   private static final String ARG_PARAM2 = "param2";

   // TODO: Rename and change types of parameters
   private String mParam1;
   private String mParam2;

   private OnFragmentInteractionListener mListener;
   private ListView monsterListView;
   private MonsterListAdapter monsterListAdapter;
   private Button importButton, orbMatchButton;
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
   public static TeamListFragment newInstance(String param1, String param2)
   {
      TeamListFragment fragment = new TeamListFragment();
      Bundle args = new Bundle();
      args.putString(ARG_PARAM1, param1);
      args.putString(ARG_PARAM2, param2);
      fragment.setArguments(args);
      return fragment;
   }

   public TeamListFragment()
   {
      // Required empty public constructor
   }

   @Override
   public void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      if (getArguments() != null)
      {
         mParam1 = getArguments().getString(ARG_PARAM1);
         mParam2 = getArguments().getString(ARG_PARAM2);
      }
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState)
   {
      View rootView = inflater.inflate(R.layout.fragment_team_list, container, false);
      monsterListView = (ListView) rootView.findViewById(R.id.monsterListView);
      importButton = (Button) rootView.findViewById(R.id.importButton);
      orbMatchButton = (Button) rootView.findViewById(R.id.orbMatchButton);
      return rootView;
   }

   // TODO: Rename method, update argument and hook method into UI event
   public void onButtonPressed(Uri uri)
   {
      if (mListener != null)
      {
         mListener.onFragmentInteraction(uri);
      }
   }

   /*
   @Override
   public void onAttach(Activity activity) {
       super.onAttach(activity);
       try {
           mListener = (OnFragmentInteractionListener) activity;
       } catch (ClassCastException e) {
           throw new ClassCastException(activity.toString()
                   + " must implement OnFragmentInteractionListener");
       }
   }
*/
   @Override
   public void onDetach()
   {
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
   public interface OnFragmentInteractionListener
   {
      // TODO: Update argument type and name
      public void onFragmentInteraction(Uri uri);
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);
      monsterListAdapter = new MonsterListAdapter(getActivity(), R.layout.monster_list_row, new ArrayList<Monster>());
      monsterListView.setAdapter(monsterListAdapter);
      Monster kirin = new Monster();
      kirin.setCurrentAtk(69);
      kirin.setCurrentHp(69);
      kirin.setCurrentRcv(69);
      kirin.setCurrentAwakenings(7);
      kirin.setAtkPlus(99);
      kirin.setHpPlus(99);
      kirin.setRcvPlus(99);

      Monster bob = new Monster();
      bob.setCurrentAtk(1337);
      bob.setCurrentHp(1337);
      bob.setCurrentRcv(1337);
      bob.setCurrentAwakenings(2);
      bob.setAtkPlus(1000);
      bob.setHpPlus(300);
      bob.setRcvPlus(37);

      monsterListAdapter.add(kirin);
      monsterListAdapter.add(kirin);
      monsterListAdapter.add(kirin);
      monsterListAdapter.add(kirin);
      monsterListAdapter.add(kirin);
      monsterListAdapter.add(bob);
      importButton.setOnClickListener(buttonOnClickListener);
      orbMatchButton.setOnClickListener(buttonOnClickListener);
      monsterListView.setOnItemClickListener(monsterListOnClickListener);
   }

   private View.OnClickListener buttonOnClickListener = new View.OnClickListener(){
      @Override
      public void onClick(View v) {
         if(v.equals(importButton)){
            if (!loggedIn){
               loginDialogFragment.newInstance();
               loginDialogFragment.show(getChildFragmentManager(),"Show Login Dialog Fragment");
            }
            else {
               //Import team list here
            }
         }
         if(v.equals(orbMatchButton)){
            ( (MainActivity) getActivity()).switchFragment(MainFragment.newInstance(1));
         }
      }
   };

   private ListView.OnItemClickListener monsterListOnClickListener = new ListView.OnItemClickListener(){
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         ( (MainActivity) getActivity()).switchFragment(MonsterPageFragment.newInstance("1", "2"));
      }
   };
}
