package com.example.anthony.damagecalculator.Data;

/**
 * Created by Anthony on 7/16/2015.
 */
public class Enemy
{
   private int targetHp, currentHp, targetDef;
   private double gravityPercent;
   private Color targetColor;



   public Enemy(int targetHp, int currentHp, int targetDef, double gravityPercent, Color targetColor)
   {
      this.targetHp = targetHp;
      this.currentHp = currentHp;
      this.targetDef = targetDef;
      this.gravityPercent = gravityPercent;
      this.targetColor = targetColor;
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

   public void setGravityPercent(int gravityPercent)
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
