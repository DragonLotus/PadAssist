package com.example.anthony.damagecalculator.Util;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil
{

   public static double orbMatch(int Attack, int OrbsLinked, int OrbPlus, int OrbAwakenings, int TPAwakenings)
   {
      // Write in combos
      // Attack, Orb Awakenings, TPA from API, draw from Monster Database
      //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
      if (OrbsLinked < 3)
      {
         throw new IllegalArgumentException();
      }
      double damage = Attack * (((OrbsLinked - 3) * .25) + 1)
            * ((OrbPlus * .06) + 1) * ((OrbAwakenings * .05) + 1);
      if (OrbsLinked == 4)
      {
         damage = damage * Math.pow(1.5, TPAwakenings);
      }

      return Math.ceil(damage);
   }

   public static double finalMultiplier(double leadMul, double extraMul, int rowAwakenings, int numberOfRows)
   {
      double multiplier;
      if (numberOfRows == 0)
      {
         multiplier = leadMul * extraMul;
      } else
      {
         multiplier = leadMul * extraMul * ((rowAwakenings * 0.1) * numberOfRows + 1);
      }
      return multiplier;
   }

   public static int monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale)
   {
      return (int) Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) ((currentLevel - 1) / (maxLevel)), statScale)));
   }
}
