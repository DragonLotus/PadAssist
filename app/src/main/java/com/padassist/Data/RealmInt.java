package com.padassist.Data;

import org.parceler.Parcel;

import io.realm.RealmIntRealmProxy;
import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */
@Parcel(implementations = {RealmIntRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {RealmInt.class})
public class RealmInt extends RealmObject {
    private int value;

    public RealmInt() {
    }

    public RealmInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
