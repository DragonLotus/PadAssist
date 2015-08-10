package com.example.anthony.damagecalculator.Util;

import com.example.anthony.damagecalculator.Data.Monster;
import com.example.anthony.damagecalculator.Data.OrbMatch;

import java.util.ArrayList;

/**
 * Created by Thomas on 7/11/2015.
 */
public class DamageCalculationUtil
{


   public static double totalMonsterDamageMain(Monster monster, ArrayList<OrbMatch> orbmatches, int orbAwakenings) {
      double totalDamage = 0;
      for(int i = 0; i < orbmatches.size(); i++) {
         if(orbmatches.get(i).getColor().equals(monster.getElement1())) {
            totalDamage += orbMatch(monster.getCurrentAtk(), orbmatches.get(i), orbAwakenings,1);
         }
      }
      return totalDamage;
   }
   public static double orbMatch(int Attack, OrbMatch orbMatch, int OrbAwakenings, int TPAwakenings)
   {
      // Write in combos
      // Attack, Orb Awakenings, TPA from API, draw from Monster Database
      //(1 + (number of plus orbs).06) x (1 + (number of plus orb awakenings).05)
      if (orbMatch.getOrbsLinked() < 3)
      {
         throw new IllegalArgumentException();
      }
      double damage = Attack * (((orbMatch.getOrbsLinked() - 3) * .25) + 1)
              * ((orbMatch.getNumOrbPlus() * .06) + 1) * ((OrbAwakenings * .05) + 1);
      if (orbMatch.getOrbsLinked() == 4)
      {
         damage = damage * Math.pow(1.5, TPAwakenings);
      }

      return Math.ceil(damage);
   }
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

   public static double monsterStatCalc(int minimumStat, int maximumStat, int currentLevel, int maxLevel, double statScale)
   {
      if(currentLevel <= 1)
      {
         return minimumStat;
      }
      //return (int) Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double) ((currentLevel - 1) / (maxLevel - 1)), statScale)));
      return Math.floor(minimumStat + (maximumStat - minimumStat) * (Math.pow((double)(currentLevel - 1) / (maxLevel - 1), statScale)));
   }
}
