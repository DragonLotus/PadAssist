package com.padassist.Data;

import org.parceler.Parcel;

import io.realm.RealmDoubleRealmProxy;
import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */
@Parcel(implementations = {RealmDoubleRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {RealmDouble.class})
public class RealmDouble extends RealmObject {
    private double value;

    public RealmDouble() {
    }

    public RealmDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
