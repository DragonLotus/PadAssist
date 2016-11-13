package com.padassist.Data;

import org.parceler.Parcel;

import io.realm.RealmLongRealmProxy;
import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */

@Parcel(implementations = {RealmLongRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {RealmLong.class})
public class RealmLong extends RealmObject {
    private long value;

    public RealmLong() {
    }

    public RealmLong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
