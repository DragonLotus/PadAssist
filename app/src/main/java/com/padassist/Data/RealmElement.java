package com.padassist.Data;

import org.parceler.Parcel;

import io.realm.RealmElementRealmProxy;
import io.realm.RealmObject;

/**
 * Created by DragonLotus on 8/4/2016.
 */
@Parcel(implementations = {RealmElementRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {RealmElement.class})
public class RealmElement extends RealmObject {
    private int value;

    public RealmElement() {
    }

    public RealmElement(int value) {
        this.value = value;
    }

    public Element getElement() {
        switch (value) {
            case 0:
                return Element.RED;
            case 1:
                return Element.BLUE;
            case 2:
                return Element.GREEN;
            case 3:
                return Element.LIGHT;
            case 4:
                return Element.DARK;
            case 5:
                return Element.HEART;
            case 6:
                return Element.JAMMER;
            case 7:
                return Element.POISON;
            case 8:
                return Element.MORTAL_POISON;
            default:
                return null;
        }
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
