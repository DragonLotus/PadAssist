package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by DragonLotus on 8/10/2015.
 */
public class Team implements Parcelable {
    private int teamHealth, teamRcv, totalDamage;
    private ArrayList<Integer> rowAwakenings, orbPlusAwakenings;
    private ArrayList<Monster> monsters;
    private ArrayList<OrbMatch> orbMatches = new ArrayList<OrbMatch>();

    public Team() {
        teamHealth = 0;
        teamRcv = 0;
    }

    public int getTeamHealth() {
        int teamHealth = 0;
        for(int i = 0; i < monsters.size(); i++){
            teamHealth += monsters.get(i).getTotalHp();
        }
        return teamHealth;
    }

    public void setTeamHealth(int teamHealth) {
        this.teamHealth = teamHealth;
    }

    public int getTeamRcv() {
        int teamRcv = 0;
        for(int i = 0; i < monsters.size(); i++){
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

    public void clearOrbMatches(){
        orbMatches.clear();
    }

    public void removeOrbMatches(int position){
        orbMatches.remove(position);
    }

    public int sizeOrbMatches(){
        return orbMatches.size();
    }

    public OrbMatch getOrbMatches(int position){
        return orbMatches.get(position);
    }

    public int sizeMonsters(){
        return monsters.size();
    }

    public Monster getMonsters(int position){
        return monsters.get(position);
    }

    public int getOrbPlusAwakenings(Color color){
        //Case Switch thing for each color. 5 elements for 5 colors. 1 red, 2 blue, 3 green, 4 light, 5 dark
        return 6;
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
