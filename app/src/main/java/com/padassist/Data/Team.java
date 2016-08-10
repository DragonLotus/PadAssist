package com.padassist.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.padassist.Util.DamageCalculationUtil;
import com.padassist.Util.NumberComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DragonLotus on 8/10/2015.
 */

public class Team extends RealmObject implements Parcelable {
    @PrimaryKey
    private long teamId;
    private int teamHealth;
    private int teamRcv;
    private int totalDamage;
    @Ignore
    private ArrayList<Integer> rowAwakenings = new ArrayList<Integer>();
    @Ignore
    private ArrayList<Integer> orbPlusAwakenings = new ArrayList<Integer>();
    @Ignore
    private ArrayList<Monster> monsters;
    @Ignore
    private ArrayList<Long> baseMonsterId = new ArrayList<Long>();

    private Monster lead;

    private Monster sub1;

    private Monster sub2;

    private Monster sub3;

    private Monster sub4;

    private Monster helper;
    @Ignore
    private ArrayList<OrbMatch> orbMatches;

    private String teamName;

    private int teamGroup;
    //Position in Team Group

    private int teamOrder;

    private boolean favorite;
    private boolean hasAwakenings;
    private boolean isActiveSkillUsed;

    private long teamIdOverwrite;
    @Ignore
    private ArrayList<Boolean> isBound = new ArrayList<>();

    private int teamHp;
    @Ignore
    private ArrayList<Element> compareElements = new ArrayList<>();
    @Ignore
    private ArrayList<Element> haveElements = new ArrayList();
    @Ignore
    private ArrayList<Element> compareAllElements = new ArrayList<>();
    @Ignore
    private ArrayList<Integer> awakeningsList = new ArrayList<>();
    @Ignore
    private ArrayList<Integer> latentsList = new ArrayList<>();
    @Ignore
    private Comparator<Integer> numberComparator = new NumberComparator();
    @Ignore
    private Realm realm = Realm.getDefaultInstance();

    public Team() {
        teamId = 0;
        teamIdOverwrite = 0;
        teamHealth = 0;
        teamRcv = 0;
        orbMatches = new ArrayList<OrbMatch>();
        hasAwakenings = true;
        favorite = false;
        isActiveSkillUsed = false;
        teamName = "Untitled Team";
        teamHp = 100;
        isBound.clear();
        for (int i = 0; i < 6; i++) {
            isBound.add(false);
        }
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
    }

    public int getTeamHealth() {
        return teamHealth;
    }

    public void setTeamHealth(int teamHealth) {
        this.teamHealth = teamHealth;
    }

