package com.padassist.Util;

import android.content.Context;

import com.padassist.Data.Element;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 10/17/2015.
 */
public class Singleton {
    private static Singleton instance;
    private int saveSortMethod = 8;
    private int baseSortMethod = 1;
    private int teamSortMethod = 8;
    private int monsterOverwrite = 0;
    private boolean ignoreEnemy = false;
    private int boardSize = 1;
    private ArrayList<Element> extraElementMultiplier = new ArrayList<>();
    private ArrayList<Integer> extraTypeMultiplier = new ArrayList<>();
    private double extraDamageMultiplier = 2;
    private boolean enableMultiplier = false;
    private boolean coopEnable = false;
    private boolean hasAwakenings = true;
    private boolean activeSkillUsed = false;

    private Context _context;
    public static Singleton getInstance() {
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {
    }

    public int getSaveSortMethod() {
        return saveSortMethod;
    }

    public void setSaveSortMethod(int saveSortMethod) {
        this.saveSortMethod = saveSortMethod;
    }

    public int getBaseSortMethod() {
        return baseSortMethod;
    }

    public void setBaseSortMethod(int baseSortMethod) {
        this.baseSortMethod = baseSortMethod;
    }

    public int getTeamSortMethod() {
        return teamSortMethod;
    }

    public void setTeamSortMethod(int teamSortMethod) {
        this.teamSortMethod = teamSortMethod;
    }

    public boolean isIgnoreEnemy() {
        return ignoreEnemy;
    }

    public void setIgnoreEnemy(boolean ignoreEnemy) {
        this.ignoreEnemy = ignoreEnemy;
    }

    public int getMonsterOverwrite() {
        return monsterOverwrite;
    }

    public void setMonsterOverwrite(int monsterOverwrite) {
        this.monsterOverwrite = monsterOverwrite;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Context getContext()
    {
        return _context;
    }

    public void setContext(Context context)
    {
        this._context = context;
    }

    public ArrayList<Element> getExtraElementMultiplier() {
        return extraElementMultiplier;
    }

    public void setExtraElementMultiplier(ArrayList<Element> extraElementMultiplier) {
        this.extraElementMultiplier = extraElementMultiplier;
    }

    public ArrayList<Integer> getExtraTypeMultiplier() {
        return extraTypeMultiplier;
    }

    public void setExtraTypeMultiplier(ArrayList<Integer> extraTypeMultiplier) {
        this.extraTypeMultiplier = extraTypeMultiplier;
    }

    public double getExtraDamageMultiplier() {
        return extraDamageMultiplier;
    }

    public void setExtraDamageMultiplier(double extraDamageMultiplier) {
        this.extraDamageMultiplier = extraDamageMultiplier;
    }

    public boolean isEnableMultiplier() {
        return enableMultiplier;
    }

    public void setEnableMultiplier(boolean enableMultiplier) {
        this.enableMultiplier = enableMultiplier;
    }

    public boolean isCoopEnable() {
        return coopEnable;
    }

    public void setCoopEnable(boolean coopEnable) {
        this.coopEnable = coopEnable;
    }

    public boolean isActiveSkillUsed() {
        return activeSkillUsed;
    }

    public void setActiveSkillUsed(boolean activeSkillUsed) {
        this.activeSkillUsed = activeSkillUsed;
    }

    public boolean hasAwakenings() {
        return hasAwakenings;
    }

    public void setHasAwakenings(boolean hasAwakenings) {
        this.hasAwakenings = hasAwakenings;
    }
}
