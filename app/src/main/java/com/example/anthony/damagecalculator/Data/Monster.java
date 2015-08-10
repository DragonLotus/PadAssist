package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Anthony on 7/13/2015.
 */
public class Monster implements Parcelable {
    public static final int HP_MULTIPLIER = 10;
    public static final int ATK_MULTIPLIER = 5;
    public static final int RCV_MULTIPLIER = 3;
    private int atkMax, atkMin, hpMax, hpMin, maxLevel, rcvMax, rcvMin, type1, type2, currentLevel, atkPlus, hpPlus, rcvPlus, maxAwakenings, currentAwakenings;
    private Color element1, element2;
    private ArrayList<String> awokenSkills;
    private String activeSkill, leaderSkill, name;
    private double atkScale, rcvScale, hpScale, currentAtk, currentRcv, currentHp;
    DecimalFormat format = new DecimalFormat("0.00");

    public Monster() {
        currentLevel = 1;
        atkMax = 1370;
        atkMin = 913;
        hpMin = 1271;
        hpMax = 3528;
        rcvMin = 256;
        rcvMax = 384;
        maxLevel = 99;
        atkScale = 1;
        rcvScale = 1;
        hpScale = 1;
        maxAwakenings = 7;
        hpPlus = 99;
        atkPlus = 99;
        rcvPlus = 99;
        currentHp = DamageCalculationUtil.monsterStatCalc(hpMin, hpMax, currentLevel, maxLevel, hpScale);
        currentAtk = DamageCalculationUtil.monsterStatCalc(atkMin, atkMax, currentLevel, maxLevel, atkScale);
        currentRcv = DamageCalculationUtil.monsterStatCalc(rcvMin, rcvMax, currentLevel, maxLevel, rcvScale);
        currentAwakenings = 7;
        element1 = Color.LIGHT;
        element2 = Color.LIGHT;
        name = "Kirin of the Sacred Gleam, Sakuya";
    }

    public int getElement1Damage(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, int combos) {
        return (int) DamageCalculationUtil.monsterElement1Damage(this, orbMatches, orbPlusAwakenings, combos);
    }

    public String getElement1DamageString(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, int combos) {
        return String.valueOf(getElement1Damage(orbMatches, orbPlusAwakenings, combos));
    }