    public int getTeamRcv() {
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
        if (monsters == null || monsters.contains(null)) {
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
        for (int i = 0; i < getMonsters().size(); i++) {
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

//    public ArrayList<OrbMatch> getOrbMatches() {
//        if (!orbMatches.equals((ArrayList) OrbMatch.getAllOrbMatches())) {
//            orbMatches = (ArrayList) OrbMatch.getAllOrbMatches();
//        }
//
//        return orbMatches;
//
//    }


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

    public void setMonsters(Monster monster1, Monster monster2, Monster monster3, Monster monster4, Monster monster5, Monster monster6) {
        setLead(monster1);
        setSub1(monster2);
        setSub2(monster3);
        setSub3(monster4);
        setSub4(monster5);
        setHelper(monster6);
    }

    public void setMonsters(int position, Monster monster) {
        if(monsters == null){
            monsters = new ArrayList<>();
        }
        monsters.set(position, monster);
        switch (position) {
            case 0:
                setLead(monster);
                break;
            case 1:
                setSub1(monster);
                break;
            case 2:
                setSub2(monster);
                break;
            case 3:
                setSub3(monster);
                break;
            case 4:
                setSub4(monster);
                break;
            case 5:
                setHelper(monster);
                break;
        }
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

    public ArrayList<Boolean> getIsBound() {
        return isBound;
    }

    public void setIsBound(ArrayList<Boolean> isBound) {
        this.isBound = isBound;
    }

    public LeaderSkill getLeadSkill() {
        if (isBound.get(0) || monsters.get(0).getMonsterId() == 0) {
            return realm.where(LeaderSkill.class).equalTo("name", "Blank").findFirst();
        } else {
            return realm.where(LeaderSkill.class).equalTo("name", lead.getLeaderSkill()).findFirst();
        }
    }

    public LeaderSkill getHelperSkill() {
        if (isBound.get(5) || monsters.get(5).getMonsterId() == 0) {
            return realm.where(LeaderSkill.class).equalTo("name", "Blank").findFirst();
        } else {
            return realm.where(LeaderSkill.class).equalTo("name", helper.getLeaderSkill()).findFirst();
        }
    }

    public int getTeamHp() {
        return teamHp;
    }

    public void setTeamHp(int teamHp) {
        this.teamHp = teamHp;
    }

    public ArrayList<Element> getOrbMatchElements() {
        return compareElements;
    }

//    public ArrayList<Element> getAllOrbMatchElements() {
//        if (compareAllElements.size() != 0) {
//            compareAllElements.clear();
//        }
//        for (int i = 0; i < getOrbMatches().size(); i++) {
//            compareAllElements.add(getOrbMatches().get(i).getElement());
//        }
//        return compareAllElements;
//    }

    public int getOrbPlusAwakenings(Element element) {
        if (element.equals(Element.RED)) {
            return orbPlusAwakenings.get(0);
        } else if (element.equals(Element.BLUE)) {
            return orbPlusAwakenings.get(1);
        } else if (element.equals(Element.GREEN)) {
            return orbPlusAwakenings.get(2);
        } else if (element.equals(Element.LIGHT)) {
            return orbPlusAwakenings.get(3);
        } else if (element.equals(Element.DARK)) {
            return orbPlusAwakenings.get(4);
        } else return 0;
    }

    public int getRowAwakenings(Element element) {
        if (element.equals(Element.RED)) {
            return rowAwakenings.get(0);
        } else if (element.equals(Element.BLUE)) {
            return rowAwakenings.get(1);
        } else if (element.equals(Element.GREEN)) {
            return rowAwakenings.get(2);
        } else if (element.equals(Element.LIGHT)) {
            return rowAwakenings.get(3);
        } else if (element.equals(Element.DARK)) {
            return rowAwakenings.get(4);
        } else return 0;
    }

    public boolean hasAwakenings() {
        return hasAwakenings;
    }

    public void setHasAwakenings(boolean hasAwakenings) {
        this.hasAwakenings = hasAwakenings;
    }

    public boolean isActiveSkillUsed() {
        return isActiveSkillUsed;
    }

    public void isActiveSkillUsed(boolean isActiveSkillUsed) {
        this.isActiveSkillUsed = isActiveSkillUsed;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public ArrayList<Integer> getAwakenings(){
        if(awakeningsList.size() == 0){
            for (int i = 0; i < monsters.size(); i++){
                if(monsters.get(i).getCurrentAwakenings() < monsters.get(i).getMaxAwakenings()){
                    for(int j = 0; j < monsters.get(i).getCurrentAwakenings(); j++){
                        awakeningsList.add(monsters.get(i).getAwokenSkills(j));
                    }
                } else {
                    for(int j = 0; j < monsters.get(i).getMaxAwakenings(); j++){
                        awakeningsList.add(monsters.get(i).getAwokenSkills(j));
                    }
                }
            }
            Collections.sort(awakeningsList, numberComparator);
        }
        return awakeningsList;
    }
    public ArrayList<Integer> getLatents(){
        if(latentsList.size() == 0){
            for (int i = 0; i < monsters.size(); i++){
                for(int j = 0; j < monsters.get(i).getLatents().size(); j++){
                    if(monsters.get(i).getLatents().get(j).getValue() != 0){
                        latentsList.add(monsters.get(i).getLatents().get(j).getValue());
                    }
                }
            }
            Collections.sort(latentsList, numberComparator);
        }
        return latentsList;
    }

    public void updateAwakenings() {
        //Case Switch thing for each color. 5 elements for 5 colors. 0 red, 1 blue, 2 green, 3 light, 4 dark
        //Check for monster bound
//        compareAllElements.clear();
        awakeningsList.clear();
        latentsList.clear();
        orbPlusAwakenings.clear();
        rowAwakenings.clear();
        for (int i = 0; i < 5; i++) {
            orbPlusAwakenings.add(0);
            rowAwakenings.add(0);
        }
        orbPlusAwakenings.add(0);
        for (int i = 0; i < getMonsters().size(); i++) {
            if (!isBound.get(i) && hasAwakenings) {
                //This isn't necessary anymore
                if (getMonsters().get(i).getAwokenSkills().size() != 0) {
                    if(getMonsters().get(i).getCurrentAwakenings()<getMonsters(i).getMaxAwakenings()){
                        for (int j = 0; j < getMonsters().get(i).getCurrentAwakenings(); j++) {
                            int awokenSkill = getMonsters().get(i).getAwokenSkills(j);
                            switch (awokenSkill) {
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
                                case 29:
                                    orbPlusAwakenings.set(5, orbPlusAwakenings.get(5) + 1);
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
                    } else {
                        for (int j = 0; j < getMonsters().get(i).getMaxAwakenings(); j++) {
                            int awokenSkill = getMonsters().get(i).getAwokenSkills(j);
                            switch (awokenSkill) {
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
                                case 29:
                                    orbPlusAwakenings.set(5, orbPlusAwakenings.get(5) + 1);
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
    }

//    public void updateOrbs() {
//        haveElements.clear();
//        compareElements.clear();
//        for (int i = 0; i < getMonsters().size(); i++) {
//            if (!isBound.get(i)) {
//                if (!haveElements.contains(getMonsters().get(i).getElement1())) {
//                    haveElements.add(getMonsters().get(i).getElement1());
//                }
//                if (!haveElements.contains(getMonsters().get(i).getElement2())) {
//                    haveElements.add(getMonsters().get(i).getElement2());
//                }
//            }
//
//        }
//        for (int i = 0; i < getOrbMatches().size(); i++) {
//            if (haveElements.contains(getOrbMatches().get(i).getElement())) {
//                compareElements.add(getOrbMatches().get(i).getElement());
//            }
//            if (getOrbMatches().get(i).getElement().equals(Element.HEART) && !compareElements.contains(Element.HEART)) {
//                compareElements.add(Element.HEART);
//            }
//        }
//    }

    public void setTeamStats(){
        int hp = 0;
        double rcv = 0;
        for(int i = 0; i < getMonsters().size(); i++) {
            hp += DamageCalculationUtil.monsterHpCalc(getMonsters(i), this);
            rcv += DamageCalculationUtil.monsterRcvCalc(getMonsters(i), this);
        }
        this.teamHealth = hp;
        this.teamRcv = (int)Math.floor(rcv + 0.5d);
    }

//    public void updateLeaderSkills() {
//        if(LeaderSkill.getLeaderSkill(lead.getLeaderSkill()) == null){
//            leadSkill = LeaderSkill.getLeaderSkill("Blank");
//        } else {
//            leadSkill = LeaderSkill.getLeaderSkill(lead.getLeaderSkill());
//        }
//        if(LeaderSkill.getLeaderSkill(helper.getLeaderSkill()) == null){
//            helperSkill = LeaderSkill.getLeaderSkill("Blank");
//        } else {
//            helperSkill = LeaderSkill.getLeaderSkill(helper.getLeaderSkill());
//        }
//    }

    public Team(Parcel source) {
        teamHealth = source.readInt();
        teamRcv = source.readInt();
        totalDamage = source.readInt();
        rowAwakenings = source.readArrayList(Integer.class.getClassLoader());
        monsters = source.readArrayList(Monster.class.getClassLoader());
        orbMatches = source.readArrayList(OrbMatch.class.getClassLoader());
        haveElements = source.readArrayList(Element.class.getClassLoader());
        compareElements = source.readArrayList(Element.class.getClassLoader());
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
        dest.writeList(haveElements);
        dest.writeList(compareElements);
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

//    public static List<Team> getAllTeams() {
//        return new Select().from(Team.class).where("teamId > ?", 0).execute();
//    }
//
//    public static List<Team> getAllTeamsAndZero() {
//        return new Select().from(Team.class).execute();
//    }
//
//    public static Team getTeamById(long id) {
//        return new Select().from(Team.class).where("teamId = ?", id).executeSingle();
//    }
//
//    public static void deleteAllTeams() {
//        new Delete().from(Team.class).execute();
//    }
//
//    public static void deleteTeam(int id) {
//        new Delete().from(Team.class).where("teamId = ?", id).executeSingle();
//    }


}
