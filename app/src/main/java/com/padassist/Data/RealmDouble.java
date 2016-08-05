package com.padassist.Data;

import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */

public class RealmDouble extends RealmObject {
    private double value;

    public RealmDouble() {
    }

    public RealmDouble(long value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