    public int getElement1DamageEnemy(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, Enemy enemy, int combos) {
        int damage = getElement1Damage(orbMatches, orbPlusAwakenings, combos);
        if(element1.equals(Color.RED)){
            if(enemy.getTargetColor().equals(Color.BLUE)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.GREEN)){
                return damage*2;
            }
            else return damage;
        }
        else if(element1.equals(Color.BLUE)){
            if(enemy.getTargetColor().equals(Color.GREEN)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.RED)){
                return damage*2;
            }
            else return damage;
        }
        else if(element1.equals(Color.GREEN)){
            if(enemy.getTargetColor().equals(Color.RED)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.BLUE)){
                return damage*2;
            }
            else return damage;
        }
        else if(element1.equals(Color.LIGHT)){
            if(enemy.getTargetColor().equals(Color.DARK)){
                return damage*2;
            }
            else return damage;
        }
        else if(element1.equals(Color.DARK)){
            if(enemy.getTargetColor().equals(Color.LIGHT)){
                return damage*2;
            }
            else return damage;
        }
        return damage;
    }

    public String getElement1DamageEnemyString(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, Enemy enemy, int combos) {
        return String.valueOf(getElement1DamageEnemy(orbMatches, orbPlusAwakenings, enemy, combos));
    }

    public int getElement2Damage(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, int combos) {
        if (element2.equals(element1)){
            return (int)Math.ceil(getElement1Damage(orbMatches, orbPlusAwakenings, combos) * .1);
        }
        return (int) DamageCalculationUtil.monsterElement2Damage(this, orbMatches, orbPlusAwakenings, combos);
    }

    public String getElement2DamageString(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, int combos) {
        return String.valueOf(getElement2Damage(orbMatches, orbPlusAwakenings, combos));
    }

    public int getElement2DamageEnemy(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, Enemy enemy, int combos) {
        int damage = getElement2Damage(orbMatches, orbPlusAwakenings, combos);
        if(element2.equals(Color.RED)){
            if(enemy.getTargetColor().equals(Color.BLUE)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.GREEN)){
                return damage*2;
            }
            else return damage;
        }
        else if(element2.equals(Color.BLUE)){
            if(enemy.getTargetColor().equals(Color.GREEN)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.RED)){
                return damage*2;
            }
            else return damage;
        }
        else if(element2.equals(Color.GREEN)){
            if(enemy.getTargetColor().equals(Color.RED)){
                return damage/2;
            }
            else if(enemy.getTargetColor().equals(Color.BLUE)){
                return damage*2;
            }
            else return damage;
        }
        else if(element2.equals(Color.LIGHT)){
            if(enemy.getTargetColor().equals(Color.DARK)){
                return damage*2;
            }
            else return damage;
        }
        else if(element2.equals(Color.DARK)){
            if(enemy.getTargetColor().equals(Color.LIGHT)){
                return damage*2;
            }
            else return damage;
        }
        return damage;
    }

    public String getElement2DamageEnemyString(ArrayList<OrbMatch> orbMatches, int orbPlusAwakenings, Enemy enemy, int combos) {
        return String.valueOf(getElement2DamageEnemy(orbMatches, orbPlusAwakenings, enemy, combos));
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentAtk() {
        return (int) currentAtk;
    }

    public int getCurrentHp() {
        return (int) currentHp;
    }

    public int getTotalHp() {
        return (int) currentHp + hpPlus * HP_MULTIPLIER;
    }

    public int getTotalAtk() {
        return (int) currentAtk + atkPlus * ATK_MULTIPLIER;
    }

    public int getTotalRcv() {
        return (int) currentRcv + rcvPlus * RCV_MULTIPLIER;
    }

    public String getWeightedString() {
        return format.format(getWeighted());
    }

    public double getWeighted() {
        return currentHp / HP_MULTIPLIER + currentAtk / ATK_MULTIPLIER + currentRcv / RCV_MULTIPLIER;
    }

    public String getTotalWeightedString() {
        return format.format(getTotalWeighted());
    }

    public double getTotalWeighted() {
        return getWeighted() + hpPlus + atkPlus + rcvPlus;
    }

    public void setCurrentAtk(double currentAtk) {
        this.currentAtk = currentAtk;
    }

    public int getCurrentRcv() {
        return (int) currentRcv;
    }

    public void setCurrentRcv(double currentRcv) {
        this.currentRcv = currentRcv;
    }

    public int getAtkMax()

    {
        return atkMax;
    }

    public void setAtkMax(int atkMax) {
        this.atkMax = atkMax;
    }

    public int getAtkMin() {
        return atkMin;
    }

    public void setAtkMin(int atkMin) {
        this.atkMin = atkMin;
    }

    public int getHpMax() {
        return hpMax;
    }

    public void setHpMax(int hpMax) {
        this.hpMax = hpMax;
    }

    public int getHpMin() {
        return hpMin;
    }

    public void setHpMin(int hpMin) {
        this.hpMin = hpMin;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getRcvMax() {
        return rcvMax;
    }

    public void setRcvMax(int rcvMax) {
        this.rcvMax = rcvMax;
    }

    public int getRcvMin() {
        return rcvMin;
    }

    public void setRcvMin(int rcvMin) {
        this.rcvMin = rcvMin;
    }

    public int getType1() {
        return type1;
    }

    public void setType1(int type1) {
        this.type1 = type1;
    }

    public int getType2() {
        return type2;
    }

    public void setType2(int type2) {
        this.type2 = type2;
    }

    public Color getElement1() {
        return element1;
    }

    public void setElement1(Color element1) {
        this.element1 = element1;
    }

    public Color getElement2() {
        return element2;
    }

    public void setElement2(Color element2) {
        this.element2 = element2;
    }

    public ArrayList<String> getAwokenSkills() {
        return awokenSkills;
    }

    public void setAwokenSkills(ArrayList<String> awokenSkills) {
        this.awokenSkills = awokenSkills;
    }

    public String getActiveSkill() {
        return activeSkill;
    }

    public void setActiveSkill(String activeSkill) {
        this.activeSkill = activeSkill;
    }

    public String getLeaderSkill() {
        return leaderSkill;
    }

    public void setLeaderSkill(String leaderSkill) {
        this.leaderSkill = leaderSkill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAtkScale() {
        return atkScale;
    }

    public void setAtkScale(double atkScale) {
        this.atkScale = atkScale;
    }

    public double getRcvScale() {
        return rcvScale;
    }

    public void setRcvScale(double rcvScale) {
        this.rcvScale = rcvScale;
    }

    public double getHpScale() {
        return hpScale;
    }

    public void setHpScale(double hpScale) {
        this.hpScale = hpScale;
    }

    public void setCurrentHp(double currentHp) {
        this.currentHp = currentHp;
    }

    public int getAtkPlus() {
        return atkPlus;
    }

    public void setAtkPlus(int atkPlus) {
        this.atkPlus = atkPlus;
    }

    public int getHpPlus() {
        return hpPlus;
    }

    public void setHpPlus(int hpPlus) {
        this.hpPlus = hpPlus;
    }

    public int getRcvPlus() {
        return rcvPlus;
    }

    public void setRcvPlus(int rcvPlus) {
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

    public Monster(Parcel source) {
        atkMax = source.readInt();
        atkMin = source.readInt();
        hpMax = source.readInt();
        hpMin = source.readInt();
        maxLevel = source.readInt();
        rcvMax = source.readInt();
        rcvMin = source.readInt();
        type1 = source.readInt();
        type2 = source.readInt();
        currentLevel = source.readInt();
        atkPlus = source.readInt();
        hpPlus = source.readInt();
        rcvPlus = source.readInt();
        maxAwakenings = source.readInt();
        currentAwakenings = source.readInt();
        element1 = (Color) source.readSerializable();
        element2 = (Color) source.readSerializable();
        awokenSkills = source.readArrayList(String.class.getClassLoader());
        activeSkill = source.readString();
        leaderSkill = source.readString();
        name = source.readString();
        atkScale = source.readDouble();
        rcvScale = source.readDouble();
        hpScale = source.readDouble();
        currentAtk = source.readDouble();
        currentHp = source.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(atkMax);
        dest.writeInt(atkMin);
        dest.writeInt(hpMax);
        dest.writeInt(hpMin);
        dest.writeInt(maxLevel);
        dest.writeInt(rcvMax);
        dest.writeInt(rcvMin);
        dest.writeInt(type1);
        dest.writeInt(type2);
        dest.writeInt(currentLevel);
        dest.writeInt(atkPlus);
        dest.writeInt(hpPlus);
        dest.writeInt(rcvPlus);
        dest.writeInt(maxAwakenings);
        dest.writeInt(currentAwakenings);
        dest.writeSerializable(element1);
        dest.writeSerializable(element2);
        dest.writeList(awokenSkills);
        dest.writeString(activeSkill);
        dest.writeString(leaderSkill);
        dest.writeString(name);
        dest.writeDouble(atkScale);
        dest.writeDouble(rcvScale);
        dest.writeDouble(hpScale);
        dest.writeDouble(currentAtk);
        dest.writeDouble(currentHp);
    }

    public static final Parcelable.Creator<Monster> CREATOR = new Creator<Monster>() {
        public Monster createFromParcel(Parcel source) {
            return new Monster(source);
        }

        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };
}


