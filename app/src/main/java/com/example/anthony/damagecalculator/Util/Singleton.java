package com.example.anthony.damagecalculator.Util;

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
}
