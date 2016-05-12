package com.example.anthony.damagecalculator.TextWatcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Anthony on 7/13/2015.
 */
public class MyTextWatcher implements TextWatcher
{
   public static final int HP_STAT = 0;
   public static final int ATK_STAT = 1;
   public static final int RCV_STAT = 2;
   public static final int CURRENT_LEVEL = 3;
   public static final int AWAKENINGS = 4;
   public static final int TARGET_HP = 5;
   public static final int CURRENT_HP = 6;
   public static final int TARGET_DEFENSE = 7;
   public static final int DAMAGE_THRESHOLD = 8;
   public static final int ADDITIONAL_COMBOS = 9;
   public static final int REDUCTION_VALUE = 10;

   private int statToChange;

   private ChangeStats update;

   public MyTextWatcher(int statToChange, ChangeStats update)
   {
      this.statToChange = statToChange;
      this.update = update;
   }

   public interface ChangeStats
   {
      public void changeMonsterAttribute(int statToChange, int statValue);
   }


   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after)
   {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count)
   {
      if(s.toString().equals("") || s.toString().equals("-"))
      {
         s = "0";
      }
      update.changeMonsterAttribute(statToChange, Integer.valueOf(s.toString()));
   }

   @Override
   public void afterTextChanged(Editable s)
   {
      Log.d ("After Text Change", "yarp");
   }
}
