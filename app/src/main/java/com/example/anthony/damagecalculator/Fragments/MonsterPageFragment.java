package com.example.anthony.damagecalculator.Fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.TextWatcher.MyTextWatcher;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MonsterPageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MonsterPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterPageFragment extends Fragment
{
   // TODO: Rename parameter arguments, choose names that match
   // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   private static final String ARG_PARAM1 = "param1";
   private static final String ARG_PARAM2 = "param2";

   // TODO: Rename and change types of parameters
   private String mParam1;
   private String mParam2;

   private OnFragmentInteractionListener mListener;
   private TextView monsterName, monsterStatsHPBase, monsterStatsHPTotal, monsterStatsATKBase, monsterStatsATKTotal, monsterStatsRCVBase, monsterStatsRCVTotal, monsterStatsWeightedValue, monsterStatsTotalWeightedValue;
   private EditText monsterLevelValue, monsterStatsHPPlus, monsterStatsATKPlus, monsterStatsRCVPlus, monsterAwakeningsValue;
   private Button monsterLevelMax, monsterStatsMax, monsterStatsHPMax, monsterStatsATKMax, monsterStatsRCVMax, monsterAwakeningsMax, monsterChoose, monsterStatsMaxAll, awakeningPlus, awakeningMinus;
   private ImageView monsterPicture, monsterAwakening1, monsterAwakening2, monsterAwakening3, monsterAwakening4, monsterAwakening5, monsterAwakening6, monsterAwakening7, monsterAwakening8, monsterAwakening9;
   private LinearLayout awakeningHolder;
   private Monster monster;
   private MyTextWatcher.ChangeStats changeStats = new MyTextWatcher.ChangeStats()
   {
      @Override
      public void changeMonsterAttribute(int statToChange, int statValue)
      {
         if (statToChange == MyTextWatcher.CURRENT_LEVEL)
         {
            if (statValue == 0)
            {
               statValue = 1;
            }
            monster.setCurrentLevel(statValue);
            monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
            monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
            monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
            monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
            monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
            monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
         } else if (statToChange == MyTextWatcher.ATK_STAT)
         {
            monster.setAtkPlus(statValue);
            monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
            monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
         } else if (statToChange == MyTextWatcher.RCV_STAT)
         {
            monster.setRcvPlus(statValue);
            monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
            monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
         } else if (statToChange == MyTextWatcher.HP_STAT)
         {
            monster.setHpPlus(statValue);
            monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
            monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
         } else if (statToChange == MyTextWatcher.AWAKENINGS)
         {
            monster.setCurrentAwakenings(statValue);
            if(monster.getCurrentAwakenings() > monster.getMaxAwakenings()){
               monster.setCurrentAwakenings(monster.getMaxAwakenings());
               monsterAwakeningsValue.setText(Integer.toString(monster.getCurrentAwakenings()));
            }
            Log.d("hello", Integer.toString(monster.getCurrentAwakenings()));
            grayAwakenings();
         }
      }
   };

   private MyTextWatcher currentLevelWatcher = new MyTextWatcher(MyTextWatcher.CURRENT_LEVEL, changeStats);
   private MyTextWatcher hpPlusWatcher = new MyTextWatcher(MyTextWatcher.HP_STAT, changeStats);
   private MyTextWatcher atkPlusWatcher = new MyTextWatcher(MyTextWatcher.ATK_STAT, changeStats);
   private MyTextWatcher rcvPlusWatcher = new MyTextWatcher(MyTextWatcher.RCV_STAT, changeStats);
   private MyTextWatcher awakeningsWatcher = new MyTextWatcher(MyTextWatcher.AWAKENINGS, changeStats);

   private View.OnFocusChangeListener editTextOnFocusChange = new View.OnFocusChangeListener()
   {
      @Override
      public void onFocusChange(View v, boolean hasFocus)
      {
         if (!hasFocus)
         {
            if (monsterAwakeningsValue.getText().toString().equals(""))
            {
               monsterAwakeningsValue.setText("0");
               monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
               grayAwakenings();
            }
            else if (monsterLevelValue.getText().toString().equals(""))
            {
               monsterLevelValue.setText("1");
            }
            else if (monsterStatsHPPlus.getText().toString().equals(""))
            {
               monsterStatsHPPlus.setText("0");
            }
            else if (monsterStatsATKPlus.getText().toString().equals(""))
            {
               monsterStatsATKPlus.setText("0");
            }
            else if (monsterStatsRCVPlus.getText().toString().equals(""))
            {
               monsterStatsRCVPlus.setText("0");
            }
            monsterStats();
         }
      }
   };

   /**
    * Use this factory method to create a new instance of
    * this fragment using the provided parameters.
    *
    * @param param1 Parameter 1.
    * @param param2 Parameter 2.
    * @return A new instance of fragment MonsterPageFragment.
    */
   // TODO: Rename and change types and number of parameters
   public static MonsterPageFragment newInstance(String param1, String param2)
   {
      MonsterPageFragment fragment = new MonsterPageFragment();
      Bundle args = new Bundle();
      args.putString(ARG_PARAM1, param1);
      args.putString(ARG_PARAM2, param2);
      fragment.setArguments(args);
      return fragment;
   }

   public MonsterPageFragment()
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
      View rootView = inflater.inflate(R.layout.fragment_monster_page, container, false);
      initTextView(rootView);
      initImageView(rootView);
      monsterLevelMax = (Button) rootView.findViewById(R.id.monsterLevelMax);
      monsterStatsMax = (Button) rootView.findViewById(R.id.monsterStatsMax);
      monsterStatsHPMax = (Button) rootView.findViewById(R.id.monsterStatsHPMax);
      monsterStatsATKMax = (Button) rootView.findViewById(R.id.monsterStatsATKMax);
      monsterStatsRCVMax = (Button) rootView.findViewById(R.id.monsterStatsRCVMax);
      monsterAwakeningsMax = (Button) rootView.findViewById(R.id.monsterAwakeningsMax);
      monsterStatsMaxAll = (Button) rootView.findViewById(R.id.monsterStatsMaxAll);
      awakeningMinus = (Button) rootView.findViewById(R.id.awakeningMinus);
      awakeningPlus = (Button) rootView.findViewById(R.id.awakeningPlus);
      monsterChoose = (Button) rootView.findViewById(R.id.monsterChoose);
      monsterLevelValue = (EditText) rootView.findViewById(R.id.monsterLevelValue);
      monsterStatsHPPlus = (EditText) rootView.findViewById(R.id.monsterStatsHPPlus);
      monsterStatsATKPlus = (EditText) rootView.findViewById(R.id.monsterStatsATKPlus);
      monsterStatsRCVPlus = (EditText) rootView.findViewById(R.id.monsterStatsRCVPlus);
      monsterAwakeningsValue = (EditText) rootView.findViewById(R.id.monsterAwakeningsValue);
      awakeningHolder = (LinearLayout) rootView.findViewById(R.id.awakeningHolder);


      return rootView;
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);
      monster = new Monster();

