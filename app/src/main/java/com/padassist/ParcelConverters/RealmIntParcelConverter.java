package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.RealmInt;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

import io.realm.RealmList;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmIntParcelConverter extends RealmListParcelConverter<RealmInt>{

    @Override
    public void itemToParcel(RealmInt input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input),0);
    }

    @Override
    public RealmInt itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmInt.class.getClassLoader()));
    }
}
