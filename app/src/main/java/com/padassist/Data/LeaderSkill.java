package com.padassist.Data;

import com.padassist.ParcelConverters.RealmDoubleParcelConverter;
import com.padassist.ParcelConverters.RealmElementListParcelConverter;
import com.padassist.ParcelConverters.RealmIntParcelConverter;
import com.padassist.ParcelConverters.RealmLongParcelConverter;

import org.parceler.ParcelPropertyConverter;

import io.realm.LeaderSkillRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DragonLotus on 9/18/2015.
 */
@org.parceler.Parcel(implementations = {LeaderSkillRealmProxy.class},
        value = org.parceler.Parcel.Serialization.BEAN,
        analyze = {LeaderSkill.class})
public class LeaderSkill extends RealmObject {

    private RealmList<RealmDouble> hpData;

    private RealmList<RealmDouble> atkData;

    private RealmList<RealmDouble> rcvData;
    @PrimaryKey
    private String name;

    private String description;

    private RealmList<RealmInt> hpType;

    private RealmList<RealmInt> hpElement;

    private RealmList<RealmInt> atkType;

    private RealmList<RealmInt> atkType2;

    private RealmList<RealmInt> atkElement;

    private RealmList<RealmInt> atkElement2;

    private RealmList<RealmInt> rcvType;

    private RealmList<RealmInt> rcvElement;

    private int comboMin;

    private int comboMax;

    private int comboMin2;

    private int comboMax2;

    private RealmList<RealmElement> matchElements;

    private RealmList<RealmElement> matchElements2;

    private RealmList<RealmLong> matchMonsters;

    private int hpSkillTypeInt;

    private int atkSkillTypeInt;

    private int rcvSkillTypeInt;

    private RealmLeaderSkillType hpSkillType;

    private RealmLeaderSkillType atkSkillType;

    private RealmLeaderSkillType rcvSkillType;

    private RealmList<RealmInt> hpPercent;
    private int minimumMatch;
    private int boardSize;
    private boolean noSkyfall;
//    private double hpMultiplier;
//    private double atkElement1Multiplier;
//    private double atkElement2Multiplier;
//    private double rcvMultiplier;

    public LeaderSkill() {
//        hpMultiplier = 1;
//        atkElement1Multiplier = 1;
//        atkElement2Multiplier = 1;
//        rcvMultiplier = 1;
        hpData = new RealmList<>();
        atkData = new RealmList<>();
        rcvData = new RealmList<>();
        hpType = new RealmList<>();
        hpElement = new RealmList<>();
        atkType = new RealmList<>();
        atkType2 = new RealmList<>();
        atkElement = new RealmList<>();
        rcvType = new RealmList<>();
        rcvElement = new RealmList<>();
        matchElements = new RealmList<>();
        matchElements2 = new RealmList<>();
        matchMonsters = new RealmList<>();
        hpPercent = new RealmList<>();
        boardSize = 1;
        noSkyfall = false;
    }

    public RealmList<RealmDouble> getAtkData() {
        return atkData;
    }
    @ParcelPropertyConverter(RealmDoubleParcelConverter.class)
    public void setAtkData(RealmList<RealmDouble> atkData) {
        this.atkData = atkData;
    }

    public void addAtkData(RealmDouble data) {
        atkData.add(data);
    }

