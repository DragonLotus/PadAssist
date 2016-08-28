package com.padassist.Data;

import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */

public class RealmString extends RealmObject {
    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
