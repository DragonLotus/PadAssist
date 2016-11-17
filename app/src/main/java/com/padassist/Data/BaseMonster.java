package com.padassist.Data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.padassist.ParcelConverters.ActiveSkillParcelConverter;
import com.padassist.ParcelConverters.LeaderSkillParcelConverter;
import com.padassist.ParcelConverters.RealmIntParcelConverter;
import com.padassist.ParcelConverters.RealmLongParcelConverter;
import com.padassist.R;
import com.padassist.Util.DamageCalculationUtil;
import com.padassist.Util.ImageResourceUtil;
import com.padassist.Util.Singleton;

import org.parceler.ParcelPropertyConverter;

import java.io.File;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.realm.BaseMonsterRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DragonLotus on 9/15/2015.
 */
@org.parceler.Parcel(implementations = {BaseMonsterRealmProxy.class},
        value = org.parceler.Parcel.Serialization.BEAN,
        analyze = {BaseMonster.class})
public class BaseMonster extends RealmObject {
    @PrimaryKey
    private long monsterId;
    @Index
    private String monsterIdString;

    private long monsterNumber;

    private int atkMax;

    private int atkMin;

    private int hpMax;

    private int hpMin;

    private int maxLevel;

    private int rcvMin;

    private int rcvMax;

    private int type1;

    private int type2;

    private int type3;
    @Index
    private String type1String;
    @Index
    private String type2String;
    @Index
    private String type3String;

    private int maxAwakenings;

    private int element1int;

    private int element2int;

//    private RealmElement element1;
//
//    private RealmElement element2;

    private RealmList<RealmInt> awokenSkills;
    @Index
    private String activeSkillString;
    @Index
    private String leaderSkillString;

    private ActiveSkill activeSkill;

    private LeaderSkill leaderSkill;

    private String name;

    private double atkScale;

    private double rcvScale;

    private double hpScale;

    private int rarity;

    private int teamCost;

    private int xpCurve;

    private RealmList<RealmLong> evolutions;
    @Ignore
    DecimalFormat format = new DecimalFormat("0.00");

    public BaseMonster() {
        monsterId = 0;
        monsterNumber = 0;
        atkMax = 0;
        atkMin = 0;
        hpMin = 0;
        hpMax = 0;
        rcvMin = 0;
        rcvMax = 0;
        maxLevel = 0;
        atkScale = 0;
        rcvScale = 0;
        hpScale = 0;
        maxAwakenings = 0;
        element1int = -1;
        element2int = -1;
//        element1 = new RealmElement(0);
//        element2 = new RealmElement(0);
        name = "Empty";
        leaderSkillString = "Blank";
        activeSkillString = "Blank";
        type1 = -1;
        type2 = -1;
        type3 = -1;
        awokenSkills = new RealmList<>();
        evolutions = new RealmList<>();
        teamCost = 0;
    }

    public double getWeightedBase(){
        if(Singleton.getInstance().isCoopEnable()){
            return (double)getHpMin() * 1.5 / 10 + (double)getAtkMin() * 1.5 / 5 + (double)getRcvMin() * 1.5 / 3;
        } else {
            return (double)getHpMin() / 10 + (double)getAtkMin() / 5 + (double)getRcvMin() / 3;
        }
    }

    public String getWeightedBaseString(){
        return format.format(getWeightedBase());
    }

    public double getWeightedTotal(){
        if(Singleton.getInstance().isCoopEnable()){
            return (double)getHpMax() * 1.5 / 10 + (double)getAtkMax() * 1.5 / 5 + (double)getRcvMax() * 1.5 / 3;
        } else {
            return (double)getHpMax() / 10 + (double)getAtkMax() / 5 + (double)getRcvMax() / 3;
        }
    }

    public String getWeightedTotalString(){
        return format.format(getWeightedTotal());
    }

    public String getActiveSkillString() {
        return activeSkillString;
    }

    public void setActiveSkillString(String activeSkillString) {
        this.activeSkillString = activeSkillString;
    }

