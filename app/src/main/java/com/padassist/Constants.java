package com.padassist;

import com.padassist.Data.Monster;

import io.realm.Realm;

/**
 * Created by Thomas on 4/7/2016.
 */
public class Constants {
    public static String VERSION = "VERSION";
    public static String KEY_INITIALIZED = "INITIALIZED";
    public static int numOfSavedMonsters = Realm.getDefaultInstance().where(Monster.class).findAll().size();
}
