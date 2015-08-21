package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.example.anthony.damagecalculator.Util.DamageCalculationUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anthony on 7/13/2015.
 */
@Table(name = "Monster")
public class Monster extends Model implements Parcelable {
    public static final int HP_MULTIPLIER = 10;
    public static final int ATK_MULTIPLIER = 5;
    public static final int RCV_MULTIPLIER = 3;
    @Column(name = "monsterId", unique = true, index = true, onUniqueConflict = Column.ConflictAction.IGNORE, onUpdate = Column.ForeignKeyAction.NO_ACTION, onDelete = Column.ForeignKeyAction.NO_ACTION)
    private int monsterId;
    @Column(name = "atkMax")
    private int atkMax;
    @Column(name = "atkMin")
    private int atkMin;
    @Column(name = "hpMax")
    private int hpMax;
    @Column(name = "hpMin")
    private int hpMin;
    @Column(name = "maxLevel")
    private int maxLevel;
    @Column(name = "rcvMin")
    private int rcvMin;
    @Column(name = "rcvMax")
    private int rcvMax;
    @Column(name = "type1")
    private int type1;
    @Column(name = "type2")
    private int type2;
    @Column(name = "currentLevel")
    private int currentLevel;
    @Column(name = "atkPlus")
    private int atkPlus;
    @Column(name = "hpPlus")
    private int hpPlus;
    @Column(name = "rcvPlus")
    private int rcvPlus;
    @Column(name = "maxAwakenings")
    private int maxAwakenings;
    @Column(name = "currentAwakenings")
    private int currentAwakenings;
    @Column(name = "element1")
    private Element element1;
    @Column(name = "element2")
    private Element element2;
    @Column(name = "awokenSkills")
    private ArrayList<Integer> awokenSkills = new ArrayList<Integer>();
    @Column(name = "activeSkill")
    private String activeSkill;
    @Column(name = "leaderSkill")
    private String leaderSkill;
    @Column(name = "name")
    private String name;
    @Column(name = "atkScale")
    private double atkScale;
    @Column(name = "rcvScale")
    private double rcvScale;
    @Column(name = "hpScale")
    private double hpScale;
    @Column(name = "currentAtk")
    private double currentAtk;
    @Column(name = "currentRcv")
    private double currentRcv;
    @Column(name = "currentHp")
    private double currentHp;
    @Column(name = "isBound")
    private boolean isBound;
    @Column(name = "teamIndex")
    private int teamIndex;
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
        element1 = Element.LIGHT;
        element2 = Element.LIGHT;
        isBound = false;
        name = "Kirin of the Sacred Gleam, Sakuya";
        awokenSkills.add(17);
        awokenSkills.add(12);
        awokenSkills.add(11);
        awokenSkills.add(28);
        awokenSkills.add(17);
        awokenSkills.add(21);
        awokenSkills.add(19);

    }

    public int getElement1Damage(Team team, int combos) {
        return (int) DamageCalculationUtil.monsterElement1Damage(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element1), combos);
    }

    public int getElement1DamageEnemy(Team team, Enemy enemy, int combos) {
        return (int) Math.ceil(DamageCalculationUtil.monsterElement1DamageEnemy(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element1), combos, enemy));
    }

    public int getElement2Damage(Team team, int combos) {
        return (int) DamageCalculationUtil.monsterElement2Damage(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element2), combos);
    }

    public int getElement2DamageEnemy(Team team, Enemy enemy, int combos) {
        return (int) Math.ceil(DamageCalculationUtil.monsterElement2DamageEnemy(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element2), combos, enemy));
    }

    public int getElement1DamageReduction(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement1DamageReduction(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element1), combos, enemy);
    }

    public int getElement2DamageReduction(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement2DamageReduction(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element2), combos, enemy);
    }

    public int getElement1DamageAbsorb(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement1DamageAbsorb(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element1), combos, enemy);
    }

    public int getElement2DamageAbsorb(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement2DamageAbsorb(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element2), combos, enemy);
    }

    public int getElement1DamageThreshold(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement1DamageThreshold(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element1), combos, enemy);
    }

    public int getElement2DamageThreshold(Team team, Enemy enemy, int combos) {
        return (int) DamageCalculationUtil.monsterElement2DamageThreshold(this, team.getOrbMatches(), team.getOrbPlusAwakenings(element2), combos, enemy);
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
        setCurrentHp(DamageCalculationUtil.monsterStatCalc(hpMin, hpMax, currentLevel, maxLevel, hpScale));
        setCurrentAtk(DamageCalculationUtil.monsterStatCalc(atkMin, atkMax, currentLevel, maxLevel, atkScale));
        setCurrentRcv(DamageCalculationUtil.monsterStatCalc(rcvMin, rcvMax, currentLevel, maxLevel, rcvScale));
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

    public Element getElement1() {
        return element1;
    }

    public void setElement1(Element element1) {
        this.element1 = element1;
    }

    public Element getElement2() {
        return element2;
    }

    public void setElement2(Element element2) {
        this.element2 = element2;
    }

    public ArrayList<Integer> getAwokenSkills() {
        return awokenSkills;
    }

    public int getAwokenSkils(int position) {
        return awokenSkills.get(position);
    }

    public void setAwokenSkills(ArrayList<Integer> awokenSkills) {
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

    public int getTotalPlus(){
        return hpPlus + atkPlus + rcvPlus;
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

    public boolean isBound() {
        return isBound;
    }

    public void setIsBound(boolean isBound) {
        this.isBound = isBound;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public int getTeamIndex() {
        return teamIndex;
    }

    public void setTeamIndex(int teamIndex) {
        this.teamIndex = teamIndex;
    }

    public int getTPA() {
        int numOfDoubleProngs = 0;
        for (int i = 0; i < currentAwakenings; i++) {
            if (awokenSkills.get(i) == 27) {
                numOfDoubleProngs++;
            }
        }
        return numOfDoubleProngs;
    }

    public Monster(Parcel source) {
        monsterId = source.readInt();
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
        element1 = (Element) source.readSerializable();
        element2 = (Element) source.readSerializable();
        awokenSkills = source.readArrayList(String.class.getClassLoader());
        activeSkill = source.readString();
        leaderSkill = source.readString();
        name = source.readString();
        atkScale = source.readDouble();
        rcvScale = source.readDouble();
        hpScale = source.readDouble();
        currentAtk = source.readDouble();
        currentHp = source.readDouble();
        isBound = source.readByte() == 1;
        teamIndex = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(monsterId);
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
        dest.writeByte((byte) (isBound ? 1 : 0));
        dest.writeInt(teamIndex);
    }

    public static final Parcelable.Creator<Monster> CREATOR = new Creator<Monster>() {
        public Monster createFromParcel(Parcel source) {
            return new Monster(source);
        }

        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };


    public static List<Monster> getAllMonsters() {
        return new Select().from(Monster.class).orderBy("teamIndex ASC").execute();
    }

    public static void deleteAllMonsters() {
        new Delete().from(Monster.class).execute();
    }

    public static List<Monster> getAllMonstersLevel(int level) {
        return new Select().from(Monster.class).where("currentLevel = ?", level).execute();
    }

    public static Monster getMonsterId(int id){
        return new Select().from(Monster.class).where("monsterId = ?", id).executeSingle();
    }
//
//    public static List<Monster> getMonsterAtkMax(int attack){
//        return new Select().from(Monster.class).where("atkMax = ?", attack).execute();
//    }
//
//    public static List<Monster> getMonsterAtkMin(int attack){
//        return new Select().from(Monster.class).where("atkMin = ?", attack).execute();
//    }
//
//    public static List<Monster> getMonsterHpMax(int hp){
//        return new Select().from(Monster.class).where("hpMax = ?", hp).execute();
//    }
//
//    public static List<Monster> getMonsterHpMin(int hp){
//        return new Select().from(Monster.class).where("hpMin = ?", hp).execute();
//    }
//
//    public static List<Monster> getMonsterMaxLevel(int level){
//        return new Select().from(Monster.class).where("maxLevel = ?", level).execute();
//    }
//
//    public static List<Monster> getMonsterRcvMax(int rcv){
//        return new Select().from(Monster.class).where("maxRcv = ?", rcv).execute();
//    }
//
//    public static List<Monster> getMonsterRcvMin(int rcv){
//        return new Select().from(Monster.class).where("minRcv = ?", rcv).execute();
//    }
//
//    public static List<Monster> getMonsterType1(int type){
//        return new Select().from(Monster.class).where("type1 = ?", type).execute();
//    }
//
//    public static List<Monster> getMonsterType2(int type){
//        return new Select().from(Monster.class).where("type2 = ?", type).execute();
//    }
//
//    public static List<Monster> getMonsterMaxAwakenings(int awakenings){
//        return new Select().from(Monster.class).where("maxAwakenings = ?", awakenings).execute();
//    }
//
//    public static List<Monster> getMonsterCurrentAwakenings(int awakenings){
//        return new Select().from(Monster.class).where("currentAwakenings = ?", awakenings).execute();
//    }
//
//    public static List<Monster> getMonsterElement1(Element element){
//        return new Select().from(Monster.class).where("element1 = ?", element).execute();
//    }
//
//    public static List<Monster> getMonsterElement2(Element element){
//        return new Select().from(Monster.class).where("element2 = ?", element).execute();
//    }
//
//    private static List<Monster> getMonsterCurrentHp(int hp){
//        return new Select().from(Monster.class).where("currentHp = ?", hp).execute();
//    }
//
//    private static List<Monster> getMonsterCurrentAtk(int attack){
//        return new Select().from(Monster.class).where("currentAtk = ?", attack).execute();
//    }
//
//    private static List<Monster> getMonsterCurrentRcv(int rcv){
//        return new Select().from(Monster.class).where("currentRcv = ?", rcv).execute();
//    }
}


