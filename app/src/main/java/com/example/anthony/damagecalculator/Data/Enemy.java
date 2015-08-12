package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Anthony on 7/16/2015.
 */
public class Enemy implements Parcelable {
    private int targetHp, currentHp, targetDef, beforeGravityHP, beforeDefenseBreak, damageThreshold;
    private double gravityPercent;
    private Color targetColor, absorb;
    private ArrayList<Color> reduction;
    private ArrayList<Integer> gravityList;
    private Boolean hasAbsorb = false, hasReduction = false, hasDamageThreshold = false;


    //default is satan from  Lord of Hell - Mythical
    public Enemy() {
        reduction = new ArrayList<Color>();
        gravityList = new ArrayList<Integer>();
        targetHp = 6666666;
        targetDef = 368;
        currentHp = targetHp;
        beforeGravityHP = currentHp;
        beforeDefenseBreak = targetDef;
        targetColor = Color.DARK;
        absorb = Color.HEART;
        gravityPercent = 1;
        damageThreshold = 200000;
    }


    public int getTargetHp() {
        return targetHp;
    }

    public void setTargetHp(int targetHp) {
        this.targetHp = targetHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }

    public int getTargetDef() {
        return targetDef;
    }

    public void setTargetDef(int targetDef) {
        this.targetDef = targetDef;
    }

    public double getGravityPercent() {
        return gravityPercent;
    }

    public void setGravityPercent(double gravityPercent) {
        this.gravityPercent = gravityPercent;
    }

    public Color getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(Color targetColor) {
        this.targetColor = targetColor;
    }

    public double getPercentHp() {
        if (targetHp == 0) {
            return 0;
        }
        return (double) currentHp / targetHp;
    }

    public int getBeforeGravityHP() {
        return beforeGravityHP;
    }

    public void setBeforeGravityHP(int beforeGravityHP) {
        this.beforeGravityHP = beforeGravityHP;
    }

    public int getBeforeDefenseBreak() {
        return beforeDefenseBreak;
    }

    public void setBeforeDefenseBreak(int beforeDefenseBreak) {
        this.beforeDefenseBreak = beforeDefenseBreak;
    }

    public int getDamageThreshold() {
        return damageThreshold;
    }

    public void setDamageThreshold(int damageThreshold) {
        this.damageThreshold = damageThreshold;
    }

    public Color getAbsorb() {
        return absorb;
    }

    public void setAbsorb(Color absorb) {
        this.absorb = absorb;
    }

    public ArrayList<Color> getReduction() {
        return reduction;
    }

    public void addReduction(Color color) {
        reduction.add(color);
    }

    public void removeReduction(Color color) {
        reduction.remove(color);
    }

    public boolean containsReduction(Color color) {
        return reduction.contains(color);
    }

    public boolean reductionIsEmpty() {
        return reduction.isEmpty();
    }

    public Boolean getHasAbsorb() {
        return hasAbsorb;
    }

    public void setHasAbsorb(Boolean hasAbsorb) {
        this.hasAbsorb = hasAbsorb;
    }

    public Boolean getHasDamageThreshold() {
        return hasDamageThreshold;
    }

    public void setHasDamageThreshold(Boolean hasDamageThreshold) {
        this.hasDamageThreshold = hasDamageThreshold;
    }

    public Boolean getHasReduction() {
        return hasReduction;
    }

    public void setHasReduction(Boolean hasReduction) {
        this.hasReduction = hasReduction;
    }

    public ArrayList<Integer> getGravityList() {
        return gravityList;
    }

    public void setGravityList(ArrayList<Integer> gravityList) {
        this.gravityList = gravityList;
    }

    public int getGravityList(int position){
        return gravityList.get(position);
    }

    public Enemy(Parcel source) {
        targetHp = source.readInt();
        currentHp = source.readInt();
        targetDef = source.readInt();
        beforeGravityHP = source.readInt();
        beforeDefenseBreak = source.readInt();
        damageThreshold = source.readInt();
        gravityPercent = source.readDouble();
        targetColor = (Color) source.readSerializable();
        absorb = (Color) source.readSerializable();
        reduction = source.readArrayList(Color.class.getClassLoader());
        hasAbsorb = source.readByte() == 1;
        hasReduction = source.readByte() == 1;
        hasDamageThreshold = source.readByte() == 1;
        gravityList = source.readArrayList(Integer.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(targetHp);
        dest.writeInt(currentHp);
        dest.writeInt(targetDef);
        dest.writeInt(beforeGravityHP);
        dest.writeInt(beforeDefenseBreak);
        dest.writeInt(damageThreshold);
        dest.writeDouble(gravityPercent);
        dest.writeSerializable(targetColor);
        dest.writeSerializable(absorb);
        dest.writeList(reduction);
        dest.writeByte((byte) (hasAbsorb ? 1 : 0));
        dest.writeByte((byte) (hasReduction ? 1 : 0));
        dest.writeByte((byte) (hasDamageThreshold ? 1 : 0));
        dest.writeList(gravityList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Enemy> CREATOR = new Parcelable.Creator<Enemy>() {
        public Enemy createFromParcel(Parcel source) {
            return new Enemy(source);
        }

        public Enemy[] newArray(int size) {
            return new Enemy[size];
        }
    };

}