    public RealmList<RealmInt> getAtkElement() {
        return atkElement;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setAtkElement(RealmList<RealmInt> atkElement) {
        this.atkElement = atkElement;
    }

    public void addAtkElement(int element) {
        atkElement.add(new RealmInt(element));
    }

    public RealmLeaderSkillType getAtkSkillType() {
        return atkSkillType;
    }

    public void setAtkSkillType(RealmLeaderSkillType atkSkillType) {
        this.atkSkillType = atkSkillType;
    }

    public RealmList<RealmInt> getAtkType() {
        return atkType;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setAtkType(RealmList<RealmInt> atkType) {
        this.atkType = atkType;
    }

    public void addAtkType(int type) {
        atkType.add(new RealmInt(type));
    }

    public int getComboMax2() {
        return comboMax2;
    }

    public void setComboMax2(int comboMax2) {
        this.comboMax2 = comboMax2;
    }

    public int getComboMax() {
        return comboMax;
    }

    public void setComboMax(int comboMax) {
        this.comboMax = comboMax;
    }

    public int getComboMin2() {
        return comboMin2;
    }

    public void setComboMin2(int comboMin2) {
        this.comboMin2 = comboMin2;
    }

    public int getComboMin() {
        return comboMin;
    }

    public void setComboMin(int comboMin) {
        this.comboMin = comboMin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<RealmDouble> getHpData() {
        return hpData;
    }

    @ParcelPropertyConverter(RealmDoubleParcelConverter.class)
    public void setHpData(RealmList<RealmDouble> hpData) {
        this.hpData = hpData;
    }

    public void addHpData(RealmDouble data) {
        hpData.add(data);
    }

    public RealmList<RealmInt> getHpElement() {
        return hpElement;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setHpElement(RealmList<RealmInt> hpElement) {
        this.hpElement = hpElement;
    }

    public void addHpElement(int element) {
        hpElement.add(new RealmInt(element));
    }

    public RealmLeaderSkillType getHpSkillType() {
        return hpSkillType;
    }

    public void setHpSkillType(RealmLeaderSkillType hpSkillType) {
        this.hpSkillType = hpSkillType;
    }

    public RealmList<RealmInt> getHpType() {
        return hpType;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setHpType(RealmList<RealmInt> hpType) {
        this.hpType = hpType;
    }

    public void addHpType(int type) {
        hpType.add(new RealmInt(type));
    }

    public RealmList<RealmElement> getMatchElements() {
        return matchElements;
    }

    @ParcelPropertyConverter(RealmElementListParcelConverter.class)
    public void setMatchElements(RealmList<RealmElement> matchElements) {
        this.matchElements = matchElements;
    }

    public void addMatchElements(int element) {
        matchElements.add(new RealmElement(element));
    }

    public RealmList<RealmElement> getMatchElements2() {
        return matchElements2;
    }

    @ParcelPropertyConverter(RealmElementListParcelConverter.class)
    public void setMatchElements2(RealmList<RealmElement> matchElements2) {
        this.matchElements2 = matchElements2;
    }

    public void addMatchElements2(int element) {
        matchElements2.add(new RealmElement(element));
    }

    public RealmList<RealmLong> getMatchMonsters() {
        return matchMonsters;
    }

    @ParcelPropertyConverter(RealmLongParcelConverter.class)
    public void setMatchMonsters(RealmList<RealmLong> matchMonsters) {
        this.matchMonsters = matchMonsters;
    }

    public void addMatchmonsters(RealmLong monsterId) {
        matchMonsters.add(monsterId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<RealmDouble> getRcvData() {
        return rcvData;
    }

    @ParcelPropertyConverter(RealmDoubleParcelConverter.class)
    public void setRcvData(RealmList<RealmDouble> rcvData) {
        this.rcvData = rcvData;
    }

    public void addRcvData(RealmDouble data) {
        rcvData.add(data);
    }

    public RealmList<RealmInt> getRcvElement() {
        return rcvElement;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setRcvElement(RealmList<RealmInt> rcvElement) {
        this.rcvElement = rcvElement;
    }

    public void addRcvElement(int element) {
        rcvElement.add(new RealmInt(element));
    }

    public RealmLeaderSkillType getRcvSkillType() {
        return rcvSkillType;
    }

    public void setRcvSkillType(RealmLeaderSkillType rcvSkillType) {
        this.rcvSkillType = rcvSkillType;
    }

    public RealmList<RealmInt> getRcvType() {
        return rcvType;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setRcvType(RealmList<RealmInt> rcvType) {
        this.rcvType = rcvType;
    }

    public void addRcvType(int type) {
        rcvType.add(new RealmInt(type));
    }

//    public double getAtkElement1Multiplier() {
//        return atkElement1Multiplier;
//    }
//
//    public void setAtkElement1Multiplier(double atkElement1Multiplier) {
//        this.atkElement1Multiplier = atkElement1Multiplier;
//    }

//    public double getAtkElement2Multiplier() {
//        return atkElement2Multiplier;
//    }
//
//    public void setAtkElement2Multiplier(double atkElement2Multiplier) {
//        this.atkElement2Multiplier = atkElement2Multiplier;
//    }

//    public double getHpMultiplier() {
//        return hpMultiplier;
//    }
//
//    public void setHpMultiplier(double hpMultiplier) {
//        this.hpMultiplier = hpMultiplier;
//    }

    public RealmList<RealmInt> getHpPercent() {
        return hpPercent;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setHpPercent(RealmList<RealmInt> hpPercent) {
        this.hpPercent = hpPercent;
    }

//    public double getRcvMultiplier() {
//        return rcvMultiplier;
//    }
//
//    public void setRcvMultiplier(double rcvMultiplier) {
//        this.rcvMultiplier = rcvMultiplier;
//    }

    public RealmList<RealmInt> getAtkElement2() {
        return atkElement2;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setAtkElement2(RealmList<RealmInt> atkElement2) {
        this.atkElement2 = atkElement2;
    }

    public RealmList<RealmInt> getAtkType2() {
        return atkType2;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setAtkType2(RealmList<RealmInt> atkType2) {
        this.atkType2 = atkType2;
    }

    public int getAtkSkillTypeInt() {
        return atkSkillTypeInt;
    }

    public void setAtkSkillTypeInt(int atkSkillTypeInt) {
        this.atkSkillTypeInt = atkSkillTypeInt;
    }

    public int getHpSkillTypeInt() {
        return hpSkillTypeInt;
    }

    public void setHpSkillTypeInt(int hpSkillTypeInt) {
        this.hpSkillTypeInt = hpSkillTypeInt;
    }

    public int getRcvSkillTypeInt() {
        return rcvSkillTypeInt;
    }

    public void setRcvSkillTypeInt(int rcvSkillTypeInt) {
        this.rcvSkillTypeInt = rcvSkillTypeInt;
    }

    public int getMinimumMatch() {
        return minimumMatch;
    }

    public void setMinimumMatch(int minimumMatch) {
        this.minimumMatch = minimumMatch;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isNoSkyfall() {
        return noSkyfall;
    }

    public void setNoSkyfall(boolean noSkyfall) {
        this.noSkyfall = noSkyfall;
    }
}
