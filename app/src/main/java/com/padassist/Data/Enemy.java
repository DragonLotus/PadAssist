package com.padassist.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.padassist.ParcelConverters.RealmElementParcelConverter;
import com.padassist.ParcelConverters.RealmIntParcelConverter;

import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;

import io.realm.EnemyRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anthony on 7/16/2015.
 */
@org.parceler.Parcel(implementations = {EnemyRealmProxy.class},
        value = org.parceler.Parcel.Serialization.BEAN,
        analyze = {Enemy.class})
public class Enemy extends RealmObject {
    @PrimaryKey
    private long enemyId;
    @Index
    private String enemyName;
    private long monsterIdPicture;
    private int targetDef;
    private int beforeDefenseBreak;
    private int damageThreshold;
    private int damageImmunity;
    private int reductionValue;
    private long targetHp;
    private long currentHp;
    private long beforeGravityHP;
    private double gravityPercent;
    private RealmList<RealmElement> targetElement;
    private RealmList<RealmElement> reduction;
    private RealmList<RealmElement> absorb;
    private RealmList<RealmInt> gravityList;
    private RealmList<RealmInt> types;
    private boolean hasAbsorb = false;
    private boolean hasReduction;
    private boolean hasDamageThreshold;
    private boolean isDamaged;
    private boolean hasDamageImmunity;


    //default is DKali from Arena 3
    public Enemy() {
        enemyId = 0;
        enemyName = "Blazing Goddess of Power, Kali";
        monsterIdPicture = 2078;
        absorb = new RealmList<>();
        reduction = new RealmList<RealmElement>();
        gravityList = new RealmList<RealmInt>();
        types = new RealmList<RealmInt>();
        targetHp = 37408889;
        targetDef = 0;
        currentHp = targetHp;
        beforeGravityHP = currentHp;
        beforeDefenseBreak = targetDef;
        targetElement = new RealmList<>();
        targetElement.add(new RealmElement(4));
        targetElement.add(new RealmElement(0));
        gravityPercent = 1;
        damageThreshold = 200000;
        damageImmunity = 1000000;
        isDamaged = false;
        hasReduction = true;
        hasDamageThreshold = false;
        hasDamageImmunity = false;
        reduction.add(new RealmElement(0));
        reduction.add(new RealmElement(1));
        reduction.add(new RealmElement(2));
        reduction.add(new RealmElement(3));
        reduction.add(new RealmElement(4));
        types.add(new RealmInt(5));
        types.add(new RealmInt(4));
        types.add(new RealmInt(1337));
        reductionValue = 50;
    }


    public long getTargetHp() {
        return targetHp;
    }

    public void setTargetHp(long targetHp) {
        this.targetHp = targetHp;
    }

    public long getCurrentHp() {
        return currentHp;
    }

