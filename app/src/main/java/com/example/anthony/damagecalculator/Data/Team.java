package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DragonLotus on 8/10/2015.
 */
@Table(name = "Team")
public class Team extends Model implements Parcelable {
    @Column(name = "teamId", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private int teamId;
    private int teamHealth;
    private int teamRcv;
    private int totalDamage;
    private ArrayList<Integer> rowAwakenings= new ArrayList<Integer>();
    private ArrayList<Integer> orbPlusAwakenings = new ArrayList<Integer>();
    @Column(name = "monsters")
    private ArrayList<Monster> monsters;
    private ArrayList<OrbMatch> orbMatches;
    @Column(name = "teamName")
    private String teamName;
    @Column(name = "teamGroup")
    private int teamGroup;
    //Position in Team Group
    @Column(name = "teamOrder")
    private int teamOrder;
    @Column(name = "favorite")
    private Boolean favorite;
    private Boolean hasAwakenings;

    public Team() {
        teamHealth = 0;
        teamRcv = 0;
        orbMatches = new ArrayList<OrbMatch>();
        hasAwakenings = true;
        favorite = false;
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

    public ArrayList<OrbMatch> getOrbMatches() {
        return orbMatches;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getTeamGroup() {
        return teamGroup;
    }

    public void setTeamGroup(int teamGroup) {
        this.teamGroup = teamGroup;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamOrder() {
        return teamOrder;
    }

    public void setTeamOrder(int teamOrder) {
        this.teamOrder = teamOrder;
    }

    public void saveMonsters(){
        for (int i = 0; i < monsters.size(); i++){
            monsters.get(i).save();
        }
    }
    public int getOrbPlusAwakenings(Element element) {
        if (element.equals(Element.RED)){
            return orbPlusAwakenings.get(0);
        } else if (element.equals(Element.BLUE)){
            return orbPlusAwakenings.get(1);
        } else if (element.equals(Element.GREEN)){
            return orbPlusAwakenings.get(2);
        } else if (element.equals(Element.LIGHT)){
            return orbPlusAwakenings.get(3);
        } else if (element.equals(Element.DARK)){
            return orbPlusAwakenings.get(4);
        }else return 0;
    }

    public int getRowAwakenings(Element element) {
        if (element.equals(Element.RED)){
            return rowAwakenings.get(0);
        } else if (element.equals(Element.BLUE)){
            return rowAwakenings.get(1);
        } else if (element.equals(Element.GREEN)){
            return rowAwakenings.get(2);
        } else if (element.equals(Element.LIGHT)){
            return rowAwakenings.get(3);
        } else if (element.equals(Element.DARK)){
            return rowAwakenings.get(4);
        }else return 0;
    }

    public Boolean hasAwakenings() {
        return hasAwakenings;
    }

    public void setHasAwakenings(Boolean hasAwakenings) {
        this.hasAwakenings = hasAwakenings;
    }

    public void update() {
        //Case Switch thing for each color. 5 elements for 5 colors. 0 red, 1 blue, 2 green, 3 light, 4 dark
        //Check for monster bound
        orbPlusAwakenings.clear();
        rowAwakenings.clear();
        for(int i = 0; i < 5; i++){
            orbPlusAwakenings.add(0);
            rowAwakenings.add(0);
        }
        for (int i = 0; i < monsters.size(); i++) {
            if (!monsters.get(i).isBound() && hasAwakenings) {
                for (int j = 0; j < monsters.get(i).getCurrentAwakenings(); j++){
                    int awokenSkill = monsters.get(i).getAwokenSkils(j);
                    switch (awokenSkill){
                        case 14:
                            orbPlusAwakenings.set(0, orbPlusAwakenings.get(0) + 1);
                            break;
                        case 15:
                            orbPlusAwakenings.set(1, orbPlusAwakenings.get(1) + 1);
                            break;
                        case 16:
                            orbPlusAwakenings.set(2, orbPlusAwakenings.get(2) + 1);
                            break;
                        case 17:
                            orbPlusAwakenings.set(3, orbPlusAwakenings.get(3) + 1);
                            break;
                        case 18:
                            orbPlusAwakenings.set(4, orbPlusAwakenings.get(4) + 1);
                            break;
                        case 22:
                            rowAwakenings.set(0, rowAwakenings.get(0) + 1);
                            break;
                        case 23:
                            rowAwakenings.set(1, rowAwakenings.get(1) + 1);
                            break;
                        case 24:
                            rowAwakenings.set(2, rowAwakenings.get(2) + 1);
                            break;
                        case 25:
                            rowAwakenings.set(3, rowAwakenings.get(3) + 1);
                            break;
                        case 26:
                            rowAwakenings.set(4, rowAwakenings.get(4) + 1);
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
        teamId = source.readInt();
        teamName = source.readString();
        teamGroup = source.readInt();
        teamOrder = source.readInt();
        favorite = source.readByte() == 1;
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
        dest.writeInt(teamId);
        dest.writeString(teamName);
        dest.writeInt(teamGroup);
        dest.writeInt(teamOrder);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }

    public static final Parcelable.Creator<Team> CREATOR = new Creator<Team>() {
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public static List<Team> getAllTeams() {
        return new Select().from(Team.class).execute();
    }

    public static void deleteAllTeams() {
        new Delete().from(Team.class).execute();
    }

//    public static List<Team> getTeamId(int id){
//        return new Select().from(Team.class).where("teamId = ?", id).execute();
//    }
//
//    public static List<Team> getTeamGroup(int teamGroup){
//        return new Select().from(Team.class).where("teamGroup = ?", teamGroup).execute();
//    }
//
//    public static List<Team> getOrder(int teamOrder){
//        return new Select().from(Team.class).where("teamOrder = ?", teamOrder).execute();
//    }
//
//    public static List<Team> getFavorite(boolean favorite){
//        return new Select().from(Team.class).where("favorite = ?", favorite).execute();
//    }



}
