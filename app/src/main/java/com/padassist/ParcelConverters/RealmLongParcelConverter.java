package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.RealmInt;
import com.padassist.Data.RealmLong;

import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmLongParcelConverter extends RealmListParcelConverter<RealmLong>{

    @Override
    public void itemToParcel(RealmLong input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input),0);
    }

    @Override
    public RealmLong itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmLong.class.getClassLoader()));
    }
}
