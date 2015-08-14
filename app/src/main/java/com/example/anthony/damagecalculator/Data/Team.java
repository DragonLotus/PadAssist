package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by DragonLotus on 8/10/2015.
 */
@Table(name = "Categories")
public class Team implements Parcelable {

    @Column(name = "teamId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int teamId;
    private int teamHealth;
    private int teamRcv;
    private int totalDamage;
    @Column(name = "rowAwakenings")
    private ArrayList<Integer> rowAwakenings= new ArrayList<Integer>();
    @Column(name = "orbPlusAwakenings")
    private ArrayList<Integer> orbPlusAwakenings = new ArrayList<Integer>();
    @Column(name = "monsters")
    private ArrayList<Monster> monsters;
    private ArrayList<OrbMatch> orbMatches;

    public Team() {
        teamHealth = 0;
        teamRcv = 0;
        orbMatches = new ArrayList<OrbMatch>();
    }

    public int getTeamHealth() {
        int teamHealth = 0;
        for (int i = 0; i < monsters.size(); i++) {
            teamHealth += monsters.get(i).getTotalHp();
        }
        return teamHealth;
    }

    public void setTeamHealth(int teamHealth) {
        this.teamHealth = teamHealth;
    }

    public int getTeamRcv() {
        int teamRcv = 0;
        for (int i = 0; i < monsters.size(); i++) {
            teamRcv += monsters.get(i).getTotalRcv();
        }
        return teamRcv;
    }

    public void setTeamRcv(int teamRcv) {
        this.teamRcv = teamRcv;
    }

    public ArrayList<Integer> getRowAwakenings() {
        return rowAwakenings;
    }

    public void setRowAwakenings(ArrayList<Integer> rowAwakenings) {
        this.rowAwakenings = rowAwakenings;
    }

    public ArrayList<Integer> getOrbPlusAwakenings() {
        return orbPlusAwakenings;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public int getTotalDamage() {
        return totalDamage;
    }

    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    public void setOrbPlusAwakenings(ArrayList<Integer> orbPlusAwakenings) {
        this.orbPlusAwakenings = orbPlusAwakenings;
    }

    public ArrayList<OrbMatch> getOrbMatches() {
        return orbMatches;
    }

    public void setOrbMatches(ArrayList<OrbMatch> orbMatches) {
        this.orbMatches = orbMatches;
    }

    public void addOrbMatches(OrbMatch orbMatch) {
        orbMatches.add(orbMatch);
    }

    public void clearOrbMatches() {
        orbMatches.clear();
    }

    public void removeOrbMatches(int position) {
        orbMatches.remove(position);
    }

    public int sizeOrbMatches() {
        return orbMatches.size();
    }

    public OrbMatch getOrbMatches(int position) {
        return orbMatches.get(position);
    }

    public int sizeMonsters() {
        return monsters.size();
    }

    public Monster getMonsters(int position) {
        return monsters.get(position);
    }

    public int getOrbPlusAwakenings(Color color) {
        if (color.equals(Color.RED)){
            return orbPlusAwakenings.get(0);
        } else if (color.equals(Color.BLUE)){
            return orbPlusAwakenings.get(1);
        } else if (color.equals(Color.GREEN)){
            return orbPlusAwakenings.get(2);
        } else if (color.equals(Color.LIGHT)){
            return orbPlusAwakenings.get(3);
        } else if (color.equals(Color.DARK)){
            return orbPlusAwakenings.get(4);
        }else return 0;
    }

    public int getRowAwakenings(Color color) {
        if (color.equals(Color.RED)){
            return rowAwakenings.get(0);
        } else if (color.equals(Color.BLUE)){
            return rowAwakenings.get(1);
        } else if (color.equals(Color.GREEN)){
            return rowAwakenings.get(2);
        } else if (color.equals(Color.LIGHT)){
            return rowAwakenings.get(3);
        } else if (color.equals(Color.DARK)){
            return rowAwakenings.get(4);
        }else return 0;
    }

    public void update() {
        //Case Switch thing for each color. 5 elements for 5 colors. 0 red, 1 blue, 2 green, 3 light, 4 dark
        //Check for monster bound
        orbPlusAwakenings.clear();
        rowAwakenings.clear();
        for (int i = 0; i < monsters.size(); i++) {
            if (!monsters.get(i).isBound()) {
                for (int j = 0; j < monsters.get(i).getCurrentAwakenings(); j++){
                    int awokenSkill = monsters.get(i).getAwokenSkils(j);
                    switch (awokenSkill){
                        case 14:
                            orbPlusAwakenings.add(0, 1);
                            break;
                        case 15:
                            orbPlusAwakenings.add(1, 1);
                            break;
                        case 16:
                            orbPlusAwakenings.add(2, 1);
                            break;
                        case 17:
                            orbPlusAwakenings.add(3, 1);
                            break;
                        case 18:
                            orbPlusAwakenings.add(4, 1);
                            break;
                        case 22:
                            rowAwakenings.add(0, 1);
                            break;
                        case 23:
                            rowAwakenings.add(1, 1);
                            break;
                        case 24:
                            rowAwakenings.add(2, 1);
                            break;
                        case 25:
                            rowAwakenings.add(3, 1);
                            break;
                        case 26:
                            rowAwakenings.add(4, 1);
                            break;
                    }
                }
            }
        }
    }

    public Team(Parcel source) {
        teamHealth = source.readInt();
        teamRcv = source.readInt();
        totalDamage = source.readInt();
        rowAwakenings = source.readArrayList(Integer.class.getClassLoader());
        monsters = source.readArrayList(Monster.class.getClassLoader());
        orbMatches = source.readArrayList(OrbMatch.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamHealth);
        dest.writeInt(teamRcv);
        dest.writeInt(totalDamage);
        dest.writeList(rowAwakenings);
        dest.writeList(monsters);
        dest.writeList(orbMatches);
    }

    public static final Parcelable.Creator<Team> CREATOR = new Creator<Team>() {
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
