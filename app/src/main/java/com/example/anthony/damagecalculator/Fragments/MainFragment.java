package com.example.anthony.damagecalculator.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
   private ImageButton redOrb, blueOrb, greenOrb, lightOrb, darkOrb;
   private LinearLayout editHolder;
   private Button addMatch, calculate, reset;
   private SeekBar orbsLinked, orbsPlus;
   private CheckBox rowCheckBox;
   private ListView orbMatches;
   private OrbMatchAdapter orbMatchAdapter;
   private boolean isRow;
   private OrbMatch orbMatch;
   private Color orbColor;
   private Toast toast;
   private MyAlertDialogFragment dialog;

   private CompoundButton.OnCheckedChangeListener rowCheckedChangeListener = new CompoundButton.OnCheckedChangeListener()
   {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
      {
         isRow = isChecked;
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

   private Button.OnClickListener resetOnClickListener = new Button.OnClickListener()
   {
      public void onClick(View v)
      {
         setAlltoGray();
         redOrb.setSelected(true);
         orbColor = Color.RED;
         orbsLinked.setProgress(0);
         orbsPlus.setProgress(0);
         rowCheckBox.setEnabled(false);
         rowCheckBox.setChecked(false);
         orbMatchAdapter.clear();
         if(toast != null) {
            toast.cancel();
         }
         toast = Toast.makeText(getActivity(), "Everything Reset", Toast.LENGTH_SHORT);
         toast.show();
      }
   };



   private void setAlltoGray()
   {
      redOrb.setSelected(false);
      blueOrb.setSelected(false);
      greenOrb.setSelected(false);
      lightOrb.setSelected(false);
      darkOrb.setSelected(false);
   }

   private ImageButton.OnClickListener orbOnClickListener = new ImageButton.OnClickListener()
   {
      public void onClick(View v)
      {
         if(v.equals(redOrb))
         {
            orbColor = Color.RED;
            setAlltoGray();
            redOrb.setSelected(true);
         }
         else if(v.equals(blueOrb))
         {
            orbColor = Color.BLUE;
            setAlltoGray();
            blueOrb.setSelected(true);
         }
         else if(v.equals(greenOrb))
         {
            orbColor = Color.GREEN;
            setAlltoGray();
            greenOrb.setSelected(true);
         }
         else if(v.equals(lightOrb))
         {
            orbColor = Color.LIGHT;
            setAlltoGray();
            lightOrb.setSelected(true);

         }
         else if(v.equals(darkOrb))
         {
            orbColor = Color.DARK;
            setAlltoGray();
            darkOrb.setSelected(true);
         }
      }
   };



/*

   private ListView.OnItemClickListener orbMatchesOnItemClickListener = new ListView.OnItemClickListener()
   {
      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
      {
         orbMatchAdapter.remove(orbMatchAdapter.getItem(position));
      }
   };
*/


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
      orbsLinked = (SeekBar) rootView.findViewById(R.id.orbsLinkedSpinner);
      orbsPlus = (SeekBar) rootView.findViewById(R.id.orbsPlusSpinner);
      addMatch = (Button) rootView.findViewById(R.id.addMatch);
      calculate = (Button) rootView.findViewById(R.id.calculate);
      reset = (Button) rootView.findViewById(R.id.reset);
      orbMatches = (ListView) rootView.findViewById(R.id.orbMatches);
      initTextView(rootView);
      initImageView(rootView);
      redOrb.setSelected(true);
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
      //colorChoices.setOnCheckedChangeListener(colorChoicesOnCheckedListener);
      addMatch.setOnClickListener(addMatchOnClickListener);
      redOrb.setOnClickListener(orbOnClickListener);
      blueOrb.setOnClickListener(orbOnClickListener);
      greenOrb.setOnClickListener(orbOnClickListener);
      lightOrb.setOnClickListener(orbOnClickListener);
      darkOrb.setOnClickListener(orbOnClickListener);

      reset.setOnClickListener(resetOnClickListener);
      orbMatchAdapter = new OrbMatchAdapter(getActivity(), R.layout.orb_match_row, new ArrayList<OrbMatch>());
      orbMatches.setAdapter(orbMatchAdapter);
      //orbMatches.setOnItemClickListener(orbMatchesOnItemClickListener);

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

   private void initImageView(View rootView)
   {
      redOrb = (ImageButton) rootView.findViewById(R.id.redOrb);
      blueOrb = (ImageButton) rootView.findViewById(R.id.blueOrb);
      greenOrb = (ImageButton) rootView.findViewById(R.id.greenOrb);
      lightOrb = (ImageButton) rootView.findViewById(R.id.lightOrb);
      darkOrb = (ImageButton) rootView.findViewById(R.id.darkOrb);
   }


}
