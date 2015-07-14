package com.example.anthony.damagecalculator.TextWatcher;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Anthony on 7/13/2015.
 */
public class MyTextWatcher implements TextWatcher
{
   public interface ChangeStats
   {
      public void monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale);
   }

   private ChangeStats update;

   @Override
   public void beforeTextChanged(CharSequence s, int start, int count, int after)
   {

   }

   @Override
   public void onTextChanged(CharSequence s, int start, int before, int count)
   {
      update.monsterStatCalc();
   }

   @Override
   public void afterTextChanged(Editable s)
   {

   }
}
