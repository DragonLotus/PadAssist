package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Thomas on 7/11/2015.
 */
@Table(name = "OrbMatch")
public class OrbMatch extends Model implements Parcelable {
    @Column(name = "orbsLinked")
    private int orbsLinked;
    @Column(name = "numOrbPlus")
    private int numOrbPlus;
    @Column(name = "element")
    private Element element;
    @Column(name = "isRow")
    private boolean isRow;
    @Column(name = "isCross")
    private boolean isCross;

    public OrbMatch(){
    }

    public OrbMatch(int orbsLinked, int numOrbPlus, Element element, boolean isRow, boolean isCross) {
        this.orbsLinked = orbsLinked;
        this.numOrbPlus = numOrbPlus;
        this.element = element;
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
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

//    public boolean checkIfRow() {
//        return isRow;
//    }

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

    public OrbMatch(Parcel source) {
        orbsLinked = source.readInt();
        numOrbPlus = source.readInt();
        element = (Element) source.readSerializable();
        isRow = source.readByte() == 1;
        isCross = source.readByte() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orbsLinked);
        dest.writeInt(numOrbPlus);
        dest.writeSerializable(element);
        dest.writeByte((byte) (isRow ? 1 : 0));
        dest.writeByte((byte) (isCross ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<OrbMatch> CREATOR = new Creator<OrbMatch>() {
        public OrbMatch createFromParcel(Parcel source) {
            return new OrbMatch(source);
        }

        public OrbMatch[] newArray(int size) {
            return new OrbMatch[size];
        }
    };

    public static List<OrbMatch> getAllOrbMatches(){
        return new Select().from(OrbMatch.class).execute();
    }

    public static void deleteAllOrbMatches(){
        new Delete().from(OrbMatch.class).execute();
    }

}
