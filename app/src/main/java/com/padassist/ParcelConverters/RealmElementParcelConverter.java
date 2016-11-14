package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.RealmElement;
import com.padassist.Data.RealmLong;

import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmElementParcelConverter extends RealmListParcelConverter<RealmElement>{

    @Override
    public void itemToParcel(RealmElement input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input),0);
    }

    @Override
    public RealmElement itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmElement.class.getClassLoader()));
    }
}
