package com.example.anthony.damagecalculator.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anthony.damagecalculator.R;

/**
 * Created by Thomas likes children on 7/12/2015.
 */
public class MyAlertDialogFragment extends DialogFragment
{
   private static final String ARG_SECTION_NUMBER = "section_number";
   private Context mContext;

   public static MyAlertDialogFragment newInstance(int sectionNumber)
   {
      MyAlertDialogFragment fragment = new MyAlertDialogFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
   }

   public MyAlertDialogFragment()
   {
      mContext = getActivity();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState)
   {
      View rootView = inflater.inflate(R.layout.main_fragment, container, false);

      return rootView;
   }

   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState)
   {
      super.onActivityCreated(savedInstanceState);

   }

   public Dialog onCreateDialog(Bundle savedInstanceState)
   {
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
      alertDialogBuilder.setTitle("REALLY?");
      alertDialogBuilder.setMessage("Are you sure you want to clear everything?");
      alertDialogBuilder.setPositiveButton("YES", null);
      alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int which)
         {
            dialog.dismiss();
         }
      });

   return alertDialogBuilder.create();

   }
}
