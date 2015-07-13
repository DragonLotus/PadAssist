package com.example.anthony.damagecalculator.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.anthony.damagecalculator.Adapters.OrbMatchAdapter;
import com.example.anthony.damagecalculator.Data.Color;
import com.example.anthony.damagecalculator.Data.OrbMatch;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/11/2015.
 */
public class MainFragment extends Fragment
{
   private static final String ARG_SECTION_NUMBER = "section_number";
   private TextView editTeam, sectionLabel, color, row, orbsLinkedValue, orbsPlusValue;
   private TextView listText;
   private ImageView red, blue, green, light, dark;
   private LinearLayout editHolder;
   private Button addMatch, calculate;
   private SeekBar orbsLinked, orbsPlus;
   private CheckBox rowCheckBox, manualEdit;
   private ListView orbMatches;
   private OrbMatchAdapter orbMatchAdapter;
   private boolean isManualEditing = false;
   private boolean isRow;
   private OrbMatch orbMatch;
   private Color orbColor;
   private RadioGroup colorChoices;

   private CompoundButton.OnCheckedChangeListener rowCheckedChangeListener = new CompoundButton.OnCheckedChangeListener()
   {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {
         isRow = isChecked;
      }
   };

   private CompoundButton.OnCheckedChangeListener manualEditCheckedChangeListener = new CompoundButton.OnCheckedChangeListener()
   {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {

      }
   };

   private SeekBar.OnSeekBarChangeListener orbsLinkedSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
   {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
         orbsLinkedValue.setText("" + (progress + 3));
         orbsPlus.setMax(progress + 3);
         if((progress + 3) >= 6 && (progress + 3) != 7)
         {
            rowCheckBox.setEnabled(true);
         }
         else
         {
            rowCheckBox.setEnabled(false);
            rowCheckBox.setChecked(false);
         }
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {

      }
   };

   private SeekBar.OnSeekBarChangeListener orbsPlusSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener()
   {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
      {
         orbsPlusValue.setText("" + (progress));
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar)
      {

      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar)
      {

      }
   };


   private View.OnClickListener addMatchOnClickListener = new View.OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         orbMatch = new OrbMatch(orbsLinked.getProgress()+3, orbsPlus.getProgress(),orbColor, isRow);
         orbMatchAdapter.add(orbMatch);
      }
   };

   private RadioGroup.OnCheckedChangeListener colorChoicesOnCheckedListener = new RadioGroup.OnCheckedChangeListener()
   {
      public void onCheckedChanged(RadioGroup group, int checkedId )
      {

         if(checkedId == R.id.redButton)
         {
            orbColor = Color.RED;
         }
         else if(checkedId == R.id.blueButton)
         {
            orbColor = Color.BLUE;
         }
         else if(checkedId == R.id.greenButton)
         {
            orbColor = Color.GREEN;
         }
         else if(checkedId == R.id.darkButton)
         {
            orbColor = Color.DARK;
         }
         else if(checkedId == R.id.lightButton)
         {
            orbColor = Color.LIGHT;
         }
      }
   };

   //private View.OnClickListener addMatchClickListener = new View.OnClickListener() {
   //    @Override
   //    public void onClick(View view) {
   //      orbMatchAdapter.add(orbMatch);
   //   }
   //};

   //private RelativeLayout
   public static MainFragment newInstance(int sectionNumber)
   {
      MainFragment fragment = new MainFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
   }

   public MainFragment()
   {
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState)
   {
      View rootView = inflater.inflate(R.layout.main_fragment, container, false);
      editHolder = (LinearLayout) rootView.findViewById(R.id.editHolder);
      rowCheckBox = (CheckBox) rootView.findViewById(R.id.rowCheckBox);
      rowCheckBox.setEnabled(false);
      manualEdit = (CheckBox) rootView.findViewById(R.id.manualEdit);
      orbsLinked = (SeekBar) rootView.findViewById(R.id.orbsLinkedSpinner);
      orbsPlus = (SeekBar) rootView.findViewById(R.id.orbsPlusSpinner);
      addMatch = (Button) rootView.findViewById(R.id.addMatch);
      calculate = (Button) rootView.findViewById(R.id.calculate);
      orbMatches = (ListView) rootView.findViewById(R.id.orbMatches);
      initTextView(rootView);
      //initImageView(rootView);
      colorChoices = (RadioGroup) rootView.findViewById(R.id.orbChoices);
      colorChoices.check(1);
      return rootView;
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);
      //Write your code here
      orbsLinked.setOnSeekBarChangeListener(orbsLinkedSeekBarChangeListener);
      orbsPlus.setOnSeekBarChangeListener(orbsPlusSeekBarChangeListener);
      rowCheckBox.setOnCheckedChangeListener(rowCheckedChangeListener);
      manualEdit.setOnCheckedChangeListener(manualEditCheckedChangeListener);
      colorChoices.setOnCheckedChangeListener(colorChoicesOnCheckedListener);
      addMatch.setOnClickListener(addMatchOnClickListener);
      orbMatchAdapter = new OrbMatchAdapter(getActivity(), R.layout.orb_match_row, new ArrayList<OrbMatch>());
      orbMatches.setAdapter(orbMatchAdapter);


      Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
   }

   private void initTextView(View rootView)
   {
      editTeam = (TextView) rootView.findViewById(R.id.editTeam);
      sectionLabel = (TextView) rootView.findViewById(R.id.section_label);
      color = (TextView) rootView.findViewById(R.id.color);
      row = (TextView) rootView.findViewById(R.id.row);
      listText = (TextView) rootView.findViewById(R.id.List);
      orbsLinkedValue = (TextView) rootView.findViewById(R.id.orbsLinkedValue);
      orbsPlusValue = (TextView) rootView.findViewById(R.id.orbsPlusValue);
   }
/*
   private void initImageView(View rootView)
   {
      red = (ImageView) rootView.findViewById(R.id.red);
      blue = (ImageView) rootView.findViewById(R.id.blue);
      green = (ImageView) rootView.findViewById(R.id.green);
      light = (ImageView) rootView.findViewById(R.id.light);
      dark = (ImageView) rootView.findViewById(R.id.dark);
   }
*/

}