//      monster.setCurrentLevel(monster.getCurrentLevel());
//      monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
//      monster.setCurrentAtk(monster.getAtkMax());
//      monsterStatsATKBase.setText(Integer.toString(monster.getAtkMax()));
//      monster.setCurrentHp(monster.getHpMax());
//      monsterStatsHPBase.setText(Integer.toString(monster.getHpMax()));
//      monster.setCurrentRcv(monster.getRcvMax());
//      monster.setHpPlus(Integer.valueOf(monsterStatsHPPlus.getText().toString()));
//      monster.setAtkPlus(Integer.valueOf(monsterStatsATKPlus.getText().toString()));
//      monster.setRcvPlus(Integer.valueOf(monsterStatsRCVPlus.getText().toString()));
//      monsterStatsRCVBase.setText(Integer.toString(monster.getRcvMax()));
//      monsterStatsATKTotal.setText(Integer.toString(monster.getTotalAtk()));
//      monsterStatsHPTotal.setText(Integer.toString(monster.getTotalHp()));
//      monsterStatsRCVTotal.setText(Integer.toString(monster.getTotalRcv()));
//
//
      monsterLevelValue.addTextChangedListener(currentLevelWatcher);
      monsterStatsHPPlus.addTextChangedListener(hpPlusWatcher);
      monsterStatsATKPlus.addTextChangedListener(atkPlusWatcher);
      monsterStatsRCVPlus.addTextChangedListener(rcvPlusWatcher);
      monsterAwakeningsValue.addTextChangedListener(awakeningsWatcher);

      monsterLevelValue.setOnFocusChangeListener(editTextOnFocusChange);
      monsterStatsHPPlus.setOnFocusChangeListener(editTextOnFocusChange);
      monsterStatsATKPlus.setOnFocusChangeListener(editTextOnFocusChange);
      monsterStatsRCVPlus.setOnFocusChangeListener(editTextOnFocusChange);
      monsterAwakeningsValue.setOnFocusChangeListener(editTextOnFocusChange);

      monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
      showAwakenings();
      grayAwakenings();
      monsterStats();
      awakeningMinus.setOnClickListener(awakeningButtons);
      awakeningPlus.setOnClickListener(awakeningButtons);

      monsterLevelMax.setOnClickListener(maxButtons);
      monsterStatsMax.setOnClickListener(maxButtons);
      monsterStatsHPMax.setOnClickListener(maxButtons);
      monsterStatsATKMax.setOnClickListener(maxButtons);
      monsterStatsRCVMax.setOnClickListener(maxButtons);
      monsterAwakeningsMax.setOnClickListener(maxButtons);
      monsterStatsMaxAll.setOnClickListener(maxButtons);
      //Temporary update stats button
      monsterChoose.setOnClickListener(maxButtons);
