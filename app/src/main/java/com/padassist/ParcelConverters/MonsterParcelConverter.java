package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.LeaderSkill;
import com.padassist.Data.Monster;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class MonsterParcelConverter implements ParcelConverter<Monster>{

    @Override
    public void toParcel(Monster input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Monster fromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Monster.class.getClassLoader()));
    }
}
