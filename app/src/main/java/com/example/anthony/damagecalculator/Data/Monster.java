package com.example.anthony.damagecalculator.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Anthony on 7/13/2015.
 */
public class Monster
{
   public static final int HP_MULTIPLIER = 10;
   public static final int ATK_MULTIPLIER = 5;
   public static final int RCV_MULTIPLIER = 3;
   private int atkMax, atkMin, hpMax, hpMin, maxLevel, rcvMax, rcvMin, type1, type2, currentLevel, currentAtk, currentRcv, currentHp, atkPlus, hpPlus, rcvPlus, maxAwakenings, currentAwakenings;
   private Color element1, element2;
   private ArrayList<String> awokenSkills;
   private String activeSkill, leaderSkill, name;
   private double atkScale, rcvScale, hpScale;
   DecimalFormat format = new DecimalFormat("0.00");
   public Monster()
   {
      atkMax = 1370;
      atkMin = 913;
      hpMin = 1271;
      hpMax = 3528;
      rcvMin = 256;
      rcvMax = 384;
      maxLevel = 99;
      maxAwakenings = 7;
   }

   public int getCurrentLevel()
   {
      return currentLevel;
   }

   public void setCurrentLevel(int currentLevel)
   {
      this.currentLevel = currentLevel;
   }

   public int getCurrentAtk()
   {
      return currentAtk;
   }

   public int getCurrentHp()
   {
      return currentHp;
   }

   public int getTotalHp() {
      return currentHp + hpPlus * HP_MULTIPLIER;
   }
   public int getTotalAtk() {
      return currentAtk + atkPlus * ATK_MULTIPLIER;
   }
   public int getTotalRcv() {
      return currentRcv + rcvPlus * RCV_MULTIPLIER;
   }
   public String getWeightedString() {
      return format.format(getWeighted());
   }

   public double getWeighted() {
      return currentHp / HP_MULTIPLIER + currentAtk / ATK_MULTIPLIER + currentRcv / RCV_MULTIPLIER;
   }

   public void setCurrentAtk(int currentAtk)
   {
      this.currentAtk = currentAtk;
   }

   public int getCurrentRcv()
   {
      return currentRcv;
   }

   public void setCurrentRcv(int currentRcv)
   {
      this.currentRcv = currentRcv;
   }

   public int getAtkMax()

   {
      return atkMax;
   }

   public void setAtkMax(int atkMax)
   {
      this.atkMax = atkMax;
   }

   public int getAtkMin()
   {
      return atkMin;
   }

   public void setAtkMin(int atkMin)
   {
      this.atkMin = atkMin;
   }

   public int getHpMax()
   {
      return hpMax;
   }

   public void setHpMax(int hpMax)
   {
      this.hpMax = hpMax;
   }

   public int getHpMin()
   {
      return hpMin;
   }

   public void setHpMin(int hpMin)
   {
      this.hpMin = hpMin;
   }

   public int getMaxLevel()
   {
      return maxLevel;
   }

   public void setMaxLevel(int maxLevel)
   {
      this.maxLevel = maxLevel;
   }

   public int getRcvMax()
   {
      return rcvMax;
   }

   public void setRcvMax(int rcvMax)
   {
      this.rcvMax = rcvMax;
   }

   public int getRcvMin()
   {
      return rcvMin;
   }

   public void setRcvMin(int rcvMin)
   {
      this.rcvMin = rcvMin;
   }

   public int getType1()
   {
      return type1;
   }

   public void setType1(int type1)
   {
      this.type1 = type1;
   }

   public int getType2()
   {
      return type2;
   }

   public void setType2(int type2)
   {
      this.type2 = type2;
   }

   public Color getElement1()
   {
      return element1;
   }

   public void setElement1(Color element1)
   {
      this.element1 = element1;
   }

   public Color getElement2()
   {
      return element2;
   }

   public void setElement2(Color element2)
   {
      this.element2 = element2;
   }

   public ArrayList<String> getAwokenSkills()
   {
      return awokenSkills;
   }

   public void setAwokenSkills(ArrayList<String> awokenSkills)
   {
      this.awokenSkills = awokenSkills;
   }

   public String getActiveSkill()
   {
      return activeSkill;
   }

   public void setActiveSkill(String activeSkill)
   {
      this.activeSkill = activeSkill;
   }

   public String getLeaderSkill()
   {
      return leaderSkill;
   }

   public void setLeaderSkill(String leaderSkill)
   {
      this.leaderSkill = leaderSkill;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public double getAtkScale()
   {
      return atkScale;
   }

   public void setAtkScale(double atkScale)
   {
      this.atkScale = atkScale;
   }

   public double getRcvScale()
   {
      return rcvScale;
   }

   public void setRcvScale(double rcvScale)
   {
      this.rcvScale = rcvScale;
   }

   public double getHpScale()
   {
      return hpScale;
   }

   public void setHpScale(double hpScale)
   {
      this.hpScale = hpScale;
   }

   public void setCurrentHp(int currentHp)
   {
      this.currentHp = currentHp;
   }

   public int getAtkPlus()
   {
      return atkPlus;
   }

   public void setAtkPlus(int atkPlus)
   {
      this.atkPlus = atkPlus;
   }

   public int getHpPlus()
   {
      return hpPlus;
   }

   public void setHpPlus(int hpPlus)
   {
      this.hpPlus = hpPlus;
   }

   public int getRcvPlus()
   {
      return rcvPlus;
   }

   public void setRcvPlus(int rcvPlus)
   {
      this.rcvPlus = rcvPlus;
   }

   public int getMaxAwakenings() {
      return maxAwakenings;
   }

   public void setMaxAwakenings(int maxAwakenings) {
      this.maxAwakenings = maxAwakenings;
   }

   public int getCurrentAwakenings() {
      return currentAwakenings;
   }

   public void setCurrentAwakenings(int currentAwakenings) {
      this.currentAwakenings = currentAwakenings;
   }
}


