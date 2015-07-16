package com.example.anthony.damagecalculator.Data;

/**
 * Created by Anthony on 7/16/2015.
 */
public class Enemy
{
   private int targetHp, currentHp, targetDef;
   private double gravityPercent;
   private Color targetColor;


   //default is satan from  Lord of Hell - Mythical
   public Enemy()
   {
      this.targetHp = 6666666;
      this.targetDef = 368;
      this.currentHp = this.targetHp;
      gravityPercent = 0;
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
}
