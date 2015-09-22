package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    private long teamId;
    private int teamHealth;
    private int teamRcv;
    private int totalDamage;
    private ArrayList<Integer> rowAwakenings= new ArrayList<Integer>();
    private ArrayList<Integer> orbPlusAwakenings = new ArrayList<Integer>();
    private ArrayList<Monster> monsters;
    private ArrayList<Long> baseMonsterId = new ArrayList<Long>();
    @Column(name = "lead", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster lead;
    @Column(name = "sub1", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster sub1;
    @Column(name = "sub2", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster sub2;
    @Column(name = "sub3", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster sub3;
    @Column(name = "sub4", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster sub4;
    @Column(name = "helper", onDelete = Column.ForeignKeyAction.NO_ACTION, onUpdate = Column.ForeignKeyAction.CASCADE, onUniqueConflict = Column.ConflictAction.REPLACE, index = true)
    private Monster helper;
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
    private Boolean isActiveSkillUsed;
    @Column(name = "teamIdOverwrite")
    private long teamIdOverwrite;
    @Column(name = "monsterOverwrite")
    private int monsterOverwrite;

    public boolean update;

    public Team() {
        teamId = 0;
        teamIdOverwrite = 0;
        teamHealth = 0;
        teamRcv = 0;
        orbMatches = new ArrayList<OrbMatch>();
        hasAwakenings = true;
        favorite = false;
        isActiveSkillUsed = false;
        teamName = "Default team";
        monsterOverwrite = 0;
    }


    public Team(Team oldTeam) {
        teamId = oldTeam.getTeamId();
        teamIdOverwrite = oldTeam.getTeamIdOverwrite();
        lead = oldTeam.getLead();
        sub1 = oldTeam.getSub1();
        sub2 = oldTeam.getSub2();
        sub3 = oldTeam.getSub3();
        sub4 = oldTeam.getSub4();
        helper = oldTeam.getHelper();
        teamName = oldTeam.getTeamName();
        teamGroup = oldTeam.getTeamGroup();
        teamOrder = oldTeam.getTeamOrder();
        favorite = oldTeam.favorite;
        monsterOverwrite = oldTeam.getMonsterOverwrite();
    }

    public int getTeamHealth() {
        int teamHealth = 0;
        for (int i = 0; i < getMonsters().size(); i++) {
            teamHealth += getMonsters().get(i).getTotalHp();
        }
        return teamHealth;
    }

    public void setTeamHealth(int teamHealth) {
        this.teamHealth = teamHealth;
    }

    public int getTeamRcv() {
        int teamRcv = 0;
        for (int i = 0; i < getMonsters().size(); i++) {
            teamRcv += getMonsters().get(i).getTotalRcv();
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
        if(monsters == null || monsters.contains(null)) {
            monsters = new ArrayList<>();
            monsters.add(lead);
            monsters.add(sub1);
            monsters.add(sub2);
            monsters.add(sub3);
            monsters.add(sub4);
            monsters.add(helper);
        }
        return monsters;
    }

    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public ArrayList<Long> getBaseMonsterId() {
        baseMonsterId.clear();
        for(int i = 0; i < getMonsters().size(); i++){
            baseMonsterId.add(getMonsters(i).getBaseMonsterId());
        }
        return baseMonsterId;
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
        return getMonsters().get(position);
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public long getTeamIdOverwrite() {
        return teamIdOverwrite;
    }

    public void setTeamIdOverwrite(long teamIdOverwrite) {
        this.teamIdOverwrite = teamIdOverwrite;
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

    public void setMonsters(Monster monster1, Monster monster2, Monster monster3, Monster monster4, Monster monster5, Monster monster6){
        setLead(monster1);
        setSub1(monster2);
        setSub2(monster3);
        setSub3(monster4);
        setSub4(monster5);
        setHelper(monster6);
    }

    public Monster getSub1() {
        return sub1;
    }

    public void setSub1(Monster sub1) {
        this.sub1 = sub1;
    }

    public Monster getSub2() {
        return sub2;
    }

    public void setSub2(Monster sub2) {
        this.sub2 = sub2;
    }

    public Monster getSub3() {
        return sub3;
    }

    public void setSub3(Monster sub3) {
        this.sub3 = sub3;
    }

    public Monster getSub4() {
        return sub4;
    }

    public void setSub4(Monster sub4) {
        this.sub4 = sub4;
    }

    public Monster getLead() {
        return lead;
    }

    public void setLead(Monster lead) {
        this.lead = lead;
    }

    public Monster getHelper() {
        return helper;
    }

    public void setHelper(Monster helper) {
        this.helper = helper;
    }

    public int getMonsterOverwrite() {
        return monsterOverwrite;
    }

    public void setMonsterOverwrite(int monsterOverwrite) {
        this.monsterOverwrite = monsterOverwrite;
    }

    public String getLeadSkill1(){
        return lead.getLeaderSkill();
    }

    public String getLeadSkill2(){
        return helper.getLeaderSkill();
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

    public Boolean isActiveSkillUsed() {
        return isActiveSkillUsed;
    }

    public void isActiveSkillUsed(Boolean isActiveSkillUsed) {
        this.isActiveSkillUsed = isActiveSkillUsed;
    }

    public void update() {
        //Case Switch thing for each color. 5 elements for 5 colors. 0 red, 1 blue, 2 green, 3 light, 4 dark
        //Check for monster bound
        for(int i = 0; i < getMonsters().size(); i++) {
            Log.d("What are monsters", "Monsters: " + getMonsters().get(i) + " " + monsters.get(i));
        }
        orbPlusAwakenings.clear();
        rowAwakenings.clear();
        for(int i = 0; i < 5; i++){
            orbPlusAwakenings.add(0);
            rowAwakenings.add(0);
        }
        for (int i = 0; i < getMonsters().size(); i++) {
            if (!getMonsters().get(i).isBound() && hasAwakenings) {
                Log.d("Awakening count", "Size of awoken skills" + getMonsters(i).getAwokenSkills().size());
                //This isn't necessary anymore
                if(getMonsters().get(i).getAwokenSkills().size() != 0){
                    for (int j = 0; j < getMonsters().get(i).getCurrentAwakenings(); j++){
                        Log.d("Awakening List", "Monster: " + getMonsters(i) + " List: " + getMonsters(i).getAwokenSkills());
                        int awokenSkill = getMonsters().get(i).getAwokenSkills(j);
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
    }

    public Team(Parcel source) {
        teamHealth = source.readInt();
        teamRcv = source.readInt();
        totalDamage = source.readInt();
        rowAwakenings = source.readArrayList(Integer.class.getClassLoader());
        monsters = source.readArrayList(Monster.class.getClassLoader());
        orbMatches = source.readArrayList(OrbMatch.class.getClassLoader());
        teamId = source.readLong();
        teamName = source.readString();
        teamGroup = source.readInt();
        teamOrder = source.readInt();
        favorite = source.readByte() == 1;
        teamIdOverwrite = source.readLong();
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
        dest.writeLong(teamId);
        dest.writeString(teamName);
        dest.writeInt(teamGroup);
        dest.writeInt(teamOrder);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeLong(teamIdOverwrite);
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
        return new Select().from(Team.class).where("teamId > ?", 0).execute();
    }


    public static Team getTeamById(long id) {
        return new Select().from(Team.class).where("teamId = ?", id).executeSingle();
    }

    public static void deleteAllTeams() {
        new Delete().from(Team.class).execute();
    }

    public static void deleteTeam(int id){
        new Delete().from(Team.class).where("teamId = ?", id).executeSingle();
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