//      monsterLevelValue.addTextChangedListener(monsterTextWatcher);
//      monsterStatsHPPlus.addTextChangedListener(monsterTextWatcher);
//      monsterStatsATKPlus.addTextChangedListener(monsterTextWatcher);
//      monsterStatsRCVPlus.addTextChangedListener(monsterTextWatcher);
//      monsterAwakeningsValue.addTextChangedListener(monsterTextWatcher);

      monsterLevelValue.setOnKeyListener(editTextOnKeyListener);
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


   public interface OnFragmentInteractionListener
   {
      // TODO: Update argument type and name
      public void onFragmentInteraction(Uri uri);
   }

   private void initTextView(View rootView)
   {
      monsterName = (TextView) rootView.findViewById(R.id.monsterName);
      monsterStatsHPBase = (TextView) rootView.findViewById(R.id.monsterStatsHPBase);
      monsterStatsHPTotal = (TextView) rootView.findViewById(R.id.monsterStatsHPTotal);
      monsterStatsATKBase = (TextView) rootView.findViewById(R.id.monsterStatsATKBase);
      monsterStatsATKTotal = (TextView) rootView.findViewById(R.id.monsterStatsATKTotal);
      monsterStatsRCVBase = (TextView) rootView.findViewById(R.id.monsterStatsRCVBase);
      monsterStatsRCVTotal = (TextView) rootView.findViewById(R.id.monsterStatsRCVTotal);
      monsterStatsWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsWeightedValue);
      monsterStatsTotalWeightedValue = (TextView) rootView.findViewById(R.id.monsterStatsTotalWeightedValue);

   }

   private void initImageView(View rootView)
   {
      monsterPicture = (ImageView) rootView.findViewById(R.id.monsterPicture);
      monsterAwakening1 = (ImageView) rootView.findViewById(R.id.monsterAwakening1);
      monsterAwakening2 = (ImageView) rootView.findViewById(R.id.monsterAwakening2);
      monsterAwakening3 = (ImageView) rootView.findViewById(R.id.monsterAwakening3);
      monsterAwakening4 = (ImageView) rootView.findViewById(R.id.monsterAwakening4);
      monsterAwakening5 = (ImageView) rootView.findViewById(R.id.monsterAwakening5);
      monsterAwakening6 = (ImageView) rootView.findViewById(R.id.monsterAwakening6);
      monsterAwakening7 = (ImageView) rootView.findViewById(R.id.monsterAwakening7);
      monsterAwakening8 = (ImageView) rootView.findViewById(R.id.monsterAwakening8);
      monsterAwakening9 = (ImageView) rootView.findViewById(R.id.monsterAwakening9);

   }


   private View.OnClickListener awakeningButtons = new View.OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         monsterAwakeningsValue.clearFocus();
         if (v.equals(awakeningMinus))
         {
            if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) > 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) <= monster.getMaxAwakenings())
            {
               monsterAwakeningsValue.setText("" + (Integer.parseInt(monsterAwakeningsValue.getText().toString()) - 1));
               monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
            }
         } else if (v.equals(awakeningPlus))
         {
            if (Integer.parseInt(monsterAwakeningsValue.getText().toString()) >= 0 && Integer.parseInt(monsterAwakeningsValue.getText().toString()) < monster.getMaxAwakenings())
            {
               monsterAwakeningsValue.setText("" + (Integer.parseInt(monsterAwakeningsValue.getText().toString()) + 1));
               monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
            }
         }
         grayAwakenings();

      }
   };

   private View.OnClickListener maxButtons = new View.OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         v.requestFocus();
         if (v.equals(monsterLevelMax))
         {
            monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
            monsterStats();
         } else if (v.equals(monsterStatsMax))
         {
            monsterStatsHPPlus.setText("99");
            monsterStatsATKPlus.setText("99");
            monsterStatsRCVPlus.setText("99");
            monsterStats();
         } else if (v.equals(monsterStatsHPMax))
         {
            monsterStatsHPPlus.setText("99");
            monsterStats();
         } else if (v.equals(monsterStatsATKMax))
         {
            monsterStatsATKPlus.setText("99");
            monsterStats();
         } else if (v.equals(monsterStatsRCVMax))
         {
            monsterStatsRCVPlus.setText("99");
            monsterStats();
         } else if (v.equals(monsterAwakeningsMax))
         {
            monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
            monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
            grayAwakenings();
         } else if (v.equals(monsterStatsMaxAll))
         {
            monsterLevelValue.setText(Integer.toString(monster.getMaxLevel()));
            monsterStatsHPPlus.setText("99");
            monsterStatsATKPlus.setText("99");
            monsterStatsRCVPlus.setText("99");
            monsterAwakeningsValue.setText(Integer.toString(monster.getMaxAwakenings()));
            monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
            grayAwakenings();
            monsterStats();
         } else if (v.equals(monsterChoose))
         {
            monsterStats();
         }
      }
   };

   public void showAwakenings()
   {
      // Show max # of awakenings
      int i = 0;
      View view = null;
      for (i = monster.getMaxAwakenings(); i < 9; i++)
      {
         view = awakeningHolder.getChildAt(i);
         view.setVisibility(View.GONE);
      }
   }

   //Gray out depending on monsterAwakeningsValue
   @TargetApi(11)
   public void grayAwakenings()
   {
      int i = 0;
      View view = null;
      for (i = 0; i < monster.getCurrentAwakenings(); i++)
      {
         view = awakeningHolder.getChildAt(i);

         view.setAlpha(1);
      }
      for (i = monster.getCurrentAwakenings(); i < monster.getMaxAwakenings(); i++)
      {
         view = awakeningHolder.getChildAt(i);

         view.setAlpha((float) .5);
      }
   }

   /*

   //TextWatcher Testerino. Testicles.
   private TextWatcher monsterTextWatcher = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
         Log.d("Text on change", "yup");
         if(monsterAwakeningsValue.getText().toString().equals(""))
         {
            monsterAwakeningsValue.setText("0");
            monster.setCurrentAwakenings(Integer.parseInt(monsterAwakeningsValue.getText().toString()));
            grayAwakenings();
         }
         else if(monsterLevelValue.getText().toString().equals("")) {
            monsterLevelValue.setText("" + 1);
         }
         else if(monsterStatsHPPlus.getText().toString().equals("")){
            monsterStatsHPPlus.setText("" + 0);
         }
         else if(monsterStatsATKPlus.getText().toString().equals("")){
            monsterStatsATKPlus.setText("" + 0);
         }
         else if(monsterStatsRCVPlus.getText().toString().equals("")){
            monsterStatsRCVPlus.setText("" + 0);
         }
         monsterStats();
      }

      @Override
      public void afterTextChanged(Editable s) {
         Log.d("Text after change", "yup");
      }
   };
*/
   public void monsterStats()
   {
      //Update method because our TextWatcher no work
      monster.setCurrentLevel(Integer.parseInt(monsterLevelValue.getText().toString()));
      monster.setCurrentAtk(DamageCalculationUtil.monsterStatCalc(monster.getAtkMin(), monster.getAtkMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getAtkScale()));
      monster.setCurrentHp(DamageCalculationUtil.monsterStatCalc(monster.getHpMin(), monster.getHpMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getHpScale()));
      monster.setCurrentRcv(DamageCalculationUtil.monsterStatCalc(monster.getRcvMin(), monster.getRcvMax(), monster.getCurrentLevel(), monster.getMaxLevel(), monster.getRcvScale()));
      monster.setHpPlus(Integer.valueOf(monsterStatsHPPlus.getText().toString()));
      monster.setAtkPlus(Integer.valueOf(monsterStatsATKPlus.getText().toString()));
      monster.setRcvPlus(Integer.valueOf(monsterStatsRCVPlus.getText().toString()));
      monsterStatsHPBase.setText(String.valueOf(monster.getCurrentHp()));
      monsterStatsATKBase.setText(String.valueOf(monster.getCurrentAtk()));
      monsterStatsRCVBase.setText(String.valueOf(monster.getCurrentRcv()));
      monsterStatsHPTotal.setText(String.valueOf(monster.getTotalHp()));
      monsterStatsATKTotal.setText(String.valueOf(monster.getTotalAtk()));
      monsterStatsRCVTotal.setText(String.valueOf(monster.getTotalRcv()));
      monsterStatsWeightedValue.setText(String.valueOf(monster.getWeightedString()));
      monsterStatsTotalWeightedValue.setText(String.valueOf(monster.getTotalWeightedString()));

   }


   private EditText.OnKeyListener editTextOnKeyListener = new EditText.OnKeyListener()
   {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event)
      {
         if(keyCode == KeyEvent.KEYCODE_ENTER)
         {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
         }
         return false;
      }
   };


}
