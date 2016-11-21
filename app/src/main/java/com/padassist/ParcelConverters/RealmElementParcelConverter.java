package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.RealmElement;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmElementParcelConverter implements ParcelConverter<RealmElement>{

    @Override
    public void toParcel(RealmElement input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmElement fromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmElement.class.getClassLoader()));
    }
}
