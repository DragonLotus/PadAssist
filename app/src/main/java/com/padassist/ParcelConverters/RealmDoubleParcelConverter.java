package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.RealmDouble;
import com.padassist.Data.RealmLong;

import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmDoubleParcelConverter extends RealmListParcelConverter<RealmDouble>{

    @Override
    public void itemToParcel(RealmDouble input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input),0);
    }

    @Override
    public RealmDouble itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmDouble.class.getClassLoader()));
    }
}
