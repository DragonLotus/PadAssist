package com.padassist.ParcelConverters;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by DragonLotus on 11/13/2016.
 */

public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {

    @Override
    public RealmList<T> createCollection() {
        return new RealmList<T>();
    }
}
