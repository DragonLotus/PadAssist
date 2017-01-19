package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.Monster;
import com.padassist.Data.RealmElement;

import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class RealmMonsterListParcelConverter extends RealmListParcelConverter<Monster>{

    @Override
    public void itemToParcel(Monster input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input),0);
    }

    @Override
    public Monster itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Monster.class.getClassLoader()));
    }
}
