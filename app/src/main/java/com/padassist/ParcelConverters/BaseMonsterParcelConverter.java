package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.BaseMonster;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class BaseMonsterParcelConverter implements ParcelConverter<BaseMonster>{

    @Override
    public void toParcel(BaseMonster input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public BaseMonster fromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(BaseMonster.class.getClassLoader()));
    }
}
