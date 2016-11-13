package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.RealmInt;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class ActiveSkillParcelConverter implements ParcelConverter<ActiveSkill>{

    @Override
    public void toParcel(ActiveSkill input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public ActiveSkill fromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(ActiveSkill.class.getClassLoader()));
    }
}
