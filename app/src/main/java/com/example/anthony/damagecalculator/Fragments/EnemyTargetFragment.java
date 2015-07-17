package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Threads.DownloadPadApi;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;


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
   private TextView percentHpValue, totalGravityValue;
   private RadioGroup orbRadioGroup;
   private Button gravityButton1, gravityButton2, gravityButton3, gravityButton4, gravityButton5, gravityButton6, gravityButton7;
   private GravityListAdapter gravityListAdapter;
   private GravityButtonAdapter gravityButtonAdapter;
   private ListView gravityList;
   private GridView gravityButtonList;
   private Enemy enemy;
   private DecimalFormat df;
   private OnFragmentInteractionListener mListener;
   private GravityListAdapter.UpdateGravityPercent updateGravityPercent = new GravityListAdapter.UpdateGravityPercent()
   {
      @Override
      public void updatePercent()
      {
         double gravity = 1.0;
         for (int i = 0; i < gravityListAdapter.getCount(); i++)
         {
            gravity *= 1 - gravityListAdapter.getItem(i) / 100.0;
         }

         totalGravityValue.setText(Math.round((1 - gravity) * 100) + "%");
         enemy.setGravityPercent(gravity);
      }
   };
   private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats()
   {
      @Override
      public void changeMonsterAttribute(int statToChange, int statValue)
      {
         if(statToChange == MyTextWatcher.TARGET_HP)
         {

            enemy.setTargetHp(statValue);
            enemy.setCurrentHp(statValue);
            currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));

         }
         if(statToChange == MyTextWatcher.CURRENT_HP)
         {
            enemy.setCurrentHp(statValue);

         }
         if(statToChange == MyTextWatcher.TARGET_DEFENSE)
         {
            enemy.setTargetDef(statValue);
         }
         Log.d("HI THOMAS", String.valueOf(enemy.getPercentHp()));
         df = new DecimalFormat("#.##");
         percentHpValue.setText(df.format(enemy.getPercentHp()));
      }
   };

   private MyTextWatcher targetHPWatcher = new MyTextWatcher(MyTextWatcher.TARGET_HP,changeStats);
   private MyTextWatcher currentHPWatcher = new MyTextWatcher(MyTextWatcher.CURRENT_HP,changeStats);
   private MyTextWatcher targetDefenseWatcher = new MyTextWatcher(MyTextWatcher.TARGET_DEFENSE,changeStats);

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
      gravityList = (ListView) rootView.findViewById(R.id.gravityList);
      totalGravityValue = (TextView) rootView.findViewById(R.id.totalGravityValue);
      gravityButtonList = (GridView) rootView.findViewById(R.id.gravityButtonGrid);
      return rootView;
   }

   public void onActivityCreated(@Nullable Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);
      enemy = new Enemy();

      targetHpValue.setText(String.valueOf(enemy.getTargetHp()));
      currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));
      targetDefenseValue.setText(String.valueOf(enemy.getTargetDef()));

      gravityListAdapter = new GravityListAdapter(getActivity(), R.layout.gravity_list_row, new ArrayList<Integer>(), updateGravityPercent);
      gravityList.setAdapter(gravityListAdapter);
      gravityButtonAdapter = new GravityButtonAdapter(getActivity(), R.layout.gravity_button_grid, new ArrayList<Integer>());
      gravityButtonList.setAdapter(gravityButtonAdapter);
      gravityButtonList.setOnItemClickListener(gravityButtonOnClickListener);
      gravityButtonInit();
      targetHpValue.addTextChangedListener(targetHPWatcher);
      currentHpValue.addTextChangedListener(currentHPWatcher);
      targetDefenseValue.addTextChangedListener(targetDefenseWatcher);
      targetHpValue.setOnFocusChangeListener(editTextOnFocusChange);
      currentHpValue.setOnFocusChangeListener(editTextOnFocusChange);
      targetDefenseValue.setOnFocusChangeListener(editTextOnFocusChange);

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


   private GridView.OnItemClickListener gravityButtonOnClickListener = new GridView.OnItemClickListener()
   {
      @Override
      public void onItemClick(AdapterView<?> parent, View v, int position, long id)
      {
         gravityListAdapter.add(gravityButtonAdapter.getItem(position));
         gravityListAdapter.notifyDataSetChanged();
      }
   };


   private void gravityButtonInit()
   {
      int[] gravityInts = {10, 15, 20, 25, 30, 35, 45};
      for (int i = 0; i < gravityInts.length; i++)
      {
         gravityButtonAdapter.add(gravityInts[i]);

      }
   }


   private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener()
   {
      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
         if (!hasFocus)
         {
            hideKeyboard(v);
            if (targetHpValue.getText().toString().equals(""))
            {
               targetHpValue.setText("0");
               enemy.setTargetHp(0);
               enemy.setCurrentHp(0);
               currentHpValue.setText(String.valueOf(enemy.getCurrentHp()));
               percentHpValue.setText(String.valueOf(enemy.getPercentHp()));
            }
            else if (currentHpValue.getText().toString().equals(""))
            {
               currentHpValue.setText("0");
               enemy.setCurrentHp(0);
               percentHpValue.setText(String.valueOf(enemy.getPercentHp()));
            }
            else if (targetDefenseValue.getText().toString().equals(""))
            {
               targetDefenseValue.setText("0");
               enemy.setTargetDef(0);
            }
         }
         ;
      }
   };


   public void hideKeyboard(View view)
   {
      InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
      inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
   }
}
