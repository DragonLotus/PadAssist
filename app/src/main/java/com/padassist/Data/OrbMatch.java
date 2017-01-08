package com.padassist.Data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

import io.realm.OrbMatchRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Thomas on 7/11/2015.
 */
@org.parceler.Parcel(implementations = {OrbMatchRealmProxy.class},
        value = org.parceler.Parcel.Serialization.BEAN,
        analyze = {OrbMatch.class})
public class OrbMatch extends RealmObject {
    @PrimaryKey
    private long matchId;

    private int orbsLinked;

    private int numOrbPlus;

    private int elementInt;

//    private Element element;

    private boolean isRow;

    private boolean isCross;

    public OrbMatch(){
    }

    public OrbMatch(long matchId, int orbsLinked, int numOrbPlus, int elementInt, boolean isRow, boolean isCross) {
        this.matchId = matchId;
        this.orbsLinked = orbsLinked;
        this.numOrbPlus = numOrbPlus;
        this.elementInt = elementInt;
        this.isRow = isRow;
        this.isCross = isCross;
    }

    public int getOrbsLinked() {
        return orbsLinked;
    }

    public void setOrbsLinked(int orbsLinked) {
        this.orbsLinked = orbsLinked;
    }

    public int getNumOrbPlus() {
        return numOrbPlus;
    }

    public void setNumOrbPlus(int numOrbPlus) {
        this.numOrbPlus = numOrbPlus;
    }

    public Element getElement() {
        switch (elementInt){
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
            case 5:
                return Element.HEART;
            case 6:
                return Element.JAMMER;
            case 7:
                return Element.POISON;
            case 8:
                return Element.MORTAL_POISON;
            default:
                return null;
        }
    }

//    public void setElement(Element element) {
//        this.element = element;
//    }

    public int getElementInt() {
        return elementInt;
    }

    public void setElementInt(int elementInt) {
        this.elementInt = elementInt;
    }

    public boolean isRow() {
        return isRow;
    }

    public void setIsRow(boolean isRow) {
        this.isRow = isRow;
    }

    public boolean isCross() {
        return isCross;
    }

    public void setIsCross(boolean isCross) {
        this.isCross = isCross;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

}
