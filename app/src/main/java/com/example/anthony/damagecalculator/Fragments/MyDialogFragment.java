package com.example.anthony.damagecalculator.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Anthony on 7/13/2015.
 */
public class MyDialogFragment extends DialogFragment
{
   public interface ResetLayout
   {
      public void resetLayout();
   }

   private ResetLayout reset;

   public Dialog onCreateDialog(Bundle savedInstanceState)
   {

      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
      alertDialogBuilder.setTitle("Reset page?");
      alertDialogBuilder.setMessage("Are you sure you want to clear everything?");
      alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int which)
         {
            reset.resetLayout();
         }

      });
      alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int which)
         {
            dialog.dismiss();
         }
      });

      return alertDialogBuilder.create();
   }

   public static MyDialogFragment newInstance(ResetLayout resetVariable)
   {
      MyDialogFragment dialogFragment = new MyDialogFragment();
      dialogFragment.setResetLayout(resetVariable);
      return dialogFragment;
   }

   public void setResetLayout(ResetLayout reset)
   {
      this.reset = reset;
   }


}
