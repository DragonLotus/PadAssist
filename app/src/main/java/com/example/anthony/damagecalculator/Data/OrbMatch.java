package com.example.anthony.damagecalculator.Data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thomas on 7/11/2015.
 */
public class OrbMatch implements Parcelable
{
   private int orbsLinked;
   private int numOrbPlus;
   private Color color;
   private boolean isRow;

   public OrbMatch(int orbsLinked, int numOrbPlus, Color color, boolean isRow)
   {
      this.orbsLinked = orbsLinked;
      this.numOrbPlus = numOrbPlus;
      this.color = color;
      this.isRow = isRow;
   }

   public int getOrbsLinked()
   {
      return orbsLinked;
   }

   public void setOrbsLinked(int orbsLinked)
   {
      this.orbsLinked = orbsLinked;
   }

   public int getNumOrbPlus()
   {
      return numOrbPlus;
   }

   public void setNumOrbPlus(int numOrbPlus)
   {
      this.numOrbPlus = numOrbPlus;
   }

   public Color getColor()
   {
      return color;
   }

   public void setColor(Color color)
   {
      this.color = color;
   }

   public boolean checkIfRow()
   {
      return isRow;
   }

   public OrbMatch(Parcel source) {
      orbsLinked = source.readInt();
      numOrbPlus = source.readInt();
      color = (Color) source.readSerializable();
      isRow = source.readByte() == 1;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(orbsLinked);
      dest.writeInt(numOrbPlus);
      dest.writeSerializable(color);
      dest.writeByte((byte) (isRow ? 1 : 0));
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

}