    public int getAtkMax() {
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

    public double getAtkScale() {
        return atkScale;
    }

    public void setAtkScale(double atkScale) {
        this.atkScale = atkScale;
    }

    public RealmList<RealmInt> getAwokenSkills() {
        return awokenSkills;
    }

    public RealmInt getAwokenSkills(int position) {
        return awokenSkills.get(position);
    }

    public void addAwokenSkills(RealmInt awakening) {
        awokenSkills.add(awakening);
    }
    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setAwokenSkills(RealmList<RealmInt> awokenSkills) {
        this.awokenSkills = awokenSkills;
    }

    public Element getElement1() {
        switch (element1int) {
            case 0:
                return Element.RED;
            case 1:
                return Element.BLUE;
            case 2:
                return Element.GREEN;
            case 3:
                return Element.LIGHT;
            case 4:
                return Element.DARK;
            default:
                return Element.BLANK;
        }

    }

    public void setElement1Int(int element1) {
        this.element1int = element1;
    }

    public Element getElement2() {
        switch (element2int) {
            case 0:
                return Element.RED;
            case 1:
                return Element.BLUE;
            case 2:
                return Element.GREEN;
            case 3:
                return Element.LIGHT;
            case 4:
                return Element.DARK;
            default:
                return Element.BLANK;
        }
    }

    public void setElement2Int(int element2) {
        this.element2int = element2;
    }

    public int getElement1Int() {
        return element1int;
    }

    public int getElement2Int() {
        return element2int;
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

    public double getHpScale() {
        return hpScale;
    }

    public void setHpScale(double hpScale) {
        this.hpScale = hpScale;
    }

    public String getLeaderSkillString() {
        return leaderSkillString;
    }

    public void setLeaderSkillString(String leaderSkillString) {
        this.leaderSkillString = leaderSkillString;
    }
    @ParcelPropertyConverter(ActiveSkillParcelConverter.class)
    public ActiveSkill getActiveSkill() {
        return activeSkill;
    }

    public void setActiveSkill(ActiveSkill activeSkill) {
        this.activeSkill = activeSkill;
    }

    public LeaderSkill getLeaderSkill() {
        return leaderSkill;
    }
    @ParcelPropertyConverter(LeaderSkillParcelConverter.class)
    public void setLeaderSkill(LeaderSkill leaderSkill) {
        this.leaderSkill = leaderSkill;
    }

    public int getMaxAwakenings() {
        return awokenSkills.size();
    }

    public void setMaxAwakenings(int maxAwakenings) {
        this.maxAwakenings = maxAwakenings;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public long getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(long monsterId) {
        this.monsterId = monsterId;
    }

    public Bitmap getMonsterPicture() {

        return ImageResourceUtil.getMonsterPicture(monsterId);
    }

//    public void setMonsterPicture(int monsterPicture) {
//        this.monsterPicture = monsterPicture;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getRcvScale() {
        return rcvScale;
    }

    public void setRcvScale(double rcvScale) {
        this.rcvScale = rcvScale;
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

    public int getType3() {
        return type3;
    }

    public void setType3(int type3) {
        this.type3 = type3;
    }

    public String getType1String() {
        return type1String;
    }

    public void setType1String(String type1String) {
        this.type1String = type1String;
    }

    public String getType3String() {
        return type3String;
    }

    public void setType3String(String type3String) {
        this.type3String = type3String;
    }

    public String getType2String() {
        return type2String;
    }

    public void setType2String(String type2String) {
        this.type2String = type2String;
    }

    public String getMonsterIdString() {
        return monsterIdString;
    }

    public void setMonsterIdString(String monsterIdString) {
        this.monsterIdString = monsterIdString;
    }

    public int getXpCurve() {
        return xpCurve;
    }

    public void setXpCurve(int xpCurve) {
        this.xpCurve = xpCurve;
    }

    public int getRarity() {
        return rarity;
    }

    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    public int getTeamCost() {
        return teamCost;
    }

    public void setTeamCost(int teamCost) {
        this.teamCost = teamCost;
    }

    public RealmList<RealmLong> getEvolutions() {
        return evolutions;
    }
    @ParcelPropertyConverter(RealmLongParcelConverter.class)
    public void setEvolutions(RealmList<RealmLong> evolutions) {
        this.evolutions = evolutions;
    }

    public ArrayList<Integer> getTypes() {
        ArrayList<Integer> types = new ArrayList<>();
        if (type1 >= 0) {
            types.add(type1);
        }
        if (type2 >= 0) {
            types.add(type2);
        }
        if (type3 >= 0) {
            types.add(type3);
        }
        return types;
    }

//    public static List<Monster> getAllMonsters() {
//        return new Select().from(BaseMonster.class).execute();
//    }
//
//    public static BaseMonster getMonsterId(long id) {
//        return new Select().from(BaseMonster.class).where("monsterId = ?", id).executeSingle();
//    }
//
//    public BaseMonster(Parcel source) {
//        monsterId = source.readLong();
//        atkMax = source.readInt();
//        atkMin = source.readInt();
//        hpMax = source.readInt();
//        hpMin = source.readInt();
//        maxLevel = source.readInt();
//        rcvMax = source.readInt();
//        rcvMin = source.readInt();
//        type1 = source.readInt();
//        type2 = source.readInt();
//        type3 = source.readInt();
//        maxAwakenings = source.readInt();
////        element1 = (Element) source.readSerializable();
////        element2 = (Element) source.readSerializable();
////        awokenSkills = source.readArrayList(Integer.class.getClassLoader());
//        activeSkillString = source.readString();
//        leaderSkillString = source.readString();
//        name = source.readString();
//        atkScale = source.readDouble();
//        rcvScale = source.readDouble();
//        hpScale = source.readDouble();
//        rarity = source.readInt();
//        teamCost = source.readInt();
//        xpCurve = source.readInt();
////        evolutions = source.readArrayList(Long.class.getClassLoader());
////        types = source.readArrayList(Integer.class.getClassLoader());
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(monsterId);
//        dest.writeInt(atkMax);
//        dest.writeInt(atkMin);
//        dest.writeInt(hpMax);
//        dest.writeInt(hpMin);
//        dest.writeInt(maxLevel);
//        dest.writeInt(rcvMax);
//        dest.writeInt(rcvMin);
//        dest.writeInt(type1);
//        dest.writeInt(type2);
//        dest.writeInt(type3);
//        dest.writeInt(maxAwakenings);
////        dest.writeSerializable(element1);
////        dest.writeSerializable(element2);
////        dest.writeList(awokenSkills);
//        dest.writeString(activeSkillString);
//        dest.writeString(leaderSkillString);
//        dest.writeString(name);
//        dest.writeDouble(atkScale);
//        dest.writeDouble(rcvScale);
//        dest.writeDouble(hpScale);
//        dest.writeInt(rarity);
//        dest.writeInt(teamCost);
//        dest.writeInt(xpCurve);
////        dest.writeList(evolutions);
////        dest.writeList(types);
//    }
//
//    public static final Parcelable.Creator<BaseMonster> CREATOR = new Creator<BaseMonster>() {
//        public BaseMonster createFromParcel(Parcel source) {
//            return new BaseMonster(source);
//        }
//
//        public BaseMonster[] newArray(int size) {
//            return new BaseMonster[size];
//        }
//    };

}