    public void setCurrentHp(long currentHp) {
        this.currentHp = currentHp;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    public int getTargetDef() {
        return targetDef;
    }

    public void setTargetDef(int targetDef) {
        this.targetDef = targetDef;
    }

    public double getGravityPercent() {
        return gravityPercent;
    }

    public void setGravityPercent(double gravityPercent) {
        this.gravityPercent = gravityPercent;
    }

    public Element getTargetCurrentElement() {
        if((double)targetHp/(double)currentHp > .5){
            return targetElement.get(0).getElement();
        } else {
            return targetElement.get(1).getElement();
        }
    }

    public void setTargetElement1(int targetElement) {
        this.targetElement.set(0,new RealmElement(targetElement));
    }

    public void setTargetElement2(int targetElement) {
        this.targetElement.set(1,new RealmElement(targetElement));
    }

    public long getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(long enemyId) {
        this.enemyId = enemyId;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public boolean getHasDamageImmunity() {
        return hasDamageImmunity;
    }

    public boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(boolean damaged) {
        isDamaged = damaged;
    }

    public long getMonsterIdPicture() {
        return monsterIdPicture;
    }

    public void setMonsterIdPicture(long monsterIdPicture) {
        this.monsterIdPicture = monsterIdPicture;
    }

    public RealmList<RealmElement> getTargetElement() {
        return targetElement;
    }

    @ParcelPropertyConverter(RealmElementParcelConverter.class)
    public void setTargetElement(RealmList<RealmElement> targetElement) {
        this.targetElement = targetElement;
    }

    public double getPercentHp() {
        if (targetHp == 0) {
            return 0;
        }
        return (double) currentHp / targetHp;
    }

    public long getBeforeGravityHP() {
        return beforeGravityHP;
    }

    public void setBeforeGravityHP(long beforeGravityHP) {
        this.beforeGravityHP = beforeGravityHP;
    }

    public int getBeforeDefenseBreak() {
        return beforeDefenseBreak;
    }

    public void setBeforeDefenseBreak(int beforeDefenseBreak) {
        this.beforeDefenseBreak = beforeDefenseBreak;
    }

    public int getDamageThreshold() {
        return damageThreshold;
    }

    public void setDamageThreshold(int damageThreshold) {
        this.damageThreshold = damageThreshold;
    }

    public RealmList<RealmElement> getAbsorb() {
        return absorb;
    }
    @ParcelPropertyConverter(RealmElementParcelConverter.class)
    public void setAbsorb(RealmList<RealmElement> absorb) {
        this.absorb = absorb;
    }

    public boolean absorbContains(Element element){
        for(int i = 0; i < absorb.size(); i++){
            if(absorb.get(i).getElement().equals(element)){
                return true;
            }
        }
        return false;
    }

    public RealmList<RealmElement> getReduction() {
        return reduction;
    }

    @ParcelPropertyConverter(RealmElementParcelConverter.class)
    public void setReduction(RealmList<RealmElement> reduction) {
        this.reduction = reduction;
    }

    public boolean reductionContains(Element element){
        for(int i = 0; i < reduction.size(); i++){
            if(reduction.get(i).getElement().equals(element)){
                return true;
            }
        }
        return false;
    }

    public boolean getHasAbsorb() {
        return hasAbsorb;
    }

    public void setHasAbsorb(boolean hasAbsorb) {
        this.hasAbsorb = hasAbsorb;
    }

    public boolean getHasDamageThreshold() {
        return hasDamageThreshold;
    }

    public void setHasDamageThreshold(boolean hasDamageThreshold) {
        this.hasDamageThreshold = hasDamageThreshold;
    }

    public void setIsDamaged(boolean isDamaged) {
        this.isDamaged = isDamaged;
    }

    public boolean getHasReduction() {
        return hasReduction;
    }

    public void setHasReduction(boolean hasReduction) {
        this.hasReduction = hasReduction;
    }

    public RealmList<RealmInt> getGravityList() {
        return gravityList;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setGravityList(RealmList<RealmInt> gravityList) {
        this.gravityList = gravityList;
    }

    public int getGravityList(int position) {
        return gravityList.get(position).getValue();
    }

    public void clearGravityList() {
        gravityList.clear();
    }

    public RealmList<RealmInt> getTypes() {
        return types;
    }

    @ParcelPropertyConverter(RealmIntParcelConverter.class)
    public void setTypes(RealmList<RealmInt> types) {
        this.types = types;
    }

    public int getReductionValue() {
        return reductionValue;
    }

    public void setReductionValue(int reductionValue) {
        this.reductionValue = reductionValue;
    }

    public boolean hasDamageImmunity() {
        return hasDamageImmunity;
    }

    public void setHasDamageImmunity(boolean hasDamageImmunity) {
        this.hasDamageImmunity = hasDamageImmunity;
    }

    public int getDamageImmunity() {
        return damageImmunity;
    }

    public void setDamageImmunity(int damageImmunity) {
        this.damageImmunity = damageImmunity;
    }

    public ArrayList<Integer> getGravityArrayList(){
        ArrayList<Integer> gravityArrayList = new ArrayList<>();
        for (int i = 0; i < gravityList.size(); i++){
            gravityArrayList.add(gravityList.get(i).getValue());
        }
        return gravityArrayList;
    }

//    public Enemy(Parcel source) {
//        targetHp = source.readLong();
//        currentHp = source.readLong();
//        targetDef = source.readInt();
//        beforeGravityHP = source.readLong();
//        beforeDefenseBreak = source.readInt();
//        damageThreshold = source.readInt();
//        gravityPercent = source.readDouble();
//        targetElement = (Element) source.readSerializable();
//        absorb = source.readArrayList(Element.class.getClassLoader());
//        reduction = source.readArrayList(Element.class.getClassLoader());
//        hasAbsorb = source.readByte() == 1;
//        hasReduction = source.readByte() == 1;
//        hasDamageThreshold = source.readByte() == 1;
//        isDamaged = source.readByte() == 1;
//        gravityList = source.readArrayList(Integer.class.getClassLoader());
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeLong(targetHp);
//        dest.writeLong(currentHp);
//        dest.writeInt(targetDef);
//        dest.writeLong(beforeGravityHP);
//        dest.writeInt(beforeDefenseBreak);
//        dest.writeInt(damageThreshold);
//        dest.writeDouble(gravityPercent);
//        dest.writeSerializable(targetElement);
//        dest.writeList(absorb);
//        dest.writeList(reduction);
//        dest.writeByte((byte) (hasAbsorb ? 1 : 0));
//        dest.writeByte((byte) (hasReduction ? 1 : 0));
//        dest.writeByte((byte) (hasDamageThreshold ? 1 : 0));
//        dest.writeByte((byte) (isDamaged ? 1 : 0));
//        dest.writeList(gravityList);
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Parcelable.Creator<Enemy> CREATOR = new Parcelable.Creator<Enemy>() {
//        public Enemy createFromParcel(Parcel source) {
//            return new Enemy(source);
//        }
//
//        public Enemy[] newArray(int size) {
//            return new Enemy[size];
//        }
//    };

}
