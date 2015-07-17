package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Adapters.GravityButtonAdapter;
import com.example.anthony.damagecalculator.Adapters.GravityListAdapter;
import com.example.anthony.damagecalculator.Adapters.OrbMatchAdapter;
import com.example.anthony.damagecalculator.Data.Enemy;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Threads.DownloadPadApi;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnemyTargetFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnemyTargetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnemyTargetFragment extends Fragment
{
   // TODO: Rename parameter arguments, choose names that match
   // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   private static final String ARG_PARAM1 = "param1";
   private static final String ARG_PARAM2 = "param2";

   // TODO: Rename and change types of parameters
   private String mParam1;
   private String mParam2;

   private EditText targetHpValue, currentHpValue, targetDefenseValue;
   private TextView percentHpValue;
   private RadioGroup orbRadioGroup;
   private Button gravityButton1, gravityButton2, gravityButton3, gravityButton4, gravityButton5, gravityButton6, gravityButton7;
   private GravityListAdapter gravityListAdapter;
   private GravityButtonAdapter gravityButtonAdapter;
   private ListView gravityList;
   private GridView gravityButtonList;
   private Enemy enemy;
   private OnFragmentInteractionListener mListener;

   /**
    * Use this factory method to create a new instance of
    * this fragment using the provided parameters.
    *
    * @param param1 Parameter 1.
    * @param param2 Parameter 2.
    * @return A new instance of fragment EnemyTargetFragment.
    */
   // TODO: Rename and change types and number of parameters
   public static EnemyTargetFragment newInstance(String param1, String param2)
   {
      EnemyTargetFragment fragment = new EnemyTargetFragment();
      Bundle args = new Bundle();
      args.putString(ARG_PARAM1, param1);
      args.putString(ARG_PARAM2, param2);
      fragment.setArguments(args);
      return fragment;
   }

   public EnemyTargetFragment()
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
      View rootView = inflater.inflate(R.layout.fragment_enemy_target, container, false);
      targetHpValue = (EditText) rootView.findViewById(R.id.targetHPValue);
      currentHpValue = (EditText) rootView.findViewById(R.id.currentHPValue);
      targetDefenseValue = (EditText) rootView.findViewById(R.id.targetDefenseValue);
      percentHpValue = (TextView) rootView.findViewById(R.id.percentHPValue);
      orbRadioGroup = (RadioGroup) rootView.findViewById(R.id.orbRadioGroup);
//      gravityButton1 = (Button) rootView.findViewById(R.id.gravityButton1);
//      gravityButton2 = (Button) rootView.findViewById(R.id.gravityButton2);
//      gravityButton3 = (Button) rootView.findViewById(R.id.gravityButton3);
//      gravityButton4 = (Button) rootView.findViewById(R.id.gravityButton4);
//      gravityButton5 = (Button) rootView.findViewById(R.id.gravityButton5);
//      gravityButton6 = (Button) rootView.findViewById(R.id.gravityButton6);
//      gravityButton7 = (Button) rootView.findViewById(R.id.gravityButton7);
      gravityList = (ListView) rootView.findViewById(R.id.gravityList);
      gravityButtonList = (GridView) rootView.findViewById(R.id.gravityButtonGrid);
      return rootView;
   }

   public void onActivityCreated(@Nullable Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);
      enemy = new Enemy();
//      gravityButton1.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton2.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton3.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton4.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton5.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton6.setOnClickListener(gravityButtonOnClickListener);
//      gravityButton7.setOnClickListener(gravityButtonOnClickListener);
      gravityButtonAdapter = new GravityButtonAdapter(getActivity(),R.layout.gravity_button_grid, new ArrayList<String>());
      gravityButtonList.setAdapter(gravityButtonAdapter);
      gravityButtonInit();
      //gravityListAdapter = new GravityListAdapter(getActivity(), R.layout.gravity_list_row, new ArrayList<Double>());
      //gravityList.setAdapter(gravityListAdapter);

      //Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
   }

   // TODO: Rename method, update argument and hook method into UI event
   public void onButtonPressed(Uri uri)
   {
      if (mListener != null)
      {
         mListener.onFragmentInteraction(uri);
      }
   }

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

   private Button.OnClickListener gravityButtonOnClickListener = new Button.OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         if(v.equals(gravityButton1))
         {
            enemy.setGravityPercent(.10);
         }
         else if(v.equals(gravityButton2))
         {
            enemy.setGravityPercent(.15);
         }
         else if(v.equals(gravityButton3))
         {
            enemy.setGravityPercent(.20);
         }
         else if(v.equals(gravityButton4))
         {
            enemy.setGravityPercent(.25);
         }
         else if(v.equals(gravityButton5))
         {
            enemy.setGravityPercent(.30);
         }
         else if(v.equals(gravityButton6))
         {
            enemy.setGravityPercent(.35);
         }
         else if(v.equals(gravityButton7))
         {
            enemy.setGravityPercent(.45);
         }
         gravityListAdapter.add(enemy.getGravityPercent());
      }
   };

   private void gravityButtonInit()
   {
      String[] gravityStrings = {"10%","15%","20%","25%","30%","35%","45%"};
      for(int i = 0; i<gravityStrings.length; i++){
         gravityButtonAdapter.add(gravityStrings[i]);

   }
   }
}
