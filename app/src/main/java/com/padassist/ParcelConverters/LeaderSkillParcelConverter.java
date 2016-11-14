package com.padassist.ParcelConverters;

import android.os.Parcel;

import com.padassist.Data.ActiveSkill;
import com.padassist.Data.LeaderSkill;

import org.parceler.ParcelConverter;
import org.parceler.Parcels;

/**
 * Created by DragonLotus on 11/12/2016.
 */

public class LeaderSkillParcelConverter implements ParcelConverter<LeaderSkill>{

    @Override
    public void toParcel(LeaderSkill input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public LeaderSkill fromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(LeaderSkill.class.getClassLoader()));
    }
}
