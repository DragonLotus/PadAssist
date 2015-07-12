package com.example.anthony.damagecalculator.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anthony.damagecalculator.MainActivity;
import com.example.anthony.damagecalculator.R;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

/**
 * Created by Thomas on 7/11/2015.
 */
public class MainFragment extends Fragment
{
    private static final String ARG_SECTION_NUMBER = "section_number";
    private TextView editTeam, sectionLabel, manualEdit, color, orbsLinked, orbPlus, row, addMatch;
    private TextView listText, calculate;
    private ImageView red, blue, green, light, dark;
    private LinearLayout editHolder;
    private CheckBox rowCheckBox;
    //private RelativeLayout
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        editHolder = (LinearLayout) rootView.findViewById(R.id.editHolder);
        rowCheckBox = (CheckBox) rootView.findViewById(R.id.rowCheckBox);
        initTextView(rootView);
        initImageView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Write your code here
        Log.d("Testing orbMatch", "orbMatch: " + DamageCalculationUtil.orbMatch(1984, 4, 4, 6, 1));
    }
    private void initTextView(View rootView)
    {
        editTeam = (TextView) rootView.findViewById(R.id.editTeam);
        sectionLabel = (TextView) rootView.findViewById(R.id.section_label);
        manualEdit = (TextView) rootView.findViewById(R.id.manualEdit);
        color = (TextView) rootView.findViewById(R.id.color);
        orbsLinked = (TextView) rootView.findViewById(R.id.orbsLinked);
        orbPlus = (TextView) rootView.findViewById(R.id.orbPlus);
        row = (TextView) rootView.findViewById(R.id.row);
        addMatch = (TextView) rootView.findViewById(R.id.addMatch);
        listText = (TextView) rootView.findViewById(R.id.List);
        calculate = (TextView) rootView.findViewById(R.id.calculate);
    }
    private void initImageView(View rootView)
    {
        red = (ImageView) rootView.findViewById(R.id.red);
        blue = (ImageView) rootView.findViewById(R.id.blue);
        green = (ImageView) rootView.findViewById(R.id.green);
        light = (ImageView) rootView.findViewById(R.id.light);
        dark = (ImageView) rootView.findViewById(R.id.dark);
    }

    public void onCheckBoxClicked(View view)
    {
        //Is the view now checked?
        boolean checked = ((CheckBox)view).isChecked();


    }
}
