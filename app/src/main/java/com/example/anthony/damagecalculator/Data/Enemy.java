package com.example.anthony.damagecalculator.Data;

import java.util.ArrayList;

/**
 * Created by Anthony on 7/16/2015.
 */
public class Enemy
{
   private int targetHp, currentHp, targetDef, beforeGravityHP, beforeDefenseBreak, damageThreshold;
   private double gravityPercent;
   private Color targetColor, absorb;
   private ArrayList<Color> reduction;


   //default is satan from  Lord of Hell - Mythical
   public Enemy()
   {
      reduction = new ArrayList<Color>();
      this.targetHp = 6666666;
      this.targetDef = 368;
      this.currentHp = this.targetHp;
      this.beforeGravityHP = this.currentHp;
      this.beforeDefenseBreak = this.targetDef;
      this.targetColor = Color.DARK;
      this.absorb = Color.BLUE;
      gravityPercent = 1;
   }


   public int getTargetHp()
   {
      return targetHp;
   }

   public void setTargetHp(int targetHp)
   {
      this.targetHp = targetHp;
   }

   public int getCurrentHp()
   {
      return currentHp;
   }

   public void setCurrentHp(int currentHp)
   {
      this.currentHp = currentHp;
   }

   public int getTargetDef()
   {
      return targetDef;
   }

   public void setTargetDef(int targetDef)
   {
      this.targetDef = targetDef;
   }

   public double getGravityPercent()
   {
      return gravityPercent;
   }

   public void setGravityPercent(double gravityPercent)
   {
      this.gravityPercent = gravityPercent;
   }

   public Color getTargetColor()
   {
      return targetColor;
   }

   public void setTargetColor(Color targetColor)
   {
      this.targetColor = targetColor;
   }

   public double getPercentHp()
   {
      if (targetHp == 0)
      {
         return 0;
      }
      return (double) currentHp / targetHp;
   }

   public int getBeforeGravityHP()
   {
      return beforeGravityHP;
   }

   public void setBeforeGravityHP(int beforeGravityHP)
   {
      this.beforeGravityHP = beforeGravityHP;
   }

   public int getBeforeDefenseBreak()
   {
      return beforeDefenseBreak;
   }

   public void setBeforeDefenseBreak(int beforeDefenseBreak)
   {
      this.beforeDefenseBreak = beforeDefenseBreak;
   }

   public int getDamageThreshold()
   {
      return damageThreshold;
   }

   public void setDamageThreshold(int damageThreshold)
   {
      this.damageThreshold = damageThreshold;
   }

   public Color getAbsorb()
   {
      return absorb;
   }

   public void setAbsorb(Color absorb)
   {
      this.absorb = absorb;
   }

   public ArrayList<Color> getReduction()
   {
      return reduction;
   }

   public void addReduction(Color color)
   {
      reduction.add(color);
   }

   public void removeReduction(Color color)
   {
      reduction.remove(color);
   }

   public boolean containsReduction(Color color)
   {
      return reduction.contains(color);
   }
   public boolean reductionIsEmpty()
   {
      return reduction.isEmpty();
   }

}
